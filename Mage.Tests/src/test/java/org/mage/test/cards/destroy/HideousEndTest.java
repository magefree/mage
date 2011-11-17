package org.mage.test.cards.destroy;

import mage.Constants;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

public class HideousEndTest extends CardTestBase {

    @Test
    public void testWithValidTarget() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Constants.Zone.HAND, playerA, "Hideous End");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Copper Myr");

        castSpell(playerA, "Hideous End");
        addFixedTarget(playerA, "Hideous End", "Copper Myr");

        execute();
        assertPermanentCount(playerB, "Copper Myr", 0);
        assertLife(playerB, 18);
    }

    @Test
    public void testWithInvalidTarget() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Constants.Zone.HAND, playerA, "Hideous End");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Zombie Goliath");

        castSpell(playerA, "Hideous End");
        addFixedTarget(playerA, "Hideous End", "Zombie Goliath");

        execute();
        assertPermanentCount(playerB, "Zombie Goliath", 1);
        assertLife(playerB, 20);
    }

    @Test
    @Ignore
    public void testWithPossibleProtection() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Constants.Zone.HAND, playerA, "Hideous End");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Plains");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Plains");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Copper Myr");
        addCard(Constants.Zone.HAND, playerB, "Apostle's Blessing");

        castSpell(playerA, "Hideous End");
        addFixedTarget(playerA, "Hideous End", "Copper Myr");

        execute();
        assertPermanentCount(playerB, "Copper Myr", 1);
        assertLife(playerB, 20);
    }
}
