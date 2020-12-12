package mage.game.events;

import mage.abilities.Ability;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class PreventedDamageEvent extends GameEvent {

    public PreventedDamageEvent(UUID targetId, UUID attackerId, Ability source, UUID playerId, int preventedDamage) {
        super(GameEvent.EventType.PREVENTED_DAMAGE, targetId, null, playerId, preventedDamage, false);
        this.setSourceId(attackerId);
    }
}
