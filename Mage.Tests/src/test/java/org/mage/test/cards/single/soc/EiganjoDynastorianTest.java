package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBaseWithRangeAll;

public class EiganjoDynastorianTest extends CardTestMultiPlayerBaseWithRangeAll {

    @Test
    public void testPreparedReplenishCastsFromExileCopy() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Eiganjo Dynastorian");
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.GRAVEYARD, playerA, "Glorious Anthem");

        attack(1, playerA, "Eiganjo Dynastorian", playerD);
        attack(1, playerA, "Memnite", playerD);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Replenish");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Eiganjo Dynastorian", 1);
        assertPermanentCount(playerA, "Glorious Anthem", 1);
        assertGraveyardCount(playerA, "Replenish", 0);
    }
}
