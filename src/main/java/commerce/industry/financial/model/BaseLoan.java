package commerce.industry.financial.model;

import commerce.industry.financial.model.interestrate.RateType;
import commerce.industry.financial.model.interestrate.RepaymentFrequency;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.Optional;

@Data
public class BaseLoan implements Loan {

    private final double principal;
    private final double payment;
    private final double initialAnnualInterestRate;
    private final long compoundingPeriod;
    private final RepaymentFrequency repaymentFrequency;
    private final long durationInMonths;
    private final long totalPayments;
    private final RateType rateType;
    private final boolean allowEarlyPrepayment;

    private int paymentsMade = 0;
    private ZonedDateTime closedTS;
    private ZonedDateTime estimatedLastPaymentDate;
    private double currentBalance;
    private double currentAnnualInterestRate;

    private double projectedLoanValue;

    @Builder
    protected BaseLoan(double principal, double payment, double initialAnnualInterestRate, long compoundingPeriod,
                       RepaymentFrequency repaymentFrequency, long durationInMonths, RateType rateType,
                       boolean allowEarlyPrepayment, long totalPayments) {
        this.principal = principal;
        this.initialAnnualInterestRate = initialAnnualInterestRate;
        this.compoundingPeriod = compoundingPeriod;
        this.repaymentFrequency = (repaymentFrequency != null) ? repaymentFrequency : RepaymentFrequency.MONTHLY;
        this.durationInMonths = durationInMonths;
        this.rateType = (rateType != null) ? rateType : RateType.FIXED;
        this.allowEarlyPrepayment = allowEarlyPrepayment;
        this.currentBalance = principal;
        this.currentAnnualInterestRate = initialAnnualInterestRate;
        this.closedTS = ZonedDateTime.now();
        this.estimatedLastPaymentDate = closedTS.plusMonths(durationInMonths);
        this.totalPayments = (totalPayments > 0) ? totalPayments : durationInMonths;
        if (payment == 0.0) {
            double rate = getCurrentPeriodRate();
            double p = ((principal * rate) / (1 - Math.pow(1 + rate, 0 - durationInMonths)));
            this.payment = new BigDecimal(p).setScale(2, RoundingMode.HALF_UP).doubleValue();
        } else {
            this.payment = payment;
        }
        this.projectedLoanValue = new BigDecimal(this.totalPayments * this.payment).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public Optional<ZonedDateTime> getClosedTS() {
        return Optional.ofNullable(closedTS);
    }

    public Optional<ZonedDateTime> getEstimatedLastPaymentDate() {
        return Optional.ofNullable(estimatedLastPaymentDate);
    }

    public double getLastPayment() {
        return new BigDecimal(projectedLoanValue - ((totalPayments - paymentsMade - 1) * payment)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public boolean isClosed() {
        return closedTS != null;
    }

    double getCurrentPeriodRate() {
        return currentAnnualInterestRate / 100 / 12;
    }

    public double getCurrentPayment() {
        return payment;
    }

}
