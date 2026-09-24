package com.sortingapp.strategy;

import com.sortingapp.model.Movie;
import java.util.Comparator;

/**
 * Стратегия сравнения двух объектов {@link Movie} по конкретному полю.
 * <p>
 * Каждая реализация инкапсулирует один критерий сортировки (паттерн Strategy),
 * что позволяет добавлять новые критерии, не изменяя код сортировщика.
 */
public interface SortStrategy extends Comparator<Movie> {

    /**
     * @return человекочитаемое название критерия сортировки для отображения в меню
     */
    String getName();
}
