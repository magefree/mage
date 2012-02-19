package org.mage.test.cards;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class TestCurseOfBloodletting extends CardTestPlayerBase {

    @Test
    public void testCard1() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Constants.Zone.HAND, playerA, "Curse of Bloodletting");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt", 2);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Bloodletting", playerB);
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerA);
        

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();
        
        assertLife(playerA, 17);
        assertLife(playerB, 14);
    }

}
