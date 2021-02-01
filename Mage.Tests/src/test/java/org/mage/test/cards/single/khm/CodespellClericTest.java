package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class CodespellClericTest extends CardTestPlayerBase {

    private static final String cleric = "Codespell Cleric";
    private static final String relic = "Darksteel Relic";

    @Test
    public void testFirstSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, cleric);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cleric);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, cleric, CounterType.P1P1, 0);
    }

    @Test
    public void testSecondSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, relic);
        addCard(Zone.HAND, playerA, cleric);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, relic);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, cleric);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, cleric, CounterType.P1P1, 1);
    }

    @Test
    public void testThirdSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, relic, 2);
        addCard(Zone.HAND, playerA, cleric);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, relic);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, relic);
        addTarget(playerA, cleric);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, cleric);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, cleric, CounterType.P1P1, 0);
    }
}
