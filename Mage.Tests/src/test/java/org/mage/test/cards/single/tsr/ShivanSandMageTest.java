package org.mage.test.cards.single.tsr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class ShivanSandMageTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_ModeOne_Manual() {
        // When Shivan Sand-Mage enters the battlefield, choose one —
        // • Remove two time counters from target permanent or suspended card.
        // • Put two time counters on target permanent with a time counter on it or suspended card.
        addCard(Zone.HAND, playerA, "Shivan Sand-Mage", 1); // {2}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shivan Sand-Mage");
        setModeChoice(playerA, "1");
        addTarget(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Shivan Sand-Mage", 1);
    }

    @Test
    public void test_ModeOne_AI() {
        // When Shivan Sand-Mage enters the battlefield, choose one —
        // • Remove two time counters from target permanent or suspended card.
        // • Put two time counters on target permanent with a time counter on it or suspended card.
        addCard(Zone.HAND, playerA, "Shivan Sand-Mage", 1); // {2}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);

        // AI must play card
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Shivan Sand-Mage", 1);
    }

    @Test
    public void test_ModeTwo_Manual() {
        // When Shivan Sand-Mage enters the battlefield, choose one —
        // • Remove two time counters from target permanent or suspended card.
        // • Put two time counters on target permanent with a time counter on it or suspended card.
        addCard(Zone.HAND, playerA, "Shivan Sand-Mage", 1); // {2}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        //
        // Suspend 3—{1}{W}
        addCard(Zone.HAND, playerA, "Duskrider Peregrine", 1); // {5}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // prepare suspended card
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 2);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Suspend 3");
        checkExileCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Duskrider Peregrine", 1);
        checkCardCounters("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Duskrider Peregrine", CounterType.TIME, 3);

        // add +2 time counters
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shivan Sand-Mage");
        setModeChoice(playerA, "2");
        addTarget(playerA, "Duskrider Peregrine");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkCardCounters("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Duskrider Peregrine", CounterType.TIME, 3 + 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Shivan Sand-Mage", 1);
    }

    @Test
    public void test_ModeTwo_AI() {
        // When Shivan Sand-Mage enters the battlefield, choose one —
        // • Remove two time counters from target permanent or suspended card.
        // • Put two time counters on target permanent with a time counter on it or suspended card.
        addCard(Zone.HAND, playerA, "Shivan Sand-Mage", 1); // {2}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        //
        // Suspend 3—{1}{W}
        addCard(Zone.HAND, playerA, "Duskrider Peregrine", 1); // {5}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // prepare suspended card
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 2);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Suspend 3");
        checkExileCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Duskrider Peregrine", 1);
        checkCardCounters("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Duskrider Peregrine", CounterType.TIME, 3);

        // must add +2 time counters
        // due to AI limitation with ETB's modes - it checks only target support
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shivan Sand-Mage");
        setModeChoice(playerA, "2");
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkCardCounters("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Duskrider Peregrine", CounterType.TIME, 3 + 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Shivan Sand-Mage", 1);
    }
}
