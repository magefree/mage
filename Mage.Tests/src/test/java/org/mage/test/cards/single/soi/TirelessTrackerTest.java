package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 2G
Creature - Human Scout
Whenever a land enters the battlefield under your control, investigate. 

Whenever you sacrifice a Clue, put a +1/+1 counter on Tireless Tracker. 
* 
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class TirelessTrackerTest extends CardTestPlayerBase {
    
    /**
     * Only landfall under your control triggers first ability
     */
    @Test
    public void landfallUnderOwnControlTriggers() {
        
        addCard(Zone.BATTLEFIELD, playerA, "Tireless Tracker", 1);
        addCard(Zone.HAND, playerA, "Forest", 3);
        addCard(Zone.HAND, playerB, "Wastes", 3);
        
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        playLand(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Wastes");
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertPermanentCount(playerA, "Forest", 1);
        assertPermanentCount(playerB, "Wastes", 1);
        assertPermanentCount(playerA, "Clue Token", 1);
    }

    // TODO: add tests for 2nd ability
}
