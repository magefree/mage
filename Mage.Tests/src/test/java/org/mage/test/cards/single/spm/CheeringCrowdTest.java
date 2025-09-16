package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 *
 * @author Jmlundeen
 */
public class CheeringCrowdTest extends CardTestCommander4Players {

    /*
    Cheering Crowd
    {1}{R/G}
    Creature - Human Citizen
    At the beginning of each player's first main phase, that player may put a +1/+1 counter on this creature. If they do, they add {C} for each counter on it.
    2/2
    */
    private static final String cheeringCrowd = "Cheering Crowd";

    @Test
    public void testCheeringCrowd() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, cheeringCrowd);

        setChoice(playerA, true);
        setChoice(playerB, true);
        setChoice(playerC, true);
        setChoice(playerD, true);

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkManaPool("PlayerA should have 1 Mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "C", 1);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN, playerD);
        checkManaPool("PlayerD should have 2 Mana", 2, PhaseStep.PRECOMBAT_MAIN, playerD, "C", 2);
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN, playerC);
        checkManaPool("PlayerC should have 3 Mana", 3, PhaseStep.PRECOMBAT_MAIN, playerC, "C", 3);
        waitStackResolved(4, PhaseStep.PRECOMBAT_MAIN, playerB);
        checkManaPool("PlayerB should have 4 Mana", 4, PhaseStep.PRECOMBAT_MAIN, playerB, "C", 4);

        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, cheeringCrowd, CounterType.P1P1, 4);
    }
}