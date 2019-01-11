package mage.cards.decks.importer;

import java.util.List;
import java.util.Optional;

import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;

public class CardLookup {

  public static final CardLookup instance = new CardLookup();

  public Optional<CardInfo> lookupCardInfo(String name) {
    return Optional.ofNullable(CardRepository.instance.findPreferedCoreExpansionCard(name, true));
  }

  public List<CardInfo> lookupCardInfo(CardCriteria criteria) {
    return CardRepository.instance.findCards(criteria);
  }

}
