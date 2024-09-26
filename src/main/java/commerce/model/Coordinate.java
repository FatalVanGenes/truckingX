package commerce.model;

import commerce.geometry.Point;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class Coordinate {

    private final Point point;

    private Coordinate(long x, long y) {
        this(new Point(x, y));
    }

    public static final Coordinate NOWHERE = new Coordinate(Long.MAX_VALUE, Long.MAX_VALUE);

    public static Coordinate of(long x, long y) {
        return new Coordinate(x, y);
    }

    public static Coordinate of(@NonNull Point point) {
        return new Coordinate(point);
    }

    public static Coordinate copy(@NonNull Coordinate other) {
        return new Coordinate((long) other.getPoint().getX(), (long) other.getPoint().getY());
    }

    public static Coordinate findClosest(Collection<Coordinate> collection, @NonNull Coordinate key) {
        if (collection.isEmpty()) {
            throw new IllegalArgumentException("coordinate collection cannot be null");
        }
        Point found =
                Point.findNearestPoint(collection.stream().map(Coordinate::getPoint).collect(Collectors.toList()),
                        key.getPoint());
        Optional<Coordinate> match = collection.stream().filter(c -> c.getPoint() == found).findFirst();
        if (match.isPresent()) {
            return match.get();
        }
        throw new RuntimeException("the impossible has happened");
    }

    public static Coordinate findCenterPoint(Collection<Supplier<Coordinate>> collection) {
        Point center =
                Point.findCenterPoint(collection.stream().map(city -> city.get().getPoint()).collect(Collectors.toList()));
        return Coordinate.of(center);
    }

    public Coordinate clone() {
        return copy(this);
    }

    public long getDistance(@NonNull Coordinate other) {
        return (long) this.getPoint().getDistance(other.getPoint());
    }

    public long getX() {
        return (long) this.getPoint().getX();
    }

    public long getY() {
        return (long) this.getPoint().getY();
    }

    @Data
    public static class CoordinateView {
        private final long x;
        private final long y;

        public static CoordinateView from(Coordinate c) {
            return new CoordinateView(c.getX(), c.getY());
        }
    }
}
