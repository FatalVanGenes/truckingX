package commerce.industry.transport.model.components;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
public class TruckPerformance {
    private final float maxGallonsTank;
    private final int towWeightLbs;

    // volatiles
    private int horsePower;
    private float maxSpeed;
    private float mpg;
    private float mileage;

    public void addMileage(int addedKm) {
        this.mileage += addedKm;
    }
}
