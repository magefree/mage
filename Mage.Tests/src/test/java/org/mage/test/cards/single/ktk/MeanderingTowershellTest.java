package org.mage.test.cards.single.ktk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class MeanderingTowershellTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.m.MeanderingTowershell Meandering Towershell} {3}{G}{G}
     * Creature — Turtle
     * Islandwalk (This creature can’t be blocked as long as defending player controls an Island.)
     * Whenever this creature attacks, exile it. Return it to the battlefield under your control tapped and attacking at the beginning of the declare attackers step on your next turn.
     * 5/9
     */
    private static final String towershell = "Meandering Towershell";

    @Test
    public void test_Simple() {
        addCard(Zone.BATTLEFIELD, playerA, towershell);

        attack(1, playerA, towershell, playerB);

        checkPermanentCount("T3 First Main: no Towershell", 3, PhaseStep.PRECOMBAT_MAIN, playerA, playerA, "Meandering Powershell", 0);
        checkLife("T3 First Main: playerB at 20 life", 3, PhaseStep.PRECOMBAT_MAIN, playerB, 20);

        // Meandering Towershell comes back attacking.

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, towershell, 1);
        assertLife(playerB, 20 - 5);
    }

    @Test
    public void test_TimeStop() {
        addCard(Zone.BATTLEFIELD, playerA, towershell);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, "Time Stop");

        attack(1, playerA, towershell, playerB);

        checkPermanentCount("T3 First Main: no Towershell", 3, PhaseStep.PRECOMBAT_MAIN, playerA, playerA, "Meandering Powershell", 0);
        checkLife("T3 First Main: playerB at 20 life", 3, PhaseStep.PRECOMBAT_MAIN, playerB, 20);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Time Stop");

        // Meandering Towershell never comes back on future turns.

        setStrictChooseMode(true);
        setStopAt(6, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerA, towershell, 1);
        assertPermanentCount(playerA, towershell, 0);
        assertLife(playerB, 20);
    }
}
