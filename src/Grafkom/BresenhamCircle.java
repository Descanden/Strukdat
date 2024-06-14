package Grafkom;

import java.awt.*;
import javax.swing.*;

public class BresenhamCircle extends JPanel {

    private int xc, yc, r;

    // Konstruktor untuk menerima titik pusat dan jari-jari lingkaran
    public BresenhamCircle(int xc, int yc, int r) {
        this.xc = xc;
        this.yc = yc;
        this.r = r;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Memanggil metode untuk menggambar lingkaran menggunakan algoritma Bresenham
        drawCircle(g, xc, yc, r);
    }

    // Metode untuk menggambar lingkaran menggunakan algoritma Bresenham
    private void drawCircle(Graphics g, int xc, int yc, int r) {
        int x = 0, y = r;
        int p = 3 - 2 * r; // Inisialisasi parameter keputusan
        drawSymmetricPoints(g, xc, yc, x, y); // Menggambar titik simetris
        while (y >= x) {
            x++;
            if (p > 0) {
                y--;  // Mengurangi y jika parameter keputusan positif
                p = p + 4 * (x - y) + 10; // Memperbarui parameter keputusan
            } else {
                p = p + 4 * x + 6; // Memperbarui parameter keputusan
            }
            drawSymmetricPoints(g, xc, yc, x, y); // Menggambar titik simetris
        }
    }

    // Metode untuk menggambar titik-titik simetris pada lingkaran
    private void drawSymmetricPoints(Graphics g, int xc, int yc, int x, int y) {
        g.drawRect(xc + x, yc + y, 1, 1);
        g.drawRect(xc - x, yc + y, 1, 1);
        g.drawRect(xc + x, yc - y, 1, 1);
        g.drawRect(xc - x, yc - y, 1, 1);
        g.drawRect(xc + y, yc + x, 1, 1);
        g.drawRect(xc - y, yc + x, 1, 1);
        g.drawRect(xc + y, yc - x, 1, 1);
        g.drawRect(xc - y, yc - x, 1, 1);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        // Membuat panel dengan titik pusat (200, 200) dan jari-jari 100
        BresenhamCircle panel = new BresenhamCircle(200, 200, 100);
        frame.add(panel);             // Menambahkan panel ke frame
        frame.setSize(400, 400);      // Mengatur ukuran frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Mengatur operasi default ketika frame ditutup
        frame.setVisible(true);       // Menampilkan frame
    }
}
