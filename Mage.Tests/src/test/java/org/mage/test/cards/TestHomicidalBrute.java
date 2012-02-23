package org.mage.test.cards;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class TestHomicidalBrute extends CardTestPlayerBase {
    
    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Civilized Scholar");
        
        setStopAt(2, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Civilized Scholar", 0);
        assertPermanentCount(playerA, "Homicidal Brute", 1);
    }
   
}
