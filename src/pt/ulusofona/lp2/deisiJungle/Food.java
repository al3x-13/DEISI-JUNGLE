package pt.ulusofona.lp2.deisiJungle;

public abstract class Food {
    protected char id;
    protected String name;
    protected String imageFilename;
    protected String tooltip;
    protected int consumedCount = 0;

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
     * Checks if the food can be consumed by the given species.
     * @param species Species
     * @return Whether the food can be consumed by the given species
     */
    public abstract boolean canBeConsumedBySpecies(Species species);

    /**
     * @return Food ID
     */
    public char getID() {
        return this.id;
    }

    /**
     * @return Food Name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Provides information about the food type. The format is a String array as follows:<p>
     * [0] -> Food ID<p>
     * [1] -> Food Name<p>
     * [2] -> Food Image Filename, e.g. 'water.png'
     * @return Food data
     */
    public String[] getFoodData() {
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
        return this.tooltip;
    }

    /**
     * @return Number of Times Consumed
     */
    public int getConsumedCount() {
        return this.consumedCount;
    }
}
