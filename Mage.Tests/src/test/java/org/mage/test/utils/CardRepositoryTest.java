package org.mage.test.utils;

import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * Testing of CardRepository functionality.
 *
 * @author Alex-Vasile
 */
public class CardRepositoryTest {

    /**
     * Test CardRepository.findCards for the difficult cases when provided with the full card name.
     *
     * CardRepository.findCards is used only for testing purposes.
     */
    @Test
    public void testFindSplitCardsByFullName() {
        // Modal double-faced
        assertFindCaseSensitive("Malakir Rebirth // Malakir Mire");
        // Transform double-faced
        assertFindCaseSensitive("Brutal Cathar // Moonrage Brute");
        // Split
        assertFindCaseSensitive("Alive // Well");
        // Flip
        assertFindCaseSensitive("Rune-Tail, Kitsune Ascendant // Rune-Tail's Essence");
        // Adventure
        assertFindCaseSensitive("Ardenvale Tactician // Dizzying Swoop");
    }

    /**
     * Test CardRepository.findCards for the difficult cases.
     *
     * CardRepository.findCards is used only for testing purposes.
     */
    @Test
    public void testFindSplitCardsByMainName() {
        // Modal double-faced
        assertFindCaseSensitive("Malakir Rebirth");
        // Transform double-faced
        assertFindCaseSensitive("Brutal Cathar");
        // Split
        assertFindCaseSensitive("Alive");
        // Flip
        assertFindCaseSensitive("Rune-Tail, Kitsune Ascendant");
        // Adventure
        assertFindCaseSensitive("Ardenvale Tactician");
    }

    /**
     * Test CardRepository.findCards for the difficult cases.
     *
     * CardRepository.findCards is used only for testing purposes.
     */
    @Test
    public void testFindSplitCardsBySecondName() {
        // Modal double-faced
        assertFindCaseSensitive("Malakir Mire");
        // Transform double-faced
        assertFindCaseSensitive("Moonrage Brute");
        // Split
        assertFindCaseSensitive("Well");
        // Flip
        assertFindCaseSensitive("Rune-Tail's Essence");
        // Adventure
        assertFindCaseSensitive("Dizzying Swoop");
    }

    /**
     * Test CardRepository.findCardsCaseInsensitive for the difficult cases.
     *
     * CardRepository.findCardsCaseInsensitive is used for actual game
     */
    @Test
    public void testFindSplitCardsByFullNameCaseInsensitive() {
        // Modal double-faced
        assertFindCaseInsensitive("Malakir Rebirth // Malakir Mire");
        // Transform double-faced
        assertFindCaseInsensitive("Brutal Cathar // Moonrage Brute");
        // Split
        assertFindCaseInsensitive("Alive // Well");
        // Flip
        assertFindCaseInsensitive("Rune-Tail, Kitsune Ascendant // Rune-Tail's Essence");
        // Adventure
        assertFindCaseInsensitive("Ardenvale Tactician // Dizzying Swoop");
    }

    /**
     * Test CardRepository.findCards for the difficult cases.
     *
     * CardRepository.findCards is used only for testing purposes.
     */
    @Test
    public void testFindSplitCardsByMainNameCaseInsensitive() {
        // Modal double-faced
        assertFindCaseInsensitive("Malakir Rebirth");
        // Transform double-faced
        assertFindCaseInsensitive("Brutal Cathar");
        // Split
        assertFindCaseInsensitive("Alive");
        // Flip
        assertFindCaseInsensitive("Rune-Tail, Kitsune Ascendant");
        // Adventure
        assertFindCaseInsensitive("Ardenvale Tactician");
    }

    /**
     * Test CardRepository.findCards for the difficult cases.
     *
     * CardRepository.findCards is used only for testing purposes.
     */
    @Test
    public void testFindSplitCardsBySecondNameCaseInsensitive() {
        // Modal double-faced
        assertFindCaseInsensitive("Malakir Mire");
        // Transform double-faced
        assertFindCaseInsensitive("Moonrage Brute");
        // Split
        assertFindCaseInsensitive("Well");
        // Flip
        assertFindCaseInsensitive("Rune-Tail's Essence");
        // Adventure
        assertFindCaseInsensitive("Dizzying Swoop");
    }

    /**
     * Checks if the card with name cardName can be found when searched for
     * using the case sensitive approach.
     *
     * @param cardName The name of the card to search by.
     */
    private void assertFindCaseSensitive(String cardName) {
        List<CardInfo> foundCards = CardRepository.instance.findCards(cardName);
        Assert.assertFalse(
                "Could not find " + "\"" + cardName + "\".",
                foundCards.isEmpty()
        );
    }

    /**
     * Checks if the card with name cardName can be found when searched for
     * using the case insensitive approach.
     *
     * @param cardName The name of the card to search by.
     */
    private void assertFindCaseInsensitive(String cardName) {
        List<CardInfo> foundCards = CardRepository.instance.findCardsCaseInsensitive(cardName);
        Assert.assertFalse(
                "Could not find " + "\"" + cardName + "\".",
                foundCards.isEmpty()
        );
    }
}
