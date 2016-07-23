/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.single.emn;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class PermeatingMassTest extends CardTestPlayerBase {
   
    @Test
    public void testWhenDiesInCombatMakesCopyStill() {
                
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant"); // 3/3
        
        // Whenever Permeating Mass deals combat damage to a creature, that creature becomes a copy of Permeating Mass.
        addCard(Zone.BATTLEFIELD, playerB, "Permeating Mass"); // 1/3
        
        attack(1, playerA, "Hill Giant");
        block(1, playerB, "Permeating Mass", "Hill Giant");
        
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
        
        assertLife(playerB, 20);
        assertGraveyardCount(playerB, "Permeating Mass", 1);
        Permanent hilly = getPermanent("Hill Giant", playerA);
        assertPowerToughness(playerA, "Hill Giant", 1, 3);
    }
}
