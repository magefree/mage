

package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.TurnPhase;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public enum  MyTurnBeforeAttackersDeclaredCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.isActivePlayer(source.getControllerId())) {
            TurnPhase turnPhase = game.getTurn().getPhase().getType();
            if (turnPhase == TurnPhase.BEGINNING || turnPhase == TurnPhase.PRECOMBAT_MAIN) {
                return true;
            }
            if (turnPhase == TurnPhase.COMBAT) {
                return !game.getTurn().isDeclareAttackersStepStarted();
            }
        }
        return false;
    }

    @Override
    public String toString() {
	return "during your turn, before attackers are declared";
    }
}

