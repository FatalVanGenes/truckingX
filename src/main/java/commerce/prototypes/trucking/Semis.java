package commerce.prototypes.trucking;

import commerce.geometry.threedee.RectangularPrism;
import commerce.industry.transport.model.Semi;
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

import static commerce.common.Utils.convertMilesToKM;
import static commerce.prototypes.trucking.PrototypeConstants.STANDARD_VARIANCE;
import static commerce.prototypes.trucking.PrototypeConstants.TIGHT_VARIANCE;
import static commerce.prototypes.trucking.PrototypeConstants.WILD_VARIANCE;

public class Semis {

    private static final int LARGE_TRUCK_TIRE_COUNT = 18;
    private static final Dimension LARGE_TRUCK_BED_SIZE = Dimension.of(13, 8, 53);

    public static Semi newExpensiveLargeSemi(Coordinate location) {
        float maxGallonsTank = 350;
        float maxKMPHSpeed = (float) convertMilesToKM(85);
        float kmpg = (float) convertMilesToKM(7);
        int towWeightLbs = 50_000;
        int horsePower = 1_200;

        DecayingVehiclePart engine =
                DecayingVehiclePart.builder().name("engine").createTS(0L).createMileage(0)
                        .milesPerPercent((float) convertMilesToKM(8_000)).daysPerPercent(75).debugVariance(STANDARD_VARIANCE)
                        .build();
        DecayingVehiclePart[] tires = IntStream.range(0, LARGE_TRUCK_TIRE_COUNT).mapToObj(i ->
                        DecayingVehiclePart.builder().name("tire").createTS(0).createMileage(0)
                                .milesPerPercent((float) convertMilesToKM(550)).daysPerPercent(22).debugVariance(TIGHT_VARIANCE).build())
                .collect(Collectors.toList()).toArray(new DecayingVehiclePart[LARGE_TRUCK_TIRE_COUNT]);

        DecayingVehiclePart transmission = DecayingVehiclePart.builder().name("transmission").createTS(0).createMileage(0)
                .milesPerPercent((float) convertMilesToKM(2_500)).daysPerPercent(32).debugVariance(STANDARD_VARIANCE).build();
        DecayingVehiclePart battery = DecayingVehiclePart.builder().name("battery").createTS(0).createMileage(0)
                .milesPerPercent((float) convertMilesToKM(750)).daysPerPercent(8)
                .debugVariance(STANDARD_VARIANCE).build();
        //huge time number means time is not a factor
        DecayingVehiclePart brakePads = DecayingVehiclePart.builder().name("brake pads").createTS(0).createMileage(0)
                .milesPerPercent((float) convertMilesToKM(175)).daysPerPercent(1_000_000).debugVariance(TIGHT_VARIANCE).build();

        TruckIdentity identity = TruckIdentity.builder().createTS(System.currentTimeMillis())
                .description("expensive truck").make("Mercedes Benz").model("Cruiser").vin(UUID.randomUUID().toString())
                .year("2021").build();
        TruckPerformance performance = TruckPerformance.builder().horsePower(horsePower).maxGallonsTank(maxGallonsTank)
                .maxSpeed(maxKMPHSpeed).mileage(0.0f).mpg(kmpg).towWeightLbs(towWeightLbs).build();

        List<DecayingVehiclePart> parts = new ArrayList<>(List.of(engine, transmission, battery, brakePads));
        parts.addAll(Arrays.asList(tires));
        TruckOperation operation =
                TruckOperation.builder().allParts(parts).cabSeating(3).currentCoordinate(Coordinate.of(500, 500))
                        .build();
        boolean hasTrailerHitch = true;

        Semi semi = Semi.builder().identity(identity).performance(performance).operation(operation)
                .hasTrailerHitch(hasTrailerHitch).build();
        semi.getAllParts().forEach(part -> part.install(semi));
        return semi;
//                maxGallonsTank,
//                maxKMPHSpeed,
//                kmpg, engine, tires, transmission, battery, brakePads, location);
    }

    public static Semi newAverageLargeSemi(Coordinate location) {
        long createTS = 0;
        float maxGallonsTank = 250;
        float maxKMPHSpeed = (float) convertMilesToKM(70);
        float kmpg = (float) convertMilesToKM(6);
        Dimension flatBed = null;
        RectangularPrism cargoBox = null;
        int towWeightLbs = 30_000;
        int horsePower = 800;

        DecayingVehiclePart engine = DecayingVehiclePart.builder().name("engine").createTS(0).createMileage(0)
                .milesPerPercent((float) convertMilesToKM(7500)).daysPerPercent(55).debugVariance(STANDARD_VARIANCE).build();
        DecayingVehiclePart[] tires = IntStream.range(0, LARGE_TRUCK_TIRE_COUNT).mapToObj(i ->
                        DecayingVehiclePart.builder().name("tire").createTS(0).createMileage(0).milesPerPercent((float) convertMilesToKM(500))
                                .daysPerPercent(18).debugVariance(STANDARD_VARIANCE).build())
                .collect(Collectors.toList()).toArray(new DecayingVehiclePart[LARGE_TRUCK_TIRE_COUNT]);
        DecayingVehiclePart transmission = DecayingVehiclePart.builder().name("transmission").createTS(0).createMileage(0)
                .milesPerPercent((float) convertMilesToKM(2000)).daysPerPercent(24).debugVariance(STANDARD_VARIANCE).build();
        DecayingVehiclePart battery = DecayingVehiclePart.builder().name("battery").createTS(0).createMileage(0)
                .milesPerPercent((float) convertMilesToKM(600)).daysPerPercent(7).debugVariance(STANDARD_VARIANCE).build();
        //huge time number means time is not a factor
        DecayingVehiclePart brakePads = DecayingVehiclePart.builder().name("brake pads").createTS(0).createMileage(0)
                .milesPerPercent((float) convertMilesToKM(150)).daysPerPercent(1_000_000).debugVariance(STANDARD_VARIANCE).build();

        TruckIdentity identity = TruckIdentity.builder().createTS(System.currentTimeMillis())
                .description("midrange truck").make("GMC").model("Wrangler").vin(UUID.randomUUID().toString())
                .year("2021").build();
        TruckPerformance performance = TruckPerformance.builder().horsePower(horsePower).maxGallonsTank(maxGallonsTank)
                .maxSpeed(maxKMPHSpeed).mpg(kmpg).mileage(0.0f).towWeightLbs(towWeightLbs).build();
        List<DecayingVehiclePart> parts = new ArrayList<>(List.of(engine, transmission, battery, brakePads));
        parts.addAll(Arrays.asList(tires));
        TruckOperation operation =
                TruckOperation.builder().allParts(parts).cabSeating(3).currentCoordinate(Coordinate.of(500, 500))
                        .build();
        boolean hasTrailerHitch = true;

        Semi semi = Semi.builder().identity(identity).performance(performance).operation(operation)
                .hasTrailerHitch(hasTrailerHitch).build();
        semi.getAllParts().forEach(part -> part.install(semi));
        return semi;
    }

    public static Semi newCheapLargeSemi(Coordinate location) {
        long createTS = 0;
        float maxGallonsTank = 150;
        float maxKMPHSpeed = (float) convertMilesToKM(60);
        float kmpg = (float) convertMilesToKM(5);
        Dimension flatBed = null;
        RectangularPrism cargoBox = null;
        int towWeightLbs = 25_000;
        int horsePower = 700;

        DecayingVehiclePart engine = DecayingVehiclePart.builder().name("engine").createTS(0).createMileage(0)
                .milesPerPercent((float) convertMilesToKM(6500)).daysPerPercent(55).debugVariance(WILD_VARIANCE).build();
        DecayingVehiclePart[] tires = IntStream.range(0, LARGE_TRUCK_TIRE_COUNT).mapToObj(i ->
                        DecayingVehiclePart.builder().name("tire").createTS(0).createMileage(0)
                                .milesPerPercent((float) convertMilesToKM(400)).daysPerPercent(18)
                                .debugVariance(WILD_VARIANCE).build())
                .collect(Collectors.toList()).toArray(new DecayingVehiclePart[LARGE_TRUCK_TIRE_COUNT]);
        DecayingVehiclePart transmission = DecayingVehiclePart.builder().name("transmission").createTS(0).createMileage(0)
                .milesPerPercent((float) convertMilesToKM(1800)).daysPerPercent(24).debugVariance(WILD_VARIANCE).build();
        DecayingVehiclePart battery = DecayingVehiclePart.builder().name("battery").createTS(0).createMileage(0)
                .milesPerPercent((float) convertMilesToKM(500)).daysPerPercent(7).debugVariance(WILD_VARIANCE).build();
        //huge time number means time is not a factor
        DecayingVehiclePart brakePads = DecayingVehiclePart.builder().name("brake pads").createTS(0).createMileage(0)
                .milesPerPercent((float) convertMilesToKM(120)).daysPerPercent(1_000_000).debugVariance(WILD_VARIANCE).build();

        TruckIdentity identity = TruckIdentity.builder().make("Ford").model("Clunker").year("2021")
                .createTS(System.currentTimeMillis()).vin(UUID.randomUUID().toString()).description("cheap truck").build();
        TruckPerformance performance =
                TruckPerformance.builder().maxGallonsTank(maxGallonsTank).towWeightLbs(towWeightLbs)
                        .horsePower(horsePower).maxSpeed(maxKMPHSpeed).mpg(kmpg).mileage(0.0f).build();
        List<DecayingVehiclePart> parts = new ArrayList<>(List.of(engine, transmission, battery, brakePads));
        parts.addAll(Arrays.asList(tires));
        TruckOperation operation =
                TruckOperation.builder().allParts(parts).currentCoordinate(Coordinate.of(500,
                        500)).cabSeating(3).build();
        boolean hasTrailerHitch = true;

        Semi semi = Semi.builder().identity(identity).performance(performance).operation(operation)
                .hasTrailerHitch(hasTrailerHitch).build();
        semi.getAllParts().forEach(part -> part.install(semi));
        return semi;
    }
}
