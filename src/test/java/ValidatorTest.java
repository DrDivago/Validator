import validator.exception.IllegalMethodUse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import validator.Validator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class ValidatorTest {

    private static final String PARAM_NOT_NULL_ERROR_MESSAGE = "param should be not null";
    private static final String PARAM_GREATER_THEN_ZERO_ERROR_MESSAGE = "param should be greater then 0";
    @Test
    void validateWithExceptionMessageTest() {
        Throwable throwable = Assertions.assertThrows(IllegalArgumentException.class, ()->
                Validator.validateWithException(null, IllegalArgumentException.class, PARAM_NOT_NULL_ERROR_MESSAGE));

        Assertions.assertEquals(PARAM_NOT_NULL_ERROR_MESSAGE, throwable.getMessage());
    }

    @Test
    void validateWithExceptionTest() {
        Throwable throwable = Assertions.assertThrows(IllegalArgumentException.class, ()->
                Validator.validateWithException(null, IllegalArgumentException.class));

        Assertions.assertEquals(PARAM_NOT_NULL_ERROR_MESSAGE, throwable.getMessage());
    }
    @Test
    void validatePredicateParamNullTest() {
        Assertions.assertThrows(IllegalMethodUse.class, ()->
                Validator.validateWithPredicate(null, null, null));
    }

    @Test
    void validatePredicatePredicateNullTest() {
        Assertions.assertThrows(IllegalMethodUse.class, ()->
                Validator.validateWithPredicate(10, null, null));
    }

    @Test
    void validatePredicateMessageNullTest() {
        Assertions.assertThrows(IllegalMethodUse.class, ()->
                Validator.validateWithPredicate(10, Validator.lessThenZero, null));
    }

    @Test
    void validatePredicateLessTheZeroTest() {
        Throwable throwable = Assertions.assertThrows(IllegalArgumentException.class, ()->
                Validator.validateWithPredicate(-1, Validator.lessThenZero));

        Assertions.assertEquals(PARAM_GREATER_THEN_ZERO_ERROR_MESSAGE, throwable.getMessage());
    }

    @Test
    void validatePredicateTest() {
        Validator.validateWithPredicate(10, Validator.lessThenZero);
    }

    @Test
    void vadidateNotNullTest() {
        Throwable throwable = Assertions.assertThrows(IllegalArgumentException.class, ()->
                Validator.validateNotNull(null));
        Assertions.assertEquals(PARAM_NOT_NULL_ERROR_MESSAGE, throwable.getMessage());
    }

    @Test
    void validateNotNullTest() {
        Validator.validateNotNull(10);
    }

    @Test
    void validateNotNullWithMessageNullTest() {
        Assertions.assertThrows(IllegalMethodUse.class, ()->
                Validator.validateNotNull(null, null));
    }

    @Test
    void validateNotNullValidTest() {
        Validator.validateNotNull(10, PARAM_GREATER_THEN_ZERO_ERROR_MESSAGE);
    }

    @Test
    void validateNotNullWithMessageTest() {
        Throwable throwable = Assertions.assertThrows(IllegalArgumentException.class, ()->
                Validator.validateNotNull(null, PARAM_NOT_NULL_ERROR_MESSAGE));

        Assertions.assertEquals(PARAM_NOT_NULL_ERROR_MESSAGE, throwable.getMessage());
    }

    @Test
    void validatePredicateNotDefaultTest() {
        Validator.validateWithPredicate(10, p->p < 0, PARAM_GREATER_THEN_ZERO_ERROR_MESSAGE);
    }

    @Test
    void validatePredicateNotDefaultThrowIllegalArgumentTest() {
        Throwable throwable = Assertions.assertThrows(IllegalArgumentException.class, ()->
                Validator.validateWithPredicate(-1, p->p < 0, PARAM_GREATER_THEN_ZERO_ERROR_MESSAGE));

        Assertions.assertEquals(PARAM_GREATER_THEN_ZERO_ERROR_MESSAGE , throwable.getMessage());
    }

    @Test
    void validateWithExceptionValid() {
        Validator.validateWithException(10, IllegalArgumentException.class, PARAM_GREATER_THEN_ZERO_ERROR_MESSAGE);
    }

    @Test
    void validatePrivateConstructorException() throws NoSuchMethodException {
        Constructor<Validator> constructor = Validator.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Assertions.assertThrows(InvocationTargetException.class, constructor::newInstance);
    }
}