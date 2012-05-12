package org.mage.test.cards.single;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class HinterlandScourgeTest extends CardTestPlayerBase {
    
    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Hinterland Hermit");
//        addCard(Constants.Zone.BATTLEFIELD, playerB, "Ornithopter");
        
        setStopAt(2, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Hinterland Hermit", 0);
        assertPermanentCount(playerA, "Hinterland Scourge", 1);
    }

    @Test
    public void testCard1() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Hinterland Hermit");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Ornithopter");
        
        attack(3, playerA, "Hinterland Scourge");
        setStopAt(3, Constants.PhaseStep.END_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Hinterland Hermit", 0);
        assertPermanentCount(playerA, "Hinterland Scourge", 1);
        assertPermanentCount(playerB, "Ornithopter", 0);
    }

    @Test
    public void testCard2() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Hinterland Hermit");
        
        attack(3, playerA, "Hinterland Scourge");
        setStopAt(3, Constants.PhaseStep.END_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 17);
        assertPermanentCount(playerA, "Hinterland Hermit", 0);
        assertPermanentCount(playerA, "Hinterland Scourge", 1);
    }
}
