package com.example.moviesorter.input;

import com.example.moviesorter.model.Movie;
import com.example.moviesorter.ui.ConsoleIO;
import com.example.moviesorter.validation.MovieValidator;
import com.example.moviesorter.validation.ValidationException;

/**
 * Реализация {@link DataSource}, которая считывает данные о фильмах
 * из консоли (пользователь вводит их вручную с клавиатуры).
 */
public class ManualDataSource implements DataSource {

    private final ConsoleIO consoleIO;
    private final MovieValidator validator;

    public ManualDataSource(ConsoleIO consoleIO, MovieValidator validator) {
        this.consoleIO = consoleIO;
        this.validator = validator;
    }

    @Override
    public Movie[] load(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размер массива должен быть положительным");
        }

        Movie[] movies = new Movie[size];
        for (int i = 0; i < size; i++) {
            consoleIO.printLine("Фильм " + (i + 1) + " из " + size + ":");
            String title = readValidTextField("  Название: ", validator::validateTitle);
            int releaseYear = readValidIntField("  Год выхода: ", validator::validateReleaseYear);
            int durationMinutes =
                    readValidIntField("  Продолжительность (мин): ", validator::validateDurationMinutes);

            movies[i] = Movie.builder()
                    .title(title)
                    .releaseYear(releaseYear)
                    .durationMinutes(durationMinutes)
                    .build();
        }
        return movies;
    }

    /**
     * Считывает текстовое поле и повторяет запрос до тех пор, пока
     * {@code fieldValidator} не примет введённое значение как корректное.
     */
    private String readValidTextField(String prompt, TextFieldValidator fieldValidator) {
        while (true) {
            String value = consoleIO.readLine(prompt);
            try {
                fieldValidator.validate(value);
                return value;
            } catch (ValidationException e) {
                consoleIO.printLine("  Ошибка: " + e.getMessage() + ". Повторите ввод.");
            }
        }
    }

    /**
     * Считывает числовое (целое) поле: если введённый текст не является
     * целым числом, запрос повторяется
     */
    private int readValidIntField(String prompt, IntFieldValidator fieldValidator) {
        while (true) {
            String raw = consoleIO.readLine(prompt).trim();
            int value;
            try {
                value = Integer.parseInt(raw);
            } catch (NumberFormatException e) {
                consoleIO.printLine("  Ошибка: введите целое число. Повторите ввод.");
                continue;
            }
            try {
                fieldValidator.validate(value);
                return value;
            } catch (ValidationException e) {
                consoleIO.printLine("  Ошибка: " + e.getMessage() + ". Повторите ввод.");
            }
        }
    }

    /**
     * Вспомогательный функциональный интерфейс. Он нужен, чтобы метод
     * {@link #readValidTextField} мог принимать разные методы проверки
     * (например {@link MovieValidator#validateTitle(String)}) в виде
     * ссылки на метод (например {@code validator::validateTitle}),
     * а не только конкретный метод по имени.
     */
    @FunctionalInterface
    private interface TextFieldValidator {
        void validate(String value) throws ValidationException;
    }

    /**
     * То же самое, что {@link TextFieldValidator}, но для полей типа int.
     * Позволяет {@link #readValidIntField} принимать и
     * {@link MovieValidator#validateReleaseYear(int)}, и
     * {@link MovieValidator#validateDurationMinutes(int)} как ссылки
     * на метод.
     */
    @FunctionalInterface
    private interface IntFieldValidator {
        void validate(int value) throws ValidationException;
    }
}
