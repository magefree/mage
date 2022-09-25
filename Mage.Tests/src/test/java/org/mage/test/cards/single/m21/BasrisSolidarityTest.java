package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BasrisSolidarityTest extends CardTestPlayerBase {

    private static final String basrisSolidarity = "Basri's Solidarity";

    @Test
    public void addCountersToControlledCreatures(){
        addCard(Zone.BATTLEFIELD, playerA, "Savannah Lions");
        addCard(Zone.BATTLEFIELD, playerA, "Scryb Sprites");
        addCard(Zone.BATTLEFIELD, playerB, "Raging Goblin");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Put a +1/+1 counter on each creature you control.
        addCard(Zone.HAND, playerA, basrisSolidarity);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, basrisSolidarity);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, "Savannah Lions", CounterType.P1P1, 1);
        assertCounterCount(playerA, "Scryb Sprites", CounterType.P1P1, 1);
        assertCounterCount(playerB, "Raging Goblin", CounterType.P1P1, 0);

    }
}
