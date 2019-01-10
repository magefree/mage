package mage.cards.decks.importer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;

public class CodDeckImportTest {

  @Test
  public void testImportCod() {
    StringBuilder errors = new StringBuilder();
    DeckCardLists deck = new CodDeckImporter().importDeck(
        "src/test/java/mage/cards/decks/importer/testdeck.cod", errors);
    assertEquals("Deck Name", deck.getName());
    assertEquals(113, deck.getCards().size());
    assertEquals(3, deck.getSideboard().size());
    int index = 0;
    for (int i = 0; i < 12; i++) {
      assertCardSame("Forest", deck.getCards().get(index++));
    }
    for (int i = 0; i < 100; i++) {
      assertCardSame("Razorverge Thicket", deck.getCards().get(index++));
    }
    assertCardSame("Avacyn's Pilgrim", deck.getCards().get(index++));
    assertEquals(index, deck.getCards().size());

    index = 0;
    for (int i = 0; i < 3; i++) {
      assertCardSame("War Priest of Thune", deck.getSideboard().get(index++));
    }
    assertEquals(index, deck.getSideboard().size());

    assertEquals("Could not find card: '@#$NOT A REAL CARD NAME@#$'\n", errors.toString());
  }

  private static void assertCardSame(String name, DeckCardInfo card) {
    assertEquals(name, card.getCardName());
    assertEquals(1, card.getQuantity());
  }

}
