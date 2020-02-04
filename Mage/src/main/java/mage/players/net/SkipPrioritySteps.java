
package mage.players.net;

import java.io.Serializable;
import mage.constants.PhaseStep;

/**
 *
 * @author LevelX2
 */
public class SkipPrioritySteps implements Serializable {

    boolean upkeep = false;
    boolean draw = false;
    boolean main1 = true;
    boolean beforeCombat = false;
    boolean endOfCombat = false;
    boolean main2 = true;
    boolean endOfTurn = false;

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
        switch (phaseStep) {
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

}
