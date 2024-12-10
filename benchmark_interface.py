import tkinter as tk
from tkinter import messagebox, scrolledtext
from tkinter import ttk
import subprocess
import re

def start_benchmark():
    try:
        thread_count = int(thread_input.get())
        if thread_count < 1:
            raise ValueError("Number of threads must be at least 1.")

        # Run the Java benchmark program
        result = subprocess.run(
            [
                "java",
                "-cp",
                "/Users/bilanviorel/IdeaProjects/PSSC/target/classes",
                "org.example.MainBenchmark",
                str(thread_count)
            ],
            text=True,
            capture_output=True
        )

        if result.returncode == 0:
            messagebox.showinfo("Success", "Benchmark completed successfully!")
        else:
            messagebox.showerror("Error", f"Benchmark failed!\n{result.stderr}")

        # Display Java program output in the text area
        output_area.delete(1.0, tk.END)
        output_area.insert(tk.END, f"\n{result.stdout}\n")
        output_area.insert(tk.END, f"\n{result.stderr}\n")

        # Calculate and update the processor score
        processor_score = calculate_processor_score(result.stdout, thread_count)
        update_score(processor_score)

    except ValueError:
        messagebox.showerror("Invalid Input", "Please enter a valid number of threads.")
    except Exception as e:
        messagebox.showerror("Error", f"An unexpected error occurred: {str(e)}")

def calculate_processor_score(output, thread_count):
    # Extract all times in milliseconds from the Java output
    times = re.findall(r'\b\d+ ms\b', output)
    times = [int(t.split()[0]) for t in times]  # Convert to integers

    if not times:
        return "N/A"  # Return "N/A" if no times are found

    # Calculate the total time and derive the score
    total_time = sum(times)
    score = total_time / thread_count
    return round(score, 2)  # Return score rounded to 2 decimals

def update_score(score):
    score_label.config(text=f"Processor Score: {score}")

# Create the main application window
app = tk.Tk()
app.title("Benchmark Interface")
app.geometry("700x500")

# Use ttk for modern widgets
style = ttk.Style()
style.theme_use('clam')

# Create a main frame
mainframe = ttk.Frame(app, padding="10 10 10 10")
mainframe.grid(row=0, column=0, sticky=(tk.N, tk.W, tk.E, tk.S))
app.columnconfigure(0, weight=1)
app.rowconfigure(0, weight=1)

# Create input frame
input_frame = ttk.Frame(mainframe, padding="5 5 5 5")
input_frame.grid(row=0, column=0, sticky=(tk.W, tk.E))

# Add a label
label = ttk.Label(input_frame, text="Enter the number of threads:", font=("Arial", 12))
label.grid(row=0, column=0, padx=5, pady=5, sticky='W')

# Add an input field
thread_input = ttk.Entry(input_frame, font=("Arial", 12), width=10)
thread_input.grid(row=0, column=1, padx=5, pady=5, sticky='W')

# Add a start button
start_button = ttk.Button(input_frame, text="Start Benchmark", command=start_benchmark)
start_button.grid(row=0, column=2, padx=5, pady=5, sticky='W')

# Add a label for processor score in the top-right corner
score_label = tk.Label(app, text="Processor Score: N/A", font=("Arial", 12), bg="lightgray")
score_label.place(x=550, y=10)  # Adjust position for top-right corner

# Add a separator
separator = ttk.Separator(mainframe, orient='horizontal')
separator.grid(row=1, column=0, sticky=(tk.W, tk.E), pady=10)

# Add a scrolled text area to display output
output_area = scrolledtext.ScrolledText(mainframe, wrap=tk.WORD, font=("Consolas", 10))
output_area.grid(row=2, column=0, sticky=(tk.N, tk.S, tk.E, tk.W))

# Configure column and row weights for responsiveness
mainframe.columnconfigure(0, weight=1)
mainframe.rowconfigure(2, weight=1)

# Run the application
app.mainloop()

# import tkinter as tk
# from tkinter import messagebox, scrolledtext
# from tkinter import ttk
# import subprocess
#
# def start_benchmark():
#     try:
#         thread_count = int(thread_input.get())
#         if thread_count < 1:
#             raise ValueError("Number of threads must be at least 1.")
#
#         # Run the Java benchmark program
#         result = subprocess.run(
#             [
#                 "java",
#                 "-cp",
#                 "/Users/bilanviorel/IdeaProjects/PSSC/target/classes",
#                 "org.example.MainBenchmark",
#                 str(thread_count)
#             ],
#             text=True,
#             capture_output=True
#         )
#
#         if result.returncode == 0:
#             messagebox.showinfo("Success", "Benchmark completed successfully!")
#         else:
#             messagebox.showerror("Error", f"Benchmark failed!\n{result.stderr}")
#
#         # Display Java program output in the text area
#         output_area.delete(1.0, tk.END)
#         output_area.insert(tk.END, f"\n{result.stdout}\n")
#         output_area.insert(tk.END, f"\n{result.stderr}\n")
#
#     except ValueError:
#         messagebox.showerror("Invalid Input", "Please enter a valid number of threads.")
#     except Exception as e:
#         messagebox.showerror("Error", f"An unexpected error occurred: {str(e)}")
#
# # Create the main application window
# app = tk.Tk()
# app.title("Benchmark Interface")
# app.geometry("700x500")
#
# # Use ttk for modern widgets
# style = ttk.Style()
# style.theme_use('clam')
#
# # Create a main frame
# mainframe = ttk.Frame(app, padding="10 10 10 10")
# mainframe.grid(row=0, column=0, sticky=(tk.N, tk.W, tk.E, tk.S))
# app.columnconfigure(0, weight=1)
# app.rowconfigure(0, weight=1)
#
# # Create input frame
# input_frame = ttk.Frame(mainframe, padding="5 5 5 5")
# input_frame.grid(row=0, column=0, sticky=(tk.W, tk.E))
#
# # Add a label
# label = ttk.Label(input_frame, text="Enter the number of threads:", font=("Arial", 12))
# label.grid(row=0, column=0, padx=5, pady=5, sticky='W')
#
# # Add an input field
# thread_input = ttk.Entry(input_frame, font=("Arial", 12), width=10)
# thread_input.grid(row=0, column=1, padx=5, pady=5, sticky='W')
#
# # Add a start button
# start_button = ttk.Button(input_frame, text="Start Benchmark", command=start_benchmark)
# start_button.grid(row=0, column=2, padx=5, pady=5, sticky='W')
#
# # Add a separator
# separator = ttk.Separator(mainframe, orient='horizontal')
# separator.grid(row=1, column=0, sticky=(tk.W, tk.E), pady=10)
#
# # Add a scrolled text area to display output
# output_area = scrolledtext.ScrolledText(mainframe, wrap=tk.WORD, font=("Consolas", 10))
# output_area.grid(row=2, column=0, sticky=(tk.N, tk.S, tk.E, tk.W))
#
# # Configure column and row weights for responsiveness
# mainframe.columnconfigure(0, weight=1)
# mainframe.rowconfigure(2, weight=1)
#
# # Run the application
# app.mainloop()
