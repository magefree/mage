package org.mage.test.cards.single.bot;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class ProwlStoicStrategistTest extends CardTestPlayerBase {

    /*
     * Prowl, Stoic Strategist {3}{W}
     * Legendary Artifact Creature — Robot
     * More Than Meets the Eye {2}{W}
     * Whenever Prowl attacks, exile up to one other target tapped creature or Vehicle.
     * For as long as that card remains exiled, its owner may play it.
     * Whenever a player plays a card exiled with Prowl, you draw a card and convert Prowl.
     * 3/3
     *
     * Prowl, Pursuit Vehicle
     * Legendary Artifact — Vehicle
     * Living metal
     * Whenever another creature or Vehicle you control enters, put a +1/+1 counter on Prowl.
     * If this is the second time this ability has resolved this turn, convert Prowl.
     * 2/3
     */
    private static final String prowl = "Prowl, Stoic Strategist";
    private static final String prowlBack = "Prowl, Pursuit Vehicle";
    private static final String bears = "Grizzly Bears"; // 2/2 {1}{G}
    private static final String copter = "Smuggler's Copter"; // Vehicle 3/3 {2}, crew 1
    private static final String arbor = "Dryad Arbor"; // Land Creature — Forest Dryad 1/1
    private static final String memnite = "Memnite"; // 1/1 {0}

    @Test
    public void testOwnerCastsExiledCreature() {
        addCard(Zone.BATTLEFIELD, playerA, prowl);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerB, bears, 1, true);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);

        attack(1, playerA, prowl, playerB);
        addTarget(playerA, bears);
        checkExileCount("bears exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, bears, 1);
        // only the owner may play it
        checkPlayableAbility("controller can't cast", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + bears, false);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, bears);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3);
        assertPermanentCount(playerB, bears, 1);
        assertExileCount(playerB, 0);
        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, prowlBack, 1);
        assertPermanentCount(playerA, prowl, 0);
    }

    @Test
    public void testExileTappedNoncreatureVehicle() {
        addCard(Zone.BATTLEFIELD, playerA, prowl);
        addCard(Zone.BATTLEFIELD, playerB, copter, 1, true);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        attack(1, playerA, prowl, playerB);
        addTarget(playerA, copter);
        checkExileCount("copter exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, copter, 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, copter);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, copter, 1);
        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, prowlBack, 1);
    }

    @Test
    public void testOwnerPlaysExiledLand() {
        addCard(Zone.BATTLEFIELD, playerA, prowl);
        addCard(Zone.BATTLEFIELD, playerB, arbor, 1, true);

        attack(1, playerA, prowl, playerB);
        addTarget(playerA, arbor);
        checkExileCount("arbor exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, arbor, 1);

        playLand(2, PhaseStep.PRECOMBAT_MAIN, playerB, arbor);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, arbor, 1);
        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, prowlBack, 1);
    }

    @Test
    public void testSecondResolutionConverts() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, prowl);
        addCard(Zone.HAND, playerA, memnite);
        addCard(Zone.HAND, playerA, copter);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, prowlBack + " using More than Meets the Eye");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("entered converted", 1, PhaseStep.PRECOMBAT_MAIN, playerA, prowlBack, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, memnite);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // first resolution: counter, no conversion
        checkPermanentCount("still converted", 1, PhaseStep.PRECOMBAT_MAIN, playerA, prowlBack, 1);
        checkPermanentCounters("one counter", 1, PhaseStep.PRECOMBAT_MAIN, playerA, prowlBack, CounterType.P1P1, 1);

        // a noncreature Vehicle also triggers it
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, copter);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, prowlBack, 0);
        assertPermanentCount(playerA, prowl, 1);
        assertCounterCount(playerA, prowl, CounterType.P1P1, 2);
        assertPowerToughness(playerA, prowl, 3 + 2, 3 + 2);
    }
}
