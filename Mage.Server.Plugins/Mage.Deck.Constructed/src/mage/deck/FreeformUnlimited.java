package mage.deck;

import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidator;
import mage.cards.decks.DeckValidatorErrorType;

/**
 * @author resech
 */
public class FreeformUnlimited extends DeckValidator {

    public FreeformUnlimited() {
        this("Constructed - Freeform Unlimited", null);
    }

    public FreeformUnlimited(String name, String shortName) {
        super(name, shortName);
    }

    @Override
    public int getDeckMinSize() {
        return 0;
    }

    @Override
    public int getSideboardMinSize() {
        return 0;
    }

    @Override
    public boolean validate(Deck deck) {
        return true;
    }
}
