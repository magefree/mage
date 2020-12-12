package mage.game.events;

import mage.abilities.Ability;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class UnattachEvent extends GameEvent {

    public UnattachEvent(UUID targetId, UUID attachmentId, Permanent attachment, Ability source) {
        super(GameEvent.EventType.UNATTACH, targetId, null, attachment == null ? null : attachment.getControllerId());
        this.setSourceId(attachmentId);
    }
}
