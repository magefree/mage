package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class KineticOozeTest extends CardTestPlayerBase {

    @Test
    public void testXFiveDoesNotRequireCounterDoublingTargets() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.HAND, playerA, "Kinetic Ooze");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerB, "Sol Ring");
        addCard(Zone.LIBRARY, playerA, "Island");

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", CounterType.P1P1, 2);
        setChoice(playerA, "X=5");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kinetic Ooze");
        addTarget(playerA, "Sol Ring");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kinetic Ooze", 1);
        assertPowerToughness(playerA, "Kinetic Ooze", 5, 5);
        assertPermanentCount(playerB, "Sol Ring", 0);
        assertGraveyardCount(playerB, "Sol Ring", 1);
        assertPowerToughness(playerA, "Memnite", 3, 3);
        assertHandCount(playerA, "Island", 1);
    }

    @Test
    public void testXTenDestroysArtifactDrawsAndDoublesOtherCreatureCounters() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.HAND, playerA, "Kinetic Ooze");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 11);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerB, "Sol Ring");
        addCard(Zone.LIBRARY, playerA, "Island");

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", CounterType.P1P1, 2);
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard", CounterType.P1P1, 1);
        setChoice(playerA, "X=10");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kinetic Ooze");
        addTarget(playerA, "Sol Ring");
        addTarget(playerA, "Memnite^Elite Vanguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kinetic Ooze", 1);
        assertPowerToughness(playerA, "Kinetic Ooze", 10, 10);
        assertPermanentCount(playerB, "Sol Ring", 0);
        assertGraveyardCount(playerB, "Sol Ring", 1);
        assertPowerToughness(playerA, "Memnite", 5, 5);
        assertPowerToughness(playerA, "Elite Vanguard", 4, 3);
        assertHandCount(playerA, "Island", 1);
    }
}
