package validator;

import validator.exception.IllegalMethodUse;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class Validator {
    public static final  Predicate<Number> lessThenZero = p-> (Integer)p < 0;
    private static final Map<Predicate<?>, String> m = new HashMap<>();
    private static final String PARAM_SHOULD_NOT_BE_NULL = "should be not null";
    static {
        m.put(lessThenZero, "param should be greater then 0");
    }

    public static <T> void validateWithException(T param, Class<? extends RuntimeException> e, String message) {
        validateNotNull(e);
        if (param == null) {
            try {
                Class[] args = getStringArgsForExceptionInstance();
                throw e.getDeclaredConstructor(args).newInstance(message);
            } catch (IllegalAccessException| InstantiationException|
                    NoSuchMethodException| InvocationTargetException e1) {
                throw new IllegalMethodUse(e1);
            }
        }
    }

    private static Class[] getStringArgsForExceptionInstance() {
        Class[] args = new Class[1];
        args[0] = String.class;
        return args;
    }

    public static <T> void validateWithException(T param, Class<? extends RuntimeException> e) {
        if (param == null) {
            try {
                String errorMessage = constructErrorMessage("param");
                throw e.getDeclaredConstructor(getStringArgsForExceptionInstance()).newInstance(errorMessage);
            } catch (InstantiationException|IllegalAccessException|
                    NoSuchMethodException|InvocationTargetException e1) {
                throw new IllegalMethodUse(e1);
            }
        }
    }

    private static String constructErrorMessage(String param) {
        return param + " " +  PARAM_SHOULD_NOT_BE_NULL;
    }
    public static <T> void validateWithPredicate(T param, Predicate<T> predicate, String message) {
        validateWithException(param, IllegalMethodUse.class);
        validateWithException(predicate, IllegalMethodUse.class);
        validateWithException(message, IllegalMethodUse.class);
        if (!predicate.test(param)) {
            return;
        }
        throw new IllegalArgumentException(message);
    }

    public static <T> void validateWithPredicate(T param, Predicate<T> predicate) {
        validateWithException(param, IllegalMethodUse.class);
        validateWithException(predicate, IllegalMethodUse.class);
        if (!predicate.test(param)) {
            return;
        }
        throw new IllegalArgumentException(m.get(predicate));
    }

    public static <T> void validateNotNull(T param, String message) {
        validateWithException(message, IllegalMethodUse.class);
        if (param == null)
            throw new IllegalArgumentException(message);
    }

    public static <T> void validateNotNull(T param) {
        if (param == null) {
            String errorMessage = constructErrorMessage("param");
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private Validator() {
        throw new IllegalStateException("Utility class");
    }
}
