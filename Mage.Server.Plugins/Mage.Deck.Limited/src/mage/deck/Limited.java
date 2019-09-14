package mage.deck;

import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Limited extends DeckValidator {

    public Limited() {
        super("Limited");
    }

    @Override
    public int getDeckMinSize() {
        return 40;
    }

    @Override
    public int getSideboardMinSize() {
        return 0;
    }

    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;
        //20091005 - 100.2b
        if (deck.getCards().size() < getDeckMinSize()) {
            invalid.put("Deck", "Must contain at least " + getDeckMinSize() + " cards: has only " + deck.getCards().size() + " cards");
            valid = false;
        }
        Map<String, Integer> counts = new HashMap<>();
        countCards(counts, deck.getCards());
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > 7 && entry.getKey().equals("Seven Dwarves")) {
                invalid.put(entry.getKey(), "Too many: " + entry.getValue());
                valid = false;
            }
        }
        return valid;
    }
}
