package org.mage.test.cards.single.ecl;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DawnhandDissidentTest extends CardTestPlayerBase {

    @Test
    public void testCastsExiledCreatureByRemovingCountersFromMultipleCreatures() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Dawnhand Dissident");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion", CounterType.P1P1, 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Blight 2: Exile target card", "Grizzly Bears");
        setChoice(playerA, "Hill Giant"); // Blight 2

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", true);
        setChoice(playerA, "Hill Giant^Silvercoat Lion"); // remove counters from among creatures
        setChoice(playerA, "X=2"); // remove both -1/-1 counters from Hill Giant

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertCounterCount(playerA, "Hill Giant", CounterType.M1M1, 0);
        assertCounterCount(playerA, "Silvercoat Lion", CounterType.P1P1, 0);
    }
}
