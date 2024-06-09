package org.mage.test.cards.single.tsp;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class PhantomWurmTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.p.PhantomWurm Phantom Wurm} {4}{G}{G}
     * Creature — Wurm Spirit
     * Phantom Wurm enters the battlefield with four +1/+1 counters on it.
     * If damage would be dealt to Phantom Wurm, prevent that damage. Remove a +1/+1 counter from Phantom Wurm.
     * 2/0
     */
    private static final String wurm = "Phantom Wurm";

    @Test
    public void test_DoubleBlocked() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, wurm);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
        addCard(Zone.BATTLEFIELD, playerB, "Eager Cadet");

        attack(1, playerA, wurm, playerB);
        block(1, playerB, "Memnite", wurm);
        block(1, playerB, "Eager Cadet", wurm);

        setChoice(playerA, "X=1"); // damage assignment
        setChoice(playerA, "X=3"); // damage assignment

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Memnite", 1);
        assertGraveyardCount(playerB, "Eager Cadet", 1);
        assertDamageReceived(playerA, wurm, 0); // all is prevented
        assertCounterCount(wurm, CounterType.P1P1, 4 - 1);
    }

    @Test
    public void test_BlockedByAnotherPhantom() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, wurm);
        addCard(Zone.BATTLEFIELD, playerB, "Phantom Nomad");

        attack(1, playerA, wurm, playerB);
        block(1, playerB, "Phantom Nomad", wurm);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertDamageReceived(playerA, wurm, 0); // all is prevented
        assertCounterCount(wurm, CounterType.P1P1, 4 - 1);
        assertDamageReceived(playerB, "Phantom Nomad", 0); // all is prevented
        assertCounterCount("Phantom Nomad", CounterType.P1P1, 2 - 1);
    }

    @Test
    public void test_BlockedByAnotherPhantom_ThenBolt() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, wurm);
        addCard(Zone.BATTLEFIELD, playerB, "Phantom Nomad");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        attack(1, playerA, wurm, playerB);
        block(1, playerB, "Phantom Nomad", wurm);

        castSpell(1, PhaseStep.COMBAT_DAMAGE, playerB, "Lightning Bolt", wurm);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertDamageReceived(playerA, wurm, 0); // all is prevented
        assertCounterCount(wurm, CounterType.P1P1, 4 - 2);
        assertDamageReceived(playerB, "Phantom Nomad", 0); // all is prevented
        assertCounterCount("Phantom Nomad", CounterType.P1P1, 2 - 1);
    }

    @Test
    public void test_DoubleStrike() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, wurm);
        addCard(Zone.BATTLEFIELD, playerB, "Adorned Pouncer"); // 1/1 double strike

        attack(1, playerA, wurm, playerB);
        block(1, playerB, "Adorned Pouncer", wurm);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Adorned Pouncer", 1);
        assertDamageReceived(playerA, wurm, 0); // all is prevented
        assertCounterCount(wurm, CounterType.P1P1, 4 - 2);
    }

    @Test
    public void test_DoubleBlocked_OneFirstStrikeOneNormal() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, wurm);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Striker", 1); // 1/1 first strike haste

        attack(1, playerA, wurm, playerB);
        block(1, playerB, "Memnite", wurm);
        block(1, playerB, "Goblin Striker", wurm);

        setChoice(playerA, "X=1"); // damage assignment
        setChoice(playerA, "X=3"); // damage assignment

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Memnite", 1);
        assertGraveyardCount(playerB, "Goblin Striker", 1);
        assertDamageReceived(playerA, wurm, 0); // all is prevented
        assertCounterCount(wurm, CounterType.P1P1, 4 - 2);
    }

    @Test
    public void test_DoubleBlocked_TwoFirstStrike() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, wurm);
        addCard(Zone.BATTLEFIELD, playerB, "Boros Recruit"); // 1/1 first strike
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Striker", 1); // 1/1 first strike haste

        attack(1, playerA, wurm, playerB);
        block(1, playerB, "Boros Recruit", wurm);
        block(1, playerB, "Goblin Striker", wurm);

        setChoice(playerA, "X=1"); // damage assignment
        setChoice(playerA, "X=3"); // damage assignment

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Boros Recruit", 1);
        assertGraveyardCount(playerB, "Goblin Striker", 1);
        assertDamageReceived(playerA, wurm, 0); // all is prevented
        assertCounterCount(wurm, CounterType.P1P1, 4 - 1);
    }

    @Test
    public void test_Blocked_ThenBolt() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, wurm);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        attack(1, playerA, wurm, playerB);
        block(1, playerB, "Memnite", wurm);

        castSpell(1, PhaseStep.COMBAT_DAMAGE, playerB, "Lightning Bolt", wurm);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Memnite", 1);
        assertDamageReceived(playerA, wurm, 0); // all is prevented
        assertCounterCount(wurm, CounterType.P1P1, 4 - 2);
    }

    @Test
    public void test_Blocked_FirstStrike_ThenBolt() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, wurm);
        addCard(Zone.BATTLEFIELD, playerB, "Boros Recruit");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        attack(1, playerA, wurm, playerB);
        block(1, playerB, "Boros Recruit", wurm);

        castSpell(1, PhaseStep.FIRST_COMBAT_DAMAGE, playerB, "Lightning Bolt", wurm);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Boros Recruit", 1);
        assertDamageReceived(playerA, wurm, 0); // all is prevented
        assertCounterCount(wurm, CounterType.P1P1, 4 - 2);
    }

    @Test
    public void test_Simultanous_NonCombat() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, wurm);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Boros Recruit");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Band Together"); // Up to two target creatures you control each deal damage equal to their power to another target creature.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Band Together");
        addTarget(playerA, "Memnite^Boros Recruit");
        addTarget(playerA, wurm);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerA, wurm, 0); // all is prevented
        assertCounterCount(wurm, CounterType.P1P1, 4 - 1);
    }

    @Test
    public void test_Bolt_ThenBolt() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, wurm);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", wurm);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", wurm);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerA, wurm, 0); // all is prevented
        assertCounterCount(wurm, CounterType.P1P1, 4 - 2);
    }
}
