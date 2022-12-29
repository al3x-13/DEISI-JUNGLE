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
    public void consumeFood(Player player, int currentPlay) {
        int foodEnergy;
        if (player.getConsumedBananas() > 0) {
            foodEnergy = -40;
        } else {
            foodEnergy = 40;
        }

        player.consumeFood(foodEnergy);
        player.increaseBananasConsumedBy1();
    }
}
