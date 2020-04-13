package mage.abilities.keyword;

import mage.cards.Card;

import java.io.Serializable;
import java.util.Set;

/*
 * @author emerald000
 */
public interface CompanionCondition extends Serializable {

    /**
     * @return The rule to get added to the card text. (Everything after the dash)
     */
    String getRule();

    /**
     * @param deck The set of cards to check.
     * @return Whether the companion is valid for that deck.
     */
    boolean isLegal(Set<Card> deck);
}
