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
     * Test CardRepository.findCards for the difficult cases when provided with the full card name.
     *
     * CardRepository.findCards is used only for testing purposes.
     */
    @Test
    public void testFindSplitCardsByFullName() {
        // Modal double-faced
        assertFindCard("Malakir Rebirth // Malakir Mire");
        // Transform double-faced
        assertFindCard("Brutal Cathar // Moonrage Brute");
        // Split
        assertFindCard("Alive // Well");
        // Flip
        assertFindCard("Rune-Tail, Kitsune Ascendant // Rune-Tail's Essence");
        // Adventure
        assertFindCard("Ardenvale Tactician // Dizzying Swoop");
    }

    /**
     * Test CardRepository.findCards for the difficult cases.
     *
     * CardRepository.findCards is used only for testing purposes.
     */
    @Test
    public void testFindSplitCardsByMainName() {
        // Modal double-faced
        assertFindCard("Malakir Rebirth");
        // Transform double-faced
        assertFindCard("Brutal Cathar");
        // Split
        assertFindCard("Alive");
        // Flip
        assertFindCard("Rune-Tail, Kitsune Ascendant");
        // Adventure
        assertFindCard("Ardenvale Tactician");
    }

    /**
     * Test CardRepository.findCards for the difficult cases.
     *
     * CardRepository.findCards is used only for testing purposes.
     */
    @Test
    public void testFindSplitCardsBySecondName() {
        // Modal double-faced
        assertFindCard("Malakir Mire");
        // Transform double-faced
        assertFindCard("Moonrage Brute");
        // Split
        assertFindCard("Well");
        // Flip
        assertFindCard("Rune-Tail's Essence");
        // Adventure
        assertFindCard("Dizzying Swoop");
    }

    /**
     * Test CardRepository.findCardsCaseInsensitive for the difficult cases.
     *
     * CardRepository.findCardsCaseInsensitive is used for actual game
     */
    @Test
    public void testFindSplitCardsByFullNameCaseInsensitive() {
        // Modal double-faced
        assertFindCard("malakIR rebirTH // maLAkir mIRe");
        // Transform double-faced
        assertFindCard("brutAL cathAR // moonRAge BRUte");
        // Split
        assertFindCard("aliVE // wELl");
        // Flip
        assertFindCard("ruNE-taIL, kitsuNE ascendaNT // rUNe-tAIl's essENce");
        // Adventure
        assertFindCard("ardenvaLE tacticiAN // dizzYIng sWOop");
    }

    /**
     * Test CardRepository.findCards for the difficult cases.
     *
     * CardRepository.findCards is used only for testing purposes.
     */
    @Test
    public void testFindSplitCardsByMainNameCaseInsensitive() {
        // Modal double-faced
        assertFindCard("malakIR rebirTH");
        // Transform double-faced
        assertFindCard("brutAL cathAR");
        // Split
        assertFindCard("aliVE");
        // Flip
        assertFindCard("ruNE-taIL, kitsuNE ascendaNT");
        // Adventure
        assertFindCard("ardenvaLE tacticiAN");
    }

    /**
     * Test CardRepository.findCards for the difficult cases.
     *
     * CardRepository.findCards is used only for testing purposes.
     */
    @Test
    public void testFindSplitCardsBySecondNameCaseInsensitive() {
        // Modal double-faced
        assertFindCard("maLAkir mIRe");
        // Transform double-faced
        assertFindCard("moonRAge BRUte");
        // Split
        assertFindCard("wELl");
        // Flip
        assertFindCard("rUNe-tAIl's essENce");
        // Adventure
        assertFindCard("dizzYIng sWOop");
    }

    /**
     * Checks if the card with name cardName can be found when searched for
     * using the case sensitive approach.
     *
     * @param cardName The name of the card to search by.
     */
    private void assertFindCard(String cardName) {
        List<CardInfo> foundCards = CardRepository.instance.findCards(cardName);
        Assert.assertFalse(
                "Could not find " + "\"" + cardName + "\".",
                foundCards.isEmpty()
        );
    }
}
