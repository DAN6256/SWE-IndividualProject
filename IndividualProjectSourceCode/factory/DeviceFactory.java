package factory;

import core.DeviceType;
import core.Room;
import devices.*;

/**
 * Factory interface for creating devices (Factory Pattern)
 */
public interface DeviceFactory {
    /**
     * Creates a new device
     * @param type the type of device to create
     * @param name the name for the device
     * @param room the room where the device will be located
     * @return the created device
     */
    Device createDevice(DeviceType type, String name, Room room);
}