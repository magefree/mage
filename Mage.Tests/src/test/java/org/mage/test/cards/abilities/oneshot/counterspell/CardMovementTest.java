
package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Tests that counterspells also work if card movement effects are active
 * 
 * @author LevelX2
 */

public class CardMovementTest extends CardTestPlayerBase {

    /**
     * Test that counterspell with rest in Peace in play works
     *
     */
    @Test
    public void testCounterWithRestInPeace() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Rest in Peace", 2);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        addCard(Zone.HAND, playerB, "Counterspell");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Silvercoat Lion", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertGraveyardCount(playerB, "Counterspell", 0);
        assertExileCount("Silvercoat Lion", 1);
        assertExileCount("Counterspell", 1);
        

    }

}