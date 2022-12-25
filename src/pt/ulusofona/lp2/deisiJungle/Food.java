package pt.ulusofona.lp2.deisiJungle;

public abstract class Food {
    protected char id;
    protected String imageFilename;
    protected String tooltip;

    public Food(char id, String imageFilename, String tooltip) {
        this.id = id;
        this.imageFilename = imageFilename;
        this.tooltip = tooltip;
    }

    /**
     * Gets the energy the given species will gain/lose based on the species' diet.
     * @param species Species given
     * @return Amount of energy the species will gain/lose upon consuming the food
     */
    public abstract int getFoodEnergyOnConsumption(Species species);
}
