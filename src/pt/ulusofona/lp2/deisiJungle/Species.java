package pt.ulusofona.lp2.deisiJungle;

import java.util.Objects;

public class Species {
    private char id;
    private String name;
    private String imageFilename;
    private DietType diet;
    private int initialEnergy;
    private int energyConsumption;
    private int energyGainOnIdle;
    private int speedMin;
    private int speedMax;

    public Species(
            char id,
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
        this.initialEnergy = initialEnergy;
        this.energyConsumption = energyConsumption;
        this.energyGainOnIdle = energyGainOnIdle;
        this.speedMin = speedMin;
        this.speedMax = speedMax;
    }

    /**
     * Provides information about the species. The format is a String array as follows:<p>
     * [0] -> Species ID<p>
     * [1] -> Species Name<p>
     * [2] -> Species Image Filename, e.g. 'lion.png'
     * @return Species data
     */
    public String[] getSpeciesData() {
        return new String[] {
                String.valueOf(this.id),
                this.name,
                this.imageFilename,
                String.valueOf(this.initialEnergy),
                String.valueOf(this.energyConsumption),
                String.valueOf(this.energyGainOnIdle),
                getSpeed()
        };
    }

    /**
     * @return Species ID
     */
    public Character getID() {
        return this.id;
    }

    /**
     * @return Species Name
     */
    public String getName() {
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
    public int getInitialEnergy() {
        return initialEnergy;
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

    /**
     * @return Species Min Speed
     */
    public int getSpeedMin() {
        return speedMin;
    }

    /**
     * @return Species Max Speed
     */
    public int getSpeedMax() {
        return speedMax;
    }
}
