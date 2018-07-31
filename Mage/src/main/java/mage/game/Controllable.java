

package mage.game;

import java.util.UUID;

/**
 * @author magenoxx_at_gmail.com
 */
public interface Controllable {
    UUID getControllerId();
    UUID getId();

    default boolean isControlledBy(UUID controllerID){
        return getControllerId().equals(controllerID);
    }
}
