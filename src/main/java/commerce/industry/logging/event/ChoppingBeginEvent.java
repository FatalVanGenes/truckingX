package commerce.industry.logging.event;

import commerce.event.Event;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChoppingBeginEvent implements Event {
}
