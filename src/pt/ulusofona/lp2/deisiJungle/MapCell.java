package pt.ulusofona.lp2.deisiJungle;

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
}
