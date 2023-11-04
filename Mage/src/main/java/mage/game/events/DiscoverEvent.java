package mage.game.events;

import mage.abilities.Ability;

import java.util.UUID;

/**
 * @author Susucr
 */
public class DiscoverEvent extends GameEvent {
    public DiscoverEvent(Ability source, UUID playerId, int amount) {
        super(EventType.DISCOVER, null, source, playerId, amount, false);
    }
}
