package mage.deck;

import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidator;

/**
 * @author fireshoes
 */
public class Freeform extends DeckValidator {

    public Freeform() {
        super("Constructed - Freeform");
    }

    public Freeform(String name) {
        super(name);
    }

    public Freeform(String name, String shortName) {
        super(name, shortName);
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
        invalid.clear();
        // http://magic.wizards.com/en/gameinfo/gameplay/formats/freeform
        if (deck.getCards().size() < getDeckMinSize()) {
            invalid.put("Deck", "Must contain at least " + getDeckMinSize() + " cards: has only " + deck.getCards().size() + " cards");
            valid = false;
        }
        return valid;
    }
}
