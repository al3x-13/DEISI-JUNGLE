package pt.ulusofona.lp2.deisiJungle;

public class Meat extends Food {
    private boolean spoiled;

    public Meat() {
        super('c', "Carne", "meat.png", "");
        this.spoiled = false;

        // TODO
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
    public int getFoodEnergyOnConsumption(Player player, int currentPlay) {
        Species species = player.getSpecies();
        if (species.getDiet() == DietType.CARNIVORE || species.getDiet() == DietType.OMNIVORE) {
            return currentPlay <= 12 ? 50 : -50;
        }
        return 0;
    }
}
