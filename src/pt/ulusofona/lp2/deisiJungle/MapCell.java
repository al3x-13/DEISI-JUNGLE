package pt.ulusofona.lp2.deisiJungle;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MapCell {
    int index;
    ArrayList<Integer> playerIDsInCell = new ArrayList<>();
    String backgroundImageFilename;
    String cellType;  // "Vazio" or "Meta"

    public MapCell(int cellIndex, String cellType) {
        if (cellIndex <= 0) {
            throw new IllegalArgumentException("Cell index must be greater than 1!");
        } else {
            this.index = cellIndex;
        }

        switch (cellType) {
            case "Vazio":
                this.cellType = cellType;
                backgroundImageFilename = "blank.png";
                break;
            case "Meta":
                this.cellType = cellType;
                backgroundImageFilename = "finish.png";
                break;
            default:
                throw new IllegalArgumentException("Cell type must be valid! The valid types are 'Vazio' and 'Meta'");
        }
    }

    /**
     * Checks if the given player ID is present in the cell.
     * @param playerID Player ID
     * @return Whether the player ID is in the cell
     */
    boolean hasPlayerID(int playerID) {
        for (int id : this.playerIDsInCell) {
            if (id == playerID) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a player ID to the cell.
     * @param playerID Player ID
     * @return Whether the player ID was successfully added
     */
    boolean addPlayer(int playerID) {
        // Check if ID is already in cell
        if (this.hasPlayerID(playerID)) {
            return false;
        }

        // Adds player ID
        this.playerIDsInCell.add(playerID);
        return true;
    }

    /**
     * Removes a player ID from the cell.
     * @param playerID Player ID
     * @return Whether the player ID was successfully removed
     */
    boolean rmPlayer(int playerID) {
        // Check if ID is in the cell
        if (!this.hasPlayerID(playerID)) {
            return false;
        }

        // Removes player ID
        for (int i = 0; i < this.playerIDsInCell.size(); i++) {
            int currentID = this.playerIDsInCell.get(i);
            if (currentID == playerID) {
                this.playerIDsInCell.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Verifies if Cell is Empty
     * @return Whether the cell is Empty or Not
     */
    boolean isEmpty(){
        return this.playerIDsInCell.size() == 0;
    }

    /**
     * @return Index
     */
    int getIndex(){
        return this.index;
    }

    /**
     * @return Array With PlayerIDs
     */
    int[] getplayerIDsInCell(){
        int output[] = new int[playerIDsInCell.size()];
        for (int i = 0 ;i < playerIDsInCell.size(); i++){
            output[i] = playerIDsInCell.get(i);
        }
        return output;
    }
}
