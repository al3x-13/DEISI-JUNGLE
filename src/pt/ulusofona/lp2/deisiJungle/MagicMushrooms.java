package pt.ulusofona.lp2.deisiJungle;

public class MagicMushrooms extends Food {
    private int magicNumber;

    public MagicMushrooms() {
        super('m', "mushroom.png", "");
        this.magicNumber = (int) ((Math.random() * (50 - 10)) + 10);
        this.tooltip = "Cogumelo Magico: +- " + this.magicNumber + "% energia";
    }

    /**
     * Depends on the number of the current play.
     * Even play number: +magicNumber% energy
     * Odd play number: -magicNumber% energy
     */
    @Override
    public int getFoodEnergyOnConsumption(Species species) {
        // TODO
        return 0;
    }
}
