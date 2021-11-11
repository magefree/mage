package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {3}{U} Instant
 * Return to their owners' hands all creatures with toughness less than or equal to the number of Islands you control.
 * 
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class EngulfTheShoreTest extends CardTestPlayerBase {
    
    /**
     * Basic test.
     */
    @Test
    public void testBasicCreatures() {
                
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Bronze Sable", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant", 1);
        addCard(Zone.HAND, playerA, "Engulf the Shore", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Engulf the Shore");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 2);
        assertHandCount(playerB, "Bronze Sable", 2);
        assertPermanentCount(playerB, "Hill Giant", 1);
        assertGraveyardCount(playerA, "Engulf the Shore", 1);
    }   
    
    /**
     * Reported bug with token creatures:
     * "Engulf the Shore does not remove Vampire tokens created with Call the Bloodline."
     * 
     * NOTE: currently test fails due to issue with not bouncing the tokens
     */
    @Test
    public void testTokenCreatures() {
                
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);    
        addCard(Zone.BATTLEFIELD, playerA, "Bronze Sable", 2);
        addCard(Zone.HAND, playerA, "Engulf the Shore", 1);
        
        addCard(Zone.HAND, playerB, "Hordeling Outburst", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant", 1);
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Hordeling Outburst");
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Engulf the Shore");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Bronze Sable", 2);
        assertPermanentCount(playerB, "Hill Giant", 1);
        assertPermanentCount(playerB, "Goblin Token", 0);
        assertGraveyardCount(playerA, "Engulf the Shore", 1);
        assertGraveyardCount(playerB, "Hordeling Outburst", 1);
    }   
}
