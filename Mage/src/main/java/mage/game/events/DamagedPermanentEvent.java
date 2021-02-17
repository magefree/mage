package mage.game.events;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class DamagedPermanentEvent extends DamagedEvent {

    public DamagedPermanentEvent(UUID targetId, UUID attackerId, UUID playerId, int amount, boolean combat) {
        super(EventType.DAMAGED_PERMANENT, targetId, attackerId, playerId, amount, combat);
    }
}
