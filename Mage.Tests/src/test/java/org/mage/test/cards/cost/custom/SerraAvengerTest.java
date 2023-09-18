package org.mage.test.cards.cost.custom;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class SerraAvengerTest extends CardTestPlayerBase {

    /**
     * Serra Avenger can't be cast on your 1st, 2nd, and 3rd turns.
     * It can only be cast on the 4th turn.
     */
    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Serra Avenger", 1);

        // Can't cost on the 1st, 2nd, and 3rd turns
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Serra Avenger", false);
        checkPlayableAbility("before", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Serra Avenger", false);
        checkPlayableAbility("before", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Serra Avenger", false);

        // Can only cast on the 4th turn.
        castSpell(7, PhaseStep.PRECOMBAT_MAIN, playerA, "Serra Avenger");

        setStopAt(7, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Serra Avenger", 1);
    }

    /**
     * Tests that we may cast Serra Avenger "earlier" if we use extra turns
     */
    @Test
    public void testWithExtraTurns() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Time Warp", 3);
        addCard(Zone.HAND, playerA, "Serra Avenger", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Time Warp", playerA);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Time Warp", playerA);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Time Warp", playerA);
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerA, "Serra Avenger");

        setStopAt(4, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Time Warp", 3);
        assertPermanentCount(playerA, "Serra Avenger", 1);
    }

}
