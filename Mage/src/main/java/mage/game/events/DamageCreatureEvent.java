

package mage.game.events;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DamageCreatureEvent extends DamageEvent {

    public DamageCreatureEvent(UUID targetId, UUID sourceId, UUID playerId, int amount, boolean preventable, boolean combat) {
        super(EventType.DAMAGE_CREATURE, targetId, sourceId, playerId, amount, preventable, combat);
    }

}
