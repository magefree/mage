package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class WoodlandChampionTest extends CardTestPlayerBase {

    // Woodland Champion {1}{G}
    // Whenever one or more tokens enter the battlefield under your control, put that many +1/+1 counters on Woodland Champion.

    @Test
    public void test_TriggerOnTwoTokens() {
        addCard(Zone.BATTLEFIELD, playerA, "Woodland Champion", 1);
        // Acorn Harvest {3}{G}
        // Create two 1/1 green Squirrel creature tokens.
        addCard(Zone.HAND, playerA, "Acorn Harvest", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        checkPermanentCounters("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Woodland Champion", CounterType.P1P1, 0);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Acorn Harvest");

        checkPermanentCount("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Squirrel", 2);
        checkPermanentCounters("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Woodland Champion", CounterType.P1P1, 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }
}
