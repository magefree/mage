package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ExpendTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.Six Wandertale Mentor} {R}{G}
     * Creature — Raccoon Bard
     * Whenever you expend 4, put a +1/+1 counter on Wandertale Mentor. (You expend 4 as you spend your fourth total mana to cast spells during a turn.)
     * {T}: Add {R} or {G}.
     * 2/2
     */
    private static final String mentor = "Wandertale Mentor";

    @Test
    public void test_OnPayingSingleSpell_4Mana_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Axebane Beast"); // {3}{G} vanilla

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Axebane Beast");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, mentor, CounterType.P1P1, 1);
    }

    @Test
    public void test_OnPayingSingleSpell_5Mana_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Blanchwood Treefolk"); // {4}{G} vanilla

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blanchwood Treefolk");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, mentor, CounterType.P1P1, 1);
    }

    @Test
    public void test_OnPayingSingleSpell_3Mana_NoTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Alpine Grizzly"); // {2}{G} vanilla

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alpine Grizzly");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, mentor, CounterType.P1P1, 0);
    }

    @Test
    public void test_CaresAboutYouSpendingMana() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, "Cloaked Siren"); // {3}{U} flash

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cloaked Siren");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, mentor, CounterType.P1P1, 0);
    }

    @Test
    public void test_RememberManaSpentBefore_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, mentor);
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 4);
        addCard(Zone.HAND, playerA, "Grizzly Bears"); // {1}{G} vanilla

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mentor, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, mentor, CounterType.P1P1, 1);
    }

    @Test
    public void test_RememberManaSpentBefore_NoTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, mentor);
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 10);
        addCard(Zone.HAND, playerA, "Axebane Beast", 2); // {3}{G} vanilla

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Axebane Beast", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mentor, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Axebane Beast");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, mentor, CounterType.P1P1, 0);
    }

    @Test
    public void test_ActivatedAbility_NoTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Devkarin Dissident"); // {4}{G}: Devkarin Dissident gets +2/+2 until end of turn.

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{G}: ");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, mentor, CounterType.P1P1, 0);
    }

    @Test
    public void test_JustTappingForMana_NoTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 4);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, mentor, CounterType.P1P1, 0);
    }

    @Test
    public void test_CastAdventure_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        addCard(Zone.HAND, playerA, "Flaxen Intruder"); // Adventure for {5}{G}{G}: Create three 2/2 green Bear creature tokens

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Welcome Home");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, mentor, CounterType.P1P1, 1);
        assertPermanentCount(playerA, "Bear Token", 3);
    }

    @Test
    public void test_Kick_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.HAND, playerA, "Gnarlid Colony"); // {1}{G} Kicker {2}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gnarlid Colony");
        setChoice(playerA, true); // yes for Kicker {2}{G}

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, mentor, CounterType.P1P1, 1);
        assertCounterCount(playerA, "Gnarlid Colony", CounterType.P1P1, 2);
    }

    @Test
    public void test_Cycling_NoTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "Stir the Sands"); // Cycling {3}{B}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling {3}{B}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, mentor, CounterType.P1P1, 0);
        assertPermanentCount(playerA, "Zombie Token", 1);
    }

    @Test
    public void test_XSpell_4_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Endless One"); // {X}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Endless One");
        setChoice(playerA, "X=4");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, mentor, CounterType.P1P1, 1);
        assertCounterCount(playerA, "Endless One", CounterType.P1P1, 4);
    }

    @Test
    public void test_XSpell_3_NoTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Endless One"); // {X}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Endless One");
        setChoice(playerA, "X=3");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, mentor, CounterType.P1P1, 0);
        assertCounterCount(playerA, "Endless One", CounterType.P1P1, 3);
    }

    @Test
    public void test_OneTriggerEachTurn() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Axebane Beast", 2); // {3}{G} vanilla

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Axebane Beast");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Axebane Beast");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, mentor, CounterType.P1P1, 2);
    }
}
