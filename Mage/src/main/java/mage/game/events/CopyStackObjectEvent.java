package mage.game.events;

import mage.MageObject;
import mage.abilities.Ability;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class CopyStackObjectEvent extends GameEvent {

    public CopyStackObjectEvent(Ability source, MageObject targetToCopy, UUID newControllerId, int amount) {
        super(GameEvent.EventType.COPY_STACKOBJECT, targetToCopy.getId(), source, newControllerId, amount, false);
    }
}
