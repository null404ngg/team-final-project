package com.sortingapp.strategy;

import com.sortingapp.model.Movie;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Контекст паттерна Strategy: сортирует список фильмов, используя
 * переданную {@link SortStrategy} в качестве критерия сравнения.
 * <p>
 * Алгоритм сортировки реализован вручную (устойчивая сортировка слиянием,
 * O(n log n)) — намеренно без использования {@code Collections.sort()},
 * {@code List.sort()}, {@code Arrays.sort()} или иных готовых реализаций.
 */
public class MovieSorter {

    /**
     * Возвращает новый отсортированный список; исходный список не изменяется.
     *
     * @param movies    исходные данные
     * @param strategy  критерий сравнения (паттерн Strategy)
     * @param ascending {@code true} — по возрастанию, {@code false} — по убыванию
     */
    public List<Movie> sort(List<Movie> movies, SortStrategy strategy, boolean ascending) {
        if (movies == null || movies.isEmpty()) {
            return new ArrayList<>();
        }
        Comparator<Movie> comparator = ascending ? strategy : strategy.reversed();
        List<Movie> copy = new ArrayList<>(movies);
        mergeSort(copy, 0, copy.size() - 1, comparator);
        return copy;
    }

    /**
     * Рекурсивная сортировка слиянием диапазона [left; right] списка {@code list}.
     */
    private void mergeSort(List<Movie> list, int left, int right, Comparator<Movie> comparator) {
        if (left >= right) {
            return;
        }
        int middle = left + (right - left) / 2;
        mergeSort(list, left, middle, comparator);
        mergeSort(list, middle + 1, right, comparator);
        merge(list, left, middle, right, comparator);
    }

    /**
     * Сливает два уже отсортированных подряд идущих диапазона
     * [left; middle] и [middle + 1; right] в один отсортированный диапазон.
     */
    private void merge(List<Movie> list, int left, int middle, int right, Comparator<Movie> comparator) {
        List<Movie> leftPart = new ArrayList<>(list.subList(left, middle + 1));
        List<Movie> rightPart = new ArrayList<>(list.subList(middle + 1, right + 1));

        int i = 0;
        int j = 0;
        int k = left;

        while (i < leftPart.size() && j < rightPart.size()) {
            if (comparator.compare(leftPart.get(i), rightPart.get(j)) <= 0) {
                list.set(k, leftPart.get(i));
                i++;
            } else {
                list.set(k, rightPart.get(j));
                j++;
            }
            k++;
        }
        while (i < leftPart.size()) {
            list.set(k, leftPart.get(i));
            i++;
            k++;
        }
        while (j < rightPart.size()) {
            list.set(k, rightPart.get(j));
            j++;
            k++;
        }
    }
}
