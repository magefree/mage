package org.mage.test.cards.cost.custom;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class SerraAvengerTest extends CardTestPlayerBase {

    /**
     * Try to cast Serra Avenger on 1st, 2nd, 3rd and 4th turns.
     * It should success only on 4th one.
     */
    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Constants.Zone.HAND, playerA, "Serra Avenger", 4);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Serra Avenger");
        castSpell(3, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Serra Avenger");
        castSpell(5, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Serra Avenger");
        castSpell(7, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Serra Avenger");

        setStopAt(7, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Serra Avenger", 1); // only the one that was cast on 4th turn
    }

    /**
     * Tests that we may cast Serra Avenger "earlier" if we use extra turns
     */
    @Test
    public void testWithExtraTurns() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Constants.Zone.HAND, playerA, "Time Warp", 3);
        addCard(Constants.Zone.HAND, playerA, "Serra Avenger", 1);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Time Warp", playerA);
        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Time Warp", playerA);
        castSpell(3, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Time Warp", playerA);
        castSpell(4, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Serra Avenger");

        setStopAt(4, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Time Warp", 3);
        assertPermanentCount(playerA, "Serra Avenger", 1);
    }

}
