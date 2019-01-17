
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.game.Game;

/**
 * @author North
 */
public enum TargetConvertedManaCost implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        Card card = game.getCard(source.getFirstTarget());
        if (card != null) {
            return card.getConvertedManaCost();
        }
        return 0;
    }

    @Override
    public TargetConvertedManaCost copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "that card's converted mana cost";
    }
}
