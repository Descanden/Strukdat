import tkinter as tk

# Definisikan warna RGB
WHITE = "#FFFFFF"
LIGHT_GRAY = "#AAAAAA"
BLACK = "#000000"

# Inisialisasi tkinter
root = tk.Tk()
root.title("Bresenham Line Drawing")

# Ukuran window
width, height = 800, 600

# Buat canvas untuk menggambar
canvas = tk.Canvas(root, width=width, height=height, bg=WHITE)
canvas.pack()

# Inisialisasi entry fields untuk koordinat
entry_x0 = tk.Entry(root, width=8)
entry_y0 = tk.Entry(root, width=8)
entry_x1 = tk.Entry(root, width=8)
entry_y1 = tk.Entry(root, width=8)

# Fungsi untuk menggambar garis menggunakan algoritma Bresenham
def draw_line(x0, y0, x1, y1):
    dx = abs(x1 - x0)
    dy = abs(y1 - y0)
    sx = 1 if x0 < x1 else -1
    sy = 1 if y0 < y1 else -1
    err = dx - dy

    while True:
        plot_pixel(x0, y0)
        if x0 == x1 and y0 == y1:
            break
        e2 = 2 * err
        if e2 > -dy:
            err -= dy
            x0 += sx
        if e2 < dx:
            err += dx
            y0 += sy

# Fungsi untuk plot pixel pada grid
def plot_pixel(x, y):
    canvas.create_rectangle(x, y, x + 1, y + 1, outline=BLACK, fill=BLACK)

# Fungsi untuk input koordinat dari keyboard (2 point)
def input_from_keyboard():
    try:
        x0 = int(entry_x0.get())
        y0 = int(entry_y0.get())
        x1 = int(entry_x1.get())
        y1 = int(entry_y1.get())
        draw_line(x0, y0, x1, y1)
        output_text((x0, y0), (x1, y1))
    except ValueError:
        print("Input tidak valid. Masukkan bilangan bulat untuk koordinat.")

# Fungsi untuk input dari mouse (4 point)
def input_from_mouse(event):
    global points
    try:
        points.append((event.x, event.y))
        if len(points) == 4:
            p0 = points[0]
            p1 = points[2]
            draw_line(p0[0], p0[1], p1[0], p1[1])
            output_text(p0, p1)  # Memanggil fungsi output teks setelah menggambar garis
            points = []  # Reset points setelah menggambar garis
    except ValueError:
        print("Input tidak valid. Masukkan bilangan bulat untuk koordinat.")

# Fungsi untuk menampilkan teks koordinat pada canvas
def output_text(p0, p1):
    # Menyusun teks koordinat
    text = f"({p0[0]}, {p0[1]}) - ({p1[0]}, {p1[1]})"
    # Menampilkan teks di antara titik p0 dan p1
    label_x = (p0[0] + p1[0]) / 2
    label_y = (p0[1] + p1[1]) / 2
    canvas.create_text(label_x, label_y, text=text, fill=BLACK)

# Fungsi untuk menggambar grid background
def draw_coordinate_grid():
    # Menggambar grid dengan jarak 50 pixel antar garis
    for i in range(0, width, 50):
        canvas.create_line(i, 0, i, height, fill=LIGHT_GRAY)
        if i != 0:
            canvas.create_text(i + 10, 10, anchor=tk.NW, text=str(i), fill=LIGHT_GRAY)
    
    for j in range(0, height, 50):
        canvas.create_line(0, j, width, j, fill=LIGHT_GRAY)
        if j != 0:
            canvas.create_text(10, j + 10, anchor=tk.NW, text=str(j), fill=LIGHT_GRAY)

# Fungsi utama untuk menjalankan aplikasi
def main():
    global points
    points = []  # Inisialisasi points untuk menyimpan koordinat mouse

    label = tk.Label(root, text="Klik mouse untuk menggambar garis (empat titik untuk dua ujung garis)")
    label.pack()

    draw_coordinate_grid()  # Gambar grid saat aplikasi dimulai

    canvas.bind("<Button-1>", input_from_mouse)  # Binding mouse click ke fungsi input_from_mouse

    # Label dan Entry fields untuk koordinat
    tk.Label(root, text="Koordinat x titik awal garis:").pack()
    entry_x0.pack()

    tk.Label(root, text="Koordinat y titik awal garis:").pack()
    entry_y0.pack()

    tk.Label(root, text="Koordinat x titik akhir garis:").pack()
    entry_x1.pack()

    tk.Label(root, text="Koordinat y titik akhir garis:").pack()
    entry_y1.pack()

    # Button untuk menggambar garis dari keyboard
    button_draw_keyboard = tk.Button(root, text="Gambar Garis (Keyboard)", command=input_from_keyboard)
    button_draw_keyboard.pack()

    button_exit = tk.Button(root, text="Exit", command=root.quit)
    button_exit.pack()

    root.mainloop()

if __name__ == '__main__':
    main()
