package com.sortingapp.fill;

import com.sortingapp.model.Movie;
import com.sortingapp.validation.ValidationException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Заполняет массив случайно сгенерированными, заведомо валидными фильмами.
 */
public class RandomFillStrategy implements FillStrategy {

    private static final String[] TITLE_WORDS = {
            "Тень", "Свет", "Звезда", "Дорога", "Легенда", "Империя", "Огонь", "Ветер", "Город", "Небо"
    };
    private static final String[] GENRES = {
            "Драма", "Комедия", "Боевик", "Триллер", "Фантастика", "Ужасы", "Мелодрама", "Приключения"
    };
    private static final int MIN_YEAR = 1970;

    private final Random random = new Random();

    @Override
    public List<Movie> fill(int length) {
        List<Movie> movies = new ArrayList<>(length);
        int maxYear = Year.now().getValue();
        for (int i = 0; i < length; i++) {
            String title = TITLE_WORDS[random.nextInt(TITLE_WORDS.length)] + " " + (i + 1);
            int year = MIN_YEAR + random.nextInt(maxYear - MIN_YEAR + 1);
            String genre = GENRES[random.nextInt(GENRES.length)];
            try {
                movies.add(Movie.builder()
                        .title(title)
                        .releaseYear(year)
                        .genre(genre)
                        .build());
            } catch (ValidationException e) {
                // Сгенерированные значения заведомо корректны; если валидация
                // всё же не прошла — пропускаем элемент, не прерывая заполнение.
                System.out.println("Пропущен случайно сгенерированный элемент: " + e.getMessage());
            }
        }
        return movies;
    }
}
