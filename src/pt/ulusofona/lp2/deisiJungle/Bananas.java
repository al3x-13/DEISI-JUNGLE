package pt.ulusofona.lp2.deisiJungle;

public class Bananas extends Food {
    private int consumableUnits;

    public Bananas() {
        super('b', "Bananas", "bananas.png", "");
        this.consumableUnits = 3;
        this.tooltip = "Bananas : " + this.consumableUnits + " : " + "+ 40 energia";
    }

    /**
     * First banana: +40 energy units
     * After first banana: -40 energy units
     */
    @Override
    public void consumeFood(Player player, int currentPlay) {
        if (this.consumableUnits == 0) {
            return;
        }

        int foodEnergy;
        if (player.getConsumedBananas() > 0) {
            foodEnergy = -40;
        } else {
            foodEnergy = 40;
        }

        player.consumeFood(foodEnergy);
        player.increaseBananasConsumedBy1();
        this.consumableUnits--;
        this.consumedCount++;
        this.tooltip = "Bananas : " + this.consumableUnits + " : " + "+ 40 energia";
    }

    @Override
    public boolean canBeConsumedBySpecies(Species species) {
        return true;
    }

    /**
     * @return Consumable Units
     */
    public int getConsumableUnits() {
        return consumableUnits;
    }

    /**
     * @param consumableUnits Consumable Units
     */
    public void loadSavedData(int consumableUnits) {
        this.consumableUnits = consumableUnits;
        this.tooltip = "Bananas : " + this.consumableUnits + " : " + "+ 40 energia";
    }
}
