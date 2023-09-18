package mage.cards.decks.importer;

import mage.cards.decks.DeckCardLists;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class DraftLogImporterTest {

    private static final FakeCardLookup LOOKUP = new FakeCardLookup();

    @Test
    public void testImport() {
        StringBuilder errors = new StringBuilder();
        DraftLogImporter importer = new DraftLogImporter() {
            @Override
            public CardLookup getCardLookup() {
                return LOOKUP;
            }
        };
        DeckCardLists deck = importer.importDeck(
                Paths.get("src", "test", "data", "importer", "testdeck.draft").toString(),
                errors,
                false
        );

        TestDeckChecker.checker()
                .addMain("Raging Ravine", 1)
                .addMain("Fiery Temper", 1)
                .addMain("Wild Mongrel", 1)
                .addMain("Wild Mongrel", 1)
                .addMain("Shielding Plax", 1)
                .addMain("Wild Mongrel", 1)
                .addMain("Basking Rootwalla", 1)
                .addMain("Wild Mongrel", 1)
                .addMain("Arena Athlete", 1)
                .addMain("Undying Rage", 1)
                .addMain("Molten Birth", 1)
                .addMain("Shed Weakness", 1)
                .addMain("Pulse of Murasa", 1)
                .addMain("Just the Wind", 1)
                .addMain("Stitcher's Apprentice", 1)
                .addMain("Life from the Loam", 1)
                .addMain("Satyr Wayfinder", 1)
                .addMain("Mad Prophet", 1)
                .addMain("Wild Mongrel", 1)
                .addMain("Wickerbough Elder", 1)
                .addMain("Basking Rootwalla", 1)
                .addMain("Satyr Wayfinder", 1)
                .addMain("Brawn", 1)
                .addMain("Myr Servitor", 1)
                .addMain("Terramorphic Expanse", 1)
                .addMain("Foil", 1)
                .addMain("Flight of Fancy", 1)
                .addMain("Mark of the Vampire", 1)
                .addMain("Repel the Darkness", 1)
                .addMain("Golgari Charm", 1)
                .addMain("Raid Bombardment", 1)
                .addMain("Reckless Wurm", 1)
                .addMain("Satyr Wayfinder", 1)
                .addMain("Kodama's Reach", 1)
                .addMain("Last Gasp", 1)
                .addMain("Wild Mongrel", 1)
                .addMain("Myr Servitor", 1)
                .addMain("Raid Bombardment", 1)
                .addMain("Treasure Cruise", 1)
                .addMain("Bloodflow Connoisseur", 1)
                .addMain("Treasure Cruise", 1)
                .addMain("Hyena Umbra", 1)
                .addMain("Kodama's Reach", 1)
                .addMain("Just the Wind", 1)
                .addMain("Flight of Fancy", 1)
                .verify(deck, 45, 0);

        assertEquals("", errors.toString());
    }

}