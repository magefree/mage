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

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        setStrictChooseMode(true);
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
}
