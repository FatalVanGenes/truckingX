package commerce.geometry.threedee;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RectangularPrism {

    private final double x;

    private final double y;

    private final double z;


    public double getSurfaceArea() {
        return 2 * ((getX() * getY()) + (getY() * getZ()) + (getZ() * getX()));
    }

    public double getVolume() {
        return getX() * getY() * getZ();
    }
}
