package commerce.industry.financial.model;

import commerce.industry.financial.model.interestrate.FixedRate;
import org.junit.jupiter.api.Test;

public class InterestRateTest {

    @Test
    void test1() {
        System.out.println(new FixedRate(6.95));
    }
}
