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

import mage.constants.PhaseStep;

import java.io.Serializable;

/**
 *
 * @author LevelX2
 */
public class SkipPrioritySteps implements Serializable {
    boolean upkeep;
    boolean draw;
    boolean main1;
    boolean beforeCombat;
    boolean endOfCombat;
    boolean main2;
    boolean endOfTurn;

    public boolean isUpkeep() {
        return upkeep;
    }

    public void setUpkeep(boolean upkeep) {
        this.upkeep = upkeep;
    }

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    public boolean isMain1() {
        return main1;
    }

    public void setMain1(boolean main1) {
        this.main1 = main1;
    }

    public boolean isBeforeCombat() {
        return beforeCombat;
    }

    public void setBeforeCombat(boolean beforeCombat) {
        this.beforeCombat = beforeCombat;
    }

    public boolean isEndOfCombat() {
        return endOfCombat;
    }

    public void setEndOfCombat(boolean endOfCombat) {
        this.endOfCombat = endOfCombat;
    }

    public boolean isMain2() {
        return main2;
    }

    public void setMain2(boolean main2) {
        this.main2 = main2;
    }

    public boolean isEndOfTurn() {
        return endOfTurn;
    }

    public void setEndOfTurn(boolean endOfTurn) {
        this.endOfTurn = endOfTurn;
    }

    public boolean isPhaseStepSet(PhaseStep phaseStep) {
        switch(phaseStep) {
            case UPKEEP:
                return isUpkeep();
            case DRAW:
                return isDraw();
            case PRECOMBAT_MAIN:
                return isMain1();
            case BEGIN_COMBAT:
                return isBeforeCombat();
            case END_COMBAT:
                return isEndOfCombat();
            case POSTCOMBAT_MAIN:
                return isMain2();
            case END_TURN:
                return isEndOfTurn();
            default:
                return true;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final SkipPrioritySteps that = (SkipPrioritySteps) o;

        if (isUpkeep() != that.isUpkeep()) return false;
        if (isDraw() != that.isDraw()) return false;
        if (isMain1() != that.isMain1()) return false;
        if (isBeforeCombat() != that.isBeforeCombat()) return false;
        if (isEndOfCombat() != that.isEndOfCombat()) return false;
        if (isMain2() != that.isMain2()) return false;
        return isEndOfTurn() == that.isEndOfTurn();

    }

    @Override
    public int hashCode() {
        int result = (isUpkeep() ? 1 : 0);
        result = 31 * result + (isDraw() ? 1 : 0);
        result = 31 * result + (isMain1() ? 1 : 0);
        result = 31 * result + (isBeforeCombat() ? 1 : 0);
        result = 31 * result + (isEndOfCombat() ? 1 : 0);
        result = 31 * result + (isMain2() ? 1 : 0);
        result = 31 * result + (isEndOfTurn() ? 1 : 0);
        return result;
    }
}
