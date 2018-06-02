

package mage.game.events;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DamagePlayerEvent extends DamageEvent {

    public DamagePlayerEvent(UUID targetId, UUID sourceId, UUID playerId, int amount, boolean preventable, boolean combat) {
        super(EventType.DAMAGE_PLAYER, targetId, sourceId, playerId, amount, preventable, combat);
    }

}
