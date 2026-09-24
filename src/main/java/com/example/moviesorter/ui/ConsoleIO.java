package com.example.moviesorter.ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Единая точка доступа к вводу с консоли ({@code System.in}).
 */
public class ConsoleIO {

    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    private final PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    /**
     * Выводит {@code prompt} (подсказку/вопрос) и считывает одну строку
     * ввода.
     */
    public String readLine(String prompt) {
        out.print(prompt);
        try {
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            out.println("Ввод завершён. Работа программы прекращена.");
            System.exit(0);
            return null;
        }
    }

    /** Печатает строку через тот же поток вывода в UTF-8, что и подсказки (prompt). */
    public void printLine(String line) {
        out.println(line);
    }

    /**
     * Считывает целое число в диапазоне [min, max] включительно.
     * Иначе — запрос повторяется снова.
     */
    public int readInt(String prompt, int min, int max) {
        while (true) {
            String input = readLine(prompt).trim();
            try {
                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    out.println("Введите число от " + min + " до " + max);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                out.println("Введите целое число");
            }
        }
    }
}
