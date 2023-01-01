package pt.ulusofona.lp2.deisiJungle;

public class MagicMushrooms extends Food {
    private int magicNumber;

    public MagicMushrooms() {
        super('m', "Cogumelo Magico", "mushroom.png", "");
        this.magicNumber = (int) ((Math.random() * (50 - 10)) + 10);
        this.tooltip = "Cogumelo Magico : +- " + this.magicNumber + "% energia";
    }

    /**
     * Depends on the number of the current play.
     * Even play number: +magicNumber% energy
     * Odd play number: -magicNumber% energy
     */
    @Override
    public void consumeFood(Player player, int currentPlay) {
        int foodEnergy = (int) (player.getEnergy() * (this.magicNumber / 100.0f));
        foodEnergy = currentPlay % 2 == 0 ? foodEnergy : -foodEnergy;

        player.consumeFood(foodEnergy);
    }

    /**
     * @return Magic Number
     */
    public int getMagicNumber() {
        return magicNumber;
    }

    /**
     * @param magicNumber Magic Number
     */
    public void loadSavedData(int magicNumber) {
        this.magicNumber = magicNumber;
        this.tooltip = "Cogumelo Magico : +- " + this.magicNumber + "% energia";
    }
}
