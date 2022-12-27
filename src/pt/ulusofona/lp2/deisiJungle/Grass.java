package pt.ulusofona.lp2.deisiJungle;

public class Grass extends Food {
    public Grass() {
        super('e', "grass.png", "Erva: +-20 energia");
    }

    /**
     * Herbivore/Omnivore: +20 energy units<p>
     * Carnivore: -20 energy units
     */
    @Override
    public int getFoodEnergyOnConsumption(Player player, int currenPlay) {
        Species species = player.getSpecies();
        if (species.getDiet() == DietType.HERBIVORE || species.getDiet() == DietType.OMNIVORE) {
            return 20;
        }
        return -20;
    }
}
