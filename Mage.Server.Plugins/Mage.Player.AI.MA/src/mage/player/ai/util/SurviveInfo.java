package mage.player.ai.util;

import mage.players.Player;

/**
 * @author noxx
 */
public class SurviveInfo {
    private boolean attackerDied;
    private boolean blockerDied;

    private Player defender;
    private boolean triggered;

    public SurviveInfo(boolean attackerDied, boolean blockerDied, Player defender, boolean triggered) {
        this.attackerDied = attackerDied;
        this.blockerDied = blockerDied;
        this.defender = defender;
        this.triggered = triggered;
    }

    public SurviveInfo(boolean attackerDied, boolean blockerDied) {
        this.attackerDied = attackerDied;
        this.blockerDied = blockerDied;
    }

    public boolean isAttackerDied() {
        return attackerDied;
    }

    public void setAttackerDied(boolean attackerDied) {
        this.attackerDied = attackerDied;
    }

    public boolean isBlockerDied() {
        return blockerDied;
    }

    public void setBlockerDied(boolean blockerDied) {
        this.blockerDied = blockerDied;
    }

    public Player getDefender() {
        return defender;
    }

    public boolean isTriggered() {
        return triggered;
    }
}
