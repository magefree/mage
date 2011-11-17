package org.mage.test.cards.targets.sacrifice;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

public class GethsVerdict extends CardTestBase {

    @Test
    public void test() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Constants.Zone.HAND, playerA, "Geth's Verdict");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Copper Myr");

        castSpell(playerA, "Geth's Verdict");
        execute();

        assertPermanentCount(playerB, "Copper Myr", 0);
        assertLife(playerB, 19);
    }
}
