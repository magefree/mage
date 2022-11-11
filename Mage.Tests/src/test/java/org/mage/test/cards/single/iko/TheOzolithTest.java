package org.mage.test.cards.single.iko;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class TheOzolithTest extends CardTestPlayerBase {

    private static final String THE_OZOLITH = "The Ozolith";
    private static final String FERTILID = "Fertilid";
    private static final String WALKING_CORPSE = "Walking Corpse";
    private static final String FULLY_GROWN = "Fully Grown";
    private static final String MURDER = "Murder";
    private static final String TATTERKITE = "Tatterkite";
    private static final String BLIGHTBEETLE = "Blightbeetle";
    private static final String PUNCTURE_BLAST = "Puncture Blast";
    private static final String BRANCHING_EVOLUTION = "Branching Evolution";
    private static final String MARCH_OF_THE_MACHINES = "March of the Machines";

    @Test
    public void testTheOzolithGetCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 6);
        addCard(Zone.BATTLEFIELD, playerA, THE_OZOLITH);
        addCard(Zone.BATTLEFIELD, playerA, FERTILID);
        addCard(Zone.HAND, playerA, FULLY_GROWN);
        addCard(Zone.HAND, playerA, MURDER);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, FULLY_GROWN, FERTILID);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, MURDER, FERTILID);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, FERTILID, 0);
        assertCounterCount(THE_OZOLITH, CounterType.P1P1, 2);
        assertCounterCount(THE_OZOLITH, CounterType.TRAMPLE, 1);
    }

    @Test
    public void testTheOzolithGiveCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 6);
        addCard(Zone.BATTLEFIELD, playerA, THE_OZOLITH);
        addCard(Zone.BATTLEFIELD, playerA, FERTILID);
        addCard(Zone.BATTLEFIELD, playerA, WALKING_CORPSE);
        addCard(Zone.HAND, playerA, FULLY_GROWN);
        addCard(Zone.HAND, playerA, MURDER);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, FULLY_GROWN, FERTILID);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, MURDER, FERTILID);

        setChoice(playerA, true); // Move counters at beginning of combat

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, FERTILID, 0);
        assertCounterCount(WALKING_CORPSE, CounterType.P1P1, 2);
        assertCounterCount(WALKING_CORPSE, CounterType.TRAMPLE, 1);
        assertCounterCount(THE_OZOLITH, CounterType.P1P1, 0);
        assertCounterCount(THE_OZOLITH, CounterType.TRAMPLE, 0);
    }

    @Test
    public void testTheOzolithCantGiveAnyCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, THE_OZOLITH);
        addCard(Zone.BATTLEFIELD, playerA, FERTILID);
        addCard(Zone.BATTLEFIELD, playerA, TATTERKITE);
        addCard(Zone.HAND, playerA, MURDER);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, MURDER, FERTILID);

        setChoice(playerA, true); // Move counters at beginning of combat

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, FERTILID, 0);
        assertCounterCount(TATTERKITE, CounterType.P1P1, 0);
        assertCounterCount(THE_OZOLITH, CounterType.P1P1, 2);
    }

    @Test
    public void testTheOzolithCantGiveSomeCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 6);
        addCard(Zone.BATTLEFIELD, playerA, THE_OZOLITH);
        addCard(Zone.BATTLEFIELD, playerA, FERTILID);
        addCard(Zone.BATTLEFIELD, playerA, WALKING_CORPSE);
        addCard(Zone.HAND, playerA, FULLY_GROWN);
        addCard(Zone.HAND, playerA, MURDER);
        addCard(Zone.BATTLEFIELD, playerB, BLIGHTBEETLE);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, FULLY_GROWN, FERTILID);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, MURDER, FERTILID);

        setChoice(playerA, true); // Move counters at beginning of combat

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, FERTILID, 0);
        assertCounterCount(WALKING_CORPSE, CounterType.P1P1, 0);
        assertCounterCount(WALKING_CORPSE, CounterType.TRAMPLE, 1);
        assertCounterCount(THE_OZOLITH, CounterType.P1P1, 2);
        assertCounterCount(THE_OZOLITH, CounterType.TRAMPLE, 0);
    }

    @Test
    public void testTheOzolithMultiples() {
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 6);
        addCard(Zone.BATTLEFIELD, playerA, THE_OZOLITH, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mirror Gallery");
        addCard(Zone.BATTLEFIELD, playerA, FERTILID);
        addCard(Zone.BATTLEFIELD, playerA, WALKING_CORPSE);
        addCard(Zone.HAND, playerA, FULLY_GROWN);
        addCard(Zone.HAND, playerA, MURDER);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, FULLY_GROWN, FERTILID);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, MURDER, FERTILID);

        setChoice(playerA, true, 2); // Move counters at beginning of combat

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, FERTILID, 0);
        assertCounterCount(WALKING_CORPSE, CounterType.P1P1, 4);
        assertCounterCount(WALKING_CORPSE, CounterType.TRAMPLE, 2);
        assertCounterCount(THE_OZOLITH, CounterType.P1P1, 0);
        assertCounterCount(THE_OZOLITH, CounterType.TRAMPLE, 0);
    }

    @Test
    public void testTheOzolithMinusPlus() {
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 9);
        addCard(Zone.BATTLEFIELD, playerA, THE_OZOLITH);
        addCard(Zone.BATTLEFIELD, playerA, MARCH_OF_THE_MACHINES);
        addCard(Zone.HAND, playerA, FERTILID);
        addCard(Zone.HAND, playerA, BRANCHING_EVOLUTION);
        addCard(Zone.HAND, playerA, PUNCTURE_BLAST);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, FERTILID, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, BRANCHING_EVOLUTION ,true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, PUNCTURE_BLAST, FERTILID);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, FERTILID, 0);
        // The Ozolith should be a creature which received 4 +1/+1 counters and 3 -1/-1 counters
        // (2 +1/+1 counters from Fertilid doubled by Branching Evolution)
        assertType(THE_OZOLITH, CardType.CREATURE, true);
        // The counters then cancel out and there should only be a single +1/+1 counter left
        assertCounterCount(THE_OZOLITH, CounterType.P1P1, 1);
        assertCounterCount(THE_OZOLITH, CounterType.M1M1, 0);
    }
}
