
package mage.filter.predicate.mageobject;

import mage.constants.CardType;
import mage.MageObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author North
 */
public class CardTypePredicate implements Predicate<MageObject> {

    private final CardType cardType;

    public CardTypePredicate(CardType cardType) {
        this.cardType = cardType;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        return input.getCardType().contains(cardType);
    }

    @Override
    public String toString() {
        return "CardType(" + cardType.toString() + ')';
    }
}
