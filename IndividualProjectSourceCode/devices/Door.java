package devices;

import core.Room;
import core.SmartHomeController;
/**
 * Smart door device
 */
public class Door extends Device {
    private boolean isLocked;
    
    public Door(String name, Room room) {
        super(name, room);
        this.isLocked = true; // Default to locked
    }
    
    public void lock() {
        if (!isLocked) {
            isLocked = true;
            SmartHomeController.getInstance().notifyObservers(
                getDescription() + " locked"
            );
        }
    }
    
    public void unlock() {
        if (isLocked) {
            isLocked = false;
            SmartHomeController.getInstance().notifyObservers(
                getDescription() + " unlocked"
            );
        }
    }
    
    public boolean isLocked() {
        return isLocked;
    }
}
