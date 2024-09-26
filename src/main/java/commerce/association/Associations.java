package commerce.association;

import commerce.model.City;
import commerce.model.Employee;
import commerce.model.Person;
import commerce.worksites.WorkSite;

public class Associations {

    private static final Associations instance = new Associations();

    public static Associations getInstance() {
        return instance;
    }

    public Bucket<City, Person> citizenry = new Bucket<>();

    public Bucket<WorkSite, Employee> employers = new Bucket<>();

    private Associations() {
        // lint
    }
}
