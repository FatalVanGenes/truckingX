package commerce.model.impl;

import commerce.industry.transport.model.Truck;
import commerce.model.VehiclePart;
import commerce.industry.transport.model.BaseTruck;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;
import java.util.Random;

@Builder
@Data
public class DecayingVehiclePart implements VehiclePart {
    private final String name;
    private final long createTS;
    private final float createMileage;
    private final float milesPerPercent;
    private final float daysPerPercent;
    private final Double debugVariance;
    private Truck parent;
    private static final Random random = new Random();

    private DecayingVehiclePart(String name, long createTS, float createMileage, float milesPerPercent,
                                float daysPerPercent, Double variance, Truck optParent) {
        this.createMileage = createMileage;
        this.createTS = createTS;
        this.debugVariance = variance;
        this.name = name;
        this.milesPerPercent = vary(milesPerPercent);
        this.daysPerPercent = vary(daysPerPercent);
        this.parent = optParent;
    }

    public Optional<Double> getDebugVariance() {
        return Optional.ofNullable(debugVariance);
    }

    public void install(BaseTruck parent) {
        this.parent = parent;
    }

    public String toString() {
        return status(parent.getMileage(), parent.getCurrentTime());
    }

    public String status(float currentMileage, long currentTS) {
        return String.format("name=%s, mileage_wear=%f, age_wear=%f", name,
                ((currentMileage - createMileage) / milesPerPercent),
                daysSince(currentTS, createTS) / daysPerPercent);
    }

    private static long daysSince(long currentTS, long baseTS) {
        return (currentTS - baseTS) / (1_000 * 60 * 60 * 24);
    }

    private float vary(float baseNumber) {
        if (getDebugVariance().isPresent()) {
            double offset = getDebugVariance().get() * baseNumber;
            double diff = random.nextFloat() * (offset * 2);
            return (float) (baseNumber - offset + diff);
        }
        return baseNumber;
    }
}
