package commerce.industry.financial.model;

import commerce.association.profession.Profession;
import commerce.industry.transport.model.Vehicle;
import commerce.model.Employee;

import java.util.Collection;

public interface BusinessEntity {

    void addAsset(Valuation asset);
    void addAssets(Collection<Valuation> assets);
    String getName();
    boolean hasAsset(Valuation asset);
    void removeAsset(Valuation asset);
    Collection<Employee> getEmployeesByProfession(Class<? extends Profession> clazz);
    Collection<Vehicle> getVehiclesByType(Class<? extends Vehicle> clazz);
}
