package commerce.industry.financial.model.interestrate;

import lombok.Data;

@Data
public class FixedRate {

    private final double interestRate;

    public double getCurrentRate(){
        return getInterestRate();
    }
}
