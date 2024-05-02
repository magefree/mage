package mage.game.events;

import mage.abilities.Ability;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class StayAttachedEvent extends GameEvent {

    // TODO: investigate why source is not used as source for the event?
    public StayAttachedEvent(UUID targetId, UUID attachmentId, Ability source) {
        super(GameEvent.EventType.STAY_ATTACHED, targetId, null, null);
        this.setSourceId(attachmentId);
    }
}
