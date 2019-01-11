package mage.cards.decks.importer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import mage.cards.decks.DeckCardLists;

public class O8dDeckImportTest {

  private static final FakeCardLookup LOOKUP = new FakeCardLookup();

  @Test
  public void testImport() {
    O8dDeckImporter importer = new O8dDeckImporter() {
      @Override
      public CardLookup getCardLookup() {
        return LOOKUP;
      }
    };
    StringBuilder errors = new StringBuilder();
    DeckCardLists deck = importer.importDeck(
        "src/test/java/mage/cards/decks/importer/samples/testdeck.o8d", errors);

    TestDeckChecker.checker()
        .addMain("Forest", 1)
        .addSide("Island", 2)
        .verify(deck, 1, 2);

    assertEquals("", errors.toString());
  }

}
