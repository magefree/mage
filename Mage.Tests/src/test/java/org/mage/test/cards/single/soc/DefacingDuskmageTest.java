package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DefacingDuskmageTest extends CardTestPlayerBase {

    @Test
    public void testOpponentsSecondDrawPreparesVandalsEdit() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Defacing Duskmage");
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 3);
        addCard(Zone.LIBRARY, playerA, "Plains", 2);

        addCard(Zone.HAND, playerB, "Divination");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.LIBRARY, playerB, "Island", 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Divination");
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Vandal's Edit");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Plains", 2);
        assertLife(playerA, 18);
        assertLife(playerB, 18);
        assertGraveyardCount(playerA, "Vandal's Edit", 0);
    }
}
