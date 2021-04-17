
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.game.Game;

/**
 * @author North
 */
public enum TargetManaValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Card card = game.getCard(sourceAbility.getFirstTarget());
        if (card != null) {
            return card.getManaValue();
        }
        return 0;
    }

    @Override
    public TargetManaValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "that card's mana value";
    }
}
