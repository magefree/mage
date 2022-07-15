
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.PhaseStep;
import mage.game.Game;

import java.util.Objects;

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
        return phaseStep == game.getStep().getType() && (!onlyDuringYourSteps || game.isActivePlayer(source.getControllerId()));
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

    @Override
    public int hashCode() {
        return Objects.hash(phaseStep, onlyDuringYourSteps);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IsStepCondition other = (IsStepCondition) obj;
        return this.onlyDuringYourSteps == other.onlyDuringYourSteps
                && this.phaseStep == other.phaseStep;
    }
}
