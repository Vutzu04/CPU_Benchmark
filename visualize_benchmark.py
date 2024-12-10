
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

def load_data(file_name):
    try:
        # Load CSV and handle bad lines properly
        return pd.read_csv(file_name, on_bad_lines='warn')
    except Exception as e:
        print(f"Error loading data from {file_name}: {e}")
        return None

def plot_benchmark(data, test_type):
    subset = data[data['Test Type'] == test_type]
    x = subset['Thread Count']
    y = subset['Global Execution Time']

    # Plot data points
    plt.plot(x, y, marker='o', linestyle='-', label=f'{test_type} Execution Time')

    # Calculate and plot max value
    max_value = y.max()
    plt.axhline(y=max_value, color='r', linestyle='--', label=f'Max Execution Time: {max_value} ms')

    # Calculate and plot trend line
    z = np.polyfit(x, y, 1)
    p = np.poly1d(z)
    plt.plot(x, p(x), "b--", label="Trend Line")

    # Chart settings
    plt.xlabel('Thread Count')
    plt.ylabel('Execution Time (ms)')
    plt.title(f'{test_type} Performance Benchmark')
    plt.legend()
    plt.grid(True)

def main():
    # Load the data
    data = load_data('benchmark_results.csv')

    if data is not None:
        # Create a figure for plotting
        plt.figure(figsize=(10, 8))

        # Plot for each test type
        for i, test_type in enumerate(['QuickSort', 'MergeSort', 'BubbleSort', 'Memory'], start=1):
            plt.subplot(2, 2, i)
            plot_benchmark(data, test_type)

        plt.tight_layout()
        plt.show()
    else:
        print("No data to plot.")

if __name__ == '__main__':
    main()
