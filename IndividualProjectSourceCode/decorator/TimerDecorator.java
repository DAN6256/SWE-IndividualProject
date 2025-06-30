package decorator;

import core.SmartHomeController;
import devices.Device;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Adds timer functionality to a device (Decorator Pattern)
 */
public class TimerDecorator extends DeviceDecorator {
    private LocalDateTime scheduledTime;
    private Timer timer;
    
    public TimerDecorator(Device device) {
        super(device);
        this.scheduledTime = null;
        this.timer = new Timer();
    }
    
    /**
     * Sets a timer to turn off the device after specified minutes
     * @param minutes the number of minutes until automatic turn off
     */
    public void setTimer(int minutes) {
        if (timer != null) {
            timer.cancel();
        }
        
        timer = new Timer();
        scheduledTime = LocalDateTime.now().plusMinutes(minutes);
        
        SmartHomeController.getInstance().notifyObservers(
            getDescription() + " set to turn off in " + minutes + " minutes"
        );
        
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                turnOff();
                scheduledTime = null;
            }
        }, minutes * 60 * 1000);
    }
    
    public boolean isTimerActive() {
        return scheduledTime != null && LocalDateTime.now().isBefore(scheduledTime);
    }
    
    public void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = new Timer();
            scheduledTime = null;
            
            SmartHomeController.getInstance().notifyObservers(
                "Timer cancelled for " + getDescription()
            );
        }
    }
    
    @Override
    public String getDescription() {
        return "Timer-Enabled " + super.getDescription();
    }
}