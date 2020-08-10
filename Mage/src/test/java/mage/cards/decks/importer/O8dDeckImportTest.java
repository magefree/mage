package mage.cards.decks.importer;

import mage.cards.decks.DeckCardLists;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
                "src/test/java/mage/cards/decks/importer/samples/testdeck.o8d", errors, false);

        TestDeckChecker.checker()
                .addMain("Forest", 1)
                .addSide("Island", 2)
                .verify(deck, 1, 2);

        assertEquals("", errors.toString());
    }

}
