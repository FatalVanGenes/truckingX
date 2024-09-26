package commerce.industry.transport.model.components;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
public class TruckIdentity {
    private final String make;
    private final String model;
    private final String year;
    private final long createTS;
    private final String vin;
    private final String description;
}
