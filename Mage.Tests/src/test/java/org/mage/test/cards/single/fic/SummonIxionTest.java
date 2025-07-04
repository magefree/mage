package org.mage.test.cards.single.fic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SummonIxionTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SummonIxion Summon: Ixion} {2}{W}
     * Enchantment Creature — Saga Unicorn
     * I - Aerospark — Exile target creature an opponent controls until this Saga leaves the battlefield.
     * II, III - Put a +1/+1 counter on each of up to two target creatures you control. You gain 2 life.
     * First strike
     * 3/3
     */
    private static final String ixion = "Summon: Ixion";

    @Test
    public void test_SimplePlay() {
        addCard(Zone.HAND, playerA, ixion, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ixion);
        addTarget(playerA, "Elite Vanguard");

        checkExileCount("after I, Vanguard exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Elite Vanguard", 1);

        // turn 3
        addTarget(playerA, "Memnite");
        addTarget(playerA, TestPlayer.TARGET_SKIP);

        // turn 5
        checkExileCount("before III, Vanguard exiled", 5, PhaseStep.UPKEEP, playerB, "Elite Vanguard", 1);

        addTarget(playerA, "Grizzly Bears");
        addTarget(playerA, "Memnite");

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 2 * 2);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertCounterCount(playerA, "Memnite", CounterType.P1P1, 2);
        assertPermanentCount(playerB, "Elite Vanguard", 1);
    }
}
