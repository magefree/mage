package mage.cards.decks.importer;

import mage.cards.decks.DeckCardLists;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class MtgaImporterTest {

    private static final FakeCardLookup LOOKUP = new FakeCardLookup();

    @Test
    public void testImport() {
        MtgaImporter importer = new MtgaImporter() {
            @Override
            public CardLookup getCardLookup() {
                return LOOKUP;
            }
        };
        StringBuilder errors = new StringBuilder();
        DeckCardLists deck = importer.importDeck(
                Paths.get("src", "test", "data", "importer", "testdeck.mtga").toString(),
                errors,
                false
        );

        TestDeckChecker.checker()
                .addMain("Niv-Mizzet Reborn", 1)
                .addMain("Teferi, Time Raveler", 1)
                .addMain("Dovin's Veto", 1)
                .addMain("Knight of Autumn", 1)
                .addMain("Expansion // Explosion", 1)
                .addMain("Forest", 1)
                .addMain("Teferi, Hero of Dominaria", 1)

                .addSide("Unmoored Ego", 1)
                .addSide("Beacon Bolt", 1)

                .verify(deck, 7, 2);

        assertEquals("", errors.toString());
    }

}