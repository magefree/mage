package mage.cards.decks.importer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import mage.cards.repository.CardInfo;

public class FakeCardLookup extends CardLookup {

  private final Map<String, CardInfo> lookup = new HashMap<>();

  public FakeCardLookup addCard(String cardName) {
    lookup.put(cardName, new CardInfo() {{
      name = cardName;
    }});
    return this;
  }

  @Override
  public Optional<CardInfo> lookupCardInfo(String name) {
    CardInfo card = lookup.get(name);
    if (card == null) {
      System.out.println("Couldn't find: " + name);
    }
    return Optional.ofNullable(card);
  }

}
