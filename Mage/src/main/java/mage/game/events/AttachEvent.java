package mage.game.events;

import mage.abilities.Ability;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class AttachEvent extends GameEvent {

    public AttachEvent(UUID targetPermanentId, Permanent attachment, Ability source) {
        super(GameEvent.EventType.ATTACH, targetPermanentId, null, attachment.getControllerId());
        this.setSourceId(attachment.getId());
    }
}
