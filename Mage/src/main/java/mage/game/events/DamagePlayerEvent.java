package mage.game.events;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DamagePlayerEvent extends DamageEvent {

    public DamagePlayerEvent(UUID targetId, UUID damageSourceId, UUID targetControllerId, int amount, boolean preventable, boolean combat) {
        super(GameEvent.EventType.DAMAGE_PLAYER, targetId, damageSourceId, targetControllerId, amount, preventable, combat);
    }
}
