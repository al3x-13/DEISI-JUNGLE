package pt.ulusofona.lp2.deisiJungle;

public class Meat extends Food {
    private boolean spoiled;

    public Meat() {
        super('c', "Carne", "meat.png", "");
        this.spoiled = false;
    }

    /**
     * First 12 plays:<p>
     *      - Carnivore/Omnivore: +50 energy units<p>
     *      - Herbivore: 0 energy units<p>
     * After 12 plays:<p>
     *      - Carnivore/Omnivore: -50 energy units<p>
     *      - Herbivore: 0 energy units<p>
     */
    @Override
    public void consumeFood(Player player, int currentPlay) {
        Species species = player.getSpecies();
        if (species.getDiet() == DietType.CARNIVORE || species.getDiet() == DietType.OMNIVORE) {
            int foodEnergy = currentPlay <= 12 ? 50 : -50;
            player.consumeFood(foodEnergy);
        }
    }

    @Override
    public boolean canBeConsumedBySpecies(Species species) {
        DietType diet = species.getDiet();
        if (diet == DietType.CARNIVORE || diet == DietType.OMNIVORE) {
            return true;
        }
        return false;
    }

    /**
     * Updates Meat Spoil Status
     * @param currentPlay Current Player Number
     */
    public void updateSpoilStatusAndTooltip(int currentPlay) {
        if (currentPlay > 12) {
            this.spoiled = true;
        }

        this.tooltip = "Carne : + 50 energia : " + (currentPlay - 1) + " jogadas";
    }
}
