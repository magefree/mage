package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsSacrificedThisTurnCount;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.game.Game;

/**
 * @author Susucr
 */
public enum PermanentsSacrifiedThisTurnHint implements Hint {
    instance;

    private static final Hint hint = new ValueHint(
            "Permanents sacrified this turn", PermanentsSacrificedThisTurnCount.instance
    );

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public PermanentsSacrifiedThisTurnHint copy() {
        return this;
    }
}
