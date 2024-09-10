

package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.TurnPhase;
import mage.game.Game;

import java.util.Locale;

/**
 * @author LevelX2
 */
public class IsPhaseCondition implements Condition {

    protected TurnPhase turnPhase;
    protected boolean yourTurn;

    public IsPhaseCondition(TurnPhase turnPhase) {
        this(turnPhase, false);
    }

    public IsPhaseCondition(TurnPhase turnPhase, boolean yourTurn) {
        this.turnPhase = turnPhase;
        this.yourTurn = yourTurn;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return turnPhase == game.getTurnPhaseType() && (!yourTurn || game.getActivePlayerId().equals(source.getControllerId()));
    }

    @Override
    public String toString() {
        return new StringBuilder("during ")
            .append(yourTurn ? "your " : "")
            .append(turnPhase)
            .toString()
            .toLowerCase(Locale.ENGLISH);
    }

}
