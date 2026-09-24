package com.sortingapp.strategy;

import com.sortingapp.model.Movie;

/**
 * Сравнивает фильмы по названию в алфавитном порядке (без учёта регистра).
 */
public class TitleSortStrategy implements SortStrategy {

    @Override
    public int compare(Movie first, Movie second) {
        return first.getTitle().compareToIgnoreCase(second.getTitle());
    }

    @Override
    public String getName() {
        return "по названию";
    }
}
