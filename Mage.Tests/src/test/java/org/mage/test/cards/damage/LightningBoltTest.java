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
        System.out.println("TEST: testDamageOpponent");
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
        System.out.println("TEST: testDamageSelf");
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

}
