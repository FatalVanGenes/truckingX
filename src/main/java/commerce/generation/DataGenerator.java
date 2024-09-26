package commerce.generation;

import org.apache.commons.lang3.tuple.Pair;
import commerce.model.City;
import commerce.model.Coordinate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataGenerator {
    private static final Random random = new Random();

    private DataGenerator() {
        // for lint
    }

    public static Map<City, Collection<City>> connectCliques(Map<Coordinate, Collection<City>> centerPoints) {
        Map<City, Collection<City>> cities = new HashMap<>();
        Map<Coordinate, Collection<Coordinate>> connections = new HashMap<>();

        centerPoints.keySet().forEach(srcCoord -> {
                    Coordinate destCoord = Coordinate.findClosest(centerPoints.keySet(), srcCoord);
                    if (!connections.containsKey(srcCoord)) {
                        connections.put(srcCoord, new HashSet<>());
                    }
                    connections.get(srcCoord).add(destCoord);
                }
        );
        connections.forEach((leftCP, rightCPCollection) ->
                rightCPCollection.forEach(rightCP -> {
                    City leftCity = City.findNearest(centerPoints.get(rightCP), leftCP);
                    City rightCity = City.findNearest(centerPoints.get(leftCP), rightCP);
                    if (!cities.containsKey(leftCity)) {
                        cities.put(leftCity, new HashSet<>());
                    }
                    cities.get(leftCity).add(rightCity);
                })
        );
        return cities;
    }

    public static Map<Coordinate, Collection<City>> getCenterPoints(Collection<Map<City, Integer>> cliqueDepth) {
        return cliqueDepth.stream().map(st -> {
            Set<City> cities = st.keySet();
            Set<Supplier<Coordinate>> cityPoints = cities.stream()
                    .map(city -> (Supplier<Coordinate>) city::getCoordinate)
                    .collect(Collectors.toSet());
            return Pair.of(Coordinate.findCenterPoint(cityPoints), cities);
        }).collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
    }

    public static Collection<Map<City, Integer>> getCliqueDepth(Collection<Collection<City>> cliqueEdges) {
        Collection<Map<City, Integer>> cliqueDepth = new ArrayList<>();
        cliqueEdges.forEach(edge -> {
            // System.out.println(edge);
            Optional<Map<City, Integer>> optMapa = cliqueDepth.stream().filter(mp ->
                    edge.stream().anyMatch(mp::containsKey)).findFirst();
            if (optMapa.isPresent()) {
                Map<City, Integer> found = optMapa.get();
                edge.forEach(key -> {
                    if (found.containsKey(key)) {
                        found.put(key, found.get(key) + 1);
                    } else {
                        found.put(key, 1);
                    }
                });
            } else {
                Map<City, Integer> entry = new HashMap<>();
                edge.forEach(key -> entry.put(key, 1));
                cliqueDepth.add(entry);
            }
        });
        return cliqueDepth;
    }

    public static Collection<Collection<City>> getCliques(Map<City, Collection<City>> nearestNeighbors) {
        Collection<Collection<City>> cliqueEdges = new HashSet<>();
        nearestNeighbors.forEach((city, neighbors) ->
            neighbors.stream().filter(n -> nearestNeighbors.get(n).contains(city)).forEach(n -> cliqueEdges.add(Set.of(city, n)))
        );
        return cliqueEdges;
    }

    public static List<Long> getPopulationSpan(double improvement, long totalCities, long totalPopulation) {
        double coefficient = 1.0;
        List<Double> raw = new ArrayList<>();
        double total = 0;
        for (int x = 0; x < totalCities; x++) {
            raw.add(coefficient);
            total += coefficient;
            coefficient *= (1 + improvement);
        }
        final double finalTotal = total;
        return raw.stream().map(val -> ((val / finalTotal) * totalPopulation)).map(Double::longValue).collect(Collectors.toList());
    }

    public static Map<City, Map<City, Long>> getAllCityDistances(Collection<City> allCities) {
        List<City> remainders = new ArrayList<>(allCities);
        Map<City, Map<City, Long>> allCityDistances = new HashMap<>();

        allCities.forEach(leftCity -> {
            remainders.remove(leftCity);
            remainders.forEach(rightCity -> {
                if (!allCityDistances.containsKey(leftCity)) {
                    allCityDistances.put(leftCity, new HashMap<>());
                } else if (allCityDistances.get(leftCity).containsKey(rightCity)) {
                    return;
                }
                if (!allCityDistances.containsKey(rightCity)) {
                    allCityDistances.put(rightCity, new HashMap<>());
                }
                long distance = leftCity.getDistance(rightCity);
                allCityDistances.get(leftCity).put(rightCity, distance);
                allCityDistances.get(rightCity).put(leftCity, distance);
                // System.out.printf("%s to %s is %d%n", leftCity.getName(), rightCity.getName(), distance);
            });
        });
        return allCityDistances;
    }

    public static Map<City, Collection<City>> findNearestNeighbors(Collection<City> allCities,
                                                                   Map<City, Map<City, Long>> allCityDistances,
                                                                   int depth,
                                                                   long maxX,
                                                                   long maxY) {
        Map<City, Collection<City>> cliquish = new HashMap<>();
        List<City> nearestTemplate = IntStream.range(0, depth)
                .mapToObj(i -> new City("bogus" + i, Coordinate.of(maxX * 2, maxY * 2), -1))
                .collect(Collectors.toList());
        allCities.forEach(city -> {
            List<City> nearestNeighbors = new ArrayList<>(nearestTemplate);
            allCityDistances.get(city).forEach((otherCity, distance) -> {
                while (true) {
                    Optional<City> match = nearestNeighbors.stream()
                            .filter(neighbor -> (city.getDistance(otherCity) < city.getDistance(neighbor)))
                            .findFirst();
                    if (match.isPresent() && !nearestNeighbors.contains(otherCity)) {
                        nearestNeighbors.remove(match.get());
                        nearestNeighbors.add(otherCity);
                    } else {
                        break;
                    }
                }
            });
            if (!cliquish.containsKey(city)) {
                cliquish.put(city, nearestNeighbors);
            }

        });
        return cliquish;
    }

    public static List<City> generateCities(int maxCities, long maxX, long maxY) {
        return IntStream.range(0, maxCities).mapToObj(i -> new City(getUniqueRandomCityName(),
                getSpreadOutCoords(maxX, maxY), -1)).collect(Collectors.toList());
    }

    private static final List<String> NAME_PREFIXES = new ArrayList<>(Set.of("Duckett's", "Barber", "Kent", "Rickets", "Cattle",
            "Sky", "Rusty", "Grassy", "Horse", "Buzzard", "Wagon", "Carter's", "Falling", "Rising", "Dead", "Windy",
            "Dusty", "Sawyer's", "Hanging", "Muddy", "Crow", "Rattler", "Mule", "Indian", "Eagle", "Hawk", "Widow's",
            "Smokey", "Hazy", "Big", "Little", "Wheel", "Bucket", "Greasy", "Despair", "Sorrow", "Salvation"));
    private static final List<String> NAME_SUFFIXES = new ArrayList<>(Set.of("Grove", "Ridge", "Junction", "View", "Pass",
            "Hollow", "Hill", "Estate", "Meadows", "Canyon", "Landing", "Gulch", "Spit", "Hub", "Pitch", "Crossing",
            "Ford", "Heaven", "Valley", "Mountain", "Point", "Reach", "Lookout", "Shack", "Villa", "Gate", "Switch",
            "Peak", "Top", "Hole", "Creek", "Mouth", "Leg"));

    private static final Set<String> TAKEN_PREFIXES = new HashSet<>();
    private static final Set<String> TAKEN_SUFFIXES = new HashSet<>();

    private static final Set<String> CHOSEN_NAMES = new HashSet<>();
    private static final Set<Coordinate> CHOSEN_COORDS = new HashSet<>();

    private static Coordinate getSpreadOutCoords(long maxX, long maxY) {
        Random random = new Random();
        AtomicReference<Coordinate> candidate = new AtomicReference<>();
        do {
            long randX = Math.abs(random.nextLong()) % maxX;
            long randY = Math.abs(random.nextLong()) % maxY;
            candidate.set(Coordinate.of(randX, randY));
        } while (CHOSEN_COORDS.stream().anyMatch(chosen -> candidate.get().getPoint().getDistance(chosen.getPoint()) < 10_000));
        CHOSEN_COORDS.add(candidate.get());
        return candidate.get();
    }

    private static String getUniqueRandomCityName() {
        String name;
        do {
            name = getRandomCityName();
        } while (CHOSEN_NAMES.contains(name));
        CHOSEN_NAMES.add(name);
        return name;
    }

    private static String getRandomCityName() {
        String prefix = getUnique(NAME_PREFIXES, TAKEN_PREFIXES);
        String suffix = getUnique(NAME_SUFFIXES, TAKEN_SUFFIXES);
        return String.format("%s %s", prefix, suffix);
    }

    private static String getUnique(List<String> availableNames, Set<String> takenNames) {
        String choice;
        // list will not contain duplicates
        if (availableNames.size() == takenNames.size()) {
            takenNames.clear();
        }
        do {
            choice = availableNames.get(random.nextInt(availableNames.size()));
        } while (takenNames.contains(choice));
        takenNames.add(choice);
        return choice;
    }
}
