
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ProvokeTest extends CardTestPlayerBase{
    
    @Test
    public void testProvokeTappedCreature() {
        // Creature - Beast 5/3        
        // Trample
        // Provoke (When this attacks, you may have target creature defending player controls untap and block it if able.)
        addCard(Zone.BATTLEFIELD, playerA, "Brontotherium");
        
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        attack(2, playerB, "Silvercoat Lion"); // So it's tapped
        
        attack(3, playerA, "Brontotherium"); 
        // Silvercoat Lion is auto-chosen since only target
        
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 18); // one attack from Lion
        assertLife(playerB, 17); // Because of Trample
        
        assertPermanentCount(playerA, "Brontotherium", 1);
        assertGraveyardCount(playerB,"Silvercoat Lion", 1);
    }
    
    @Test
    public void testProvokeCreatureThatCantBlock() {
        // Creature - Beast 5/3        
        // Trample
        // Provoke (When this attacks, you may have target creature defending player controls untap and block it if able.)
        addCard(Zone.BATTLEFIELD, playerA, "Brontotherium");
        
        // Creature - Zombie Imp 1/1
        // Discard a card: Putrid Imp gains flying until end of turn.
        // Threshold - As long as seven or more cards are in your graveyard, Putrid Imp gets +1/+1 and can't block.
        addCard(Zone.BATTLEFIELD, playerB, "Putrid Imp", 1);
        addCard(Zone.GRAVEYARD, playerB, "Swamp", 7);

        attack(2, playerB, "Putrid Imp"); // So it's tapped
        
        attack(3, playerA, "Brontotherium"); 
        // Putrid Imp is auto-chosen sicne only target
        
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Brontotherium", 1);
        assertPermanentCount(playerB, "Putrid Imp", 1); // Can't Block so still alive

        assertLife(playerA, 18); // one attack from Imp
        assertLife(playerB, 15); // Not blocked
    }       
}
