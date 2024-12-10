package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainBenchmark {

    public static void main(String[] args) {
        int startThreads = 1;
        int maxThreads = 2;
        int increment = 1;

        if (args.length > 0) {
            try {
                maxThreads = Integer.parseInt(args[0]);
                System.out.println("Received maxThreads: " + maxThreads);
            } catch (NumberFormatException e) {
                System.err.println("Invalid number provided for maxThreads. Using default maxThreads = " + maxThreads);
            }
        } else {
            System.out.println("No maxThreads provided. Using default maxThreads = " + maxThreads);
        }

        try (BenchmarkResultLogger logger = new BenchmarkResultLogger("benchmark_results.csv")) {
            for (int threadCount = startThreads; threadCount <= maxThreads; threadCount += increment) {
                runBenchmarks(threadCount, logger);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        runPythonScript();
    }

    private static void runBenchmarks(int threadCount, BenchmarkResultLogger logger) throws IOException {
        executeBenchmark("QuickSort", new QuickSortBenchmark(threadCount), threadCount, logger);
        executeBenchmark("MergeSort", new MergeSortBenchmark(threadCount), threadCount, logger);
        executeBenchmark("BubbleSort", new BubbleSortBenchmark(threadCount), threadCount, logger);
        executeBenchmark("Memory", new MemoryBenchmark(threadCount), threadCount, logger);
    }

    private static void executeBenchmark(String testType, Benchmark benchmark, int threadCount, BenchmarkResultLogger logger) throws IOException {
        System.out.println("Starting " + testType + " benchmark with " + threadCount + " threads.");
        List<Long> globalTimes = new ArrayList<>();

        for (int i = 0; i < 3; i++) { // Run each benchmark 3 times
            long globalStart = System.currentTimeMillis();
            List<Long> threadTimes = benchmark.run();
            long globalEnd = System.currentTimeMillis();

            globalTimes.add(globalEnd - globalStart);
            if (i == 2) { // On the last run, log the average
                long averageGlobalTime = globalTimes.stream().mapToLong(Long::longValue).sum() / 3;
                logger.log(testType, threadCount, averageGlobalTime, threadTimes);
                System.out.println(testType + " benchmark completed in " + averageGlobalTime + " ms (average).");

                int score = calculateScore(averageGlobalTime, threadCount);
                System.out.println(testType + " Score: " + score);
            }
        }
    }

    private static int calculateScore(long averageTime, int threadCount) {
        // Simplistic scoring logic: lower times and higher thread counts yield higher scores
        return Math.max(1, Math.min(100, (int) (100 - (averageTime / threadCount * threadCount) / 10)));
    }

    private static void runPythonScript() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python3", "visualize_benchmark.py");
            processBuilder.inheritIO();
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Python script executed successfully.");
            } else {
                System.err.println("Python script execution failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error running Python script: " + e.getMessage());
            e.printStackTrace();
        }
    }
}



































//package org.example;
//
//import java.io.IOException;
//import java.util.List;
//
//public class MainBenchmark {
//
//    public static void main(String[] args) {
//        int startThreads = 1;
//        int maxThreads = 2;
//        int increment = 1;
//
//        if (args.length > 0) {
//            try {
//                maxThreads = Integer.parseInt(args[0]);
//                System.out.println("Received maxThreads: " + maxThreads);
//            } catch (NumberFormatException e) {
//                System.err.println("Invalid number provided for maxThreads. Using default maxThreads = " + maxThreads);
//            }
//        } else {
//            System.out.println("No maxThreads provided. Using default maxThreads = " + maxThreads);
//        }
//
//        try (BenchmarkResultLogger logger = new BenchmarkResultLogger("benchmark_results.csv")) {
//            for (int threadCount = startThreads; threadCount <= maxThreads; threadCount += increment) {
//                runBenchmarks(threadCount, logger);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        runPythonScript();
//    }
//
//    private static void runBenchmarks(int threadCount, BenchmarkResultLogger logger) throws IOException {
//        executeBenchmark("QuickSort", new QuickSortBenchmark(threadCount), threadCount, logger);
//        executeBenchmark("MergeSort", new MergeSortBenchmark(threadCount), threadCount, logger);
//        executeBenchmark("BubbleSort", new BubbleSortBenchmark(threadCount), threadCount, logger);
//        executeBenchmark("Memory", new MemoryBenchmark(threadCount), threadCount, logger);
//    }
//
//    private static void executeBenchmark(String testType, Benchmark benchmark, int threadCount, BenchmarkResultLogger logger) throws IOException {
//        System.out.println("Starting " + testType + " benchmark with " + threadCount + " threads.");
//        long globalStart = System.currentTimeMillis();
//        List<Long> threadTimes = benchmark.run();
//        long globalEnd = System.currentTimeMillis();
//
//        long globalExecutionTime = globalEnd - globalStart;
//        logger.log(testType, threadCount, globalExecutionTime, threadTimes);
//        System.out.println(testType + " benchmark completed in " + globalExecutionTime + " ms.");
//    }
//
//    private static void runPythonScript() {
//        try {
//            ProcessBuilder processBuilder = new ProcessBuilder("python3", "visualize_benchmark.py");
//            processBuilder.inheritIO();
//            Process process = processBuilder.start();
//            int exitCode = process.waitFor();
//            if (exitCode == 0) {
//                System.out.println("Python script executed successfully.");
//            } else {
//                System.err.println("Python script execution failed with exit code: " + exitCode);
//            }
//        } catch (IOException | InterruptedException e) {
//            System.err.println("Error running Python script: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//}
//
//
////
////package org.example;
////import java.io.IOException;
////import java.util.List;
////
////public class MainBenchmark {
////
////    public static void main(String[] args) {
////        int startThreads = 1;
////        int maxThreads = 10;
////        int increment = 1;
////
////        try (BenchmarkResultLogger logger = new BenchmarkResultLogger("benchmark_results.csv")) {
////            for (int threadCount = startThreads; threadCount <= maxThreads; threadCount += increment) {
////                runBenchmarks(threadCount, logger);
////            }
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////
////        runPythonScript();
////    }
////
////    private static void runBenchmarks(int threadCount, BenchmarkResultLogger logger) throws IOException {
////        executeBenchmark("QuickSort", new QuickSortBenchmark(threadCount), threadCount, logger);
////        executeBenchmark("MergeSort", new MergeSortBenchmark(threadCount), threadCount, logger);
////        executeBenchmark("BubbleSort", new BubbleSortBenchmark(threadCount), threadCount, logger);
////        executeBenchmark("Memory", new MemoryBenchmark(threadCount), threadCount, logger);
////    }
////
////    private static void executeBenchmark(String testType, Benchmark benchmark, int threadCount, BenchmarkResultLogger logger) throws IOException {
////        long globalStart = System.currentTimeMillis();
////        List<Long> threadTimes = benchmark.run();
////        long globalEnd = System.currentTimeMillis();
////
////        long globalExecutionTime = globalEnd - globalStart;
////        logger.log(testType, threadCount, globalExecutionTime, threadTimes);
////    }
////
////    private static void runPythonScript() {
////        try {
////            ProcessBuilder processBuilder = new ProcessBuilder("python3", "visualize_benchmark.py");
////            processBuilder.inheritIO();
////            Process process = processBuilder.start();
////            int exitCode = process.waitFor();
////            if (exitCode == 0) {
////                System.out.println("Python script executed successfully.");
////            } else {
////                System.err.println("Python script execution failed with exit code: " + exitCode);
////            }
////        } catch (IOException | InterruptedException e) {
////            System.err.println("Error running Python script: " + e.getMessage());
////            e.printStackTrace();  // Print the full stack trace for more information
////        }
////    }
////
////}
