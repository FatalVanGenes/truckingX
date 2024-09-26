package commerce.industry.logging.worksite;

import commerce.association.profession.Manager;
import commerce.association.profession.Profession;
import commerce.global.Banks;
import commerce.heavyequipment.WheelLoader;
import commerce.industry.financial.model.BaseLoan;
import commerce.industry.financial.model.Loan;
import commerce.industry.financial.model.Transaction;
import commerce.industry.financial.model.Valuation;
import commerce.industry.financial.worksite.Bank;
import commerce.industry.logging.attachment.LogFork;
import commerce.industry.logging.profession.LumberJack;
import commerce.industry.transport.model.Vehicle;
import commerce.model.Employee;
import commerce.prototypes.trucking.Pickups;
import commerce.prototypes.trucking.Semis;
import commerce.worksites.WorkSite;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
public class LumberCamp extends WorkSite {

    private final Collection<Vehicle> vehicles = new HashSet<>();
    private final Collection<Employee> workers = new HashSet<>();
    private final int initialCrewCount;

    public void staffUp() {
        IntStream.range(0, initialCrewCount * 6).forEach(i -> workers.add(Employee.builder()
                .profession(new LumberJack())
                        .name("guy")
                        .skillz(Map.of(LumberJack.class, 75))
                .build()));
        IntStream.range(0, 2).forEach(i -> workers.add(Employee.builder()
                .profession(new Manager())
                .name("buddy")
                .skillz(Map.of(Manager.class, 91))
                .build()));
        {
            Collection<Valuation> vehicleInitialPurchase = new HashSet<>();
            IntStream.range(0, initialCrewCount).forEach(i -> vehicleInitialPurchase.add(Valuation.of(Pickups.newAveragePickup(this.getLocation()), 40_000)));
            vehicleInitialPurchase.add(Valuation.of(Semis.newAverageLargeSemi(this.getLocation()), 150_000));
            vehicleInitialPurchase.add(Valuation.of(WheelLoader.builder().attachment(new LogFork()).build(), 250_000));
            Loan bankLoan = BaseLoan.builder().durationInMonths(120).initialAnnualInterestRate(4.5).principal(600_000).build();
            Bank lender = Banks.FIRSTNATIONAL;
            Transaction.assign(lender, vehicleInitialPurchase);
            Transaction.purchase(lender, vehicleInitialPurchase, this.getBusinessUnit(), Valuation.of(bankLoan));
            vehicles.addAll(vehicleInitialPurchase.stream().map(Valuation::getItem).map(asset -> (Vehicle)asset).collect(Collectors.toList()));
        }
    }

    public Collection<Vehicle> getVehiclesByType(@NonNull Class<? extends Vehicle> clazz) {
        Collection<Vehicle> level1 = vehicles.stream().filter(v -> v.getClass() == clazz).collect(Collectors.toList());
        level1.addAll(this.getBusinessUnit().getVehiclesByType(clazz));
        return level1;
    }

    public Collection<Employee> getEmployeesByProfession(@NonNull Class<? extends Profession> clazz) {
        return workers.stream().filter(emp -> emp.getSkillz().containsKey(clazz)).collect(Collectors.toList());
    }
}
