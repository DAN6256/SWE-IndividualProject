package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import devices.Device;
import factory.DeviceFactory;
import factory.ConcreteDeviceFactory;
import observer.SystemObserver;
import strategy.AutomationStrategy;

/**
 * SmartHomeController (Singleton Pattern)
 * Central controller for the smart home system
 */
public class SmartHomeController {
    // Singleton instance
    private static SmartHomeController instance;
    
    // Collections
    private Map<String, Room> rooms;
    private Map<String, AutomationStrategy> automationModes;
    private List<SystemObserver> observers;
    
    // Components
    private DeviceFactory deviceFactory;
    private AutomationStrategy currentMode;
    
    /**
     * Private constructor (part of Singleton pattern)
     */
    private SmartHomeController() {
        rooms = new HashMap<>();
        automationModes = new HashMap<>();
        observers = new ArrayList<>();
        deviceFactory = new ConcreteDeviceFactory();
    }
    
    /**
     * Gets the singleton instance of SmartHomeController
     * @return the singleton instance
     */
    public static SmartHomeController getInstance() {
        if (instance == null) {
            instance = new SmartHomeController();
        }
        return instance;
    }
    
    /**
     * Adds a new room to the system
     * @param name the room name
     * @return the newly created room
     */
    public Room addRoom(String name) {
        Room room = new Room(name);
        rooms.put(name, room);
        notifyObservers("Room added: " + name);
        return room;
    }
    
    /**
     * Gets a room by name
     * @param name the room name
     * @return the room or null if not found
     */
    public Room getRoom(String name) {
        return rooms.get(name);
    }
    
    /**
     * Gets all rooms in the system
     * @return list of all rooms
     */
    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms.values());
    }
    
    /**
     * Registers an observer to receive system notifications
     * @param observer the observer to add
     */
    public void addObserver(SystemObserver observer) {
        observers.add(observer);
    }
    
    /**
     * Removes an observer from the system
     * @param observer the observer to remove
     */
    public void removeObserver(SystemObserver observer) {
        observers.remove(observer);
    }
    
    /**
     * Notifies all observers about a system event
     * @param event the event message
     */
    public void notifyObservers(String event) {
        for (SystemObserver observer : observers) {
            observer.update(event);
        }
    }
    
    /**
     * Registers an automation mode strategy
     * @param name the mode name
     * @param strategy the automation strategy
     */
    public void registerAutomationMode(String name, AutomationStrategy strategy) {
        automationModes.put(name, strategy);
    }
    
    /**
     * Sets the current automation mode
     * @param modeName the name of the mode to set
     */
    public void setAutomationMode(String modeName) {
        AutomationStrategy strategy = automationModes.get(modeName);
        if (strategy != null) {
            currentMode = strategy;
            notifyObservers("Automation mode changed to: " + modeName);
        }
    }
    
    /**
     * Executes the current automation mode
     */
    public void executeCurrentMode() {
        if (currentMode != null) {
            currentMode.execute(this);
            notifyObservers("Executed automation mode: " + currentMode.getName());
        }
    }
    
    /**
     * Creates a new device using the factory pattern
     * @param roomName the name of the room for the device
     * @param type the type of device to create
     * @param name the name for the device
     * @return the created device
     */
    public Device createDevice(String roomName, DeviceType type, String name) {
        Room room = getRoom(roomName);
        if (room == null) {
            room = addRoom(roomName);
        }
        
        Device device = deviceFactory.createDevice(type, name, room);
        room.addDevice(device);
        notifyObservers("Device created: " + name + " (" + type + ") in " + roomName);
        return device;
    }
}