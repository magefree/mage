package org.mage.test.cards.single.snc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class VampireScrivenerTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.v.VampireScrivener Vampire Scrivener} {4}{B}
     * Creature — Vampire Warlock
     * Flying
     * Whenever you gain life during your turn, put a +1/+1 counter on Vampire Scrivener.
     * Whenever you lose life during your turn, put a +1/+1 counter on Vampire Scrivener.
     * 2/2
     */
    private static final String scrivener = "Vampire Scrivener";

    @Test
    public void test_LoseLife_Twice() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, scrivener, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Battlefield Forge"); // painland
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}"); // cause 1 trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA); // cause 1 trigger

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 3 - 1);
        assertCounterCount(playerA, scrivener, CounterType.P1P1, 2);
    }

    @Test
    public void test_RakdosCharm() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, scrivener, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Kobolds of Kher Keep", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 2);
        addCard(Zone.HAND, playerA, "Rakdos Charm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rakdos Charm");
        setModeChoice(playerA, "3"); // Choose third mode

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 4);
        assertCounterCount(playerA, scrivener, CounterType.P1P1, 1);
    }

    @Test
    public void test_RakdosCharm_NotYourTurn() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, scrivener, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Kobolds of Kher Keep", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 2);
        addCard(Zone.HAND, playerA, "Rakdos Charm");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Rakdos Charm");
        setModeChoice(playerA, "3"); // Choose third mode

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 4);
        assertCounterCount(playerA, scrivener, CounterType.P1P1, 0); // No trigger, as not your turn.
    }
}
