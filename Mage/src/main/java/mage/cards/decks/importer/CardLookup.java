package mage.cards.decks.importer;

import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.util.CardUtil;

import java.util.List;
import java.util.Objects;

/**
 * Deck import: helper class to mock cards repository
 */
public class CardLookup {

    public static final CardLookup instance = new CardLookup();

    public CardInfo lookupCardInfo(String name) {
        return CardRepository.instance.findPreferredCoreExpansionCard(name);
    }

    public List<CardInfo> lookupCardInfo(CardCriteria criteria) {
        // can be override to make fake lookup, e.g. for tests
        return CardRepository.instance.findCards(criteria);
    }

    public CardInfo lookupCardInfo(String name, String set, String cardNumber) {
        CardCriteria cardCriteria = new CardCriteria();
        cardCriteria.name(name);
        if (set != null) {
            cardCriteria.setCodes(set);
        }
        if (cardNumber != null) {
            int intCardNumber = CardUtil.parseCardNumberAsInt(cardNumber);
            cardCriteria.minCardNumber(intCardNumber);
            cardCriteria.maxCardNumber(intCardNumber);
        }
        List<CardInfo> foundCards = lookupCardInfo(cardCriteria);

        CardInfo res = null;

        // if possible then use strict card number
        if (cardNumber != null) {
            res = foundCards.stream().filter(c -> Objects.equals(c.getCardNumber(), cardNumber)).findAny().orElse(null);
        }

        if (res == null) {
            res = foundCards.stream().findAny().orElse(null);
        }

        return res;
    }

}
