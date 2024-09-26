package commerce.generation;

import commerce.model.City;
import commerce.model.Coordinate;
import commerce.render.Renderer;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static commerce.generation.DataGenerator.connectCliques;
import static commerce.generation.DataGenerator.findNearestNeighbors;
import static commerce.generation.DataGenerator.generateCities;
import static commerce.generation.DataGenerator.getAllCityDistances;
import static commerce.generation.DataGenerator.getCenterPoints;
import static commerce.generation.DataGenerator.getCliqueDepth;
import static commerce.generation.DataGenerator.getCliques;
import static commerce.render.Renderer.renderCenterPointEdgeData;

class GenerationTest {

    public static final int MAX_X = 1_000_000;
    public static final int MAX_Y = 1_000_000;

    public static final int MAX_CITIES = 20;

    @Test
    void getSpread() {
        List<Long> orderedPopulations = DataGenerator.getPopulationSpan(0.19, 20, 19000);
        orderedPopulations.forEach(System.out::println);
    }

    @Test
    void getOrderedCities() {
        List<City> allCities = generateCities(MAX_CITIES, MAX_X, MAX_Y);
        Map<City, Map<City, Long>> allCityDistances = getAllCityDistances(allCities);
        Map<City, Collection<City>> rawCliquish = findNearestNeighbors(allCities, allCityDistances, 7, MAX_X, MAX_Y);
        String s = "";
        Map<City, Long> cityDistances = rawCliquish.entrySet().stream().collect(Collectors.toMap(ent -> ent.getKey(), ent ->
                ent.getValue().stream().map(dest -> dest.getDistance(ent.getKey())).reduce(Long::sum).get()
        ));
        List<Long> orderedPopulations = DataGenerator.getPopulationSpan(0.19, 20, 19000);
        Collections.reverse(orderedPopulations);
        Map<City, Long> cityPopulations = new HashMap<>();
        cityDistances.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(ent -> cityPopulations.put(ent.getKey(), orderedPopulations.get(cityPopulations.size())));
        cityPopulations.forEach((k,v) -> System.out.println(k + " " + v));
    }

    @Test
    void first() {

        List<City> allCities = generateCities(MAX_CITIES, MAX_X, MAX_Y);
        // allCities.forEach(System.out::println);
        Map<City, Map<City, Long>> allCityDistances = getAllCityDistances(allCities);
        {
            Map<City, Collection<City>> rawCliquish = findNearestNeighbors(allCities, allCityDistances, 7, MAX_X, MAX_Y);
            Map<City, Long> cityDistances = rawCliquish.entrySet().stream().collect(Collectors.toMap(ent -> ent.getKey(), ent ->
                    ent.getValue().stream().map(dest -> dest.getDistance(ent.getKey())).reduce(Long::sum).get()
            ));
            List<Long> orderedPopulations = DataGenerator.getPopulationSpan(0.19, MAX_CITIES, 19000);
            Collections.reverse(orderedPopulations);
            Map<City, Long> cityPopulations = new HashMap<>();
            cityDistances.entrySet().stream().sorted(Map.Entry.comparingByValue())
                    .forEach(ent -> cityPopulations.put(ent.getKey(), orderedPopulations.get(cityPopulations.size())));
            // cityPopulations.forEach((k,v) -> System.out.println(k + " " + v));
            cityPopulations.forEach((k,v) -> k.setPopulation(v));
/*
            cityDistances.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(ent ->
                    // cityPopulations.put(ent.getKey(), orderedPopulations.get(cityPopulations.size())
                            allCities.get(allCities.indexOf(ent.getKey())).setPopulation(ent.getValue()));
                    // ));
 */
        }
        System.out.println(Renderer.renderPointData(allCities));

        Map<City, Collection<City>> rawCliquish = findNearestNeighbors(allCities, allCityDistances, 2, MAX_X,
                MAX_Y);

        Collection<Collection<City>> cliqueEdges = getCliques(rawCliquish);

        Collection<Map<City, Integer>> cliqueDepth = getCliqueDepth(cliqueEdges);

        System.out.println(Renderer.renderEdgeData(cliqueDepth));

        System.out.println(Renderer.renderSupplementalEdgeData(allCities, cliqueEdges));

        Map<Coordinate, Collection<City>> centerPoints = getCenterPoints(cliqueDepth);

        System.out.println(Renderer.renderCenterPoints(centerPoints.keySet()));

        Map<City, Collection<City>> cityLinks = connectCliques(centerPoints);

        System.out.println(renderCenterPointEdgeData(cityLinks));
    }
}
