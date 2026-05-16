package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class YavimayaBloomsageTest extends CardTestPlayerBase {

    @Test
    public void testPreparesAfterCounterMakesTargetPowerSeven() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Yavimaya Bloomsage");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard", CounterType.P1P1, 4);
        addTarget(playerA, "Elite Vanguard");

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPowerToughness(playerA, "Elite Vanguard", 7, 6);
        assertExileCount(playerA, "Channel", 1);
    }
}
