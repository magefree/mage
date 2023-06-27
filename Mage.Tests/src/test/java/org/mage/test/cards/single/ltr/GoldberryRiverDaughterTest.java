package org.mage.test.cards.single.ltr;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;

public class GoldberryRiverDaughterTest extends CardTestPlayerBase {
    static final String goldberry = "Goldberry, River-Daughter";
    static final String ability1 = "{T}: Move a counter of each kind not on {this} from another target permanent you control onto Goldberry.";
    static final String ability2 = "{U}, {T}: Move one or more counters from Goldberry onto another target permanent you control. If you do, draw a card.";

    @Test
    // Author: alexander-novo
    // Happy path test - remove some counters from something. Then put some of them on something else.
    public void testHappyPath() {
        CounterType counter1 = CounterType.ACORN;
        CounterType counter2 = CounterType.AEGIS;
        String island = "Island";

        addCard(Zone.BATTLEFIELD, playerA, goldberry, 1);
        addCard(Zone.BATTLEFIELD, playerA, island, 1);

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, island, counter1, 2);
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, island, counter2, 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ability1, island);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);

        checkPermanentCounters("First Ability", 1, PhaseStep.PRECOMBAT_MAIN, playerA, island, counter1, 1);
        checkPermanentCounters("First Ability", 1, PhaseStep.PRECOMBAT_MAIN, playerA, island, counter2, 0);
        checkPermanentCounters("First Ability", 1, PhaseStep.PRECOMBAT_MAIN, playerA, goldberry, counter1, 1);
        checkPermanentCounters("First Ability", 1, PhaseStep.PRECOMBAT_MAIN, playerA, goldberry, counter2, 1);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, ability2, island);
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN, 1);
        setChoiceAmount(playerA, 0, 1); // acorn, aegis counters

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertCounterCount(island, counter1, 1);
        assertCounterCount(island, counter2, 1);
        assertCounterCount(goldberry, counter1, 1);
        assertCounterCount(goldberry, counter2, 0);

        // One from turn 2 draw, one from goldberry
        assertHandCount(playerA, 2);
    }

    @Test
    // Author: alexander-novo
    // Unhappy path - Try to remove some counters from something when some of those counters are already on Goldberry
    public void testCounterAlreadyOnGoldberry() {
        CounterType counter = CounterType.ACORN;
        String island = "Island";

        addCard(Zone.BATTLEFIELD, playerA, goldberry, 1);
        addCard(Zone.BATTLEFIELD, playerA, island, 1);

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, island, counter, 1);
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, goldberry, counter, 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ability1, island);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertCounterCount(island, counter, 1);
        assertCounterCount(goldberry, counter, 1);
    }

    @Test(expected = AssertionError.class)
    // Author: alexander-novo
    // Unhappy path - Try to not move a counter from Goldberry even though she has a counter on her.
    // Should fail since we are attempting to move 0 counters, even though we must move at least one if possible.
    public void testNotMovingCounter() {
        CounterType counter = CounterType.ACORN;
        String island = "Island";

        addCard(Zone.BATTLEFIELD, playerA, goldberry, 1);
        addCard(Zone.BATTLEFIELD, playerA, island, 1);

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, goldberry, counter, 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ability2, island);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        setChoiceAmount(playerA, 0); // Try to remove 0 counters from goldberry

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertCounterCount(goldberry, counter, 0);
        assertCounterCount(island, counter, 1);
        assertHandCount(playerA, 1);
    }

    @Test
    // Author: alexander-novo
    // Unhappy path - Activate second ability with no counters on Goldberry
    public void testNoCounters() {
        String island = "Island";

        addCard(Zone.BATTLEFIELD, playerA, goldberry, 1);
        addCard(Zone.BATTLEFIELD, playerA, island, 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ability2, island);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 0);
    }

    @Test
    // Author: alexander-novo
    // Bug - Goldberry doesn't seem to get some of the effects from some of the counters she takes
    public void testM1M1Counters() {
        CounterType counter = CounterType.M1M1;
        String island = "Island";

        addCard(Zone.BATTLEFIELD, playerA, goldberry, 1);
        addCard(Zone.BATTLEFIELD, playerA, island, 1);

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, island, counter, 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ability1, island);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertCounterCount(goldberry, counter, 1);
        assertPowerToughness(playerA, goldberry, 0, 2);
    }
}
