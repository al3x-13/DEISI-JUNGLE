package pt.ulusofona.lp2.deisiJungle;

import java.util.Objects;

public class Species {
    Character id;
    String name;
    String imageFilename;

    public Species(Character id, String name, String imageFilename) {
        this.id = id;
        this.name = name;
        this.imageFilename = imageFilename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
}
