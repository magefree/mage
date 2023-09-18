package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.SideboardCardsYouControlCount;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.game.Game;

/**
 * @author JayDi85
 */
public enum OpenSideboardHint implements Hint {

    instance;
    private static final Hint hint = new ValueHint("Cards in your sideboard", SideboardCardsYouControlCount.instance);

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability) + " (<i>Right click on battlefield to open player's sideboard at any time</i>)";
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
