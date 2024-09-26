package commerce.model;

import commerce.industry.transport.model.BaseTruck;
import org.junit.jupiter.api.Test;

import java.util.List;

import static commerce.prototypes.trucking.Semis.newAverageLargeSemi;
import static commerce.prototypes.trucking.Semis.newCheapLargeSemi;
import static commerce.prototypes.trucking.Semis.newExpensiveLargeSemi;

class TruckTest {

    @Test
    void testPartsDepreciation() throws Exception {
        List<BaseTruck> trucks = List.of(
                newAverageLargeSemi(Coordinate.of(100, 100)),
                newAverageLargeSemi(Coordinate.of(200, 300)),
                newCheapLargeSemi(Coordinate.of(100, 100)),
                newCheapLargeSemi(Coordinate.of(100, 100)),
                newExpensiveLargeSemi(Coordinate.of(100, 100)),
                newExpensiveLargeSemi(Coordinate.of(100, 100))
        );

        long sixMonths = 180 * 24 * 60 * 60 * 1_000L;

        trucks.forEach(truck -> {
            truck.addMileage(50_000);
            truck.showPartsStatus(sixMonths);
        });
    }

}
