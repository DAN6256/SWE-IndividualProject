package observer;

/**
 * Observer interface for system events (Observer Pattern)
 */
public interface SystemObserver {
    /**
     * Update method called when a system event occurs
     * @param event the event description
     */
    void update(String event);
}