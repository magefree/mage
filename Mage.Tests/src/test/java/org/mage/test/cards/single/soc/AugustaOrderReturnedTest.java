package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBaseWithRangeAll;

public class AugustaOrderReturnedTest extends CardTestMultiPlayerBaseWithRangeAll {

    @Test
    public void testEachPlayerExilesAndCountersCountOnlyNonlandCards() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Augusta, Order Returned");
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.GRAVEYARD, playerA, "Forest");
        addCard(Zone.GRAVEYARD, playerB, "Lightning Bolt");
        addCard(Zone.GRAVEYARD, playerC, "Grizzly Bears");

        attack(1, playerA, "Augusta, Order Returned", playerD);
        attack(1, playerA, "Memnite", playerD);
        addTarget(playerA, "Forest");
        addTarget(playerB, "Lightning Bolt");
        addTarget(playerC, "Grizzly Bears");
        addTarget(playerA, "Memnite");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount("Forest", 1);
        assertExileCount("Lightning Bolt", 1);
        assertExileCount("Grizzly Bears", 1);
        assertPowerToughness(playerA, "Memnite", 3, 3);
        assertPermanentCount(playerA, "Augusta, Order Returned", 1);
    }
}
