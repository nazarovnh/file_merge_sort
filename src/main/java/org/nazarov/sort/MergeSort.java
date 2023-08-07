package org.nazarov.sort;

import java.util.ArrayList;
import java.util.List;

import org.nazarov.utils.Pair;

import static java.lang.Integer.valueOf;

public class MergeSort<T extends Comparable<T>> {

    private List<Pair<Integer, Integer>> integerArray;
    private List<Pair<String, Integer>> stringArray;
    private static final int CHECK_ARRAY = -1;

    public T getValue(List<Pair<T, Integer>> arr, int index) {
        return arr.get(index).getFirst();
    }

    public Integer getNumberOfArray(List<Pair<T, Integer>> arr, int index) {
        return arr.get(index).getSecond();
    }

    private void mergeSort(int left, int mid, int right, List<Pair<T, Integer>> arr) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<Pair<T, Integer>> leftArray = new ArrayList<Pair<T, Integer>>();

        for (int i = 0; i < n1; i++) {
            leftArray.add(arr.get(left + i));
        }

        List<Pair<T, Integer>> rightArray = new ArrayList<Pair<T, Integer>>();
        for (int i = 0; i < n2; i++) {
            rightArray.add(arr.get(mid + i + 1));
        }

        int i = 0;
        int j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (getValue(leftArray, i).compareTo(getValue(rightArray, j)) > 0) {
                arr.set(k, leftArray.get(i));
                i++;
            } else {
                arr.set(k, rightArray.get(j));
                j++;
            }
            k++;
        }
        while (i < n1) {
            arr.set(k, leftArray.get(i));
            i++;
            k++;
        }
        while (j < n2) {
            arr.set(k, rightArray.get(j));
            j++;
            k++;
        }
    }

    public void merge(int left, int right, List<Pair<T, Integer>> arr) {
        if (getNumberOfArray(arr, left) == getNumberOfArray(arr, right)) {
            for (int i = left; i < right; i++) {
                arr.set(i, new Pair<>(getValue(arr, i), CHECK_ARRAY));
            }
            return;
        }
        if (left >= right) {
            return;
        }
        int mid = left + (left + right) / 2;
        merge(left, mid, arr);
        merge(mid + 1, right, arr);

        mergeSort(left, mid, right, arr);
    }
}
