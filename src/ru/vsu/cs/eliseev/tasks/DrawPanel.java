package ru.vsu.cs.eliseev.tasks;

import ru.vsu.cs.eliseev.tasks.affine.Transformation;
import ru.vsu.cs.eliseev.tasks.drawers.*;
import ru.vsu.cs.eliseev.tasks.figure.IFigure;
import ru.vsu.cs.eliseev.tasks.figure.Rhomb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {
    private ScreenConverter converter;
    private Graphic graphic;
    private Point lastP;
    private List<IFigure> figures = new ArrayList<>();
    private final double GRID_SIZE = 15;
    private final int DEFAULT_FONT_SIZE = 11;
    private Font font = null;

    public DrawPanel() {

        converter = new ScreenConverter(800, 600, -GRID_SIZE, GRID_SIZE, 2 * GRID_SIZE, 2 * GRID_SIZE);
        graphic = new Graphic(converter);
        List<RealPoint> pointsForRhomb = new ArrayList<>();
        pointsForRhomb.add(new RealPoint(2, 0));
        pointsForRhomb.add(new RealPoint(0, 3));
        pointsForRhomb.add(new RealPoint(2, 6));
        pointsForRhomb.add(new RealPoint(4, 3));
        IFigure rhomb = new Rhomb(pointsForRhomb);
        figures.add(rhomb);


        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (lastP != null){
                    Point curP = e.getPoint();
                    ScreenPoint delta = new ScreenPoint(-curP.x + lastP.x, -curP.y + lastP.y);
                    RealPoint deltaR = converter.s2r(delta);
                    converter.moveCorner(deltaR);
                    lastP = curP;
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                lastP = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                lastP = null;
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        this.addMouseWheelListener(e -> {
            int count = e.getWheelRotation();
            double base = count < 0 ? 0.95 : 1.05;
            double kf = 1;
            for (int i = Math.abs(count); i > 0; i--) {
                kf *= base;
            }
            converter.changeScale(kf);
            repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        converter.setsHeight(getHeight());
        converter.setsWidth(getWidth());

        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D biG = bi.createGraphics();
        biG.setColor(Color.WHITE);
        biG.fillRect(0, 0, getWidth(), getHeight());

        //LineDrawer ld = new GraphicsLineDrawer(biG);
        //LineDrawer ld = new DDALineDrawer(new GraphicsPixelDrawer(biG));
        LineDrawer ld = new BresenhamLineDrawer(new GraphicsPixelDrawer(biG));

        biG.setColor(Color.BLACK);
        graphic.draw(ld, biG);
        for (IFigure figure : figures) {
            figure.drawFigure(figure.getPoints(), converter, ld, Color.BLACK);
        }
        g2d.drawImage(bi, 0, 0, null);
        biG.dispose();
    }

    private static void drawLine(LineDrawer ld, ScreenConverter sc, Line l) {
        ScreenPoint p1 = sc.r2s(l.getP1());
        ScreenPoint p2 = sc.r2s(l.getP2());
        ld.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY(), Color.black);
    }

    public void transformFigures(Transformation tr) {
        for (IFigure figure : figures) {
            List<RealPoint> newPoints = new ArrayList<>();
            for (RealPoint p : figure.getPoints()) {
                newPoints.add(tr.pointConversion(p));
            }
            figure.setPoints(newPoints);
        }
        repaint();
    }

    public void initFigure() {
        figures.clear();
        List<RealPoint> pointsForRhomb = new ArrayList<>();
        pointsForRhomb.add(new RealPoint(2, 0));
        pointsForRhomb.add(new RealPoint(0, 3));
        pointsForRhomb.add(new RealPoint(2, 6));
        pointsForRhomb.add(new RealPoint(4, 3));
        IFigure rhomb = new Rhomb(pointsForRhomb);
        figures.add(rhomb);
        repaint();
    }

    private Font getFont(int size) {
        if (font == null || font.getSize() != size) {
            font = new Font("Comic Sans MS", Font.BOLD, size);
        }
        return font;
    }
}
