package devices;

import core.Room;
import core.SmartHomeController;

/**
 * Smart light device
 */
public class Light extends Device {
    private int brightness;
    
    public Light(String name, Room room) {
        super(name, room);
        this.brightness = 100; // Default to full brightness
    }
    
    public void setBrightness(int level) {
        if (level >= 0 && level <= 100) {
            int oldBrightness = this.brightness;
            this.brightness = level;
            SmartHomeController.getInstance().notifyObservers(
                getDescription() + " brightness changed from " + oldBrightness + "% to " + level + "%"
            );
        }
    }
    
    public int getBrightness() {
        return brightness;
    }
}


