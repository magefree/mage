package org.mage.test.serverside.deck;

import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;
import mage.util.DeckBuildUtils;
import mage.util.TournamentUtil;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestPlayerBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Testing lands suggestion and adding. It used in:
 * - client side: for auto-lands suggest button
 * - server side: for AI and invalid deck's timeout
 *
 * @author JayDi85
 */
public class DeckAutoLandsTest extends MageTestPlayerBase {

    @Test
    public void test_LandsSuggest_OneColor() {
        Deck deck = prepareDeck(Arrays.asList(
                new DeckCardInfo("Grizzly Bears", "129", "S99", 40)
        ));
        assertSuggestedLands("one color", deck, 60, 0, 0, 0, 0, 20);
    }

    @Test
    public void test_LandsSuggest_TwoColors() {
        Deck deck = prepareDeck(Arrays.asList(
                new DeckCardInfo("Arbalest Engineers", "206", "BRO", 40)
        ));
        assertSuggestedLands("two colors", deck, 60, 0, 0, 0, 10, 10);
    }

    @Test
    public void test_LandsSuggest_ThreeColors() {
        Deck deck = prepareDeck(Arrays.asList(
                new DeckCardInfo("Adun Oakenshield", "216", "LEG", 40)
        ));
        assertSuggestedLands("tree colors", deck, 60, 0, 0, 7, 7, 6);
    }

    @Test
    public void test_LandsSuggest_FiveColors() {
        Deck deck = prepareDeck(Arrays.asList(
                new DeckCardInfo("Atogatog", "286", "ODY", 40)
        ));
        assertSuggestedLands("five colors", deck, 60, 4, 4, 4, 4, 4);
    }

    @Test
    public void test_LandsSuggest_NoColors() {
        Deck deck = prepareDeck(Arrays.asList(
                new DeckCardInfo("Abstruse Archaic", "712", "CMM", 40)
        ));
        assertSuggestedLands("no colors", deck, 60, 4, 4, 4, 4, 4);
    }

    @Test
    public void test_LandsSuggest_NoNeedLands() {
        Deck deck = prepareDeck(Arrays.asList(
                new DeckCardInfo("Grizzly Bears", "129", "S99", 40)
        ));
        assertSuggestedLands("no need lands - 39", deck, 39, 0, 0, 0, 0, 0);
        assertSuggestedLands("no need lands - 40", deck, 40, 0, 0, 0, 0, 0);
        assertSuggestedLands("no need lands - 41", deck, 41, 0, 0, 0, 0, 1);
    }

    private Deck prepareDeck(List<DeckCardInfo> cards) {
        DeckCardLists source = new DeckCardLists();
        source.getCards().addAll(cards);
        Deck deck = null;
        try {
            deck = Deck.load(source, true);
        } catch (GameException e) {
            Assert.fail("Can't prepare deck: " + cards);
        }
        return deck;
    }

    private void assertSuggestedLands(
            String info,
            Deck deck,
            int needTotal,
            int needPlains,
            int needIslands,
            int needSwamps,
            int needMountains,
            int needForests) {
        int[] lands = DeckBuildUtils.landCountSuggestion(needTotal, deck.getMaindeckCards());
        List<Integer> current = new ArrayList<>(Arrays.asList(lands[0], lands[1], lands[2], lands[3], lands[4]));
        List<Integer> need = new ArrayList<>(Arrays.asList(needPlains, needIslands, needSwamps, needMountains, needForests));
        Assert.assertTrue(info + " - wrong deck size", deck.getMaindeckCards().size() + current.stream().mapToInt(x -> x).sum() >= needTotal);
        Assert.assertEquals(info + " - wrong lands count (WUBRG)", need, current);
    }

    @Test
    public void test_PossibleSets_OneCompatibleSet() {
        Deck deck = prepareDeck(Arrays.asList(
                new DeckCardInfo("Grizzly Bears", "129", "S99", 1)
        ));
        assertPossibleSets("one compatible set", deck, Arrays.asList("S99"));
    }

    @Test
    public void test_PossibleSets_MakeSureNoSnowLands() {
        Deck deck = prepareDeck(Arrays.asList(
                new DeckCardInfo("Grizzly Bears", "129", "S99", 1),
                new DeckCardInfo("Abominable Treefolk", "194", "MH1", 1) // MH1 with snow lands
        ));
        assertPossibleSets("no snow lands", deck, Arrays.asList("S99"));
    }

    @Test
    public void test_PossibleSets_MultipleCompatibleSets() {
        Deck deck = prepareDeck(Arrays.asList(
                new DeckCardInfo("Grizzly Bears", "129", "S99", 1),
                new DeckCardInfo("Akki Raider", "92", "BOK", 1), // BOK without lands, but with boosters
                new DeckCardInfo("Grizzly Bears", "169", "POR", 1),
                new DeckCardInfo("Aggravated Assault", "25", "MP2", 1) // MP2 without lands and boosters
        ));
        assertPossibleSets("multiple compatible sets", deck, Arrays.asList("POR", "S99"));
    }

    @Test
    public void test_PossibleSets_CompatibleBlocks() {
        // BOK from Kamigawa block, so it must look at:
        // * CHK - Champions of Kamigawa - has lands
        // * SOK - Saviors of Kamigawa - no lands
        // * BOK - Betrayers of Kamigawa - no lands
        Deck deck = prepareDeck(Arrays.asList(
                new DeckCardInfo("Akki Raider", "92", "BOK", 1), // BOK without lands, but with boosters
                new DeckCardInfo("Aggravated Assault", "25", "MP2", 1) // MP2 without lands and boosters
        ));
        assertPossibleSets("compatible block", deck, Arrays.asList("CHK"));
    }

    @Test
    public void test_PossibleSets_NoCompatibleSetsOrBlocks() {
        Deck deck = prepareDeck(Arrays.asList(
                new DeckCardInfo("Amulet of Kroog", "36", "ATQ", 1) // ATQ without lands
        ));

        // must find random sets
        int tries = 3;
        List<String> possibleSets = new ArrayList<>();
        for (int i = 0; i < tries; ++i) {
            possibleSets.addAll(TournamentUtil.getLandSetCodeForDeckSets(deck.getExpansionSetCodes()).stream().sorted().collect(Collectors.toList()));
        }
        Assert.assertEquals("must find 1 set per request, but get " + possibleSets, tries, possibleSets.size());
        Assert.assertNotEquals("must find different random sets, but get " + possibleSets, 1, possibleSets.stream().distinct().count());
    }

    private void assertPossibleSets(
            String info,
            Deck deck,
            List<String> needSets) {
        List<String> possibleSets = TournamentUtil.getLandSetCodeForDeckSets(deck.getExpansionSetCodes()).stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(info + " - wrong possible sets", needSets, possibleSets);
    }
}
