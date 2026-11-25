package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.dynamicvalue.common.StrangeNewWorldsValue;
import mage.game.Game;

public enum StrangeNewWorldsHint implements Hint {
    instance;

    private static final Hint hint =
        new ValueHint("Differently named lands you control", StrangeNewWorldsValue.instance);

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public StrangeNewWorldsHint copy() {
        return instance;
    }
}
