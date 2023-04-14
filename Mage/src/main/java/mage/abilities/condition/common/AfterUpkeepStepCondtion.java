package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.PhaseStep;
import mage.game.Game;

/**
 * @author LevelX2
 */
public enum AfterUpkeepStepCondtion implements Condition {

    instance;
    @Override

    public boolean apply(Game game, Ability source) {
        return game.getTurnStepType().isAfter(PhaseStep.UPKEEP);
    }

    @Override
    public String toString() {
        return "after upkeep step";
    }
}
