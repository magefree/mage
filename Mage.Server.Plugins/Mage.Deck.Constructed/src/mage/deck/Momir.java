package mage.deck;

import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidator;
import mage.cards.decks.DeckValidatorErrorType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author nigelzor
 */
public class Momir extends DeckValidator {

    public Momir() {
        super("Momir Basic", "Momir");
    }

    @Override
    public int getDeckMinSize() {
        return 60;
    }

    @Override
    public int getSideboardMinSize() {
        return 0;
    }

    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;
        errorsList.clear();

        if (deck.getMaindeckCards().size() != getDeckMinSize()) {
            addError(DeckValidatorErrorType.DECK_SIZE, "Deck", "Must contain " + getDeckMinSize() + " cards: has " + deck.getMaindeckCards().size() + " cards");
            valid = false;
        }

        List<String> basicLandNames = new ArrayList<>(Arrays.asList("Forest", "Island", "Mountain", "Swamp", "Plains", "Wastes"));
        for (Card card : deck.getCards()) {
            if (!basicLandNames.contains(card.getName())) {
                addError(DeckValidatorErrorType.OTHER, card.getName(), "Only basic lands are allowed", true);
                valid = false;
            }
        }

        return valid;
    }

}
