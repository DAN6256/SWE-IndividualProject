package strategy;

import core.SmartHomeController;

/**
 * Strategy interface for automation modes (Strategy Pattern)
 */
public interface AutomationStrategy {
    /**
     * Executes the automation mode
     * @param controller the smart home controller
     */
    void execute(SmartHomeController controller);
    
    /**
     * Gets the name of the automation mode
     * @return the mode name
     */
    String getName();
}