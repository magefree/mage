/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class BecomesBlockedTest extends CardTestPlayerBase {
    
    /**
     * Reported bug: 
     * There's something wrong with how Rabid Elephant is getting his +2/+2 bonus, 
     * it doesn't last until end of turn, but seems to be removed right after the blockers step.
     */
    @Test
    public void testRabidElephant() {
        
        // {4}{G} 
        // Whenever Rabid Elephant becomes blocked, it gets +2/+2 until end of turn for each creature blocking it.
        addCard(Zone.BATTLEFIELD, playerA, "Rabid Elephant", 1); // 3/4
        
        addCard(Zone.BATTLEFIELD, playerB, "Savannah Lions", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant", 1); // 3/3
        
        attack(1, playerA, "Rabid Elephant");
        block(1, playerB, "Savannah Lions", "Rabid Elephant");
        block(1, playerB, "Hill Giant", "Rabid Elephant");                

        // test passes if PhaseStep ends at DECLARE_BLOCKERS
        //setStopAt(1, PhaseStep.DECLARE_BLOCKERS);
        setStopAt(1, PhaseStep.COMBAT_DAMAGE);
        execute();
        
        // blocked by 2 creatures, so gets +2/+2 twice, making it 7/8
        assertPowerToughness(playerA, "Rabid Elephant", 7, 8);
    }
}
