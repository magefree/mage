package org.mage.test.cards.single;

import mage.Constants;
import mage.counters.CounterType;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * also tests emblems
 * 
 * @author BetaSteward
 */
public class SorinLordOfInnistradTest extends CardTestPlayerBase {
    
    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Sorin, Lord of Innistrad");
        
        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "+1: put a a 1/1 black Vampire creature token with lifelink onto the battlefield. ");
        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Sorin, Lord of Innistrad", 1);
        assertPermanentCount(playerA, "Vampire", 1);

    }

    @Test
    public void testCard2() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Sorin, Lord of Innistrad");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Sejiri Merfolk");
        
        addCounters(1, Constants.PhaseStep.UPKEEP, playerA, "Sorin, Lord of Innistrad", CounterType.LOYALTY, 1);
        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "-2: You get an emblem with \"[creature you control get +1/+0. ]\". ");
        activateAbility(3, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "-2: You get an emblem with \"[creature you control get +1/+0. ]\". ");
        setStopAt(3, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Sorin, Lord of Innistrad", 0);
        assertPermanentCount(playerA, "Sejiri Merfolk", 1);
        assertPowerToughness(playerA, "Sejiri Merfolk", 4, 1, Filter.ComparisonScope.Any);
    }

    @Test
    public void testCard3() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Sorin, Lord of Innistrad");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Angel of Mercy");
        
        addCounters(1, Constants.PhaseStep.UPKEEP, playerA, "Sorin, Lord of Innistrad", CounterType.LOYALTY, 3);
        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "-6: ", "Craw Wurm^Angel of Mercy");
        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 23);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Sorin, Lord of Innistrad", 0);
        assertPermanentCount(playerB, "Craw Wurm", 0);
        assertPermanentCount(playerB, "Angel of Mercy", 0);
        assertPermanentCount(playerA, "Craw Wurm", 1);
        assertPermanentCount(playerA, "Angel of Mercy", 1);
    }

}
