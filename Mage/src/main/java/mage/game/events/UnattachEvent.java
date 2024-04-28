package mage.game.events;

import mage.abilities.Ability;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class UnattachEvent extends GameEvent {

    // TODO: investigate why source is not used as source for the event?
    public UnattachEvent(UUID targetId, UUID attachmentId, Permanent attachment, Ability source) {
        super(GameEvent.EventType.UNATTACH, targetId, attachmentId, attachment == null ? null : attachment.getControllerId());
    }
}
