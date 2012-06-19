package mage.interfaces.rate;

import mage.Constants;
import mage.cards.Card;

/**
 * Interface for the class responsible for rating cards.
 *
 * @author nantuko
 */
public interface RateCallback {
    int rateCard(Card card);
    Card getBestBasicLand(Constants.ColoredManaSymbol color);
}