package mage.game.events;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class DamagePermanentEvent extends DamageEvent {

    public DamagePermanentEvent(UUID targetId, UUID damageSourceId, UUID targetControllerId, int amount, boolean preventable, boolean combat) {
        super(EventType.DAMAGE_PERMANENT, targetId, damageSourceId, targetControllerId, amount, preventable, combat);
    }
}
