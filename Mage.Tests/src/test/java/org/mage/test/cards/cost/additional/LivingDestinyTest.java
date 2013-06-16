package org.mage.test.cards.cost.additional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class LivingDestinyTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Living Destiny");
        addCard(Zone.HAND, playerA, "Craw Wurm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Living Destiny");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
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
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Living Destiny");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Living Destiny");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // card is still at hand
        assertHandCount(playerA, 1);
    }

    /**
     * Tests that non creature card can't be revealed
     */
    @Test
    public void testNonCreatureCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Living Destiny");
        addCard(Zone.HAND, playerA, "Divination");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Living Destiny");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // cards are still at hand
        assertHandCount(playerA, 2);
    }
}
