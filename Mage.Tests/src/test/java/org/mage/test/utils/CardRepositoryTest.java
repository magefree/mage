package org.mage.test.utils;

import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.CardScanner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Testing of CardRepository functionality.
 *
 * @author Alex-Vasile, JayDi85
 */
public class CardRepositoryTest {

    @Before
    public void setUp() {
        CardScanner.scan();
    }

    /**
     * Test CardRepository.findCards for the difficult cases when provided with the full card name.
     * <p>
     * CardRepository.findCards is used only for testing purposes.
     */
    @Test
    public void test_FindSplitCardsByFullName() {
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
     * <p>
     * CardRepository.findCards is used only for testing purposes.
     */
    @Test
    public void test_FindSplitCardsByMainName() {
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
     * <p>
     * CardRepository.findCards is used only for testing purposes.
     */
    @Test
    public void test_FindSplitCardsBySecondName() {
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
     * <p>
     * CardRepository.findCardsCaseInsensitive is used for actual game
     */
    @Test
    public void test_FindSplitCardsByFullNameCaseInsensitive() {
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
     * <p>
     * CardRepository.findCards is used only for testing purposes.
     */
    @Test
    public void test_FindSplitCardsByMainNameCaseInsensitive() {
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
     * <p>
     * CardRepository.findCards is used only for testing purposes.
     */
    @Test
    public void test_FindSplitCardsBySecondNameCaseInsensitive() {
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
     * Reported bug: https://github.com/magefree/mage/issues/9533
     * <p>
     * Each half of a split card displays the combined information of both halves in the deck editor.
     * <p>
     * `findCards`'s `returnSplitCardHalf` parameter should handle this issue
     */
    @Test
    public void test_splitCardInfoIsntDoubled() {
        // Consecrate   // Consume
        // {1}{W/B}     // {2}{W}{B}
        List<CardInfo> fullCard1 = CardRepository.instance.findCards("Consecrate", 1, false);
        Assert.assertTrue(fullCard1.get(0).isSplitCard());
        Assert.assertEquals("Consecrate // Consume", fullCard1.get(0).getName());
        List<CardInfo> fullCard2 = CardRepository.instance.findCards("Consume", 1, false);
        Assert.assertTrue(fullCard2.get(0).isSplitCard());
        Assert.assertEquals("Consecrate // Consume", fullCard2.get(0).getName());

        List<CardInfo> splitHalfCardLeft = CardRepository.instance.findCards("Consecrate", 1, true);
        Assert.assertTrue(splitHalfCardLeft.get(0).isSplitCardHalf());
        Assert.assertEquals("Consecrate", splitHalfCardLeft.get(0).getName());
        List<CardInfo> splitHalfCardRight = CardRepository.instance.findCards("Consume", 1, true);
        Assert.assertTrue(splitHalfCardRight.get(0).isSplitCardHalf());
        Assert.assertEquals("Consume", splitHalfCardRight.get(0).getName());
    }

    /**
     * Checks if the card with name cardName can be found when searched for
     * using the case-sensitive approach.
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

    /**
     * Some set can contain both main and second side cards with same card numbers,
     * so search result must return main side first
     */
    @Test
    public void test_SearchSecondSides_FindCard() {
        // XLN - Ixalan - Arguel's Blood Fast -> Temple of Aclazotz - 90
        Assert.assertEquals("Arguel's Blood Fast", CardRepository.instance.findCard("XLN", "90").getName());
        Assert.assertEquals("Arguel's Blood Fast", CardRepository.instance.findCard("XLN", "90", true).getName());
        Assert.assertEquals("Arguel's Blood Fast", CardRepository.instance.findCard("XLN", "90", false).getName());

        // VOW - Innistrad: Crimson Vow - Jacob Hauken, Inspector -> Hauken's Insight - 320
        Assert.assertEquals("Jacob Hauken, Inspector", CardRepository.instance.findCard("VOW", "320").getName());
        Assert.assertEquals("Jacob Hauken, Inspector", CardRepository.instance.findCard("VOW", "320", true).getName());
        Assert.assertEquals("Jacob Hauken, Inspector", CardRepository.instance.findCard("VOW", "320", false).getName());
    }

    @Test
    public void test_SearchSecondSides_FindCardWithPreferredSetAndNumber() {
        // VOW - Innistrad: Crimson Vow - Jacob Hauken, Inspector -> Hauken's Insight - 65
        // VOW - Innistrad: Crimson Vow - Jacob Hauken, Inspector -> Hauken's Insight - 320
        // VOW - Innistrad: Crimson Vow - Jacob Hauken, Inspector -> Hauken's Insight - 332
        Assert.assertEquals("65", CardRepository.instance.findCardWithPreferredSetAndNumber("Jacob Hauken, Inspector", "VOW", "65").getCardNumber());
        Assert.assertEquals("320", CardRepository.instance.findCardWithPreferredSetAndNumber("Jacob Hauken, Inspector", "VOW", "320").getCardNumber());
        Assert.assertEquals("332", CardRepository.instance.findCardWithPreferredSetAndNumber("Jacob Hauken, Inspector", "VOW", "332").getCardNumber());

        Assert.assertEquals("65", CardRepository.instance.findCardWithPreferredSetAndNumber("Hauken's Insight", "VOW", "65").getCardNumber());
        Assert.assertEquals("320", CardRepository.instance.findCardWithPreferredSetAndNumber("Hauken's Insight", "VOW", "320").getCardNumber());
        Assert.assertEquals("332", CardRepository.instance.findCardWithPreferredSetAndNumber("Hauken's Insight", "VOW", "332").getCardNumber());
    }
}
