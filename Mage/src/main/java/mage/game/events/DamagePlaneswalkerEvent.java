package mage.game.events;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DamagePlaneswalkerEvent extends DamageEvent {

    public DamagePlaneswalkerEvent(UUID targetId, UUID damageSourceId, UUID targetControllerId, int amount, boolean preventable, boolean combat) {
        super(GameEvent.EventType.DAMAGE_PLANESWALKER, targetId, damageSourceId, targetControllerId, amount, preventable, combat);
    }
}
