package org.mage.test.player;

import mage.constants.PhaseStep;
import mage.game.Game;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class PlayerAction {

    private final String actionName;
    private final int turnNum;
    private final PhaseStep step;
    private final String action;

    public PlayerAction(String actionName, int turnNum, PhaseStep step, String action) {
        this.actionName = actionName;
        this.turnNum = turnNum;
        this.step = step;
        this.action = action;
    }

    public int getTurnNum() {
        return turnNum;
    }

    public PhaseStep getStep() {
        return step;
    }

    public String getAction() {
        return action;
    }

    public String getActionName() {
        return this.actionName;
    }

    /**
     * Calls after action removed from commands queue later (for multi steps action, e.g. AI related)
     */
    public void onActionRemovedLater(Game game, TestPlayer player) {
        //
    }
}
