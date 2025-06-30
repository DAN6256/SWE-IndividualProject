package main;

import core.DeviceType;
import core.SmartHomeController;
import decorator.MotionSensorDecorator;
import decorator.TimerDecorator;
import devices.Device;
import devices.Door;
import devices.Light;
import devices.Thermostat;
import observer.ConsoleDisplayObserver;
import observer.SystemLogger;
import strategy.MorningModeStrategy;
import strategy.NightModeStrategy;
import strategy.VacationModeStrategy;

/**
 * Main class to demonstrate the Smart Home Controller system
 */
public class Main {
    public static void main(String[] args) {
        // Get the singleton instance of the controller
        SmartHomeController controller = SmartHomeController.getInstance();
        
        // Register observers
        SystemLogger logger = new SystemLogger();
        controller.addObserver(logger);
        controller.addObserver(new ConsoleDisplayObserver());
        
        // Register automation strategies
        controller.registerAutomationMode("night", new NightModeStrategy());
        controller.registerAutomationMode("vacation", new VacationModeStrategy());
        controller.registerAutomationMode("morning", new MorningModeStrategy());
        
        // Create rooms
        controller.addRoom("Living Room");
        controller.addRoom("Kitchen");
        controller.addRoom("Bedroom");
        controller.addRoom("Bathroom");
        
        // Create devices using the factory pattern
        Device livingRoomLight = controller.createDevice("Living Room", DeviceType.LIGHT, "Main Light");
        Device kitchenLight = controller.createDevice("Kitchen", DeviceType.LIGHT, "Ceiling Light");
        Device bedroomLight = controller.createDevice("Bedroom", DeviceType.LIGHT, "Bedside Lamp");
        
        Device livingRoomThermostat = controller.createDevice("Living Room", DeviceType.THERMOSTAT, "Thermostat");
        Device bedroomThermostat = controller.createDevice("Bedroom", DeviceType.THERMOSTAT, "Thermostat");
        
        Device frontDoor = controller.createDevice("Living Room", DeviceType.DOOR, "Front Door");
        Device backDoor = controller.createDevice("Kitchen", DeviceType.DOOR, "Back Door");
        
        Device securityCamera = controller.createDevice("Living Room", DeviceType.SECURITY_CAMERA, "Security Camera");
        
        System.out.println("\n----- Basic Device Operations -----");
        
        // Demonstrate basic device operations
        livingRoomLight.turnOn();
        ((Light)livingRoomLight).setBrightness(80);
        
        ((Thermostat)livingRoomThermostat).setTemperature(23.5f);
        
        ((Door)frontDoor).unlock();
        
        // Demonstrate room-based control
        System.out.println("\n----- Room-Based Control -----");
        controller.getRoom("Kitchen").turnAllDevicesOn();
        controller.getRoom("Bedroom").turnAllDevicesOn();
        controller.getRoom("Bedroom").turnAllDevicesOff();
        
        // Demonstrate decorators
        System.out.println("\n----- Decorator Pattern Demo -----");
        
        // Add motion sensor to living room light
        Device motionLight = new MotionSensorDecorator(livingRoomLight);
        ((MotionSensorDecorator)motionLight).detectMotion();
        
        // Add timer to kitchen light
        Device timerLight = new TimerDecorator(kitchenLight);
        ((TimerDecorator)timerLight).setTimer(2); // Turn off after 2 minutes
        
        // Combine decorators - a motion-sensing light with a timer
        Device smartLight = new TimerDecorator(new MotionSensorDecorator(bedroomLight));
        ((MotionSensorDecorator)((TimerDecorator)smartLight).getWrappedDevice()).detectMotion();
        ((TimerDecorator)smartLight).setTimer(5); // Turn off after 5 minutes
        
        // Demonstrate automation modes (Strategy pattern)
        System.out.println("\n----- Automation Modes (Strategy Pattern) -----");
        
        controller.setAutomationMode("night");
        controller.executeCurrentMode();
        
        System.out.println("\n----- Switching to Morning Mode -----");
        controller.setAutomationMode("morning");
        controller.executeCurrentMode();
        
        System.out.println("\n----- System logs -----");
        logger.getAllLogs().forEach(System.out::println);
    }
}