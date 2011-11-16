package org.mage.test.cards.triggers;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 * @author ayratn
 *
 * Card: Whenever a player taps a land for mana, Manabarbs deals 1 damage to that player.
 */
public class ManabarbsTest extends CardTestBase {

    /**
     * Issue 374: manabarb enchantment
     * Games goes into a freeze loop.
     *
     * version: 0.8.1
     *
     * Couldn't reproduce.
     */
    @Test
    public void testMultiTriggers() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Manabarbs");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Constants.Zone.HAND, playerA, "Lightning Elemental");
        addCard(Constants.Zone.HAND, playerA, "Ball Lightning");

        castSpell(playerA, "Ball Lightning");
        castSpell(playerA, "Lightning Elemental");

        execute();
        assertLife(playerA, 13); // burns from Manabarbs
        assertLife(playerB, 10); // ai should attack with 4/1 + 6/1
        assertPermanentCount(playerA, "Lightning Elemental", 1);
        assertPermanentCount(playerA, "Ball Lightning", 0); // sacrificed at EOT
    }
}
