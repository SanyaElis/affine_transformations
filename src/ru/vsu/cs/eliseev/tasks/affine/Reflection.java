package ru.vsu.cs.eliseev.tasks.affine;


import ru.vsu.cs.eliseev.tasks.RealPoint;

public class Reflection implements IAffine{//Отражение

    private int signX, signY;

    @Override
    public RealPoint pointConversion(RealPoint p) {
        return new RealPoint(p.getX() * signX, p.getY() * signY);
    }

    public Reflection(int signX, int signY) {
        this.signX = signX;
        this.signY = signY;
    }
}
