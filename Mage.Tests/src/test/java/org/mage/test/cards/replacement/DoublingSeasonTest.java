package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.stream.IntStream;

/**
 * Doubling Season:
 * If an effect would put one or more tokens onto the
 * battlefield under your control, it puts twice that many of those tokens onto
 * the battlefield instead.
 * If an effect would place one or more counters on a
 * permanent you control, it places twice that many of those counters on that
 * permanent instead.
 *
 * @author LevelX2, JayDi85
 */
public class DoublingSeasonTest extends CardTestPlayerBase {

    /**
     * Tests that instead of one spore counter there were two spore counters
     * added to Pallid Mycoderm if Doubling Season is on the battlefield.
     */
    @Test
    public void test_Counters_ByTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        // At the beginning of your upkeep, put a spore counter on Pallid Mycoderm.
        addCard(Zone.HAND, playerA, "Pallid Mycoderm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pallid Mycoderm");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertCounterCount("Pallid Mycoderm", CounterType.SPORE, 2);
    }

    /**
     * Tests that 2 Saproling tokens are created instead of one if Doubling
     * Season is on the battlefield.
     */
    @Test
    public void test_Tokens_ByActivate() {
        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        // At the beginning of your upkeep, put a spore counter on Pallid Mycoderm.
        // Remove three spore counters from Pallid Mycoderm: Create a 1/1 green Saproling creature token.
        addCard(Zone.HAND, playerA, "Pallid Mycoderm");

        // prepare pallid and wait upkeeps to collect spore counters
        // turn 3 - +2 counters
        // turn 5 - +2 counters
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pallid Mycoderm");

        // create token and double it
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Remove three spore counters from {this}: Create");

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Saproling Token", 1 + 1);
        assertCounterCount("Pallid Mycoderm", CounterType.SPORE, 2 + 2 - 3);
    }

    /**
     * Tests if 3 damage are prevented with Test of Faith and Doubling Season is
     * on the battlefield, that 6 +1/+1 counters are added to the target
     * creature.
     */
    @Test
    public void test_Counters_ByPreventEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Prevent the next 3 damage that would be dealt to target creature this turn.
        // For each 1 damage prevented this way, put a +1/+1 counter on that creature.
        addCard(Zone.HAND, playerA, "Test of Faith");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        // Lightning Helix deals 3 damage to any target and you gain 3 life.
        addCard(Zone.HAND, playerB, "Lightning Helix");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Test of Faith", "Silvercoat Lion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Helix", "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 23);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertCounterCount("Silvercoat Lion", CounterType.P1P1, 6);
        assertPowerToughness(playerA, "Silvercoat Lion", 8, 8);
    }

    @Test
    public void test_Tokens_ByPlaneswalkerActivate() {
        // +1: Create a 2/2 black Zombie creature token.
        addCard(Zone.BATTLEFIELD, playerA, "Liliana, Dreadhorde General");
        //
        // If an effect would create one or more tokens under your control, it creates twice that many of those tokens instead.
        // If an effect would put one or more counters on a permanent you control, it puts twice that many of those counters on that permanent instead.
        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Zombie Token", 2);
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
    public void test_RiteOfReplication_Tokens_ByMultipleEffects() {
        /**
         * If an effect would put one or more tokens onto the battlefield under
         * your control, it puts twice that many of those tokens onto the
         * battlefield instead. If an effect would place one or more counters on
         * a permanent you control, it places twice that many of those counters
         * on that permanent instead.
         */

        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 9);

        // Create a token that's a copy of target creature onto the battlefield.
        // If Rite of Replication was kicked, put five of those tokens onto the battlefield instead.
        addCard(Zone.HAND, playerA, "Rite of Replication");
        // When Venerable Monk enters the battlefield, you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerB, "Venerable Monk", 1);

        // create 5 * 2 copied tokens
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rite of Replication", "Venerable Monk");
        setChoice(playerA, true); // use kicker
        IntStream.rangeClosed(1, 9).forEach(value -> {
            setChoice(playerA, "When {this} enters"); // x10 triggers order
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 + 10 * 2); // x10 etb x2 life
        assertPermanentCount(playerA, "Venerable Monk", 10);
    }

    /**
     * Doubling Season doesn't create two tokens from opponent's Rite of Raging Storm
     */
    @Test
    public void test_RiteOfReplication_TokensFromController_CanAttack() {
        // At the beginning of each player's upkeep, that player creates a 5/1 red Elemental creature token named Lightning Rager.
        // It has trample, haste, and "At the beginning of the end step, sacrifice this creature."
        addCard(Zone.HAND, playerA, "Rite of the Raging Storm");// {3}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        //
        // If an effect would put one or more tokens onto the battlefield under your control, it puts twice that many of those tokens onto the battlefield instead.
        // If an effect would place one or more counters on a permanent you control, it places twice that many of those counters on that permanent instead.
        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season");

        // prepare x2 tokens on turn 2 and turn 3
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rite of the Raging Storm");

        // controller's token can attack opponent
        attack(3, playerA, "Lightning Rager");
        attack(3, playerA, "Lightning Rager");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Rite of the Raging Storm", 1);
        assertPermanentCount(playerA, "Lightning Rager", 2);
        assertTappedCount("Lightning Rager", true, 2);

        assertLife(playerB, 10);
        assertLife(playerA, 20);
    }

    @Test
    public void test_RiteOfReplication_TokensFromOpponent_CanNotAttack() {
        // At the beginning of each player's upkeep, that player creates a 5/1 red Elemental creature token named Lightning Rager.
        // It has trample, haste, and "At the beginning of the end step, sacrifice this creature."
        addCard(Zone.HAND, playerA, "Rite of the Raging Storm");// {3}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        //
        // If an effect would put one or more tokens onto the battlefield under your control, it puts twice that many of those tokens onto the battlefield instead.
        // If an effect would place one or more counters on a permanent you control, it places twice that many of those counters on that permanent instead.
        addCard(Zone.BATTLEFIELD, playerB, "Doubling Season");

        // prepare x2 tokens on turn 2
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rite of the Raging Storm");

        // opponent's token can not attack
        attack(2, playerB, "Lightning Rager"); // can't attack

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);

        try {
            execute();
            
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Can't find available command - attack:Lightning Rager")) {
                Assert.fail("Should have thrown error about cannot attack, but got:\n" + e.getMessage());
            }
        }

        assertPermanentCount(playerA, "Rite of the Raging Storm", 1);
        assertPermanentCount(playerB, "Lightning Rager", 2);
        assertTappedCount("Lightning Rager", true, 0);
    }

    /**
     * Gatherer Ruling: 10/1/2005: Planeswalkers will enter the battlefield with
     * double the normal amount of loyalty counters. However, if you activate an
     * ability whose cost has you put loyalty counters on a planeswalker, the
     * number you put on isn't doubled. This is because those counters are put
     * on as a cost, not as an effect.
     */
    @Test
    public void test_LoyaltyCounters_DoubleSeason() {
        // planeswalker, 2 starting loyalty
        // +1: Draw a card, then discard a card at random.
        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season");
        addCard(Zone.BATTLEFIELD, playerA, "Tibalt, the Fiend-Blooded");

        // 2 starting loyalty
        // on etb: x2 from double season
        int onEtbCount = 2 * 2;
        checkPermanentCounters("etb counters", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tibalt, the Fiend-Blooded", CounterType.LOYALTY, onEtbCount);

        // on +1 cost: nothing from double season
        int onCostCount = 1;
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Draw a card, then discard a card at random.");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertCounterCount("Tibalt, the Fiend-Blooded", CounterType.LOYALTY, onEtbCount + onCostCount);
    }

    @Test
    public void test_LoyaltyCounters_Pir() {
        // If one or more counters would be put on a permanent your team controls, that many plus one of each of those
        // kinds of counters are put on that permanent instead.
        addCard(Zone.BATTLEFIELD, playerA, "Pir, Imaginative Rascal");
        //
        // planeswalker, 2 starting loyalty
        // +1: Draw a card, then discard a card at random.
        addCard(Zone.BATTLEFIELD, playerA, "Tibalt, the Fiend-Blooded");

        // 2 starting loyalty
        // on etb: +1 from pir
        int onEtbCount = 2 + 1;
        checkPermanentCounters("etb counters", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tibalt, the Fiend-Blooded", CounterType.LOYALTY, onEtbCount);

        // on +1 cost: +1 from pir
        int onCostCount = 1 + 1;
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Draw a card, then discard a card at random.");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertCounterCount("Tibalt, the Fiend-Blooded", CounterType.LOYALTY, onEtbCount + onCostCount);
    }

    /**
     * +1 cost is not affected by double, but replace event like Pir,
     * Imaginative Rascal will be affected
     * https://github.com/magefree/mage/issues/5802
     */
    @Test
    public void test_LoyaltyCounters_SeasonAndPir_SeasonFirst() {
        /*
        details explain from https://www.reddit.com/r/mtgrules/comments/aql1it/doubling_season_and_pir_imaginative_rascal/

        When you activate Tezzeret's ability to make Thopters, you'll put on 4.
        In this case, Doubling Season can't apply initially because it's a cost and not an effect,
        but Pir can apply making it 2 and then that lets Season in the door to make it 4.

        Related rules:
        616.1
        If two or more replacement and/or prevention effects are attempting to modify the way an event affects an
        object or player, the affected object’s controller (or its owner if it has no controller) or the affected
        player chooses one to apply, following the steps listed below [..].
        616.1e
        Once the chosen effect has been applied, this process is repeated (taking into account only replacement or
        prevention effects that would now be applicable) until there are no more left to apply.
        614.16
        Some replacement effects apply “if an effect would create one or more tokens” or “if an effect would
        put one or more counters on a permanent.” These replacement effects apply if the effect of a resolving
        spell or ability creates a token or puts a counter on a permanent, and they also apply if another replacement
        or prevention effect does so, even if the original event being modified wasn’t itself an effect.
         */

        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season");
        //
        // If one or more counters would be put on a permanent your team controls, that many plus one of each of those
        // kinds of counters are put on that permanent instead.
        addCard(Zone.BATTLEFIELD, playerA, "Pir, Imaginative Rascal");
        // planeswalker, 4 starting loyalty
        // +1: Exile the top card of your library. You may play it this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Fire Artisan");

        // choose etb replacement effects (from season and pir)
        setChoice(playerA, "Doubling Season");

        // 4 starting loyalty
        // on etb: x2 from double season, +1 from pir
        int onEtbCount = 4 * 2 + 1;
        checkPermanentCounters("etb counters", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chandra, Fire Artisan", CounterType.LOYALTY, onEtbCount);

        // +1 cost (no double effect on cost, +1 effect from pir, double effect after pir's effect)
        int onCostCount = (1 + 1) * 2;
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Chandra, Fire Artisan", CounterType.LOYALTY, onEtbCount + onCostCount);
    }

    @Test
    public void test_LoyaltyCounters_SeasonAndPir_PirFirst() {
        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season");
        //
        // If one or more counters would be put on a permanent your team controls, that many plus one of each of those
        // kinds of counters are put on that permanent instead.
        addCard(Zone.BATTLEFIELD, playerA, "Pir, Imaginative Rascal");
        // planeswalker, 4 starting loyalty
        // +1: Exile the top card of your library. You may play it this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Fire Artisan");

        // choose etb replacement effects (from season and pir)
        setChoice(playerA, "Pir, Imaginative Rascal");

        // 4 starting loyalty
        // on etb: +1 from pir, x2 from double season
        int onEtbCount = (4 + 1) * 2;
        checkPermanentCounters("etb counters", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chandra, Fire Artisan", CounterType.LOYALTY, onEtbCount);

        // +1 cost (no double effect on cost, +1 effect from pir, double effect after pir's effect)
        int onCostCount = (1 + 1) * 2;
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Chandra, Fire Artisan", CounterType.LOYALTY, onEtbCount + onCostCount);
    }
}
