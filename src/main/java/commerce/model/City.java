package commerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@Data
public class City {

    private final String name;
    private final Coordinate coordinate;
    @Setter
    private long population;

    public long getDistance(@NonNull City other) {
        return getDistance(other.getCoordinate());
    }

    public long getDistance(@NonNull Coordinate other) {
        return coordinate.getDistance(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(name, city.name) && Objects.equals(coordinate, city.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinate);
    }

    public static City findNearest(@NonNull Collection<City> destCities, @NonNull City source) {
        AtomicReference<City> found = new AtomicReference<>(destCities.iterator().next());
        AtomicReference<Long> closest = new AtomicReference<>(source.getDistance(found.get()));
        destCities.forEach(candidate -> {
            if (!source.equals(candidate)) {
                long d = candidate.getDistance(source);
                if (d < closest.get()) {
                    closest.set(d);
                    found.set(candidate);
                }
            }
        });
        return found.get();
    }

    public static City findNearest(@NonNull Collection<City> destCities, @NonNull Coordinate source) {
        AtomicReference<City> found = new AtomicReference<>(destCities.iterator().next());
        AtomicReference<Long> closest = new AtomicReference<>(found.get().getDistance(source));
        destCities.forEach(candidate -> {
            Coordinate candidateCoordinate = candidate.getCoordinate();
            if (!source.equals(candidateCoordinate)) {
                long d = candidateCoordinate.getDistance(source);
                if (d < closest.get()) {
                    closest.set(d);
                    found.set(candidate);
                }
            }
        });
        return found.get();
    }

    @Data
    public static class CityView {
        private final String name;
        private final long x;
        private final long y;
        private final long population;

        public static CityView from(City city) {
            return new CityView(city.getName(), city.getCoordinate().getX(), city.getCoordinate().getY(), city.getPopulation());
        }
    }
}
