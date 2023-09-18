package org.mage.test.serverside.deck;

import mage.cards.decks.DeckValidator;
import mage.deck.Commander;
import mage.deck.Limited;
import mage.deck.Modern;
import mage.deck.Standard;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestBase;

import java.util.ArrayList;

import static org.mage.test.serverside.deck.DeckValidationUtil.testDeckValid;

/**
 * @author LevelX2
 */
public class DeckValidatorTest extends MageTestBase {

    @Test
    public void testStandardDeckCardsAmountValid() {
        DeckTester deckTester = new DeckTester(new Standard());
        deckTester.addMaindeck("Mountain", 60);
        deckTester.validate();
    }

    @Test
    public void testStandardDeckCardsAmountNotValid() {
        ArrayList<DeckValidationUtil.CardNameAmount> deck = new ArrayList<>();
        deck.add(new DeckValidationUtil.CardNameAmount("Mountain", 59));

        ArrayList<DeckValidationUtil.CardNameAmount> sideboard = new ArrayList<>();
        sideboard.add(new DeckValidationUtil.CardNameAmount("Mountain", 16));

        DeckValidator validator = new Standard();
        testDeckValid(validator, deck, sideboard);
        Assert.assertEquals("invalid message not correct",
                "Deck=Must contain at least 60 cards: has only 59 cards, Sideboard=Must contain no more than 15 cards : has 16 cards", validator.getErrorsListInfo());
    }

    @Test
    public void testLimitedValid() {
        DeckTester deckTester = new DeckTester(new Limited());
        deckTester.addMaindeck("Counterspell", 4);
        deckTester.addMaindeck("Mountain", 36);

        deckTester.validate("Deck should be valid");
    }

    @Test
    public void testLimitedNotValidToLessCards() {
        DeckTester deckTester = new DeckTester(new Limited());

        deckTester.addMaindeck("Counterspell", 4);
        deckTester.addMaindeck("Mountain", 35);

        deckTester.validate("Deck should not be valid", false);
    }

    @Test
    public void testModern1() {
        DeckTester deckTester = new DeckTester(new Modern());

        deckTester.addMaindeck("Counterspell", 5);
        deckTester.addMaindeck("Mountain", 56);

        deckTester.validate("only 4 of a card are allowed", false);
    }

    private void assertCounterspellValid(ArrayList<DeckValidationUtil.CardNameAmount> deckList) {
        final boolean needValid = true; // card valid after Modern Horizons 2
        boolean valid = testDeckValid(new Modern(), deckList);
        if (valid != needValid) {
            Assert.fail("Counterspell " + (needValid ? "must be" : "not") + " allowed in modern");
        }
    }

    @Test
    public void testModernCounterspell1() {
        // if card is legal in any set then it must be legal in all other sets too

        ArrayList<DeckValidationUtil.CardNameAmount> deckList = new ArrayList<>();
        deckList.add(new DeckValidationUtil.CardNameAmount("JVC", 24, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("6ED", 61, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("5ED", 77, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("4ED", 65, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("G00", 1, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("DD2", 24, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("ICE", 64, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("F05", 11, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("LEA", 54, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("LEB", 55, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("ME4", 45, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("ME2", 44, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("S99", 34, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("7ED", 67, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("3ED", 54, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("MMQ", 69, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("VMA", 64, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("2ED", 55, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("TPR", 43, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("TMP", 57, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("MH2", 267, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertCounterspellValid(deckList);
    }

    private void assertPsychatogValid(ArrayList<DeckValidationUtil.CardNameAmount> deckList) {
        // if that card will be valid in modern then you must replace it with another non valid card
        // google for "Cards Wizards Should Reprint for Modern"
        final boolean needValid = false;
        boolean valid = testDeckValid(new Modern(), deckList);
        if (valid != needValid) {
            Assert.fail("Psychatog " + (needValid ? "must be" : "not") + " allowed in modern");
        }
    }

    @Test
    public void testModernPsychatog() {
        // test non valid card
        ArrayList<DeckValidationUtil.CardNameAmount> deckList = new ArrayList<>();
        deckList.add(new DeckValidationUtil.CardNameAmount("ODY", 292, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertPsychatogValid(deckList);

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("VMA", 258, 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        assertPsychatogValid(deckList);
    }

    @Test
    public void testModernBanned() {
        ArrayList<DeckValidationUtil.CardNameAmount> deckList = new ArrayList<>();
        DeckValidator validator = new Modern();

        deckList.add(new DeckValidationUtil.CardNameAmount("Ancestral Vision", 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        boolean validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertTrue(validator.getErrorsListInfo(), validationSuccessful);
        validator.getErrorsList().clear();

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("Ancient Den", 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getErrorsListInfo(), validationSuccessful);
        validator.getErrorsList().clear();

        deckList.add(new DeckValidationUtil.CardNameAmount("Birthing Pod", 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getErrorsListInfo(), validationSuccessful);
        validator.getErrorsList().clear();

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("Blazing Shoal", 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getErrorsListInfo(), validationSuccessful);
        validator.getErrorsList().clear();

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("Bloodbraid Elf", 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertTrue(validator.getErrorsListInfo(), validationSuccessful);
        validator.getErrorsList().clear();

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("Chrome Mox", 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getErrorsListInfo(), validationSuccessful);
        validator.getErrorsList().clear();

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("Cloudpost", 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getErrorsListInfo(), validationSuccessful);
        validator.getErrorsList().clear();

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("Dark Depths", 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getErrorsListInfo(), validationSuccessful);
        validator.getErrorsList().clear();

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("Deathrite Shaman", 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getErrorsListInfo(), validationSuccessful);
        validator.getErrorsList().clear();

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("Dig Through Time", 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getErrorsListInfo(), validationSuccessful);
        validator.getErrorsList().clear();

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("Dread Return", 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getErrorsListInfo(), validationSuccessful);
        validator.getErrorsList().clear();

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("Glimpse of Nature", 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getErrorsListInfo(), validationSuccessful);
        validator.getErrorsList().clear();

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("Great Furnace", 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getErrorsListInfo(), validationSuccessful);
        validator.getErrorsList().clear();

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("Green Sun's Zenith", 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getErrorsListInfo(), validationSuccessful);
        validator.getErrorsList().clear();

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("Hypergenesis", 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getErrorsListInfo(), validationSuccessful);
        validator.getErrorsList().clear();

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("Jace, the Mind Sculptor", 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertTrue(validator.getErrorsListInfo(), validationSuccessful);
        validator.getErrorsList().clear();

        deckList.clear();
        deckList.add(new DeckValidationUtil.CardNameAmount("Mental Misstep", 4));
        deckList.add(new DeckValidationUtil.CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getErrorsListInfo(), validationSuccessful);
        validator.getErrorsList().clear();
    }
}
