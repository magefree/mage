package org.mage.test.cards.single.frc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBaseWithRangeAll;

/**
 * @author JayDi85
 */
public class JaceMultiverseArchitectMultiplayerTest extends CardTestMultiPlayerBaseWithRangeAll {

    /**
     * Jace, Multiverse Architect {1}{W}{U}{B}{R}
     * Legendary Planeswalker — Jace, loyalty 4
     * At the beginning of combat on each opponent's turn, they may pay {2}. If they don't, creatures they control
     * can't attack Jaces you control this turn.
     */
    private static final String jace = "Jace, Multiverse Architect";

    @Test
    public void test_Combat_NotPaid_CanAttackJacesOfOtherPlayers() {
        // turn order: A -> D -> C -> B
        addCard(Zone.BATTLEFIELD, playerA, jace);
        addCard(Zone.BATTLEFIELD, playerC, "Jace Beleren"); // loyalty 3
        addCard(Zone.BATTLEFIELD, playerD, "Grizzly Bears"); // 2/2

        setChoice(playerD, false); // turn 2: don't pay {2}
        attack(2, playerD, "Grizzly Bears", "Jace Beleren");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerC, "Jace Beleren", CounterType.LOYALTY, 3 - 2);
    }
}
