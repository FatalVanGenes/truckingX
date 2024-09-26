package commerce.model;

import commerce.association.profession.Profession;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class Employee extends Person {

    private static long employeeIdNextUp = 1000L;

    private final String employeeId = String.valueOf(employeeIdNextUp++);
    private Profession profession;

}
