package org.mage.test.cards.restriction;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class CantUseActivatedAbilitiesTest extends CardTestPlayerBase {

    /**
     * I can activate artifacts despite my opponent having Collector Ouphe or 
     * Karn, the Great Creator in play. The artifact says it can't be activated, but that's a lie.
     */
    @Test
    public void testCantActivateManaAbility() {
        // Activated abilities of artifacts can't be activated.        
        addCard(Zone.HAND, playerA, "Collector Ouphe"); // Creature  {1}{G}  2/2
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);        
        // {T}: Add {C}{C}
        addCard(Zone.BATTLEFIELD, playerB, "Sol Ring"); // Artifact
             
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Collector Ouphe");
                  
        setStopAt(1, PhaseStep.END_COMBAT);                
        setStrictChooseMode(true);
        execute();

        // Sol Ring can't produce mana
        Assert.assertTrue("PlayerB may not be able to produce any mana but he he can produce " + playerB.getManaAvailable(currentGame).toString(), playerB.getManaAvailable(currentGame).toString().equals("[]"));
    }
    
   @Test
    public void testCantActivateActivatedAbility() {
        // Activated abilities of artifacts can't be activated.        
        addCard(Zone.BATTLEFIELD, playerA, "Collector Ouphe"); // Creature  {1}{G}  2/2
        
        // {1}: Adarkar Sentinel gets +0/+1 until end of turn.
        addCard(Zone.BATTLEFIELD, playerB, "Adarkar Sentinel"); // Artifact Creature — Soldier (3/3)
        addCard(Zone.BATTLEFIELD, playerB, "Island");

        checkPlayableAbility("Can't Tap", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}", false);
//        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}: ");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
                
        execute();

        assertPowerToughness(playerB, "Adarkar Sentinel", 3, 3);

    }
}
