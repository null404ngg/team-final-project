package com.sortingapp.fill;

import com.sortingapp.model.Movie;
import com.sortingapp.validation.MovieValidator;
import com.sortingapp.validation.ValidationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Заполняет массив данными из текстового файла.
 * <p>
 * Ожидаемый формат строки: {@code название;год;жанр}. Строки, не прошедшие
 * валидацию (неверный формат, некорректные значения), пропускаются с
 * предупреждением в консоль — чтение файла при этом не прерывается.
 */
public class FileFillStrategy implements FillStrategy {

    private static final String FIELD_SEPARATOR = ";";
    private static final int EXPECTED_FIELD_COUNT = 3;

    private final Path filePath;

    public FileFillStrategy(String filePath) {
        this.filePath = Path.of(filePath);
    }

    @Override
    public List<Movie> fill(int length) throws IOException {
        List<Movie> movies = new ArrayList<>();

        if (!Files.exists(filePath)) {
            throw new IOException("Файл не найден: " + filePath);
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null && movies.size() < length) {
                lineNumber++;
                if (line.trim().isEmpty()) {
                    continue;
                }
                try {
                    movies.add(parseLine(line));
                } catch (ValidationException e) {
                    System.out.println("Строка " + lineNumber + " пропущена: " + e.getMessage());
                }
            }
        }

        if (movies.size() < length) {
            System.out.println("В файле оказалось валидных строк меньше, чем запрошено: "
                    + movies.size() + " из " + length + ".");
        }
        return movies;
    }

    /**
     * Разбирает и валидирует одну строку файла в объект {@link Movie}.
     */
    private Movie parseLine(String line) throws ValidationException {
        String[] parts = line.split(FIELD_SEPARATOR, -1);
        if (parts.length != EXPECTED_FIELD_COUNT) {
            throw new ValidationException("ожидалось " + EXPECTED_FIELD_COUNT
                    + " поля через '" + FIELD_SEPARATOR + "', получено " + parts.length);
        }

        MovieValidator.validateTitle(parts[0]);
        int year = MovieValidator.validateReleaseYear(parts[1]);
        MovieValidator.validateGenre(parts[2]);

        return Movie.builder()
                .title(parts[0].trim())
                .releaseYear(year)
                .genre(parts[2].trim())
                .build();
    }
}
