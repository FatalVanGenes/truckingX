package commerce.industry.logging.processor;

import commerce.industry.common.processor.CommerceProcessor;
import commerce.industry.logging.worksite.LumberCamp;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WoodHarvestingProcessor implements CommerceProcessor {

    private final LumberCamp lumberCamp;

    @Override
    public void init() {
        lumberCamp.staffUp();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void teardown() {

    }

    private enum States {
        INIT,
        PREDAWN,
        START,
        DAY,
        END_OF_DAY,
        NIGHT
    }
}
