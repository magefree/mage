

package mage.game.events;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class DamageEvent extends GameEvent {

    protected boolean combat;

    public DamageEvent(EventType type, UUID targetId, UUID sourceId, UUID playerId, int amount, boolean preventable, boolean combat) {
        super(type, targetId, sourceId, playerId, amount, preventable);
        this.combat = combat;
    }

    public boolean isCombatDamage() {
        return combat;
    }

    public boolean isPreventable() {
        return flag;
    }

}
