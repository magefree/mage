package org.mage.test.cards;

import mage.Constants;
import mage.counters.CounterType;
import mage.filter.Filter;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
@Ignore
public class SoulSeizerTest extends CardTestPlayerBase {
    
    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Soul Seizer");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");        
        
        attack(1, playerA, "Soul Seizer");
        setStopAt(1, Constants.PhaseStep.END_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertPermanentCount(playerA, "Ghastly Haunting", 1);
        assertPermanentCount(playerA, "Soul Seizer", 0);
        assertPermanentCount(playerA, "Craw Wurm", 1);
    }

    @Test
    public void testCard1() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Soul Seizer");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Constants.Zone.HAND, playerB, "Clear");
        
        
        attack(1, playerA, "Soul Seizer");
        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Clear", "Ghastly Haunting");
        setStopAt(2, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertPermanentCount(playerA, "Ghastly Haunting", 0);
        assertPermanentCount(playerA, "Soul Seizer", 0);
        assertGraveyardCount(playerA, 1);
        assertPermanentCount(playerB, "Craw Wurm", 1);
    }

    @Test
    public void testCard2() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Soul Seizer");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Constants.Zone.HAND, playerA, "Battlegrowth");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");
        
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Battlegrowth", "Soul Seizer");
        attack(1, playerA, "Soul Seizer");
        setStopAt(1, Constants.PhaseStep.END_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 18);
        assertPermanentCount(playerA, "Ghastly Haunting", 1);
        assertCounterCount("Ghastly Haunting", CounterType.P1P1, 1);
        assertPermanentCount(playerA, "Soul Seizer", 0);
        assertGraveyardCount(playerA, 1);
        assertPermanentCount(playerA, "Craw Wurm", 1);
        assertPowerToughness(playerA, "Craw Wurm", 6, 4, Filter.ComparisonScope.All);
    }

}
