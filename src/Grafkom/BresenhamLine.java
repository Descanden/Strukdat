package Grafkom;

import java.awt.*;
import javax.swing.*;

public class BresenhamLine extends JPanel {

    private int x0, y0, x1, y1;

    // Konstruktor untuk menerima titik awal dan titik akhir garis
    public BresenhamLine(int x0, int y0, int x1, int y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Memanggil metode untuk menggambar garis menggunakan algoritma Bresenham
        drawLine(g, x0, y0, x1, y1);
    }

    // Metode untuk menggambar garis menggunakan algoritma Bresenham
    private void drawLine(Graphics g, int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0); // Menghitung perubahan absolut dalam x
        int dy = Math.abs(y1 - y0); // Menghitung perubahan absolut dalam y
        int sx = x0 < x1 ? 1 : -1;  // Menentukan arah langkah untuk x
        int sy = y0 < y1 ? 1 : -1;  // Menentukan arah langkah untuk y
        int err = dx - dy;          // Inisialisasi parameter kesalahan

        while (true) {
            g.drawRect(x0, y0, 1, 1);  // Menggambar piksel pada koordinat saat ini
            if (x0 == x1 && y0 == y1) break; // Jika koordinat saat ini adalah titik akhir, keluar dari loop
            int e2 = 2 * err; // Menghitung dua kali parameter kesalahan
            if (e2 > -dy) {
                err -= dy;    // Mengurangi kesalahan dengan dy
                x0 += sx;     // Menggerakkan x ke arah langkah
            }
            if (e2 < dx) {
                err += dx;    // Menambah kesalahan dengan dx
                y0 += sy;     // Menggerakkan y ke arah langkah
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        // Membuat panel dengan titik awal (20, 30) dan titik akhir (180, 120)
        BresenhamLine panel = new BresenhamLine(20, 30, 180, 120);
        frame.add(panel);             // Menambahkan panel ke frame
        frame.setSize(400, 400);      // Mengatur ukuran frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Mengatur operasi default ketika frame ditutup
        frame.setVisible(true);       // Menampilkan frame
    }
}
