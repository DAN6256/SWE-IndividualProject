package observer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Logs system events (Observer Pattern)
 */
public class SystemLogger implements SystemObserver {
    private List<String> logs;
    private DateTimeFormatter formatter;
    
    public SystemLogger() {
        logs = new ArrayList<>();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Override
    public void update(String event) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = timestamp + " - " + event;
        logs.add(logEntry);
        System.out.println("[LOG] " + logEntry);
    }
    
    /**
     * Gets all logs in the system
     * @return list of all log entries
     */
    public List<String> getAllLogs() {
        return new ArrayList<>(logs);
    }
    
    /**
     * Clears all logs
     */
    public void clearLogs() {
        logs.clear();
    }
}

