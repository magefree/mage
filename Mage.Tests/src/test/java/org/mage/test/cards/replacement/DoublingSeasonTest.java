package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Doubling Season:
 *      If an effect would put one or more tokens onto the battlefield under your control, it puts twice that many of those tokens onto the battlefield instead.
 *      If an effect would place one or more counters on a permanent you control, it places twice that many of those counters on that permanent instead.
 *
 * @author LevelX2
 */
public class DoublingSeasonTest extends CardTestPlayerBase {

    /**
     * Tests that instead of one spore counter there were two spore counters added to Pallid Mycoderm
     * if Doubling Season is on the battlefield.
     */
    @Test
    public void testDoubleSporeCounter() {
        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        addCard(Zone.HAND, playerA, "Pallid Mycoderm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pallid Mycoderm");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertCounterCount("Pallid Mycoderm", CounterType.SPORE, 2);
    }

    /**
     * Tests if 3 damage are prevented with Test of Faith and Doubling Season is on
     * the battlefield, that 6 +1/+1 counters are added to the target creature.
     */
    @Test
    public void testDoubleP1P1Counter() {
        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Test of Faith");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        addCard(Zone.HAND, playerB, "Lightning Helix");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Test of Faith", "Silvercoat Lion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Helix", "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 23);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertCounterCount("Silvercoat Lion", CounterType.P1P1, 6);
        assertPowerToughness(playerA, "Silvercoat Lion", 8, 8);

    }
    /**
     * Tests that 2 Saproling tokens are created instead of one if Doubling Season is on
     * the battlefield.
     */
    @Test
    public void testDoubleTokens() {
        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        addCard(Zone.HAND, playerA, "Pallid Mycoderm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pallid Mycoderm");

        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Remove three Spore counters from {this}: Put a 1/1 green Saproling creature token onto the battlefield.");

        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Saproling", 2);
        assertCounterCount("Pallid Mycoderm", CounterType.SPORE, 1);

    }

}
