package commerce.industry.financial.model;

import lombok.Data;

@Data
public class Valuation {
    private final Asset item;
    private final Double price;

    public static Valuation of(Asset item, Double price) {
        return new Valuation(item, price);
    }

    public static Valuation of(Asset item, long price) {
        return new Valuation(item, Double.valueOf(price));
    }

    public static Valuation of(Loan loan) {
        return of(loan, loan.getPrincipal());
    }

    public static Valuation of(Cash cash) {
        return new Valuation(cash, cash.getAmount());
    }
}
