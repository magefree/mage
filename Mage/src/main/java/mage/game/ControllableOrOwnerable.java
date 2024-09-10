package mage.game;

import mage.MageItem;

import java.util.UUID;

/**
 * @author JayDi85
 */
public interface ControllableOrOwnerable extends MageItem {

    /**
     * @return the controller if available otherwise the owner
     */
    UUID getControllerOrOwnerId();
}
