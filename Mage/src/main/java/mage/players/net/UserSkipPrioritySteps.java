
package mage.players.net;

import java.io.Serializable;

/**
 *
 * @author LevelX2
 */
public class UserSkipPrioritySteps implements Serializable {

    final SkipPrioritySteps yourTurn;
    final SkipPrioritySteps opponentTurn;

    boolean stopOnDeclareAttackersDuringSkipAction = true;
    boolean stopOnDeclareBlockerIfNoneAvailable = true;
    boolean stopOnAllMainPhases = true;
    boolean stopOnAllEndPhases = true;

    public UserSkipPrioritySteps() {
        yourTurn = new SkipPrioritySteps();
        opponentTurn = new SkipPrioritySteps();
    }

    public SkipPrioritySteps getYourTurn() {
        return yourTurn;
    }

    public SkipPrioritySteps getOpponentTurn() {
        return opponentTurn;
    }

    public boolean isStopOnDeclareBlockerIfNoneAvailable() {
        return stopOnDeclareBlockerIfNoneAvailable;
    }

    public void setStopOnDeclareBlockerIfNoneAvailable(boolean stopOnDeclareBlockerIfNoneAvailable) {
        this.stopOnDeclareBlockerIfNoneAvailable = stopOnDeclareBlockerIfNoneAvailable;
    }

    public boolean isStopOnDeclareAttackersDuringSkipAction() {
        return stopOnDeclareAttackersDuringSkipAction;
    }

    public void setStopOnDeclareAttackersDuringSkipActions(boolean stopOnDeclareAttackersDuringSkipActions) {
        this.stopOnDeclareAttackersDuringSkipAction = stopOnDeclareAttackersDuringSkipActions;
    }

    public boolean isStopOnAllMainPhases() {
        return stopOnAllMainPhases;
    }

    public void setStopOnAllMainPhases(boolean stopOnAllMainPhases) {
        this.stopOnAllMainPhases = stopOnAllMainPhases;
    }

    public boolean isStopOnAllEndPhases() {
        return stopOnAllEndPhases;
    }

    public void setStopOnAllEndPhases(boolean stopOnAllEndPhases) {
        this.stopOnAllEndPhases = stopOnAllEndPhases;
    }

}
