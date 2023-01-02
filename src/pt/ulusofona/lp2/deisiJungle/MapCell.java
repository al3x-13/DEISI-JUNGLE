package pt.ulusofona.lp2.deisiJungle;

import java.util.ArrayList;

public class MapCell {
    private int index;
    private ArrayList<Integer> playerIDsInCell = new ArrayList<>();
    private String backgroundImageFilename;
    private String cellType;  // "Vazio" or "Meta" to pass DP tests
    private Food foodItem = null;

    public MapCell(int cellIndex, String cellType) {
        if (cellIndex <= 0) {
            throw new IllegalArgumentException("Cell index must be greater than 1!");
        } else {
            this.index = cellIndex;
        }

        switch (cellType) {
            case "Start":
                this.cellType = "Vazio";
                backgroundImageFilename = "start.png";
                break;
            case "Finish":
                this.cellType = "Meta";
                backgroundImageFilename = "finish.png";
                break;
            case "Middle":
                this.cellType = "Vazio";
                backgroundImageFilename = "middle.png";
                break;
            default:
                throw new IllegalArgumentException(
                        "Cell type must be valid! The valid types are 'Start', 'Middle' and 'Finish'"
                );
        }
    }

    /**
     * Checks if the given player ID is present in the cell.
     * @param playerID Player ID
     * @return Whether the player ID is in the cell
     */
    public boolean hasPlayerID(int playerID) {
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
    public boolean addPlayer(int playerID) {
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
    public boolean rmPlayer(int playerID) {
        // Check if ID is in the cell
        if (!this.hasPlayerID(playerID)) {
            return false;
        }

        // Removes player ID
        for (int i = 0; i < this.playerIDsInCell.size(); i++) {
            int currentID = this.playerIDsInCell.get(i);
            if (currentID == playerID) {
                this.playerIDsInCell.remove(i);
            }
        }
        return true;
    }

    /**
     * @return Cell Index
     */
    public int getIndex(){
        return this.index;
    }

    /**
     * @return Cell type
     */
    public String getCellType() {
        return this.cellType;
    }

    /**
     * @return Image filename
     */
    public String getImageFilename() {
        return this.backgroundImageFilename;
    }

    /**
     * Gets player IDs in current cell in integer array format (e.g. [3,5], for players with ID 3 and 5).
     * @return Array with player IDs
     */
    public int[] getPlayerIDsInCell(){
        int output[] = new int[this.playerIDsInCell.size()];
        for (int i = 0; i < this.playerIDsInCell.size(); i++){
            output[i] = this.playerIDsInCell.get(i);
        }
        return output;
    }

    /**
     * Gets player IDs in current cell in String format (e.g. "3,5", for players with ID 3 and 5).
     * @return String with current players in cell
     */
    public String getPlayerIDsInCellToString() {
        String output = "";

        if (this.playerIDsInCell.size() > 0) {
            output = this.playerIDsInCell.toString();

            // Removes square brackets and empty spaces
            output = output.replace("[", "")
                    .replace("]", "")
                    .replace(" ", "");
        } else {;
            output = "";
        }
        return output;
    }

    /**
     * Adds food item to cell.
     * @param food Food to add
     */
    public void setFood(Food food) {
        this.foodItem = food;
    }

    /**
     * @return Cell Food Item, if there's no food returns null
     */
    public Food getFoodItem(){
        return this.foodItem;
    }
}
