package org.mage.test.cards.single.roe;

import mage.Constants;
import mage.cards.Card;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public class CastThroughTimeTest extends CardTestPlayerBase {

    /**
     * Tests Rebound works with a card that has no Rebound itself
     */
    @Test
    public void testCastWithRebound() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Cast Through Time");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(3, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 14);
    }

    /**
     * Tests rebound from two Cast Through Time instances
     * Should have no effect for second copy
     */
    @Test
    public void testCastWithDoubleRebound() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Cast Through Time", 2);
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(3, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 14);
    }

    /**
     * Tests rebound tooltip
     */
    @Test
    public void testReboundTooltipExists() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Cast Through Time");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        boolean found = false;
        for (Card card : currentGame.getPlayer(playerA.getId()).getHand().getCards(currentGame)) {
            if (card.getName().equals("Lightning Bolt")) {
                for (String rule : card.getRules()) {
                    if (rule.startsWith("Rebound")) {
                        found = true;
                    }
                }
            }
        }

        Assert.assertTrue("Couldn't find Rebound rule text displayed for the card", found);
    }

    /**
     * Tests Rebound disappeared
     */
    @Test
    public void testCastWithoutRebound() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Cast Through Time");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        addCard(Constants.Zone.HAND, playerA, "Naturalize");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Naturalize", "Cast Through Time");
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(3, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 17);
    }

    /**
     * Tests other than Battlefield zone
     */
    @Test
    public void testInAnotherZone() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Constants.Zone.GRAVEYARD, playerA, "Cast Through Time");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(3, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 17);
    }

}
