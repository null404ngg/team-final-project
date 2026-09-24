package com.example.moviesorter.input;

import com.example.moviesorter.model.Movie;
import java.util.Random;

/**
 * Реализация {@link DataSource}, которая придумывает фильмы случайным образом.
 */
public class RandomDataSource implements DataSource {

    // Название собирается из двух слов: первое из FIRST_WORDS, второе из SECOND_WORDS.
    // Второе слово в родительном падеже, поэтому подходит к любому первому:
    // "Тень дракона", "Путь звёзд".
    private static final String[] FIRST_WORDS = {
        "Тайна", "Легенда", "Возвращение", "Тень", "Хроники",
        "Путь", "Сердце", "Последний день", "Проклятие", "Дети"
    };
    private static final String[] SECOND_WORDS = {
        "океана", "дракона", "города", "времени", "звёзд",
        "пустыни", "короля", "леса", "империи", "призрака"
    };

    // Генератор случайных чисел. Создаём один раз и используем много раз.
    private final Random random = new Random();

    @Override
    public Movie[] load(int size) {
        Movie[] movies = new Movie[size];

        for (int i = 0; i < size; i++) {
            // random.nextInt(n) возвращает случайное число от 0 до n-1.
            String title = FIRST_WORDS[random.nextInt(FIRST_WORDS.length)]
                    + " " + SECOND_WORDS[random.nextInt(SECOND_WORDS.length)];
            int releaseYear = 1950 + random.nextInt(76);     // от 1950 до 2025
            int durationMinutes = 60 + random.nextInt(141);  // от 60 до 200 минут

            movies[i] = Movie.builder()
                    .title(title)
                    .releaseYear(releaseYear)
                    .durationMinutes(durationMinutes)
                    .build();
        }
        return movies;
    }
}
