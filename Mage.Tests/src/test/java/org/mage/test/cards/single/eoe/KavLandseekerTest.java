package org.mage.test.cards.single.eoe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class KavLandseekerTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.k.KavLandseeker Kav Landseeker} {3}{R}
     * Creature — Kavu Soldier
     * Menace (This creature can’t be blocked except by two or more creatures.)
     * When this creature enters, create a Lander token. At the beginning of the end step on your next turn, sacrifice that token. (It’s an artifact with “{2}, {T}, Sacrifice this token: Search your library for a basic land card, put it onto the battlefield tapped, then shuffle.”)
     * 4/3
     */
    private static final String kav = "Kav Landseeker";

    @Test
    public void test_Simple() {
        addCard(Zone.HAND, playerA, kav);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kav);

        checkPermanentCount("T3: playerA has a Lander Token", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, playerA, "Lander Token", 1);

        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Lander Token", 0);
    }

    @Test
    public void test_TimeStop() {
        addCard(Zone.HAND, playerA, kav);
        addCard(Zone.HAND, playerA, "Time Stop"); // end the turn
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kav);

        checkPermanentCount("T3: playerA has a Lander Token", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, playerA, "Lander Token", 1);
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Time Stop");

        setStopAt(6, PhaseStep.PRECOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        // the delayed trigger has been cleaned up since the "next turn" had no end step.
        assertPermanentCount(playerA, "Lander Token", 1);
    }
}
