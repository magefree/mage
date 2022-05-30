
package org.mage.test.cards.cost.modification;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class FluctuatorTest extends CardTestPlayerBase {

    /**
     * NOTE: As of 4/19/2017 this test is failing due to a bug in code. 
     * See issue #3148
     *
     * Fluctuator makes 'Akroma's Vengeance' cyclic cost reduced to {1} Test it
     * with one Plains on battlefield.
     */
    @Test
    public void testFluctuatorReducedBy2() {

        // Destroy all artifacts, creatures, and enchantments.
        // Cycling ({3}, Discard this card: Draw a card.)
        addCard(Zone.HAND, playerA, "Akroma's Vengeance");

        // Cycling abilities you activate cost you up to {2} less to activate.
        addCard(Zone.BATTLEFIELD, playerA, "Fluctuator");

        // One mana should be enough
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling");
        setChoice(playerA, "2"); // reduce 2 generic mana

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Akroma's Vengeance", 1);
        assertHandCount(playerA, 1);
    }

    /**
     * Fluctuator makes 'Akroma's Vengeance' cyclic cost reduced to {1}
     *
     * Make sure it wasn't reduced more than by two.
     */
    @Test
    public void testFluctuatorReducedNotBy3() {

        // Destroy all artifacts, creatures, and enchantments.
        // Cycling ({3}, Discard this card: Draw a card.)
        addCard(Zone.HAND, playerA, "Akroma's Vengeance");

        // Cycling abilities you activate cost you up to {2} less to activate.
        addCard(Zone.BATTLEFIELD, playerA, "Fluctuator");

        checkPlayableAbility("Can't cycle", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    /**
     * NOTE: As of 4/19/2017 this test is failing due to a bug in code. 
     * See issue #3148
     *
     * Test 2 Fluctuators reduce cycling cost up to 4.
     */
    @Test
    public void testTwoFluctuatorsReduceBy4() {

        // Destroy all artifacts, creatures, and enchantments.
        // Cycling ({3}, Discard this card: Draw a card.)
        addCard(Zone.HAND, playerA, "Akroma's Vengeance");

        // Cycling abilities you activate cost you up to {2} less to activate.
        addCard(Zone.BATTLEFIELD, playerA, "Fluctuator", 2); // 2 copies

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling");
        setChoice(playerA, "2"); // reduce 2 generic mana
        setChoice(playerA, "1"); // reduce 1 generic mana

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Akroma's Vengeance", 1);
        assertHandCount(playerA, 1);
    }
}
