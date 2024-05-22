package mage.filter.predicate.card;

import mage.MageObjectReference;
import mage.cards.Card;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.watchers.common.CardsMilledWatcher;

/**
 * Needs CardsMilledWatcher to work.
 *
 * @author Susucr
 */
public enum MilledThisTurnPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        return CardsMilledWatcher.wasMilledThisTurn(
                new MageObjectReference(input.getMainCard(), game), game
        );
    }
}
