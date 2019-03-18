
package org.mage.test.serverside.deck;

import java.util.ArrayList;
import java.util.List;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidator;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.deck.Limited;
import mage.deck.Modern;
import mage.deck.Standard;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestBase;

/**
 *
 * @author LevelX2
 */
public class DeckValidatorTest extends MageTestBase {

    static class CardNameAmount {

        String name;
        String setCode;
        String cardNumber;

        int number;

        public CardNameAmount(String setCode, int cardNumber, int number) {
            this.name = "";
            this.setCode = setCode;
            this.cardNumber = String.valueOf(cardNumber);
            this.number = number;
        }

        public CardNameAmount(String name, int number) {
            this.name = name;
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public int getNumber() {
            return number;
        }

        public String getSetCode() {
            return setCode;
        }

        public String getCardNumber() {
            return cardNumber;
        }

    }

    @Test
    public void testStandardDeckCardsAmountValid() {
        ArrayList<CardNameAmount> deck = new ArrayList<>();
        deck.add(new CardNameAmount("Mountain", 60));

        DeckValidator validator = new Standard();
        boolean validationSuccessful = testDeckValid(validator, deck);
        Assert.assertTrue(validator.getInvalid().toString(), validationSuccessful);
    }

    @Test
    public void testStandardDeckCardsAmountNotValid() {
        ArrayList<CardNameAmount> deck = new ArrayList<>();
        deck.add(new CardNameAmount("Mountain", 59));

        ArrayList<CardNameAmount> sideboard = new ArrayList<>();
        sideboard.add(new CardNameAmount("Mountain", 16));

        DeckValidator validator = new Standard();
        testDeckValid(validator, deck, sideboard);
        Assert.assertEquals("invalid message not correct",
                "{Sideboard=Must contain no more than 15 cards : has 16 cards, Deck=Must contain at least 60 cards: has only 59 cards}", validator.getInvalid().toString());
    }

    @Test
    public void testLimitedValid() {
        ArrayList<CardNameAmount> deck = new ArrayList<>();

        deck.add(new CardNameAmount("Counterspell", 4));
        deck.add(new CardNameAmount("Mountain", 36));

        Assert.assertTrue("Deck should be valid", testDeckValid(new Limited(), deck));
    }

    @Test
    public void testLimitedNotValidToLessCards() {
        ArrayList<CardNameAmount> deckList = new ArrayList<>();

        deckList.add(new CardNameAmount("Counterspell", 4));
        deckList.add(new CardNameAmount("Mountain", 35));

        Assert.assertFalse("Deck should not be valid", testDeckValid(new Limited(), deckList));
    }

    @Test
    public void testModern1() {
        ArrayList<CardNameAmount> deckList = new ArrayList<>();

        deckList.add(new CardNameAmount("Counterspell", 5));
        deckList.add(new CardNameAmount("Mountain", 56));

        Assert.assertFalse("only 4 of a card are allowed", testDeckValid(new Modern(), deckList));
    }

    @Test
    public void testModernCounterspell1() {
        ArrayList<CardNameAmount> deckList = new ArrayList<>();
        deckList.add(new CardNameAmount("DD3JVC", 24, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("6ED", 61, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("5ED", 77, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("4ED", 65, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("JR", 5, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("DD2", 24, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("ICE", 64, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("FNMP", 66, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("LEA", 55, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("LEB", 55, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("ME4", 45, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("ME2", 44, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("S99", 34, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("7ED", 67, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("3ED", 54, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("MMQ", 69, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("VMA", 64, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("2ED", 55, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("TPR", 43, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("TMP", 57, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("S00", 12, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

    }

    @Test
    public void testModernBanned() {
        ArrayList<CardNameAmount> deckList = new ArrayList<>();
        DeckValidator validator = new Modern();

        deckList.add(new CardNameAmount("Ancestral Vision", 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        boolean validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertTrue(validator.getInvalid().toString(), validationSuccessful);
        validator.getInvalid().clear();

        deckList.clear();
        deckList.add(new CardNameAmount("Ancient Den", 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getInvalid().toString(), validationSuccessful);
        validator.getInvalid().clear();

        deckList.add(new CardNameAmount("Birthing Pod", 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getInvalid().toString(), validationSuccessful);
        validator.getInvalid().clear();

        deckList.clear();
        deckList.add(new CardNameAmount("Blazing Shoal", 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getInvalid().toString(), validationSuccessful);
        validator.getInvalid().clear();

        deckList.clear();
        deckList.add(new CardNameAmount("Bloodbraid Elf", 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertTrue(validator.getInvalid().toString(), validationSuccessful);
        validator.getInvalid().clear();

        deckList.clear();
        deckList.add(new CardNameAmount("Chrome Mox", 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getInvalid().toString(), validationSuccessful);
        validator.getInvalid().clear();

        deckList.clear();
        deckList.add(new CardNameAmount("Cloudpost", 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getInvalid().toString(), validationSuccessful);
        validator.getInvalid().clear();

        deckList.clear();
        deckList.add(new CardNameAmount("Dark Depths", 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getInvalid().toString(), validationSuccessful);
        validator.getInvalid().clear();

        deckList.clear();
        deckList.add(new CardNameAmount("Deathrite Shaman", 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getInvalid().toString(), validationSuccessful);
        validator.getInvalid().clear();

        deckList.clear();
        deckList.add(new CardNameAmount("Dig Through Time", 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getInvalid().toString(), validationSuccessful);
        validator.getInvalid().clear();

        deckList.clear();
        deckList.add(new CardNameAmount("Dread Return", 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getInvalid().toString(), validationSuccessful);
        validator.getInvalid().clear();

        deckList.clear();
        deckList.add(new CardNameAmount("Glimpse of Nature", 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getInvalid().toString(), validationSuccessful);
        validator.getInvalid().clear();

        deckList.clear();
        deckList.add(new CardNameAmount("Great Furnace", 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getInvalid().toString(), validationSuccessful);
        validator.getInvalid().clear();

        deckList.clear();
        deckList.add(new CardNameAmount("Green Sun's Zenith", 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getInvalid().toString(), validationSuccessful);
        validator.getInvalid().clear();

        deckList.clear();
        deckList.add(new CardNameAmount("Hypergenesis", 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getInvalid().toString(), validationSuccessful);
        validator.getInvalid().clear();

        deckList.clear();
        deckList.add(new CardNameAmount("Jace, the Mind Sculptor", 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertTrue(validator.getInvalid().toString(), validationSuccessful);
        validator.getInvalid().clear();

        deckList.clear();
        deckList.add(new CardNameAmount("Mental Misstep", 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        validationSuccessful = testDeckValid(validator, deckList);
        Assert.assertFalse(validator.getInvalid().toString(), validationSuccessful);
        validator.getInvalid().clear();
    }

    private boolean testDeckValid(DeckValidator validator, List<CardNameAmount> cards) {
        return testDeckValid(validator, cards, null);
    }

    private boolean testDeckValid(DeckValidator validator, List<CardNameAmount> cards, List<CardNameAmount> cardsSideboard) {
        Deck deckToTest = new Deck();
        if (cards != null) {
            for (CardNameAmount cardNameAmount : cards) {
                CardInfo cardinfo;
                if (cardNameAmount.getName().isEmpty()) {
                    cardinfo = CardRepository.instance.findCard(cardNameAmount.getSetCode(), cardNameAmount.getCardNumber());
                } else {
                    cardinfo = CardRepository.instance.findCard(cardNameAmount.getName());
                }
                for (int i = 0; i < cardNameAmount.getNumber(); i++) {
                    deckToTest.getCards().add(cardinfo.getCard());
                }
            }
        }
        if (cardsSideboard != null) {
            for (CardNameAmount cardNameAmount : cardsSideboard) {
                CardInfo cardinfo;
                if (cardNameAmount.getName().isEmpty()) {
                    cardinfo = CardRepository.instance.findCard(cardNameAmount.getSetCode(), cardNameAmount.getCardNumber());
                } else {
                    cardinfo = CardRepository.instance.findCard(cardNameAmount.getName());
                }
                for (int i = 0; i < cardNameAmount.getNumber(); i++) {
                    deckToTest.getSideboard().add(cardinfo.getCard());
                }
            }
        }
        return validator.validate(deckToTest);
    }
}
