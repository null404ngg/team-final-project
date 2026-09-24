package com.example.sorting.strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeSortStrategy<T> implements SortStrategy<T> {

    @Override
    public void sort(List<T> list, Comparator<T> comparator) {
        if (list.size() <2) {
            return;
        }

        int mid = list.size() / 2;
        List<T> left = list.subList(0, mid);
        List<T> right = list.subList(mid, list.size());

        sort(left, comparator);
        sort(right, comparator);
        List<T> merged = merge(left, right, comparator);

        for (int i = 0; i < merged.size(); i++) {
            list.set(i, merged.get(i));
        }

    }

    private List<T> merge(List<T> left, List<T> right, Comparator<T> comparator) {
        List<T> result = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < left.size() && j < right.size()) {
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }
        while (i < left.size()) {
            result.add(left.get(i));
            i++;
        }
        while (j < right.size()) {
            result.add(right.get(j));
            j++;
        }
        return result;
    }
}
