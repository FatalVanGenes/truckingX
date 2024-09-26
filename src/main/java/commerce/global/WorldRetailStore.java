package commerce.global;

import commerce.association.profession.Profession;
import commerce.industry.financial.model.BusinessEntity;
import commerce.industry.financial.model.Valuation;
import commerce.industry.transport.model.Vehicle;
import commerce.model.Employee;

import java.util.Collection;

public class WorldRetailStore implements BusinessEntity {

    private static final WorldRetailStore instance = new WorldRetailStore();

    private WorldRetailStore() {
    }

    public static WorldRetailStore getInstance() {
        return instance;
    }

    @Override
    public void addAsset(Valuation asset) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void addAssets(Collection<Valuation> assets) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getName() {
        return "WorldRetailStore";
    }

    @Override
    public boolean hasAsset(Valuation asset) {
        return true;
    }

    @Override
    public void removeAsset(Valuation asset) {
        // do nothing
    }

    @Override
    public Collection<Employee> getEmployeesByProfession(Class<? extends Profession> clazz) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Collection<Vehicle> getVehiclesByType(Class<? extends Vehicle> clazz) {
        throw new RuntimeException("Not implemented");
    }
}
