package pt.ulusofona.lp2.deisiJungle;

public abstract class Food {
    protected char id;
    protected String name;
    protected String imageFilename;
    protected String tooltip;

    public Food(char id, String name, String imageFilename, String tooltip) {
        this.id = id;
        this.name = name;
        this.imageFilename = imageFilename;
        this.tooltip = tooltip;
    }

    /**
     * Updates player data (energy, etc.) based on the species' diet and the consumed food type.
     * @param player Player
     * @param currentPlay Current play number
     * @return Whether food was successfully consumed or not
     */
    public abstract void consumeFood(Player player, int currentPlay);

    /**
     * @return Food ID
     */
    public char getID() {
        return this.id;
    }

    /**
     * Provides information about the food type. The format is a String array as follows:<p>
     * [0] -> Food ID<p>
     * [1] -> Food Name<p>
     * [2] -> Food Image Filename, e.g. 'water.png'
     * @return Food data
     */
    String[] getFoodData() {
        return new String[] { String.valueOf(this.id), this.name, this.imageFilename };
    }

    /**
     * @return Food Filename
     */
    public String getImageFilename() {
        return this.imageFilename;
    }

    /**
     * @return Food Tooltip
     */
    public String getTooltip() {
        return tooltip;
    }
}
