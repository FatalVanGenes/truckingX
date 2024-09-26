package commerce.geometry.threedee;

import commerce.geometry.twodee.Circle;
import commerce.geometry.Point;

public class Cylinder extends Circle implements ThreeD {

    private final double length;

    public Cylinder(Point center, double radius, double length) {
        super(center, radius);
        this.length = length;
    }

    public double getLength() {
        return length;
    }

    @Override
    public double getSurfaceArea() {
        return 2 * Math.PI * getRadius() * (getRadius() + getLength());
    }

    @Override
    public double getVolume() {
        return Math.PI * (getRadius() * getRadius() ) * getLength();
    }
}
