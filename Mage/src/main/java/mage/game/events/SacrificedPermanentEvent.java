package mage.game.events;

import mage.abilities.Ability;

import java.util.UUID;

/**
 * @author Grath
 */
public class SacrificedPermanentEvent extends GameEvent {
    public SacrificedPermanentEvent(UUID targetId, Ability source, UUID playerId) {
        super(EventType.SACRIFICED_PERMANENT, targetId, source, playerId);
    }
}
