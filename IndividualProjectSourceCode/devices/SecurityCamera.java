
package devices;

import core.Room;
import core.SmartHomeController;
/**
 * Smart security camera device
 */
public class SecurityCamera extends Device {
    private boolean isRecording;
    
    public SecurityCamera(String name, Room room) {
        super(name, room);
        this.isRecording = false;
    }
    
    public void startRecording() {
        if (!isRecording) {
            isRecording = true;
            SmartHomeController.getInstance().notifyObservers(
                getDescription() + " started recording"
            );
        }
    }
    
    public void stopRecording() {
        if (isRecording) {
            isRecording = false;
            SmartHomeController.getInstance().notifyObservers(
                getDescription() + " stopped recording"
            );
        }
    }
    
    public boolean isRecording() {
        return isRecording;
    }
}