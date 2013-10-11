/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jeff
 */
public class AvenTrailblazerTest extends CardTestPlayerBase {
    
    @Test
    public void testAvenTrailblazerEffect() {
        
        addCard(Zone.HAND, playerA, "Aven Trailblazer");
        addCard(Zone.BATTLEFIELD, playerA, "City of Brass", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 10);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aven Trailblazer");
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Aven Trailblazer", 2, 2);

    }
}
