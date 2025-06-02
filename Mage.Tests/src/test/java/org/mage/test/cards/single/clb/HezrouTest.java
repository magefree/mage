package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class HezrouTest extends CardTestPlayerBase {

    private static final String hezrou = "Hezrou"; // 6/6
    // Whenever one or more creatures you control become blocked, each blocking creature gets -1/-1 until end of turn.
    private static final String stench = "Demonic Stench"; // {B} Instant
    // Each creature that blocked this turn gets -1/-1 until end of turn.

    private static final String kraken = "Kraken Hatchling"; // 0/4
    private static final String guard = "Maritime Guard"; // 1/3
    private static final String fortress = "Fortress Crab"; // 1/6
    private static final String turtle = "Aegis Turtle"; // 0/5
    private static final String pangolin = "Gloom Pangolin"; // 1/5
    private static final String wishcoin = "Wishcoin Crab"; // 2/5

    private void setupCreatures() {
        addCard(Zone.BATTLEFIELD, playerA, kraken);
        addCard(Zone.BATTLEFIELD, playerA, guard);
        addCard(Zone.BATTLEFIELD, playerA, fortress);
        addCard(Zone.BATTLEFIELD, playerB, turtle);
        addCard(Zone.BATTLEFIELD, playerB, pangolin);
        addCard(Zone.BATTLEFIELD, playerB, wishcoin);
    }

    @Test
    public void testTrigger() {
        setupCreatures();
        addCard(Zone.BATTLEFIELD, playerA, hezrou);

        attack(1, playerA, kraken, playerB);
        attack(1, playerA, guard, playerB);
        attack(1, playerA, fortress, playerB);
        block(1, playerB, turtle, kraken);
        block(1, playerB, pangolin, guard);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertPowerToughness(playerA, kraken, 0, 4);
        assertPowerToughness(playerA, guard, 1, 3);
        assertPowerToughness(playerA, fortress, 1, 6);
        assertPowerToughness(playerB, turtle, -1, 4);
        assertPowerToughness(playerB, pangolin, 0, 4);
        assertPowerToughness(playerB, wishcoin, 2, 5);
    }

    @Test
    public void testAdventure() {
        setupCreatures();
        addCard(Zone.HAND, playerA, hezrou);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");

        attack(1, playerA, kraken, playerB);
        attack(1, playerA, guard, playerB);
        attack(1, playerA, fortress, playerB);
        block(1, playerB, turtle, kraken);
        block(1, playerB, pangolin, guard);

        castSpell(1, PhaseStep.END_COMBAT, playerA, stench);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertPowerToughness(playerA, kraken, 0, 4);
        assertPowerToughness(playerA, guard, 1, 3);
        assertPowerToughness(playerA, fortress, 1, 6);
        assertPowerToughness(playerB, turtle, -1, 4);
        assertPowerToughness(playerB, pangolin, 0, 4);
        assertPowerToughness(playerB, wishcoin, 2, 5);
    }

}
