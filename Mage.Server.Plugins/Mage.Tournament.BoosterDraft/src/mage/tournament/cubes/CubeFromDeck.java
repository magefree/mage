package mage.tournament.cubes;

import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.game.draft.DraftCube;

/**
 * @author spjspj, JayDi85
 */
public class CubeFromDeck extends DraftCube {

    /**
     * Calls on table create, can use any names
     */
    public CubeFromDeck(Deck cubeFromDeck) {
        this();

        if (cubeFromDeck == null) {
            return;
        }

        DeckCardLists cards = cubeFromDeck.prepareCardsOnlyDeck();
        for (DeckCardInfo card : cards.getCards()) {
            cubeCards.add(new CardIdentity(card.getCardName(), card.getSetCode(), card.getCardNumber()));
        }

        // add useful info about cubes, but don't print user defined data like deck name due security reasons
        this.setUpdateInfo(String.format("%d cards", cubeCards.size()));
    }

    /**
     * Calls on server's startng, must use default name - that's name will see all users after connection
     */
    public CubeFromDeck() {
        // don't change default name - new tourney dialog use it to choose a cube's deck
        super("Cube From Deck", "your custom cube", 0, 0, 0);
    }
}
