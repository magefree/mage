package org.mage.test.cards.single.ulg;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Ignore;
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

    @Ignore
    @Test
    public void testSacrificeDiesTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, welder);
        addCard(Zone.BATTLEFIELD, playerA, wurmcoil);
        addCard(Zone.BATTLEFIELD, playerA, aspirant);
        addCard(Zone.GRAVEYARD, playerA, relic);

        addTarget(playerA, relic);
        addTarget(playerA, wurmcoil);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}:");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, wurmcoil, 1);
        assertPermanentCount(playerA, relic, 1);
        assertCounterCount(aspirant, CounterType.P1P1, 1);
        assertPermanentCount(playerA, "Wurm", 2); // TODO: currently fails here
    }
}
