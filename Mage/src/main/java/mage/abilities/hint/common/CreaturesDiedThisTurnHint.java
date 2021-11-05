package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.CreaturesDiedThisTurnCount;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum CreaturesDiedThisTurnHint implements Hint {
    instance;

    private static final Hint hint = new ValueHint(
            "Creatures that died this turn", CreaturesDiedThisTurnCount.instance
    );

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public CreaturesDiedThisTurnHint copy() {
        return this;
    }
}
