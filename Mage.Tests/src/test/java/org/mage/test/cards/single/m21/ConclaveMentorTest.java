package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ConclaveMentorTest extends CardTestPlayerBase {

    @Test
    public void testIncreaseCounters() {
        // If one or more +1/+1 counters would be put on a creature you control,
        // that many plus one +1/+1 counters are put on that creature instead.
        addCard(Zone.BATTLEFIELD, playerA, "Conclave Mentor");
        // When Bond Beetle enters the battlefield, put a +1/+1 counter on target creature.
        addCard(Zone.HAND, playerA, "Bond Beetle");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bond Beetle");
        addTarget(playerA, "Conclave Mentor");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, "Conclave Mentor", CounterType.P1P1, 2);


    }

    @Test
    public void diesTrigger() {
        // When Conclave Mentor dies, you gain life equal to its power.
        addCard(Zone.BATTLEFIELD, playerA, "Conclave Mentor");
        addCard(Zone.HAND, playerA, "Terror");
        addCard(Zone.HAND, playerA, "Giant Growth");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth","Conclave Mentor");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Terror","Conclave Mentor");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 25);


    }
}
