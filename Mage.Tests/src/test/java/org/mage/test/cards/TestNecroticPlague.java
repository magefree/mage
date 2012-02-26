package org.mage.test.cards;

import mage.Constants;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * also tests triggered abilities that switch from one permanent to another
 * 
 * @author BetaSteward
 * 
 */
public class TestNecroticPlague extends CardTestPlayerBase {

    @Test
    public void testCard1() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Constants.Zone.HAND, playerA, "Necrotic Plague");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Sejiri Merfolk");
        
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Necrotic Plague", "Sejiri Merfolk");

        setStopAt(2, Constants.PhaseStep.PRECOMBAT_MAIN);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerB, "Sejiri Merfolk", 0);
        assertGraveyardCount(playerA, "Necrotic Plague", 1);
        assertGraveyardCount(playerB, 1);
        assertGraveyardCount(playerB, "Sejiri Merfolk", 1);
    }

    @Test
    public void testCard2() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Goblin Deathraiders");
        addCard(Constants.Zone.HAND, playerA, "Necrotic Plague");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Sejiri Merfolk");
        
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Necrotic Plague", "Sejiri Merfolk");

        setStopAt(3, Constants.PhaseStep.PRECOMBAT_MAIN);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Goblin Deathraiders", 0);
        assertPermanentCount(playerB, "Sejiri Merfolk", 0);
        assertGraveyardCount(playerA, 2);
        assertGraveyardCount(playerA, "Necrotic Plague", 1);
        assertGraveyardCount(playerA, "Goblin Deathraiders", 1);
        assertGraveyardCount(playerB, 1);
        assertGraveyardCount(playerB, "Sejiri Merfolk", 1);
    }
    
}
