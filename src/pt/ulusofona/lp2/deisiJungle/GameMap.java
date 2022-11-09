package pt.ulusofona.lp2.deisiJungle;

import java.util.ArrayList;
import java.util.Arrays;

public class GameMap {
    int size;
    MapCell[] map;

    GameMap(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Map size must be greater than 1!");
        }

        this.size = size;
        this.map = new MapCell[size];

        // Initializes each cell of the map to its corresponding type
        for (int i = 0; i < this.map.length; i++) {
            int cellIndex = i+1;
            if (i == this.map.length - 1) {
                this.map[i] = new MapCell(cellIndex, "Meta");
            } else {
                this.map[i] = new MapCell(cellIndex, "Vazio");
            }
        }
    }

    /**
     * @return Map Size
     */
    int getMapSize() {
        return this.size;
    }

    /**
     * @return Index (game index) of the MapCell with finish
     */
    int getFinishMapCellIndex() {
        return this.size;
    }

    /**
     * Gets the corresponding Cell for the given index
     * @param squareNr Cell index
     * @return Cell
     */
    MapCell getMapCell(int squareNr){
        for (int i = 0 ; i < map.length ; i++){
            if (map[i].getIndex() == squareNr){
                return map[i];
            }
        }
        return null;
    }

    /**
     * Moves the given player from its current position to the given destination.
     * @param player Player to move
     * @param destIndex Index of the destination MapCell
     * @return Whether the player was successfully moved to the desired destination or not
     */
    boolean movePlayer(Player player, int destIndex, int energyCost) {
        // Cheks if player has enough energy to make the play
        if (player.getEnergy() < energyCost) {
            return false;
        }

        // Gets player current position in the map
        int currentIndex = player.getCurrentMapPosition();

        // Removes player from current 'MapCell'
        if (!this.map[currentIndex - 1].rmPlayer(player.getID())) {
            return false;
        }

        // Adds player to destination 'MapCell'
        if (!this.map[destIndex - 1].addPlayer(player.getID())) {
            return false;
        }

        // Charge energy for the player move
        player.decreaseEnergy(2);

        // Updates player current map position
        player.updateMapPosition(destIndex);

        return true;
    }

    /**
     * Places a player in a cell without any previous verification or validation.
     * Does not remove the player from the previous cell it was in, it that is the case.
     * @param playerID Player ID
     * @param cellIndex Destination cell index
     * @return Whether the player was successfully placed in the destination cell
     */
    boolean placePlayerInCell(int playerID, int cellIndex) {
        return this.map[cellIndex - 1].addPlayer(playerID);
    }

    /**
     * Gets the player IDs at game finish ordered from 1st to last.
     * @return Player IDs ordered by game finish positions
     */
    ArrayList<Integer> getPlayerIDsOrderedByFinishPosition() {
        ArrayList<Integer> playerIDs = new ArrayList<>();

        // Iterates over game map from finish to start to get player IDs ordered from 1st to last
        for (int i = this.map.length - 1; i >= 0 ; i--) {
            int[] ids = this.map[i].getPlayerIDsInCell();
            Arrays.sort(ids);

            for (int id : ids) {
                playerIDs.add(id);
            }
        }
        return playerIDs;
    }
}
