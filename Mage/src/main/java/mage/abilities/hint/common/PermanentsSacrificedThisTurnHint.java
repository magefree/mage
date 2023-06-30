package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsSacrificedThisTurnCount;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.game.Game;

/**
 * @author Susucr
 */
public enum PermanentsSacrificedThisTurnHint implements Hint {
    instance;

    private static final Hint hint = new ValueHint(
            "Permanents sacrificed this turn", PermanentsSacrificedThisTurnCount.instance
    );

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public PermanentsSacrificedThisTurnHint copy() {
        return this;
    }
}
