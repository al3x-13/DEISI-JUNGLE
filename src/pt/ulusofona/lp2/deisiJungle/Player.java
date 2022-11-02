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
}
