
package org.mage.test.cards.abilities.oneshot.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DeflectingPalmTest extends CardTestPlayerBase {

    /**
     * Test that prevented damage will be created with the correct source and 
     * will trigger the ability of Satyr Firedance
     * https://github.com/magefree/mage/issues/804
     */
    
    @Test
    public void testDamageInPlayer() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        // The next time a source of your choice would deal damage to you this turn, prevent that damage. 
        // If damage is prevented this way, Deflecting Palm deals that much damage to that source's controller.
        addCard(Zone.HAND, playerA, "Deflecting Palm");
        // Whenever an instant or sorcery spell you control deals damage to an opponent, Satyr Firedancer deals 
        // that much damage to target creature that player controls.
        addCard(Zone.BATTLEFIELD, playerA, "Satyr Firedancer");
        
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB ,"Lightning Bolt", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA ,"Deflecting Palm", null, "Lightning Bolt");
        setChoice(playerA, "Lightning Bolt");
        addTarget(playerA, "Silvercoat Lion"); // target for Satyr Firedancer
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Deflecting Palm", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        
        
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        
        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }
}