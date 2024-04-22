package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.Card;
import mage.game.Game;

import java.util.List;

public enum TotalCardsExiledOwnedManaValue implements DynamicValue {
    instance;

    private static final Hint hint = new ValueHint("Total mana value of cards you own in exile", instance);

    private TotalCardsExiledOwnedManaValue() {
    }

    @Override
    public TotalCardsExiledOwnedManaValue copy() {
        return this;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int totalCMC = 0;
        List<Card> cards = game.getExile().getAllCards(
                game,
                sourceAbility.getControllerId()
        );
        for (Card card : cards) {
            totalCMC += card.getManaValue();
        }
        return totalCMC;
    }

    @Override
    public String getMessage() {
        return "the total mana value of cards you own in exile";
    }

    @Override
    public String toString() {
        return "X";
    }

    public static Hint getHint() {
        return hint;
    }
}
