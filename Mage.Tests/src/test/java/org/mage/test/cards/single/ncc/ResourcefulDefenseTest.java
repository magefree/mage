package org.mage.test.cards.single.ncc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.r.ResourcefulDefense Resourceful Defense} {2}{W}
 * <p>
 * Whenever a permanent you control leaves the battlefield, if it had counters on it,
 * put those counters on target permanent you control.
 * <p>
 * {4}{W}: Move any number of counters from target permanent you control to another target permanent you control.
 *
 * @author Alex-Vasile
 */
public class ResourcefulDefenseTest extends CardTestPlayerBase {
    private static final String resourcefulDefense = "Resourceful Defense";
    // Vivid Creek enters the battlefield tapped with two charge counters on it.
    private static final String vividCreek = "Vivid Creek";
    private static final String everflowingChalice = "Everflowing Chalice";
    // Steelbane Hydra enters the battlefield with X +1/+1 counters on it.
    private static final String steelbaneHydra = "Steelbane Hydra"; // {X}{G}{G}
    private static final String lightningBolt = "Lightning Bolt";

    /**
     * Move counters from a creature that died.
     */
    @Test
    public void testMoveWhenDied() {
        addCard(Zone.BATTLEFIELD, playerA, "Archway Commons", 9);
        addCard(Zone.BATTLEFIELD, playerA, resourcefulDefense);
        addCard(Zone.BATTLEFIELD, playerA, everflowingChalice);
        addCard(Zone.HAND, playerA, steelbaneHydra);
        addCard(Zone.HAND, playerA, lightningBolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, steelbaneHydra);
        setChoice(playerA, "X=1");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, lightningBolt, steelbaneHydra);
        addTarget(playerA, everflowingChalice);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertCounterCount(everflowingChalice, CounterType.P1P1, 1);
    }

    /**
     * Move all of one counter from one permanent to another when the source only has one coutner type.
     */
    @Test
    public void testMoveAllSingleCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Archway Commons", 5);
        addCard(Zone.BATTLEFIELD, playerA, resourcefulDefense);
        addCard(Zone.BATTLEFIELD, playerA, vividCreek);
        addCard(Zone.BATTLEFIELD, playerA, everflowingChalice);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{W}: ");
        addTarget(playerA, vividCreek);
        addTarget(playerA, everflowingChalice);
        setChoiceAmount(playerA, 2);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertCounterCount(vividCreek, CounterType.CHARGE, 0);
        assertCounterCount(everflowingChalice, CounterType.CHARGE, 2);
    }

    /**
     * Move some of one counter from one permanent to another when the source only has one coutner type.
     */
    @Test
    public void testSomeAllSingleCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Archway Commons", 5);
        addCard(Zone.BATTLEFIELD, playerA, resourcefulDefense);
        addCard(Zone.BATTLEFIELD, playerA, vividCreek);
        addCard(Zone.BATTLEFIELD, playerA, everflowingChalice);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{W}: ");
        addTarget(playerA, vividCreek);
        addTarget(playerA, everflowingChalice);
        setChoiceAmount(playerA, 1);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertCounterCount(vividCreek, CounterType.CHARGE, 1);
        assertCounterCount(everflowingChalice, CounterType.CHARGE, 1);
    }

    /**
     * Move multiple counter types from one permanent to another.
     *
     * Also tests that when a creature without counters dies that you won't be prompted.
     * The hydra has no counters after the second activation and will die because toughtness==0, but we aren't prompted
     * for targets when it dies.
     */
    @Test
    public void testMoveAllMultipleCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Archway Commons", 8);
        addCard(Zone.BATTLEFIELD, playerA, resourcefulDefense);
        addCard(Zone.BATTLEFIELD, playerA, vividCreek);
        addCard(Zone.HAND, playerA, steelbaneHydra);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, steelbaneHydra);
        setChoice(playerA, "X=1");

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{W}: ");
        addTarget(playerA, vividCreek);
        addTarget(playerA, steelbaneHydra);
        setChoiceAmount(playerA, 2);

        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{W}: ");
        addTarget(playerA, steelbaneHydra);
        addTarget(playerA, vividCreek);
        setChoiceAmount(playerA, 2);
        setChoiceAmount(playerA, 1);

        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertCounterCount(vividCreek, CounterType.CHARGE, 2);
        assertCounterCount(vividCreek, CounterType.P1P1, 1);
        assertGraveyardCount(playerA, steelbaneHydra, 1);
    }

    /**
     * Move multiple counter types from a creature that died.
     */
    @Test
    public void testMoveMultipleWhenDied() {
        addCard(Zone.BATTLEFIELD, playerA, "Archway Commons", 9);
        addCard(Zone.BATTLEFIELD, playerA, resourcefulDefense);
        addCard(Zone.BATTLEFIELD, playerA, everflowingChalice);
        addCard(Zone.BATTLEFIELD, playerA, vividCreek);
        addCard(Zone.HAND, playerA, steelbaneHydra);
        addCard(Zone.HAND, playerA, lightningBolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, steelbaneHydra);
        setChoice(playerA, "X=1");

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{W}: ");
        addTarget(playerA, vividCreek);
        addTarget(playerA, steelbaneHydra);
        setChoiceAmount(playerA, 2);

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, steelbaneHydra);
        addTarget(playerA, everflowingChalice);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertCounterCount(vividCreek, CounterType.CHARGE, 0);
        assertCounterCount(everflowingChalice, CounterType.CHARGE, 2);
        assertCounterCount(everflowingChalice, CounterType.P1P1, 1);
    }
}
