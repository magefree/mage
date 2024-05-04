package org.mage.test.cards.single.gtc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * @author Susucr
 */
public class HellkiteTyranTest extends CardTestCommander4Players {

    /**
     * {@link mage.cards.h.HellkiteTyrant Hellkite Tyrant} {4}{R}{R}
     * Creature — Dragon
     * Flying, trample
     * Whenever Hellkite Tyrant deals combat damage to a player, gain control of all artifacts that player controls.
     * At the beginning of your upkeep, if you control twenty or more artifacts, you win the game.
     * 6/5
     */
    private static final String tyrant = "Hellkite Tyrant";

    @Test
    public void test_BothTriggers() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, tyrant, 1);

        // Will gain controll of all of those
        addCard(Zone.BATTLEFIELD, playerB, "Mox Sapphire", 10);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 10);

        // Will not gain control of all of those:
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard", 2);
        addCard(Zone.BATTLEFIELD, playerC, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerD, "Mox Ruby", 2);
        addCard(Zone.BATTLEFIELD, playerD, "Elite Vanguard", 2);

        checkPermanentCount("A: 1 Hellikite Tyran", 5, PhaseStep.UPKEEP, playerA, "Hellkite Tyrant", 1);
        checkPermanentCount("A: 10 Mox Sapphire", 5, PhaseStep.UPKEEP, playerA, "Mox Sapphire", 10);
        checkPermanentCount("A: 10 Memnite", 5, PhaseStep.UPKEEP, playerA, "Memnite", 10);
        checkPermanentCount("B: 0 Mox Sapphire", 5, PhaseStep.UPKEEP, playerB, "Mox Sapphire", 0);
        checkPermanentCount("B: 0 Memnite", 5, PhaseStep.UPKEEP, playerB, "Memnite", 0);
        checkPermanentCount("B: 2 Plains", 5, PhaseStep.UPKEEP, playerB, "Plains", 2);
        checkPermanentCount("B: 2 Elite Vanguard", 5, PhaseStep.UPKEEP, playerB, "Elite Vanguard", 2);
        checkPermanentCount("C: 2 Plains", 5, PhaseStep.UPKEEP, playerC, "Plains", 2);
        checkPermanentCount("D: 2 Mox Ruby", 5, PhaseStep.UPKEEP, playerD, "Mox Ruby", 2);
        checkPermanentCount("D: 2 Mox Ruby", 5, PhaseStep.UPKEEP, playerD, "Elite Vanguard", 2);

        attack(1, playerA, tyrant, playerB);

        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertWonTheGame(playerA);
    }
}
