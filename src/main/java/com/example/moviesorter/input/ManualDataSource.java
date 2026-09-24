package com.example.moviesorter.input;

import com.example.moviesorter.model.Movie;
import com.example.moviesorter.ui.ConsoleIO;
import com.example.moviesorter.validation.MovieValidator;
import com.example.moviesorter.validation.ValidationException;
import java.util.function.Consumer;
import java.util.function.Function;

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
            String title = readField("  Название: ", s -> s, validator::validateTitle);
            int releaseYear = readField("  Год выхода: ", Integer::parseInt, validator::validateReleaseYear);
            int durationMinutes = readField(
                    "  Продолжительность (мин): ", Integer::parseInt, validator::validateDurationMinutes);

            movies[i] = Movie.builder()
                    .title(title)
                    .releaseYear(releaseYear)
                    .durationMinutes(durationMinutes)
                    .build();
        }
        return movies;
    }

    /**
     * Считывает поле: разбирает введённую строку с помощью {@code parser}
     * и проверяет результат с помощью {@code check}. При ошибке сообщает
     * о ней и повторяет запрос только для этого поля.
     */
    private <T> T readField(String prompt, Function<String, T> parser, Consumer<T> check) {
        while (true) {
            try {
                T value = parser.apply(consoleIO.readLine(prompt).trim());
                check.accept(value);
                return value;
            } catch (NumberFormatException e) {
                consoleIO.printLine("  Ошибка: введите целое число. Повторите ввод.");
            } catch (ValidationException e) {
                consoleIO.printLine("  Ошибка: " + e.getMessage() + ". Повторите ввод.");
            }
        }
    }
}
