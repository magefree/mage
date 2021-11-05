package mage.cards.decks.importer;

import mage.cards.decks.DeckCardLists;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class DecDeckImportTest {

    private static final FakeCardLookup LOOKUP = new FakeCardLookup();

    @Test
    public void testImport() {
        StringBuilder errors = new StringBuilder();
        DecDeckImporter importer = new DecDeckImporter() {
            @Override
            public CardLookup getCardLookup() {
                return LOOKUP;
            }
        };
        DeckCardLists deck = importer.importDeck(
                Paths.get("src", "test", "data", "importer", "testdeck.dec").toString(),
                errors,
                false
        );

        TestDeckChecker.checker()
                .addMain("Masticore", 4)
                .addMain("Metalworker", 4)
                .addMain("Phyrexian Colossus", 1)
                .addMain("Crumbling Sanctuary", 1)
                .addMain("Grim Monolith", 4)
                .addMain("Mishra's Helix", 1)
                .addMain("Phyrexian Processor", 4)
                .addMain("Tangle Wire", 4)
                .addMain("Thran Dynamo", 4)
                .addMain("Voltaic Key", 4)
                .addMain("Tinker", 4)
                .addMain("Brainstorm", 4)
                .addMain("Crystal Vein", 4)
                .addMain("Island", 9)
                .addMain("Rishadan Port", 4)
                .addMain("Saprazzan Skerry", 4)
                .addSide("Annul", 4)
                .addSide("Chill", 4)
                .addSide("Miscalculation", 4)
                .addSide("Mishra's Helix", 1)
                .addSide("Rising Waters", 2)
                .verify(deck, 60, 15);

        assertEquals("", errors.toString());
    }

}
