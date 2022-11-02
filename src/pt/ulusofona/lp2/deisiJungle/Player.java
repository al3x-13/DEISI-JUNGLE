package pt.ulusofona.lp2.deisiJungle;

public class Player {
    int id;
    String name;
    Species species;
    int energyUnits;
    int currentMapPosition = 0;

    public Player(int id, String name, Species species, int energyUnits) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.energyUnits = energyUnits;
    }

    /**
     * @return Player ID
     */
    int getID() {
        return this.id;
    }

    /**
     * @return Player name
     */
    String getName() {
        return this.name;
    }

    /**
     * @return Player current energy units
     */
    int getEnergy() {
        return this.energyUnits;
    }

    /**
     * @return Player Species
     */
    Species getSpecies() {
        return this.species;
    }

    /**
     * Updates Player Map Position
     * @param newPosition New Player Position
     */
    void updateMapPosition(int newPosition) {
        this.currentMapPosition = newPosition;
    }
}
