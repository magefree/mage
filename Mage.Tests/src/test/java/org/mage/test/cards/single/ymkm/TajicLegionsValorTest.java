package org.mage.test.cards.single.ymkm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class TajicLegionsValorTest extends CardTestPlayerBase {

    @Test
    public void testCounterSelectsSpellbookAndGivesHaste() {
        addCard(Zone.BATTLEFIELD, playerA, "Tajic, Legion's Valor");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Tajic, Legion's Valor", CounterType.P1P1, 2);
        assertPermanentCount(playerA, "Boros Recruit", 1);
        assertPermanentCount(playerA, "Swiftblade Vindicator", 1);
        assertLife(playerB, 17);
    }

    @Test
    public void testNoSpellbookCardAboveFive() {
        addCard(Zone.BATTLEFIELD, playerA, "Tajic, Legion's Valor");
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tajic, Legion's Valor", CounterType.P1P1, 5);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Tajic, Legion's Valor", CounterType.P1P1, 6);
        assertPermanentCount(playerA, 1);
    }

}
