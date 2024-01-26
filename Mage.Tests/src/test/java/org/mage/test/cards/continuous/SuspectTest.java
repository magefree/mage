package org.mage.test.cards.continuous;

import mage.abilities.keyword.MenaceAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class SuspectTest extends CardTestPlayerBase {

    private static final String offender = "Repeat Offender";
    private static final String bear = "Grizzly Bears";
    private static final String alibi = "Airtight Alibi";

    private final void assertSuspected(String name, boolean suspected) {
        Permanent permanent = getPermanent(name);
        Assert.assertEquals("Permanent should " + (suspected ? "" : "not ") + "be suspected", suspected, permanent.isSuspected());
        if (suspected) {
            permanent.getAbilities().containsClass(MenaceAbility.class);
        }
    }

    @Test
    public void testNoSuspect() {
        addCard(Zone.BATTLEFIELD, playerA, offender);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertSuspected(offender, false);
    }

    @Test
    public void testSuspect() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, offender);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{B}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertSuspected(offender, true);
    }

    @Test
    public void testIsSuspected() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3 + 3);
        addCard(Zone.BATTLEFIELD, playerA, offender);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{B}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{B}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertSuspected(offender, true);
        assertCounterCount(playerA, offender, CounterType.P1P1, 1);
    }

    @Test
    public void testCantBlock() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, offender);
        addCard(Zone.BATTLEFIELD, playerB, bear);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{B}");

        attack(2, playerB, bear);
        block(2, playerA, offender, bear);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertSuspected(offender, true);
        assertTapped(bear, true);
        assertLife(playerA, 20 - 2);
    }

    @Test
    public void testAlibiRemoveSuspect() {
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 3 + 3);
        addCard(Zone.BATTLEFIELD, playerA, offender);
        addCard(Zone.HAND, playerA, alibi);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{B}");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, alibi, offender);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertSuspected(offender, false);
    }

    @Test
    public void testAlibiPreventSuspect() {
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 3 + 3);
        addCard(Zone.BATTLEFIELD, playerA, offender);
        addCard(Zone.HAND, playerA, alibi);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, alibi, offender);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}{B}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertSuspected(offender, false);
        assertCounterCount(offender, CounterType.P1P1, 0);
    }
}
