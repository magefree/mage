package org.mage.test.serverside.deck;

import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.CardScanner;
import mage.constants.ColoredManaSymbol;
import mage.player.ai.ComputerPlayer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestPlayerBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * AI: bot's deck building from a limited pool (draft, sealed and other bot's constructs)
 *
 * @author JayDi85
 */
public class BotDeckBuildTest extends MageTestPlayerBase {

    private static final int LIMITED_DECK_SIZE = 40;
    private static final int LIMITED_DECK_LANDS = 17;
    private static final int LIMITED_DECK_SPELLS = 23;

    @Before
    public void setUp() {
        CardScanner.scan();
    }

    @Test
    public void test_Limited_OnlySpells() {
        List<Card> pool = new ArrayList<>();
        addCards(pool, "Grizzly Bears", 10);
        addCards(pool, "Centaur Courser", 10);
        addCards(pool, "Llanowar Elves", 10);

        Deck deck = ComputerPlayer.buildDeck(LIMITED_DECK_SIZE, pool, Collections.singletonList(ColoredManaSymbol.G));
        assertDeck(deck, LIMITED_DECK_SIZE, LIMITED_DECK_LANDS, LIMITED_DECK_SPELLS);
    }

    @Test
    public void test_Limited_NonBasicLandsMustBeCountedAsLands() {
        // nonbasic lands from the pool must take lands slots, not spells slots
        List<Card> pool = new ArrayList<>();
        addCards(pool, "Grizzly Bears", 10);
        addCards(pool, "Centaur Courser", 10);
        addCards(pool, "Llanowar Elves", 10);
        addCards(pool, "Oakhollow Village", 3); // green nonbasic land with a high bot's rating (uncommon)

        Deck deck = ComputerPlayer.buildDeck(LIMITED_DECK_SIZE, pool, Collections.singletonList(ColoredManaSymbol.G));
        assertDeck(deck, LIMITED_DECK_SIZE, LIMITED_DECK_LANDS, LIMITED_DECK_SPELLS);
        Assert.assertEquals("nonbasic lands must be used", 3, deck.getMaindeckCards().stream()
                .filter(card -> card.getName().equals("Oakhollow Village"))
                .count());
    }

    @Test
    public void test_Limited_NonBasicLandsMustBeOnColor() {
        // nonbasic lands that make only other colors must not be used
        List<Card> pool = new ArrayList<>();
        addCards(pool, "Grizzly Bears", 10);
        addCards(pool, "Centaur Courser", 10);
        addCards(pool, "Llanowar Elves", 10);
        addCards(pool, "Oakhollow Village", 2); // G - on color
        addCards(pool, "Lilypad Village", 2); // U - off color
        addCards(pool, "Lupinflower Village", 2); // W - off color

        Deck deck = ComputerPlayer.buildDeck(LIMITED_DECK_SIZE, pool, Collections.singletonList(ColoredManaSymbol.G));
        assertDeck(deck, LIMITED_DECK_SIZE, LIMITED_DECK_LANDS, LIMITED_DECK_SPELLS);
        assertCardsCount(deck, "Oakhollow Village", 2);
        assertCardsCount(deck, "Lilypad Village", 0);
        assertCardsCount(deck, "Lupinflower Village", 0);
    }

    private void assertCardsCount(Deck deck, String cardName, int needAmount) {
        Assert.assertEquals("wrong amount of " + cardName, needAmount, deck.getMaindeckCards().stream()
                .filter(card -> card.getName().equals(cardName))
                .count());
    }

    private void addCards(List<Card> pool, String cardName, int amount) {
        CardInfo cardInfo = CardRepository.instance.findCard(cardName);
        Assert.assertNotNull("can't find card " + cardName, cardInfo);
        for (int i = 0; i < amount; i++) {
            pool.add(cardInfo.createCard());
        }
    }

    private void assertDeck(Deck deck, int needTotal, int needLands, int needSpells) {
        long lands = deck.getMaindeckCards().stream().filter(Card::isLand).count();
        long spells = deck.getMaindeckCards().stream().filter(card -> !card.isLand()).count();
        Assert.assertEquals("wrong lands count", needLands, lands);
        Assert.assertEquals("wrong spells count", needSpells, spells);
        Assert.assertEquals("wrong deck size", needTotal, deck.getMaindeckCards().size());
    }
}
