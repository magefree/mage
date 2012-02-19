package org.mage.test.cards;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class TestCurseOfEchoes extends CardTestPlayerBase {

    @Test
    public void testCard1() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Island", 5);
        addCard(Constants.Zone.HAND, playerA, "Curse of Echoes");
        addCard(Constants.Zone.HAND, playerB, "Jace's Ingenuity");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Echoes", playerB);
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerB, "Jace's Ingenuity");        

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();
        
        assertHandCount(playerA, 3);
        assertHandCount(playerB, 3);
    }

}
