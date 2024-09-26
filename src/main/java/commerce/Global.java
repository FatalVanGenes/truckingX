package commerce;

public class Global {

    private static final Global instance = new Global();

    private long currentTS = 0;
    private int nextPersonId = 1_000;
    private final Object lock = new Object();

    public static Global getInstance() {
        return instance;
    }

    private Global() {
        // lint
    }

    public void setCurrentTS(long currentTS) {
        this.currentTS = currentTS;
    }

    public long getCurrentTS() {
        return currentTS;
    }

    public int getNewPersonId() {
        synchronized (lock) {
            return nextPersonId++;
        }
    }
}
