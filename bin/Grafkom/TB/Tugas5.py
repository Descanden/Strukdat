import tkinter as tk
from tkinter import messagebox
import matplotlib.pyplot as plt
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

# Global variables to store coordinates
x1, y1, x2, y2 = None, None, None, None
clicked = 0

# Function to perform Cohen-Sutherland line clipping algorithm
def clipping(x1, y1, x2, y2, window):
    def compute_code(x, y):
        code = 0
        if x < window['x_min']:
            code |= 1
        elif x > window['x_max']:
            code |= 2
        if y < window['y_min']:
            code |= 4
        elif y > window['y_max']:
            code |= 8
        return code

    code1 = compute_code(x1, y1)
    code2 = compute_code(x2, y2)

    while True:
        if not (code1 | code2):
            return [(x1, y1), (x2, y2)]
        elif code1 & code2:
            return None
        else:
            x, y = None, None
            code_outside = code1 if code1 else code2

            if code_outside & 8:
                x = x1 + (x2 - x1) * (window['y_max'] - y1) / (y2 - y1)
                y = window['y_max']
            elif code_outside & 4:
                x = x1 + (x2 - x1) * (window['y_min'] - y1) / (y2 - y1)
                y = window['y_min']
            elif code_outside & 2:
                y = y1 + (y2 - y1) * (window['x_max'] - x1) / (x2 - x1)
                x = window['x_max']
            elif code_outside & 1:
                y = y1 + (y2 - y1) * (window['x_min'] - x1) / (x2 - x1)
                x = window['x_min']

            if code_outside == code1:
                x1, y1 = x, y
                code1 = compute_code(x1, y1)
            else:
                x2, y2 = x, y
                code2 = compute_code(x2, y2)

# Function to handle mouse click event on plot
def on_click(event):
    global x1, y1, x2, y2, clicked
    if event.xdata is not None and event.ydata is not None:
        if clicked == 0:
            x1, y1 = event.xdata, event.ydata
            clicked += 1
        elif clicked == 1:
            x2, y2 = event.xdata, event.ydata
            clicked += 1
            perform_clipping()

# Function to handle button click event to perform clipping and display results
def perform_clipping():
    global x1, y1, x2, y2, clicked

    # Get values from entry fields
    x1_str = entry_x1.get()
    y1_str = entry_y1.get()
    x2_str = entry_x2.get()
    y2_str = entry_y2.get()

    # Validate input
    if not x1_str or not y1_str or not x2_str or not y2_str:
        messagebox.showerror("Error", "Masukkan koordinat garis dengan benar")
        return

    # Convert to integers
    try:
        x1 = int(x1_str)
        y1 = int(y1_str)
        x2 = int(x2_str)
        y2 = int(y2_str)
    except ValueError as e:
        messagebox.showerror("Error", f"Masukkan koordinat yang valid: {e}")
        return

    window = {
        'x_min': int(entry_x_min.get()),
        'y_min': int(entry_y_min.get()),
        'x_max': int(entry_x_max.get()),
        'y_max': int(entry_y_max.get())
    }

    clipped_line = clipping(x1, y1, x2, y2, window)

    if clipped_line:
        display_text(clipped_line)
        display_graph(clipped_line, window)
    else:
        messagebox.showinfo("Info", "Garis tidak terlihat setelah clipping")
    
    # Reset coordinates and clicked counter
    x1, y1, x2, y2 = None, None, None, None
    clicked = 0

# Function to display clipped line in text format
def display_text(clipped_line):
    text_output.config(state=tk.NORMAL)
    text_output.delete(1.0, tk.END)
    text_output.insert(tk.END, f"Garis setelah clipping: {clipped_line}")
    text_output.config(state=tk.DISABLED)

# Function to display clipped line and window in graphical format
def display_graph(clipped_line, window):
    fig, ax = plt.subplots()
    (x1, y1), (x2, y2) = clipped_line
    ax.plot([x1, x2], [y1, y2], marker='o', label="Garis setelah Clipping")

    # Adding points outside the line
    ax.scatter([window['x_min'], window['x_max']], [window['y_min'], window['y_max']], color='red', marker='X', label="Titik Potong Diluar Garis")

    rect = plt.Rectangle((window['x_min'], window['y_min']),
                         window['x_max'] - window['x_min'],
                         window['y_max'] - window['y_min'],
                         linewidth=1, edgecolor='r', facecolor='none')
    ax.add_patch(rect)

    plt.xlim(window['x_min'] - 10, window['x_max'] + 10)
    plt.ylim(window['y_min'] - 10, window['y_max'] + 10)
    plt.title("Hasil Clipping")
    plt.xlabel("X")
    plt.ylabel("Y")
    plt.grid(True)
    plt.legend()

    # Embedding matplotlib figure in Tkinter window
    canvas = FigureCanvasTkAgg(fig, master=frame_graph)
    canvas.draw()
    canvas.get_tk_widget().pack()

# Creating the main Tkinter window
root = tk.Tk()
root.title("Clipping Lines")

# Frame for input fields
frame_input = tk.Frame(root)
frame_input.pack(padx=10, pady=10)

# Labels and entry fields for line coordinates
tk.Label(frame_input, text="Koordinat x1 titik awal garis:").grid(row=0, column=0, padx=5, pady=5)
entry_x1 = tk.Entry(frame_input)
entry_x1.grid(row=0, column=1, padx=5, pady=5)

tk.Label(frame_input, text="Koordinat y1 titik awal garis:").grid(row=1, column=0, padx=5, pady=5)
entry_y1 = tk.Entry(frame_input)
entry_y1.grid(row=1, column=1, padx=5, pady=5)

tk.Label(frame_input, text="Koordinat x2 titik akhir garis:").grid(row=2, column=0, padx=5, pady=5)
entry_x2 = tk.Entry(frame_input)
entry_x2.grid(row=2, column=1, padx=5, pady=5)

tk.Label(frame_input, text="Koordinat y2 titik akhir garis:").grid(row=3, column=0, padx=5, pady=5)
entry_y2 = tk.Entry(frame_input)
entry_y2.grid(row=3, column=1, padx=5, pady=5)

# Labels and entry fields for window coordinates
tk.Label(frame_input, text="x_min window:").grid(row=4, column=0, padx=5, pady=5)
entry_x_min = tk.Entry(frame_input)
entry_x_min.grid(row=4, column=1, padx=5, pady=5)

tk.Label(frame_input, text="y_min window:").grid(row=5, column=0, padx=5, pady=5)
entry_y_min = tk.Entry(frame_input)
entry_y_min.grid(row=5, column=1, padx=5, pady=5)

tk.Label(frame_input, text="x_max window:").grid(row=6, column=0, padx=5, pady=5)
entry_x_max = tk.Entry(frame_input)
entry_x_max.grid(row=6, column=1, padx=5, pady=5)

tk.Label(frame_input, text="y_max window:").grid(row=7, column=0, padx=5, pady=5)
entry_y_max = tk.Entry(frame_input)
entry_y_max.grid(row=7, column=1, padx=5, pady=5)

# Frame for displaying graphical output
frame_graph = tk.Frame(root)
frame_graph.pack(padx=10, pady=10)

# Button to perform clipping
btn_clip = tk.Button(root, text="Clip Garis", command=perform_clipping)
btn_clip.pack(pady=10)

# Text widget to display text output
text_output = tk.Text(root, height=4, width=50)
text_output.pack(padx=10, pady=10)
text_output.config(state=tk.DISABLED)  # <-- Perlu diperbaiki di sini


# Frame for displaying graphical output
frame_graph = tk.Frame(root)
frame_graph.pack(padx=10, pady=10)

# Button to perform clipping
btn_clip = tk.Button(root, text="Clip Garis", command=perform_clipping)
btn_clip.pack(pady=10)

# Text widget to display text output
text_output = tk.Text(root, height=4, width=50)
text_output.pack(padx=10, pady=10)
text_output.config(state=tk.DISABLED)

# Embedding matplotlib figure in Tkinter window
fig, ax = plt.subplots()
ax.set_xlim(0, 100)
ax.set_ylim(0, 100)
ax.set_title("Klik untuk memilih koordinat garis")
ax.set_xlabel("X")
ax.set_ylabel("Y")
ax.grid(True)
fig.canvas.mpl_connect('button_press_event', on_click)
canvas = FigureCanvasTkAgg(fig, master=frame_graph)
canvas.draw()
canvas.get_tk_widget().pack()

root.mainloop()

