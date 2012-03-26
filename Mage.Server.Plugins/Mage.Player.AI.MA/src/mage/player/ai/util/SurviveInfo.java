package mage.player.ai.util;

/**
 * @author noxx
 */
public class SurviveInfo {
    private boolean attackerDied;
    private boolean blockerDied;

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
}
