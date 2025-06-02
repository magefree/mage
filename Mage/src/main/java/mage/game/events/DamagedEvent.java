package mage.game.events;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class DamagedEvent extends GameEvent {

    protected boolean combat;
    protected int excess;

    protected DamagedEvent(EventType type, UUID targetId, UUID attackerId, UUID playerId, int amount, boolean combat) {
        super(type, targetId, null, playerId, amount, false);
        this.combat = combat;
        this.excess = 0;
        this.setSourceId(attackerId);
    }

    public boolean isCombatDamage() {
        return combat;
    }

    public void setExcess(int excess) {
        this.excess = Math.max(excess, 0);
    }

    public int getExcess() {
        return excess;
    }

}
