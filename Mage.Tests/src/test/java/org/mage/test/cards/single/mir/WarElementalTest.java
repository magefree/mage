package org.mage.test.cards.single.mir;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class WarElementalTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.w.WarElemental War Elemental} {R}{R}{R}
     * Creature — Elemental
     * When War Elemental enters the battlefield, sacrifice it unless an opponent was dealt damage this turn.
     * Whenever an opponent is dealt damage, put that many +1/+1 counters on War Elemental.
     * 1/1
     */
    private static final String elemental = "War Elemental";

    @Test
    public void test_Trigger_Combat() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, elemental);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");

        attack(1, playerA, "War Elemental");
        attack(1, playerA, "Grizzly Bears");
        attack(1, playerA, "Elite Vanguard");
        block(1, playerB, "Memnite", "Elite Vanguard");

        // Only one trigger.

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerB, 1);
        assertCounterCount(playerA, elemental, CounterType.P1P1, 1 + 2);
        assertLife(playerB, 20 - 1 - 2);
    }

    @Test
    public void test_Trigger_NonCombat() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, elemental);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, elemental, CounterType.P1P1, 3);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_NoTrigger_You() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, elemental);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, elemental, CounterType.P1P1, 0);
        assertLife(playerA, 20 - 3);
    }
}
