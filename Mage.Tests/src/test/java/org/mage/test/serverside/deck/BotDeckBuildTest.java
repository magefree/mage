package org.mage.test.serverside.deck;

import mage.cards.Card;
import mage.cards.RateCard;
import mage.cards.decks.CardNameUtil;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * AI: bot's deck building from a limited pool (draft, sealed and other bot's constructs)
 * <p>
 * Warning, a card with draft ratings from resources/ratings gets a score from it instead a rarity, so deck building
 * tests must use cards without ratings (or check the ratings files after the update)
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
        addCards(pool, "Treetop Village", 3); // green nonbasic land with a high bot's rating (uncommon)

        Deck deck = ComputerPlayer.buildDeck(LIMITED_DECK_SIZE, pool, Collections.singletonList(ColoredManaSymbol.G));
        assertDeck(deck, LIMITED_DECK_SIZE, LIMITED_DECK_LANDS, LIMITED_DECK_SPELLS);
        Assert.assertEquals("nonbasic lands must be used", 3, deck.getMaindeckCards().stream()
                .filter(card -> card.getName().equals("Treetop Village"))
                .count());
    }

    @Test
    public void test_Limited_NonBasicLandsMustBeOnColor() {
        // nonbasic lands that make only other colors must not be used
        List<Card> pool = new ArrayList<>();
        addCards(pool, "Grizzly Bears", 10);
        addCards(pool, "Centaur Courser", 10);
        addCards(pool, "Llanowar Elves", 10);
        addCards(pool, "Treetop Village", 2); // G - on color
        addCards(pool, "Faerie Conclave", 2); // U - off color
        addCards(pool, "Forbidding Watchtower", 2); // W - off color

        Deck deck = ComputerPlayer.buildDeck(LIMITED_DECK_SIZE, pool, Collections.singletonList(ColoredManaSymbol.G));
        assertDeck(deck, LIMITED_DECK_SIZE, LIMITED_DECK_LANDS, LIMITED_DECK_SPELLS);
        assertCardsCount(deck, "Treetop Village", 2);
        assertCardsCount(deck, "Faerie Conclave", 0);
        assertCardsCount(deck, "Forbidding Watchtower", 0);
    }

    @Test
    public void test_Limited_HybridManaMustUseDeckColorsOnly() {
        // hybrid mana symbols must not add basic lands of other colors
        List<Card> pool = new ArrayList<>();
        addCards(pool, "Grizzly Bears", 13); // {1}{G}
        addCards(pool, "Kitchen Finks", 5); // {1}{G/W}{G/W}
        addCards(pool, "Boggart Ram-Gang", 5); // {R/G}{R/G}{R/G}

        Deck deck = ComputerPlayer.buildDeck(LIMITED_DECK_SIZE, pool, Collections.singletonList(ColoredManaSymbol.G));
        assertDeck(deck, LIMITED_DECK_SIZE, LIMITED_DECK_LANDS, LIMITED_DECK_SPELLS);
        assertCardsCount(deck, "Forest", LIMITED_DECK_LANDS);
    }

    @Test
    public void test_Limited_LandsMustBeExactForEqualColors() {
        // lands split by colors must keep the lands count (no round up for both colors)
        List<Card> pool = new ArrayList<>();
        addCards(pool, "Grizzly Bears", 10); // {1}{G}
        addCards(pool, "Savannah Lions", 10); // {W}
        addCards(pool, "Ornithopter", 3); // {0}

        Deck deck = ComputerPlayer.buildDeck(LIMITED_DECK_SIZE, pool, Arrays.asList(ColoredManaSymbol.G, ColoredManaSymbol.W));
        assertDeck(deck, LIMITED_DECK_SIZE, LIMITED_DECK_LANDS, LIMITED_DECK_SPELLS);
        long forests = deck.getMaindeckCards().stream().filter(card -> card.getName().equals("Forest")).count();
        long plains = deck.getMaindeckCards().stream().filter(card -> card.getName().equals("Plains")).count();
        Assert.assertTrue("lands must be split equally: forests " + forests + ", plains " + plains,
                (forests == 8 && plains == 9) || (forests == 9 && plains == 8));
    }

    @Test
    public void test_Limited_RedDeckMustNotGetPlains() {
        // remaining lands after the colors split must go to the deck colors
        List<Card> pool = new ArrayList<>();
        addCards(pool, "Raging Goblin", 10); // {R}
        addCards(pool, "Grizzly Bears", 7); // {1}{G}
        addCards(pool, "Walking Corpse", 6); // {1}{B}

        Deck deck = ComputerPlayer.buildDeck(LIMITED_DECK_SIZE, pool, Arrays.asList(ColoredManaSymbol.R, ColoredManaSymbol.G, ColoredManaSymbol.B));
        assertDeck(deck, LIMITED_DECK_SIZE, LIMITED_DECK_LANDS, LIMITED_DECK_SPELLS);
        assertCardsCount(deck, "Plains", 0);
        assertCardsCount(deck, "Mountain", 7);
        assertCardsCount(deck, "Forest", 5);
        assertCardsCount(deck, "Swamp", 5);
    }

    @Test
    public void test_Limited_HybridCardsMustBeOnColor() {
        // hybrid cards without any deck color must not be used
        List<Card> pool = new ArrayList<>();
        addCards(pool, "Grizzly Bears", 25); // {1}{G}
        addCards(pool, "Kitchen Finks", 3); // {1}{G/W}{G/W} - on color
        addCards(pool, "Seedpod Squire", 3); // {3}{W/U} - off color
        addCards(pool, "Boros Reckoner", 3); // {R/W}{R/W}{R/W} - off color

        Deck deck = ComputerPlayer.buildDeck(LIMITED_DECK_SIZE, pool, Collections.singletonList(ColoredManaSymbol.G));
        assertDeck(deck, LIMITED_DECK_SIZE, LIMITED_DECK_LANDS, LIMITED_DECK_SPELLS);
        assertCardsCount(deck, "Kitchen Finks", 3);
        assertCardsCount(deck, "Seedpod Squire", 0);
        assertCardsCount(deck, "Boros Reckoner", 0);
        assertCardsCount(deck, "Forest", LIMITED_DECK_LANDS);
    }

    @Test
    public void test_Limited_DefaultDeckSize() {
        // deck size 0 must build default 40 cards deck with spells, not lands only
        List<Card> pool = new ArrayList<>();
        addCards(pool, "Grizzly Bears", 30);

        Deck deck = ComputerPlayer.buildDeck(0, pool, Collections.singletonList(ColoredManaSymbol.G));
        assertDeck(deck, LIMITED_DECK_SIZE, LIMITED_DECK_LANDS, LIMITED_DECK_SPELLS);
    }

    @Test
    public void test_Rating_SplitCardMustUseBestHalf() {
        // split card can be cast by any half, so a red deck can use Fire // Ice
        Card fireIce = createCard("Fire // Ice"); // {1}{R} // {1}{U}
        int scoreRed = RateCard.rateCard(fireIce, Collections.singletonList(ColoredManaSymbol.R), false);
        int scoreRedBlue = RateCard.rateCard(fireIce, Arrays.asList(ColoredManaSymbol.R, ColoredManaSymbol.U), false);
        int scoreGreen = RateCard.rateCard(fireIce, Collections.singletonList(ColoredManaSymbol.G), false);
        Assert.assertEquals("red deck must use red half", scoreRedBlue, scoreRed);
        Assert.assertFalse("red deck must use red half", RateCard.isOffColorCard(fireIce, Collections.singletonList(ColoredManaSymbol.R)));
        Assert.assertTrue("green deck can't use any half", RateCard.isOffColorCard(fireIce, Collections.singletonList(ColoredManaSymbol.G)));
        Assert.assertTrue("green deck can't use any half", scoreGreen < scoreRed);
    }

    @Test
    public void test_Rating_ExpensiveCardMustNotBeBetterByCostOnly() {
        // same rarity, type and colored symbols, but diff mana value
        List<ColoredManaSymbol> colors = Collections.singletonList(ColoredManaSymbol.G);
        int scoreCheap = RateCard.rateCard(createCard("Grizzly Bears"), colors, false); // {1}{G}, common creature
        int scoreExpensive = RateCard.rateCard(createCard("Spined Wurm"), colors, false); // {4}{G}, common creature
        Assert.assertEquals("mana value must not change a score", scoreCheap, scoreExpensive);
    }

    @Test
    public void test_Rating_FileRatingsMustUseFullRange() {
        // ratings of every file are normalized to [1..100], 0 means no rating (rarity rating will be used instead)
        // LCI: The Millennium Calendar is the worst card (and mythic), Bonehoard Dracosaur is the best card
        Assert.assertEquals("worst card of the set must get min rating, not rarity rating",
                1, RateCard.getBaseCardScore(createCard("The Millennium Calendar")));
        Assert.assertEquals("best card of the set must get max rating",
                100, RateCard.getBaseCardScore(createCard("Bonehoard Dracosaur")));
    }

    @Test
    public void test_Rating_FileRatingsMustSupportUnicodeNames() {
        // ratings files keep original card names (17Lands: Tura Kennerüd), so it must be found by xmage names (ascii)
        Assert.assertEquals("Aether Vial", CardNameUtil.normalizeCardName("Æther Vial"));
        Assert.assertEquals("Yahenni's Expertise", CardNameUtil.normalizeCardName("Yahenni’s Expertise"));
        Assert.assertEquals("Kongming, \"Sleeping Dragon\"", CardNameUtil.normalizeCardName("Kongming, “Sleeping Dragon”"));

        // uncommon cards from 17Lands files: DMU Tura Kennerüd, Skyknight and LCI Bartolomé del Presidio
        Assert.assertNotEquals("must use file rating, not uncommon rarity rating",
                60, RateCard.getBaseCardScore(createCard("Tura Kennerud, Skyknight")));
        Assert.assertNotEquals("must use file rating, not uncommon rarity rating",
                60, RateCard.getBaseCardScore(createCard("Bartolome del Presidio")));
    }

    @Test
    public void test_Rating_SetsListMustIgnoreEmptyLines() {
        // empty line in setsWithRatings.csv must not stop the loading of other sets
        List<String> lines = Arrays.asList("# comment", "", "lci", "   ", " fra ", "#dom");
        Assert.assertEquals(Arrays.asList("lci", "fra"), RateCard.parseSetsList(lines));
    }

    @Test
    public void test_Limited_SplitCardMustUseLandsForCastableHalfOnly() {
        // red deck with Fire // Ice needs Mountains only
        List<Card> pool = new ArrayList<>();
        addCards(pool, "Raging Goblin", 15); // {R}
        addCards(pool, "Fire // Ice", 8); // {1}{R} // {1}{U}

        Deck deck = ComputerPlayer.buildDeck(LIMITED_DECK_SIZE, pool, Collections.singletonList(ColoredManaSymbol.R));
        assertDeck(deck, LIMITED_DECK_SIZE, LIMITED_DECK_LANDS, LIMITED_DECK_SPELLS);
        assertCardsCount(deck, "Fire // Ice", 8);
        assertCardsCount(deck, "Mountain", LIMITED_DECK_LANDS);
    }

    @Test
    public void test_Limited_MinCreatures() {
        // deck must have 15 creatures if the pool has it
        List<Card> pool = new ArrayList<>();
        addCards(pool, "Harmonize", 15); // {2}{G}{G}, uncommon sorcery
        addCards(pool, "Explosive Vegetation", 8); // {3}{G}, uncommon sorcery
        addCards(pool, "Grizzly Bears", 20); // {1}{G}, common creature

        Deck deck = ComputerPlayer.buildDeck(LIMITED_DECK_SIZE, pool, Collections.singletonList(ColoredManaSymbol.G));
        assertDeck(deck, LIMITED_DECK_SIZE, LIMITED_DECK_LANDS, LIMITED_DECK_SPELLS);
        assertCardsCount(deck, "Grizzly Bears", 15);
    }

    @Test
    public void test_Limited_MaxExpensiveCards() {
        // deck must have max 3 cards with mana value 6+
        List<Card> pool = new ArrayList<>();
        addCards(pool, "Avenger of Zendikar", 10); // {5}{G}{G}, mythic creature
        addCards(pool, "Grizzly Bears", 30); // {1}{G}, common creature

        Deck deck = ComputerPlayer.buildDeck(LIMITED_DECK_SIZE, pool, Collections.singletonList(ColoredManaSymbol.G));
        assertDeck(deck, LIMITED_DECK_SIZE, LIMITED_DECK_LANDS, LIMITED_DECK_SPELLS);
        assertCardsCount(deck, "Avenger of Zendikar", 3);
    }

    @Test
    public void test_Limited_MinCheapCards() {
        // deck must have min 4 cards with mana value 2 or less
        List<Card> pool = new ArrayList<>();
        addCards(pool, "Ravenous Baloth", 25); // {2}{G}{G}, rare creature
        addCards(pool, "Grizzly Bears", 10); // {1}{G}, common creature

        Deck deck = ComputerPlayer.buildDeck(LIMITED_DECK_SIZE, pool, Collections.singletonList(ColoredManaSymbol.G));
        assertDeck(deck, LIMITED_DECK_SIZE, LIMITED_DECK_LANDS, LIMITED_DECK_SPELLS);
        assertCardsCount(deck, "Grizzly Bears", 4);
    }

    @Test
    public void test_Limited_RoomIsNotExpensiveCard() {
        // room card can be cast by any half, so it's not a card with mana value 6+ (cheapest half is used)
        List<Card> pool = new ArrayList<>();
        addCards(pool, "Bottomless Pool // Locker Room", 6); // {U} // {4}{U}, mana value of the whole card is 6
        addCards(pool, "Coral Merfolk", 30); // {1}{U}, common creature

        Deck deck = ComputerPlayer.buildDeck(LIMITED_DECK_SIZE, pool, Collections.singletonList(ColoredManaSymbol.U));
        assertDeck(deck, LIMITED_DECK_SIZE, LIMITED_DECK_LANDS, LIMITED_DECK_SPELLS);
        assertCardsCount(deck, "Bottomless Pool // Locker Room", 6);
    }

    private Card createCard(String cardName) {
        CardInfo cardInfo = CardRepository.instance.findCard(cardName);
        Assert.assertNotNull("can't find card " + cardName, cardInfo);
        return cardInfo.createCard();
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
