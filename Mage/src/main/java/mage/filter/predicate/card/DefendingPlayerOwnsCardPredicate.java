package mage.filter.predicate.card;

import mage.cards.Card;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum DefendingPlayerOwnsCardPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input.getObject().isOwnedBy(game.getCombat().getDefendingPlayerId(input.getSourceId(), game));
    }

    @Override
    public String toString() {
        return "";
    }
}
