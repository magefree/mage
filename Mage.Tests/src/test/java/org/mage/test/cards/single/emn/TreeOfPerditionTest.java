/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.single.emn;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class TreeOfPerditionTest extends CardTestPlayerBase {
    
    /*
    Reported bug: Tree of Perdition retains toughness change after being bounced and replayed
    Exchanged toughness with opponent's life to become an 0/20, got bounced, 
    and when replayed it was still 0/20. It should be a new object and enter as an 0/13.
    */
    @Test
    public void testTreeOfPerditionBouncedAndReplayed() {
        
        /*
         Tree of Perdition - {3}{B} - Creature Plant - 0/13
        Defender
        Tap: Exchange target opponent's life total with Tree of Perdition's toughness.
        */
        addCard(Zone.BATTLEFIELD, playerA, "Tree of Perdition");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerB, "Unsummon", 1); // {U} instant - return target creature to owner hand
        addCard(Zone.BATTLEFIELD,playerB,"Island",1);
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}:");
        addTarget(playerA, playerB);
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Unsummon");
        addTarget(playerA, "Tree of Perdition");
        
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Tree of Perdition");
        
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 13);
        assertPowerToughness(playerA, "Tree of Perdition", 0, 13);
    }
    
   /*
    Reported bug: Tree of Perdition is gaining both power and toughness equal to opponent's life total
    instead of just toughness equal to it.
    */
    @Test
    public void testTreeOfPerditionOnlyGainsToughnessEqualToLife() {
        
        /*
         Tree of Perdition - {3}{B} - Creature Plant - 0/13
        Defender
        Tap: Exchange target opponent's life total with Tree of Perdition's toughness.
        */
        addCard(Zone.BATTLEFIELD, playerA, "Tree of Perdition");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}:");
        addTarget(playerA, playerB);
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 13);
        assertPowerToughness(playerA, "Tree of Perdition", 0, 20);
    }
}
