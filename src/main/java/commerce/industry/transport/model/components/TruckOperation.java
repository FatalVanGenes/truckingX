package commerce.industry.transport.model.components;

import commerce.model.Coordinate;
import commerce.model.Employee;
import commerce.model.Trip;
import commerce.model.impl.DecayingVehiclePart;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
public class TruckOperation {
    private final Collection<DecayingVehiclePart> allParts;
    private final List<TruckEvent> events = new ArrayList<>();
    private final int cabSeating;

    // volatiles
    private Employee driver;
    private Coordinate currentCoordinate;
    private Trip currentTrip;

    public Optional<Coordinate> getCoordinate() {
        return Optional.ofNullable(currentCoordinate);
    }

    public Optional<Trip> getTrip() {
        return Optional.ofNullable(currentTrip);
    }

    public Optional<Employee> getDriver() {
        return Optional.ofNullable(driver);
    }
}

