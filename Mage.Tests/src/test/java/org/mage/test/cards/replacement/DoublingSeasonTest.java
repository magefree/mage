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

        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Remove three spore counters from {this}: Create a 1/1 green Saproling creature token.");

        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Saproling Token", 2);
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

        // Create a tokenthat's a copy of target creature onto the battlefield. If Rite of Replication was kicked, put five of those tokens onto the battlefield instead.
        addCard(Zone.HAND, playerA, "Rite of Replication");
        // When Venerable Monk enters the battlefield, you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerB, "Venerable Monk", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rite of Replication", "Venerable Monk");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 40);
        assertPermanentCount(playerA, "Venerable Monk", 10);

    }

    /**
     * Doubling Season doesn't create two tokens from opponent's Rite of Raging
     * Storm
     */
    @Test
    public void testDoubleRiteOfRagingStorm() {
        // At the beginning of each player's upkeep, that player creates a 5/1 red Elemental creature token named Lightning Rager.
        // It has trample, haste, and "At the beginning of the end step, sacrifice this creature."
        addCard(Zone.HAND, playerA, "Rite of the Raging Storm");// {3}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // If an effect would put one or more tokens onto the battlefield under your control, it puts twice that many of those tokens onto the battlefield instead.
        // If an effect would place one or more counters on a permanent you control, it places twice that many of those counters on that permanent instead.
        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rite of the Raging Storm");

        attack(2, playerB, "Lightning Rager"); // Can't attack

        attack(3, playerA, "Lightning Rager");
        attack(3, playerA, "Lightning Rager");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Rite of the Raging Storm", 1);

        assertPermanentCount(playerA, "Lightning Rager", 2);

        assertLife(playerB, 10);
        assertLife(playerA, 20);

    }

    @Test
    public void testDoubleRiteOfRagingStormOpponent() {
        // At the beginning of each player's upkeep, that player creates a 5/1 red Elemental creature token named Lightning Rager.
        // It has trample, haste, and "At the beginning of the end step, sacrifice this creature."
        addCard(Zone.HAND, playerA, "Rite of the Raging Storm");// {3}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // If an effect would put one or more tokens onto the battlefield under your control, it puts twice that many of those tokens onto the battlefield instead.
        // If an effect would place one or more counters on a permanent you control, it places twice that many of those counters on that permanent instead.
        addCard(Zone.BATTLEFIELD, playerB, "Doubling Season");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rite of the Raging Storm");

        attack(2, playerB, "Lightning Rager:0"); // Can't attack
        attack(2, playerB, "Lightning Rager:1"); // Can't attack

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Rite of the Raging Storm", 1);

        assertPermanentCount(playerB, "Lightning Rager", 2);

        assertLife(playerB, 20);
        assertLife(playerA, 20);

    }

    /**
     * Gatherer Ruling: 10/1/2005: Planeswalkers will enter the battlefield with
     * double the normal amount of loyalty counters. However, if you activate an
     * ability whose cost has you put loyalty counters on a planeswalker, the
     * number you put on isn't doubled. This is because those counters are put
     * on as a cost, not as an effect.
     */
    @Test
    public void testPlaneswalkerLoyalty() {
        addCard(Zone.BATTLEFIELD, playerA, "Tibalt, the Fiend-Blooded");

        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Draw a card, then discard a card at random.");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        //Should not be doubled
        assertCounterCount("Tibalt, the Fiend-Blooded", CounterType.LOYALTY, 3);
    }

    /**
     * +1 cost is not affected by double, but replace event like Pir,
     * Imaginative Rascal will be affected
     * https://github.com/magefree/mage/issues/5802
     */
    @Test
    public void testPlaneswalkerWithoutReplacementEffect() {
        //addCard(Zone.BATTLEFIELD, playerA, "Pir, Imaginative Rascal");
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Fire Artisan");
        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertCounterCount(playerA, "Chandra, Fire Artisan", CounterType.LOYALTY, 4 + 1);
    }

    @Test
    public void testPlaneswalkerWithReplacementEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Fire Artisan");
        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season"); // x2 counters
        addCard(Zone.BATTLEFIELD, playerA, "Pir, Imaginative Rascal"); // +1 counter

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertCounterCount(playerA, "Chandra, Fire Artisan", CounterType.LOYALTY, 4 + (1 + 1) * 2);
    }

    @Test
    public void testPlaneswalkerCreatesToken() {
        setStrictChooseMode(true);

        // +1: Create a 2/2 black Zombie creature token.
        // -4: Each player sacrifices two creatures.
        // -9: Each opponent chooses a permanent they control of each permanent type and sacrifices the rest.
        addCard(Zone.BATTLEFIELD, playerA, "Liliana, Dreadhorde General");
        // If an effect would create one or more tokens under your control, it creates twice that many of those tokens instead.
        // If an effect would put one or more counters on a permanent you control, it puts twice that many of those counters on that permanent instead.
        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season"); //

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Zombie Token", 2);
    }

}
