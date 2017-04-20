/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.players.net;

import java.io.Serializable;

/**
 *
 * @author LevelX2
 */

public class UserSkipPrioritySteps implements Serializable {
    final SkipPrioritySteps yourTurn;
    final SkipPrioritySteps opponentTurn;

    boolean stopOnDeclareAttackersDuringSkipAction;
    boolean stopOnDeclareBlockerIfNoneAvailable;
    boolean stopOnAllMainPhases;
    boolean stopOnAllEndPhases;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final UserSkipPrioritySteps that = (UserSkipPrioritySteps) o;

        if (isStopOnDeclareAttackersDuringSkipAction() != that
            .isStopOnDeclareAttackersDuringSkipAction())
            return false;
        if (isStopOnDeclareBlockerIfNoneAvailable() != that.isStopOnDeclareBlockerIfNoneAvailable())
            return false;
        if (isStopOnAllMainPhases() != that.isStopOnAllMainPhases()) return false;
        if (isStopOnAllEndPhases() != that.isStopOnAllEndPhases()) return false;
        if (getYourTurn() != null ? !getYourTurn().equals(that.getYourTurn()) : that.getYourTurn
            () != null)
            return false;
        return getOpponentTurn() != null ? getOpponentTurn().equals(that.getOpponentTurn()) :
            that.getOpponentTurn() == null;

    }

    @Override
    public int hashCode() {
        int result = getYourTurn() != null ? getYourTurn().hashCode() : 0;
        result = 31 * result + (getOpponentTurn() != null ? getOpponentTurn().hashCode() : 0);
        result = 31 * result + (isStopOnDeclareAttackersDuringSkipAction() ? 1 : 0);
        result = 31 * result + (isStopOnDeclareBlockerIfNoneAvailable() ? 1 : 0);
        result = 31 * result + (isStopOnAllMainPhases() ? 1 : 0);
        result = 31 * result + (isStopOnAllEndPhases() ? 1 : 0);
        return result;
    }
}
