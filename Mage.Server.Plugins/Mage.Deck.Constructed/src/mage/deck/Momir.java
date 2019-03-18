
package mage.deck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidator;

/**
 *
 * @author nigelzor
 */
public class Momir extends DeckValidator {

    public Momir() {
        this("Momir Basic");
    }

    public Momir(String name) {
        super(name);
    }

    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;

        if (deck.getCards().size() != 60) {
            invalid.put("Deck", "Must contain 60 cards: has " + deck.getCards().size() + " cards");
            valid = false;
        }

        List<String> basicLandNames = new ArrayList<>(Arrays.asList("Forest", "Island", "Mountain", "Swamp", "Plains", "Wastes"));
        for (Card card : deck.getCards()) {
            if (!basicLandNames.contains(card.getName())) {
                invalid.put(card.getName(), "Only basic lands are allowed");
                valid = false;
            }
        }

        return valid;
    }

}
