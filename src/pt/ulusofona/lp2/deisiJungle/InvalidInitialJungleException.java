package pt.ulusofona.lp2.deisiJungle;

enum ExceptionCause {
    PLAYER,
    FOOD
}

public class InvalidInitialJungleException extends Exception {
    private String message;
    boolean invalidPlayer;
    boolean invalidFood;

    public InvalidInitialJungleException(String message, ExceptionCause cause) {
        this.message = message;
        if (cause == ExceptionCause.PLAYER) {
            this.invalidPlayer = true;
            this.invalidFood = false;
        } else {
            this.invalidPlayer = false;
            this.invalidFood = true;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    public boolean isInvalidPlayer() {
        return invalidPlayer;
    }

    public boolean isInvalidFood() {
        return invalidFood;
    }
}
