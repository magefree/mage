package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.c.CodespellCleric Codespell Cleric}
 * {W}
 * Creature — Human Cleric
 * P/T 1/1
 * Vigilance
 * When Codespell Cleric enters the battlefield, if it was the second spell you cast this turn, put a +1/+1 counter on target creature.
 *
 * @author TheElk801
 */
public class CodespellClericTest extends CardTestPlayerBase {

    private static final String cleric = "Codespell Cleric";
    private static final String relic = "Darksteel Relic";

    /**
     * No +1/+1 since its the first spell.
     */
    @Test
    public void testFirstSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, cleric);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cleric);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, cleric, CounterType.P1P1, 0);
    }

    /**
     * Put a +1/+1 since it's the second spell.
     */
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

    /**
     * No +1/+1 since its the third spell.
     */
    @Test
    public void testThirdSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, relic, 2);
        addCard(Zone.HAND, playerA, cleric);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, relic);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, relic);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, cleric);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, cleric, CounterType.P1P1, 0);
    }
}
