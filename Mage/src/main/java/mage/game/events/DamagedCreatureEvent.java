

package mage.game.events;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DamagedCreatureEvent extends DamagedEvent {

    public DamagedCreatureEvent(UUID targetId, UUID attackerId, UUID playerId, int amount, boolean combat) {
        super(GameEvent.EventType.DAMAGED_CREATURE, targetId, attackerId, playerId, amount, combat);
    }
}
