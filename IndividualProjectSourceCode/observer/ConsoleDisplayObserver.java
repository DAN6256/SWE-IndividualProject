package observer;

/**
 * Displays system events to the console (Observer Pattern)
 */
public class ConsoleDisplayObserver implements SystemObserver {
    
    @Override
    public void update(String event) {
        System.out.println("[DISPLAY] " + event);
    }
}