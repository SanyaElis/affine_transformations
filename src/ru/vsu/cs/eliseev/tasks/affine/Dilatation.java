package ru.vsu.cs.eliseev.tasks.affine;


import ru.vsu.cs.eliseev.tasks.RealPoint;

public class Dilatation implements IAffine{//Масштабирование a, b

    private  double kx, ky;

    @Override
    public RealPoint pointConversion(RealPoint p) {
        return new RealPoint(p.getX() * kx, p.getY() * ky);
    }
}
