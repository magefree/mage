
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class DelayedTriggerTest extends CardTestPlayerBase {

    /**
     * Activated Sunforger's unequip ability and cast Otherworldly Journey for free, targeting Cathodion. 
     * When Cathodion returned to the battlefield, it did not get a +1/+1 counter. *
     * 
     */
    @Test
    public void testOtherworldlyJourney1() {
        // Exile target creature. At the beginning of the next end step, return that card to the battlefield under its owner's control with a +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Otherworldly Journey");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Otherworldly Journey", "Silvercoat Lion");

        setStopAt(2, PhaseStep.DRAW);
        execute();

        assertGraveyardCount(playerA, "Otherworldly Journey", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 3, 3);
        
    }
    
    @Test
    public void testOtherworldlyJourney2() {
        // Exile target creature. At the beginning of the next end step, return that card to the battlefield under its owner's control with a +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Otherworldly Journey");
        addCard(Zone.HAND, playerA, "Cloudshift");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Otherworldly Journey", "Silvercoat Lion");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Silvercoat Lion");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Otherworldly Journey", 1);
        assertGraveyardCount(playerA, "Cloudshift", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2); // one one use of +1/+1
        
    }
}
