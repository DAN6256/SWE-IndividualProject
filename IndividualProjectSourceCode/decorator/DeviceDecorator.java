package decorator;

import core.Room;
import devices.Device;

/**
 * Base decorator class for devices (Decorator Pattern)
 */
public abstract class DeviceDecorator extends Device {
    protected Device wrappedDevice;
    
    public DeviceDecorator(Device device) {
        super(device.getName(), device.getRoom());
        this.wrappedDevice = device;
        this.isOn = device.isOn();
    }
    
    /**
     * Gets the wrapped device
     * @return the wrapped device
     */
    public Device getWrappedDevice() {
        return wrappedDevice;
    }
    
    @Override
    public String getName() {
        return wrappedDevice.getName();
    }
    
    @Override
    public Room getRoom() {
        return wrappedDevice.getRoom();
    }
    
    @Override
    public boolean isOn() {
        return wrappedDevice.isOn();
    }
    
    @Override
    public void turnOn() {
        wrappedDevice.turnOn();
        isOn = wrappedDevice.isOn();
    }
    
    @Override
    public void turnOff() {
        wrappedDevice.turnOff();
        isOn = wrappedDevice.isOn();
    }
    
    @Override
    public String getDescription() {
        return wrappedDevice.getDescription();
    }
}