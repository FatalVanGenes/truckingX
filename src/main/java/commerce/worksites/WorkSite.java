package commerce.worksites;

import commerce.industry.financial.model.Asset;
import commerce.model.Coordinate;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import static commerce.industry.financial.model.Corporation.BusinessUnit;


@Data
@SuperBuilder
public abstract class WorkSite implements Asset {

    private final Coordinate location;
    private final BusinessUnit businessUnit;

}
