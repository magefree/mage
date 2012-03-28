package org.mage.test.cards;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class CobraTrapTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Constants.Zone.HAND, playerA, "Cobra Trap");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Constants.Zone.HAND, playerB, "Stone Rain");
        
        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Stone Rain", "Forest");
        castSpell(2, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Cobra Trap");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Forest", 1);
        assertPermanentCount(playerA, "Snake", 4);
    }

    @Test
    public void testCardNegative() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Constants.Zone.HAND, playerA, "Cobra Trap");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Constants.Zone.HAND, playerB, "Stone Rain");
        
        castSpell(2, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Cobra Trap");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Forest", 2);
        assertPermanentCount(playerA, "Snake", 0);
    }

}
