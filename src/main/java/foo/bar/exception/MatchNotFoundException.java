package foo.bar.exception;

/*
 * Represents the error of trying to operate a non-existing match.
 */
public class MatchNotFoundException extends RuntimeException{

    public MatchNotFoundException(String message) {
        super(message);
    }
}
