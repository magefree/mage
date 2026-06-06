package org.mage.test.decks.importer;

import mage.cards.decks.DeckCardLists;
import mage.cards.decks.importer.CardLookup;
import mage.cards.decks.importer.O8dDeckImporter;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

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
                Paths.get("src", "test", "data", "importer", "testdeck.o8d").toString(),
                errors,
                false
        );

        Assert.assertEquals("", errors.toString());
        TestDeckChecker.checker()
                .addMain("Forest", 1)
                .addSide("Island", 2)
                .verify(deck, 1, 2);
    }

}
