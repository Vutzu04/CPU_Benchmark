package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BenchmarkResultLogger implements AutoCloseable {
    private FileWriter csvWriter;

    public BenchmarkResultLogger(String fileName) throws IOException {
        csvWriter = new FileWriter(fileName);
        // Write the header of the CSV
        csvWriter.append("Test Type,Thread Count,Global Execution Time");

        // Add headers for each thread execution time. We use a maximum of 150 threads
        for (int i = 1; i <= 150; i++) {
            csvWriter.append(",Thread " + i + " Time");
        }

        csvWriter.append("\n");
    }

    public void log(String testType, int threadCount, long globalExecutionTime, List<Long> threadTimes) throws IOException {
        csvWriter.append(testType)
                .append(",")
                .append(Integer.toString(threadCount))
                .append(",")
                .append(Long.toString(globalExecutionTime));

        // Log each thread's execution time in a separate column
        for (int i = 0; i < threadCount; i++) {
            if (i < threadTimes.size()) {
                csvWriter.append(",").append(Long.toString(threadTimes.get(i)));
            } else {
                csvWriter.append(","); // In case there are fewer times than expected
            }
        }

        csvWriter.append("\n");
    }

    public void close() throws IOException {
        csvWriter.flush();
        csvWriter.close();
    }
}
