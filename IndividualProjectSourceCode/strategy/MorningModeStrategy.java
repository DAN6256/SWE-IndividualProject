package strategy;

import core.Room;
import core.SmartHomeController;
import devices.Device;
import devices.Door;
import devices.Light;
import devices.Thermostat;

/**
 * Morning mode automation strategy (Strategy Pattern)
 */
public class MorningModeStrategy implements AutomationStrategy {
    
    @Override
    public void execute(SmartHomeController controller) {
        // Wake up mode
        for (Room room : controller.getAllRooms()) {
            for (Device device : room.getAllDevices()) {
                // Turn on lights at a low brightness
                if (device instanceof Light) {
                    device.turnOn();
                    ((Light) device).setBrightness(50); // Dim light for morning
                }
                
                // Set thermostats to comfortable temperature
                if (device instanceof Thermostat) {
                    ((Thermostat) device).setTemperature(22.0f); // Comfortable temperature
                }
                
                // Unlock main doors
                if (device instanceof Door && device.getName().contains("Front")) {
                    ((Door) device).unlock();
                }
            }
        }
    }
    
    @Override
    public String getName() {
        return "Morning Mode";
    }
}