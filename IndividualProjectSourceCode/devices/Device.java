package devices;

import core.Room;
import core.SmartHomeController;

/**
 * Abstract base class for all smart home devices
 */
public abstract class Device {
    protected String name;
    protected boolean isOn;
    protected Room room;
    
    /**
     * Creates a new device
     * @param name the device name
     * @param room the room where the device is located
     */
    public Device(String name, Room room) {
        this.name = name;
        this.room = room;
        this.isOn = false;
    }
    
    /**
     * Gets the device name
     * @return the device name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the room where the device is located
     * @return the room
     */
    public Room getRoom() {
        return room;
    }
    
    /**
     * Checks if the device is on
     * @return true if the device is on, false otherwise
     */
    public boolean isOn() {
        return isOn;
    }
    
    /**
     * Turns on the device
     */
    public void turnOn() {
        if (!isOn) {
            isOn = true;
            SmartHomeController.getInstance().notifyObservers(
                getDescription() + " turned ON"
            );
        }
    }
    
    /**
     * Turns off the device
     */
    public void turnOff() {
        if (isOn) {
            isOn = false;
            SmartHomeController.getInstance().notifyObservers(
                getDescription() + " turned OFF"
            );
        }
    }
    
    /**
     * Toggles the device state (on/off)
     */
    public void toggle() {
        if (isOn) {
            turnOff();
        } else {
            turnOn();
        }
    }
    
    /**
     * Gets a description of the device
     * @return a string describing the device
     */
    public String getDescription() {
        return room.getName() + " " + name;
    }
}