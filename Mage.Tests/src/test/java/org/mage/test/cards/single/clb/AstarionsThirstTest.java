package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * Astarion's Thirst
 * {3}{B}
 * Instant
 * Exile target creature.
 * Put X +1/+1 counters on a commander creature you control, where X is the power of the creature exiled this way.
 *
 * @author Alex-Vasile
 */
public class AstarionsThirstTest extends CardTestCommander4Players {
    private static final String astarionsThirst = "Astarion's Thirst";

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9414
     *      Adding counters portion does not work
     */
    @Test
    public void canAddCounters() {
        // {R}{W}
        // 0/3
        String akiri = "Akiri, Line-Slinger";
        addCard(Zone.COMMAND, playerA, akiri);
        addCard(Zone.HAND, playerA, astarionsThirst);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // 7/7
        addCard(Zone.BATTLEFIELD, playerD, "Ancient Bronze Dragon");

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, akiri, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, astarionsThirst, "Ancient Bronze Dragon");

        execute();

        assertPowerToughness(playerA, akiri, 7, 10);
        assertCounterCount(playerA, akiri, CounterType.P1P1, 7);
    }
}
