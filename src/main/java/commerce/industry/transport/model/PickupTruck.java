package commerce.industry.transport.model;

import commerce.geometry.threedee.RectangularPrism;
import commerce.industry.transport.model.components.TruckIdentity;
import commerce.industry.transport.model.components.TruckOperation;
import commerce.industry.transport.model.components.TruckPerformance;
import commerce.model.Dimension;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class PickupTruck extends BaseTruck {

    private PickupTruck(TruckIdentity identity, TruckPerformance performance, TruckOperation operation,
                  Dimension flatBed, RectangularPrism cargoBox, boolean hasTrailerHitch) {
        super(identity, performance, operation, flatBed, cargoBox, hasTrailerHitch);
    }
}
