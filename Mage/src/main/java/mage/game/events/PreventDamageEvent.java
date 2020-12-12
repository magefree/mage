package mage.game.events;

import mage.abilities.Ability;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class PreventDamageEvent extends GameEvent {

    public PreventDamageEvent(UUID targetId, UUID attackerId, Ability source, UUID playerId, int damageToPrevent, boolean isCombatDamage) {
        super(GameEvent.EventType.PREVENT_DAMAGE, targetId, null, playerId, damageToPrevent, isCombatDamage);
        this.setSourceId(attackerId);
    }

    public boolean isCombatDamage() {
        return flag;
    }
}
