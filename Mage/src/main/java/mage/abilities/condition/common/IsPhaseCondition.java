

package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.TurnPhase;
import mage.game.Game;

import java.util.Locale;
import java.util.Objects;

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
        return turnPhase == game.getTurn().getPhaseType() && (!yourTurn || game.getActivePlayerId().equals(source.getControllerId()));
    }

    @Override
    public String toString() {
        return new StringBuilder("during ")
            .append(yourTurn ? "your " : "")
            .append(turnPhase)
            .toString()
            .toLowerCase(Locale.ENGLISH);
    }

    @Override
    public int hashCode() {
        return Objects.hash(turnPhase, yourTurn);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IsPhaseCondition other = (IsPhaseCondition) obj;
        return this.yourTurn == other.yourTurn
                && this.turnPhase == other.turnPhase;
    }
}
