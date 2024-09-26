package commerce.industry.transport.model;

import commerce.industry.financial.model.Asset;
import commerce.model.Coordinate;
import commerce.model.Employee;
import commerce.model.Trip;
import commerce.model.impl.DecayingVehiclePart;

import java.util.Collection;
import java.util.Optional;

public interface Truck extends Asset, Vehicle {

    void addDriver(Employee driver);
    Optional<Coordinate> getCoordinate();
    long getCurrentTime();
    Optional<Trip> getTrip();

    void setTrip(Trip trip);

    void setCurrentCoordinate(Coordinate currentCoordinate);

    float getMaxGallonsTank();

    float getMaxSpeed();

    float getMpg();

    Collection<DecayingVehiclePart> getAllParts();

    void removeDriver();

    float getMileage();

    Optional<Employee> getDriver();

    long getCreateTS();

    void updateMaxSpeed(float maxSpeed);

    void setTripAndWarp(Trip trip);
    void updateMpg(float mpg);

    void addMileage(int addedKM);

    String getDescription();
}
