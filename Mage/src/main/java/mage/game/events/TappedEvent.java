package mage.game.events;

import mage.abilities.Ability;

import java.util.UUID;

/**
 * @author Susucr
 */
public class TappedEvent extends GameEvent {
    public TappedEvent(UUID targetId, Ability source, UUID playerId, boolean forCombat) {
        super(EventType.TAPPED, targetId, source, playerId, 0, forCombat);
    }
}
