package commerce.model;

import org.junit.jupiter.api.Test;

public class TripTest {

    @Test
    public void testDueNW() {
        calculate("testDueNW", Coordinate.of(1_234,1_234), Coordinate.of(1_000, 1_000));
    }

    @Test
    public void testDueEast() {
        calculate("testDueEast", Coordinate.of(1_000,1_000), Coordinate.of(2_000, 1_000));
    }

    @Test
    public void testDueSouth() {
        calculate("testDueSouth", Coordinate.of(1_000,1_000), Coordinate.of(1_000, 2_000));
    }

    @Test
    public void testDueWest() {
        calculate("testDueWest", Coordinate.of(1_000,1_000), Coordinate.of(0, 1_000));
    }

    @Test
    public void testDueNorth() {
        calculate("testDueNorth", Coordinate.of(1_000,1_000), Coordinate.of(1_000, 0));
    }

    private static void calculate(String name, Coordinate sourceCoord, Coordinate destCoord) {
        Trip trip = new Trip(sourceCoord, destCoord);
        System.out.println(name);
        // System.out.println("slope: " + trip.getSlope());
        System.out.println("radians: " + trip.getLine().getAzimuth().getTheta());
        System.out.println("degrees: " + trip.getLine().getAzimuth().getDegrees());
    }
}
