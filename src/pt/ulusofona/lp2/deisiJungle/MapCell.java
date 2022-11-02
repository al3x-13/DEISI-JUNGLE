package pt.ulusofona.lp2.deisiJungle;

import java.util.ArrayList;

public class MapCell {
    ArrayList<Integer> playerIDsInCell = new ArrayList<>();
    String backgroundImageFilename;
    String cellType;  // "Vazio" or "Meta"

    public MapCell(String cellType) {
        switch (cellType) {
            case "Vazio":
                this.cellType = cellType;
                backgroundImageFilename = "blank.png";
                break;
            case "meta":
                this.cellType = cellType;
                backgroundImageFilename = "finish.png";
                break;
            default:
                throw new IllegalArgumentException("Cell type must be valid! The valid types are 'Vazio' and 'Meta'");
        }
    }
}
