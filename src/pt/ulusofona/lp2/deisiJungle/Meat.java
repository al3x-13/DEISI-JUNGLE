package pt.ulusofona.lp2.deisiJungle;

public class Meat extends Food {
    private boolean spoiled;

    public Meat() {
        super('c', "meat.png", "");
        this.spoiled = false;

        // TODO
    }

    @Override
    public int getFoodEnergyOnConsumption(Species species) {
        return 0;
    }
}
