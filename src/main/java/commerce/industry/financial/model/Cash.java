package commerce.industry.financial.model;

import lombok.Data;

@Data
public class Cash implements Asset {
    private final double amount;

    public static Cash of(double amount) {
        return new Cash(amount);
    }
}
