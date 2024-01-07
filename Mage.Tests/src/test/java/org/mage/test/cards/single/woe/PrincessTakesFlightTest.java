package org.mage.test.cards.single.woe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author notgreat
 */
public class PrincessTakesFlightTest extends CardTestPlayerBase {
    private static final String saga = "The Princess Takes Flight";
    private static final String bear = "Grizzly Bears";

    @Test
    public void testReturn() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, saga);
        addCard(Zone.BATTLEFIELD, playerA, bear);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, saga);
        addTarget(playerA, bear);

        checkPermanentCount("Bear gone", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, bear, 0);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, bear, 1);
        assertGraveyardCount(playerA, saga, 1);
    }
}
