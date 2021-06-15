package org.mage.test.serverside.deck;

import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidator;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;

import java.util.List;

/**
 * @author TheElk801
 */
public class DeckValidationUtil {

    private DeckValidationUtil() {
    }

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
