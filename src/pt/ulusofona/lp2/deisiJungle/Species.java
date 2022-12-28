package pt.ulusofona.lp2.deisiJungle;

import java.util.Objects;

public class Species {
    private Character id;
    private String name;
    private String imageFilename;
    private DietType diet;
    private int energyUnits;
    private int energyConsumption;
    private int energyGainOnIdle;
    private int speedMin;
    private int speedMax;

    public Species(
            Character id,
            String name,
            String imageFilename,
            DietType diet,
            int initialEnergy,
            int energyConsumption,
            int energyGainOnIdle,
            int speedMin,
            int speedMax
    ) {
        this.id = id;
        this.name = name;
        this.imageFilename = imageFilename;
        this.diet = diet;
        this.energyUnits = initialEnergy;
        this.energyConsumption = energyConsumption;
        this.energyGainOnIdle = energyGainOnIdle;
        this.speedMin = speedMin;
        this.speedMax = speedMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Species species = (Species) o;
        return Objects.equals(id, species.id) && Objects.equals(name, species.name) && Objects.equals(imageFilename, species.imageFilename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imageFilename);
    }

    /**
     * Provides information about the species. The format is a String array as follows:<p>
     * [0] -> Species ID<p>
     * [1] -> Species Name<p>
     * [2] -> Species Image Filename, e.g. 'lion.png'
     * @return Species data
     */
    String[] getSpeciesData() {
        return new String[] { this.id.toString(), this.name, this.imageFilename };
    }

    /**
     * @return Species ID
     */
    Character getID() {
        return this.id;
    }

    /**
     * @return Species Name
     */
    String getName() {
        return this.name;
    }

    /**
     * @return Species Diet Type
     */
    public DietType getDiet() {
        return this.diet;
    }

    /**
     * @return Species current energy
     */
    public int getEnergyUnits() {
        return energyUnits;
    }

    /**
     * @return Species Speed in format "X..Y"
     */
    public String getSpeed() {
        return this.speedMin + ".." + this.speedMax;
    }

    /**
     * @return Species energy gain on idle
     */
    public int getEnergyGainOnIdle() {
        return this.energyGainOnIdle;
    }

    /**
     * @return Species energy consumption
     */
    public int getEnergyConsumption() {
        return this.energyConsumption;
    }
}
