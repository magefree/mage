package org.mage.test.cards.single.blb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author adams-shaun
 */
public class DriftgloomCoyoteTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DriftgloomCoyote Driftgloom Coyote} {3}{W}{W}
     * Creature - Elemental Coyote 3/4
     * When this creature enters, exile target creature an opponent controls until this creature leaves the battlefield.
     * If that creature had power 2 or less, put a +1/+1 counter on this creature.
     */
    private static final String coyote = "Driftgloom Coyote";

    private void castCoyoteOn(String target) {
        addCard(Zone.HAND, playerA, coyote);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerB, target);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, coyote);
        addTarget(playerA, target);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerB, target, 1);
    }

    @Test
    public void test_ExiledCreatureHadPowerTwo() {
        castCoyoteOn("Grizzly Bears");

        assertCounterCount(playerA, coyote, CounterType.P1P1, 1);
        assertPowerToughness(playerA, coyote, 4, 5);
    }

    @Test
    public void test_ExiledCreatureHadPowerThree() {
        castCoyoteOn("Hill Giant");

        assertCounterCount(playerA, coyote, CounterType.P1P1, 0);
        assertPowerToughness(playerA, coyote, 3, 4);
    }
}
