package devices;

import core.Room;
import core.SmartHomeController;
/**
 * Smart thermostat device
 */
public class Thermostat extends Device {
    private float temperature;
    
    public Thermostat(String name, Room room) {
        super(name, room);
        this.temperature = 22.0f; // Default temperature in Celsius
    }
    
    public void setTemperature(float temp) {
        float oldTemp = this.temperature;
        this.temperature = temp;
        SmartHomeController.getInstance().notifyObservers(
            getDescription() + " temperature changed from " + oldTemp + "°C to " + temp + "°C"
        );
    }
    
    public float getTemperature() {
        return temperature;
    }
}
