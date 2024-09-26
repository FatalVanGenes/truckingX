package commerce.event;

import java.util.Collection;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EventListeners {

    private static final GlobalEventListener instance = new GlobalEventListener();

    public static GlobalEventListener getInstance() {
        return instance;
    }

    @FunctionalInterface
    public interface Callback {
        void eventHandler(Event event);
    }

    public static class GlobalEventListener implements EventListener {

        private final Map<Event, Set<Callback>> subscribers = new ConcurrentHashMap<>();

        public void publish(Event event) {
            subscribers.forEach((evt,callables) -> callables.forEach(callable -> callable.eventHandler(evt)));
        }

        public void subscribe(Callback caller, Collection<Event> eventsOfInterest) {
            eventsOfInterest.forEach(evt -> subscribers.computeIfAbsent(evt, k -> new HashSet<>()).add(caller));
        }

        public void unsubscribe(Callback caller, Collection<Event> eventsOfInterest) {
            eventsOfInterest.forEach(subscribers::remove);
        }
    }
}
