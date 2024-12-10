
package org.example;

import java.util.Arrays;

public class QuickSortBenchmark extends SortBenchmark {

    public QuickSortBenchmark(int threadCount) {
        super(threadCount);
    }

    @Override
    protected void sort() {
        int[] array = generateRandomArray();
        quickSort(array, 0, array.length - 1);
    }

    private int[] generateRandomArray() {
        int size = 10000; // Set size for benchmarking purposes
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * 10000);
        }
        return array;
    }

    private void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }

    private int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }
}
