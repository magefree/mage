package org.mage.test.decks.importer;

import mage.cards.decks.DeckCardLists;
import mage.cards.decks.importer.CardLookup;
import mage.cards.decks.importer.DckDeckImporter;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

public class DckDeckImportTest {

    private static final FakeCardLookup LOOKUP = new FakeCardLookup();

    @Test
    public void testImport() {
        StringBuilder errors = new StringBuilder();
        DckDeckImporter importer = new DckDeckImporter() {
            @Override
            public CardLookup getCardLookup() {
                return LOOKUP;
            }
        };
        DeckCardLists deck = importer.importDeck(
                Paths.get("src", "test", "data", "importer", "testdeck.dck").toString(),
                errors,
                false
        );

        Assert.assertEquals("", errors.toString());
        TestDeckChecker.checker()
                .addMain("Ugin, the Ineffable", 1)
                .addMain("Cephalid Looter", 1)
                .addMain("Adventure Awaits", 1)
                .addMain("Acquisitions Expert", 1)
                .addSide("Archon of Emeria", 3)
                .addSide("Akoum Hellhound", 1)
                .verify(deck, 4, 4);
    }
}
