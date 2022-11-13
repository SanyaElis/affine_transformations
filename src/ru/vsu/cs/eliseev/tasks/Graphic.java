package ru.vsu.cs.eliseev.tasks;

import ru.vsu.cs.eliseev.tasks.drawers.LineDrawer;

import java.awt.*;
import java.text.DecimalFormat;

public class Graphic {
    private final ScreenConverter sc;
    private final DecimalFormat decimalFormat = new DecimalFormat("#.#");
    private final int DEFAULT_FONT_SIZE = 11;
    private Font font = null;


    public Graphic(ScreenConverter sc) {
        this.sc = sc;
    }

    public void draw(LineDrawer ld, Graphics2D g2d) {
        drawCoordinateAndGrid(ld, g2d);
    }

    private void drawCoordinateAndGrid(LineDrawer ld, Graphics2D g2d) {
        int ax = 96;//кф для насечки
        g2d.setFont(getFont(DEFAULT_FONT_SIZE));
        // Draws a grid to the right of the coordinate line
        for (double x = 0; x < sc.getX() + sc.getWidth(); x += HH(0, sc.getWidth())) {
            drawXGrid(ld, g2d, ax, x);
        }

        // Draws a grid to the left of the coordinate line
        for (double x = 0; x > sc.getX() - sc.getWidth(); x -= HH(0, sc.getWidth())) {
            drawXGrid(ld, g2d, ax, x);

        }

        // Draws a grid to the up of the coordinate line
        for (double y = 0; y < sc.getY() + sc.getHeight(); y += HH(0, sc.getHeight())) {
            drawYGrid(ld, g2d, ax, y);
        }

        // Draws a grid to the down of the coordinate line
        for (double y = 0; y > sc.getY() - sc.getHeight(); y -= HH(0, sc.getHeight())) {
            drawYGrid(ld, g2d, ax, y);
        }

        drawOneLine(ld, new Line(new RealPoint(0, sc.getY() - sc.getHeight()), new RealPoint(0, sc.getY())), Color.DARK_GRAY);

        drawOneLine(ld, new Line(new RealPoint(sc.getX() + sc.getWidth(), 0), new RealPoint(sc.getX(), 0)), Color.DARK_GRAY);

    }

    private void drawYGrid(LineDrawer ld, Graphics2D g2d, int ax, double y) {
        drawOneLine(ld, new Line(new RealPoint(sc.getX() + sc.getWidth(), y), new RealPoint(sc.getX(), y)), Color.LIGHT_GRAY);
        drawOneLine(ld, new Line(new RealPoint(-sc.getWidth() / ax, y), new RealPoint(sc.getWidth() / ax, y)), Color.BLACK);
        ScreenPoint point = sc.r2s(new RealPoint(-sc.getWidth() / ax, y));
        g2d.drawString(decimalFormat.format(y), point.getX() + 20, point.getY() - 8);
    }

    private void drawXGrid(LineDrawer ld, Graphics2D g2d, int ax, double x) {
        drawOneLine(ld, new Line(new RealPoint(x, sc.getY() - sc.getHeight()), new RealPoint(x, sc.getY())), Color.LIGHT_GRAY);
        drawOneLine(ld, new Line(new RealPoint(x, -sc.getHeight() / ax), new RealPoint(x, sc.getHeight() / ax)), Color.BLACK);
        ScreenPoint point = sc.r2s(new RealPoint(x, -sc.getHeight() / ax));
        g2d.drawString(decimalFormat.format(x), point.getX() - 8, point.getY() + 20);
    }


    private double HH(double a1, double a2) {
        double result = 1;

        while (Math.abs(a2 - a1) / result < 1.0)
            result /= 10.0;
        while (Math.abs(a2 - a1) / result >= 20.0)
            result *= 10.0;

        if (Math.abs(a2 - a1) / result < 2.0)
            result /= 5.0;
        if (Math.abs(a2 - a1) / result < 4.0)
            result /= 2.0;

        return result;
    }

    private void drawOneLine(LineDrawer ld, Line l, Color c) {
        ScreenPoint p1 = sc.r2s(l.getP1());
        ScreenPoint p2 = sc.r2s(l.getP2());
        ld.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY(), c);
    }

    private Font getFont(int size) {
        if (font == null || font.getSize() != size) {
            font = new Font("Comic Sans MS", Font.BOLD, size);
        }
        return font;
    }
}
