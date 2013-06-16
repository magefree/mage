package mage.interfaces.rate;

import java.util.List;

import mage.cards.Card;
import mage.constants.ColoredManaSymbol;

/**
 * Interface for the class responsible for rating cards.
 *
 * @author nantuko
 */
public interface RateCallback {
    int rateCard(Card card);
    Card getBestBasicLand(ColoredManaSymbol color, List<String> setsToUse);
}