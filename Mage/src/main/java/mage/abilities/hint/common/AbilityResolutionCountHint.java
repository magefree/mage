package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.AbilityResolutionCount;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.game.Game;

/**
 * @author emerald000
 */
public enum AbilityResolutionCountHint implements Hint {

    instance;
    private static final Hint hint = new ValueHint("Resolution count", AbilityResolutionCount.instance);

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
