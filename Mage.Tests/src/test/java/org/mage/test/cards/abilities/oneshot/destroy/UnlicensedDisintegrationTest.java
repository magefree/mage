package org.mage.test.cards.abilities.oneshot.destroy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


/*
Unlicensed Disintigration - Hi! Noticed that everytime that i succesfully cast 
Unlicensed Disintigration with an artifact on the board the opponent wont lose 3 life. 
The creature dies but the last piece of text does not work (teM, 2020-02-24 15:17:36)
*/
public class UnlicensedDisintegrationTest extends CardTestPlayerBase{
    
    /*
    Unlicensed Disintegration {1}{B}{R}
    
    Destroy target creature. If you control an artifact, 
    Unlicensed Disintegration deals 3 damage to that creature's controller.
    
    
    Avacyn, Angel of Hope {5}{W}{W}
    
    Flying, vigilance, indestructible
    Other permanents you control have indestructible.
    */
    
    @Test
    public void testDestroyCreatureLifeLoss(){
        addCard(Zone.HAND, playerA, "Unlicensed Disintegration");
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp",2);
        
        // Need an artifact to trigger the damage
        addCard(Zone.BATTLEFIELD, playerA, "Sol Ring");

        // Play Unlicensed Disintegration, targeting Balduvian Bears
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unlicensed Disintegration", "Balduvian Bears");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertGraveyardCount(playerA, "Unlicensed Disintegration", 1);
        assertLife(playerB, 17);
        assertGraveyardCount(playerB, "Balduvian Bears", 1);
    }
  
    @Test
    public void testDestroyCreatureLifeLossIndestructible(){
        addCard(Zone.HAND, playerA, "Unlicensed Disintegration");
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Avacyn, Angel of Hope");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp",2);
        
        // Need an artifact to trigger the damage
        addCard(Zone.BATTLEFIELD, playerA, "Sol Ring");

        // Play Unlicensed Disintegration, targeting Balduvian Bears
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unlicensed Disintegration", "Balduvian Bears");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertGraveyardCount(playerA, "Unlicensed Disintegration", 1);
        
        assertLife(playerB, 17);
        assertPermanentCount(playerB, "Balduvian Bears", 1);
        assertPermanentCount(playerB, "Avacyn, Angel of Hope", 1);
    }

    @Test
    public void testDestroyCreatureNoLifeLossNoArtifact(){
        addCard(Zone.HAND, playerA, "Unlicensed Disintegration");
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp",2);
        
        // Play Unlicensed Disintegration, targeting Balduvian Bears
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unlicensed Disintegration", "Balduvian Bears");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertGraveyardCount(playerA, "Unlicensed Disintegration", 1);
        assertLife(playerB, 20);
        assertGraveyardCount(playerB, "Balduvian Bears", 1);
    }

    @Test
    public void testDestroyCreatureNoLifeLossNoArtifactIndestructible(){
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp",2);  
        addCard(Zone.HAND, playerA, "Unlicensed Disintegration");
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Avacyn, Angel of Hope");
        
        // Play Unlicensed Disintegration, targeting Balduvian Bears
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unlicensed Disintegration", "Balduvian Bears");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertGraveyardCount(playerA, "Unlicensed Disintegration", 1);
        
        assertLife(playerB, 20);
        assertPermanentCount(playerB, "Balduvian Bears", 1);
        assertPermanentCount(playerB, "Avacyn, Angel of Hope", 1);
    }
}
