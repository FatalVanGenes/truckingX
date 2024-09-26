package commerce.geometry;

import com.google.common.util.concurrent.AtomicDouble;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

@AllArgsConstructor
@Data
public class Point {

    private final double x;
    private final double y;

    public double getDistance(Point other) {
        return getDistance(this, other);
    }

    public static double getDistance(Point thisOne, Point other) {
        double xEdge = Math.abs(thisOne.getX() - other.getX());
        double yEdge = Math.abs(thisOne.getY() - other.getY());
        return Math.sqrt((xEdge * xEdge) + (yEdge * yEdge));
    }

    public static Point of(double x, double y) {
        return new Point(x, y);
    }

    public static Point findNearestPointSupplier(Collection<Supplier<Point>> points, Point srcPoint) {
        if (points.isEmpty()) {
            throw new RuntimeException("would result in divide by zero");
        }
        AtomicReference<Pair<Double, Point>> center = new AtomicReference<>();
        points.forEach(pt -> {
            Point destPoint = pt.get();
            double calc = destPoint.getDistance(srcPoint);
            if ((center.get() == null) || (calc < center.get().getLeft())) {
                center.set(Pair.of(destPoint.getDistance(srcPoint), destPoint));
            }
        });
        return center.get().getRight();
    }

    public static Point findNearestPoint(Collection<Point> points, Point srcPoint) {
        if (points.isEmpty()) {
            throw new RuntimeException("would result in divide by zero");
        }
        AtomicReference<Pair<Double, Point>> center = new AtomicReference<>();
        points.forEach(destPoint -> {
            double calc = destPoint.getDistance(srcPoint);
            if ((center.get() == null) || (calc < center.get().getLeft())) {
                center.set(Pair.of(destPoint.getDistance(srcPoint), destPoint));
            }
        });
        return center.get().getRight();
    }

    public static Point findCenterPointSupplier(Collection<Supplier<Point>> points) {
        if (points.isEmpty()) {
            throw new RuntimeException("would result in divide by zero");
        }
        AtomicDouble x = new AtomicDouble(0);
        AtomicDouble y = new AtomicDouble(0);
        points.forEach(pt -> {
            x.addAndGet(pt.get().getX());
            y.addAndGet(pt.get().getY());
        });
        int size = points.size();
        return Point.of(x.get() / size, y.get() / size);
    }

    public static Point findCenterPoint(Collection<Point> points) {
        if (points.isEmpty()) {
            throw new RuntimeException("would result in divide by zero");
        }
        AtomicDouble x = new AtomicDouble(0);
        AtomicDouble y = new AtomicDouble(0);
        points.forEach(pt -> {
            x.addAndGet(pt.getX());
            y.addAndGet(pt.getY());
        });
        int size = points.size();
        return Point.of(x.get() / size, y.get() / size);
    }
}
