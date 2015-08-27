package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Doubling Season: If an effect would put one or more tokens onto the
 * battlefield under your control, it puts twice that many of those tokens onto
 * the battlefield instead. If an effect would place one or more counters on a
 * permanent you control, it places twice that many of those counters on that
 * permanent instead.
 *
 * @author LevelX2
 */
public class DoublingSeasonTest extends CardTestPlayerBase {

    /**
     * Tests that instead of one spore counter there were two spore counters
     * added to Pallid Mycoderm if Doubling Season is on the battlefield.
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
     * Tests if 3 damage are prevented with Test of Faith and Doubling Season is
     * on the battlefield, that 6 +1/+1 counters are added to the target
     * creature.
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
     * Tests that 2 Saproling tokens are created instead of one if Doubling
     * Season is on the battlefield.
     */
    @Test
    public void testDoubleTokens() {
        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        addCard(Zone.HAND, playerA, "Pallid Mycoderm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pallid Mycoderm");

        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Remove three spore counters from {this}: Put a 1/1 green Saproling creature token onto the battlefield.");

        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Saproling", 2);
        assertCounterCount("Pallid Mycoderm", CounterType.SPORE, 1);

    }

    /**
     * Creatures with enter the battlefield triggers are causing a bug when
     * multiple copies are made simultaneously (ie via Doubling Season +
     * Kiki-Jiki, Mirror Breaker or Rite of Replication). After the tokens have
     * entered the battlefield it asks their controller to choose the order that
     * the triggered abilities on the stack but no window opens to select the
     * triggers leaving no option to move the game forward (besides rollback and
     * just not making the tokens). Several attempts with the different
     * combinations make it *seem to be a general bug about duplicates entering
     * at the same time and not related to the specific cards.
     */
    @Test
    public void testDoubleRiteOfReplication() {
        /**
         * If an effect would put one or more tokens onto the battlefield under
         * your control, it puts twice that many of those tokens onto the
         * battlefield instead. If an effect would place one or more counters on
         * a permanent you control, it places twice that many of those counters
         * on that permanent instead.
         */

        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 9);

        // Put a token that's a copy of target creature onto the battlefield. If Rite of Replication was kicked, put five of those tokens onto the battlefield instead.
        addCard(Zone.HAND, playerA, "Rite of Replication");
        // When Venerable Monk enters the battlefield, you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerB, "Venerable Monk", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rite of Replication", "Venerable Monk");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 40);
        assertPermanentCount(playerA, "Venerable Monk", 10);

    }
}
