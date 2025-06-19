package org.mage.test.cards.single.stx;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SpitefulSquadTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SpitefulSquad} {2}{W}{B}
     * Creature — Human Warlock
     * Deathtouch
     * This creature enters with two +1/+1 counters on it.
     * When this creature dies, put its counters on target creature you control.
     * 0/0
     */
    private static final String squad = "Spiteful Squad";

    @Test
    public void test_Simple() {
        addCard(Zone.BATTLEFIELD, playerA, squad);
        addCard(Zone.HAND, playerA, "Murder");
        addCard(Zone.BATTLEFIELD, playerA, "Squire");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", squad, true);
        addTarget(playerA, "Squire");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Squire", CounterType.P1P1, 2);
    }
}
