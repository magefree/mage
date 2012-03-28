package org.mage.test.cards;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class FeedThePackTest extends CardTestPlayerBase {
    
    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Feed the Pack");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Craw Wurm");        
        
        setStopAt(2, Constants.PhaseStep.DRAW);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Wolf", 4);
        assertPermanentCount(playerB, "Craw Wurm", 0);
    }

}
