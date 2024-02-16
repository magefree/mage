package org.mage.test.cards.modal;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class OnlyOnceModeTest extends CardTestPlayerBase {

    @Test
    public void test_OncePerGame() {
        // {2}, {T}: Choose one that hasn't been chosen
        // Three Bowls of Porridge deals 2 damage to target creature.
        // Tap target creature.
        // Sacrifice Three Bowls of Porridge. You gain 3 life.
        addCard(Zone.BATTLEFIELD, playerA, "Three Bowls of Porridge");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Spectral Bears", 1); // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // each mode usage must be removed from a list, so all mode choices must be 1

        // turn 1 - first mode available
        // choose 1: damage 2 to target
        checkDamage("before mode 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spectral Bears", 0);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}");
        setModeChoice(playerA, "1");
        addTarget(playerA, "Spectral Bears"); // to damage
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkDamage("before mode 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spectral Bears", 2);

        // turn 3 - first mode doesn't restore, so mode 2 will be used
        // choose 2: Tap target creature
        checkPermanentTapped("before mode 2", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Spectral Bears", false, 1);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}");
        setModeChoice(playerA, "1");
        addTarget(playerA, "Spectral Bears"); // to tap
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentTapped("after mode 2", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Spectral Bears", true, 1);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_OncePerTurn() {
        int triggerCalls = 5;

        // Whenever another creature enters the battlefield under your control, choose one that hasn't been chosen this turn
        // Add {G}{G}{G}.
        // Put a +1/+1 counter on each creature you control.
        // Scry 2, then draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Galadriel, Light of Valinor");
        //
        addCard(Zone.HAND, playerA, "Grizzly Bears", triggerCalls); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2 * triggerCalls);
        //
        addCard(Zone.LIBRARY, playerA, "Mountain", 10);

        // each mode usage must be removed from a list, so all mode choices must be 1

        // do trigger 1: Add {G}{G}{G}
        checkManaPool("before mode 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "G", 0);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        setModeChoice(playerA, "1");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkManaPool("after mode 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "G", 3);

        // do trigger 2: Put a +1/+1 counter
        checkPermanentCounters("before mode 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", CounterType.P1P1, 0);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        setModeChoice(playerA, "1");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCounters("after mode 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", CounterType.P1P1, 1);

        // do trigger 3: Scry 2, then draw a card
        checkHandCardCount("before mode 3", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain", 0);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        setModeChoice(playerA, "1");
        addTarget(playerA, "Mountain"); // scry 2, one to bottom, one to top
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkHandCardCount("after mode 3", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain", 1);

        // do trigger 4: nothing
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        // next turn triggers works again
        // do trigger 5: Add {G}{G}{G}
        checkManaPool("before mode 1 on turn 3", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "G", 0);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        setModeChoice(playerA, "1");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        checkManaPool("after mode 1 on turn 3", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "G", 3);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", triggerCalls);
    }
}
