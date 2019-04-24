package mage.players.net;

import java.io.Serializable;

/**
 * @author LevelX2
 */
public class UserSkipPrioritySteps implements Serializable {

    final SkipPrioritySteps yourTurn;
    final SkipPrioritySteps opponentTurn;

    boolean stopOnDeclareAttackers = true;
    boolean stopOnDeclareBlockersWithZeroPermanents = false;
    boolean stopOnDeclareBlockersWithAnyPermanents = true;
    boolean stopOnAllMainPhases = true;
    boolean stopOnAllEndPhases = true;
    boolean stopOnStackNewObjects = true;

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

    public boolean isStopOnDeclareBlockersWithZeroPermanents() {
        return stopOnDeclareBlockersWithZeroPermanents;
    }

    public void setStopOnDeclareBlockersWithZeroPermanents(boolean stopOnDeclareBlockersWithZeroPermanents) {
        this.stopOnDeclareBlockersWithZeroPermanents = stopOnDeclareBlockersWithZeroPermanents;
    }

    public boolean isStopOnDeclareAttackers() {
        return stopOnDeclareAttackers;
    }

    public void setStopOnDeclareAttackersDuringSkipActions(boolean stopOnDeclareAttackersDuringSkipActions) {
        this.stopOnDeclareAttackers = stopOnDeclareAttackersDuringSkipActions;
    }

    public boolean isStopOnDeclareBlockersWithAnyPermanents() {
        return stopOnDeclareBlockersWithAnyPermanents;
    }

    public void setStopOnDeclareBlockersWithAnyPermanents(boolean stopOnDeclareBlockersWithAnyPermanents) {
        this.stopOnDeclareBlockersWithAnyPermanents = stopOnDeclareBlockersWithAnyPermanents;
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

    public boolean isStopOnStackNewObjects() {
        return stopOnStackNewObjects;
    }

    public void setStopOnStackNewObjects(boolean stopOnStackNewObjects) {
        this.stopOnStackNewObjects = stopOnStackNewObjects;
    }
}
