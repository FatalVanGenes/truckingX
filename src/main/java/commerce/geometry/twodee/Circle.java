package commerce.geometry.twodee;

import commerce.geometry.Point;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Circle implements TwoD {
    private final Point center;
    private final double radius;

    @Override
    public double getArea() {
        return radius * radius * Math.PI;
    }
}
