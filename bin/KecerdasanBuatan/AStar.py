import tkinter as tk # Untuk membuat visualisasi gambar
import heapq

# Fungsi heuristic untuk menghitung jarak perkiraan dari titik A ke B
def heuristic(a, b):
    return abs(a[0] - b[0]) + abs(a[1] - b[1])

# Implementasi algoritma A* untuk mencari jalur dari start ke goal
def a_star_search(map, start, goal):
    neighbors = [(0, 1), (1, 0), (0, -1), (-1, 0)]  # Titik yang mungkin (atas, kanan, bawah, kiri)
    close_set = set()
    came_from = {}
    gscore = {start: 0}
    fscore = {start: heuristic(start, goal)}
    oheap = []
    
    heapq.heappush(oheap, (fscore[start], start))
    
    while oheap:
        current = heapq.heappop(oheap)[1]  # Mengambil titik dengan nilai cost terkecil dari heap
        # Jika titik saat ini adalah goal, maka membuat jalur dari titik awal ke tujuan
        if current == goal:
            data = []
            while current in came_from:
                data.append(current)
                current = came_from[current]
            return data[::-1]
        
        close_set.add(current)
        for i, j in neighbors:
            neighbor = current[0] + i, current[1] + j  # Menghitung koordinat titik
            tentative_g_score = gscore[current] + 1  # Menghitung cost titik
            
            # Memeriksa apakah titik berada di dalam peta
            if 0 <= neighbor[0] < len(map) and 0 <= neighbor[1] < len(map[0]):
                # Memeriksa apakah titik tidak berada di dinding ('#')
                if map[neighbor[0]][neighbor[1]] != '#':
                    # Memperbarui jalur jika jalur baru lebih baik atau titik belum diperiksa
                    if neighbor not in close_set or tentative_g_score < gscore.get(neighbor, 0):
                        came_from[neighbor] = current
                        gscore[neighbor] = tentative_g_score
                        fscore[neighbor] = tentative_g_score + heuristic(neighbor, goal)
                        heapq.heappush(oheap, (fscore[neighbor], neighbor))
                
    return False

# Kelas pencarian jalur
class PathFindingApp:
    def __init__(self, root, map):
        self.root = root
        self.map = map
        self.cell_size = 50
        self.canvas = tk.Canvas(root, width=len(map[0])*self.cell_size, height=len(map)*self.cell_size)
        self.canvas.pack()
        
        start = (1, 1)  # Titik Start
        goal = (8, 8)   # Titik Goal
        
        path = a_star_search(self.map, start, goal)  # Melakukan pencarian jalur
        self.draw_map()  # Menggambar peta
        if path:
            self.draw_path(path)  # Menggambar jalur jika ditemukan
            print(f"Shortest path length: {len(path)}")
            self.print_map_with_path(path)
    
    # Metode untuk menggambar peta
    def draw_map(self):
        for i, row in enumerate(self.map):
            for j, cell in enumerate(row):
                x0 = j * self.cell_size
                y0 = i * self.cell_size
                x1 = x0 + self.cell_size
                y1 = y0 + self.cell_size
                color = 'white'
                if cell == '#':
                    color = 'black'
                elif cell == 'S':
                    color = 'cyan'
                elif cell == 'G':
                    color = 'red'
                self.canvas.create_rectangle(x0, y0, x1, y1, fill=color, outline='gray')
    
    # Metode untuk menggambar jalur
    def draw_path(self, path):
        for step in path:
            x0 = step[1] * self.cell_size
            y0 = step[0] * self.cell_size
            x1 = x0 + self.cell_size
            y1 = y0 + self.cell_size
            self.canvas.create_rectangle(x0, y0, x1, y1, fill='steel blue', outline='gray')
    
    # Metode untuk mencetak peta dengan jalur
    def print_map_with_path(self, path):
        # Fungsi untuk menyalin peta dan dimodifikasi dengan menambahkan jalur
        map_copy = [row[:] for row in self.map]
        for step in path:
            if map_copy[step[0]][step[1]] not in ('S', 'G'):
                map_copy[step[0]][step[1]] = '-'
        
        for row in map_copy:
            print(' '.join(row))

if __name__ == "__main__":
    # Definisikan peta sebagai list of lists, dengan '#' untuk dinding, '.' untuk jalan, 'S' untuk start, dan 'G' untuk goal
    map_data = [
        ['#', '#', '#', '#', '#', '#', '#', '#', '#', '#'],
        ['#', 'S', '.', '.', '#', '.', '.', '.', '.', '#'],
        ['#', '#', '#', '.', '#', '.', '#', '#', '.', '#'],
        ['#', '.', '.', '.', '.', '.', '#', '.', '.', '#'],
        ['#', '.', '#', '#', '#', '#', '#', '.', '#', '#'],
        ['#', '.', '.', '.', '.', '#', '.', '.', '.', '#'],
        ['#', '.', '#', '#', '.', '#', '.', '#', '#', '#'],
        ['#', '.', '.', '.', '.', '#', '.', '.', '.', '#'],
        ['#', '.', '#', '#', '.', '.', '.', '#', '.', '#'],
        ['#', '#', '#', '#', '#', '#', '#', '#', 'G', '#']
    ]

    root = tk.Tk()
    app = PathFindingApp(root, map_data)
    root.mainloop()
