package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Tests that if a creature was controlled by opponent and died and was cast
 * again by owner, that continuous effects of this creature work with controller
 * for the owner
 *
 * @author LevelX2
 */
public class GainControlDiedCastAgainTest extends CardTestPlayerBase {

    @Test
    public void testBoostEffectsWorksForControllerOfElesh() {

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        /**
         * Volrath's Stronghold Legendary Land {t}: Add {C}.
         * {1}{B}, {tap}: Put target creature card from your graveyard on top of
         * your library.
         */
        addCard(Zone.BATTLEFIELD, playerB, "Volrath's Stronghold");
        /**
         * Elesh Norn, Grand Cenobite {5}{W}{W} Legendary Creature — Praetor 4/7
         * Vigilance Other creatures you control get +2/+2. Creatures your
         * opponents control get -2/-2.
         */
        addCard(Zone.BATTLEFIELD, playerB, "Elesh Norn, Grand Cenobite");
        addCard(Zone.BATTLEFIELD, playerB, "Kalonian Tusker"); // simple 3/3

        /**
         * Keiga, the Tide Star Legendary Dragon Spirit When Keiga, the Tide
         * Star dies, gain control of target creature
         */
        addCard(Zone.BATTLEFIELD, playerA, "Keiga, the Tide Star");
        addCard(Zone.BATTLEFIELD, playerA, "Kalonian Tusker");

        attack(2, playerB, "Elesh Norn, Grand Cenobite");
        block(2, playerA, "Keiga, the Tide Star", "Elesh Norn, Grand Cenobite");
        addTarget(playerA, "Elesh Norn, Grand Cenobite");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Elesh Norn, Grand Cenobite", 1);

        assertPowerToughness(playerA, "Kalonian Tusker", 5, 5);
        assertPowerToughness(playerB, "Kalonian Tusker", 1, 1);
    }

    @Test
    public void testBoostEffectsWorkForController() {
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 6);
        /**
         * Akroma's Vengeance {4}{W}{W} Sorcery Destroy all artifacts,
         * creatures, and enchantments.
         */
        addCard(Zone.HAND, playerB, "Akroma's Vengeance");
        /**
         * Volrath's Stronghold Legendary Land {t}: Add {C}.
         * {1}{B}, {tap}: Put target creature card from your graveyard on top of
         * your library.
         */
        addCard(Zone.BATTLEFIELD, playerB, "Volrath's Stronghold");
        /**
         * Elesh Norn, Grand Cenobite {5}{W}{W} Legendary Creature — Praetor
         * Vigilance Other creatures you control get +2/+2. Creatures your
         * opponents control get -2/-2.
         */
        addCard(Zone.HAND, playerB, "Elesh Norn, Grand Cenobite");

        addCard(Zone.BATTLEFIELD, playerB, "Darksteel Gargoyle"); // indestructible 3/3

        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        /**
         * Keiga, the Tide Star {5}{U} Legendary Dragon Spirit 5/5 When Keiga,
         * the Tide Star dies, gain control of target creature
         */
        addCard(Zone.HAND, playerA, "Keiga, the Tide Star");
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Gargoyle");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Elesh Norn, Grand Cenobite");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Keiga, the Tide Star");

        attack(4, playerB, "Elesh Norn, Grand Cenobite");
        block(4, playerA, "Keiga, the Tide Star", "Elesh Norn, Grand Cenobite");
        addTarget(playerA, "Elesh Norn, Grand Cenobite");

        // Destroy all creatures, enchantments and artifacts
        castSpell(4, PhaseStep.POSTCOMBAT_MAIN, playerB, "Akroma's Vengeance");

        // Put Elesh Norn back on library
        activateAbility(5, PhaseStep.END_TURN, playerB, "{1}{B}, {T}: Put target creature card", "Elesh Norn, Grand Cenobite");

        castSpell(6, PhaseStep.PRECOMBAT_MAIN, playerB, "Elesh Norn, Grand Cenobite");
        setStopAt(6, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Keiga, the Tide Star", 0);
        assertGraveyardCount(playerA, "Keiga, the Tide Star", 1);

        assertPermanentCount(playerB, "Elesh Norn, Grand Cenobite", 1);
        assertGraveyardCount(playerB, "Elesh Norn, Grand Cenobite", 0);
        assertGraveyardCount(playerB, "Akroma's Vengeance", 1);

        assertPermanentCount(playerA, "Darksteel Gargoyle", 1);
        assertPermanentCount(playerB, "Darksteel Gargoyle", 1);

        assertPowerToughness(playerA, "Darksteel Gargoyle", 1, 1);
        assertPowerToughness(playerB, "Darksteel Gargoyle", 5, 5);
    }
}
