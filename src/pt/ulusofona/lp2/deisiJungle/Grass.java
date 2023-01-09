package pt.ulusofona.lp2.deisiJungle;

public class Grass extends Food {
    public Grass() {
        super('e', "Erva","grass.png", "Erva : +- 20 energia");
    }

    /**
     * Herbivore/Omnivore: +20 energy units<p>
     * Carnivore: -20 energy units
     */
    @Override
    public void consumeFood(Player player, int currentPlay) {
        Species species = player.getSpecies();
        if (species.getDiet() == DietType.HERBIVORE || species.getDiet() == DietType.OMNIVORE) {
            player.consumeFood(20);
        } else {
            player.consumeFood(-20);
        }
        this.consumedCount++;
    }

    @Override
    public boolean canBeConsumedBySpecies(Species species) {
        return true;
    }
}
