package mage.game.events;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class DamageEvent extends GameEvent {

    protected boolean combat;
    private boolean asThoughInfect = false;
    private boolean asThoughWither = false;

    public DamageEvent(EventType type, UUID targetId, UUID damageSourceId, UUID targetControllerId, int amount, boolean preventable, boolean combat) {
        super(type, targetId, null, targetControllerId, amount, preventable);
        this.combat = combat;
        this.setSourceId(damageSourceId);
    }

    public boolean isCombatDamage() {
        return combat;
    }

    public boolean isPreventable() {
        return flag;
    }

    public void setAsThoughInfect(boolean asThoughInfect) {
        this.asThoughInfect = asThoughInfect;
    }

    public boolean isAsThoughInfect() {
        return asThoughInfect;
    }

    public void setAsThoughWither(boolean asThoughWither) {
        this.asThoughWither = asThoughWither;
    }

    public boolean isAsThoughWither() {
        return asThoughWither;
    }
}
