package org.mage.test.cards.single.sos;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SlumberingTrudgeTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SlumberingTrudge Slumbering Trudge} Slumbering Trudge {X}{G}
     * Creature — Plant Beast
     * This creature enters with a number of stun counters on it equal to three minus X. If X is 2 or less, it enters tapped. (If a permanent with a stun counter would become untapped, remove one from it instead.)
     * 6/6
     */
    private static final String trudge = "Slumbering Trudge";

    @Test
    public void test_X_0() {
        addCard(Zone.HAND, playerA, trudge, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, trudge);
        setChoice(playerA, "X=0");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, trudge, 1);
        assertTapped(trudge, true);
        assertCounterCount(playerA, trudge, CounterType.STUN, 3);
    }

    @Test
    public void test_X_1() {
        addCard(Zone.HAND, playerA, trudge, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, trudge);
        setChoice(playerA, "X=1");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, trudge, 1);
        assertTapped(trudge, true);
        assertCounterCount(playerA, trudge, CounterType.STUN, 2);
    }

    @Test
    public void test_X_2() {
        addCard(Zone.HAND, playerA, trudge, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, trudge);
        setChoice(playerA, "X=2");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, trudge, 1);
        assertTapped(trudge, true);
        assertCounterCount(playerA, trudge, CounterType.STUN, 1);
    }

    @Test
    public void test_X_3() {
        addCard(Zone.HAND, playerA, trudge, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, trudge);
        setChoice(playerA, "X=3");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, trudge, 1);
        assertTapped(trudge, false);
        assertCounterCount(playerA, trudge, CounterType.STUN, 0);
    }
}
