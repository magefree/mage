
package org.mage.test.cards.triggers.delayed;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author kevinwshin
 */
public class BanishingLightTest extends CardTestPlayerBase {
    
    final private String KITHKIN_RABBLE = "Kithkin Rabble";
    final private String BANISHING_LIGHT = "Banishing Light";
    final private String CLAWS_OF_GIX = "Claws of Gix";
    final private String PLAINS = "Plains";
    final private String CRUSADE = "Crusade";
    final private String ELESH_NORN = "Elesh Norn, Grand Cenobite";
    
    /**
     * From gatherer rulings for Banishing Light:
     *   "The exiled card returns to the battlefield immediately after Banishing
     *    Light leaves the battlefield. Nothing happens between the two events,
     *    including state-based actions. Banishing Light and the returned card
     *    aren’t on the battlefield at the same time."
     */
    @Test
    public void testStateBasedActionsNotChecked() {
        addCard(Zone.HAND, playerA, KITHKIN_RABBLE, 1);
        addCard(Zone.HAND, playerA, BANISHING_LIGHT, 1);
        addCard(Zone.BATTLEFIELD, playerA, CLAWS_OF_GIX, 1);
        addCard(Zone.BATTLEFIELD, playerA, PLAINS, 8);
        
        //net +0/+0 to playerA's Kithkin Rabble
        addCard(Zone.BATTLEFIELD, playerB, CRUSADE, 2);
        addCard(Zone.BATTLEFIELD, playerB, ELESH_NORN, 1);
        
        //remove one Crusade, still +0/+0 due to Kithkin Rabble's ability
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, BANISHING_LIGHT);
        setChoice(playerA, CRUSADE);
        
        //should be a 1/1 at this point
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, KITHKIN_RABBLE);
        
        //sacrifice the Banishing Light to return Crusade
        activateAbility(1, PhaseStep.END_TURN, playerA,
                "{1}, Sacrifice a permanent you control: You gain 1 life.",
                NO_TARGET, KITHKIN_RABBLE, StackClause.WHILE_NOT_ON_STACK);
        setChoice(playerA, BANISHING_LIGHT);
        
        setStopAt(1, PhaseStep.CLEANUP);
        execute();
        
        //test the Claws of Gix was activated and resolved
        assertLife(playerA, 21);
        
        //test the Banishing Light was actually removed
        assertPermanentCount(playerA, BANISHING_LIGHT, 0);
        assertGraveyardCount(playerA, BANISHING_LIGHT, 1);
        assertPermanentCount(playerB, CRUSADE, 2);
        
        //test that the Kithkin Rabble survived
        assertPermanentCount(playerA, KITHKIN_RABBLE, 1);
        assertGraveyardCount(playerA, KITHKIN_RABBLE, 0);
    }
}
