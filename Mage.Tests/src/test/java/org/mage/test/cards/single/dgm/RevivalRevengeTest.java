package org.mage.test.cards.single.dgm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 701.10d
 * To double a player’s life total, the player gains or loses an amount of life such that their
 * new life total is twice its current value.
 *
 * @author JayDi85
 */
public class RevivalRevengeTest extends CardTestPlayerBase {

    @Test
    public void test_Revenge_DoubleLifeTotalAndOpponentLosesHalf() {
        // Revenge: Double your life total. Target opponent loses half their life, rounded up.
        addCard(Zone.HAND, playerA, "Revival // Revenge"); // Revenge {4}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //
        // Whenever you gain life, put a +1/+1 counter on this creature.
        addCard(Zone.BATTLEFIELD, playerA, "Ajani's Pridemate"); // 2/2
        setLife(playerB, 15);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Revenge", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 20);
        assertCounterCount(playerA, "Ajani's Pridemate", CounterType.P1P1, 1);
        assertLife(playerB, 15 - 8); // half of 15 rounded up
    }

    @Test
    public void test_Revenge_DoubleNegativeLifeTotal() {
        // Revenge: Double your life total. Target opponent loses half their life, rounded up.
        addCard(Zone.HAND, playerA, "Revival // Revenge"); // Revenge {4}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //
        // You can't lose the game and your opponents can't win the game.
        addCard(Zone.BATTLEFIELD, playerA, "Platinum Angel");
        setLife(playerA, -5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Revenge", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, -5 * 2);
        assertLife(playerB, 20 - 10);
    }
}
