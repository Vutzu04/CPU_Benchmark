package org.example;

import java.util.ArrayList;
import java.util.List;

public class MemoryBenchmark implements Benchmark {
    private int threadCount;

    public MemoryBenchmark(int threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public List<Long> run() {
        List<Long> threadTimes = new ArrayList<>();

        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                long start = System.currentTimeMillis();
                performMemoryTask();
                long end = System.currentTimeMillis();
                synchronized (threadTimes) {
                    threadTimes.add(end - start);
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return threadTimes;
    }

    private void performMemoryTask() {
        List<int[]> memoryList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            memoryList.add(new int[10000]);
        }
        memoryList.clear();
    }
}
