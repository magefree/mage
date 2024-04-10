package org.mage.test.serverside.deck;

import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLayout;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestPlayerBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author JayDi85
 */
public class DeckHashTest extends MageTestPlayerBase {

    private static final DeckCardInfo BAD_CARD = new DeckCardInfo("unknown card", "123", "xxx");
    private static final DeckCardInfo GOOD_CARD_1 = new DeckCardInfo("Lightning Bolt", "141", "CLU");
    private static final DeckCardInfo GOOD_CARD_1_ANOTHER_SET = new DeckCardInfo("Lightning Bolt", "146", "M10");
    private static final DeckCardInfo GOOD_CARD_1_x3 = new DeckCardInfo("Lightning Bolt", "141", "CLU", 3);
    private static final DeckCardInfo GOOD_CARD_2 = new DeckCardInfo("Bear's Companion", "182", "2x2");
    private static final DeckCardInfo GOOD_CARD_3 = new DeckCardInfo("Amplifire", "92", "RNA");

    private void assertDecks(String check, boolean mustBeSame, Deck deck1, Deck deck2) {
        Assert.assertEquals(
                check + " - " + (mustBeSame ? "hash code must be same" : "hash code must be different"),
                mustBeSame,
                deck1.getDeckHash() == deck2.getDeckHash()
        );
    }

    private Deck prepareCardsDeck(List<DeckCardInfo> mainCards, List<DeckCardInfo> sideboardCards) {
        DeckCardLists deckSource = new DeckCardLists();
        deckSource.getCards().addAll(mainCards);
        deckSource.getSideboard().addAll(sideboardCards);
        try {
            return Deck.load(deckSource, true);
        } catch (GameException e) {
            throw new IllegalArgumentException("Must not catch exception, but found " + e, e);
        } catch (Throwable e) {
            Assert.fail("wtf");
        }
        return null;
    }

    private Deck prepareEmptyDeck(String name, String author, boolean addMainLayout, boolean addSideboardLayout) {
        DeckCardLists deckSource = new DeckCardLists();
        deckSource.setName(name);
        deckSource.setAuthor(author);
        if (addMainLayout) {
            deckSource.setCardLayout(new DeckCardLayout(new ArrayList<>(), "xxx"));
        }
        if (addSideboardLayout) {
            deckSource.setCardLayout(new DeckCardLayout(new ArrayList<>(), "xxx"));
        }
        try {
            return Deck.load(deckSource, true);
        } catch (GameException e) {
            throw new IllegalArgumentException("Must not catch exception, but found " + e, e);
        }
    }

    @Test
    public void test_MustIgnoreNonCardsData() {
        // additional info must be ignored for deck hash
        Deck emptyDeck = new Deck();
        assertDecks("empty with empty", true, emptyDeck, emptyDeck);
        assertDecks("empty with new empty", true, emptyDeck, new Deck());

        assertDecks("empty with name", true, emptyDeck, prepareEmptyDeck("name", "", false, false));
        assertDecks("empty with author", true, emptyDeck, prepareEmptyDeck("", "author", false, false));
        assertDecks("empty with layout main", true, emptyDeck, prepareEmptyDeck("", "", true, false));
        assertDecks("empty with layout sideboard", true, emptyDeck, prepareEmptyDeck("", "", false, true));
        assertDecks("empty with all", true, emptyDeck, prepareEmptyDeck("name", "author", true, true));

        assertDecks("empty with non empty", false, emptyDeck, prepareCardsDeck(Arrays.asList(GOOD_CARD_1), Arrays.asList()));
    }

    @Test
    public void test_MustUseCardNameAndAmountOnly() {
        // card names and amount must be important
        // set and card number must be ignored

        assertDecks(
                "empty",
                true,
                prepareCardsDeck(Arrays.asList(), Arrays.asList()),
                prepareCardsDeck(Arrays.asList(), Arrays.asList())
        );

        assertDecks(
                "same names",
                true,
                prepareCardsDeck(Arrays.asList(GOOD_CARD_1), Arrays.asList()),
                prepareCardsDeck(Arrays.asList(GOOD_CARD_1), Arrays.asList())
        );

        assertDecks(
                "same names but diff sets",
                true,
                prepareCardsDeck(Arrays.asList(GOOD_CARD_1), Arrays.asList()),
                prepareCardsDeck(Arrays.asList(GOOD_CARD_1_ANOTHER_SET), Arrays.asList())
        );

        assertDecks(
                "diff names",
                false,
                prepareCardsDeck(Arrays.asList(GOOD_CARD_1), Arrays.asList()),
                prepareCardsDeck(Arrays.asList(GOOD_CARD_2), Arrays.asList())
        );

        assertDecks(
                "same main, same side",
                true,
                prepareCardsDeck(Arrays.asList(GOOD_CARD_1), Arrays.asList(GOOD_CARD_3)),
                prepareCardsDeck(Arrays.asList(GOOD_CARD_1), Arrays.asList(GOOD_CARD_3))
        );

        assertDecks(
                "diff main, same side",
                false,
                prepareCardsDeck(Arrays.asList(GOOD_CARD_1), Arrays.asList(GOOD_CARD_3)),
                prepareCardsDeck(Arrays.asList(GOOD_CARD_2), Arrays.asList(GOOD_CARD_3))
        );

        assertDecks(
                "same main, diff side",
                false,
                prepareCardsDeck(Arrays.asList(GOOD_CARD_1), Arrays.asList(GOOD_CARD_2)),
                prepareCardsDeck(Arrays.asList(GOOD_CARD_1), Arrays.asList(GOOD_CARD_3))
        );

        assertDecks(
                "same names, but diff amount - main",
                false,
                prepareCardsDeck(Arrays.asList(GOOD_CARD_1), Arrays.asList()),
                prepareCardsDeck(Arrays.asList(GOOD_CARD_1_x3), Arrays.asList())
        );

        assertDecks(
                "same names, but diff amount - side",
                false,
                prepareCardsDeck(Arrays.asList(), Arrays.asList(GOOD_CARD_1)),
                prepareCardsDeck(Arrays.asList(), Arrays.asList(GOOD_CARD_1_x3))
        );
    }

    @Test
    public void test_MustIgnoreCardsOrder() {
        // deck hash must use sorted list, so deck order must be ignored

        assertDecks(
                "diff order - main",
                true,
                prepareCardsDeck(Arrays.asList(GOOD_CARD_1, GOOD_CARD_1, GOOD_CARD_1), Arrays.asList()),
                prepareCardsDeck(Arrays.asList(GOOD_CARD_1_x3), Arrays.asList())
        );

        assertDecks(
                "diff order - side",
                true,
                prepareCardsDeck(Arrays.asList(), Arrays.asList(GOOD_CARD_1, GOOD_CARD_1, GOOD_CARD_1)),
                prepareCardsDeck(Arrays.asList(), Arrays.asList(GOOD_CARD_1_x3))
        );

        assertDecks(
                "diff order - diff names",
                true,
                prepareCardsDeck(Arrays.asList(GOOD_CARD_1, GOOD_CARD_2, GOOD_CARD_3), Arrays.asList()),
                prepareCardsDeck(Arrays.asList(GOOD_CARD_3, GOOD_CARD_1, GOOD_CARD_2), Arrays.asList())
        );
    }

    @Test
    public void test_MustIgnoreUnknownCards() {
        // hash must use only real cards and ignore missing

        assertDecks(
                "empty and unknown",
                true,
                prepareCardsDeck(Arrays.asList(), Arrays.asList()),
                prepareCardsDeck(Arrays.asList(BAD_CARD), Arrays.asList())
        );

        assertDecks(
                "unknown and unknown",
                true,
                prepareCardsDeck(Arrays.asList(BAD_CARD), Arrays.asList()),
                prepareCardsDeck(Arrays.asList(BAD_CARD), Arrays.asList())
        );

        assertDecks(
                "unknown and x2 unknown",
                true,
                prepareCardsDeck(Arrays.asList(BAD_CARD), Arrays.asList()),
                prepareCardsDeck(Arrays.asList(BAD_CARD, BAD_CARD), Arrays.asList())
        );

        assertDecks(
                "card and unknown",
                false,
                prepareCardsDeck(Arrays.asList(GOOD_CARD_1), Arrays.asList()),
                prepareCardsDeck(Arrays.asList(BAD_CARD), Arrays.asList())
        );

        assertDecks(
                "card + unknown and unknown",
                false,
                prepareCardsDeck(Arrays.asList(BAD_CARD, GOOD_CARD_1), Arrays.asList()),
                prepareCardsDeck(Arrays.asList(BAD_CARD), Arrays.asList())
        );

        assertDecks(
                "card + unknown and card + unknown",
                true,
                prepareCardsDeck(Arrays.asList(BAD_CARD, GOOD_CARD_1), Arrays.asList()),
                prepareCardsDeck(Arrays.asList(GOOD_CARD_1, BAD_CARD), Arrays.asList())
        );
    }

    @Test
    public void test_MustRaiseErrorOnTooManyCards() {
        // good amount
        Assert.assertEquals(90, new DeckCardInfo(GOOD_CARD_1.getCardName(), GOOD_CARD_1.getCardNumber(), GOOD_CARD_1.getSetCode(), 90).getAmount());

        // bad amount
        try {
            new DeckCardInfo(GOOD_CARD_1.getCardName(), GOOD_CARD_1.getCardNumber(), GOOD_CARD_1.getSetCode(), 100);
        } catch (Exception e) {
            if (e.getMessage().contains("Found too big amount")) {
                // good
                return;
            }
        }
        Assert.fail("must raise exception on too big amount");
    }
}
