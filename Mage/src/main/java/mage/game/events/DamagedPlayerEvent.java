

package mage.game.events;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DamagedPlayerEvent extends DamagedEvent {

    public DamagedPlayerEvent(UUID targetId, UUID sourceId, UUID playerId, int amount, boolean combat) {
        super(EventType.DAMAGED_PLAYER, targetId, sourceId, playerId, amount, combat);
    }

}
