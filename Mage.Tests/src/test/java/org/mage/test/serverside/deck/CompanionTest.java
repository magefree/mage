package org.mage.test.serverside.deck;

import mage.deck.Commander;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestBase;

import java.util.Arrays;
import java.util.List;

import static org.mage.test.serverside.deck.DeckValidationUtil.testDeckValid;

/**
 * @author TheElk801
 */
public class CompanionTest extends MageTestBase {

    @Test
    public void testKaheeraFalseMaindeck() {
        List<DeckValidationUtil.CardNameAmount> deck = Arrays.asList(
                new DeckValidationUtil.CardNameAmount("Forest", 98),
                new DeckValidationUtil.CardNameAmount("Cylian Elf", 1)
        );

        List<DeckValidationUtil.CardNameAmount> sideboard = Arrays.asList(
                new DeckValidationUtil.CardNameAmount("Morophon, the Boundless", 1),
                new DeckValidationUtil.CardNameAmount("Kaheera, the Orphanguard", 1)
        );

        Assert.assertFalse(
                "Maindeck must have all legal creature types with Kaheera as a companion",
                testDeckValid(new Commander(), deck, sideboard)
        );
    }

    @Test
    public void testKaheeraFalseCommander() {
        List<DeckValidationUtil.CardNameAmount> deck = Arrays.asList(
                new DeckValidationUtil.CardNameAmount("Forest", 98),
                new DeckValidationUtil.CardNameAmount("Silvercoat Lion", 1)
        );

        List<DeckValidationUtil.CardNameAmount> sideboard = Arrays.asList(
                new DeckValidationUtil.CardNameAmount("Jasmine Boreal", 1),
                new DeckValidationUtil.CardNameAmount("Kaheera, the Orphanguard", 1)
        );

        Assert.assertFalse(
                "Commander must be valid with Kaheera as a companion",
                testDeckValid(new Commander(), deck, sideboard)
        );
    }

    @Test
    public void testKaheeraChangeling() {
        // Changelings can be in a deck Kaheera as companion as their first abilities apply during deck construction.
        List<DeckValidationUtil.CardNameAmount> deck = Arrays.asList(
                new DeckValidationUtil.CardNameAmount("Forest", 98),
                new DeckValidationUtil.CardNameAmount("Woodland Changeling", 1)
        );

        List<DeckValidationUtil.CardNameAmount> sideboard = Arrays.asList(
                new DeckValidationUtil.CardNameAmount("Morophon, the Boundless", 1),
                new DeckValidationUtil.CardNameAmount("Kaheera, the Orphanguard", 1)
        );

        Assert.assertTrue(
                "Changelings are legal with Kaheera as a companion",
                testDeckValid(new Commander(), deck, sideboard)
        );
    }

    @Test
    public void testGristKaheera() {
        // Grist, the Hunger Tide can't be in a deck with Kaheera as companion as its first ability applies during deck construction.
        List<DeckValidationUtil.CardNameAmount> deck = Arrays.asList(
                new DeckValidationUtil.CardNameAmount("Forest", 98),
                new DeckValidationUtil.CardNameAmount("Grist, the Hunger Tide", 1)
        );

        List<DeckValidationUtil.CardNameAmount> sideboard = Arrays.asList(
                new DeckValidationUtil.CardNameAmount("Doran, the Siege Tower", 1),
                new DeckValidationUtil.CardNameAmount("Kaheera, the Orphanguard", 1)
        );

        Assert.assertFalse(
                "Grist is not legal with Kaheera as a companion",
                testDeckValid(new Commander(), deck, sideboard)
        );
    }

    @Test
    public void testGristUmoriCreature() {
        // Grist, the Hunger Tide can be in a creature deck with Umori as companion as its first ability applies during deck construction.
        List<DeckValidationUtil.CardNameAmount> deck = Arrays.asList(
                new DeckValidationUtil.CardNameAmount("Forest", 97),
                new DeckValidationUtil.CardNameAmount("Grizzly Bears", 1),
                new DeckValidationUtil.CardNameAmount("Grist, the Hunger Tide", 1)
        );

        List<DeckValidationUtil.CardNameAmount> sideboard = Arrays.asList(
                new DeckValidationUtil.CardNameAmount("Nath of the Gilt-Leaf", 1),
                new DeckValidationUtil.CardNameAmount("Umori, the Collector", 1)
        );

        Assert.assertTrue(
                "Grist is legal as a creature with Umori as a companion",
                testDeckValid(new Commander(), deck, sideboard)
        );
    }

    @Test
    public void testGristUmoriPlaneswalker() {
        // Grist, the Hunger Tide can be in a planeswalker deck with Umori as companion as it's still a planeswalker
        List<DeckValidationUtil.CardNameAmount> deck = Arrays.asList(
                new DeckValidationUtil.CardNameAmount("Forest", 97),
                new DeckValidationUtil.CardNameAmount("Garruk Wildspeaker", 1),
                new DeckValidationUtil.CardNameAmount("Grist, the Hunger Tide", 1)
        );

        List<DeckValidationUtil.CardNameAmount> sideboard = Arrays.asList(
                new DeckValidationUtil.CardNameAmount("Lord Windgrace", 1),
                new DeckValidationUtil.CardNameAmount("Umori, the Collector", 1)
        );

        Assert.assertTrue(
                "Grist is legal as a planeswalker with Umori as a companion",
                testDeckValid(new Commander(), deck, sideboard)
        );
    }
}
