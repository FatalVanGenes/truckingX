package commerce.geometry.threedee;

public class Cube extends RectangularPrism {

    public Cube(int radius) {
        super(radius, radius, radius);
    }

    @Override
    public double getSurfaceArea() {
        return 6 * Math.pow(getX(), 2);
    }

    @Override
    public double getVolume() {
        return Math.pow(getX(), 3);
    }
}
