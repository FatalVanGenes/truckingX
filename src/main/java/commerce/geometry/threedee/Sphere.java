package commerce.geometry.threedee;

import commerce.geometry.twodee.Circle;
import commerce.geometry.Point;

public class Sphere extends Circle implements ThreeD  {

    protected Sphere(Point center, double radius) {
        super(center, radius);
    }

    @Override
    public double getSurfaceArea() {
        return 4 * Math.PI * getRadius() * getRadius();
    }

    @Override
    public double getVolume() {
        return (4.0 / 3) * Math.PI * Math.pow(getRadius(), 3);
    }
}
