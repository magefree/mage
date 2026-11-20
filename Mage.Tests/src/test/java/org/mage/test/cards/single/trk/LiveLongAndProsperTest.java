package org.mage.test.cards.single.trk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 701.10d
 * To double a player’s life total, the player gains or loses an amount of life such that their
 * new life total is twice its current value.
 * <p>
 * 514.3a
 * At this point, the game checks to see if any state-based actions would be performed and/or any
 * triggered abilities are waiting to be put onto the stack (including those that trigger “at the
 * beginning of the next cleanup step”). If so, those state-based actions are performed, then those
 * triggered abilities are put on the stack, then the active player gets priority. Players may cast
 * spells and activate abilities. Once the stack is empty and all players pass in succession,
 * another cleanup step begins.
 *
 * @author JayDi85
 */
public class LiveLongAndProsperTest extends CardTestPlayerBase {

    @Test
    public void test_DoubleLifeTotal_IsLifeGain() {
        // Double your life total. Draw cards equal to the number of cards in your hand.
        // You have no maximum hand size until your next turn.
        addCard(Zone.HAND, playerA, "Live Long and Prosper"); // {5}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Whenever you gain life, put a +1/+1 counter on this creature.
        addCard(Zone.BATTLEFIELD, playerA, "Ajani's Pridemate"); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Live Long and Prosper");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 20);
        assertCounterCount(playerA, "Ajani's Pridemate", CounterType.P1P1, 1);
    }

    @Test
    public void test_DoubleLifeTotal_LifeGainReplaced() {
        // Double your life total. Draw cards equal to the number of cards in your hand.
        // You have no maximum hand size until your next turn.
        addCard(Zone.HAND, playerA, "Live Long and Prosper"); // {5}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // If you would gain life, you gain twice that much life instead.
        addCard(Zone.BATTLEFIELD, playerA, "Boon Reflection");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Live Long and Prosper");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 20 * 2);
    }

    @Test
    public void test_DoubleLifeTotal_CantGainLife() {
        // Double your life total. Draw cards equal to the number of cards in your hand.
        // You have no maximum hand size until your next turn.
        addCard(Zone.HAND, playerA, "Live Long and Prosper"); // {5}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Mountain", 2);
        //
        // Players can't gain life.
        addCard(Zone.BATTLEFIELD, playerB, "Leyline of Punishment");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Live Long and Prosper");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertHandCount(playerA, 2 + 2); // the other parts of the spell still work
    }

    @Test
    public void test_DoubleNegativeLifeTotal_IsLifeLoss() {
        // Double your life total. Draw cards equal to the number of cards in your hand.
        // You have no maximum hand size until your next turn.
        addCard(Zone.HAND, playerA, "Live Long and Prosper"); // {5}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // You can't lose the game and your opponents can't win the game.
        addCard(Zone.BATTLEFIELD, playerA, "Platinum Angel");
        setLife(playerA, -5);
        //
        // Whenever an opponent loses life, you gain that much life.
        addCard(Zone.BATTLEFIELD, playerB, "Exquisite Blood");

        // the test setup of the life total is a life loss too
        checkLife("before", 1, PhaseStep.PRECOMBAT_MAIN, playerB, 20 + 5);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Live Long and Prosper");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, -5 - 5);
        assertLife(playerB, 20 + 5 + 5); // setup + doubling
    }

    @Test
    public void test_DrawEqualToCardsInHand() {
        // Double your life total. Draw cards equal to the number of cards in your hand.
        // You have no maximum hand size until your next turn.
        addCard(Zone.HAND, playerA, "Live Long and Prosper"); // {5}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Mountain", 3);

        // the spell itself is not in hand on resolve
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Live Long and Prosper");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 3 + 3);
    }

    @Test
    public void test_CantBeCountered() {
        // This spell can't be countered.
        addCard(Zone.HAND, playerA, "Live Long and Prosper"); // {5}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Counter target spell.
        addCard(Zone.HAND, playerB, "Cancel"); // {1}{U}{U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Live Long and Prosper");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cancel", "Live Long and Prosper", "Live Long and Prosper");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 20);
        assertGraveyardCount(playerA, "Live Long and Prosper", 1);
        assertGraveyardCount(playerB, "Cancel", 1);
    }

    @Test
    public void test_NoMaximumHandSizeUntilYourNextTurn() {
        // Double your life total. Draw cards equal to the number of cards in your hand.
        // You have no maximum hand size until your next turn.
        addCard(Zone.HAND, playerA, "Live Long and Prosper"); // {5}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Mountain", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Live Long and Prosper");

        // no discard down to 7 in the cleanup step of this turn
        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertHandCount(playerA, 5 + 5);
    }

    @Test
    public void test_NoMaximumHandSizeUntilYourNextTurn_SecondCleanupStep() {
        // Double your life total. Draw cards equal to the number of cards in your hand.
        // You have no maximum hand size until your next turn.
        addCard(Zone.HAND, playerA, "Live Long and Prosper"); // {5}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Mountain", 5);
        //
        // Create three 2/2 white Knight creature tokens. Exile them at the beginning of the next cleanup step.
        addCard(Zone.HAND, playerA, "Waylay"); // {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Live Long and Prosper");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Waylay");

        // the cleanup trigger makes a second cleanup step in the same turn - still no discard down to 7
        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Knight Token", 0);
        assertHandCount(playerA, 5 + 6); // fillers + drawn cards (the fillers and Waylay were in hand on resolve)
    }
}
