package mage.game.events;

import java.util.UUID;
import mage.abilities.Ability;

/**
 * @author oscscull
 */
public class UnlockDoorEvent extends GameEvent {

    public UnlockDoorEvent(UUID sourceId, Ability source, UUID controllerId, boolean isLeftHalf) {
        super(GameEvent.EventType.UNLOCK_DOOR, sourceId, source, controllerId);
        this.flag = isLeftHalf; // Use flag to indicate which half
    }

    public boolean isLeftHalf() {
        return flag;
    }

    public boolean isRightHalf() {
        return !flag;
    }
}
