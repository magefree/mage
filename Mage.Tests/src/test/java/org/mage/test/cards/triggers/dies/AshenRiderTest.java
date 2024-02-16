package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class AshenRiderTest extends CardTestPlayerBase {

    /*
     * Volrath, the Shapestealer and Ashen Rider, Ashen Rider has a counter on it:
        Turn Volrath into Ashen Rider:
        Destroy the Volrath (who's the Ashen Rider) with Putrefy:  
        The death trigger for the Volrath copying Ashen Rider did not trigger.
     */
    @Test
    public void cartelAristrocraftInteractionOpponentDoesNotPayLife() {
        setStrictChooseMode(true);
        
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);        
        
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);        
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);        
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);        

        // Flying
        // When Ashen Rider enters the battlefield or dies, exile target permanent.
        addCard(Zone.BATTLEFIELD, playerA, "Ashen Rider"); // Creature {4}{W}{W}{B}{B}

        // At the beginning of combat on your turn, put a -1/-1 counter on up to one target creature.
        // {1}: Until your next turn, Volrath, the Shapestealer becomes a copy of target creature with a counter on it, except it's 7/5 and it has this ability.        
        addCard(Zone.HAND, playerA, "Volrath, the Shapestealer"); // Creature {2}{B}{G}{U}       
        addTarget(playerA, "Ashen Rider");

        // Destroy target artifact or creature. It can't be regenerated.
        addCard(Zone.HAND, playerA, "Putrefy"); // Instant {1}{B}{G}


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Volrath, the Shapestealer");
        
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}: Until your next turn");
        addTarget(playerA,  "Ashen Rider");
        
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Putrefy", "Ashen Rider[only copy]");

        addTarget(playerA, "Silvercoat Lion"); // Dies trigger of Volrath, the Shapestealer copied from Ashen Rider

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Ashen Rider", 4,4);
        
        assertGraveyardCount(playerA, "Putrefy", 1);        
        assertGraveyardCount(playerA, "Volrath, the Shapestealer", 1);
        
        assertExileCount(playerB, "Silvercoat Lion", 1);
        
    }    
}
