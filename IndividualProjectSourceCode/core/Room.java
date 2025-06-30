package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import devices.Device;

/**
 * Room class that manages a collection of devices
 */
public class Room {
    private String name;
    private Map<String, Device> devices;
    
    /**
     * Creates a new Room with the given name
     * @param name the room name
     */
    public Room(String name) {
        this.name = name;
        this.devices = new HashMap<>();
    }
    
    /**
     * Gets the room name
     * @return the room name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Adds a device to the room
     * @param device the device to add
     */
    public void addDevice(Device device) {
        devices.put(device.getName(), device);
    }
    
    /**
     * Gets a device by name
     * @param name the device name
     * @return the device or null if not found
     */
    public Device getDevice(String name) {
        return devices.get(name);
    }
    
    /**
     * Gets all devices in the room
     * @return list of all devices
     */
    public List<Device> getAllDevices() {
        return new ArrayList<>(devices.values());
    }
    
    /**
     * Turns on all devices in the room
     */
    public void turnAllDevicesOn() {
        for (Device device : devices.values()) {
            device.turnOn();
        }
    }
    
    /**
     * Turns off all devices in the room
     */
    public void turnAllDevicesOff() {
        for (Device device : devices.values()) {
            device.turnOff();
        }
    }
    
    @Override
    public String toString() {
        return name;
    }
}