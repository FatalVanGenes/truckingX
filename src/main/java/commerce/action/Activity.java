package commerce.action;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;

public class Activity {

    public String daily(ZonedDateTime time) {
        DayOfWeek day = time.getDayOfWeek();

        if ((day == DayOfWeek.SATURDAY) || (day == DayOfWeek.SUNDAY)) {
            return "day off";
        }
        int hour = time.getHour();
        int min = time.getMinute();
        if ((hour < 7) || (hour == 7) && (min <= 30)) {
            return "sleeping";
        } else if (hour < 8) {
            return "commuting to work";
        } else if ((hour < 17) || (hour == 17) && (min <= 30)) {
            return "working";
        } else if (hour < 18) {
            return "commuting home";
        }
        return "sleeping";
    }
}
