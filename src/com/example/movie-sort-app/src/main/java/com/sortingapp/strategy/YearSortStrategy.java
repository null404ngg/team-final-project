package com.sortingapp.strategy;

import com.sortingapp.model.Movie;

/**
 * Сравнивает фильмы по году выпуска.
 */
public class YearSortStrategy implements SortStrategy {

    @Override
    public int compare(Movie first, Movie second) {
        return Integer.compare(first.getReleaseYear(), second.getReleaseYear());
    }

    @Override
    public String getName() {
        return "по году выпуска";
    }
}
