package mage.cards.decks.importer;

import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;

import java.util.List;

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

}
