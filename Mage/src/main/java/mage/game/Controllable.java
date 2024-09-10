package mage.game;

import java.util.UUID;

/**
 * @author magenoxx_at_gmail.com
 */
public interface Controllable extends ControllableOrOwnerable {

    UUID getControllerId();

    default boolean isControlledBy(UUID controllerID) {
        if (getControllerId() == null) {
            return false;
        }
        return getControllerId().equals(controllerID);
    }
}
