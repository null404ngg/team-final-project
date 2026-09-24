package com.example.moviesorter.input;

import com.example.moviesorter.model.Movie;
import com.example.moviesorter.ui.ConsoleIO;
import com.example.moviesorter.validation.MovieValidator;
import com.example.moviesorter.validation.ValidationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация {@link DataSource}, которая считывает данные о фильмах
 * из текстового файла (UTF-8).
 * Пример - Название;Год выхода;Продолжительность (мин)
 * Пустые строки и строки, начинающиеся с {@code #}, пропускаются.
 * Некорректные строки тоже пропускаются с сообщением о номере строки
 * и причине ошибки.
 */
public class FileDataSource implements DataSource {

    private final Path path;
    private final ConsoleIO consoleIO;
    private final MovieValidator validator;

    public FileDataSource(Path path, ConsoleIO consoleIO, MovieValidator validator) {
        this.path = path;
        this.consoleIO = consoleIO;
        this.validator = validator;
    }

    @Override
    public Movie[] load(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размер массива должен быть положительным");
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            consoleIO.printLine("Не удалось прочитать файл: " + path);
            return new Movie[0];
        }

        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < lines.size() && movies.size() < size; i++) {
            // Убираем пробелы и невидимый символ BOM, который иногда
            // добавляет в начало файла Блокнот Windows.
            String line = lines.get(i).replace("﻿", "").trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }

            try {
                movies.add(parseLine(line));
            } catch (NumberFormatException e) {
                consoleIO.printLine("  Строка " + (i + 1) + " пропущена: год и продолжительность должны быть числами");
            } catch (ValidationException e) {
                consoleIO.printLine("  Строка " + (i + 1) + " пропущена: " + e.getMessage());
            }
        }

        if (movies.size() < size) {
            consoleIO.printLine("В файле нашлось только " + movies.size() + " корректных фильмов из " + size);
        }
        return movies.toArray(new Movie[0]);
    }

    /** Превращает строку вида "Матрица;1999;136" в объект {@link Movie}. */
    private Movie parseLine(String line) {
        String[] parts = line.split(";");
        if (parts.length != 3) {
            throw new ValidationException("должно быть 3 поля через ';'");
        }

        String title = parts[0].trim();
        int releaseYear = Integer.parseInt(parts[1].trim());
        int durationMinutes = Integer.parseInt(parts[2].trim());

        validator.validateTitle(title);
        validator.validateReleaseYear(releaseYear);
        validator.validateDurationMinutes(durationMinutes);

        return Movie.builder()
                .title(title)
                .releaseYear(releaseYear)
                .durationMinutes(durationMinutes)
                .build();
    }
}
