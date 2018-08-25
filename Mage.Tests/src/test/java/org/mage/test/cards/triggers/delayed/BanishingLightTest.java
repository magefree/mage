
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
    
    /**
     * From gatherer rulings for Banishing Light:
     *   "The exiled card returns to the battlefield immediately after Banishing
     *    Light leaves the battlefield. Nothing happens between the two events,
     *    including state-based actions. Banishing Light and the returned card
     *    aren’t on the battlefield at the same time."
     */
    @Test
    public void testStateBasedActionsNotChecked() {
        addCard(Zone.HAND, playerA, "Kithkin Rabble", 1);
        addCard(Zone.HAND, playerA, "Banishing Light", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Claws of Gix", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 8);
        
        //net +0/+0 to playerA's Kithkin Rabble
        addCard(Zone.BATTLEFIELD, playerB, "Crusade", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Elesh Norn, Grand Cenobite", 1);
        
        //remove one Crusade, still +0/+0 due to Kithkin Rabble's ability
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Banishing Light");
        setChoice(playerA, "Crusade");
        
        //should be a 1/1 at this point
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Kithkin Rabble");
        
        //sacrifice the Banishing Light to return Crusade
        activateAbility(1, PhaseStep.END_TURN, playerA,
                "{1}, Sacrifice a permanent you control: You gain 1 life.",
                NO_TARGET, "Kithkin Rabble", StackClause.WHILE_NOT_ON_STACK);
        setChoice(playerA, "Banishing Light");
        
        setStopAt(1, PhaseStep.CLEANUP);
        execute();
        
        //test the Claws of Gix was activated and resolved
        assertLife(playerA, 21);
        
        //test the Banishing Light was actually removed
        assertPermanentCount(playerA, "Banishing Light", 0);
        assertGraveyardCount(playerA, "Banishing Light", 1);
        assertPermanentCount(playerB, "Crusade", 2);
        
        //test that the Kithkin Rabble survived
        assertPermanentCount(playerA, "Kithkin Rabble", 1);
        assertGraveyardCount(playerA, "Kithkin Rabble", 0);
    }
}
