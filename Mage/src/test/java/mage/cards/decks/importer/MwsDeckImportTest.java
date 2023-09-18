package mage.cards.decks.importer;

import mage.cards.decks.DeckCardLists;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class MwsDeckImportTest {

    private static final FakeCardLookup LOOKUP = new FakeCardLookup();

    @Test
    public void testImport() {
        MWSDeckImporter importer = new MWSDeckImporter() {
            @Override
            public CardLookup getCardLookup() {
                return LOOKUP;
            }
        };
        StringBuilder errors = new StringBuilder();
        DeckCardLists deck = importer.importDeck(
                Paths.get("src", "test", "data", "importer", "testdeck.mwDeck").toString(),
                errors,
                false
        );

        TestDeckChecker.checker()
                .addMain("Mutavault", 4)
                .addMain("Plains", 18)
                .addMain("Daring Skyjek", 2)
                .addMain("Azorius Arrester", 4)
                .addMain("Banisher Priest", 4)
                .addMain("Boros Elite", 4)
                .addMain("Dryad Militant", 4)
                .addMain("Imposing Sovereign", 4)
                .addMain("Precinct Captain", 4)
                .addMain("Soldier of the Pantheon", 4)
                .addMain("Spear of Heliod", 3)
                .addMain("Rootborn Defenses", 1)
                .addMain("Brave the Elements", 4)

                .addSide("Wear/Tear", 1)
                .addSide("Glare of Heresy", 2)
                .addSide("Fiendslayer Paladin", 3)
                .addSide("Riot Control", 3)
                .addSide("Ajani, Caller of the Pride", 3)
                .addSide("Rootborn Defenses", 3)

                .verify(deck, 60, 15);

        assertEquals("", errors.toString());
    }

}
