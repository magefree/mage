
package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Autumn's Veil - Instant {G}
 * Spells you control can't be countered by blue or black spells this turn, and creatures
 * you control can't be the targets of blue or black spells this turn.
 *
 *
 * @author LevelX2
 */

public class AutumnsVeilTest extends CardTestPlayerBase {

    /**
     * Test that a spell can't be countered
     *
     */
    @Test
    public void testCantBeCounteredNormal() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Autumn's Veil", 1);
        addCard(Zone.HAND, playerA, "Runeclaw Bear", 1);

        addCard(Zone.HAND, playerB, "Counterspell");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Autumn's Veil");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Runeclaw Bear");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Counterspell", "Runeclaw Bear", "Runeclaw Bear");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Autumn's Veil", 1);
        assertGraveyardCount(playerB, "Counterspell", 1);
        
        assertPermanentCount(playerA, "Runeclaw Bear", 1);        

    }

}