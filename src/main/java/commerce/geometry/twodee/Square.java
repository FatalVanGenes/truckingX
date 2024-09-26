package commerce.geometry.twodee;

import commerce.geometry.Point;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Square extends Rectangle {

    public Square(Point upperLeft, double length) {
        super(upperLeft, new Point(upperLeft.getX() + length, upperLeft.getY() + length));
    }

}
