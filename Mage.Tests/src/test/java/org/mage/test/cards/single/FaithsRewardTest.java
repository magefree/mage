package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Loki
 */
public class FaithsRewardTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.HAND, playerA, "Faith's Reward", 1);
        addCard(Zone.BATTLEFIELD, playerA, "White Knight");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "White Knight");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Faith's Reward");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "White Knight", 1);
    }
}
