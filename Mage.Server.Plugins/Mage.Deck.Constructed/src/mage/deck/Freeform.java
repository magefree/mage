
package mage.deck;

import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidator;

/**
 *
 * @author fireshoes
 */
public class Freeform extends DeckValidator {

    public Freeform() {
        super("Constructed - Freeform");
    }

    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;
        // http://magic.wizards.com/en/gameinfo/gameplay/formats/freeform
        if (deck.getCards().size() < 40) {
            invalid.put("Deck", "Must contain at least 40 cards: has only " + deck.getCards().size() + " cards");
            valid = false;
        }
        return valid;
    }
}
