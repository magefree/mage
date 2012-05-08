package org.mage.test.cards.cost.variable;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class MikaeusTheLunarchTest extends CardTestPlayerBase {

    /**
     * Tests that Mikaeus, the Lunarch enters with X counters
     */
    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Constants.Zone.HAND, playerA, "Mikaeus, the Lunarch");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Mikaeus, the Lunarch");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerA, "Mikaeus, the Lunarch", 1);
    }

    
}
