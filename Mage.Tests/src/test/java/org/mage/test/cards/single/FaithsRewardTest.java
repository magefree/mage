package org.mage.test.cards.single;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Loki
 */
public class FaithsRewardTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Constants.Zone.HAND, playerA, "Faith's Reward", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "White Knight");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "White Knight");
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Faith's Reward");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "White Knight", 1);
    }
}
