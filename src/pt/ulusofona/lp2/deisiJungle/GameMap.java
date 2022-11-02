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
            if (i == this.map.length - 1) {
                this.map[i] = new MapCell("Meta");
            } else {
                this.map[i] = new MapCell("Vazio");
            }
        }
    }
}
