package com.example.moviesorter;

import com.example.moviesorter.input.DataSource;
import com.example.moviesorter.input.ManualDataSource;
import com.example.moviesorter.model.Movie;
import com.example.moviesorter.ui.ConsoleIO;
import com.example.moviesorter.validation.MovieValidator;

/**
 * Временная точка входа в программу (класс с методом {@code main}, с
 * которого начинается выполнение).
 */
public class Main {

    public static void main(String[] args) {
        // ConsoleIO отвечает за весь ввод/вывод в консоли.
        ConsoleIO consoleIO = new ConsoleIO();
        // Проверяет корректность введённых данных.
        MovieValidator validator = new MovieValidator();

        DataSource dataSource = new ManualDataSource(consoleIO, validator);

        int size = consoleIO.readInt("Сколько фильмов ввести? ", 1, 100);
        Movie[] movies = dataSource.load(size);

        consoleIO.printLine("");
        consoleIO.printLine("Введённые фильмы:");
        for (Movie movie : movies) {
            consoleIO.printLine(movie.toString());
        }
    }
}
