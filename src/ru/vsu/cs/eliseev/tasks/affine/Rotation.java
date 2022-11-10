package ru.vsu.cs.eliseev.tasks.affine;

import ru.vsu.cs.eliseev.tasks.RealPoint;

public class Rotation implements IAffine {

    private double angle;

    @Override
    public RealPoint pointConversion(RealPoint p) {
        double angleInRadians = (angle / 180.0) * Math.PI;
        System.out.println(Math.sin(angleInRadians));
        return new RealPoint(p.getX() * Math.cos(angleInRadians) - p.getY() * Math.sin(angleInRadians),
                p.getX() * Math.sin(angleInRadians) + p.getY() * Math.cos(angleInRadians));
    }

    public Rotation(double angle) {
        this.angle = angle;
    }
}
