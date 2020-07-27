package org.mage.test.cards.single.iko;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class TheOzolithTest extends CardTestPlayerBase {

    private static final String ozlth = "The Ozolith";
    private static final String frtld = "Fertilid";
    private static final String wlkncrps = "Walking Corpse";
    private static final String flgrn = "Fully Grown";
    private static final String mrdr = "Murder";
    private static final String ttrkt = "Tatterkite";
    private static final String bltbtl = "Blightbeetle";
    private static final String pnctrblst = "Puncture Blast";

    @Test
    public void testTheOzolithGetCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 6);
        addCard(Zone.BATTLEFIELD, playerA, ozlth);
        addCard(Zone.BATTLEFIELD, playerA, frtld);
        addCard(Zone.HAND, playerA, flgrn);
        addCard(Zone.HAND, playerA, mrdr);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flgrn, frtld);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mrdr, frtld);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, frtld, 0);
        assertCounterCount(ozlth, CounterType.P1P1, 2);
        assertCounterCount(ozlth, CounterType.TRAMPLE, 1);
    }

    @Test
    public void testTheOzolithGiveCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 6);
        addCard(Zone.BATTLEFIELD, playerA, ozlth);
        addCard(Zone.BATTLEFIELD, playerA, frtld);
        addCard(Zone.BATTLEFIELD, playerA, wlkncrps);
        addCard(Zone.HAND, playerA, flgrn);
        addCard(Zone.HAND, playerA, mrdr);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flgrn, frtld);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mrdr, frtld);

        setChoice(playerA, "Yes"); // Move counters at beginning of combat

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, frtld, 0);
        assertCounterCount(wlkncrps, CounterType.P1P1, 2);
        assertCounterCount(wlkncrps, CounterType.TRAMPLE, 1);
        assertCounterCount(ozlth, CounterType.P1P1, 0);
        assertCounterCount(ozlth, CounterType.TRAMPLE, 0);
    }

    @Test
    public void testTheOzolithCantGiveAnyCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, ozlth);
        addCard(Zone.BATTLEFIELD, playerA, frtld);
        addCard(Zone.BATTLEFIELD, playerA, ttrkt);
        addCard(Zone.HAND, playerA, mrdr);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mrdr, frtld);

        setChoice(playerA, "Yes"); // Move counters at beginning of combat

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, frtld, 0);
        assertCounterCount(ttrkt, CounterType.P1P1, 0);
        assertCounterCount(ozlth, CounterType.P1P1, 2);
    }

    @Test
    public void testTheOzolithCantGiveSomeCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 6);
        addCard(Zone.BATTLEFIELD, playerA, ozlth);
        addCard(Zone.BATTLEFIELD, playerA, frtld);
        addCard(Zone.BATTLEFIELD, playerA, wlkncrps);
        addCard(Zone.HAND, playerA, flgrn);
        addCard(Zone.HAND, playerA, mrdr);
        addCard(Zone.BATTLEFIELD, playerB, bltbtl);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flgrn, frtld);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mrdr, frtld);

        setChoice(playerA, "Yes"); // Move counters at beginning of combat

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, frtld, 0);
        assertCounterCount(wlkncrps, CounterType.P1P1, 0);
        assertCounterCount(wlkncrps, CounterType.TRAMPLE, 1);
        assertCounterCount(ozlth, CounterType.P1P1, 2);
        assertCounterCount(ozlth, CounterType.TRAMPLE, 0);
    }

    @Test
    public void testTheOzolithMultiples() {
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 6);
        addCard(Zone.BATTLEFIELD, playerA, ozlth, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mirror Gallery");
        addCard(Zone.BATTLEFIELD, playerA, frtld);
        addCard(Zone.BATTLEFIELD, playerA, wlkncrps);
        addCard(Zone.HAND, playerA, flgrn);
        addCard(Zone.HAND, playerA, mrdr);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flgrn, frtld);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mrdr, frtld);

        setChoice(playerA, "Yes", 2); // Move counters at beginning of combat

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, frtld, 0);
        assertCounterCount(wlkncrps, CounterType.P1P1, 4);
        assertCounterCount(wlkncrps, CounterType.TRAMPLE, 2);
        assertCounterCount(ozlth, CounterType.P1P1, 0);
        assertCounterCount(ozlth, CounterType.TRAMPLE, 0);
    }

    @Ignore
    @Test
    public void testTheOzolithMinusPlus() {
        // TODO: this test fails because of incorrect last known information handling
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, ozlth);
        addCard(Zone.BATTLEFIELD, playerA, frtld);
        addCard(Zone.HAND, playerA, pnctrblst);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pnctrblst, frtld);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, frtld, 0);
        assertCounterCount(ozlth, CounterType.P1P1, 2);
        assertCounterCount(ozlth, CounterType.M1M1, 3);
    }
}
