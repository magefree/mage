package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BadDealTest extends CardTestPlayerBase {

    @Test
    public void castBadDeal(){
        addCard(Zone.HAND, playerA, "Bad Deal");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.HAND, playerB, "Forest", 2);
        addCard(Zone.LIBRARY, playerA, "Swamp", 2);

        // You draw two cards and each opponent discards two cards. Each player loses 2 life.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bad Deal");
        addTarget(playerB, "Forest^Forest");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 18);
        assertHandCount(playerA, 2);
        assertHandCount(playerB, 0);
    }
}
