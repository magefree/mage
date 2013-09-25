/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.conditional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class MirrorSigilSergeantTest extends CardTestPlayerBase {
    
    @Test
    public void testTokenEffect() {
        
        addCard(Zone.BATTLEFIELD, playerA, "Mirror-Sigil Sergeant");
        addCard(Zone.BATTLEFIELD, playerA, "Merfolk Spy");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        
        addCard(Zone.LIBRARY, playerB, "Plains");
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
        
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Mirror-Sigil Sergeant", 2);

    }
    
    @Test
    public void testTokenEffect2() {
        
        addCard(Zone.BATTLEFIELD, playerA, "Mirror-Sigil Sergeant");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        
        addCard(Zone.LIBRARY, playerB, "Plains");
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
        
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Mirror-Sigil Sergeant", 1);

    }
    
}
