package ru.vsu.cs.eliseev.tasks.affine;

import ru.vsu.cs.eliseev.tasks.RealPoint;

public class Translation implements IAffine{//перенос
    private double dx, dy;
    @Override
    public RealPoint pointConversion(RealPoint p) {
        return new RealPoint(p.getX() + dx, p.getY() + dy);
    }

    public Translation(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
}
