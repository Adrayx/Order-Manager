package bll.validators;

/**
 * Aceasta este interfața care va fi implementată de către fiecare validator
 * de care aș avea nevoie la introducerea datelor
 * @param <T>
 */

public interface Validator<T> {
    public void validate(T t);
}
