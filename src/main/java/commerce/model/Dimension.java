package commerce.model;

public class Dimension {
    private final long heightFeet;
    private final long widthFeet;
    private final long depthFeet;

    private Dimension(long heightFeet, long widthFeet, long depthFeet) {
        this.heightFeet = heightFeet;
        this.widthFeet = widthFeet;
        this.depthFeet = depthFeet;
    }

    public static Dimension of(long heightFeet, long widthFeet, long depthFeet) {
        return new Dimension(heightFeet, widthFeet, depthFeet);
    }

    public long getHeightFeet() {
        return heightFeet;
    }

    public long getWidthFeet() {
        return widthFeet;
    }

    public long getDepthFeet() {
        return depthFeet;
    }
}
