package org.mage.test.cards;

import junit.framework.Assert;
import mage.Constants;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TestAlphaBrawl extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 8);
        addCard(Constants.Zone.HAND, playerA, "Alpha Brawl");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Air Elemental", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Horned Turtle", 4);
        
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Alpha Brawl", "Air Elemental");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerB, "Air Elemental", 0);
        assertPermanentCount(playerB, "Horned Turtle", 0);
    }

    
    @Test
    public void testCardWithInfect() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 8);
        addCard(Constants.Zone.HAND, playerA, "Alpha Brawl");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Blackcleave Goblin", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Air Elemental", 2);
        
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Alpha Brawl", "Blackcleave Goblin");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerB, "Blackcleave Goblin", 0);
        assertPermanentCount(playerB, "Air Elemental", 2);
        assertPowerToughness(playerB, "Air Elemental", 2, 2, Filter.ComparisonScope.All);
    }
    
}
