package pt.ulusofona.lp2.deisiJungle;

public class Bananas extends Food {
    private int consumableUnits;

    public Bananas() {
        super('b', "Cacho de bananas", "bananas.png", "");
        this.consumableUnits = 3;
        this.tooltip = "Bananas : " + this.consumableUnits + " : " + "+ 40 energia";
    }

    /**
     * First banana: +40 energy units
     * After first banana: -40 energy units
     */
    @Override
    public int getFoodEnergyOnConsumption(Player player, int currentPlay) {
        if (player.getConsumedBananas() > 0) {
            return -40;
        } else {
            return 40;
        }
    }
}
