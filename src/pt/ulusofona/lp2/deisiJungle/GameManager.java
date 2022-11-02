package pt.ulusofona.lp2.deisiJungle;

import javax.swing.*;
import java.util.ArrayList;

public class GameManager {
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
        Species elephant = new Species('E', "Elefante", "elephant.png");
        Species lion = new Species('L', "Leão", "lion.png");
        Species turtle = new Species('T', "Tartaruga", "turtle.png");
        Species bird = new Species('P', "Pássaro", "bird.png");
        Species tarzan = new Species('Z', "Tarzan", "tarzan.png");

        return new String[][] {
                elephant.getSpeciesData(),
                lion.getSpeciesData(),
                turtle.getSpeciesData(),
                bird.getSpeciesData(),
                tarzan.getSpeciesData()
        };
    }

    /**
     * Creates the initial game Map.
     * @param jungleSize map size
     * @param initialEnergy initial player energy
     * @param playersInfo players info
     * @return Whether the initial map was successfully created
     */
    public boolean createInitialJungle(int jungleSize, int initialEnergy, String[][] playersInfo) {
        // TODO
        return false;
    }

    /**
     * Gets all player IDs in the given map cell.
     * @param squareNr map cell index
     * @return Array with all player IDs in the cell
     */
    public int[] getPlayerIds(int squareNr) {
        // TODO
        return new int[0];
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
        // TODO
        return new String[0];
    }

    /**
     * Gets player info for the given ID.
     * @param playerId player identifier
     * @return The player info or null if the player does not exist
     */
    public String[] getPlayerInfo(int playerId) {
        // TODO
        return new String[0];
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
        // TODO: Is this right???
        return "professional wrestling";
    }
}
