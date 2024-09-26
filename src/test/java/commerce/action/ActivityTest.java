package commerce.action;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityTest {

    @Test
    void basic() {
        Activity activity = new Activity();
        ZonedDateTime dateTime = ZonedDateTime.now().withDayOfMonth(17).withMonth(2).withYear(2023);

        assertThat(activity.daily(dateTime.withHour(5))).isEqualTo("sleeping");
        assertThat(activity.daily(dateTime.withHour(7).withMinute(45))).isEqualTo("commuting to work");
        assertThat(activity.daily(dateTime.withHour(11))).isEqualTo("working");
        assertThat(activity.daily(dateTime.withHour(14))).isEqualTo("working");
        assertThat(activity.daily(dateTime.withHour(17).withMinute(45))).isEqualTo("commuting home");
        assertThat(activity.daily(dateTime.withHour(20))).isEqualTo("sleeping");
    }
}
