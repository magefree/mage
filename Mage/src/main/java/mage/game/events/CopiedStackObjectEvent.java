package mage.game.events;

import mage.MageObject;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class CopiedStackObjectEvent extends GameEvent {

    public CopiedStackObjectEvent(MageObject target, MageObject newCopy, UUID newControllerId) {
        super(GameEvent.EventType.COPIED_STACKOBJECT, newCopy.getId(), null, newControllerId);
        this.setSourceId(target.getId());
    }
}
