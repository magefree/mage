package mage.cards.decks.importer;

import mage.cards.decks.DeckCardLists;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class MtgjsonDeckImportTest {

    private static final FakeCardLookup LOOKUP = new FakeCardLookup();

    @Test
    public void testImport() {
        StringBuilder errors = new StringBuilder();
        MtgjsonDeckImporter importer = new MtgjsonDeckImporter() {
            @Override
            public CardLookup getCardLookup() {
                return LOOKUP;
            }
        };

        // offline deck from https://mtgjson.com/api/v5/decks/ArcaneTempo_GRN.json
        DeckCardLists deck = importer.importDeck(
                Paths.get("src", "test", "data", "importer", "testdeck.json").toString(),
                errors,
                false
        );
        assertEquals("Arcane Tempo", deck.getName());
        TestDeckChecker.checker()
                .addMain("Goblin Electromancer", 4)
                .addMain("Crackling Drake", 4)
                .addMain("Murmuring Mystic", 2)
                .addMain("Arclight Phoenix", 1)
                .addMain("Niv-Mizzet, Parun", 2)
                .addMain("Chart a Course", 4)
                .addMain("Lava Coil", 4)
                .addMain("Beacon Bolt", 1)
                .addMain("Opt", 4)
                .addMain("Radical Idea", 4)
                .addMain("Shock", 4)
                .addMain("Dive Down", 2)
                .addMain("Blink of an Eye", 1)
                .addMain("The Mirari Conjecture", 1)
                .addMain("Sulfur Falls", 3)
                .addMain("Izzet Guildgate", 4)
                .addMain("Island", 8)
                .addMain("Mountain", 7)
                //
                .addSide("The Mirari Conjecture", 1)
                .addSide("Beacon Bolt", 1)
                .addSide("Negate", 3)
                .addSide("Entrancing Melody", 3)
                .addSide("Fiery Cannonade", 3)
                .addSide("Shivan Fire", 2)
                .addSide("Disdainful Stroke", 2)
                //
                .verify(deck, 60, 15);

        assertEquals("", errors.toString());
    }

}
