package com.example.moviesorter.input;

import com.example.moviesorter.model.Movie;

/**
 * Интерфейс для заполнения массива фильмов.
 */
public interface DataSource {

    /**
     * Возвращает массив уже проверенных (валидных) фильмов.
     * @param size сколько фильмов нужно получить, должно быть положительным числом
     * @return массив фильмов; источник данных может вернуть меньше, чем
     *         {@code size} (например, если в файле не хватило валидных строк),
     *         но никогда не больше и никогда не с {@code null}-элементами внутри
     */
    Movie[] load(int size);
}
