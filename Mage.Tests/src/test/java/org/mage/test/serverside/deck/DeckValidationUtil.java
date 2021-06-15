package org.mage.test.serverside.deck;

import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidator;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public class DeckValidationUtil {

    static class CardNameAmount {

        String name;
        String setCode;
        String cardNumber;
        int number;

        CardNameAmount(String setCode, int cardNumber, int number) {
            this.name = "";
            this.setCode = setCode;
            this.cardNumber = String.valueOf(cardNumber);
            this.number = number;
        }

        CardNameAmount(String name, int number) {
            this.name = name;
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public int getNumber() {
            return number;
        }

        String getSetCode() {
            return setCode;
        }

        String getCardNumber() {
            return cardNumber;
        }
    }

    private DeckValidationUtil() {
    }

    public static boolean testDeckValid(DeckValidator validator, List<CardNameAmount> cards) {
        return testDeckValid(validator, cards, null);
    }

    public static boolean testDeckValid(DeckValidator validator, List<CardNameAmount> cards, List<CardNameAmount> cardsSideboard) {
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
                    assert cardinfo != null;
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
                    assert cardinfo != null;
                    deckToTest.getSideboard().add(cardinfo.getCard());
                }
            }
        }
        return validator.validate(deckToTest);
    }

}

class DeckTester {
    private final DeckValidator deckValidator;
    private final List<DeckValidationUtil.CardNameAmount> maindeck = new ArrayList<>();
    private final List<DeckValidationUtil.CardNameAmount> sideboard = new ArrayList<>();

    DeckTester(DeckValidator deckValidator) {
        this.deckValidator = deckValidator;
    }

    void addMaindeck(String name, int amount) {
        maindeck.add(new DeckValidationUtil.CardNameAmount(name, amount));
    }

    void addSideboard(String name, int amount) {
        sideboard.add(new DeckValidationUtil.CardNameAmount(name, amount));
    }

    void validate() {
        validate(null);
    }

    void validate(String message) {
        validate(message, true);
    }

    void validate(String message, boolean expected) {
        boolean valid = DeckValidationUtil.testDeckValid(deckValidator, maindeck, sideboard);
        Assert.assertEquals(message != null ? message : deckValidator.getErrorsListInfo(), expected, valid);
    }
}
