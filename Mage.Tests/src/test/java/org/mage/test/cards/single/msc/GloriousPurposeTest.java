package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class GloriousPurposeTest extends CardTestPlayerBase {

    @Test
    public void testConnivingCreatureGetsCounterAndPlanCounterIsAdded() {
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Glorious Purpose");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Echo Inspector");
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Echo Inspector");
        setChoice(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Echo Inspector", CounterType.P1P1, 2);
        assertCounterCount(playerA, "Glorious Purpose", CounterType.PLAN, 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }
}
