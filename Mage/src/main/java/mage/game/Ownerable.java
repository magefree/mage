package mage.game;

import java.util.UUID;

/**
 * @author JayDi85
 */
public interface Ownerable extends ControllableOrOwnerable {
    UUID getOwnerId();
}
