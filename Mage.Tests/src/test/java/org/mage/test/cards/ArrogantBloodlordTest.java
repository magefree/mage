package org.mage.test.cards;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class ArrogantBloodlordTest extends CardTestPlayerBase {
    // Gets blocked
    @Test
    public void testCardBlocked() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Arrogant Bloodlord");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Wall of Air", 1);
        
        attack(1, playerA, "Arrogant Bloodlord");
        block(1, playerB, "Wall of Air", "Arrogant Bloodlord");

        setStopAt(1, Constants.PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Arrogant Bloodlord", 0);
        assertPermanentCount(playerB, "Wall of Air", 1);
    }
    
    @Test
    public void testCardBlocker() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Caravan Hurda");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Arrogant Bloodlord");
        
        attack(1, playerA, "Caravan Hurda");
        block(1, playerB, "Arrogant Bloodlord", "Caravan Hurda");

        setStopAt(1, Constants.PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Caravan Hurda", 1);
        assertPermanentCount(playerB, "Arrogant Bloodlord", 0);
    }
}
