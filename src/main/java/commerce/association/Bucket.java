package commerce.association;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Bucket<S, T> {

    private final Map<S, Set<T>> container;

    private final Object lock;

    public Bucket() {
        container = new HashMap<>();
        lock = new Object();
    }

    public void add(S city, T person) {
        synchronized (lock) {
            if (!container.containsKey(city)) {
                container.put(city, new HashSet<>());
            }
            Set<T> currentSet = container.get(city);
            currentSet.add(person);
        }
    }

    public Set<T> get(S key) {
        synchronized (lock) {
            return new HashSet<>(container.get(key));
        }
    }

    public void remove(S city, T person) {
        synchronized (lock) {
            if (container.containsKey(city)) {
                container.get(city).remove(person);
            }
        }
    }
}
