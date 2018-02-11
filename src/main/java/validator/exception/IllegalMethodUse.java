package validator.exception;

public class IllegalMethodUse extends RuntimeException{
    private static final long serialVersionUID = 4888483741338791520L;

    public IllegalMethodUse(String message) {
        super(message);
    }

    public IllegalMethodUse(Throwable e) {
        super(e);
    }
}
