package commerce.geometry;

import org.junit.jupiter.api.Test;

public class LineTest {

    @Test
    public void testDueNW() {
        calculate("testDueNW", Point.of(1_234, 1_234), Point.of(1_000, 1_000));
    }

    @Test
    public void testDueEast() {
        calculate("testDueEast", Point.of(1_000, 1_000), Point.of(2_000, 1_000));
    }

    @Test
    public void testDueSouth() {
        calculate("testDueSouth", Point.of(1_000, 1_000), Point.of(1_000, 2_000));
    }

    @Test
    public void testDueWest() {
        calculate("testDueWest", Point.of(1_000, 1_000), Point.of(0, 1_000));
    }

    @Test
    public void testDueNorth() {
        calculate("testDueNorth", Point.of(1_000, 1_000), Point.of(1_000, 0));
    }

    private static void calculate(String name, Point sourceCoord, Point destCoord) {
        Line trip = Line.of(sourceCoord, destCoord);
        System.out.println(name);
        // System.out.println("slope: " + trip.getSlope());
        System.out.println("radians: " + trip.getAzimuth().getTheta());
        System.out.println("degrees: " + trip.getAzimuth().getDegrees());
    }
}
