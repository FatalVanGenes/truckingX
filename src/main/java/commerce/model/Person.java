package commerce.model;

import commerce.Global;
import commerce.Strategy;
import commerce.association.profession.Profession;
import commerce.association.profession.Professions;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@SuperBuilder
@Data
public abstract class Person {
    private final int id;
    private final String name;
    private final long birthTS;
    private final Map<Class<? extends Profession>, Integer> skillz;

    private Strategy strategy;
    private Coordinate coordinate;

    Person(int bogusId, @NonNull String name, long birthTS, Map<Class<? extends Profession>, Integer> bogusMap,
           Strategy strategy, Coordinate initialCoordinate) {
        Random random = new Random();
        this.skillz = Professions.ALL.stream().map(pair -> Pair.of(pair, random.nextInt(100)))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        this.id = Global.getInstance().getNewPersonId();
        this.name = name;
        this.birthTS = birthTS;
        this.coordinate = initialCoordinate;
    }

    public int getAge() {
        TimeUnit timeUnit = TimeUnit.DAYS;
        return (int) timeUnit.convert(System.currentTimeMillis() - this.birthTS, TimeUnit.DAYS);
    }

    public Optional<Coordinate> getCoordinate() {
        return Optional.ofNullable(coordinate);
    }

    public Optional<Strategy> getStrategy() {
        return Optional.ofNullable(strategy);
    }
}
