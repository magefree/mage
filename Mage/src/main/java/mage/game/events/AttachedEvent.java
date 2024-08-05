package mage.game.events;

import mage.abilities.Ability;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class AttachedEvent extends GameEvent {

    // TODO: investigate why source is provided but not used at all?
    public AttachedEvent(UUID targetPermanentId, Permanent attachment, Ability source) {
        super(GameEvent.EventType.ATTACHED, targetPermanentId, null, attachment.getControllerId());
        this.setSourceId(attachment.getId());
    }
}
