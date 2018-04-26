package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

/**
 * Created by Eric on 9/24/2016.
 */
public class ChromaOutrageShamanCount implements DynamicValue {

    private int chroma;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        chroma = 0;
        for (Card card : game.getBattlefield().getAllActivePermanents(new FilterControlledPermanent(), sourceAbility.getControllerId(), game)) {
            chroma += card.getManaCost().getMana().getRed();
        }
        return chroma;
    }

    @Override
    public DynamicValue copy() {
        return new ChromaOutrageShamanCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
