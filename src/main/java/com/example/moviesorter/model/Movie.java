package com.example.moviesorter.model;

import java.util.Objects;

/**
 * Неизменяемый класс "Фильм" с тремя полями: название, год
 * выхода и продолжительность в минутах.
 */
public final class Movie {

    private final String title;
    private final int releaseYear;
    private final int durationMinutes;

    private Movie(Builder builder) {
        this.title = builder.title;
        this.releaseYear = builder.releaseYear;
        this.durationMinutes = builder.durationMinutes;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Movie)) {
            return false;
        }
        Movie movie = (Movie) other;
        return releaseYear == movie.releaseYear
                && durationMinutes == movie.durationMinutes
                && Objects.equals(title, movie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, releaseYear, durationMinutes);
    }

    @Override
    public String toString() {
        return "Movie{title='" + title + "', releaseYear=" + releaseYear
                + ", durationMinutes=" + durationMinutes + "}";
    }

    /**
     * Строитель (Builder) для {@link Movie}. Все три поля обязательны:
     * метод {@link #build()} выбросит {@link IllegalStateException}, если
     * хотя бы одно из них не было задано.
     */
    public static final class Builder {

        private String title;
        private Integer releaseYear;
        private Integer durationMinutes;

        private Builder() {
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder releaseYear(int releaseYear) {
            this.releaseYear = releaseYear;
            return this;
        }

        public Builder durationMinutes(int durationMinutes) {
            this.durationMinutes = durationMinutes;
            return this;
        }

        public Movie build() {
            if (title == null || releaseYear == null || durationMinutes == null) {
                throw new IllegalStateException(
                        "Movie requires title, releaseYear and durationMinutes to be set");
            }
            return new Movie(this);
        }
    }
}
