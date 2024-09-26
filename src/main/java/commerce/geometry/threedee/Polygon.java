package commerce.geometry.threedee;

import com.google.common.util.concurrent.AtomicDouble;
import commerce.geometry.Line;
import commerce.geometry.Point;
import commerce.geometry.twodee.Rectangle;
import commerce.geometry.twodee.TwoD;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@Data
public class Polygon implements TwoD {

    private final Collection<Line> sides;


    public static Polygon of(Collection<Point> points) {
        AtomicReference<Collection<Line>> builderSides = new AtomicReference<>(new ArrayList<>());
        AtomicReference<Point> first = new AtomicReference<>();
        AtomicReference<Point> last = new AtomicReference<>();
        points.forEach(point -> {
            if (first.get() == null) {
                first.set(point);
            } else {
                Point recent = last.get();
                if (recent != null) {
                    builderSides.get().add(Line.of(recent, point));
                }
                last.set(point);
            }
        });
        builderSides.get().add(Line.of(last.get(), first.get()));
        return new Polygon(builderSides.get());
    }

    public Rectangle getBoundingBox() {
        AtomicBoolean first = new AtomicBoolean(false);
        AtomicDouble minX = new AtomicDouble(), minY = new AtomicDouble(), maxX = new AtomicDouble(), maxY = new AtomicDouble();
        sides.forEach(side -> {
            if (!first.get()) {
                maxX.set(Math.max(side.getPointA().getX(), side.getPointB().getX()));
                minX.set(Math.min(side.getPointA().getX(), side.getPointB().getX()));
                maxY.set(Math.max(side.getPointA().getY(), side.getPointB().getY()));
                minY.set(Math.min(side.getPointA().getY(), side.getPointB().getY()));
                first.set(true);
            } else {
                maxX.set(Math.max(Math.max(side.getPointA().getX(), side.getPointB().getX()), maxX.get()));
                minX.set(Math.max(Math.min(side.getPointA().getX(), side.getPointB().getX()), minX.get()));
                maxY.set(Math.max(Math.max(side.getPointA().getY(), side.getPointB().getY()), maxY.get()));
                minX.set(Math.max(Math.min(side.getPointA().getY(), side.getPointB().getY()), minY.get()));
            }
        });
        return new Rectangle(Point.of(minX.get(), minY.get()), Point.of(maxX.get(), maxY.get()));
    }

    @Override
    public double getArea() {
        AtomicDouble area = new AtomicDouble(0.0);
        sides.forEach(side -> area.addAndGet(side.getLength()));
        return Math.abs(area.get() / 2.0);
    }
}
