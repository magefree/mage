

package mage.game.events;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DamagedCreatureEvent extends DamagedEvent {

    public DamagedCreatureEvent(UUID targetId, UUID sourceId, UUID playerId, int amount, boolean combat) {
        super(EventType.DAMAGED_CREATURE, targetId, sourceId, playerId, amount, combat);
    }

}
