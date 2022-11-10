package ru.vsu.cs.eliseev.tasks.figure;

import ru.vsu.cs.eliseev.tasks.RealPoint;
import ru.vsu.cs.eliseev.tasks.ScreenConverter;
import ru.vsu.cs.eliseev.tasks.drawers.LineDrawer;

import java.util.List;

public interface IFigure {

    void drawFigure(List<RealPoint> points, ScreenConverter sc, LineDrawer ld);
    List<RealPoint> getPoints();
    void setPoints(List<RealPoint> points);
}
