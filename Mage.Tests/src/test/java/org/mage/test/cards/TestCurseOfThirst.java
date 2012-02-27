package org.mage.test.cards;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class TestCurseOfThirst extends CardTestPlayerBase {

    @Test
    public void testCard1() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Constants.Zone.HAND, playerA, "Curse of Thirst");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Thirst", playerB);        

        setStopAt(2, Constants.PhaseStep.DRAW);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 19);
    }

    @Test
    public void testCard2() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Constants.Zone.HAND, playerA, "Curse of Thirst");
        addCard(Constants.Zone.HAND, playerA, "Curse of Bloodletting");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Bloodletting", playerB);        
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Thirst", playerB);        

        setStopAt(2, Constants.PhaseStep.DRAW);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 16);
    }

}
