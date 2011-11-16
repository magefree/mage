package org.mage.test.cards.damage;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 * @author ayrat
 */
public class LightningBoltTest extends CardTestBase {

    @Test
    public void testDamageOpponent() {
        addCard(Constants.Zone.HAND, playerA, "Mountain");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");

        playLand(playerA, "Mountain");
        castSpell(playerA, "Lightning Bolt");
        // not specifying target, AI should choose opponent by itself

        execute();
        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }

    @Test
    public void testDamageSelf() {
        addCard(Constants.Zone.HAND, playerA, "Mountain");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");

        playLand(playerA, "Mountain");
        castSpell(playerA, "Lightning Bolt");
        addFixedTarget(playerA, "Lightning Bolt", playerA);
        playerA.setAllowBadMoves(true);

        execute();
        assertLife(playerA, 17);
        assertLife(playerB, 20);
    }

    @Test
    public void testDamageSmallCreature() {
        addCard(Constants.Zone.HAND, playerA, "Mountain");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Sejiri Merfolk");

        playLand(playerA, "Mountain");
        castSpell(playerA, "Lightning Bolt");
        addFixedTarget(playerA, "Lightning Bolt", "Sejiri Merfolk");

        execute();
        assertPermanentCount(playerB, "Sejiri Merfolk", 0);
    }

    @Test
    public void testDamageBigCreature() {
        addCard(Constants.Zone.HAND, playerA, "Mountain");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");

        playLand(playerA, "Mountain");
        castSpell(playerA, "Lightning Bolt");
        addFixedTarget(playerA, "Lightning Bolt", "Craw Wurm");

        execute();
        assertPermanentCount(playerB, "Craw Wurm", 1);
    }

    @Test
    public void testDamageBigCreatureTwice() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");

        castSpell(playerA, "Lightning Bolt");
        addFixedTarget(playerA, "Lightning Bolt", "Craw Wurm");
        castSpell(playerA, "Lightning Bolt");
        addFixedTarget(playerA, "Lightning Bolt", "Craw Wurm");

        execute();
        assertPermanentCount(playerB, "Craw Wurm", 0);
    }

}
