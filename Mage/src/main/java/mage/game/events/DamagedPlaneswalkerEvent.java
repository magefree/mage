

package mage.game.events;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DamagedPlaneswalkerEvent extends DamagedEvent {

    public DamagedPlaneswalkerEvent(UUID targetId, UUID sourceId, UUID playerId, int amount, boolean combat) {
        super(EventType.DAMAGED_PLANESWALKER, targetId, sourceId, playerId, amount, combat);
    }

}
