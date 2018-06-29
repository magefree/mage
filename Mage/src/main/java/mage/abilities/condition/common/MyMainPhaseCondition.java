
package mage.abilities.condition.common;

import java.util.EnumSet;
import java.util.Set;

import mage.constants.TurnPhase;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

/**
 * @author LevelX2
 */

public enum MyMainPhaseCondition implements Condition {

    instance;
    private static final Set<TurnPhase> turnPhases = EnumSet.of(TurnPhase.PRECOMBAT_MAIN, TurnPhase.POSTCOMBAT_MAIN);

    @Override
    public boolean apply(Game game, Ability source) {
        return game.isActivePlayer(source.getControllerId()) &&
                turnPhases.contains(game.getTurn().getPhase().getType());
    }
}
