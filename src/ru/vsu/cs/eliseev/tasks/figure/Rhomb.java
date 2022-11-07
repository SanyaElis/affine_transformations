package ru.vsu.cs.eliseev.tasks.figure;

import ru.vsu.cs.eliseev.tasks.RealPoint;
import ru.vsu.cs.eliseev.tasks.ScreenConverter;
import ru.vsu.cs.eliseev.tasks.drawers.LineDrawer;

import java.util.List;

public class Rhomb implements IFigure{

    private List<RealPoint> points;

    public Rhomb(List<RealPoint> points) {
        if (points.size() != 4){
            System.out.println("Нехватает точек");
        }
        this.points = points;
    }

    @Override
    public void drawFigure(List<RealPoint> points, ScreenConverter sc, LineDrawer ld){
        boolean first = true;
        RealPoint prev = points.get(0);
        for (RealPoint p: points) {
           if (first){
               first = false;
               continue;
           }
           ld.drawLine(sc.r2s(prev).getX(), sc.r2s(prev).getY(), sc.r2s(p).getX(), sc.r2s(p).getY());
           prev = p;
        }
    }

    @Override
    public List<RealPoint> getPoints() {
        return points;
    }
}
