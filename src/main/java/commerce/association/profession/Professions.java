package commerce.association.profession;

import commerce.industry.logging.profession.LumberJack;
import commerce.industry.sawmill.profession.WoodPlaner;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class Professions {

    public static final Set<Class<? extends Profession>> ALL = Set.of(Driver.class, LumberJack.class, Manager.class,
            President.class, WoodPlaner.class);
}
