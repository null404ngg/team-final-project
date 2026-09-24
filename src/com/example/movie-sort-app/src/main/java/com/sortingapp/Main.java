package com.sortingapp;

import com.sortingapp.fill.FileFillStrategy;
import com.sortingapp.fill.FillStrategy;
import com.sortingapp.fill.ManualFillStrategy;
import com.sortingapp.fill.RandomFillStrategy;
import com.sortingapp.model.Movie;
import com.sortingapp.strategy.GenreSortStrategy;
import com.sortingapp.strategy.MovieSorter;
import com.sortingapp.strategy.SortStrategy;
import com.sortingapp.strategy.TitleSortStrategy;
import com.sortingapp.strategy.YearSortStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Консольное приложение для заполнения и сортировки массива фильмов.
 * <p>
 * Работает в бесконечном цикле, единственный способ завершить работу —
 * выбрать соответствующий пункт меню. Любые ошибки ввода (в том числе при
 * чтении из файла) обрабатываются и не приводят к аварийному завершению.
 */
public final class Main {

    private final Scanner scanner = new Scanner(System.in);
    private final MovieSorter sorter = new MovieSorter();
    private List<Movie> movies = new ArrayList<>();

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        boolean running = true;
        System.out.println("=== Сортировка фильмов ===");
        while (running) {
            try {
                printMenu();
                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1" -> fillArray();
                    case "2" -> sortArray();
                    case "3" -> printArray();
                    case "0" -> running = false;
                    default -> System.out.println("Неизвестный пункт меню, попробуйте снова.");
                }
            } catch (RuntimeException e) {
                // Последний рубеж защиты: даже непредвиденная ошибка не должна
                // приводить к аварийному завершению программы.
                System.out.println("Произошла непредвиденная ошибка: " + e.getMessage()
                        + ". Работа программы продолжена.");
            }
        }
        System.out.println("Работа программы завершена.");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1 - Заполнить массив фильмов");
        System.out.println("2 - Отсортировать массив");
        System.out.println("3 - Показать массив");
        System.out.println("0 - Выход");
        System.out.print("Выберите пункт меню: ");
    }

    private void fillArray() {
        System.out.println();
        System.out.println("Способ заполнения:");
        System.out.println("1 - Случайно");
        System.out.println("2 - Вручную");
        System.out.println("3 - Из файла");
        System.out.print("Выберите способ: ");
        String way = scanner.nextLine().trim();

        int length = readLength();
        if (length < 0) {
            System.out.println("Заполнение отменено.");
            return;
        }

        FillStrategy strategy;
        switch (way) {
            case "1" -> strategy = new RandomFillStrategy();
            case "2" -> strategy = new ManualFillStrategy(scanner);
            case "3" -> {
                System.out.print("Введите путь к файлу: ");
                String path = scanner.nextLine().trim();
                strategy = new FileFillStrategy(path);
            }
            default -> {
                System.out.println("Неизвестный способ заполнения.");
                return;
            }
        }

        try {
            movies = strategy.fill(length);
            System.out.println("Массив заполнен, элементов: " + movies.size());
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    /**
     * Запрашивает у пользователя желаемую длину массива.
     *
     * @return длину массива (&gt;= 0) или -1, если пользователь ввёл "0" для отмены
     */
    private int readLength() {
        while (true) {
            System.out.print("Введите длину массива (0 - отмена): ");
            String raw = scanner.nextLine().trim();
            try {
                int length = Integer.parseInt(raw);
                if (length == 0) {
                    return -1;
                }
                if (length < 0) {
                    System.out.println("Длина не может быть отрицательной, попробуйте снова.");
                    continue;
                }
                return length;
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число.");
            }
        }
    }

    private void sortArray() {
        if (movies.isEmpty()) {
            System.out.println("Массив пуст, сначала заполните его (пункт 1).");
            return;
        }

        System.out.println();
        System.out.println("Критерий сортировки:");
        System.out.println("1 - По названию");
        System.out.println("2 - По году выпуска");
        System.out.println("3 - По жанру");
        System.out.print("Выберите критерий: ");
        String criterion = scanner.nextLine().trim();

        SortStrategy strategy;
        switch (criterion) {
            case "1" -> strategy = new TitleSortStrategy();
            case "2" -> strategy = new YearSortStrategy();
            case "3" -> strategy = new GenreSortStrategy();
            default -> {
                System.out.println("Неизвестный критерий сортировки.");
                return;
            }
        }

        System.out.print("Порядок (1 - по возрастанию, 2 - по убыванию): ");
        boolean ascending = !"2".equals(scanner.nextLine().trim());

        movies = sorter.sort(movies, strategy, ascending);
        System.out.println("Массив отсортирован " + strategy.getName()
                + (ascending ? " (по возрастанию)." : " (по убыванию)."));
    }

    private void printArray() {
        if (movies.isEmpty()) {
            System.out.println("Массив пуст.");
            return;
        }
        System.out.println();
        System.out.println("Текущий массив (" + movies.size() + " элементов):");
        for (int i = 0; i < movies.size(); i++) {
            System.out.println((i + 1) + ") " + movies.get(i));
        }
    }
}
