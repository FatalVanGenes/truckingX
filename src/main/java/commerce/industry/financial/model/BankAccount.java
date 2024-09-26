package commerce.industry.financial.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Data
public class BankAccount {
    private final BusinessEntity owner;
    @Setter
    private double balance;

}
