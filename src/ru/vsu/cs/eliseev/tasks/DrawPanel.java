package ru.vsu.cs.eliseev.tasks;

import ru.vsu.cs.eliseev.tasks.drawers.GraphicsLineDrawer;
import ru.vsu.cs.eliseev.tasks.drawers.LineDrawer;

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

    private static Line findLines(List<Line> lines, ScreenConverter sc, ScreenPoint sp, int eps){
        Line answer = null;
        for (Line l: lines) {
           ScreenPoint p1 = sc.r2s(l.getP1()) ;
           ScreenPoint p2 = sc.r2s(l.getP2()) ;
           if ( (p1.getX() - eps) < (sp.getX() ) && (sp.getX() < p2.getX() + eps) && (p1.getX() + eps) > (sp.getX() ) && (sp.getX() < p2.getX() + eps)){
               double ax = p1.getX() - p2.getX();
               double ay = p1.getY() - p2.getY();


            }
        }
        return answer;
    }
    public DrawPanel() {

        converter = new ScreenConverter(800,600, -2,2, 4, 4);
        ox = new Line(new RealPoint(-1,0), new RealPoint(1,0));
        oy = new Line(new RealPoint(0,-1), new RealPoint(0,1));
        //current = new Line(new RealPoint(0,0), new RealPoint(0,0));

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
        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int count = e.getWheelRotation();
                double base = count < 0 ? 0.99 : 1.01;
                double kf = 1;
                for (int i = Math.abs(count); i > 0; i--) {
                    kf *= base;
                }
                converter.setWidth(converter.getWidth() * kf);
                converter.setHeight(converter.getHeight() * kf);
                repaint();
            }
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

         LineDrawer ld = new GraphicsLineDrawer(biG);
        //LineDrawer ld = new DDALineDrawer(new GraphicsPixelDrawer(biG));

        biG.setColor(Color.BLACK);
        /*ld.drawLine(getWidth() / 2, getHeight() / 2, currentX, currentY);*/
        drawLine(ld,converter,ox);
        drawLine(ld,converter,oy);
        //drawLine(ld,converter,current);
        for (Line l: lines) {
          drawLine(ld, converter, l);
        }
        if (current != null){
            drawLine(ld,converter,current);
        }
        g2d.drawImage(bi,0,0,null);
        biG.dispose();
    }

    private static void drawLine(LineDrawer ld,ScreenConverter sc, Line l){
        ScreenPoint p1 = sc.r2s(l.getP1());
        ScreenPoint p2 = sc.r2s(l.getP2());
        ld.drawLine(p1.getX(),p1.getY(), p2.getX(), p2.getY());
    }


}
