package Grafkom.TB;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class BresenhamLineDrawing extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private List<Point> points = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();

    private class Line {
        Point p0, p1;

        Line(Point p0, Point p1) {
            this.p0 = p0;
            this.p1 = p1;
        }
    }

    public BresenhamLineDrawing() {
        setTitle("Bresenham Line Drawing");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Canvas canvas = new Canvas();
        canvas.setBackground(Color.BLACK);

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                points.add(e.getPoint());
                if (points.size() == 2) {
                    lines.add(new Line(points.get(0), points.get(1)));
                    points.clear();
                    canvas.repaint();
                }
            }
        });

        getContentPane().add(canvas);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (Line line : lines) {
            drawLine(g, line.p0.x, line.p0.y, line.p1.x, line.p1.y, Color.WHITE);
        }
    }

    private void drawLine(Graphics g, int x0, int y0, int x1, int y1, Color color) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            g.setColor(color);
            g.drawLine(x0, y0, x0, y0);
            if (x0 == x1 && y0 == y1) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BresenhamLineDrawing();
            }
        });
    }
}
