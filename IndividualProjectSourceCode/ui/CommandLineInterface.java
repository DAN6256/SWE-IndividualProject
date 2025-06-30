package ui;

import core.DeviceType;
import core.Room;
import core.SmartHomeController;
import devices.Device;
import devices.Door;
import devices.Light;
import devices.Thermostat;
import observer.SystemLogger;

import java.util.List;
import java.util.Scanner;

/**
 * A simple command-line interface for the Smart Home Controller
 */
public class CommandLineInterface {
    private static final String DIVIDER = "----------------------------------------";
    private SmartHomeController controller;
    private SystemLogger logger;
    private Scanner scanner;
    private boolean running;
    
    public CommandLineInterface() {
        controller = SmartHomeController.getInstance();
        logger = new SystemLogger();
        controller.addObserver(logger);
        scanner = new Scanner(System.in);
        running = true;
    }
    
    /**
     * Runs the command-line interface
     */
    public void run() {
        setupInitialSystem();
        
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            handleUserChoice(choice);
            
            System.out.println(DIVIDER);
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
        }
        
        scanner.close();
    }
    
    /**
     * Sets up the initial system with some default rooms and devices
     */
    private void setupInitialSystem() {
        // Add rooms
        controller.addRoom("Living Room");
        controller.addRoom("Kitchen");
        controller.addRoom("Bedroom");
        controller.addRoom("Bathroom");
        
        // Add devices
        controller.createDevice("Living Room", DeviceType.LIGHT, "Main Light");
        controller.createDevice("Living Room", DeviceType.THERMOSTAT, "Thermostat");
        controller.createDevice("Living Room", DeviceType.DOOR, "Front Door");
        
        controller.createDevice("Kitchen", DeviceType.LIGHT, "Ceiling Light");
        controller.createDevice("Kitchen", DeviceType.DOOR, "Back Door");
        
        controller.createDevice("Bedroom", DeviceType.LIGHT, "Bedside Lamp");
        controller.createDevice("Bedroom", DeviceType.THERMOSTAT, "Thermostat");
        
        controller.createDevice("Bathroom", DeviceType.LIGHT, "Mirror Light");
        
        // Register automation modes
        controller.registerAutomationMode("night", new strategy.NightModeStrategy());
        controller.registerAutomationMode("morning", new strategy.MorningModeStrategy());
        controller.registerAutomationMode("vacation", new strategy.VacationModeStrategy());
    }
    
    /**
     * Displays the main menu options
     */
    private void displayMenu() {
        System.out.println("\n" + DIVIDER);
        System.out.println("SMART HOME CONTROLLER");
        System.out.println(DIVIDER);
        System.out.println("1. List Rooms");
        System.out.println("2. List Devices in a Room");
        System.out.println("3. Control Device");
        System.out.println("4. Turn All Devices in Room On/Off");
        System.out.println("5. Activate Automation Mode");
        System.out.println("6. View System Logs");
        System.out.println("7. Add New Room");
        System.out.println("8. Add New Device");
        System.out.println("0. Exit");
        System.out.println(DIVIDER);
    }
    
    /**
     * Handles the user's menu choice
     * @param choice the menu option chosen
     */
    private void handleUserChoice(int choice) {
        switch (choice) {
            case 0:
                running = false;
                System.out.println("Exiting Smart Home Controller. Goodbye!");
                break;
            case 1:
                listRooms();
                break;
            case 2:
                listDevicesInRoom();
                break;
            case 3:
                controlDevice();
                break;
            case 4:
                controlRoomDevices();
                break;
            case 5:
                activateAutomationMode();
                break;
            case 6:
                viewSystemLogs();
                break;
            case 7:
                addNewRoom();
                break;
            case 8:
                addNewDevice();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    /**
     * Lists all rooms in the system
     */
    private void listRooms() {
        System.out.println("\nAVAILABLE ROOMS:");
        List<Room> rooms = controller.getAllRooms();
        
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
            return;
        }
        
        for (int i = 0; i < rooms.size(); i++) {
            System.out.println((i + 1) + ". " + rooms.get(i).getName());
        }
    }
    
    /**
     * Lists all devices in a selected room
     */
    private void listDevicesInRoom() {
        Room room = selectRoom();
        if (room == null) return;
        
        System.out.println("\nDEVICES IN " + room.getName().toUpperCase() + ":");
        List<Device> devices = room.getAllDevices();
        
        if (devices.isEmpty()) {
            System.out.println("No devices found in this room.");
            return;
        }
        
        for (int i = 0; i < devices.size(); i++) {
            Device device = devices.get(i);
            String status = device.isOn() ? "ON" : "OFF";
            System.out.println((i + 1) + ". " + device.getName() + " [" + status + "]");
        }
    }
    
    /**
     * Controls a selected device
     */
    private void controlDevice() {
        Room room = selectRoom();
        if (room == null) return;
        
        List<Device> devices = room.getAllDevices();
        if (devices.isEmpty()) {
            System.out.println("No devices found in this room.");
            return;
        }
        
        System.out.println("\nSELECT DEVICE TO CONTROL:");
        for (int i = 0; i < devices.size(); i++) {
            Device device = devices.get(i);
            String status = device.isOn() ? "ON" : "OFF";
            System.out.println((i + 1) + ". " + device.getName() + " [" + status + "]");
        }
        
        int deviceIndex = getIntInput("Enter device number: ") - 1;
        if (deviceIndex < 0 || deviceIndex >= devices.size()) {
            System.out.println("Invalid device selection.");
            return;
        }
        
        Device selectedDevice = devices.get(deviceIndex);
        System.out.println("\nCONTROL OPTIONS FOR " + selectedDevice.getName() + ":");
        System.out.println("1. Turn On");
        System.out.println("2. Turn Off");
        System.out.println("3. Toggle");
        
        if (selectedDevice instanceof Light) {
            System.out.println("4. Set Brightness");
        } else if (selectedDevice instanceof Thermostat) {
            System.out.println("4. Set Temperature");
        } else if (selectedDevice instanceof Door) {
            System.out.println("4. Lock");
            System.out.println("5. Unlock");
        }
        
        int action = getIntInput("Select action: ");
        
        switch (action) {
            case 1:
                selectedDevice.turnOn();
                break;
            case 2:
                selectedDevice.turnOff();
                break;
            case 3:
                selectedDevice.toggle();
                break;
            case 4:
                if (selectedDevice instanceof Light) {
                    int brightness = getIntInput("Enter brightness (0-100): ");
                    ((Light) selectedDevice).setBrightness(brightness);
                } else if (selectedDevice instanceof Thermostat) {
                    float temp = getFloatInput("Enter temperature: ");
                    ((Thermostat) selectedDevice).setTemperature(temp);
                } else if (selectedDevice instanceof Door) {
                    ((Door) selectedDevice).lock();
                }
                break;
            case 5:
                if (selectedDevice instanceof Door) {
                    ((Door) selectedDevice).unlock();
                }
                break;
            default:
                System.out.println("Invalid action.");
        }
    }
    
    /**
     * Controls all devices in a selected room
     */
    private void controlRoomDevices() {
        Room room = selectRoom();
        if (room == null) return;
        
        System.out.println("\nCONTROL ALL DEVICES IN " + room.getName().toUpperCase() + ":");
        System.out.println("1. Turn All Devices On");
        System.out.println("2. Turn All Devices Off");
        
        int action = getIntInput("Select action: ");
        
        switch (action) {
            case 1:
                room.turnAllDevicesOn();
                break;
            case 2:
                room.turnAllDevicesOff();
                break;
            default:
                System.out.println("Invalid action.");
        }
    }
    
    /**
     * Activates an automation mode
     */
    private void activateAutomationMode() {
        System.out.println("\nSELECT AUTOMATION MODE:");
        System.out.println("1. Night Mode");
        System.out.println("2. Morning Mode");
        System.out.println("3. Vacation Mode");
        
        int mode = getIntInput("Select mode: ");
        
        switch (mode) {
            case 1:
                controller.setAutomationMode("night");
                break;
            case 2:
                controller.setAutomationMode("morning");
                break;
            case 3:
                controller.setAutomationMode("vacation");
                break;
            default:
                System.out.println("Invalid mode selection.");
                return;
        }
        
        controller.executeCurrentMode();
        System.out.println("Automation mode activated successfully.");
    }
    
    /**
     * Views system logs
     */
    private void viewSystemLogs() {
        System.out.println("\nSYSTEM LOGS:");
        List<String> logs = logger.getAllLogs();
        
        if (logs.isEmpty()) {
            System.out.println("No logs found.");
            return;
        }
        
        for (String log : logs) {
            System.out.println(log);
        }
    }
    
    /**
     * Adds a new room to the system
     */
    private void addNewRoom() {
        System.out.print("Enter room name: ");
        String roomName = scanner.nextLine();
        
        if (roomName == null || roomName.trim().isEmpty()) {
            System.out.println("Room name cannot be empty.");
            return;
        }
        
        Room room = controller.addRoom(roomName);
        System.out.println("Room '" + room.getName() + "' added successfully.");
    }
    
    /**
     * Adds a new device to a room
     */
    private void addNewDevice() {
        Room room = selectRoom();
        if (room == null) return;
        
        System.out.println("\nSELECT DEVICE TYPE:");
        System.out.println("1. Light");
        System.out.println("2. Thermostat");
        System.out.println("3. Door");
        System.out.println("4. Security Camera");
        
        int typeChoice = getIntInput("Enter device type: ");
        DeviceType type;
        
        switch (typeChoice) {
            case 1:
                type = DeviceType.LIGHT;
                break;
            case 2:
                type = DeviceType.THERMOSTAT;
                break;
            case 3:
                type = DeviceType.DOOR;
                break;
            case 4:
                type = DeviceType.SECURITY_CAMERA;
                break;
            default:
                System.out.println("Invalid device type.");
                return;
        }
        
        System.out.print("Enter device name: ");
        String deviceName = scanner.nextLine();
        
        if (deviceName == null || deviceName.trim().isEmpty()) {
            System.out.println("Device name cannot be empty.");
            return;
        }
        
        Device device = controller.createDevice(room.getName(), type, deviceName);
        System.out.println("Device '" + device.getName() + "' added to '" + room.getName() + "' successfully.");
    }
    
    /**
     * Prompts the user to select a room
     * @return the selected room or null if invalid
     */
    private Room selectRoom() {
        List<Room> rooms = controller.getAllRooms();
        
        if (rooms.isEmpty()) {
            System.out.println("No rooms found. Please add a room first.");
            return null;
        }
        
        System.out.println("\nSELECT ROOM:");
        for (int i = 0; i < rooms.size(); i++) {
            System.out.println((i + 1) + ". " + rooms.get(i).getName());
        }
        
        int roomIndex = getIntInput("Enter room number: ") - 1;
        if (roomIndex < 0 || roomIndex >= rooms.size()) {
            System.out.println("Invalid room selection.");
            return null;
        }
        
        return rooms.get(roomIndex);
    }
    
    /**
     * Gets integer input from the user
     * @param prompt the prompt to display
     * @return the integer input
     */
    private int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    /**
     * Gets float input from the user
     * @param prompt the prompt to display
     * @return the float input
     */
    private float getFloatInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Float.parseFloat(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    /**
     * Main method to start the CLI
     */
    public static void main(String[] args) {
        CommandLineInterface cli = new CommandLineInterface();
        cli.run();
    }
}