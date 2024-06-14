package Grafkom;

import java.awt.*;
import javax.swing.*;

public class MidpointLine extends JPanel {

    private int x0, y0, x1, y1;

    // Konstruktor untuk menerima titik awal dan titik akhir garis
    public MidpointLine(int x0, int y0, int x1, int y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Memanggil metode untuk menggambar garis menggunakan algoritma Midpoint
        drawLine(g, x0, y0, x1, y1);
    }

    // Metode untuk menggambar garis menggunakan algoritma Midpoint
    private void drawLine(Graphics g, int x0, int y0, int x1, int y1) {
        int dx = x1 - x0; // Menghitung perubahan dalam x
        int dy = y1 - y0; // Menghitung perubahan dalam y
        int d = 2 * dy - dx; // Inisialisasi parameter keputusan
        int incrE = 2 * dy; // Increment untuk pilihan East
        int incrNE = 2 * (dy - dx); // Increment untuk pilihan North-East

        int x = x0, y = y0;
        g.drawRect(x, y, 1, 1); // Menggambar piksel pertama

        while (x < x1) {
            if (d <= 0) {
                d += incrE; // Memperbarui parameter keputusan untuk East
                x++; // Menggerakkan x ke arah kanan
            } else {
                d += incrNE; // Memperbarui parameter keputusan untuk North-East
                x++; // Menggerakkan x ke arah kanan
                y++; // Menggerakkan y ke atas
            }
            g.drawRect(x, y, 1, 1); // Menggambar piksel berikutnya
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        // Membuat panel dengan titik awal (20, 30) dan titik akhir (180, 120)
        MidpointLine panel = new MidpointLine(20, 30, 180, 120);
        frame.add(panel); // Menambahkan panel ke frame
        frame.setSize(400, 400); // Mengatur ukuran frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Mengatur operasi default ketika frame ditutup
        frame.setVisible(true); // Menampilkan frame
    }
}

