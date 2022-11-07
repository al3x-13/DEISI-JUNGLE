package pt.ulusofona.lp2.deisiJungle;

public class Player {
    int id;
    String name;
    Species species;
    int energyUnits;
    int currentMapPosition = 1;  // Game cells' index starts at 1

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
     * @return Player current position in the map
     */
    int getCurrentMapPosition() {
        return this.currentMapPosition;
    }

    /**
     * Updates Player Map Position
     * @param newPosition New Player Position
     */
    void updateMapPosition(int newPosition) {
        this.currentMapPosition = newPosition;
    }

    /**
     * Decreases player energy by the given amount of units (move cost).
     * @param energyUnits Energy units to decrease from overall energy
     */
    void decreaseEnergy(int energyUnits) {
        this.energyUnits -= energyUnits;
    }
}
