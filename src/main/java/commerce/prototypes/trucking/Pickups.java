package commerce.prototypes.trucking;

import commerce.geometry.threedee.RectangularPrism;
import commerce.industry.transport.model.PickupTruck;
import commerce.industry.transport.model.components.TruckIdentity;
import commerce.industry.transport.model.components.TruckOperation;
import commerce.industry.transport.model.components.TruckPerformance;
import commerce.model.Coordinate;
import commerce.model.Dimension;
import commerce.model.impl.DecayingVehiclePart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static commerce.prototypes.trucking.PrototypeConstants.STANDARD_VARIANCE;

import static commerce.common.Utils.convertMilesToKM;

public class Pickups {
    private static final int PICKUP_TIRE_COUNT = 4;
    private static final Dimension PICKUP_BED_SIZE = Dimension.of(3, 6, 12);

    public static PickupTruck newAveragePickup(Coordinate location) {
        long createTS = 0;
        float maxGallonsTank = 25;
        float maxKMPHSpeed = (float) convertMilesToKM(70);
        float kmpg = (float) convertMilesToKM(6);
        RectangularPrism cargoBox = null;
        int towWeightLbs = 3_000;
        int horsePower = 800;

        DecayingVehiclePart engine = DecayingVehiclePart.builder().name("engine").createTS(0).createMileage(0)
                .milesPerPercent((float) convertMilesToKM(7500)).daysPerPercent(55).debugVariance(STANDARD_VARIANCE).build();
        DecayingVehiclePart[] tires = IntStream.range(0, PICKUP_TIRE_COUNT).mapToObj(i ->
                        DecayingVehiclePart.builder().name("tire").createTS(0).createMileage(0).milesPerPercent((float) convertMilesToKM(500))
                                .daysPerPercent(18).debugVariance(STANDARD_VARIANCE).build())
                .collect(Collectors.toList()).toArray(new DecayingVehiclePart[PICKUP_TIRE_COUNT]);
        DecayingVehiclePart transmission = DecayingVehiclePart.builder().name("transmission").createTS(0).createMileage(0)
                .milesPerPercent((float) convertMilesToKM(2000)).daysPerPercent(24).debugVariance(STANDARD_VARIANCE).build();
        DecayingVehiclePart battery = DecayingVehiclePart.builder().name("battery").createTS(0).createMileage(0)
                .milesPerPercent((float) convertMilesToKM(600)).daysPerPercent(7).debugVariance(STANDARD_VARIANCE).build();
        //huge time number means time is not a factor
        DecayingVehiclePart brakePads = DecayingVehiclePart.builder().name("brake pads").createTS(0).createMileage(0)
                .milesPerPercent((float) convertMilesToKM(150)).daysPerPercent(1_000_000).debugVariance(STANDARD_VARIANCE).build();

        TruckIdentity identity = TruckIdentity.builder().createTS(System.currentTimeMillis())
                .description("midrange pickup").make("GMC").model("Wrangler").vin(UUID.randomUUID().toString())
                .year("2021").build();
        TruckPerformance performance = TruckPerformance.builder().horsePower(horsePower).maxGallonsTank(maxGallonsTank)
                .maxSpeed(maxKMPHSpeed).mpg(kmpg).mileage(0.0f).towWeightLbs(towWeightLbs).build();
        List<DecayingVehiclePart> parts = new ArrayList<>(List.of(engine, transmission, battery, brakePads));
        parts.addAll(Arrays.asList(tires));
        TruckOperation operation =
                TruckOperation.builder().allParts(parts).cabSeating(3).currentCoordinate(Coordinate.of(500, 500))
                        .build();
        boolean hasTrailerHitch = true;

        PickupTruck pickupTruck = PickupTruck.builder().identity(identity).performance(performance).operation(operation)
                .flatBed(PICKUP_BED_SIZE)
                .hasTrailerHitch(hasTrailerHitch).build();
        pickupTruck.getAllParts().forEach(part -> part.install(pickupTruck));
        return pickupTruck;
    }

}
