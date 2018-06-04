

package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.TurnPhase;
import mage.game.Game;

/**
 * @author LevelX2
 */
public class IsPhaseCondition implements Condition {

    protected TurnPhase turnPhase;

    public IsPhaseCondition(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return turnPhase == game.getTurn().getPhaseType();
    }

    @Override
    public String toString() {
        return new StringBuilder("during ").append(turnPhase).toString();
    }

}
