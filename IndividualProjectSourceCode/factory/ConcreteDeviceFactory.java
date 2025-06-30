package factory;

import core.DeviceType;
import core.Room;
import devices.*;

/**
 * Concrete implementation of the DeviceFactory (Factory Pattern)
 */
public class ConcreteDeviceFactory implements DeviceFactory {
    
    @Override
    public Device createDevice(DeviceType type, String name, Room room) {
        switch (type) {
            case LIGHT:
                return new Light(name, room);
            case THERMOSTAT:
                return new Thermostat(name, room);
            case DOOR:
                return new Door(name, room);
            case SECURITY_CAMERA:
                return new SecurityCamera(name, room);
            default:
                throw new IllegalArgumentException("Unknown device type: " + type);
        }
    }
}