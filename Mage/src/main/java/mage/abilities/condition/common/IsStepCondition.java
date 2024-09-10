
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.PhaseStep;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class IsStepCondition implements Condition {

    protected PhaseStep phaseStep;
    protected boolean onlyDuringYourSteps;

    public IsStepCondition(PhaseStep phaseStep) {
        this(phaseStep, true);
    }

    public IsStepCondition(PhaseStep phaseStep, boolean onlyDuringYourSteps) {
        this.phaseStep = phaseStep;
        this.onlyDuringYourSteps = onlyDuringYourSteps;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return phaseStep == game.getTurnStepType() && (!onlyDuringYourSteps || game.isActivePlayer(source.getControllerId()));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("during ");
        if (onlyDuringYourSteps) {
            sb.append("your ").append(phaseStep.getStepText());
        } else if (phaseStep == PhaseStep.UPKEEP) {
            sb.append("any upkeep step");
        } else {
            sb.append("the ").append(phaseStep.getStepText());
        }
        return sb.toString();
    }

}
