package mage.cards.decks.importer;

import mage.cards.decks.DeckCardLists;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

public class DekDeckImportTest {

    private static final FakeCardLookup LOOKUP = new FakeCardLookup(false)
            .addCard("Nezumi Shortfang")
            .addCard("Kessig Prowler")
            .addCard("Murderous Rider")
            .addCard("Refuse // Cooperate")
            .addCard("Riverglide Pathway")
            .addCard("Dead // Gone");

    @Test
    public void testImport() {
        DekDeckImporter importer = new DekDeckImporter() {
            @Override
            public CardLookup getCardLookup() {
                return LOOKUP;
            }
        };
        StringBuilder errors = new StringBuilder();
        DeckCardLists deck = importer.importDeck(
                Paths.get("src", "test", "data", "importer", "testdeck.dek").toString(),
                errors,
                false
        );

        Assert.assertEquals("", errors.toString());
        TestDeckChecker.checker()
                .addMain("Nezumi Shortfang", 2)
                .addMain("Kessig Prowler", 1)
                .addMain("Murderous Rider", 1)
                .addMain("Refuse // Cooperate", 1)
                .addSide("Riverglide Pathway", 2)
                .addSide("Dead // Gone", 1)
                .verify(deck, 5, 3);
    }

}
