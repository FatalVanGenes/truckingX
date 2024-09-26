package commerce.simulation;

import commerce.association.profession.Manager;
import commerce.association.profession.President;
import commerce.global.Banks;
import commerce.heavyequipment.WheelLoader;
import commerce.industry.financial.model.BaseLoan;
import commerce.industry.financial.model.Corporation;
import commerce.industry.financial.model.Loan;
import commerce.industry.financial.model.Valuation;
import commerce.industry.financial.model.interestrate.RateType;
import commerce.industry.financial.worksite.Bank;
import commerce.industry.logging.profession.LumberJack;
import commerce.industry.logging.worksite.LumberCamp;
import commerce.industry.transport.model.PickupTruck;
import commerce.industry.transport.model.Semi;
import commerce.model.Coordinate;
import commerce.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TimberTest {

    @Test
    void testStaffLumberCamp() {
        Coordinate coord = Coordinate.of(100,100);
        Bank bank = Banks.FIRSTNATIONAL;
        Corporation corporation = new Corporation("84 Lumber");
        Employee boardMember1 = Employee.builder().name("Fonda, Peter").coordinate(coord).birthTS(100).build();
                // new PersonImpl("Fonda, Peter", 100, coord);
        Employee boardMember2 = Employee.builder().name("Nolte, Nick").birthTS(100).coordinate(coord).build();
        Employee boardMember3 = Employee.builder().name("Hopper, Dennis").birthTS(100).coordinate(coord).build();
        corporation.addBoardMembers(List.of(boardMember1, boardMember2, boardMember3));

        Corporation.BusinessUnit businessUnit = corporation.getPrimaryBusinessUnit();
        Employee president = Employee.builder().name("Bronson, Charles").birthTS(100).coordinate(coord)
                .profession(new President())
                .build();
        businessUnit.setPresident(president);
        assertThat(businessUnit.getEmployeesByProfession(President.class)).hasSize(1);

        // create a pool of 50 Person
        LumberCamp camp = LumberCamp.builder().businessUnit(businessUnit).initialCrewCount(5).build();
        Loan loan = BaseLoan.builder().durationInMonths(7 * 12).initialAnnualInterestRate(4.5).principal(250_000.00)
                .rateType(RateType.FIXED).build();
        businessUnit.addAsset(Valuation.of(loan));
        businessUnit.addAsset(Valuation.of(camp, 1_000));

        camp.staffUp();
        // camp.wait(HIRING_COMPLETE_EVENT, 10_000);
        assertThat(camp.getEmployeesByProfession(Manager.class)).hasSize(2);
        assertThat(camp.getEmployeesByProfession(LumberJack.class)).hasSize(5 * 6);
        assertThat(camp.getVehiclesByType(PickupTruck.class)).hasSize(5);
        assertThat(camp.getVehiclesByType(Semi.class)).hasSize(1);
        assertThat(camp.getVehiclesByType(WheelLoader.class)).hasSize(1);
    }

    @Test
    void action() {

    }

    @Test
    void testStaffSawMill() {

    }

    @Test
    void testStaffTruckingCompany() {

    }
}
