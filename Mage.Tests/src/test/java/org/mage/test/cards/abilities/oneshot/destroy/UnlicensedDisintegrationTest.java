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
    
    @Test
    public void testDestroyCreatureLifeLoss(){
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, "Unlicensed Disintegration");
        addCard(Zone.BATTLEFIELD, playerB, "Volrath, the Shapestealer");
        
        // Need an artifact to trigger the damage
        addCard(Zone.BATTLEFIELD, playerA, "Sol Ring");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unlicensed Disintegration", "Volrath, the Shapestealer");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();
        
        assertLife(playerA, 20);
        assertGraveyardCount(playerA, "Unlicensed Disintegration", 1);
        assertLife(playerB, 17);
        assertGraveyardCount(playerB, "Volrath, the Shapestealer", 1);
    }
  
    @Test
    public void testDestroyCreatureLifeLossIndestructible(){
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, "Unlicensed Disintegration");
        addCard(Zone.BATTLEFIELD, playerB, "Volrath, the Shapestealer");
        addCard(Zone.BATTLEFIELD, playerB, "Avacyn, Angel of Hope");
        
        // Need an artifact to trigger the damage
        addCard(Zone.BATTLEFIELD, playerA, "Sol Ring");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unlicensed Disintegration", "Volrath, the Shapestealer");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();
        
        assertLife(playerA, 20);
        assertGraveyardCount(playerA, "Unlicensed Disintegration", 1);
        
        assertLife(playerB, 17);
        assertPermanentCount(playerB, "Volrath, the Shapestealer", 1);
        assertPermanentCount(playerB, "Avacyn, Angel of Hope", 1);
    }

    @Test
    public void testDestroyCreatureNoLifeLossNoArtifact(){
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, "Unlicensed Disintegration");
        addCard(Zone.BATTLEFIELD, playerB, "Volrath, the Shapestealer");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unlicensed Disintegration", "Volrath, the Shapestealer");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();
        
        assertLife(playerA, 20);
        assertGraveyardCount(playerA, "Unlicensed Disintegration", 1);
        assertLife(playerB, 20);
        assertGraveyardCount(playerB, "Volrath, the Shapestealer", 1);
    }

    @Test
    public void testDestroyCreatureNoLifeLossNoArtifactIndestructible(){
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, "Unlicensed Disintegration");
        addCard(Zone.BATTLEFIELD, playerB, "Volrath, the Shapestealer");
        addCard(Zone.BATTLEFIELD, playerB, "Avacyn, Angel of Hope");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unlicensed Disintegration", "Volrath, the Shapestealer");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();
        
        assertLife(playerA, 20);
        assertGraveyardCount(playerA, "Unlicensed Disintegration", 1);
        
        assertLife(playerB, 20);
        assertPermanentCount(playerB, "Volrath, the Shapestealer", 1);
        assertPermanentCount(playerB, "Avacyn, Angel of Hope", 1);
    }
}
