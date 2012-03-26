package org.mage.test.cards;

import mage.Constants;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * also tests required targets
 * 
 * @author BetaSteward
 */
public class TestHollowhengeSpirit extends CardTestPlayerBase {
    
    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Constants.Zone.HAND, playerA, "Hollowhenge Spirit");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");
        
        castSpell(2, Constants.PhaseStep.DECLARE_BLOCKERS, playerA, "Hollowhenge Spirit");
        attack(2, playerB, "Craw Wurm");
        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Hollowhenge Spirit", 1);
        assertPermanentCount(playerB, "Craw Wurm", 1);
        assertTapped("Hollowhenge Spirit", false);
        assertTapped("Craw Wurm", true);
    }

    @Test
    public void testCard1() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Constants.Zone.HAND, playerA, "Hollowhenge Spirit");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Craw Wurm");
        
        castSpell(1, Constants.PhaseStep.DECLARE_BLOCKERS, playerA, "Hollowhenge Spirit");
        attack(1, playerA, "Craw Wurm");
        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Hollowhenge Spirit", 1);
        assertPermanentCount(playerA, "Craw Wurm", 1);
        assertTapped("Hollowhenge Spirit", false);
        assertTapped("Craw Wurm", true);
    }

    @Test
    public void testCard2() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Constants.Zone.HAND, playerA, "Hollowhenge Spirit");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Craw Wurm");
        
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Hollowhenge Spirit");
        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Hollowhenge Spirit", 1);
        assertPermanentCount(playerA, "Craw Wurm", 1);
        assertTapped("Craw Wurm", false);
    }

}
