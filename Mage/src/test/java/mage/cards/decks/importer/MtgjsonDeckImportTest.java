package mage.cards.decks.importer;

import mage.cards.decks.DeckCardLists;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

public class MtgjsonDeckImportTest {

    private static final FakeCardLookup LOOKUP = new FakeCardLookup();

    @Test
    public void testImport_Normal() {
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
        Assert.assertEquals("Arcane Tempo", deck.getName());
        Assert.assertEquals("", errors.toString());
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
    }

    @Test
    public void testImport_Commander() {
        StringBuilder errors = new StringBuilder();
        MtgjsonDeckImporter importer = new MtgjsonDeckImporter() {
            @Override
            public CardLookup getCardLookup() {
                return LOOKUP;
            }
        };

        // offline deck from https://mtgjson.com/api/v5/decks/GrandLarceny_OTC.json
        DeckCardLists deck = importer.importDeck(
                Paths.get("src", "test", "data", "importer", "testdeckCommander.json").toString(),
                errors,
                false
        );
        Assert.assertEquals("Desert Bloom", deck.getName());
        Assert.assertEquals("", errors.toString());
        TestDeckChecker.checker()
                .addSide("Yuma, Proud Protector", 1) // commander
                //
                .addMain("Kirri, Talented Sprout", 1)
                .addMain("Scavenger Grounds", 1)
                .addMain("Sun Titan", 1)
                .addMain("Omnath, Locus of Rage", 1)
                .addMain("Descend upon the Sinful", 1)
                .addMain("Chromatic Lantern", 1)
                .addMain("Marshal's Anthem", 1)
                .addMain("Sheltered Thicket", 1)
                .addMain("Scute Swarm", 1)
                .addMain("Hour of Promise", 1)
                .addMain("Oracle of Mul Daya", 1)
                .addMain("Ramunap Excavator", 1)
                .addMain("Scattered Groves", 1)
                .addMain("World Shaper", 1)
                .addMain("Nesting Dragon", 1)
                .addMain("Turntimber Sower", 1)
                .addMain("Sevinne's Reclamation", 1)
                .addMain("Ancient Greenwarden", 1)
                .addMain("Titania, Protector of Argoth", 1)
                .addMain("Return of the Wildspeaker", 1)
                .addMain("Perennial Behemoth", 1)
                .addMain("Avenger of Zendikar", 1)
                .addMain("Hazezon, Shaper of Sand", 1)
                .addMain("Escape to the Wilds", 1)
                .addMain("Heaven // Earth", 1)
                .addMain("Genesis Hydra", 1)
                .addMain("Sunscorched Divide", 1)
                .addMain("The Mending of Dominaria", 1)
                .addMain("Decimate", 1)
                .addMain("Sand Scout", 1)
                .addMain("Embrace the Unknown", 1)
                .addMain("Dune Chanter", 1)
                .addMain("Cataclysmic Prospecting", 1)
                .addMain("Vengeful Regrowth", 1)
                .addMain("Angel of Indemnity", 1)
                .addMain("Cactus Preserve", 1)
                .addMain("Rumbleweed", 1)
                .addMain("Terramorphic Expanse", 1)
                .addMain("Evolving Wilds", 1)
                .addMain("Swiftfoot Boots", 1)
                .addMain("Explore", 1)
                .addMain("Sol Ring", 1)
                .addMain("Satyr Wayfinder", 1)
                .addMain("Perpetual Timepiece", 1)
                .addMain("Crawling Sensation", 1)
                .addMain("Painted Bluffs", 1)
                .addMain("Command Tower", 1)
                .addMain("Magmatic Insight", 1)
                .addMain("Krosan Verge", 1)
                .addMain("Desert of the True", 1)
                .addMain("Skullwinder", 1)
                .addMain("Desert of the Indomitable", 1)
                .addMain("Jungle Shrine", 1)
                .addMain("Bitter Reunion", 1)
                .addMain("Desert of the Fervent", 1)
                .addMain("Valorous Stance", 1)
                .addMain("Dunes of the Dead", 1)
                .addMain("Shefet Dunes", 1)
                .addMain("Hashep Oasis", 1)
                .addMain("Elvish Rejuvenator", 1)
                .addMain("Winding Way", 1)
                .addMain("Springbloom Druid", 1)
                .addMain("Arcane Signet", 1)
                .addMain("Unholy Heat", 1)
                .addMain("Thrilling Discovery", 1)
                .addMain("Electric Revelation", 1)
                .addMain("Eccentric Farmer", 1)
                .addMain("Harrow", 1)
                .addMain("Ramunap Ruins", 1)
                .addMain("Path to Exile", 1)
                .addMain("Requisition Raid", 1)
                .addMain("Bovine Intervention", 1)
                .addMain("Map the Frontier", 1)
                .addMain("Conduit Pylons", 1)
                .addMain("Mirage Mesa", 1)
                .addMain("Wreck and Rebuild", 1)
                .addMain("Angel of the Ruins", 1)
                .addMain("Bristling Backwoods", 1)
                .addMain("Creosote Heath", 1)
                .addMain("Abraded Bluffs", 1)
                .addMain("Scaretiller", 1)
                .addMain("Nantuko Cultivator", 1)
                .addMain("Plains", 6)
                .addMain("Mountain", 4)
                .addMain("Forest", 7)
                //
                .verify(deck, 99, 1);
    }

}
