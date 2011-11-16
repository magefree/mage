package org.mage.test.cards.destroy;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 * Created by IntelliJ IDEA.
 * User: Loki
 * Date: 16/11/11
 * Time: 10:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class HideousEndTest extends CardTestBase {

    @Test
    public void testWithValidTarget() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Constants.Zone.HAND, playerA, "Hideous End");
        addCard(Constants.Zone.HAND, playerB, "Copper Myr");

        castSpell(playerA, "Hideous End");
        addFixedTarget(playerA, "Hideous End", "Copper Myr");

        execute();
        assertPermanentCount(playerB, "Copper Myr", 0);
        assertLife(playerB, 18);
    }
}
