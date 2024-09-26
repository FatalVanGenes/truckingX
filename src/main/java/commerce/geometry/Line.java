package commerce.geometry;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Line {
    private final Point pointA;
    private final Point pointB;
    private final Azimuth azimuth;

    private Line(Point pointA, Point pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.azimuth = Azimuth.of(pointA, pointB);
    }

    public static Line of(Point pointA, Point pointB) {
        return new Line(pointA, pointB);
    }

    public double getLength() {
        return Point.getDistance(pointA, pointB);
    }

    @AllArgsConstructor(access = AccessLevel.PACKAGE)
    @Data
    public static class Azimuth {

        private final double theta;
        private final double degrees;

        public static Azimuth of(Point startPoint, Point destPoint) {
            double radians = getSlopeInRadians(startPoint, destPoint);
            double degrees = radians * (180 / Math.PI);
            return new Azimuth(radians, degrees);
        }

        private static double getSlopeInRadians(Point startPoint, Point destPoint) {
            double radians = Math.atan2(destPoint.getY() - startPoint.getY(), destPoint.getX() - startPoint.getX());
            radians += Math.PI / 2.0;
            if (radians < 0) {
                radians += (Math.PI * 2);
            }
            return radians;
        }
    }

}
