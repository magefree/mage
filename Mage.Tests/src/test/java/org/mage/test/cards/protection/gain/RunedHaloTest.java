
package org.mage.test.cards.protection.gain;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class RunedHaloTest extends CardTestPlayerBase {

    // As Runed Halo enters the battlefield, name a card.
    // You have protection from the chosen name.
    
    @Test
    public void testGainProtectionFromAttackingCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Runed Halo");
        
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Runed Halo");
        setChoice(playerA, "Silvercoat Lion");
        
        attack(2, playerB, "Silvercoat Lion");
        
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20); // because he has protecion from Silvercoat Lion
    }
 
}

