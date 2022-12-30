package pt.ulusofona.lp2.deisiJungle;

public class Grass extends Food {
    public Grass() {
        super('e', "Erva","grass.png", "Erva : +-20 energia");
    }

    /**
     * Herbivore/Omnivore: +20 energy units<p>
     * Carnivore: -20 energy units
     */
    @Override
    public void consumeFood(Player player, int currentPlay) {
        int foodEnergy;
        Species species = player.getSpecies();
        if (species.getDiet() == DietType.HERBIVORE || species.getDiet() == DietType.OMNIVORE) {
            foodEnergy = 20;
        } else {
            foodEnergy = -20;
        }

        player.consumeFood(foodEnergy);
    }
}
