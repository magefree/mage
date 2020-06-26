
package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 *
 * @author LevelX2
 */

public class WorldEnchantmentsRuleTest extends CardTestMultiPlayerBase {

    /**
     * 704.5m If two or more permanents have the supertype world, all except the one that has had 
     * the world supertype for the shortest amount of time are put into their owners' graveyards. 
     * In the event of a tie for the shortest amount of time, all are put into their owners' graveyards. 
     * This is called the “world rule.
     * 
     */
    @Test
    public void TestTwoWorldEnchantments() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, "Nether Void", 1);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);
        
        addCard(Zone.BATTLEFIELD, playerD, "Swamp", 7);
        addCard(Zone.HAND, playerD, "Nether Void", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nether Void");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion"); // just needed to get different craete time to second Nether Void
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Nether Void");
                
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Nether Void", 0);
        assertPermanentCount(playerD, "Nether Void", 1);
    }

    // 801.12 The "world rule" applies to a permanent only if other world permanents are within its controller's range of influence.
    @Test
    public void TestTwoWorldEnchantmentsNotInRangeOfInfluence() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, "Nether Void", 1);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerC, "Swamp", 7);
        addCard(Zone.HAND, playerC, "Nether Void", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nether Void");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion"); // just needed to get different craete time to second Nether Void
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerC, "Nether Void");

        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Nether Void", 1);
        assertPermanentCount(playerC, "Nether Void", 1);
    }
}