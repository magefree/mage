package mage.cards.decks.importer;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;

public class CodDeckImportTest {

  private static final FakeCardLookup LOOKUP = new FakeCardLookup()
    .addCard("Forest")
    .addCard("Razorverge Thicket")
    .addCard("Avacyn's Pilgrim")
    .addCard("War Priest of Thune");

  @Test
  public void testImportCod() {
    CodDeckImporter importer = new CodDeckImporter() {
      @Override
      public CardLookup getCardLookup() {
        return LOOKUP;
      }
    };
    StringBuilder errors = new StringBuilder();
    DeckCardLists deck = importer.importDeck(
        "src/test/java/mage/cards/decks/importer/testdeck.cod", errors);
    assertEquals("Deck Name", deck.getName());

    TestDeckChecker.checker()
        .addMain("Forest", 12)
        .addMain("Razorverge Thicket", 100)
        .addMain("Avacyn's Pilgrim", 1)
        .addSide("War Priest of Thune", 3)
        .verify(deck, 113, 3);

    assertEquals("Could not find card: '@#$NOT A REAL CARD NAME@#$'\n", errors.toString());
  }

  private static void assertCardSame(String name, DeckCardInfo card) {
    assertEquals(name, card.getCardName());
    assertEquals(1, card.getQuantity());
  }

}
