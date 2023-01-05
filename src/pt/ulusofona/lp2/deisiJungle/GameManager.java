package pt.ulusofona.lp2.deisiJungle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Scanner;

public class GameManager {
    // Game Species
    private ArrayList<Species> species = new ArrayList<>() {
        {
            add(new Species('E', "Elefante", "elephant.png", DietType.HERBIVORE, 180, 4, 10, 1, 6));
            add(new Species('L', "Leão", "lion.png", DietType.CARNIVORE, 80, 2, 10, 4, 6));
            add(new Species('T', "Tartaruga", "turtle.png", DietType.OMNIVORE, 150, 1, 5, 1, 3));
            add(new Species('P', "Pássaro", "bird.png", DietType.OMNIVORE, 70, 4, 50, 5, 6));
            add(new Species('Z', "Tarzan", "tarzan.png", DietType.OMNIVORE, 70, 2, 20, 1, 6));
        }
    };
    // Game Food Types
    private ArrayList<Food> foods = new ArrayList<>() {
        {
            add(new Grass());
            add(new Water());
            add(new Bananas());
            add(new Meat());
            add(new MagicMushrooms());
        }
    };
    private ArrayList<Player> players = new ArrayList<>();
    private GameMap map = null;
    private int currentPlay = 1;
    // Stores the current round player index for 'players' ArrayList
    private int currentRoundPlayerIndex;
    private String creditsImagePath = "src/images/credits.png";

    public GameManager() {}

    /**
     * Provides information about all the allowed species in the game.
     * Each element of the array has the following format:<p>
     * [0] -> Species ID<p>
     * [1] -> Species Name<p>
     * [2] -> Species Image Filename, e.g. 'turtle.png'
     * It must return at least 5 species.
     * @return Array containing info about allowed game species
     */
    public String[][] getSpecies() {
        String[][] output = new String[species.size()][];
        for (int i = 0; i < species.size(); i++) {
            output[i] = species.get(i).getSpeciesData();
        }
        return output;
    }

    /**
     * Provides information about all the allowed food types in the game.
     * Each element of the array has the following format:<p>
     * [0] -> Food ID<p>
     * [1] -> Food Name<p>
     * [2] -> Food Image Filename, e.g. 'meat.png'
     * It must return at least 5 food types.
     * @return Array containing info about allowed game food types
     */
    public String[][] getFoodTypes() {
        String[][] output = new String[foods.size()][];
        for (int i = 0; i < foods.size(); i++) {
            output[i] = foods.get(i).getFoodData();
        }
        return output;
    }

    /**
     * Creates the initial game Map.
     * @param jungleSize map size
     * @param playersInfo players info
     * @param foodsInfo foods info
     * @return Whether the initial map was successfully created
     */
    public void createInitialJungle(int jungleSize, String[][] playersInfo, String[][] foodsInfo) throws InvalidInitialJungleException {
        reset();  // Resets game data structures

        // Validates number of players (2-4 players)
        int numberOfPlayers = playersInfo.length;
        if (numberOfPlayers < 2 || numberOfPlayers > 4) {
            throw new InvalidInitialJungleException("Invalid number of players! The number of players must be between 2 and 4.",ExceptionCause.PLAYER);
        }

        // Validates jungleSize, the map must have at least 2 cells per player
        if (jungleSize < numberOfPlayers * 2) {
            throw new InvalidInitialJungleException("Invalid jungle size! The map must have at least 2 cells per player.",ExceptionCause.PLAYER);
        }

        // Validates players' info
        for (String[] player : playersInfo) {
            int playerID;
            try {
                playerID = Integer.parseInt(player[0]);
            } catch (NumberFormatException e) {
                throw new InvalidInitialJungleException("Invalid player ID! The ID must be a number.",ExceptionCause.PLAYER);
            }

            String playerName;
            if (player[1] == null || player[1].length() == 0) {
                throw new InvalidInitialJungleException("Invalid player name! The name must not be null nor empty.",ExceptionCause.PLAYER);
            } else {
                playerName = player[1];
            }

            Character playerSpeciesID;
            if (player[2] == null || player[2].length() == 0) {
                throw new InvalidInitialJungleException("Invalid Species ID! The Species ID must be a character.",ExceptionCause.PLAYER);
            } else {
                playerSpeciesID = player[2].charAt(0);
            }

            Species playerSpecies = getSpeciesByID(playerSpeciesID);

            // Validates player species (checks if it exists)
            if (playerSpecies == null) {
                throw new InvalidInitialJungleException("Invalid Species ID! The given Species does not exist.",ExceptionCause.PLAYER);
            }

            for (Player currentPlayer : players) {
                // Checks if the ID already exists
                if (currentPlayer.getID() == playerID) {
                    throw new InvalidInitialJungleException("Invalid player ID! This ID already exists.",ExceptionCause.PLAYER);
                }

                // In case the player species corresponds to 'tarzan' (cant have 2 'tarzan's)
                if (playerSpecies.getName().equals("Tarzan") && currentPlayer.getSpecies().getID() == playerSpeciesID) {
                    throw new InvalidInitialJungleException("Tarzan already exists!",ExceptionCause.PLAYER);
                }
            }

            // Creates new Player instance and adds it to 'players' ArrayList
            this.players.add(new Player(playerID, playerName, playerSpecies));
        }

        // Initializes game map and places players in the first 'MapCell'
        map = new GameMap(jungleSize, this.players);

        InitializationError validateFoodsReturn = validateFoodsAndAddToMap(foodsInfo, jungleSize);
        if (validateFoodsReturn != null) {
            throw new InvalidInitialJungleException("Invalid Food",ExceptionCause.FOOD);
        }

        sortPlayersByID();
        currentRoundPlayerIndex = 0;  // Sets the index of the first player to make a play
    }

    /**
     * Creates the initial game Map.
     * @param jungleSize map size
     * @param playersInfo players info
     * @return Whether the initial map was successfully created
     */
    public void createInitialJungle(int jungleSize, String[][] playersInfo) throws InvalidInitialJungleException  {
        createInitialJungle(jungleSize, playersInfo, null);
    }

    /**
     * Gets all player IDs in the given map cell.
     * @param squareNr map cell index
     * @return Array with all player IDs in the cell
     */
    public int[] getPlayerIds(int squareNr) {
        if (map.getMapSize() < squareNr){
            return new int[0];
        }

        // Gets cell object
        MapCell cell = map.getMapCell(squareNr);

        if (cell == null) {
            return new int[0];
        }

        return cell.getPlayerIDsInCell();
    }

    /**
     * Gets info about the given map cell in order for the Visualizer to draw it.<p>
     * Each element of the array has the following format:<p>
     * [0] -> Image Filename to draw in that cell, e.g. 'blank.png'<p>
     * [1] -> Cell description, e.g. 'empty', 'finish', 'Bananas : <N> : + 40 energia'<p>
     * [2] -> Player IDs in that cell, e.g. '3,5'
     * @param squareNr map cell index
     * @return Info about the given cell
     */
    public String[] getSquareInfo(int squareNr) {
        String[] squareInfo = new String[3];

        if (squareNr > 0 && squareNr <= map.getMapSize()) {
            // Gets map cell
            MapCell mapCell = map.getMapCell(squareNr);
            if (mapCell != null) {
                // Gets food in specific map cell
                Food food = mapCell.getFoodItem();

                if (food != null) {
                    // Updates food types dependent on current play number
                    updateFoodsDependentOnCurrentPlay(food, currentPlay);

                    squareInfo[0] = food.getImageFilename();
                    squareInfo[1] = food.getTooltip();
                } else {
                    squareInfo[0] = mapCell.getImageFilename();
                    squareInfo[1] = mapCell.getCellType();
                }

                // Converts players' IDs from ArrayList to String;
                squareInfo[2] = mapCell.getPlayerIDsInCellToString();

                return squareInfo;
            }
        }

        // Returns null in case the cell is not valid
        return null;
    }

    /**
     * Gets player info for the given ID.
     * @param playerId player identifier
     * @return The player info or null if the player does not exist
     */
    public String[] getPlayerInfo(int playerId) {
        Player player;

        // Gets player index or -1 if player ID is not valid
        int playerIndex = getPlayerIndex(playerId);
        if (playerIndex != -1) {  // Check if player exists
            player = this.players.get(playerIndex);

            String[] playerInfo = new String[5];
            playerInfo[0] = String.valueOf(player.getID());
            playerInfo[1] = String.valueOf(player.getName());
            playerInfo[2] = String.valueOf(player.getSpecies().getID());
            playerInfo[3] = String.valueOf(player.getEnergy());
            playerInfo[4] = player.getSpecies().getSpeed();

            return playerInfo;
        }
        return null;
    }

    /**
     * Gets info about the player who's currently playing.
     * @return Current player info
     */
    public String[] getCurrentPlayerInfo() {
        return getPlayerInfo(this.players.get(currentRoundPlayerIndex).getID());
    }

    /**
     * Gets all players' info.
     * @return Info from all players
     */
    public String[][] getPlayersInfo() {
        String[][] playersInfo = new String[this.players.size()][4];
        for (int i = 0; i < this.players.size(); i++) {
            playersInfo[i] = getPlayerInfo(this.players.get(i).getID());
        }
        return playersInfo;
    }

    /**
     * Gets info about energy for the player who's currently playing.
     * Each element of the array has the following format:<p>
     * [0] -> Energy consumption after moving <nrPositions><p>
     * [1] -> Energy gain by not moving<p>
     * @return Info about current player's energy
     */
    public String[] getCurrentPlayerEnergyInfo(int nrPositions) {
        String[] playerEnergyInfo = new String[2];
        playerEnergyInfo[0] = String.valueOf(
                Math.abs(this.players.get(currentRoundPlayerIndex).getSpecies().getEnergyConsumption() * nrPositions)
        );
        playerEnergyInfo[1] = String.valueOf(
                this.players.get(currentRoundPlayerIndex).getSpecies().getEnergyGainOnIdle()
        );
        return playerEnergyInfo;
    }

    /**
     * Moves the player who's currently playing to the desired map cell.
     * @param nrSquares number of cells to move (must be 1 <= nrSquares <= 6)
     * @param bypassValidation bypasses all previous validations when true
     * @return Whether current player was moved successfully
     */
    public MovementResult moveCurrentPlayer(int nrSquares, boolean bypassValidation) {
        // Gets current player
        Player currentPlayer = this.players.get(currentRoundPlayerIndex);
        Species currentPlayerSpecies = currentPlayer.getSpecies();
        int nrSquaresAbs = Math.abs(nrSquares);

        // Checks if 'nrSquares' is a valid value without 'bypassValidation'
        if (!bypassValidation && nrSquaresAbs != 0) {
            if (nrSquaresAbs < currentPlayerSpecies.getSpeedMin() || nrSquaresAbs > currentPlayerSpecies.getSpeedMax()) {
                switchToNextPlayerAndUpdateCurrentPlay();
                return new MovementResult(MovementResultCode.INVALID_MOVEMENT, null);
            }
        }

        // Checks if player has sufficient energy to make the play
        int playerEnergy = currentPlayer.getEnergy();
        int playerEnergyConsumedPerMove = currentPlayer.getEnergyConsumption();
        int playEnergyCost = nrSquaresAbs == 0 ?
                currentPlayerSpecies.getEnergyGainOnIdle()
                : nrSquaresAbs * playerEnergyConsumedPerMove;

        int playerCurrentPosition = currentPlayer.getCurrentMapPosition();
        int playerDestinationIndex = playerCurrentPosition + nrSquares;

        // Checks if player has moved or not
        if (nrSquaresAbs == 0) {
            // Increases player energy on idle
            currentPlayer.increaseEnergy(currentPlayerSpecies.getEnergyGainOnIdle());
        } else {
            if (playerEnergy < playEnergyCost) {
                switchToNextPlayerAndUpdateCurrentPlay();
                return new MovementResult(MovementResultCode.NO_ENERGY, null);
            }

            // Makes sure the player does not exceed map limit
            if (playerDestinationIndex > this.map.getFinishMapCellIndex()) {
                playerDestinationIndex = this.map.getFinishMapCellIndex();
            }

            // Makes sure the player cannot retreat behind position 1
            if (playerDestinationIndex < 1) {
                playerDestinationIndex = 1;
            }

            if (!map.movePlayerAndUpdateEnergy(currentPlayer, playerDestinationIndex, playEnergyCost)) {
                switchToNextPlayerAndUpdateCurrentPlay();
                return new MovementResult(MovementResultCode.INVALID_MOVEMENT, null);
            }

            // Increases player covered distance
            currentPlayer.increaseDistanceCovered(Math.abs(nrSquares));
        }


        boolean caughtFood = false;
        MapCell destinationCell = this.map.getMapCell(playerDestinationIndex);
        Food destinationCellFood = destinationCell.getFoodItem();
        // Checks if destination has food in it
        if (destinationCellFood != null && destinationCellFood.canBeConsumedBySpecies(currentPlayerSpecies)) {
            // Food consumption by the player
            destinationCellFood.consumeFood(currentPlayer, currentPlay);
            caughtFood = true;
        }

        // Updates player for next play
        switchToNextPlayerAndUpdateCurrentPlay();

        // Player movement completed successfully
        if (caughtFood) {
            return new MovementResult(MovementResultCode.CAUGHT_FOOD, "Apanhou " + destinationCellFood.name);
        }
        return new MovementResult(MovementResultCode.VALID_MOVEMENT, null);
    }

    /**
     * Gets info about the winner (player).
     * @return Winner info
     */
    public String[] getWinnerInfo() {
        if (!isGameOver()) {
            return null;
        }

        // Gets winner in case one has gotten to the finish
        for (Player player : this.players) {
            if (player.getCurrentMapPosition() == this.map.getFinishMapCellIndex()) {
                return new String[] {
                        String.valueOf(player.getID()),
                        player.getName(),
                        String.valueOf(player.getSpecies().getID()),
                        String.valueOf(player.getEnergy())
                };
            }
        }

        // Gets the second-closest player to the finish (distance between 2 closest players
        // to finish is greater than half of the map)
        int winnerID;
        int closestPlayerToFinishID = -1;
        int secondClosestPlayerToFinishID = -1;

        for (int i = map.getMapSize(); i > 0; i--) {
            MapCell currentCell = map.getMapCell(i);
            int[] playersIDsInCell = currentCell.getPlayerIDsInCell();

            for (int playerID : playersIDsInCell) {
                if (closestPlayerToFinishID < 0) {
                    closestPlayerToFinishID = playerID;
                } else if (secondClosestPlayerToFinishID < 0) {
                    secondClosestPlayerToFinishID = playerID;
                    break;
                }
            }
        }

        winnerID = secondClosestPlayerToFinishID;
        return getPlayerInfo(winnerID);
    }

    /**
     * Gets game results for each player (finish position, name, species, current map cell) in the following format:<p>
     * '#FINISH_POSITION NAME, SPECIES, CURRENT_MAP_CELL'
     * @return Game results
     */
    public ArrayList<String> getGameResults() {
        ArrayList<String> gameResults = new ArrayList<>();

        ArrayList<Integer> orderedPlayerIDs = this.map.getGameResultsByIDs();
        for (int i = 0; i < orderedPlayerIDs.size(); i++) {
            for (Player player : this.players) {
                if (player.getID() == orderedPlayerIDs.get(i)) {
                    // Builds game results string for each player
                    gameResults.add(
                            new StringBuilder()
                                    .append("#")
                                    .append(i + 1)
                                    .append(" ")
                                    .append(player.getName())
                                    .append(", ")
                                    .append(Normalizer.normalize(
                                            player.getSpecies().getName(), Normalizer.Form.NFD)
                                            .replaceAll("[^\\p{ASCII}]", "")
                                    )
                                    .append(", ")
                                    .append(player.getCurrentMapPosition())
                                    .append(", ")
                                    .append(player.getDistanceCovered())
                                    .append(", ")
                                    .append(player.getTotalFoodConsumed())
                                    .toString()
                    );
                    break;
                }
            }
        }

        return gameResults;
    }

    /**
     * Gets JPanel with credits.
     * @return Credits
     */
    public JPanel getAuthorsPanel() {
        JPanel credits = new JPanel();
        credits.setBounds(0, 0, 300, 300);
        credits.setBackground(new Color(195, 216, 218));
        File bgFile = new File(this.creditsImagePath);

        BufferedImage img;
        try {
            img = ImageIO.read(bgFile);
        } catch (IOException e) {
            return null;
        }

        JLabel bg = new JLabel(new ImageIcon(img));
        credits.add(bg);
        return credits;
    }

    /**
     * @return Who is Taborda
     */
    public String whoIsTaborda() {
        return "professional wrestling";
    }

    /**
     * Saves the current game state into a file so that it can be continued later.
     * @param file File to store game state
     * @return Whether the game state was successfully saved or not
     */
    public boolean saveGame(File file) {
        return generateGameSave(file);
    }

    /**
     * Loads game state from a file.
     * @param file File used to load game state
     * @return Whether the game state was successfully loaded or not
     */
    public boolean loadGame(File file) {
        return loadGameSave(file);
    }

    /**
     * Resets all structures used to store game data.<p>
     * Useful for resetting game and Unit tests.
     */
    public void reset() {
        players = new ArrayList<>();
        map = null;
    }

    /**
     * File format:<p>
     * Line | Info<p>
     * 1    | Current Play Number<p>
     * 2    | Current Round Player Index<p>
     * 4..7 | Player Data<p>
     * 8..  | Map Cell Data<p>
     * @param file File
     * @return Whether the save was successfully generated
     */
    private boolean generateGameSave(File file) {
        try {
            FileWriter fw = new FileWriter(file);
            saveGameInfo(fw);
            saveGameMap(fw);
            // Close file at the end
            fw.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    private void saveGameInfo(FileWriter fw) throws IOException {
        // Saves current play number
        fw.write(String.valueOf(currentPlay));
        fw.write('\n');
        // Saves current round player index
        fw.write(String.valueOf(currentRoundPlayerIndex));
        fw.write('\n');
        // Saves players
        // Format: '<id>,<name>,<speciesID>,<energy>,<mapPosition>,<distanceCovered>,<foodConsumed>,<consumedBananas>'
        for (int i = 0; i < 4; i++) {
            if (i < players.size()) {
                Player player = players.get(i);
                String[] playerData = new String[8];
                playerData[0] = String.valueOf(player.getID());
                playerData[1] = String.valueOf(player.getName());
                playerData[2] = String.valueOf(player.getSpecies().getID());
                playerData[3] = String.valueOf(player.getEnergy());
                playerData[4] = String.valueOf(player.getCurrentMapPosition());
                playerData[5] = String.valueOf(player.getDistanceCovered());
                playerData[6] = String.valueOf(player.getTotalFoodConsumed());
                playerData[7] = String.valueOf(player.getConsumedBananas());
                fw.write(String.join(",", playerData));
            } else {
                fw.write("NULL");
            }
            // Appends newline char at the end
            fw.write('\n');
        }
    }

    private void saveGameMap(FileWriter fw) throws IOException {
        // Saves map size
        fw.write(String.valueOf(this.map.getMapSize()));
        fw.write('\n');

        // Format: '<index>,<backgroundImageFilename>,<cellType>,<foodId>:<foodData>'
        for (int i = 1; i <= map.getMapSize(); i++) {
            MapCell mapCell = map.getMapCell(i);
            Food food = mapCell.getFoodItem();
            String[] cellData = new String[2];
            cellData[0] = String.valueOf(mapCell.getIndex());

            // Saves food data
            if (food != null) {
                switch (food.getID()) {
                    case 'b':
                        cellData[1] = food.getID() + ":" + ((Bananas) food).getConsumableUnits();
                        break;
                    case 'm':
                        cellData[1] = food.getID() + ":" + ((MagicMushrooms) food).getMagicNumber();
                        break;
                    default:
                        cellData[1] = food.getID() + ":NULL";
                }
            } else {
                cellData[1] = "NULL";
            }

            // Writes cell data to file
            fw.write(String.join(",", cellData));
            fw.write('\n');
        }
    }

    /**
     * @param file File
     * @return Whether the save was successfully loaded
     */
    private boolean loadGameSave(File file) {
        reset();
        try {
            Scanner scanner = new Scanner(file);
            loadGameInfo(scanner);
            loadGameMap(scanner);
            loadPlayersIntoCorrectCells();
            // Close scanner at the end
            scanner.close();
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    private void loadGameInfo(Scanner scanner) {
        // Gets current play number
        String currentPlayString = scanner.nextLine();
        this.currentPlay = Integer.parseInt(currentPlayString);
        // Gets current round player index
        String currentRoundPlayerIndexString = scanner.nextLine();
        this.currentRoundPlayerIndex = Integer.parseInt(currentRoundPlayerIndexString);

        // Gets players' info
        for (int i = 0; i < 4; i++) {
            String[] playerData = scanner.nextLine().split(",");
            if (!playerData[0].equals("NULL")) {
                int id = Integer.parseInt(playerData[0]);
                String name = playerData[1];
                char speciesID = playerData[2].charAt(0);
                int energy = Integer.parseInt(playerData[3]);
                int currentPos = Integer.parseInt(playerData[4]);
                int distanceCovered = Integer.parseInt(playerData[5]);
                int totalFood = Integer.parseInt(playerData[6]);
                int bananas = Integer.parseInt(playerData[7]);

                Species species = getSpeciesByID(speciesID);
                Player player = new Player(id, name, species);
                player.loadSavedData(energy, currentPos, distanceCovered, totalFood, bananas);
                this.players.add(player);
            }
        }
    }

    private void loadGameMap(Scanner scanner) {
        // Gets map size
        int mapSize = Integer.parseInt(scanner.nextLine());

        // Creates Game Map
        this.map = new GameMap(mapSize, this.players);

        while (scanner.hasNext()) {
            String[] cellData = scanner.nextLine().split(",");;
            int index = Integer.parseInt(cellData[0]);
            String foodString = cellData[1];

            // Gets map cell
            MapCell cell = this.map.getMapCell(index);

            if (!foodString.equals("NULL")) {
                String[] foodData = foodString.split(":");
                char foodID = foodData[0].charAt(0);
                Food foodItem = createFood(foodID);

                if (foodID == 'b') {
                    ((Bananas)foodItem).loadSavedData(Integer.parseInt(foodData[1]));
                }
                if (foodID == 'm') {
                    ((MagicMushrooms)foodItem).loadSavedData(Integer.parseInt(foodData[1]));
                }

                // Adds food to cell
                cell.setFood(foodItem);
            }
        }
    }

    private void loadPlayersIntoCorrectCells() {
        // Gets cell
        MapCell firstCell = this.map.getMapCell(1);

        // Removes players from the first cell
        for (Player player : this.players) {
            firstCell.rmPlayer(player.getID());
        }

        // Places players in the right cell
        for (Player player : this.players) {
            // Gets cell
            MapCell cell = this.map.getMapCell(player.getCurrentMapPosition());
            cell.addPlayer(player.getID());
        }
    }

    /**
     * Gets Species object from all game species using the species ID.<p>
     * If there's no Species for the given ID it returns null.
     * @param id Species ID
     * @return Species object or null
     */
    public Species getSpeciesByID(Character id) {
        for (Species species : species) {
            if (species.getID() == id) {
                return species;
            }
        }
        return null;
    }

    /**
     * Gets Food object from all game food types using the Food ID.<p>
     * If there's no Food that matches the given ID, it returns null.
     * @param id Food ID
     * @return Food object or null
     */
    public boolean foodExists(char id) {
        for (Food food : foods) {
            if (food.getID() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the player index in the ArrayList where players are stored.
     * @param playerID Player ID
     * @return The player index in the ArrayList or -1 in case the player does not exist.
     */
    public int getPlayerIndex(int playerID) {
        for (int index = 0; index < this.players.size(); index++) {
            if (this.players.get(index).getID() == playerID) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Sorts players ArrayList by ID (using quicksort algo).
     */
    public void sortPlayersByID() {
        // Selection Sort
        for (int i = 0; i < this.players.size() - 1; i++) {
            int lowestID = i;
            // Find the lowest ID
            for (int j = i+1; j < this.players.size(); j++) {
                if (this.players.get(j).getID() < this.players.get(lowestID).getID()) {
                    lowestID = j;
                }
            }
            ;
            Player temp = this.players.get(lowestID);
            this.players.set(lowestID, this.players.get(i));
            this.players.set(i, temp);
        }
    }

    /**
     * Checks if the game is over, i.e. if any player has arrived at the finish
     * or if the distance between the 2 closest player to the finish is greater
     * than half of the map size.
     * @return Whether the game is over or not
     */
    public boolean isGameOver() {
        // Checks if any of the players arrived at the finish cell (AKA won)
        for (Player player : this.players) {
            if (player.getCurrentMapPosition() == this.map.getFinishMapCellIndex()) {
                return true;
            }
        }

        // Checks if the distance between the 2 closest players to the finish is greater than half the map
        int closestPlayerToFinishPosition = -1;
        int secondClosestPlayerToFinishPosition = -1;
        for (int i = map.getMapSize(); i > 0; i--) {
            MapCell currentCell = map.getMapCell(i);
            int[] playersIDsInCell = currentCell.getPlayerIDsInCell();

            for (int playerID : playersIDsInCell) {
                if (closestPlayerToFinishPosition < 0) {
                    closestPlayerToFinishPosition = i;
                } else if (secondClosestPlayerToFinishPosition < 0) {
                    secondClosestPlayerToFinishPosition = i;
                    break;
                }
            }
        }

        return (closestPlayerToFinishPosition - secondClosestPlayerToFinishPosition) > (map.getMapSize() / 2);
    }

    /**
     * Updates the current shift player.
     */
    private void switchToNextPlayerAndUpdateCurrentPlay() {
        // Updates player for next play
        currentRoundPlayerIndex = currentRoundPlayerIndex + 1 < this.players.size() ? currentRoundPlayerIndex + 1 : 0;
        // Updates current playe number
        currentPlay++;
    }

    /**
     * Gets current play number.
     * @return Current play
     */
    public int getCurrentPlay() {
        return this.currentPlay;
    }

    /**
     * Creates a new Food object and returns it.
     * @param foodID Food type to be created
     * @return Food Object
     */
    private Food createFood(char foodID) {
        Food food = null;
        switch (foodID) {
            case 'e':
                food = new Grass();
                break;
            case 'a':
                food = new Water();
                break;
            case 'b':
                food = new Bananas();
                break;
            case 'c':
                food = new Meat();
                break;
            case 'm':
                food = new MagicMushrooms();
                break;
        }
        return food;
    }

    /**
     * Updates food types which depend on the current play number.
     * @param food Food To Be Updated
     * @param currentPlay Current Play Number
     */
    private void updateFoodsDependentOnCurrentPlay(Food food, int currentPlay) {
        switch (food.getID()) {
            case 'c':
                ((Meat) food).updateSpoilStatusAndTooltip(currentPlay);
                break;
        }
    }

    /**
     * @return Players
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /**
     * @return Game Map
     */
    public GameMap getMap() {
        return this.map;
    }

    /**
     * Validates game foods and adds them to the game map.
     * @param foodsInfo Foods Info
     * @param jungleSize Jungle Size
     * @return InitializationError if an error occurred during validation and null otherwise
     */
    private InitializationError validateFoodsAndAddToMap(String[][] foodsInfo, int jungleSize) {
        // Validates foods info
        if (foodsInfo != null) {
            for (String[] food : foodsInfo) {
                // Checks if food ID has a valid format
                char foodID;
                if (food[0] == null || food[0].length() == 0) {
                    return new InitializationError("Invalid Food ID! The Food ID must be a character.");
                }
                foodID = food[0].charAt(0);

                // Checks if food ID is valid (i.e. contained in 'getFoodTypes')
                if (!foodExists(foodID)) {
                    return new InitializationError("Invalid Food ID! The given Food ID does not exist.");
                }

                // Checks if food object has a valid position
                int foodPosition;
                try {
                    foodPosition = Integer.parseInt(food[1]);
                    if (foodPosition <= 1 || foodPosition >= jungleSize) {
                        return new InitializationError(
                                "Invalid Food Position! " +
                                        "Food position must be in the map range except start and finish positions."
                        );
                    }
                } catch (NumberFormatException e) {
                    return new InitializationError("Invalid Food Position! Food position must be an integer value.");
                }

                // Gets 'MapCell' in which the food will be placed
                MapCell foodCell = map.getMapCell(foodPosition);

                // Places food in the given map position
                // If that cell already has a food item returns an error
                Food newFood = createFood(foodID);
                foodCell.setFood(newFood);
            }
        }
        return null;
    }

    /**
     * Updates credits image path.
     * @param newPath Image Path
     */
    public void updateCreditsImagePath(String newPath) {
        this.creditsImagePath = newPath;
    }
}