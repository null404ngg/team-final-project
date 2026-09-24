package com.sortingapp.fill;

import com.sortingapp.model.Movie;
import java.io.IOException;
import java.util.List;

/**
 * Стратегия заполнения исходного массива данных (паттерн Strategy).
 * Конкретные реализации определяют источник данных: случайная генерация,
 * ручной ввод с консоли или чтение из файла.
 */
public interface FillStrategy {

    /**
     * @param length желаемое количество элементов
     * @return список из {@code length} валидных объектов {@link Movie}
     *         (для файловой стратегии итоговый размер может быть меньше,
     *         если часть строк файла не прошла валидацию)
     * @throws IOException при ошибке чтения источника данных
     */
    List<Movie> fill(int length) throws IOException;
}
