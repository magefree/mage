package org.mage.test.cards.single.alc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Advanced gameplay tests for A-Luminarch Aspirant
 *
 * These tests verify the Alchemy rebalance works correctly in game scenarios.
 * They demonstrate the key difference from paper version:
 * - Paper: Triggers at beginning of combat (offensive)
 * - Alchemy: Triggers at beginning of end step (defensive)
 *
 * This makes the card slower and less aggressive in Limited/Constructed.
 *
 * @author Vernon
 */
public class ALuminarchAspirantGameplayTest extends CardTestPlayerBase {

    /**
     * Test: Complete turn cycle with Alchemy trigger
     * Demonstrates that counter is placed at end of turn, not during combat
     */
    @Test
    public void testCompleteTurnCycleWithAlchemyTiming() {
        addCard(Zone.HAND, playerA, "A-Luminarch Aspirant");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        // Turn 1: Cast the card
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "A-Luminarch Aspirant");
        addTarget(playerA, "Silvercoat Lion");

        // Verify card is in play before combat
        setStopAt(1, PhaseStep.BEGINNING_OF_COMBAT);
        execute();
        assertPermanentCount(playerA, "A-Luminarch Aspirant", 1);
        // No counters yet - trigger doesn't fire in combat
        assertCounterCount(playerA, "Silvercoat Lion", CounterType.P1P1, 0);

        // Continue to end of turn where trigger should fire
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        // Now counter should be placed
        assertCounterCount(playerA, "Silvercoat Lion", CounterType.P1P1, 1);
    }

    /**
     * Test: Attacking with a creature boosted by end-step counter
     * Shows the timing implications of end-step trigger
     */
    @Test
    public void testAttackingWithEndStepBoostedCreature() {
        addCard(Zone.HAND, playerA, "A-Luminarch Aspirant");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        // Turn 1: Cast Aspirant targeting Silvercoat Lion
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "A-Luminarch Aspirant");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Silvercoat Lion gets counter at end of turn 1
        assertCounterCount(playerA, "Silvercoat Lion", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 3, 3); // 2/2 + 1/1

        // Turn 2: Cast another creature then attack with the boosted one
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        execute();

        // Attack phase - Silvercoat Lion attacks as 3/3
        declareAttacker(2, playerA, "Silvercoat Lion");
        setStopAt(2, PhaseStep.COMBAT_DAMAGE);
        execute();

        assertLife(playerB, 20 - 3); // Took 3 damage from Silvercoat Lion

        // End of turn 2: Another counter should be placed on Silvercoat Lion
        setStopAt(2, PhaseStep.END_TURN);
        addTarget(playerA, "Silvercoat Lion");
        execute();

        assertCounterCount(playerA, "Silvercoat Lion", CounterType.P1P1, 2);
        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4);
    }

    /**
     * Test: Comparing timing differences
     * Shows why the end-step trigger is a nerf (can't use boost in same-turn combat)
     */
    @Test
    public void testTimingIsNerfedComparedToPaper() {
        // With paper Luminarch Aspirant, you could:
        // 1. Cast in main phase
        // 2. Combat trigger fires (beginning of combat)
        // 3. Creature attacked as boosted

        // With Alchemy version (end step trigger):
        // 1. Cast in main phase
        // 2. Combat trigger does NOT fire
        // 3. Creature attacks as unboosted
        // 4. End step trigger fires
        // 5. Creature will be boosted for NEXT turn's combat

        addCard(Zone.HAND, playerA, "A-Luminarch Aspirant");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        // Cast Aspirant on turn 1
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "A-Luminarch Aspirant");
        addTarget(playerA, "Silvercoat Lion");

        // Declare attack - Silvercoat Lion attacks UNBOOSTED
        declareAttacker(1, playerA, "Silvercoat Lion");
        setStopAt(1, PhaseStep.COMBAT_DAMAGE);
        execute();

        // Verify attack damage is 2 (not 3) - no counter yet
        assertLife(playerB, 20 - 2);

        // End of turn: Counter is finally placed
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertCounterCount(playerA, "Silvercoat Lion", CounterType.P1P1, 1);

        // Next turn attack will benefit from the counter
        declareAttacker(2, playerA, "Silvercoat Lion");
        setStopAt(2, PhaseStep.COMBAT_DAMAGE);
        execute();

        assertLife(playerB, 20 - 2 - 3); // First turn 2 damage, second turn 3 damage (with counter)
    }

    /**
     * Test: Multiple instances of Alchemy card
     * Demonstrates stacking with multiple Aspirants
     */
    @Test
    public void testMultipleAlchemyAspirants() {
        addCard(Zone.HAND, playerA, "A-Luminarch Aspirant", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        // Turn 1: Cast both Aspirants, both targeting same creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "A-Luminarch Aspirant");
        addTarget(playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "A-Luminarch Aspirant");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Both abilities should fire at end step, placing 2 counters
        assertCounterCount(playerA, "Silvercoat Lion", CounterType.P1P1, 2);
        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4); // 2/2 + 2 counters
    }

    /**
     * Test: Interaction with creature removal
     * Demonstrates vulnerability of the end-step trigger
     */
    @Test
    public void testRemovalBeforeTriggerResolves() {
        addCard(Zone.HAND, playerA, "A-Luminarch Aspirant");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        addCard(Zone.HAND, playerB, "Terror");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);

        // Turn 1: Cast Aspirant
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "A-Luminarch Aspirant");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Silvercoat Lion", CounterType.P1P1, 1);

        // Turn 2: Cast another Aspirant
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "A-Luminarch Aspirant");
        addTarget(playerA, "Silvercoat Lion");

        // But opponent removes Silvercoat Lion before end step
        castSpell(2, PhaseStep.BEGIN_COMBAT, playerB, "Terror");
        addTarget(playerB, "Silvercoat Lion");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // Silvercoat Lion is gone, counter effect wasted
        assertPermanentCount(playerA, "Silvercoat Lion", 0);
    }

    /**
     * Test: End-step timing with instant-speed responses
     * Demonstrates that opponent gets priority at end step
     */
    @Test
    public void testOpponentPriorityAtEndStep() {
        addCard(Zone.HAND, playerA, "A-Luminarch Aspirant");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        addCard(Zone.HAND, playerB, "Counterspell");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        // Turn 1: Cast Aspirant
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "A-Luminarch Aspirant");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // At end step, the ability's trigger is placed on stack
        assertCounterCount(playerA, "Silvercoat Lion", CounterType.P1P1, 1);
    }

    /**
     * Test: Interaction with hexproof creatures
     * Verifies A-Luminarch Aspirant respects hexproof restriction
     */
    @Test
    public void testTargetingWithHexproofRestrictions() {
        addCard(Zone.HAND, playerA, "A-Luminarch Aspirant");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Geist of Saint Traft"); // Hexproof creature

        // Turn 1: Cast Aspirant targeting normal creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "A-Luminarch Aspirant");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Silvercoat Lion", CounterType.P1P1, 1);
        // Geist of Saint Traft should have no counters (wasn't targeted)
        assertCounterCount(playerA, "Geist of Saint Traft", CounterType.P1P1, 0);
    }
}

