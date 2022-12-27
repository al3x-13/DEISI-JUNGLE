package pt.ulusofona.lp2.deisiJungle;

public class Water extends Food {
    public Water() {
        super('a', "water.png", "Agua: + 15U|20% energia");
    }

    /**
     * Carnivore/Herbivore: +15 energy units<p>
     * Omnivore: +20% energy
     * @param species Species given
     */
    @Override
    public int getFoodEnergyOnConsumption(Player player, int currentPlay) {
        Species species = player.getSpecies();
        if (species.getDiet() == DietType.OMNIVORE) {
            return (int) (species.getEnergyUnits() * 0.3f);
        }
        return 15;
    }
}
