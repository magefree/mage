package mage.game.events;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DamagedPlaneswalkerEvent extends DamagedEvent {

    public DamagedPlaneswalkerEvent(UUID targetId, UUID attackerId, UUID playerId, int amount, boolean combat) {
        super(GameEvent.EventType.DAMAGED_PLANESWALKER, targetId, null, playerId, amount, combat);
        this.setSourceId(attackerId);
    }

}
