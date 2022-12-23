package pt.ulusofona.lp2.deisiJungle;

public class InitializationError {
    private String message;

    InitializationError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
