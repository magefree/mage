
package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class MightOfOldKrosaTest extends CardTestPlayerBase {

    @Test
    public void testTwiceMightOfOldKrosaBeginCombat() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Might of Old Krosa", 2);
        
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Might of Old Krosa", "Silvercoat Lion");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Might of Old Krosa", "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_COMBAT); 
        execute();

        assertGraveyardCount(playerA, "Might of Old Krosa", 2);
        
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA,  "Silvercoat Lion", 6, 6);
    }
    /**
     * Threw two Might of old Krosa's onto a creature, but only one had any effect.
     */
    
    @Test
    public void testTwiceMightOfOldKrosa() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Might of Old Krosa", 2);
        
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Might of Old Krosa", "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Might of Old Krosa", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT); 
        execute();

        assertGraveyardCount(playerA, "Might of Old Krosa", 2);
        
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA,  "Silvercoat Lion", 10, 10);
    }

}
