package org.mage.test.cards.single.eve;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class HotheadedGiantTest extends CardTestPlayerBase {
    private static final String giant = "Hotheaded Giant";
    private static final String goblin = "Mons's Goblin Raiders";

    @Test
    public void testSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, goblin);
        addCard(Zone.HAND, playerA, giant);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goblin, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, giant);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, giant, CounterType.M1M1, 0);
    }

    @Test
    public void testNoSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, giant);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, giant);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, giant, CounterType.M1M1, 2);
    }
}
