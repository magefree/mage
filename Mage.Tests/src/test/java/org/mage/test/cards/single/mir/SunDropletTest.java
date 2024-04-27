package org.mage.test.cards.single.mir;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SunDropletTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SunDroplet Sun Droplet} {2}
     * Artifact
     * Whenever you’re dealt damage, put that many charge counters on Sun Droplet.
     * At the beginning of each upkeep, you may remove a charge counter from Sun Droplet. If you do, you gain 1 life.
     */
    private static final String droplet = "Sun Droplet";

    @Test
    public void test_Trigger_Combat() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, droplet);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2); // 2/2

        attack(1, playerA, "Grizzly Bears");
        attack(1, playerA, "Grizzly Bears");

        // Only one trigger.

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerB, droplet, CounterType.CHARGE, 2 * 2);
        assertLife(playerB, 20 - 2 * 2);
    }

    @Test
    public void test_Trigger_NonCombat() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, droplet);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerB, droplet, CounterType.CHARGE, 3);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_NoTrigger_OtherPlayer() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, droplet);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerB, droplet, CounterType.CHARGE, 0);
        assertLife(playerA, 20 - 3);
    }
}
