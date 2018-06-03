

package mage.game.events;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DamagePlaneswalkerEvent extends DamageEvent {

    public DamagePlaneswalkerEvent(UUID targetId, UUID sourceId, UUID playerId, int amount, boolean preventable, boolean combat) {
        super(EventType.DAMAGE_PLANESWALKER, targetId, sourceId, playerId, amount, preventable, combat);
    }

}
