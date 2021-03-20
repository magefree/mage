package mage.filter.predicate.card;

import mage.cards.Card;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum DefendingPlayerOwnsCardPredicate implements ObjectPlayerPredicate<ObjectPlayer<Card>> {
    instance;

    @Override
    public boolean apply(ObjectPlayer<Card> input, Game game) {
        return game.getCombat().getPlayerDefenders(game, false).contains(input.getObject().getOwnerId());
    }

    @Override
    public String toString() {
        return "";
    }
}
