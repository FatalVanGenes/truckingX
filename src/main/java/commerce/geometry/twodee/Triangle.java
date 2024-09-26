package commerce.geometry.twodee;

import commerce.geometry.Line;
import commerce.geometry.Point;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Triangle implements TwoD {
    private final Line side1;
    private final Line side2;
    private final Line side3;

    public Triangle(Point pointA, Point pointB, Point pointC) {
        this(Line.of(pointA, pointB), Line.of(pointB, pointC), Line.of(pointC, pointA));
    }

    @Override
    public double getArea() {
        return (side1.getLength() + side2.getLength() + side3.getLength()) / 2;
    }
}
