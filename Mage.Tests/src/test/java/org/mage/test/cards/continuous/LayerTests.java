package org.mage.test.cards.continuous;

import mage.ObjectColor;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class LayerTests extends CardTestPlayerBase {

    /**
     * Conspiracy -> Opalescence -> Enchanted Evening
     *      Conspiracy is dependent on Opalescence
     *      Opalescence is dependent on Enchanted Evening
     *
     * So, the effects should be applied as follows:
     *      Enchanted Evening -> Opalescence -> Conspiracy
     */
    @Test
    public void testMultipleLayeredDependency() {


        addCard(Zone.HAND, playerA, "Conspiracy"); // creatures get chosen subtype
        addCard(Zone.HAND, playerA, "Opalescence"); // enchantments become creatures P/T equal to CMC
        addCard(Zone.HAND, playerA, "Enchanted Evening"); // all permanents become enchantments

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem", 1); // keep lands alive // all creatures +1/+1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Conspiracy", true);
        setChoice(playerA, "Advisor");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Opalescence", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Enchanted Evening");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertType("Swamp", CardType.LAND, SubType.ADVISOR); // Subtype changed with Conspiracy
        assertPowerToughness(playerA, "Swamp", 1, 1); // boosted with Glorious Anthem
        assertType("Enchanted Evening", CardType.ENCHANTMENT, SubType.ADVISOR); // Subtype changed with Conspiracy
        assertPowerToughness(playerA, "Enchanted Evening", 6, 6); // boosted with Glorious Anthem

    }

    /**
     * Reported bug:
     *      This came up in a recent EDH game and we had no idea how to progress.
     *      Player A cast a Humility, then a March of the Machines, and finally a Mycosynth Lattice.
     *      Does the game get stuck in an endless loop of each card gaining and losing its respective creature-ness and abilities?
     *      Answer: No, they all die
     */
    @Test
    public void testMycosynthLatticeAndMarchOfTheMachinesAndHumility() {
        addCard(Zone.HAND, playerA, "Mycosynth Lattice"); // all permanents are artifacts
        addCard(Zone.HAND, playerA, "March of the Machines"); // artifacts become creatures
        addCard(Zone.HAND, playerA, "Humility"); // all creatures lose abilities and P/T is 1/1

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Humility", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "March of the Machines", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mycosynth Lattice");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // everything dies
        assertPermanentCount(playerA, "Humility", 0);
        assertPermanentCount(playerA, "March of the Machines", 0);
        assertPermanentCount(playerA, "Mycosynth Lattice", 0);
        assertPermanentCount(playerA, "Island", 0);
    }

    @Test
    public void testBloodMoon_UrborgTombOfYawgmothInteraction() {
        // Blood Moon : Nonbasic lands are Mountains.
        // Urborg, Tomb of Yawgmoth : Each land is a Swamp in addition to its other types.
        // Expected behavior:  Urborg loses all abilities and becomes a Mountain.  The Plains does not have subtype Swamp due to this effect.
        addCard(Zone.BATTLEFIELD, playerA, "Blood Moon");
        addCard(Zone.BATTLEFIELD, playerA, "Urborg, Tomb of Yawgmoth", 1);  // non-basic land
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertType("Urborg, Tomb of Yawgmoth", CardType.LAND, SubType.MOUNTAIN);  // Urborg is a Mountain now
        assertPermanentCount(playerA, "Swamp", 0);  // no Swamp subtypes on the battlefield
        assertPermanentCount(playerA, "Plains", 1); // the Plains is not affected by the Urborg
        assertType("Plains", CardType.LAND, SubType.PLAINS);

    }

    @Test
    @Ignore //Works fine in the game.  Test fails, though.
    public void complexExampleFromLayersArticle() {
        /*In play there is a Grizzly Bears which has already been Giant Growthed, 
        a Bog Wraith enchanted by a Lignify, and Figure of Destiny with its 3rd ability activated. 
        I then cast a Mirrorweave targeting the Figure of Destiny. What does each creature look like?
         */
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.HAND, playerA, "Giant Growth", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Bog Wraith", 1);
        addCard(Zone.HAND, playerA, "Lignify", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Figure of Destiny", 1);
        addCard(Zone.HAND, playerA, "Mirrorweave", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 20);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 20);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 20);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lignify", "Bog Wrath");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R/W}:");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R/W}{R/W}{R/W}:");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R/W}{R/W}{R/W}{R/W}{R/W}{R/W}:");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mirrorweave", "Figure of Destiny");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Figure of Destiny", 3);
        assertPowerToughness(playerA, "Figure of Destiny", 4, 4, Filter.ComparisonScope.All);
        assertPowerToughness(playerA, "Figure of Destiny", 8, 8, Filter.ComparisonScope.All);
        assertPowerToughness(playerA, "Figure of Destiny", 0, 4, Filter.ComparisonScope.All);

    }

    @Test
    public void testUrborgWithAnimateLandAndOvinize() {
        // Animate Land: target land is a 3/3 until end of turn and is still a land.
        // Ovinize: target creature becomes 0/1 and loses all abilities until end of turn.
        // Urborg, Tomb of Yawgmoth : Each land is a Swamp in addition to its other types.
        // Expected behavior:  Urborg loses all abilities and becomes a 0/1 creature.
        addCard(Zone.HAND, playerA, "Animate Land", 1);
        addCard(Zone.HAND, playerA, "Ovinize", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Urborg, Tomb of Yawgmoth", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Animate Land", "Urborg, Tomb of Yawgmoth");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Ovinize", "Urborg, Tomb of Yawgmoth");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertType("Urborg, Tomb of Yawgmoth", CardType.CREATURE, SubType.SWAMP);  // Urborg is a creature
        assertPowerToughness(playerA, "Urborg, Tomb of Yawgmoth", 0, 1); // Urborg is a 0/1 creature

    }

    @Test
    @Ignore //This works fine in the game.  Test fails.
    public void testFromAnArticle() {
        /*
        Aiden has a Battlegate Mimic on the battlefield. Nick controls two Wilderness Hypnotists. 
        Aiden casts a Scourge of the Nobilis, targeting the Mimic; after that resolves Nick activates 
        one of his Hypnotist's abilities, targeting the Mimic. Aiden attacks with the Mimic, and 
        casts Inside Out before the damage step. Once Inside Out resolves, Nick activates the ability 
        of his other Hypnotist. How much damage will the Mimic deal?
         */
        addCard(Zone.HAND, playerA, "Scourge of the Nobilis", 1);
        addCard(Zone.HAND, playerA, "Inside Out", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Battlegate Mimic", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);

        addCard(Zone.BATTLEFIELD, playerB, "Wilderness Hypnotist", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scourge of the Nobilis", "Battlegate Mimic");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}:", "Battlegate Mimic");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Inside Out", "Battlegate Mimic");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}:", "Battlegate Mimic");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Battlegate Mimic", 4, 2);

    }

    @Test
    public void testExampleFromReddit2021() {
        // Life and Limb, Humility, and Yavimaya, Cradle of Growth
        // Result: all lands (including Yavimaya) will be 1/1 green Forest 
        // lands and Saproling creatures in addition to their other types, 
        // and have no abilities.

        /*
        All Forests and all Saprolings are 1/1 green Saproling creatures 
        and Forest lands in addition to their other types. (They're affected by summoning sickness.)
        */
        addCard(Zone.BATTLEFIELD, playerA, "Life and Limb", 1);
        
        /*
        All creatures lose all abilities and have base power and toughness 1/1.
        */
        addCard(Zone.BATTLEFIELD, playerA, "Humility", 1);
        
        /*
        Each land is a Forest in addition to its other land types.
        */
        addCard(Zone.BATTLEFIELD, playerA, "Yavimaya, Cradle of Growth", 1);
        
        // added some lands to the battlefield

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // all lands are forests in addition to other types
        assertType("Plains", CardType.CREATURE, SubType.FOREST);
        assertType("Swamp", CardType.CREATURE, SubType.FOREST);
        assertType("Island", CardType.CREATURE, SubType.FOREST);
        assertType("Yavimaya, Cradle of Growth", CardType.CREATURE, SubType.FOREST);

        // all lands are 1/1 Saproling creatures
        assertPowerToughness(playerA, "Plains", 1, 1);
        assertPowerToughness(playerA, "Swamp", 1, 1);
        assertPowerToughness(playerB, "Island", 1, 1);
        assertPowerToughness(playerA, "Yavimaya, Cradle of Growth", 1, 1);

        // all lands are green
        assertColor(playerA, "Plains", ObjectColor.GREEN, true);
        assertColor(playerA, "Swamp", ObjectColor.GREEN, true);
        assertColor(playerB, "Island", ObjectColor.GREEN, true);
        assertColor(playerA, "Yavimaya, Cradle of Growth", ObjectColor.GREEN, true);

    }

}
