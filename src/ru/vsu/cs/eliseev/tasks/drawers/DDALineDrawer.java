package ru.vsu.cs.eliseev.tasks.drawers;

import java.awt.*;

public class DDALineDrawer implements LineDrawer{
    private final PixelDrawer pd;

    public DDALineDrawer(PixelDrawer pd) {
        this.pd = pd;
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, Color c) {

        if(x2 < x1){
            int t = x1;
            x1 = x2;
            x2 = t;
            t = y1;
            y1 = y2;
            y2 = t;
        }

        int dx = x2 - x1;
        double dy = y2 - y1;
        if(dx != 0){
            for (int i = 0; i <= dx; i++) {
                pd.drawPixel(x1 + i, (int)(y1 + i * dy / dx), Color.BLACK);
            }
        }

        if(y2 < y1){
            int t = x1;
            x1 = x2;
            x2 = t;
            t = y1;
            y1 = y2;
            y2 = t;
        }

        dx = x2 - x1;
        dy = y2 - y1;
        if(dy != 0){
            if (dy > dx) {
                for (int i = 0; i <= dy; i++) {
                    pd.drawPixel((int) (x1 + i * dx / dy), y1 + i, c);
                }
            }
        }
    }
}