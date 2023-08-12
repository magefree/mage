package mage.filter.predicate.card;

import mage.cards.Card;
import mage.cards.ModalDoubleFacedCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum DoubleFacedCardPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        return input instanceof ModalDoubleFacedCard || input.getSecondCardFace() != null;
    }

    @Override
    public String toString() {
        return "Double-Faced";
    }
}
