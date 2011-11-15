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

        execute();
        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }
}
