
package mage.view;

import java.io.Serializable;
import mage.cards.decks.Deck;

/**
 * @author nantuko
 */
public class DeckView implements Serializable {

    private final String name;
    private final SimpleCardsView cards;
    private final SimpleCardsView sideboard;

    public DeckView(Deck deck) {
        name = deck.getName();
        cards = new SimpleCardsView(deck.getCards(), false);
        sideboard = new SimpleCardsView(deck.getSideboard(), false);
    }

    public String getName() {
        return name;
    }

    public SimpleCardsView getCards() {
        return cards;
    }

    public SimpleCardsView getSideboard() {
        return sideboard;
    }
}
