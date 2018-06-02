

package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ElvishArchdruidTest extends CardTestPlayerBase {

    /**
     * Elvish Archdruid gives +1/+1 to Nettle Sentinel
     * If Archdruid dies and Nettle Sentinel has got 2 damage
     * Nettle Sentinel must go to graveyard immediately
     */
    @Test
    public void testBoostWithUndying() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Elvish Archdruid", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Nettle Sentinel", 1);
        // Pyroclasm deals 2 damage to each creature.
        addCard(Zone.HAND, playerA, "Pyroclasm");        

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pyroclasm");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN); // has to be the same phase as the cast spell to see if Nettle Sentinel dies in this phase
        execute();

        assertGraveyardCount(playerA, "Pyroclasm", 1);
        assertPermanentCount(playerA, "Elvish Archdruid", 0);
        assertPermanentCount(playerA, "Nettle Sentinel", 0);
    }

}