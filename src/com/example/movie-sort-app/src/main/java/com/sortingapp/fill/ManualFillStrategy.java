package com.sortingapp.fill;

import com.sortingapp.model.Movie;
import com.sortingapp.validation.MovieValidator;
import com.sortingapp.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Заполняет массив данными, введёнными пользователем вручную с консоли.
 * Каждое поле переспрашивается до тех пор, пока не будет введено
 * корректное значение — некорректный ввод никогда не приводит к падению.
 */
public class ManualFillStrategy implements FillStrategy {

    /**
     * Функциональный интерфейс для валидации "сырого" строкового значения поля.
     *
     * @param <T> тип итогового значения после успешной валидации
     */
    private interface FieldValidator<T> {
        T validate(String raw) throws ValidationException;
    }

    private final Scanner scanner;

    public ManualFillStrategy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public List<Movie> fill(int length) {
        List<Movie> movies = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            System.out.println("Фильм " + (i + 1) + " из " + length + ":");
            String title = readValidated("  Название: ",
                    raw -> {
                        MovieValidator.validateTitle(raw);
                        return raw.trim();
                    });
            int year = readValidated("  Год выпуска: ", MovieValidator::validateReleaseYear);
            String genre = readValidated("  Жанр: ",
                    raw -> {
                        MovieValidator.validateGenre(raw);
                        return raw.trim();
                    });

            try {
                movies.add(Movie.builder()
                        .title(title)
                        .releaseYear(year)
                        .genre(genre)
                        .build());
            } catch (ValidationException e) {
                // Поля уже провалидированы по отдельности, сюда практически не попадём,
                // но на всякий случай не роняем программу и пропускаем элемент.
                System.out.println("  Не удалось создать фильм: " + e.getMessage());
                i--;
            }
        }
        return movies;
    }

    /**
     * Запрашивает у пользователя строку и валидирует её переданным валидатором,
     * повторяя запрос до успешного результата.
     */
    private <T> T readValidated(String prompt, FieldValidator<T> validator) {
        while (true) {
            System.out.print(prompt);
            String raw = scanner.nextLine();
            try {
                return validator.validate(raw);
            } catch (ValidationException e) {
                System.out.println("  Ошибка: " + e.getMessage() + ". Попробуйте снова.");
            }
        }
    }
}
