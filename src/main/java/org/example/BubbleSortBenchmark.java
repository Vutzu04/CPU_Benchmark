package org.example;

public class BubbleSortBenchmark extends SortBenchmark {

    public BubbleSortBenchmark(int threadCount) {
        super(threadCount);
    }

    @Override
    protected void sort() {
        int[] array = generateRandomArray();
        bubbleSort(array);
    }

    private int[] generateRandomArray() {
        int size = 10000;
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * 10000);
        }
        return array;
    }

    private void bubbleSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
}
