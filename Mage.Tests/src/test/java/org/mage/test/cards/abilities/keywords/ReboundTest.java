/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeff
 */
public class ReboundTest extends CardTestPlayerBase{
    
    /**
     * Test that the spell with rebound is moved to exile if
     * the spell resolves
     */
    
    @Test
    public void testCastFromHandMovedToExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        
        // Target creature gets +1/+0 until end of turn and is unblockable this turn.
        addCard(Zone.HAND, playerA, "Distortion Strike");
        
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Distortion Strike", "Memnite");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        //check exile and graveyard        
        assertExileCount("Distortion Strike", 1);
        assertGraveyardCount(playerA, 0);
    }
    /**
     * Test that the spell with rebound can be cast again
     * on the beginning of the next upkeep without paying mana costs
     */
    
    @Test
    public void testRecastFromExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        
        // Target creature gets +1/+0 until end of turn and is unblockable this turn.
        addCard(Zone.HAND, playerA, "Distortion Strike");
        
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Distortion Strike", "Memnite");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();
        
        //check exile and graveyard        
        assertPowerToughness(playerA, "Memnite", 2, 1);
        assertExileCount("Distortion Strike", 0);
        assertGraveyardCount(playerA, "Distortion Strike", 1);
    }
    
    /**
     * Check that a countered spell with rebound
     * is not cast again
     */
    
    @Test
    public void testDontRecastAfterCounter() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        
        // Target creature gets +1/+0 until end of turn and is unblockable this turn.
        addCard(Zone.HAND, playerA, "Distortion Strike");       
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell");       

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Distortion Strike", "Memnite");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Distortion Strike");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();
        
        //check exile and graveyard        
        assertGraveyardCount(playerB, "Counterspell", 1);
        assertGraveyardCount(playerA, "Distortion Strike", 1);

        assertPowerToughness(playerA, "Memnite", 1, 1);
        assertExileCount("Distortion Strike", 0);
    }    
    
    
    /**
     * Check that a fizzled spell with rebound
     * is not cast again on the next controllers upkeep
     */
    
    @Test
    public void testDontRecastAfterFizzling() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        
        // Target creature gets +1/+0 until end of turn and is unblockable this turn.
        addCard(Zone.HAND, playerA, "Distortion Strike");       
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);       

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Distortion Strike", "Memnite");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Memnite","Distortion Strike");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        //check exile and graveyard        
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Distortion Strike", 1);
        assertGraveyardCount(playerA, "Memnite", 1);
    }      
}
