

package mage.deck;

import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidator;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Limited extends DeckValidator {

    public Limited() {
        super("Limited");
    }

    @Override
    public boolean validate(Deck deck) {
         boolean valid = true;
        //20091005 - 100.2b
        if (deck.getCards().size() < 40) {
            invalid.put("Deck", "Must contain at least 40 cards: has only " + deck.getCards().size() + " cards");
            valid = false;

        }
        return valid;
    }

}
