

package mage.game.events;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DamagedPlayerEvent extends DamagedEvent {

    public DamagedPlayerEvent(UUID targetId, UUID attackerId, UUID playerId, int amount, boolean combat) {
        super(GameEvent.EventType.DAMAGED_PLAYER, targetId, attackerId, playerId, amount, combat);
    }
}
