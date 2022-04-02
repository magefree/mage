package org.mage.test.cards.dynamicvalue;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class SavedDamageValueTest extends CardTestPlayerBase {

    /**
     * Check that the dealt damage is added to life
     *
    */
    @Test
    public void ArmadilloCloakTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Enchant creature
        // Enchanted creature gets +2/+2 and has trample.
        // Whenever enchanted creature deals damage, you gain that much life.
        addCard(Zone.HAND, playerA, "Armadillo Cloak");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Armadillo Cloak", "Silvercoat Lion");

        attack(3, playerA, "Silvercoat Lion");
        
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA,"Armadillo Cloak", 1);        
        assertPowerToughness(playerA,  "Silvercoat Lion", 4, 4);
        
        assertLife(playerA, 24);
        assertLife(playerB, 16);
    }
}
