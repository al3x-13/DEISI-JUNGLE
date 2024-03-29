package pt.ulusofona.lp2.deisiJungle;

public class Player {
    private int id;
    private String name;
    private Species species;
    private int energyUnits;
    private int currentMapPosition = 1;  // Game cells' index starts at 1
    private int distanceCovered = 0;
    private int totalFoodConsumed = 0;
    private int consumedBananas = 0;

    public Player(int id, String name, Species species) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.energyUnits = species.getInitialEnergy();
    }

    /**
     * @return Player ID
     */
    public int getID() {
        return this.id;
    }

    /**
     * @return Player name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return Player current energy units
     */
    public int getEnergy() {
        return this.energyUnits;
    }

    /**
     * Sets player energy to the given value.
     * @param energy New player energy
     */
    public void setEnergy(int energy) {
        this.energyUnits = energy;
    }

    /**
     * @return Player Species
     */
    public Species getSpecies() {
        return this.species;
    }

    /**
     * @return Player current position in the map
     */
    public int getCurrentMapPosition() {
        return this.currentMapPosition;
    }

    /**
     * @return Distance covered by the player in the current game
     */
    public int getDistanceCovered() {
        return this.distanceCovered;
    }

    /**
     * @return Energy consumed per play
     */
    public int getEnergyConsumption() {
        return this.species.getEnergyConsumption();
    }

    /**
     * @return Banas consumed by the player in the current game
     */
    public int getConsumedBananas() {
        return this.consumedBananas;
    }

    /**
     * Updates Player Map Position
     * @param newPosition New Player Position
     */
    public void updateMapPosition(int newPosition) {
        this.currentMapPosition = newPosition;
    }

    /**
     * Increases player energy by the given amount of units.
     * @param energyUnits Energy units to increase from overall energy
     */
    public void increaseEnergy(int energyUnits) {
        if (this.energyUnits + energyUnits > 200) {
            this.energyUnits = 200;
        } else {
            this.energyUnits += energyUnits;
        }
    }

    /**
     * Decreases player energy by the given amount of units (move cost).
     * @param energyUnits Energy units to decrease from overall energy
     */
    public void decreaseEnergy(int energyUnits) {
        this.energyUnits -= energyUnits;
    }

    /**
     * Increases distance covered by the player
     * @param nrCells Number of cells to increase
     */
    public void increaseDistanceCovered(int nrCells) {
        this.distanceCovered += nrCells;
    }

    /**
     * Updates player energy with energy gained/lost food consumption with given energy.
     * Increases total consumed food count by 1.
     * @param foodEnergy Energy to add/subtract
     */
    public void consumeFood(int foodEnergy) {
        if (this.energyUnits + foodEnergy > 200) {
            this.energyUnits = 200;
        } else if (this.energyUnits + foodEnergy < 0) {
            this.energyUnits = 0;
        }else {
            this.energyUnits += foodEnergy;
        }
        this.totalFoodConsumed++;
    }

    /**
     * @return Total Food Consumed
     */
    public int getTotalFoodConsumed() {
        return this.totalFoodConsumed;
    }

    /**
     * Increases total bananas consumed by 1.
     */
    public void increaseBananasConsumedBy1() {
        this.consumedBananas++;
    }

    /**
     * Loads player data saved in game save.
     * @param currentMapPosition Current Map Position
     * @param distanceCovered Distance Covered
     * @param totalFoodConsumed Total Food Consumed
     * @param consumedBananas Consumed Bananas
     */
    public void loadSavedData(int energyUnits, int currentMapPosition, int distanceCovered, int totalFoodConsumed, int consumedBananas) {
        this.energyUnits = energyUnits;
        this.currentMapPosition = currentMapPosition;
        this.distanceCovered = distanceCovered;
        this.totalFoodConsumed = totalFoodConsumed;
        this.consumedBananas = consumedBananas;
    }
}
