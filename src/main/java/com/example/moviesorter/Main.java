package com.example.moviesorter;

import com.example.moviesorter.input.DataSource;
import com.example.moviesorter.input.FileDataSource;
import com.example.moviesorter.input.ManualDataSource;
import com.example.moviesorter.model.Movie;
import com.example.moviesorter.ui.ConsoleIO;
import com.example.moviesorter.validation.MovieValidator;
import java.io.File;
import java.nio.file.Path;

/**
 * Временная точка входа в программу (класс с методом {@code main}, с
 * которого начинается выполнение).
 */
public class Main {
    private static final String DEFAULT_FILE = "data/movies.txt";

    public static void main(String[] args) {
        // ConsoleIO отвечает за весь ввод/вывод в консоли.
        ConsoleIO consoleIO = new ConsoleIO();
        // Проверяет корректность введённых данных.
        MovieValidator validator = new MovieValidator();

        consoleIO.printLine("Способ заполнения массива:");
        consoleIO.printLine("  1 — вручную");
        consoleIO.printLine("  2 — из файла");
        int choice = consoleIO.readInt("Ваш выбор: ", 1, 2);

        DataSource dataSource;
        if (choice == 1) {
            dataSource = new ManualDataSource(consoleIO, validator);
        } else {
            dataSource = new FileDataSource(readFilePath(consoleIO), consoleIO, validator);
        }

        int size = consoleIO.readInt("Сколько фильмов загрузить? ", 1, 100);
        Movie[] movies = dataSource.load(size);

        consoleIO.printLine("");
        consoleIO.printLine("Загруженные фильмы (" + movies.length + "):");
        for (Movie movie : movies) {
            consoleIO.printLine(movie.toString());
        }
    }

    /**
     * Спрашивает путь к файлу, пока пользователь не укажет существующий файл.
     * Если просто нажать Enter, берётся файл по умолчанию.
     */
    private static Path readFilePath(ConsoleIO consoleIO) {
        while (true) {
            String input = consoleIO.readLine("Путь к файлу (Enter — " + DEFAULT_FILE + "): ").trim();
            if (input.isEmpty()) {
                input = DEFAULT_FILE;
            }

            File file = new File(input);
            if (file.isFile()) {
                return file.toPath();
            }
            consoleIO.printLine("Файл не найден: " + file.getAbsolutePath());
        }
    }
}
