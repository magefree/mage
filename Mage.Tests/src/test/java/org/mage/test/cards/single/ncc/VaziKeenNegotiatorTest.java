package org.mage.test.cards.single.ncc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author awjackson
 */
public class VaziKeenNegotiatorTest extends CardTestPlayerBase {
    @Test
    public void testVaziActivatedAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Vazi, Keen Negotiator");
        addCard(Zone.HAND, playerA, "Krenko's Command");    // Create two 1/1 red Goblin creature tokens
        addCard(Zone.HAND, playerA, "Strike It Rich");      // Create a Treasure token

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Krenko's Command", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Strike It Rich", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target opponent creates", playerB);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Goblin Token", 2);
        assertPermanentCount(playerA, "Treasure Token", 1);
        assertPermanentCount(playerB, "Treasure Token", 1);
    }
}
