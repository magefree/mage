package org.mage.test.cards.single.woc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CourtOfGarenbrigTest extends CardTestPlayerBase {
    
    /**
     * Court of Garenbrig
     * {1}{G}{G}
     * Enchantment
     *
     * When Court of Garenbrig enters the battlefield, you become the monarch.
     * At the beginning of your upkeep, distribute two +1/+1 counters among up to two target creatures. Then if you're the monarch, double the number of +1/+1 counters on each creature you control.
     */
    private final String court = "Court of Garenbrig";
    private final String bears = "Grizzly Bears"; // 2/2
    private final String piker = "Goblin Piker"; // 2/1
    private final String beetle = "Bond Beetle"; // {G} 0/1, enters with a +1/+1

    @Test
    public void testNoTargetMonarch() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, court);
        addCard(Zone.HAND, playerA, beetle);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, beetle, true);
        addTarget(playerA, beetle); // beetle trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, court);

        addTarget(playerA, TestPlayer.TARGET_SKIP); // no target for court trigger
        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertCounterCount(bears, CounterType.P1P1, 0);
        assertCounterCount(beetle, CounterType.P1P1, 1 * 2);
    }

    @Test
    public void testNoTargetNotMonarch() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, court);
        addCard(Zone.HAND, playerA, beetle);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerB, piker);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, beetle, true);
        addTarget(playerA, beetle); // beetle trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, court);

        attack(2, playerB, piker);

        addTarget(playerA, TestPlayer.TARGET_SKIP); // no target for court trigger
        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertCounterCount(bears, CounterType.P1P1, 0);
        assertCounterCount(beetle, CounterType.P1P1, 1);
    }

    @Test
    public void testOneTargetMonarch() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, court);
        addCard(Zone.HAND, playerA, beetle);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, beetle, true);
        addTarget(playerA, beetle); // beetle trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, court);

        addTarget(playerA, bears + "^X=2");
        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertCounterCount(bears, CounterType.P1P1, 2 * 2);
        assertCounterCount(beetle, CounterType.P1P1, 1 * 2);
    }

    @Test
    public void testOneTargetNotMonarch() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, court);
        addCard(Zone.HAND, playerA, beetle);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerB, piker);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, beetle, true);
        addTarget(playerA, beetle); // beetle trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, court);

        attack(2, playerB, piker);

        addTarget(playerA, bears + "^X=2");
        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertCounterCount(bears, CounterType.P1P1, 2);
        assertCounterCount(beetle, CounterType.P1P1, 1);
    }

    @Test
    public void testTwoTargetMonarch() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, court);
        addCard(Zone.HAND, playerA, beetle);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, beetle, true);
        addTarget(playerA, beetle); // beetle trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, court);

        addTarget(playerA, bears + "^X=1");
        addTarget(playerA, beetle + "^X=1");
        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertCounterCount(bears, CounterType.P1P1, 1 * 2);
        assertCounterCount(beetle, CounterType.P1P1, (1+1) * 2);
    }

    @Test
    public void testTwoTargetNotMonarch() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, court);
        addCard(Zone.HAND, playerA, beetle);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerB, piker);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, beetle, true);
        addTarget(playerA, beetle); // beetle trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, court);

        attack(2, playerB, piker);

        addTarget(playerA, bears + "^X=1");
        addTarget(playerA, beetle + "^X=1");
        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertCounterCount(bears, CounterType.P1P1, 1);
        assertCounterCount(beetle, CounterType.P1P1, 1+1);
    }
}
