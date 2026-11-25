package org.mage.test.cards.single.ecl;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class RetchedWretchTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.r.RetchedWretch}
     * <p>
     * {2}{B} Creature - Goblin 4/2<br>
     * When this creature dies, if it had a -1/-1 counter on it, return it to the battlefield under its owner's
     * control and it loses all abilities.
     */
    private static final String RETCHED_WRETCH = "Retched Wretch";

    @Test
    public void test_ReturnAndLoseAllAbilities() {
        addCustomEffect_TargetDestroy(playerA, 1);
        addCard(Zone.BATTLEFIELD, playerA, RETCHED_WRETCH);

        // first die - return without abilities
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, RETCHED_WRETCH, CounterType.M1M1, 1);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", RETCHED_WRETCH);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("returned", 1, PhaseStep.PRECOMBAT_MAIN, playerA, RETCHED_WRETCH, 1);

        // second die - no more returns
        addCounters(1, PhaseStep.POSTCOMBAT_MAIN, playerA, RETCHED_WRETCH, CounterType.M1M1, 1);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "target destroy", RETCHED_WRETCH);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, RETCHED_WRETCH, 1);
    }
}
