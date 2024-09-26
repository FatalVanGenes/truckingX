package commerce.industry.financial.worksite;

import commerce.association.profession.Profession;
import commerce.industry.financial.model.BankAccount;
import commerce.industry.financial.model.BusinessEntity;
import commerce.industry.financial.model.Valuation;
import commerce.industry.transport.model.Vehicle;
import commerce.model.Employee;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
@Data
public class Bank implements BusinessEntity {
    private final String name;
    private final Map<BusinessEntity, BankAccount> accounts = new HashMap<>();
    private final Collection<Valuation> assets = new ArrayList<>();
    private final Collection<Employee> workers = new HashSet<>();

    @Override
    public void addAsset(Valuation asset) {
        assets.add(asset);
    }

    @Override
    public void addAssets(Collection<Valuation> assets) {
        this.assets.addAll(assets);
    }

    @Override
    public boolean hasAsset(Valuation asset) {
        return assets.contains(asset);
    }

    @Override
    public void removeAsset(Valuation asset) {
        assets.remove(asset);
    }

    @Override
    public Collection<Employee> getEmployeesByProfession(Class<? extends Profession> clazz) {
        return workers.stream().filter(emp -> emp.getSkillz().containsKey(clazz)).collect(Collectors.toList());
    }

    @Override
    public Collection<Vehicle> getVehiclesByType(Class<? extends Vehicle> clazz) {
        throw new RuntimeException("not implemented");
    }
}
