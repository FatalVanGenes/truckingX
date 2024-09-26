package commerce.industry.sawmill.worksite;

import commerce.worksites.WorkSite;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
public class SawMill extends WorkSite {

}
