package org.mage.test.cards.cost.additional;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class LivingDestinyTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Constants.Zone.HAND, playerA, "Living Destiny");
        addCard(Constants.Zone.HAND, playerA, "Craw Wurm");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Living Destiny");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Living Destiny", 1);
        assertLife(playerA, 26);
        assertLife(playerB, 20);
    }

    /**
     * Card can't be cast without possibility to pay additional cost
     */
    @Test
    public void testCantCast() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Constants.Zone.HAND, playerA, "Living Destiny");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Living Destiny");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        // card is still at hand
        assertHandCount(playerA, 1);
    }

    /**
     * Tests that non creature card can't be revealed
     */
    @Test
    public void testNonCreatureCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Constants.Zone.HAND, playerA, "Living Destiny");
        addCard(Constants.Zone.HAND, playerA, "Divination");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Living Destiny");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        // cards are still at hand
        assertHandCount(playerA, 2);
    }
}
