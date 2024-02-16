package org.mage.test.cards.planeswalker;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class NissaStewardOfElementsTest extends CardTestPlayerBase {

    private static final String nissa = "Nissa, Steward of Elements";

    private void doTest(int xValue) {
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 2 + xValue);
        addCard(Zone.HAND, playerA, nissa);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nissa);
        setChoice(playerA, "X=" + xValue);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        if (xValue == 0) {
            assertGraveyardCount(playerA, nissa, 1);
        } else {
            assertCounterCount(playerA, nissa, CounterType.LOYALTY, xValue);
        }
    }

    @Test
    public void test0Counters() {
        doTest(0);
    }

    @Test
    public void test1Counter() {
        doTest(1);
    }

    @Test
    public void test2Counters() {
        doTest(2);
    }

    @Test
    public void test10Counters() {
        doTest(10);
    }
}
