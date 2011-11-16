package org.mage.test.cards.triggers;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 * @author ayratn
 *
 * Card: When Mausoleum Guard dies, put two 1/1 white Spirit creature tokens with flying onto the battlefield.
 */
public class MausoleumGuardTest extends CardTestBase {

    /**
     * Issue 350:	Mausoleum Guard
     * Destroyed a mausoleum guard, was supposed to get two 1/1 fliers when it dies. None of them were created.
     *
     * version: 0.8.1
     */
    @Test
    public void testTokensAppeared() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Mausoleum Guard");

        castSpell(playerA, "Lightning Bolt");
        addFixedTarget(playerA, "Lightning Bolt", "Mausoleum Guard");

        execute();
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerB, "Mausoleum Guard", 0);
        assertPermanentCount(playerA, "Spirit", 0);
        assertPermanentCount(playerB, "Spirit", 2);
    }
}
