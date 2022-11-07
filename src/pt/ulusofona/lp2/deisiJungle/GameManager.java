package pt.ulusofona.lp2.deisiJungle;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameManager {
    // Game Species
    ArrayList<Species> species = new ArrayList<>() {
        {
            add(new Species('E', "Elefante", "elephant.png"));
            add(new Species('L', "Leão", "lion.png"));
            add(new Species('T', "Tartaruga", "turtle.png"));
            add(new Species('P', "Pássaro", "bird.png"));
            add(new Species('Z', "Tarzan", "tarzan.png"));
        }
    };
    ArrayList<Player> players = new ArrayList<>();
    GameMap map = null;

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
     * Creates the initial game Map.
     * @param jungleSize map size
     * @param initialEnergy initial player energy
     * @param playersInfo players info
     * @return Whether the initial map was successfully created
     */
    public boolean createInitialJungle(int jungleSize, int initialEnergy, String[][] playersInfo) {
        reset();  // Resets game data structures

        // Validates 'jungleSize'
        if (jungleSize < 1) {
            return false;
        }

        // Validates 'initialEnergy'
        if (initialEnergy < 1) {
            return false;
        }

        // Validates number of players (2-4 players)
        int numberOfPlayers = playersInfo.length;
        if (numberOfPlayers < 2 || numberOfPlayers > 4) {
            return false;
        }

        // Validates number of map cells (at least 2 per player)
        if (jungleSize < numberOfPlayers * 2) {
            return false;
        }

        // Validates players' info
        for (String[] player : playersInfo) {
            int playerID;
            try {
                playerID = Integer.parseInt(player[0]);
            } catch (NumberFormatException e) {
                return false;
            }

            String playerName;
            if (player[1] == null || player[1].length() == 0) {
                return false;
            } else {
                playerName = player[1];
            }

            Character playerSpeciesID;
            if (player[2] == null || player[2].length() == 0) {
                return false;
            } else {
                playerSpeciesID = player[2].charAt(0);
            }

            // Gets Species by ID
            Species playerSpecies = getSpeciesByID(playerSpeciesID);

            // Validates player species (checks if it exists)
            if (playerSpecies == null) {
                return false;
            }

            for (Player currentPlayer : players) {
                // Checks if the ID already exists
                if (currentPlayer.getID() == playerID) {
                    return false;
                }

                // In case the player species corresponds to 'tarzan' (cant have 2 'tarzan's)
                if (playerSpecies.getName().equals("Tarzan") && currentPlayer.getSpecies().getID() == playerSpeciesID) {
                    return false;
                }
            }

            // Creates new Player instance and adds it to 'players' ArrayList
            this.players.add(new Player(playerID, playerName, playerSpecies, initialEnergy));
        }

        // Initializes game map
        map = new GameMap(jungleSize);

        // Sorts players by ID
        sortPlayersByID();

        // TODO: improve this
        return true;
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
     * [1] -> Cell description, e.g. 'empty', 'finish'<p>
     * [2] -> Player IDs in that cell, e.g. '3,5'
     * @param squareNr map cell index
     * @return Info about the given cell
     */
    public String[] getSquareInfo(int squareNr) {
        String[] squareInfo = new String[3];

        if (squareNr >= 0 && squareNr <= map.getMapSize()) {
            if (squareNr == map.getMapSize()){
                squareInfo[0] = "finish.png";
                squareInfo[1] = "Meta";
            }else {
                squareInfo[0] = "blank.png";
                squareInfo[1] = "Vazio";
            }

            // Converts players' IDs from ArrayList to String
            squareInfo[2] = map.getMapCell(squareNr).getPlayerIDsInCellToString();

            return squareInfo;
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

            String[] playerInfo = new String[4];
            playerInfo[0] = String.valueOf(player.getID());
            playerInfo[1] = String.valueOf(player.getName());
            playerInfo[2] = String.valueOf(player.getSpecies().getID());
            playerInfo[3] = String.valueOf(player.getEnergy());
            return playerInfo;
        }
        return null;
    }

    /**
     * Gets info about the player who's currently playing.
     * @return Current player info
     */
    public String[] getCurrentPlayerInfo() {
        // TODO
        return new String[0];
    }

    /**
     * Gets all players' info.
     * @return Info from all players
     */
    public String[][] getPlayersInfo() {
        // TODO
        return new String[0][0];
    }

    /**
     * Moves the player who's currently playing to the desired map cell.
     * @param nrSquares number of cells to move (must be 1 <= nrSquares <= 6)
     * @param bypassValidation bypasses all previous validations when true
     * @return Whether current player was moved successfully
     */
    public boolean moveCurrentPlayer(int nrSquares, boolean bypassValidation) {
        // TODO
        return false;
    }

    /**
     * Gets info about the winner (player).
     * @return Winner info
     */
    public String[] getWinnerInfo() {
        // TODO
        return new String[0];
    }

    /**
     * Gets game results for each player (finish position, name, species, current map cell) in the following format:<p>
     * '#FINISH_POSITION NAME, SPECIES, CURRENT_MAP_CELL'
     * @return Game results
     */
    public ArrayList<String> getGameResults() {
        // TODO
        return new ArrayList<>();
    }

    /**
     * Gets JPanel with credits.
     * @return Credits
     */
    public JPanel getAuthorsPanel() {
        // TODO
        return new JPanel();
    }

    /**
     * @return Who is Taborda
     */
    public String whoIsTaborda() {
        return "professional wrestling";
    }

    /**
     * Resets all structures used to store game data.<p>
     * Useful for resetting game and Unit tests.
     */
    public void reset() {
        players = new ArrayList<>();
        map = null;
        // TODO: update as we add more stuff
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
}