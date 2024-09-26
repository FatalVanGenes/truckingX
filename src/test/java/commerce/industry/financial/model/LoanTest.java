package commerce.industry.financial.model;

import commerce.industry.financial.model.interestrate.RateType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoanTest {

    @Test
    void test1() {
        BaseLoan loan = BaseLoan.builder().durationInMonths(60).initialAnnualInterestRate(3.95)
                .principal(10_000.00).rateType(RateType.FIXED).build();
        System.out.println(loan);
        // never null
        // assertThat(loan.getPayment()).isNotNull();
        System.out.println(loan.getLastPayment());
    }
}
