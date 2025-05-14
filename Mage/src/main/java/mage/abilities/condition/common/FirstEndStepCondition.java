package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.TurnPhase;
import mage.game.Game;

/**
 * @author balazskristof
 */
public enum FirstEndStepCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getTurn().getPhase(TurnPhase.END).getCount() == 0;
    }

    @Override
    public String toString() {
    return "it's the first end step of the turn";
    }
}
