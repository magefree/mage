package mage.filter.predicate.card;

import mage.cards.Card;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * @author Plopman, Alex-Vasile
 */
public class CardManaCostLessThanControlledLandCountPredicate implements Predicate<Card> {

    private static final String string = "card with mana value less than or equal to the number of lands you control";
    private static final CardManaCostLessThanControlledLandCountPredicate instance = new CardManaCostLessThanControlledLandCountPredicate();

    private CardManaCostLessThanControlledLandCountPredicate() { }

    public static CardManaCostLessThanControlledLandCountPredicate getInstance() {
        return instance;
    }

    @Override
    public boolean apply(Card input, Game game) {
        return input.getManaValue() <= game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, input.getOwnerId(), game).size();
    }

    @Override
    public String toString() {
        return string;
    }
}
