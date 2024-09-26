package commerce.industry.financial.model;

import commerce.association.profession.Profession;
import commerce.industry.transport.model.Vehicle;
import commerce.model.Employee;
import lombok.Data;
import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Corporation implements BusinessEntity {

    private final Collection<Valuation> assets = new HashSet<>();
    private final Map<String, BusinessUnit> businessUnits = new HashMap<>();
    private final Collection<Employee> boardMembers = new HashSet<>();
    private final Collection<Vehicle> vehicles = new HashSet<>();

    private final String name;

    public Corporation(String name) {
        this.name = name;
        this.businessUnits.put(BusinessUnit.PRIMARY, new BusinessUnit(name + "-HQ"));
    }

    public void addBoardMembers(Collection<Employee> boardMembers) {
        this.boardMembers.addAll(boardMembers);
    }

    @Override
    public boolean hasAsset(Valuation asset) {
        return this.assets.contains(asset);
    }

    public BusinessUnit getPrimaryBusinessUnit() {
        return businessUnits.get(BusinessUnit.PRIMARY);
    }

    @Override
    public void addAsset(Valuation asset) {
        this.assets.add(asset);
    }

    @Override
    public void addAssets(Collection<Valuation> assets) {
        this.assets.addAll(assets);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void removeAsset(Valuation asset) {
        this.assets.remove(asset);
    }

    @Override
    public Collection<Employee> getEmployeesByProfession(Class<? extends Profession> clazz) {
        return boardMembers.stream().filter(emp -> emp.getClass() == clazz).collect(Collectors.toSet());
    }

    public Collection<Vehicle> getVehiclesByType(@NonNull Class<? extends Vehicle> clazz) {
        return vehicles.stream().filter(v -> v.getClass() == clazz).collect(Collectors.toList());
    }

    @Data
    public static class BusinessUnit implements BusinessEntity {
        public static final String PRIMARY = "PRIMARY";
        public static final String SUBSIDIARY = "SUBSIDIARY";

        private final Collection<Valuation> assets = new HashSet<>();
        private final Collection<Employee> employees = new HashSet<>();
        private final Collection<Vehicle> vehicles = new HashSet<>();

        private final String name;


        @Override
        public void addAsset(Valuation asset) {
            this.assets.add(asset);
        }

        @Override
        public void addAssets(Collection<Valuation> assets) {
            this.assets.addAll(assets);
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public boolean hasAsset(Valuation asset) {
            return this.assets.contains(asset);
        }

        @Override
        public void removeAsset(Valuation asset) {
            this.assets.remove(asset);
        }

        public void setPresident(Employee president) {
            employees.add(president);
        }

        @Override
        public Collection<Employee> getEmployeesByProfession(Class<? extends Profession> clazz) {
            return employees.stream().filter(employee -> employee.getProfession().getClass() == clazz).collect(Collectors.toSet());
        }

        @Override
        public Collection<Vehicle> getVehiclesByType(@NonNull Class<? extends Vehicle> clazz) {
            return vehicles.stream().filter(v -> v.getClass() == clazz).collect(Collectors.toList());
        }

    }
}
