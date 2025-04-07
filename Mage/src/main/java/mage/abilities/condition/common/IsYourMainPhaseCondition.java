
package mage.abilities.condition.common;

import java.util.EnumSet;
import java.util.Set;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.TurnPhase;
import mage.game.Game;

/**
 * @author androosss
 */
public enum IsYourMainPhaseCondition implements Condition {

    instance;

    private static final Set<TurnPhase> turnPhases = EnumSet.of(TurnPhase.PRECOMBAT_MAIN, TurnPhase.POSTCOMBAT_MAIN);

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getActivePlayerId().equals(source.getControllerId())
                && turnPhases.contains(game.getTurn().getPhase().getType());
    }

    @Override
    public String toString() {
        return "it's your main phase";
    }
}
