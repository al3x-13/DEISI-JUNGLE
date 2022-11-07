package pt.ulusofona.lp2.deisiJungle;

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
}
