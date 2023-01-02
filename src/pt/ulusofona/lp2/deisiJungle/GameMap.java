package pt.ulusofona.lp2.deisiJungle;

import java.util.ArrayList;
import java.util.Arrays;

public class GameMap {
    int size;
    MapCell[] map;

    GameMap(int size, ArrayList<Player> players) {
        if (size < 1) {
            throw new IllegalArgumentException("Map size must be greater than 1!");
        }

        this.size = size;
        this.map = new MapCell[size];

        // Initializes each cell of the map to its corresponding type
        for (int i = 0; i < this.map.length; i++) {
            int cellIndex = i+1;
            if (i == 0) {
                this.map[i] = new MapCell(cellIndex, "Start");
            } else if (i == this.map.length - 1) {
                this.map[i] = new MapCell(cellIndex, "Finish");
            } else {
                this.map[i] = new MapCell(cellIndex, "Middle");
            }
        }

        // Places players in the first 'MapCell'
        for (Player player : players) {
            this.placePlayerInCell(player.getID(), 1);
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
        for (MapCell mapCell : map) {
            if (mapCell.getIndex() == squareNr) {
                return mapCell;
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
    boolean movePlayerAndUpdateEnergy(Player player, int destIndex, int energyCost) {
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
        player.decreaseEnergy(energyCost);

        // Updates player current map position
        player.updateMapPosition(destIndex);

        return true;
    }

    /**
     * Places a player in a cell without any previous verification or validation.
     * Does not remove the player from the previous cell it was in, it that is the case.
     *
     * @param playerID  Player ID
     * @param cellIndex Destination cell index
     */
    void placePlayerInCell(int playerID, int cellIndex) {
        this.map[cellIndex - 1].addPlayer(playerID);
    }

    /**
     * Gets the player IDs at game finish ordered from 1st to last.
     * @return Player IDs ordered by game finish positions
     */
    ArrayList<Integer> getGameResultsByIDs() {
        ArrayList<Integer> playerIDs = new ArrayList<>();

        // Checks if any of the players arrived at the finish cell (AKA won)
        MapCell finishCell = getMapCell(size);
        if (finishCell.getPlayerIDsInCell().length != 0) {
            playerIDs.add(finishCell.getPlayerIDsInCell()[0]);

            // Iterates over game map from finish to start to get player IDs ordered from 1st to last
            for (int i = size - 1; i > 0 ; i--) {
                int[] ids = getMapCell(i).getPlayerIDsInCell();
                Arrays.sort(ids);

                for (int id : ids) {
                    playerIDs.add(id);
                }
            }
        } else {
            // Checks if the distance between the 2 closest players to the finish is greater than half the map
            int closestPlayerToFinishPosition = -1;
            int secondClosestPlayerToFinishPosition = -1;
            for (int i = this.size; i > 0; i--) {
                MapCell currentCell = getMapCell(i);
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

            MapCell closestPlayerCell = getMapCell(closestPlayerToFinishPosition);
            MapCell secondClosestPlayerCell = getMapCell(secondClosestPlayerToFinishPosition);
            int[] secondClosestCellPlayerIDs = secondClosestPlayerCell.getPlayerIDsInCell();
            Arrays.sort(secondClosestCellPlayerIDs);
            playerIDs.add(secondClosestCellPlayerIDs[0]);
            playerIDs.add(closestPlayerCell.getPlayerIDsInCell()[0]);

            // Iterates over game map from finish to start to get player IDs ordered from 1st to last
            for (int i = secondClosestPlayerToFinishPosition; i > 0 ; i--) {
                int[] ids = getMapCell(i).getPlayerIDsInCell();
                Arrays.sort(ids);

                for (int j = 0; j < ids.length; j++) {
                    if (i == secondClosestPlayerToFinishPosition) {
                        if (j != 0) {
                            playerIDs.add(ids[j]);
                        }
                    } else {
                        playerIDs.add(ids[j]);
                    }
                }
            }
        }
        return playerIDs;
    }
}
