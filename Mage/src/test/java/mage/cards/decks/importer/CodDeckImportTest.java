package mage.cards.decks.importer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import mage.cards.decks.DeckCardLists;

public class CodDeckImportTest {

  private static final FakeCardLookup LOOKUP = new FakeCardLookup(false)
      .addCard("Forest")
      .addCard("Razorverge Thicket")
      .addCard("Avacyn's Pilgrim")
      .addCard("War Priest of Thune");

  @Test
  public void testImport() {
    CodDeckImporter importer = new CodDeckImporter() {
      @Override
      public CardLookup getCardLookup() {
        return LOOKUP;
      }
    };
    StringBuilder errors = new StringBuilder();
    DeckCardLists deck = importer.importDeck(
        "src/test/java/mage/cards/decks/importer/samples/testdeck.cod", errors);
    assertEquals("Deck Name", deck.getName());

    TestDeckChecker.checker()
        .addMain("Forest", 12)
        .addMain("Razorverge Thicket", 100)
        .addMain("Avacyn's Pilgrim", 1)
        .addSide("War Priest of Thune", 3)
        .verify(deck, 113, 3);

    assertEquals("Could not find card: '@#$NOT A REAL CARD NAME@#$'\n", errors.toString());
  }

}
