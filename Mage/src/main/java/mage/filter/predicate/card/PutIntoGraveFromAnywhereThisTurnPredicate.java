package mage.filter.predicate.card;

import mage.cards.Card;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

/**
 * @author Alex-Vasile
 */
public enum PutIntoGraveFromAnywhereThisTurnPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        CardsPutIntoGraveyardWatcher watcher = game.getState().getWatcher(CardsPutIntoGraveyardWatcher.class);

        return watcher != null && watcher.checkCardFromAnywhere(input, game);
    }
}
