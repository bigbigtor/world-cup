package foo.bar.exception;

/**
 * Represents the error of trying to create a match involving a busy team.
 */
public class BusyTeamException extends RuntimeException {

    public BusyTeamException(String message) {
        super(message);
    }
}
