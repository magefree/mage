package mage.game.events;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class DamagedEvent extends GameEvent {

    protected boolean combat;
    protected int excess;

    public DamagedEvent(EventType type, UUID targetId, UUID attackerId, UUID playerId, int amount, boolean combat) {
        super(type, targetId, null, playerId, amount, false);
        this.combat = combat;
        this.excess = 0;
        this.setSourceId(attackerId);
    }

    public boolean isCombatDamage() {
        return combat;
    }

    public boolean isPreventable() {
        return flag;
    }

    public void setExcess(int excess) {
        this.excess = Math.max(excess, 0);
    }

    public int getExcess() {
        return excess;
    }

    public UUID getAttackerId() {
        return getSourceId();
    }
}
