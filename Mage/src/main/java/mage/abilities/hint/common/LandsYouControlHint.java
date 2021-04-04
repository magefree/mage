package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum LandsYouControlHint implements Hint {

    instance;
    private static final Hint hint = new ValueHint("Lands you control", LandsYouControlCount.instance);

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
