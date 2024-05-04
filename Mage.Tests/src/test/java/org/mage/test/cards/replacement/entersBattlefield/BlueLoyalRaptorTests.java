package org.mage.test.cards.replacement.entersBattlefield;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jimga150
 */
public class BlueLoyalRaptorTests extends CardTestPlayerBase {

    @Test
    public void testCounterTypeAdding() {
        addCard(Zone.BATTLEFIELD, playerA, "Blue, Loyal Raptor", 1);
        addCard(Zone.HAND, playerA, "Huatli's Snubhorn", 1);
        addCard(Zone.HAND, playerA, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blue, Loyal Raptor", CounterType.P1P1, 2);
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blue, Loyal Raptor", CounterType.INDESTRUCTIBLE, 1);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Huatli's Snubhorn", true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", true);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, "Huatli's Snubhorn", CounterType.P1P1, 1);
        assertCounterCount(playerA, "Huatli's Snubhorn", CounterType.INDESTRUCTIBLE, 1);

        assertCounterCount(playerA, "Memnite", CounterType.P1P1, 0);
        assertCounterCount(playerA, "Memnite", CounterType.INDESTRUCTIBLE, 0);
    }

}
