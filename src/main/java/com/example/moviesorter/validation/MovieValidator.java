package com.example.moviesorter.validation;

import com.example.moviesorter.model.Movie;
import java.time.Year;

/**
 * Проверяет "сырые" (ещё не обёрнутые в {@link Movie}) значения полей
 * перед тем, как они попадут в объект {@link Movie}, а также проверяет
 * уже полностью собранный объект {@link Movie} одним вызовом.
 */
public class MovieValidator {

    private static final int TITLE_MIN_LENGTH = 1;
    private static final int TITLE_MAX_LENGTH = 150;

    // 1888 год — самый ранний из сохранившихся фильмов в истории кино.
    private static final int FIRST_FILM_YEAR = 1888;
    // Разрешаем указывать и фильмы, анонсированные на следующий год.
    private static final int MAX_RELEASE_YEAR = Year.now().getValue() + 1;
    private static final int MIN_DURATION_MINUTES = 1;
    // 600 минут (10 часов) — заведомо большой верхний предел.
    private static final int MAX_DURATION_MINUTES = 600;

    /**
     * Проверяет все три поля уже собранного объекта фильма.
     *
     * @throws ValidationException с указанием первого же поля, которое не прошло проверку
     */
    public void validate(Movie movie) throws ValidationException {
        validateTitle(movie.getTitle());
        validateReleaseYear(movie.getReleaseYear());
        validateDurationMinutes(movie.getDurationMinutes());
    }

    public void validateTitle(String title) throws ValidationException {
        if (title == null || title.isBlank()) {
            throw new ValidationException("Название не должно быть пустым");
        }
        int length = title.trim().length();
        if (length < TITLE_MIN_LENGTH || length > TITLE_MAX_LENGTH) {
            throw new ValidationException(
                    "Название должно быть от " + TITLE_MIN_LENGTH + " до " + TITLE_MAX_LENGTH + " символов");
        }
    }

    public void validateReleaseYear(int releaseYear) throws ValidationException {
        if (releaseYear < FIRST_FILM_YEAR || releaseYear > MAX_RELEASE_YEAR) {
            throw new ValidationException(
                    "Год выхода должен быть от " + FIRST_FILM_YEAR + " до " + MAX_RELEASE_YEAR);
        }
    }

    public void validateDurationMinutes(int durationMinutes) throws ValidationException {
        if (durationMinutes < MIN_DURATION_MINUTES || durationMinutes > MAX_DURATION_MINUTES) {
            throw new ValidationException(
                    "Продолжительность должна быть от " + MIN_DURATION_MINUTES
                            + " до " + MAX_DURATION_MINUTES + " минут");
        }
    }
}
