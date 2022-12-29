package pt.ulusofona.lp2.deisiJungle;

public class Water extends Food {
    public Water() {
        super('a', "Agua", "water.png", "Agua: + 15U|20% energia");
    }

    /**
     * Carnivore/Herbivore: +15 energy units<p>
     * Omnivore: +20% energy
     */
    @Override
    public void consumeFood(Player player, int currentPlay) {
        int foodEnergy;
        Species species = player.getSpecies();
        if (species.getDiet() == DietType.OMNIVORE) {
            foodEnergy = (int) (species.getEnergyUnits() * 0.3f);
        } else {
            foodEnergy = 15;
        }

        player.consumeFood(foodEnergy);
    }
}
