package commerce.geometry.twodee;

import commerce.geometry.Point;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Rectangle implements TwoD {
    private final Point upperLeft;
    private final Point lowerRight;

    @Override
    public double getArea() {
        return Math.abs(upperLeft.getX() - lowerRight.getX()) * Math.abs(upperLeft.getY() - lowerRight.getY());
    }
}
