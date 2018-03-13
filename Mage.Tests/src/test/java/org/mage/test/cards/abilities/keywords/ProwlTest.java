/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class ProwlTest extends CardTestPlayerBase {
    
    @Ignore // have not figured out how to have the test API cast a card using Prowl yet
    @Test
    public void testBasicProwlCasting() {            
        // Auntie's Snitch {2}{B} Creature — Goblin Rogue (3/1) 
        // Auntie's Snitch can't block.
        // Prowl {1}{B} (You may cast this for its prowl cost if you dealt combat damage to a player this turn with a Goblin or Rogue.)
        // Whenever a Goblin or Rogue you control deals combat damage to a player, if Auntie's Snitch is in your graveyard, you may return Auntie's Snitch to your hand.
        addCard(Zone.HAND, playerA, "Auntie's Snitch");
        
        // {1}{R} Creature — Goblin Warrior 1/1
        // Red creatures you control have first strike.
        addCard(Zone.BATTLEFIELD, playerA, "Bloodmark Mentor");        
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        
        attack(1, playerA, "Bloodmark Mentor");
        
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Auntie's Snitch using prowl");
        setChoice(playerA, "Yes"); // choosing to pay prowl cost
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        assertLife(playerB, 19);
        assertPermanentCount(playerA, "Bloodmark Mentor", 1);
        assertPermanentCount(playerA, "Auntie's Snitch", 1);
    }
    
    /*
     * Reported bug: Prowl is not taking into consideration other cost reducing effects. For instance Goblin Warchief
     * does not reduce the Prowl cost of other Goblin cards with Prowl ability.
    */
    @Ignore  // have not figured out how to have the test API cast a card using Prowl yet
    @Test
    public void testProwlWithCostDiscount() {
           
        // Auntie's Snitch {2}{B} Creature — Goblin Rogue (3/1) 
        // Auntie's Snitch can't block.
        // Prowl {1}{B} (You may cast this for its prowl cost if you dealt combat damage to a player this turn with a Goblin or Rogue.)
        // Whenever a Goblin or Rogue you control deals combat damage to a player, if Auntie's Snitch is in your graveyard, you may return Auntie's Snitch to your hand.
        addCard(Zone.HAND, playerA, "Auntie's Snitch");        
        
        // Goblin Warchief {1}{R}{R} Creature — Goblin Warrior (2/2)
        // Goblin spells you cast cost 1 less to cast.
        // Goblin creatures you control have haste.
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Warchief");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
                
        attack(1, playerA, "Goblin Warchief");
        
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Auntie's Snitch using prowl"); // should only cost {B} with Warchief discount
        setChoice(playerA, "Yes"); // choosing to pay prowl cost
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        assertLife(playerB, 18);
        assertPermanentCount(playerA, "Goblin Warchief", 1);
        assertPermanentCount(playerA, "Auntie's Snitch", 1);
    }
}
