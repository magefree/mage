package mage.game.events;

import mage.abilities.Ability;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author JayDi85
 */
public class StayAttachedEvent extends GameEvent {

    public StayAttachedEvent(UUID targetId, UUID attachmentId, Ability source) {
        super(GameEvent.EventType.STAY_ATTACHED, targetId, null, null);
        this.setSourceId(attachmentId);
    }
}
