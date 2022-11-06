package ru.vsu.cs.eliseev.tasks.affine;

import ru.vsu.cs.eliseev.tasks.RealPoint;

import java.util.List;

public class Transformation implements IAffine{

    private List<IAffine> transformations;

    @Override
    public RealPoint pointConversion(RealPoint p) {
        for (IAffine transorm: transformations) {
            p = transorm.pointConversion(p);
        }
        return p;
    }

    public Transformation(List<IAffine> transformations) {
        this.transformations = transformations;
    }
}
