
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author LevelX2
 */

public enum AddendumCondition implements Condition {

    instance;
    private static final Set<TurnPhase> turnPhases = EnumSet.of(
            TurnPhase.PRECOMBAT_MAIN, TurnPhase.POSTCOMBAT_MAIN
    );

    @Override
    public boolean apply(Game game, Ability source) {
        if (!game.isActivePlayer(source.getControllerId()) ||
                !turnPhases.contains(game.getTurn().getPhase().getType())) {
            return false;
        }
        if (CastFromEverywhereSourceCondition.instance.apply(game, source)) {
            return true;
        }
        Spell spell = game.getSpell(source.getSourceId());
        return spell != null && !spell.isCopy();
    }
}
