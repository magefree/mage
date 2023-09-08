package mage.tournament.cubes;

import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.game.draft.DraftCube;

/**
 * @author spjspj
 */
public class CubeFromDeck extends DraftCube {

    public CubeFromDeck(Deck cubeFromDeck) {
        super("Cube From Deck");

        DeckCardLists cards = null;
        if (cubeFromDeck != null) {
            cards = cubeFromDeck.getDeckCardLists();
        }

        if (cards != null) {
            for (DeckCardInfo card : cards.getCards()) {
                cubeCards.add(new CardIdentity(card.getCardName(), card.getSetCode(), card.getCardNum()));
            }
        }
    }
}
