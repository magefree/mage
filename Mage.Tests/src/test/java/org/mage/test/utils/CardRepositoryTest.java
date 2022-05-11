package org.mage.test.utils;

import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Testing of CardRepository functionality.
 *
 * @author Alex-Vasile
 */
public class CardRepositoryTest {

    /**
     * Test CardRepository.findCards for the difficult cases.
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

    // TODO: Same three as above but for case-insensitive


    /**
     *
     *
     * @param cardName The name of the card to search by.
     */
    private void assertFindCaseSensitive(String cardName) {
        List<CardInfo> foundCards = CardRepository.instance.findCards(cardName);
        Assert.assertFalse(
                "Could not find " + "\"" + cardName + "\n",
                foundCards.isEmpty()
        );
    }

    /**
     *
     *
     * @param cardName The name of the card to search by.
     */
    private void assertFindCaseInsensitive(String cardName) {
        List<CardInfo> foundCards = CardRepository.instance.findCardsCaseInsensitive(cardName);
        Assert.assertFalse(
                "Could not find " + "\"" + cardName + "\n",
                foundCards.isEmpty()
        );
    }
}
