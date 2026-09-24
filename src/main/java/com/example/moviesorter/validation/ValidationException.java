package com.example.moviesorter.validation;

/**
 * Выбрасывается, когда значение поля объекта
 * {@link com.example.moviesorter.model.Movie} не соответствует правилам
 * проверки (вне допустимого диапазона, слишком длинное и т.п.).
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
