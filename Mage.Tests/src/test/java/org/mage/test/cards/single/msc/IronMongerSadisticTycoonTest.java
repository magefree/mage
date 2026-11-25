package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class IronMongerSadisticTycoonTest extends CardTestPlayerBase {

    /**
     * Iron Monger, Sadistic Tycoon {2}{B}
     * Legendary Artifact Creature - Human Villain 2/2
     * Flying
     * Whenever a creature you control connives, put a +1/+1 counter on each Villain you control.
     */
    private static final String ironMonger = "Iron Monger, Sadistic Tycoon";

    @Test
    public void test_Connive_CountersOnEachVillainYouControl() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, ironMonger);
        //
        addCard(Zone.HAND, playerA, "Echo Inspector"); // {3}{U}, Bird Rogue 2/3, connives on enter
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Mountain"); // land card to discard, no counter from connive itself
        addCard(Zone.LIBRARY, playerA, "Island"); // card to draw
        //
        addCard(Zone.BATTLEFIELD, playerA, "A.I.M. Bot"); // Robot Villain 2/3
        addCard(Zone.BATTLEFIELD, playerB, "A.I.M. Bot"); // Robot Villain 2/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Echo Inspector");
        setChoice(playerA, "Mountain"); // connive discard

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Mountain", 1);
        // villains you control
        assertCounterCount(playerA, ironMonger, CounterType.P1P1, 1);
        assertCounterCount(playerA, "A.I.M. Bot", CounterType.P1P1, 1);
        // not a villain
        assertCounterCount(playerA, "Echo Inspector", CounterType.P1P1, 0);
        // opponent's villain
        assertCounterCount(playerB, "A.I.M. Bot", CounterType.P1P1, 0);
    }
}
