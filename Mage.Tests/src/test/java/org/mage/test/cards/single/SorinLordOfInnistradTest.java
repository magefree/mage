package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
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
        addCard(Zone.BATTLEFIELD, playerA, "Sorin, Lord of Innistrad");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Put a a 1/1 black Vampire creature token with lifelink onto the battlefield. ");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Sorin, Lord of Innistrad", 1);
        assertPermanentCount(playerA, "Vampire", 1);

    }

    @Test
    public void testCard2() {
        addCard(Zone.BATTLEFIELD, playerA, "Sorin, Lord of Innistrad");
        addCard(Zone.BATTLEFIELD, playerA, "Sejiri Merfolk");

        addCounters(1, PhaseStep.UPKEEP, playerA, "Sorin, Lord of Innistrad", CounterType.LOYALTY, 1);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2: You get an emblem with ");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "-2: You get an emblem with ");
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Sorin, Lord of Innistrad", 0);
        assertPermanentCount(playerA, "Sejiri Merfolk", 1);
        assertPowerToughness(playerA, "Sejiri Merfolk", 4, 1, Filter.ComparisonScope.Any);
    }

    @Test
    public void testCard3() {
        addCard(Zone.BATTLEFIELD, playerA, "Sorin, Lord of Innistrad");
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");
        addCard(Zone.BATTLEFIELD, playerB, "Angel of Mercy");

        addCounters(1, PhaseStep.UPKEEP, playerA, "Sorin, Lord of Innistrad", CounterType.LOYALTY, 3);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-6: ", "Craw Wurm^Angel of Mercy");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Craw Wurm", 1);
        assertPermanentCount(playerA, "Angel of Mercy", 1);

        assertLife(playerA, 23);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Sorin, Lord of Innistrad", 0);
        assertPermanentCount(playerB, "Craw Wurm", 0);
        assertPermanentCount(playerB, "Angel of Mercy", 0);
        assertPermanentCount(playerA, "Craw Wurm", 1);
        assertPermanentCount(playerA, "Angel of Mercy", 1);
    }

}
