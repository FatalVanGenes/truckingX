package commerce.heavyequipment;

import commerce.industry.financial.model.Asset;
import commerce.industry.transport.model.Vehicle;
import commerce.model.Attachment;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FrontEndLoader implements Asset, Vehicle {
    private volatile Attachment attachment;

}
