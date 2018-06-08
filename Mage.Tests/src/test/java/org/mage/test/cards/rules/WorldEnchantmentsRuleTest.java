
package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class WorldEnchantmentsRuleTest extends CardTestPlayerBase {

    /**
     * 704.5m If two or more permanents have the supertype world, all except the one that has had 
     * the world supertype for the shortest amount of time are put into their owners' graveyards. 
     * In the event of a tie for the shortest amount of time, all are put into their owners' graveyards. 
     * This is called the “world rule.
     * 
     */
    @Test
    public void TestTwoWorldEnchantsments() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, "Nether Void", 1);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);
        
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 7);
        addCard(Zone.HAND, playerB, "Nether Void", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nether Void");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion"); // just needed to get different craete time to second Nether Void
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Nether Void");
                
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Nether Void", 0);
        assertPermanentCount(playerB, "Nether Void", 1);
    }
        
}