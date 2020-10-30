package mage.filter.predicate.other;

import mage.cards.Card;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * @author North
 */
public enum FaceDownPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        return input.isFaceDown(game);
    }

    @Override
    public String toString() {
        return "Face-down";
    }
}
