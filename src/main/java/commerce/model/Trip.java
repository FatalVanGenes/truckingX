package commerce.model;

import commerce.geometry.Line;

public class Trip {

    private final Coordinate startPoint;
    private final Coordinate destPoint;

    private final Line line;
    private boolean originReached;
    private boolean destinationReached;

    public Trip(Coordinate startPoint, Coordinate destPoint) {
        this.startPoint = startPoint;
        this.destPoint = destPoint;
        this.line = Line.of(startPoint.getPoint(), destPoint.getPoint());
    }

    public boolean isOriginReached() {
        return originReached;
    }

    public boolean isDestinationReached() {
        return destinationReached;
    }

    public Line getLine() {
        return line;
    }

    public Coordinate getStartPoint() {
        return startPoint;
    }

    public Coordinate getDestPoint() {
        return destPoint;
    }

}
