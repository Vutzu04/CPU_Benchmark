package org.example;

import java.util.ArrayList;
import java.util.List;

public abstract class SortBenchmark implements Benchmark {
    protected int threadCount;

    public SortBenchmark(int threadCount) {
        this.threadCount = threadCount;
    }

    public List<Long> run() {
        List<Long> threadTimes = new ArrayList<>();

        // Create and start threads
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                long start = System.currentTimeMillis();
                sort();
                long end = System.currentTimeMillis();
                synchronized (threadTimes) {
                    threadTimes.add(end - start);
                }
            });
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return threadTimes;
    }

    // Abstract method for the specific sorting implementation
    protected abstract void sort();
}
