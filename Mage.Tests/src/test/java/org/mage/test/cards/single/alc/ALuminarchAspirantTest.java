package org.mage.test.cards.single.alc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.a.ALuminarchAspirant}
 *
 * A-Luminarch Aspirant {1}{W}
 * Creature - Human Cleric
 * Power/Toughness: 1/1
 *
 * At the beginning of the end step, put a +1/+1 counter on target creature you control.
 *
 * Alchemy rebalance: Changed trigger from beginning of combat to beginning of end step
 * to reduce the card's aggressive tempo advantage.
 *
 * @author Vernon
 */
public class ALuminarchAspirantTest extends CardTestPlayerBase {

    /**
     * Test: Card does NOT trigger at beginning of combat
     * Expected: No counter placed during combat phase
     */
    @Test
    public void testNoCounterAtBeginningOfCombat() {
        addCard(Zone.HAND, playerA, "A-Luminarch Aspirant");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "A-Luminarch Aspirant");
        addTarget(playerA, "A-Luminarch Aspirant"); // target itself for the ability

        setStopAt(1, PhaseStep.BEGINNING_OF_COMBAT);
        execute();

        // A-Luminarch Aspirant should be on the battlefield with no counters yet
        assertPermanentCount(playerA, "A-Luminarch Aspirant", 1);
        assertCounterCount(playerA, "A-Luminarch Aspirant", CounterType.P1P1, 0);
    }

    /**
     * Test: Card DOES trigger at beginning of end step
     * Expected: Counter placed on target creature at end step
     */
    @Test
    public void testCounterAtBeginningOfEndStep() {
        addCard(Zone.HAND, playerA, "A-Luminarch Aspirant");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "A-Luminarch Aspirant");
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Grizzly Bears should have one +1/+1 counter
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        // Grizzly Bears should be 3/3 (2/2 base + 1/+1 counter)
        assertPowerToughness(playerA, "Grizzly Bears", 3, 3);
    }

    /**
     * Test: Multiple end steps trigger multiple counters
     * Expected: Counter placed each end step
     */
    @Test
    public void testMultipleCountersOverMultipleTurns() {
        addCard(Zone.HAND, playerA, "A-Luminarch Aspirant");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "A-Luminarch Aspirant");
        addTarget(playerA, "Grizzly Bears");

        // End turn 1
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // After turn 1 end step: Grizzly Bears has 1 counter
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);

        // Advance to turn 2
        setStopAt(2, PhaseStep.END_TURN);
        addTarget(playerA, "Grizzly Bears");
        execute();

        // After turn 2 end step: Grizzly Bears has 2 counters
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 2);
        // Grizzly Bears should be 4/4 (2/2 base + 2 +1/+1 counters)
        assertPowerToughness(playerA, "Grizzly Bears", 4, 4);
    }

    /**
     * Test: Can target different creatures
     * Expected: Flexibility in targeting over multiple turns
     */
    @Test
    public void testTargetMultipleDifferentCreatures() {
        addCard(Zone.HAND, playerA, "A-Luminarch Aspirant");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "A-Luminarch Aspirant");
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // After turn 1: Grizzly Bears has counter
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertCounterCount(playerA, "Silvercoat Lion", CounterType.P1P1, 0);

        // Turn 2, target different creature
        setStopAt(2, PhaseStep.END_TURN);
        addTarget(playerA, "Silvercoat Lion");
        execute();

        // After turn 2: Silvercoat Lion now has counter
        assertCounterCount(playerA, "Silvercoat Lion", CounterType.P1P1, 1);
        // Grizzly Bears still has original counter
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
    }

    /**
     * Test: Ability only works on your own creatures
     * Expected: Cannot target opponent's creatures
     */
    @Test
    public void testCanOnlyTargetOwnCreatures() {
        addCard(Zone.HAND, playerA, "A-Luminarch Aspirant");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "A-Luminarch Aspirant");
        // Should only be able to target playerA's Grizzly Bears
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // PlayerA's creature should have counter
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        // PlayerB's creature should not have counter
        assertCounterCount(playerB, "Grizzly Bears", CounterType.P1P1, 0);
    }

    /**
     * Test: Card removed before end step doesn't trigger
     * Expected: Ability doesn't resolve if card is removed
     */
    @Test
    public void testAbilityDoesNotTriggerIfCardRemoved() {
        addCard(Zone.HAND, playerA, "A-Luminarch Aspirant");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Remove Soul");
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "A-Luminarch Aspirant");
        addTarget(playerA, "Grizzly Bears");

        // Remove A-Luminarch Aspirant before end step
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Remove Soul");
        addTarget(playerA, "A-Luminarch Aspirant");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Grizzly Bears should have no counters (ability was on removed card)
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 0);
    }
}

