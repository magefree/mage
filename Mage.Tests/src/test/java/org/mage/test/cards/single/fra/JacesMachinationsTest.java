package org.mage.test.cards.single.fra;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class JacesMachinationsTest extends CardTestPlayerBase {

    /**
     * Jace's Machinations {2}{U}
     * Instant
     * Until end of turn, you may activate loyalty abilities of Jace planeswalkers you control on any player's turn
     * any time you could cast an instant.
     * Empower Jace 8. (Put eight loyalty counters on a Jace token you control. If you don't control one, first create
     * a blue Jace planeswalker token with "[-1]: Surveil 1" and "[-3]: Draw a card.")
     */
    @Test
    public void test_ActivateJaceLoyaltyOnOpponentTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Jace Beleren"); // nontoken Jace planeswalker, 3 starting loyalty
        addCard(Zone.HAND, playerA, "Jace's Machinations");

        // cast on the opponent's turn
        castSpell(2, PhaseStep.UPKEEP, playerA, "Jace's Machinations");
        waitStackResolved(2, PhaseStep.UPKEEP);
        // +2: Each player draws a card, activated on the opponent's turn
        activateAbility(2, PhaseStep.DRAW, playerA, "+2:");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Jace's Machinations", 1);
        assertCounterCount(playerA, "Jace Beleren", CounterType.LOYALTY, 5); // 3 + 2
        assertPermanentCount(playerA, "Jace", 1); // empowered token
        assertCounterCount(playerA, "Jace", CounterType.LOYALTY, 8);
    }

    /**
     * Jace's Machinations {2}{U}
     * Instant
     * Until end of turn, you may activate loyalty abilities of Jace planeswalkers you control on any player's turn
     * any time you could cast an instant.
     * Empower Jace 8. (Put eight loyalty counters on a Jace token you control. If you don't control one, first create
     * a blue Jace planeswalker token with "[-1]: Surveil 1" and "[-3]: Draw a card.")
     */
    @Test
    public void test_NonJacePlaneswalkerIsNotAffected() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Ajani Goldmane"); // non-Jace planeswalker, "+1: You gain 2 life."
        addCard(Zone.HAND, playerA, "Jace's Machinations");

        // cast on the opponent's turn
        castSpell(2, PhaseStep.UPKEEP, playerA, "Jace's Machinations");
        waitStackResolved(2, PhaseStep.UPKEEP);
        checkPlayableAbility("Ajani not affected", 2, PhaseStep.DRAW, playerA, "+1:", false);
        // the empowered Jace token is affected, so it can use "-3: Draw a card" on the opponent's turn
        checkPlayableAbility("Jace token affected", 2, PhaseStep.DRAW, playerA, "-3:", true);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Ajani Goldmane", CounterType.LOYALTY, 4); // unchanged starting loyalty
        assertLife(playerA, 20);
    }

    /**
     * Jace's Machinations {2}{U}
     * Instant
     * Until end of turn, you may activate loyalty abilities of Jace planeswalkers you control on any player's turn
     * any time you could cast an instant.
     * Empower Jace 8. (Put eight loyalty counters on a Jace token you control. If you don't control one, first create
     * a blue Jace planeswalker token with "[-1]: Surveil 1" and "[-3]: Draw a card.")
     */
    @Test
    public void test_EffectEndsAtEndOfTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Jace Beleren"); // nontoken Jace planeswalker
        addCard(Zone.HAND, playerA, "Jace's Machinations");

        // cast on the opponent's turn
        castSpell(2, PhaseStep.UPKEEP, playerA, "Jace's Machinations");
        waitStackResolved(2, PhaseStep.UPKEEP);
        checkPlayableAbility("playable on the turn it was cast", 2, PhaseStep.DRAW, playerA, "+2:", true);
        // two turns later it is the opponent's turn again and the effect is gone
        checkPlayableAbility("not playable after that turn", 4, PhaseStep.DRAW, playerA, "+2:", false);

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Jace Beleren", CounterType.LOYALTY, 3); // never activated
    }

    /**
     * Jace's Machinations {2}{U}
     * Instant
     * Until end of turn, you may activate loyalty abilities of Jace planeswalkers you control on any player's turn
     * any time you could cast an instant.
     * Empower Jace 8. (Put eight loyalty counters on a Jace token you control. If you don't control one, first create
     * a blue Jace planeswalker token with "[-1]: Surveil 1" and "[-3]: Draw a card.")
     */
    @Test
    public void test_EmpowerJaceCreatesToken() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Jace's Machinations");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Jace's Machinations");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Jace", 1);
        assertCounterCount(playerA, "Jace", CounterType.LOYALTY, 8);
    }

    /**
     * Jace's Machinations {2}{U}
     * Instant
     * Until end of turn, you may activate loyalty abilities of Jace planeswalkers you control on any player's turn
     * any time you could cast an instant.
     * Empower Jace 8. (Put eight loyalty counters on a Jace token you control. If you don't control one, first create
     * a blue Jace planeswalker token with "[-1]: Surveil 1" and "[-3]: Draw a card.")
     */
    @Test
    public void test_EmpowerJaceAddsCountersToExistingToken() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "No Admittance"); // deals 3 damage to any target, empower Jace 1
        addCard(Zone.HAND, playerA, "Jace's Machinations");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "No Admittance", playerB); // damage to the opponent
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Jace's Machinations");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Jace", 1); // no second token
        assertCounterCount(playerA, "Jace", CounterType.LOYALTY, 9); // 1 + 8
        assertLife(playerB, 17);
    }
}
