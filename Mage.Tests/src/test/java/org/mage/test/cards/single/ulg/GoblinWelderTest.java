package org.mage.test.cards.single.ulg;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class GoblinWelderTest extends CardTestPlayerBase {

    private static final String welder = "Goblin Welder";
    private static final String wurmcoil = "Wurmcoil Engine";
    private static final String relic = "Darksteel Relic";
    private static final String aspirant = "Blood Aspirant";

    @Test
    public void testSacrificeDiesTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, welder);
        addCard(Zone.BATTLEFIELD, playerA, wurmcoil);
        addCard(Zone.BATTLEFIELD, playerA, aspirant);
        addCard(Zone.GRAVEYARD, playerA, relic);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}:");
        addTarget(playerA, wurmcoil);
        addTarget(playerA, relic);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        // must have 2 dies triggers on stack: from source and from another
        setChoice(playerA, "Whenever you sacrifice a permanent"); // select from 2 triggers
        checkStackSize("check triggers", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, wurmcoil, 1);
        assertPermanentCount(playerA, relic, 1);
        assertCounterCount(aspirant, CounterType.P1P1, 1);
        assertPermanentCount(playerA, "Phyrexian Wurm Token", 2);
    }
}
