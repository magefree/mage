package org.mage.test.cards.single;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * also tests regenerate and
 * tests that permanents with protection can be sacrificed
 * 
 * @author BetaSteward
 */
public class FaithsShieldTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "White Knight");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Constants.Zone.HAND, playerA, "Faith's Shield");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        
        setChoice(playerA, "Red");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Faith's Shield", "White Knight");
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "White Knight");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 17);
        assertPermanentCount(playerA, "White Knight", 1);
    }

    @Test
    public void testCardExile1() {
        setLife(playerA, 5);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "White Knight");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Constants.Zone.HAND, playerA, "Faith's Shield");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        
        setChoice(playerA, "Red");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Faith's Shield", "White Knight");
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerA);

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();
        
        assertLife(playerA, 5);
        assertLife(playerB, 20);
    }

}
