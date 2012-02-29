package org.mage.test.cards;

import junit.framework.Assert;
import mage.Constants;
import mage.filter.Filter;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * also tests flashback
 * 
 * @author BetaSteward
 */
public class TestIncreasingCards extends CardTestPlayerBase {

    @Test
    public void testIncreasingAmbition() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 8);
        addCard(Constants.Zone.HAND, playerA, "Increasing Ambition");
        addCard(Constants.Zone.LIBRARY, playerA, "Swamp", 4);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Increasing Ambition");
        activateAbility(3, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {7}{B}");

        setStopAt(3, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, 0);
        assertHandCount(playerA, 4);
        assertExileCount("Increasing Ambition", 1);

    }

    @Test
    public void testIncreasingConfusion() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Constants.Zone.HAND, playerA, "Increasing Confusion");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Increasing Confusion");
        activateAbility(3, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {X}{U}");

        setStopAt(3, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, 0);
        assertExileCount("Increasing Confusion", 1);
        assertGraveyardCount(playerB, 9);

    }
 
    @Test
    public void testIncreasingDevotion() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 9);
        addCard(Constants.Zone.HAND, playerA, "Increasing Devotion");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Increasing Devotion");
        activateAbility(3, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {7}{W}{W}");

        setStopAt(3, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, 0);
        assertPermanentCount(playerA, "Human", 15);
        assertExileCount("Increasing Devotion", 1);

    }
    
    @Test
    public void testIncreasingSavagery() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 7);
        addCard(Constants.Zone.HAND, playerA, "Increasing Savagery");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Ornithopter");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Increasing Savagery");
        activateAbility(3, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {5}{G}{G}");

        setStopAt(3, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, 0);
        assertPowerToughness(playerA, "Ornithopter", 15, 17, Filter.ComparisonScope.Any);
        assertExileCount("Increasing Savagery", 1);

    }

    @Test
    public void testIncreasingVengeance() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Constants.Zone.HAND, playerA, "Increasing Vengeance");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt", 2);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Increasing Vengeance", "Lightning Bolt");
        castSpell(3, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        activateAbility(3, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {3}{R}{R}");

        setStopAt(3, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 5);
        assertGraveyardCount(playerA, 2);
        assertExileCount("Increasing Vengeance", 1);

    }
    
}
