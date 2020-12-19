package org.mage.test.player;

import mage.constants.PhaseStep;
import mage.game.Game;
import org.mage.test.serverside.base.CardTestCodePayload;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class PlayerAction {

    private final String actionName;
    private final int turnNum;
    private final PhaseStep step;
    private final String action;
    private final CardTestCodePayload codePayload; // special code to execute (e.g. on dynamic check)

    public PlayerAction(String actionName, int turnNum, PhaseStep step, String action) {
        this(actionName, turnNum, step, action, null);
    }

    public PlayerAction(String actionName, int turnNum, PhaseStep step, String action, CardTestCodePayload codePayload) {
        this.actionName = actionName;
        this.turnNum = turnNum;
        this.step = step;
        this.action = action;
        this.codePayload = codePayload;
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
        return actionName;
    }

    public CardTestCodePayload getCodePayload() {
        return codePayload;
    }

    /**
     * Calls after action removed from commands queue later (for multi steps
     * action, e.g.AI related)
     *
     * @param game
     * @param player
     */
    public void onActionRemovedLater(Game game, TestPlayer player) {
        //
    }
}
