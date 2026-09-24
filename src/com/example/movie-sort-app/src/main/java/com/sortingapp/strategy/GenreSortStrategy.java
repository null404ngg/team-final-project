package com.sortingapp.strategy;

import com.sortingapp.model.Movie;

/**
 * Сравнивает фильмы по жанру в алфавитном порядке (без учёта регистра).
 */
public class GenreSortStrategy implements SortStrategy {

    @Override
    public int compare(Movie first, Movie second) {
        return first.getGenre().compareToIgnoreCase(second.getGenre());
    }

    @Override
    public String getName() {
        return "по жанру";
    }
}
