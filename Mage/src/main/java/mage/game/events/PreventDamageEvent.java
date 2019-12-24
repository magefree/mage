package mage.game.events;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class PreventDamageEvent extends GameEvent {

    public PreventDamageEvent(UUID targetId, UUID sourceId, UUID playerId, int damageToPrevent, boolean isCombatDamage) {
        super(EventType.PREVENT_DAMAGE, targetId, sourceId, playerId, damageToPrevent, isCombatDamage);
    }

    public boolean isCombatDamage() {
        return flag;
    }
}
