package decorator;

import core.SmartHomeController;
import devices.Device;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Adds motion detection to a device (Decorator Pattern)
 */
public class MotionSensorDecorator extends DeviceDecorator {
    private boolean motionDetected;
    
    public MotionSensorDecorator(Device device) {
        super(device);
        this.motionDetected = false;
    }
    
    /**
     * Simulates motion being detected
     */
    public void detectMotion() {
        motionDetected = true;
        SmartHomeController.getInstance().notifyObservers(
            "Motion detected near " + getDescription()
        );
        
        // Auto-turn on when motion is detected
        if (!isOn()) {
            turnOn();
        }
        
        // Reset motion detection after 5 minutes
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                motionDetected = false;
            }
        }, 5 * 60 * 1000);
    }
    
    public boolean isMotionDetected() {
        return motionDetected;
    }
    
    @Override
    public void turnOn() {
        super.turnOn();
        SmartHomeController.getInstance().notifyObservers(
            "Motion sensor for " + getDescription() + " activated"
        );
    }
    
    @Override
    public void turnOff() {
        super.turnOff();
        SmartHomeController.getInstance().notifyObservers(
            "Motion sensor for " + getDescription() + " deactivated"
        );
    }
    
    @Override
    public String getDescription() {
        return "Motion-Sensing " + super.getDescription();
    }
}
