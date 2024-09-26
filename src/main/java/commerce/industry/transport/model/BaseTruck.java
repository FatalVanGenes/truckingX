package commerce.industry.transport.model;

import commerce.Global;
import commerce.geometry.threedee.RectangularPrism;
import commerce.industry.transport.model.components.TruckIdentity;
import commerce.industry.transport.model.components.TruckOperation;
import commerce.industry.transport.model.components.TruckPerformance;
import commerce.model.Coordinate;
import commerce.model.Dimension;
import commerce.model.Employee;
import commerce.model.Trip;
import commerce.model.impl.DecayingVehiclePart;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public abstract class BaseTruck implements Truck {

    private final TruckIdentity identity;
    private final TruckPerformance performance;
    private final TruckOperation operation;
    private final Dimension flatBed;
    private final RectangularPrism cargoBox;
    private final boolean hasTrailerHitch;

    @Override
    public void addDriver(Employee driver) {
        setDriver(driver);
        getCoordinate().ifPresent(driver::setCoordinate);
    }

    @Override
    public Optional<Employee> getDriver() {
        return this.operation.getDriver();
    }

    private void setDriver(Employee driver) {
        this.operation.setDriver(driver);
    }

    @Override
    public long getCreateTS() {
        return this.identity.getCreateTS();
    }


    @Override
    public void updateMaxSpeed(float maxSpeed) {
        this.performance.setMaxSpeed(maxSpeed);
    }

    @Override
    public void setTrip(Trip currentTrip) {
        this.operation.setCurrentTrip(currentTrip);
    }

    @Override
    public void setTripAndWarp(Trip currentTrip) {
        setCurrentCoordinate(currentTrip.getStartPoint());
        setTrip(currentTrip);
    }

    @Override
    public void updateMpg(float mpg) {
        this.performance.setMpg(mpg);
    }


    @Override
    public void setCurrentCoordinate(Coordinate currentCoordinate) {
        this.operation.setCurrentCoordinate(currentCoordinate);
        getDriver().ifPresent(driver -> driver.setCoordinate(currentCoordinate));
    }

    public Optional<Dimension> getFlatBed() {
        return Optional.ofNullable(flatBed);
    }

    @Override
    public float getMaxGallonsTank() {
        return performance.getMaxGallonsTank();
    }

    @Override
    public float getMaxSpeed() {
        return performance.getMaxSpeed();
    }

    @Override
    public float getMpg() {
        return performance.getMpg();
    }

    @Override
    public Collection<DecayingVehiclePart> getAllParts() {
        return operation.getAllParts();
    }

    @Override
    public void removeDriver() {
        operation.setDriver(null);
    }

    @Override
    public float getMileage() {
        return performance.getMileage();
    }

    public long getCurrentTime() {
        return Global.getInstance().getCurrentTS();
    }

    @Override
    public Optional<Trip> getTrip() {
        return operation.getTrip();
    }

    @Override
    public Optional<Coordinate> getCoordinate() {
        return operation.getCoordinate();
    }

    @Override
    public void addMileage(int addedKM) {
        this.performance.addMileage(addedKM);
    }

    public void showPartsStatus(long asOf) {
        System.out.printf("%ntruck: %s%n", getDescription());
        // this.operation.getAllParts().stream().peek(part -> part.install(this));
        this.operation.getAllParts().forEach(System.out::println);
    }

    @Override
    public String getDescription() {
        return identity.getDescription();
    }

}
