package mage.cards.decks.importer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import mage.cards.decks.DeckCardLists;

public class DecDeckImportTest {

  @Test
  public void testImport() {
    StringBuilder errors = new StringBuilder();
    DeckCardLists deck = new DecDeckImporter().importDeck(
        "src/test/java/mage/cards/decks/importer/testdeck.dec", errors);
    assertEquals(60, deck.getCards().size());
    assertEquals(15, deck.getSideboard().size());
  }

}
