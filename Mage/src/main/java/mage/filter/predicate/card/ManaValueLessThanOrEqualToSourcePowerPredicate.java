package mage.filter.predicate.card;

import mage.cards.Card;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author xenohedron
 */
public enum ManaValueLessThanOrEqualToSourcePowerPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        Permanent sourcePermanent = input.getSource().getSourcePermanentOrLKI(game);
        return sourcePermanent != null && input.getObject().getManaValue() <= sourcePermanent.getPower().getValue();
    }

    @Override
    public String toString() {
        return "mana value less than or equal to {this}'s power";
    }
}
