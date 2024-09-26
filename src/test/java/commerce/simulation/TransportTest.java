package commerce.simulation;

import commerce.industry.transport.model.Truck;
import org.junit.jupiter.api.Test;
import commerce.prototypes.trucking.Semis;
import commerce.model.Coordinate;
import commerce.model.Employee;
import commerce.model.Trip;
import commerce.association.profession.Driver;

public class TransportTest {

    @Test
    public void testSimulateTruckTravel() {
        Coordinate pointA = Coordinate.of(1_000,1_000);
        Coordinate pointB = Coordinate.of(2_000, 2_000);
        // create a truck
        Truck truck = Semis.newAverageLargeSemi(Coordinate.of(10,10));
        // create a driver
        Employee driver = Employee.builder().name("Martin Rain").birthTS(12345L).profession(new Driver()).build();
        // assign them to the truck
        truck.addDriver(driver);

        Trip trip = new Trip(pointA, pointB);
        truck.setTripAndWarp(trip);

        assert driver.getCoordinate().get().equals(truck.getCoordinate().get());
        // no guarantee the truck's current starting point is the same as the trip start point
        assert truck.getCoordinate().get().equals(truck.getTrip().get().getStartPoint());
    }
}
