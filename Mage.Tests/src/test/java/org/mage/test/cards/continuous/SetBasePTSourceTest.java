package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Check that effects setting base power/toughness work properly, including CDA and 7b with duration
 *
 * @author xenohedron
 */
public class SetBasePTSourceTest extends CardTestPlayerBase {

    @Test
    public void testCDAsetPT() {
        String rootless = "Rootless Yew"; // 3GG 5/4
        // When Rootless Yew dies, search your library for a creature card with power or toughness 6 or greater,
        // reveal it, put it into your hand, then shuffle.
        String enigma = "Enigma Drake"; // 1UR */4
        // Enigma Drake's power is equal to the number of instant and sorcery cards in your graveyard.
        String kami = "Traproot Kami"; // G 0/*
        // Traproot Kami’s toughness is equal to the number of Forests on the battlefield.

        setStrictChooseMode(true);
        addCard(Zone.LIBRARY, playerA, enigma);
        addCard(Zone.GRAVEYARD, playerA, "Shock", 6);
        addCard(Zone.BATTLEFIELD, playerA, rootless);
        addCard(Zone.BATTLEFIELD, playerA, "Tarmogoyf");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, kami);
        addCard(Zone.BATTLEFIELD, playerA, "Visara the Dreadful");

        // Activate Visara to trigger dies ability and search. CDA must apply in all zones.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Destroy target", rootless);
        addTarget(playerA, enigma); // power 6 due to 6 instants in graveyard

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, enigma, 1);
        assertGraveyardCount(playerA, rootless, 1);
        assertPowerToughness(playerA, kami, 0, 3); // 3 forests
        assertPowerToughness(playerA, "Tarmogoyf", 2, 3); // Instant and creature
    }

    @Test
    public void testLayer7b() {
        String mimic = "Battlegate Mimic"; // {1}{R/W} 2/1
        // Whenever you cast a spell that’s both red and white, Battlegate Mimic has base power and toughness 4/2
        // until end of turn and gains first strike until end of turn.
        String figure = "Figure of Destiny"; // {R/W} 1/1
        // {R/W}: Figure of Destiny becomes a Kithkin Spirit with base power and toughness 2/2.
        // {R/W}{R/W}{R/W}: If Figure of Destiny is a Spirit, it becomes a Kithkin Spirit Warrior with base power and toughness 4/4.
        // {R/W}{R/W}{R/W}{R/W}{R/W}{R/W}: If Figure of Destiny is a Warrior, it becomes a Kithkin Spirit Warrior Avatar
        // with base power and toughness 8/8, flying, and first strike.
        String cloudshift = "Cloudshift"; // {W} Instant
        // Exile target creature you control, then return that card to the battlefield under your control.

        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, mimic);
        addCard(Zone.HAND, playerA, figure);
        addCard(Zone.HAND, playerA, cloudshift, 2);
        addCard(Zone.HAND, playerA, "Double Cleave"); // {1}{R/W} Instant

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, figure);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, mimic, 4, 2);
        assertPowerToughness(playerA, figure,1, 1);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{R/W}: ");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{R/W}{R/W}{R/W}: ");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, mimic, 4, 2);
        assertPowerToughness(playerA, figure,4, 4);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPowerToughness(playerA, mimic, 2, 1);
        assertPowerToughness(playerA, figure,4, 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Double Cleave", mimic);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, cloudshift, figure);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, mimic, 4, 2);
        assertPowerToughness(playerA, figure, 1, 1);

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, cloudshift, mimic);
        waitStackResolved(2, PhaseStep.POSTCOMBAT_MAIN);
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{R/W}: ");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, mimic, 2, 1);
        assertPowerToughness(playerA, figure, 2, 2);
    }

    @Test
    public void testSvogthos() {
        String svogthos = "Svogthos, the Restless Tomb"; // Land
        // {3}{B}{G}: Until end of turn, Svogthos, the Restless Tomb becomes a black and green Plant Zombie creature with
        // "This creature’s power and toughness are each equal to the number of creature cards in your graveyard." It’s still a land.

        setStrictChooseMode(true);
        addCard(Zone.GRAVEYARD, playerA, "Walking Corpse", 5);
        addCard(Zone.BATTLEFIELD, playerA, svogthos);
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 5);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{B}{G}: ");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, svogthos, 5, 5);
    }

    @Test
    public void testSuturedGhoul() {
        String ghoul = "Sutured Ghoul"; // 4BBB */* Trample
        //  As Sutured Ghoul enters the battlefield, exile any number of creature cards from your graveyard.
        // Sutured Ghoul's power is equal to the total power of the exiled cards and its toughness is equal to their total toughness.
        String tymaret = "Tymaret, Chosen from Death"; // BB 2/*
        // Tymaret's toughness is equal to your devotion to black.
        String corpse = "Walking Corpse"; // 1B 2/2
        String bogstomper = "Bogstomper"; // 4BB 6/5

        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, corpse);
        addCard(Zone.GRAVEYARD, playerA, tymaret, 1);
        addCard(Zone.GRAVEYARD, playerA, bogstomper, 1);
        addCard(Zone.HAND, playerA, ghoul);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ghoul);
        addTarget(playerA, tymaret + "^" + bogstomper);
        // Total devotion is 3 + 1 so Tymaret has toughness 4 in all zones
        // Therefore Sutured Ghoul exiling Tymaret and Bogstomper should have power 2+6 and toughness 4+5
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, tymaret, 1);
        assertExileCount(playerA, bogstomper, 1);
        assertPowerToughness(playerA, ghoul, 8, 9);

    }

}
