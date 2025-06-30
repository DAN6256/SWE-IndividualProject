package strategy;

import core.Room;
import core.SmartHomeController;
import devices.Device;
import devices.Door;
import devices.Thermostat;

/**
 * Vacation mode automation strategy (Strategy Pattern)
 */
public class VacationModeStrategy implements AutomationStrategy {
    
    @Override
    public void execute(SmartHomeController controller) {
        // Security-focused mode for when nobody is home
        for (Room room : controller.getAllRooms()) {
            for (Device device : room.getAllDevices()) {
                // Turn lights on and off randomly (not implemented)
                
                // Set thermostats to energy-saving mode
                if (device instanceof Thermostat) {
                    ((Thermostat) device).setTemperature(17.0f); // Lower temperature to save energy
                }
                
                // Ensure all doors are locked
                if (device instanceof Door) {
                    ((Door) device).lock();
                }
            }
        }
    }
    
    @Override
    public String getName() {
        return "Vacation Mode";
    }
}
