package commerce.render;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import commerce.model.City;
import commerce.model.Coordinate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Renderer {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create(); // .disableHtmlEscaping().create();

    public static String renderCenterPoints(Collection<Coordinate> points) {
        return String.format("var centerPoints = %s;%n", gson.toJson(points.stream()
                .map(Coordinate.CoordinateView::from)
                .collect(Collectors.toList())));
    }

    public static String renderCenterPointEdgeData(Map<City, Collection<City>> cityLinks) {
        List<List<Coordinate.CoordinateView>> outerValues = new ArrayList<>();
        cityLinks.forEach((left, rightCollection) ->
                rightCollection.forEach(right ->
                        outerValues.add(List.of(Coordinate.CoordinateView.from(left.getCoordinate()),
                                Coordinate.CoordinateView.from(right.getCoordinate())))
                ));
        return String.format("var cpEdges = %s;%n", gson.toJson(outerValues));
    }

    public static String renderPointData(List<City> allCities) {
        return String.format("var pointData = %s;%n", gson.toJson(allCities.stream().map(City.CityView::from)
                .collect(Collectors.toList())));
    }

    public static String renderSupplementalEdgeData(List<City> allCities, Collection<Collection<City>> cliqueEdges) {
        List<City> remainders = new ArrayList<>(allCities);
        cliqueEdges.forEach(st -> st.forEach(remainders::remove));
        // used Set.of before which can produce duplicate element Exception
        Collection<Collection<City>> extra = remainders.stream()
                .map(city -> List.of(city, City.findNearest(allCities, city))).collect(Collectors.toList());
        List<List<Coordinate.CoordinateView>> outerValues = extra.stream().map(cities -> cities.stream()
                        .map(City::getCoordinate).map(Coordinate.CoordinateView::from).collect(Collectors.toList()))
                .collect(Collectors.toList());
        return String.format("var humanEdges = %s;%n", gson.toJson(outerValues));
    }

    public static String renderEdgeData(Collection<Map<City, Integer>> cliqueDepth) {
        List<List<Coordinate.CoordinateView>> outerValues = cliqueDepth.stream()
                .map(cityMap -> cityMap.keySet().stream().map(City::getCoordinate).map(Coordinate.CoordinateView::from)
                        .collect(Collectors.toList())).collect(Collectors.toList());
        return String.format("var edges = %s;%n", gson.toJson(outerValues));
    }
}
