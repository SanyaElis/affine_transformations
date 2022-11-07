package ru.vsu.cs.eliseev.tasks;

import ru.vsu.cs.eliseev.tasks.affine.Transformation;
import ru.vsu.cs.eliseev.tasks.drawers.*;
import ru.vsu.cs.eliseev.tasks.figure.IFigure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {
    private int currentX, currentY;
    private ScreenConverter converter;
    private Line ox, oy, current;
    private Point lastP;
    private List<Line> lines = new ArrayList<>();
    private List<IFigure> figures = new ArrayList<>();
    private final double GRID_SIZE = 15;
    private final double unitInterval = 1;

//    private static Line findLines(List<Line> lines, ScreenConverter sc, ScreenPoint sp, int eps){
//        Line answer = null;
//        for (Line l: lines) {
//           ScreenPoint p1 = sc.r2s(l.getP1()) ;
//           ScreenPoint p2 = sc.r2s(l.getP2()) ;
//           if ( (p1.getX() - eps) < (sp.getX() ) && (sp.getX() < p2.getX() + eps) && (p1.getX() + eps) > (sp.getX() ) && (sp.getX() < p2.getX() + eps)){
//               double ax = p1.getX() - p2.getX();
//               double ay = p1.getY() - p2.getY();
//
//
//            }
//        }
//        return answer;
//    }
    public DrawPanel() {

        converter = new ScreenConverter(800,600, -GRID_SIZE, GRID_SIZE, 2 * GRID_SIZE, 2 * GRID_SIZE);
        ox = new Line(new RealPoint(-GRID_SIZE ,0), new RealPoint(GRID_SIZE,0));
        oy = new Line(new RealPoint(0,-GRID_SIZE), new RealPoint(0, GRID_SIZE));

        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(lastP != null){
                    Point curP = e.getPoint();
                    ScreenPoint delta = new ScreenPoint(curP.x - lastP.x,curP.y - lastP.y);
                    RealPoint deltaR = converter.s2r(delta);
                    converter.setX(deltaR.getX());
                    converter.setY(deltaR.getY());
                    lastP = curP;
                    repaint();
                }
                if(current != null){
                    ScreenPoint p = new ScreenPoint(e.getX(), e.getY());
                    current.setP2(converter.s2r(p));
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
               /* *//*currentX = e.getX();
                currentY = e.getY();*//*
                ScreenPoint sp = new ScreenPoint(e.getX(), e.getY());
                current.setP2(converter.s2r(sp));
                repaint();*/
            }
        });
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    lastP = e.getPoint();
                }
                if (SwingUtilities.isLeftMouseButton(e)){
                    ScreenPoint p = new ScreenPoint(e.getX(), e.getY());
                    current = new Line(converter.s2r(p), converter.s2r(p));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    lastP = null;
                }
                if (SwingUtilities.isLeftMouseButton(e)){
                    lines.add(current);
                    current = null;
                    repaint();
                }
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
            double base = count < 0 ? 0.99 : 1.01;
            double kf = 1;
            for (int i = Math.abs(count); i > 0; i--) {
                kf *= base;
            }
            converter.setWidth(converter.getWidth() * kf);
            converter.setHeight(converter.getHeight() * kf);
            repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        converter.setsHeight(getHeight());
        converter.setsWidth(getWidth());

        BufferedImage bi = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics2D biG = bi.createGraphics();
        biG.setColor(Color.WHITE);
        biG.fillRect(0,0,getWidth(),getHeight());

         //LineDrawer ld = new GraphicsLineDrawer(biG);
        //LineDrawer ld = new DDALineDrawer(new GraphicsPixelDrawer(biG));
        LineDrawer ld = new BresenhamLineDrawer(new GraphicsPixelDrawer(biG));

        biG.setColor(Color.BLACK);

        drawLine(ld,converter,ox);
        drawLine(ld,converter,oy);

        drawGridOnOx(ld, converter);
        drawGridOnOy(ld, converter);

        for (Line l: lines) {
          drawLine(ld, converter, l);
        }
     /*   if (current != null){
            drawLine(ld,converter,current);
        }*/
        g2d.drawImage(bi,0,0,null);
        biG.dispose();
    }

    private static void drawLine(LineDrawer ld,ScreenConverter sc, Line l){
        ScreenPoint p1 = sc.r2s(l.getP1());
        ScreenPoint p2 = sc.r2s(l.getP2());
        ld.drawLine(p1.getX(),p1.getY(), p2.getX(), p2.getY());
    }

    public void transformFigures(Transformation tr){
        for (IFigure figure: figures) {
            for (RealPoint p: figure.getPoints()) {
                p = tr.pointConversion(p);
            }
        }
    }
    private void drawGridOnOy(LineDrawer drawer, ScreenConverter converter){
        for (int i = 0; i < 2 * GRID_SIZE; i++) {
            drawLine(drawer,converter,new Line(new RealPoint( - 0.1,-GRID_SIZE + (i + unitInterval)), new RealPoint( 0.1, -GRID_SIZE + (i + unitInterval))));
        }
    }

    private void drawGridOnOx(LineDrawer drawer, ScreenConverter converter){
        for (int i = 0; i < 2 * GRID_SIZE; i++) {
            drawLine(drawer,converter,new Line(new RealPoint(-GRID_SIZE + (i + unitInterval),0.1), new RealPoint(-GRID_SIZE  + (i + unitInterval),-0.1)));
        }
    }
}
