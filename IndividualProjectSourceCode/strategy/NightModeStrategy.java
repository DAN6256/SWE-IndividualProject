package strategy;

import core.Room;
import core.SmartHomeController;
import devices.Device;
import devices.Door;
import devices.Light;
import devices.Thermostat;


/**
 * Night mode automation strategy (Strategy Pattern)
 */
public class NightModeStrategy implements AutomationStrategy {
    
    @Override
    public void execute(SmartHomeController controller) {
        // Turn off all lights
        for (Room room : controller.getAllRooms()) {
            for (Device device : room.getAllDevices()) {
                if (device instanceof Light) {
                    device.turnOff();
                }
                
                // Set thermostats to night temperature
                if (device instanceof Thermostat) {
                    ((Thermostat) device).setTemperature(19.0f); // Lower temperature for night
                }
                
                // Lock all doors
                if (device instanceof Door) {
                    ((Door) device).lock();
                }
            }
        }
    }
    
    @Override
    public String getName() {
        return "Night Mode";
    }
}

