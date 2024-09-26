package commerce.industry.financial.model;

import commerce.prototypes.trucking.Semis;
import commerce.industry.financial.model.interestrate.RateType;
import commerce.industry.financial.model.interestrate.RepaymentFrequency;
import commerce.industry.transport.model.Truck;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static commerce.model.Coordinate.NOWHERE;
import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseTest {

    @Test
    void purchaseTruck() {
        Truck truck = Semis.newAverageLargeSemi(NOWHERE);
        Corporation nissan = new Corporation("Nissan");
        Corporation uhaul = new Corporation("UHaul");

        Valuation sellableAsset = Valuation.of(truck, 25_000);
        nissan.addAsset(sellableAsset);
        assertThat(nissan.hasAsset(sellableAsset)).isTrue();

        Valuation loanAsset = Valuation.of(BaseLoan.builder()
                .allowEarlyPrepayment(true)
                .compoundingPeriod(1)
                .durationInMonths(72)
                .initialAnnualInterestRate(7.25)
                .principal(20_000)
                .rateType(RateType.FIXED)
                .repaymentFrequency(RepaymentFrequency.MONTHLY)
                .build());
        Valuation cashAsset = Valuation.of(Cash.of(5_000));
        Transaction.purchase(nissan, sellableAsset, uhaul, Set.of(loanAsset, cashAsset));

        assertThat(nissan.hasAsset(sellableAsset)).isFalse();
        assertThat(nissan.hasAsset(cashAsset)).isTrue();
        assertThat(nissan.hasAsset(loanAsset)).isTrue();

        assertThat(uhaul.hasAsset(sellableAsset)).isTrue();
    }
}
