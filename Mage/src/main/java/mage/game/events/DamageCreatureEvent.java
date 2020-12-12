package mage.game.events;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DamageCreatureEvent extends DamageEvent {

    public DamageCreatureEvent(UUID targetId, UUID damageSourceId, UUID targetControllerId, int amount, boolean preventable, boolean combat) {
        super(GameEvent.EventType.DAMAGE_CREATURE, targetId, damageSourceId, targetControllerId, amount, preventable, combat);
    }
}
