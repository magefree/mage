package org.mage.test.cards;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class TestWerewolfRansacker extends CardTestPlayerBase {
    
    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Afflicted Deserter");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Ornithopter");
        
        setStopAt(2, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerB, "Ornithopter", 0);
        assertPermanentCount(playerA, "Afflicted Deserter", 0);
        assertPermanentCount(playerA, "Werewolf Ransacker", 1);
    }
   
}
