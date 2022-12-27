package pt.ulusofona.lp2.deisiJungle;

public abstract class Food {
    protected char id;
    protected String name;
    protected String imageFilename;
    protected String tooltip;

    public Food(char id, String name, String imageFilename, String tooltip) {
        this.id = id;
        this.imageFilename = imageFilename;
        this.tooltip = tooltip;
    }

    /**
     * Gets the energy the given species will gain/lose based on the species' diet.
     * @param player Player
     * @param currentPlay Current play number
     * @return Amount of energy the species will gain/lose upon consuming the food
     */
    public abstract int getFoodEnergyOnConsumption(Player player, int currentPlay);

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
}
