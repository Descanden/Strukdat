# Import modul tkinter untuk GUI dan heapq untuk struktur data heap
import tkinter as tk
import heapq

# Fungsi heuristic untuk menghitung jarak antara dua titik
def heuristic(a, b):
    return abs(a[0] - b[0]) + abs(a[1] - b[1])

# Fungsi GBFS untuk mencari jalur dari start ke goal
def greedy_best_first_search(map, start, goal):
    dot = [(0, 1), (1, 0), (0, -1), (-1, 0)]  # Titik yang mungkin (atas, kanan, bawah, kiri)
    close_set = set()  # Set untuk menyimpan titik yang sudah diperiksa
    came_from = {}     # Dictionary untuk menyimpan jalur
    oheap = []         # Heap untuk menyimpan titik dengan prioritas
    
    # Masukkan titik awal ke heap dengan prioritas 0
    heapq.heappush(oheap, (0, start))
    
    # Loop sampai heap tidak kosong
    while oheap:
        current = heapq.heappop(oheap)[1]  # Ambil titik dengan prioritas terkecil dari heap
        
        # Jika titik saat ini adalah goal, maka buat jalur dari titik awal ke tujuan
        if current == goal:
            data = []
            while current in came_from:
                data.append(current)
                current = came_from[current]
            return data
        
        close_set.add(current)  # Tandai titik saat ini sebagai sudah diperiksa
        
        # Periksa semua tetangga dari titik saat ini
        for i, j in dot:
            neighbor = current[0] + i, current[1] + j  # Hitung koordinat tetangga
            
            # Pastikan tetangga berada di dalam peta
            if 0 <= neighbor[0] < len(map) and 0 <= neighbor[1] < len(map[0]):
                # Pastikan tetangga bukan dinding ('#')
                if map[neighbor[0]][neighbor[1]] != '#':
                    # Jika tetangga belum diperiksa, tambahkan ke heap
                    if neighbor not in close_set and neighbor not in [i[1] for i in oheap]:
                        came_from[neighbor] = current
                        priority = heuristic(neighbor, goal)
                        heapq.heappush(oheap, (priority, neighbor))
                
    return False  # Jika jalur tidak ditemukan, kembalikan False

# Kelas untuk aplikasi pencarian jalur
class PathFindingApp:
    def __init__(self, root, map):
        self.root = root
        self.map = map
        self.cell_size = 50
        self.canvas = tk.Canvas(root, width=len(map[0])*self.cell_size, height=len(map)*self.cell_size)
        self.canvas.pack()
        
        start = (1, 1)  # Titik Start
        goal = (9, 8)   # Titik Goal
        
        # Cari jalur menggunakan GBFS
        path = greedy_best_first_search(self.map, start, goal)
        
        # Gambar peta dan jalur
        self.draw_map()
        if path:
            self.draw_path(path)
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
        map_copy = [row[:] for row in self.map]
        for step in path:
            if map_copy[step[0]][step[1]] not in ('S', 'G'):
                map_copy[step[0]][step[1]] = '-'
        
        for row in map_copy:
            print(' '.join(row))

# Fungsi utama
if __name__ == "__main__":
    # Definisikan peta sebagai list of lists, dengan '#' untuk dinding, '.', 'S', 'G' untuk start, dan goal
    map = [
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

    root = tk.Tk()            # Buat instance Tkinter
    app = PathFindingApp(root, map)  # Buat instance aplikasi PathFindingApp
    root.mainloop()           # Mulai loop utama Tkinter
