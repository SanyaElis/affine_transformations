package ru.vsu.cs.eliseev.tasks.affine;

import ru.vsu.cs.eliseev.tasks.RealPoint;

public class Rotation implements IAffine {

    private double angle;

    @Override
    public RealPoint pointConversion(RealPoint p) {
        return new RealPoint(p.getX() * Math.cos(angle) - p.getY() * Math.sin(angle),
                p.getX() * Math.sin(angle) + p.getY() * Math.cos(angle));
    }

    public Rotation(double angle) {
        this.angle = angle;
    }
}
