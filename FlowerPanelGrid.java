package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class FlowerPanelGrid extends JPanel {
    private final Queue<Point> flowerQueue = new LinkedList<>();
    private final Random rand = new Random();
    private Timer animationTimer;
    private boolean fallingFlowers = true;
    private boolean checkerboardBackground = true;

    private int flowerCount = 4;
    private int gridSpacing = 100;
    private Color[] flowerColors;

    public FlowerPanelGrid() {
        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                flowerQueue.add(new Point(e.getX(), e.getY()));
                repaint();
            }
        });

        animationTimer = new Timer(30, e -> animateFallingFlowers());
        animationTimer.start();
    }

    public void setFlowerCount(int count) {
        this.flowerCount = count;
        generateGridFlowers();
        repaint();
    }

    public void toggleFallingFlowers(boolean enabled) {
        fallingFlowers = enabled;
        if (fallingFlowers) {
            animationTimer.start();
        } else {
            animationTimer.stop();
        }
    }

    public void toggleBackground() {
        checkerboardBackground = !checkerboardBackground;
        repaint();
    }

    private void generateGridFlowers() {
        flowerQueue.clear();
        flowerColors = new Color[flowerCount];

        int cols = getWidth() / gridSpacing;
        int rows = getHeight() / gridSpacing;
        int placedFlowers = 0;

        for (int i = 0; i < rows && placedFlowers < flowerCount; i++) {
            for (int j = 0; j < cols && placedFlowers < flowerCount; j++) {
                int x = j * gridSpacing + rand.nextInt(20) - 10;
                int y = i * gridSpacing + rand.nextInt(20) - 10;
                flowerQueue.add(new Point(x, y));
                flowerColors[placedFlowers++] = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            }
        }
    }

    private void animateFallingFlowers() {
        if (fallingFlowers) {
            for (Point p : flowerQueue) {
                p.y += 2;
                if (p.y > getHeight()) {
                    p.y = 0;
                }
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (checkerboardBackground) {
            drawCheckerboardBackground(g2d);
        } else {
            drawDiagonalGrid(g2d);
        }

        drawGrid(g2d);

        int index = 0;
        for (Point p : flowerQueue) {
            drawFlower(g2d, p.x, p.y, 40, 5, flowerColors[index++]);
        }

        g2d.dispose();
    }

    private void drawCheckerboardBackground(Graphics2D g2d) {
        int tileSize = 50;
        Color color1 = Color.LIGHT_GRAY;
        Color color2 = Color.WHITE;

        for (int x = 0; x < getWidth(); x += tileSize) {
            for (int y = 0; y < getHeight(); y += tileSize) {
                g2d.setColor((x / tileSize + y / tileSize) % 2 == 0 ? color1 : color2);
                g2d.fillRect(x, y, tileSize, tileSize);
            }
        }
    }

    private void drawDiagonalGrid(Graphics2D g2d) {
        g2d.setColor(new Color(200, 200, 200));
        for (int i = -getHeight(); i < getWidth(); i += 50) {
            g2d.drawLine(i, 0, i + getHeight(), getHeight());
        }
    }

    private void drawGrid(Graphics2D g2d) {
        g2d.setColor(new Color(200, 200, 200));
        for (int x = 0; x < getWidth(); x += gridSpacing) {
            g2d.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y += gridSpacing) {
            g2d.drawLine(0, y, getWidth(), y);
        }
    }

    private void drawFlower(Graphics2D g2d, int x, int y, int radius, int petals, Color color) {
        g2d.setColor(color);
        for (int i = 0; i < petals; i++) {
            double angle = i * (2 * Math.PI / petals);
            int petalX = x + (int) (Math.cos(angle) * radius * 1.5);
            int petalY = y + (int) (Math.sin(angle) * radius * 1.5);
            g2d.fillOval(petalX - radius / 2, petalY - radius / 2, radius, radius);
        }
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(x - radius / 2, y - radius / 2, radius, radius);
    }
}
