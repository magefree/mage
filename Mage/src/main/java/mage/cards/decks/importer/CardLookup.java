package mage.cards.decks.importer;

import java.util.Optional;

import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;

public class CardLookup {

  public static final CardLookup instance = new CardLookup();

  public Optional<CardInfo> lookupCardInfo(String name) {
    return Optional.ofNullable(CardRepository.instance.findPreferedCoreExpansionCard(name, true));
  }

}
