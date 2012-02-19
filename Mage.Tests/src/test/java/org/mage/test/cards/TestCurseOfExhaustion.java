package org.mage.test.cards;

import mage.Constants;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class TestCurseOfExhaustion extends CardTestPlayerBase {

    @Test
    public void testCard1() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Constants.Zone.HAND, playerA, "Curse of Exhaustion");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Constants.Zone.HAND, playerB, "Lightning Bolt", 2);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Exhaustion", playerB);
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();
        
        assertLife(playerB, 20);
        assertLife(playerA, 17);
    }

    @Test
    public void testCard2() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Constants.Zone.HAND, playerA, "Curse of Exhaustion");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt", 2);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Exhaustion", playerB);
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 14);
    }
}
