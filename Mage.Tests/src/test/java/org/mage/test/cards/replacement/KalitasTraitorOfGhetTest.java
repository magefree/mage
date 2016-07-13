/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class KalitasTraitorOfGhetTest extends CardTestPlayerBase {
    
    /*
    * Reported bug: Damnation with Kalitas, Traitor of Ghet on my side and 3 opponent creatures, it only exiled 1 creature giving me only 1 zombie instead of 3.
    */
    @Test
    public void testDamnation() {
        
        /*
        Kalitas, Traitor of Ghet {2}{B}{B} 3/4 lifelink - Legendary Vampire
        If a nontoken creature an opponent controls would die, instead exile that card and put a 2/2 black Zombie creature token onto the battlefield.
        */
        addCard(Zone.BATTLEFIELD, playerA, "Kalitas, Traitor of Ghet", 1);
        /*
        Damnation {2}{B}{B} - Sorcery
         Destroy all creatures. They can't be regenerated.
        */
        addCard(Zone.HAND, playerA, "Damnation", 1);
        
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        
        addCard(Zone.BATTLEFIELD, playerB, "Bronze Sable", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Wall of Roots", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Sigiled Starfish", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Damnation");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerA, "Kalitas, Traitor of Ghet", 1);
        assertGraveyardCount(playerA, "Damnation", 1);
        assertExileCount("Bronze Sable", 1);
        assertExileCount("Wall of Roots", 1);
        assertExileCount("Sigiled Starfish", 1);              
        assertGraveyardCount(playerB, 0); // all 3 creatures of playerB should be exiled not in graveyard
        assertExileCount("Kalitas, Traitor of Ghet", 0); // player controlled, not opponent so not exiled  
        assertPermanentCount(playerA, "Zombie", 3); // 3 tokens generated from exiling 3 opponent's creatures
    }
}
