package mage.deck;

import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidator;
import mage.cards.decks.DeckValidatorErrorType;

/**
 * @author fireshoes
 */
public class Freeform extends DeckValidator {

    public Freeform() {
        this("Constructed - Freeform", null);
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
        errorsList.clear();
        // http://magic.wizards.com/en/gameinfo/gameplay/formats/freeform
        if (deck.getCards().size() < getDeckMinSize()) {
            addError(DeckValidatorErrorType.DECK_SIZE, "Deck", "Must contain at least " + getDeckMinSize() + " cards: has only " + deck.getCards().size() + " cards");
            valid = false;
        }
        return valid;
    }
}
