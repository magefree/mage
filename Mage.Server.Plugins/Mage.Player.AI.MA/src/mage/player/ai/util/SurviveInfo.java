package mage.player.ai.util;

import mage.game.permanent.Permanent;

/**
 * AI: combat simulation result
 *
 * @author noxx, JayDi85
 */
public class SurviveInfo {
    private final boolean attackerDied;
    private final boolean blockerDied;
    private final int diffBlockingScore;
    private final int diffNonblockingScore;

    private Permanent blocker; // for final result

    public SurviveInfo(boolean attackerDied, boolean blockerDied, int diffBlockingScore, int diffNonblockingScore) {
        this.attackerDied = attackerDied;
        this.blockerDied = blockerDied;
        this.diffBlockingScore = diffBlockingScore;
        this.diffNonblockingScore = diffNonblockingScore;
    }

    public void setBlocker(Permanent blocker) {
        this.blocker = blocker;
    }

    public Permanent getBlocker() {
        return this.blocker;
    }

    public int getDiffBlockingScore() {
        return this.diffBlockingScore;
    }

    public int getDiffNonblockingScore() {
        return this.diffNonblockingScore;
    }

    public boolean isAttackerDied() {
        return attackerDied;
    }

    public boolean isBlockerDied() {
        return blockerDied;
    }
}
