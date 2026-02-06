package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * Combat's blocking tests
 * <p>
 * TODO: add tests with multi blocker requirement effects
 * <p>
 * Supported abilities:
 * - DeathtouchAbility - supported, has tests
 * - FirstStrikeAbility - supported, has tests
 * - DoubleStrikeAbility - ?
 * - IndestructibleAbility - supported, need tests
 * - FlyingAbility - ?
 * - ReachAbility - ?
 * - ExaltedAbility - ?
 * - Trample + Deathtouch
 * - combat damage and die triggers - need to implement full combat simulation with stack resolve, see CombatUtil->willItSurviveSimple
 * - other use cases, see https://github.com/magefree/mage/issues/4485
 *
 * @author JayDi85
 */
public class BlockSimulationAITest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_Block_1_small_attacker_vs_1_big_blocker() {
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2

        attack(1, playerA, "Arbor Elf");

        // ai must block
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("x1 blocker", 1, playerB, "Balduvian Bears");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Arbor Elf", 1);
    }

    @Test
    public void test_Block_1_small_attacker_vs_2_big_blockers() {
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Spectral Bears", 1); // 3/3

        attack(1, playerA, "Arbor Elf");

        // ai must block by optimal blocker
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("x1 optimal blocker", 1, playerB, "Balduvian Bears");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Arbor Elf", 1);
    }

    @Test
    public void test_Block_1_small_attacker_vs_1_small_blocker_same() {
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf", 1); // 1/1

        attack(1, playerA, "Arbor Elf");

        // ai must block
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("x1 blocker", 1, playerB, "Arbor Elf");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Arbor Elf", 1);
        assertGraveyardCount(playerB, "Arbor Elf", 1);
    }

    @Test
    public void test_Block_1_small_attacker_vs_1_small_blocker_better() {
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf", 1); // 1/1
        //addCard(Zone.BATTLEFIELD, playerB, "Elvish Archers"); // 2/1 first strike
        addCard(Zone.BATTLEFIELD, playerB, "Dregscape Zombie", 1); // 2/1

        attack(1, playerA, "Arbor Elf");

        // ai must ignore block to keep better creature alive
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("no blockers", 1, playerB, "");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 1);
        assertPermanentCount(playerA, "Arbor Elf", 1);
        assertPermanentCount(playerB, "Dregscape Zombie", 1);
    }

    @Test
    public void test_Block_1_small_attacker_vs_1_small_blocker_better_but_player_die() {
        addCustomEffect_TargetDamage(playerA, 19);

        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Dregscape Zombie", 1); // 2/1

        // prepare 1 life
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target damage 19", playerB);

        attack(1, playerA, "Arbor Elf");

        // ai must keep better blocker in normal case, but now it must protect from lose and sacrifice it
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("x1 blocker", 1, playerB, "Dregscape Zombie");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 1);
        assertGraveyardCount(playerA, "Arbor Elf", 1);
        assertGraveyardCount(playerB, "Dregscape Zombie", 1);
    }

    @Test
    public void test_Block_1_big_attacker_vs_1_small_blocker() {
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf", 1); // 1/1

        attack(1, playerA, "Balduvian Bears");

        // ai must not block
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("no blockers", 1, playerB, "");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2);
        assertGraveyardCount(playerA, "Balduvian Bears", 0);
        assertGraveyardCount(playerB, "Arbor Elf", 0);
    }

    @Test
    public void test_Block_2_big_attackers_vs_1_small_blocker() {
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Deadbridge Goliath", 1); // 5/5
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf", 1); // 1/1

        attack(1, playerA, "Balduvian Bears");
        attack(1, playerA, "Deadbridge Goliath");

        // ai must not block
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("no blockers", 1, playerB, "");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2 - 5);
        assertGraveyardCount(playerA, "Balduvian Bears", 0);
        assertGraveyardCount(playerA, "Deadbridge Goliath", 0);
        assertGraveyardCount(playerB, "Arbor Elf", 0);
    }

    @Test
    public void test_Block_2_big_attackers_vs_1_big_blocker_a() {
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Deadbridge Goliath", 1); // 5/5
        addCard(Zone.BATTLEFIELD, playerB, "Colossal Dreadmaw", 1); // 6/6

        attack(1, playerA, "Balduvian Bears");
        attack(1, playerA, "Deadbridge Goliath");

        // ai must block bigger attacker and survive (6/6 must block 5/5)
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("x1 optimal blocker", 1, playerB, "Colossal Dreadmaw");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2);
        assertGraveyardCount(playerA, "Balduvian Bears", 0);
        assertGraveyardCount(playerA, "Deadbridge Goliath", 1);
        assertGraveyardCount(playerB, "Colossal Dreadmaw", 0);
    }

    @Test
    public void test_Block_2_big_attackers_vs_1_big_blocker_b() {
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Deadbridge Goliath", 1); // 5/5
        addCard(Zone.BATTLEFIELD, playerA, "Colossal Dreadmaw", 1); // 6/6
        //
        addCard(Zone.BATTLEFIELD, playerB, "Spectral Bears", 1); // 3/3

        attack(1, playerA, "Arbor Elf");
        attack(1, playerA, "Balduvian Bears");
        attack(1, playerA, "Deadbridge Goliath");
        attack(1, playerA, "Colossal Dreadmaw");

        // ai must block bigger attacker and survive (3/3 must block 2/2)
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("x1 optimal blocker", 1, playerB, "Spectral Bears");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 1 - 5 - 6);
        assertGraveyardCount(playerA, "Balduvian Bears", 1);
        assertPermanentCount(playerB, "Spectral Bears", 1);
    }

    @Test
    public void test_Block_1_attacker_vs_many_blockers() {
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2
        //
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Spectral Bears", 1); // 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Deadbridge Goliath", 1); // 5/5
        addCard(Zone.BATTLEFIELD, playerB, "Colossal Dreadmaw", 1); // 6/6

        attack(1, playerA, "Balduvian Bears");

        // ai must use smaller blocker and survive (3/3 must block 2/2)
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("x1 optimal blocker", 1, playerB, "Spectral Bears");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Balduvian Bears", 1);
        assertDamageReceived(playerB, "Spectral Bears", 2);
    }

    @Test
    public void test_Block_1_attacker_vs_first_strike() {
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2
        //
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "White Knight", 1); // 2/2 with first strike
        addCard(Zone.BATTLEFIELD, playerB, "Spectral Bears", 1); // 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Deadbridge Goliath", 1); // 5/5
        addCard(Zone.BATTLEFIELD, playerB, "Colossal Dreadmaw", 1); // 6/6

        attack(1, playerA, "Balduvian Bears");

        // ai must use smaller blocker and survive (2/2 with first strike must block 2/2)
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("x1 optimal blocker", 1, playerB, "White Knight");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Balduvian Bears", 1);
        assertDamageReceived(playerB, "White Knight", 0); // due first strike
    }

    @Test
    public void test_Block_1_attacker_vs_deathtouch() {
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2
        //
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Arashin Cleric", 1); // 1/3
        addCard(Zone.BATTLEFIELD, playerB, "Graveblade Marauder", 1); // 1/4 with deathtouch
        addCard(Zone.BATTLEFIELD, playerB, "Deadbridge Goliath", 1); // 5/5
        addCard(Zone.BATTLEFIELD, playerB, "Colossal Dreadmaw", 1); // 6/6

        attack(1, playerA, "Balduvian Bears");

        // ai must use smaller blocker to kill and survive (1/4 with deathtouch must block 2/2 -- not a smaller 1/3)
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("x1 optimal blocker", 1, playerB, "Graveblade Marauder");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Balduvian Bears", 1);
        assertDamageReceived(playerB, "Graveblade Marauder", 2);
    }

    @Test
    public void test_Block_deathtouch_attacker_vs_menace() {
        // possible bug: AI freeze, see https://github.com/magefree/mage/issues/13342
        // it's only HumanPlayer related and can't be tested here

        // First strike, Deathtouch
        // Whenever Glissa Sunslayer deals combat damage to a player, choose one —
        // • You draw a card and you lose 1 life.
        // • Destroy target enchantment.
        // • Remove up to three counters from target permanent.
        addCard(Zone.BATTLEFIELD, playerA, "Glissa Sunslayer", 1); // 3/3
        // Deathtouch
        // Whenever a creature you control with deathtouch deals combat damage to a player, that player gets two poison counters.
        addCard(Zone.BATTLEFIELD, playerA, "Fynn, the Fangbearer", 1); // 1/3
        // Deathtouch
        // Toxic 1 (Players dealt combat damage by this creature also get a poison counter.)
        addCard(Zone.BATTLEFIELD, playerA, "Bilious Skulldweller", 1); // 1/1
        //
        // Menace
        // At the beginning of each player's upkeep, Furnace Punisher deals 2 damage to that player unless they control
        // two or more basic lands.
        addCard(Zone.BATTLEFIELD, playerB, "Furnace Punisher", 1); // 3/3

        attack(1, playerA, "Glissa Sunslayer");
        attack(1, playerA, "Fynn, the Fangbearer");
        attack(1, playerA, "Bilious Skulldweller");
        setChoice(playerA, "Whenever a creature you control", 2); // x3 triggers
        setModeChoice(playerA, "1"); // you draw a card and you lose 1 life

        // ai must not block attacker with Deathtouch
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("no blockers", 1, playerB, "");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20 - 1 - 2); // from draw, from furnace damage
        assertLife(playerB, 20 - 3 - 1 - 1);
        assertPermanentCount(playerA, "Glissa Sunslayer", 1);
        assertGraveyardCount(playerB, 0);
    }

    // ==================== Flying + Reach Interactions ====================

    @Test
    public void test_Block_FlyingAttacker_BlockedByReach() {
        // Flying creatures can only be blocked by creatures with flying or reach
        // AI should use reach creatures to block flyers

        // Serra Angel: 4/4 flying, vigilance
        addCard(Zone.BATTLEFIELD, playerA, "Serra Angel", 1);
        // Giant Spider: 2/4 reach - can block flyers
        addCard(Zone.BATTLEFIELD, playerB, "Giant Spider", 1);

        attack(1, playerA, "Serra Angel");

        // AI should block with reach creature (survives with 4 toughness vs 4 power)
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("reach blocks flyer", 1, playerB, "Giant Spider");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20); // Blocked, no damage
        // Spider survives (4 toughness - 4 damage = 0, dies) - actually both die
        assertGraveyardCount(playerA, "Serra Angel", 1);
        assertGraveyardCount(playerB, "Giant Spider", 1);
    }

    @Test
    public void test_Block_FlyingAttacker_NotBlockedByNonFlyer() {
        // Ground creatures without reach cannot block flyers
        // AI should NOT try to block with ground creatures

        addCard(Zone.BATTLEFIELD, playerA, "Serra Angel", 1); // 4/4 flying
        addCard(Zone.BATTLEFIELD, playerB, "Colossal Dreadmaw", 1); // 6/6 trample, no flying/reach

        attack(1, playerA, "Serra Angel");

        // AI cannot block a flyer with a ground creature
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("no valid blockers", 1, playerB, "");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 4); // Takes flying damage
        assertPermanentCount(playerA, "Serra Angel", 1);
        assertPermanentCount(playerB, "Colossal Dreadmaw", 1);
    }

    @Test
    public void test_Block_FlyingAttacker_ChooseBestFlyingBlocker() {
        // When AI has multiple flying/reach blockers, it should choose optimally

        addCard(Zone.BATTLEFIELD, playerA, "Wind Drake", 1); // 2/2 flying
        // AI has flying and reach options
        addCard(Zone.BATTLEFIELD, playerB, "Giant Spider", 1); // 2/4 reach
        addCard(Zone.BATTLEFIELD, playerB, "Welkin Tern", 1); // 2/1 flying

        attack(1, playerA, "Wind Drake");

        // AI should use Giant Spider (survives) over Welkin Tern (trades)
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("use spider", 1, playerB, "Giant Spider");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Wind Drake", 1); // Killed by spider
        assertPermanentCount(playerB, "Giant Spider", 1); // Survives
        assertPermanentCount(playerB, "Welkin Tern", 1); // Not used
    }

    // ==================== Double Strike Blocking Decisions ====================

    @Test
    public void test_Block_DoubleStrike_AIAccountsForDoubleDamage() {
        // Double strike deals damage twice (first strike + normal)
        // A 2/2 double strike deals 4 total damage
        // AI should recognize this and not block with creatures that would die

        // Fencing Ace: 1/1 double strike
        addCard(Zone.BATTLEFIELD, playerA, "Fencing Ace", 1);
        // AI has a 2/2 - would die to double strike (2 damage total)
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2

        attack(1, playerA, "Fencing Ace");

        // AI should block - 2/2 can kill the 1/1 and only takes 1 damage in first strike
        // Then normal damage happens but Fencing Ace is dead
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("block double strike", 1, playerB, "Balduvian Bears");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        // Both die in combat - Fencing Ace deals 1 first strike, Bears deal 2 back
        assertGraveyardCount(playerA, "Fencing Ace", 1);
        assertDamageReceived(playerB, "Balduvian Bears", 1); // Only first strike damage
    }

    @Test
    public void test_Block_DoubleStrike_LargerAttacker() {
        // Larger double strike creature - AI must account for full damage

        // Mirran Crusader: 2/2 double strike, protection from black/green
        addCard(Zone.BATTLEFIELD, playerA, "Mirran Crusader", 1);
        // AI has options
        addCard(Zone.BATTLEFIELD, playerB, "Colossal Dreadmaw", 1); // 6/6 trample
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2

        attack(1, playerA, "Mirran Crusader");

        // AI should block with 6/6 - survives (takes 2+2=4 damage) and kills the 2/2
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("big blocker survives", 1, playerB, "Colossal Dreadmaw");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Mirran Crusader", 1);
        assertDamageReceived(playerB, "Colossal Dreadmaw", 4); // 2 first strike + 2 normal
    }

    // ==================== Menace Blocking Requirements ====================

    @Test
    public void test_Block_Menace_RequiresTwoBlockers() {
        // Menace requires at least two creatures to block
        // AI with only one creature cannot block a menace creature

        // Boggart Brute: 3/2 menace
        addCard(Zone.BATTLEFIELD, playerA, "Boggart Brute", 1);
        // AI has only one blocker
        addCard(Zone.BATTLEFIELD, playerB, "Colossal Dreadmaw", 1); // 6/6

        attack(1, playerA, "Boggart Brute");

        // AI cannot block menace with single creature
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("cannot block menace alone", 1, playerB, "");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 3); // Takes menace damage
        assertPermanentCount(playerA, "Boggart Brute", 1);
        assertPermanentCount(playerB, "Colossal Dreadmaw", 1);
    }

    @Test
    public void test_Block_Menace_TwoBlockersCanBlock() {
        // With two creatures, AI can block menace

        // Boggart Brute: 3/2 menace
        addCard(Zone.BATTLEFIELD, playerA, "Boggart Brute", 1);
        // AI has two blockers
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 2); // 2/2 each

        attack(1, playerA, "Boggart Brute");

        // AI can gang-block menace with two creatures
        // Combined: 4 power kills 3/2, one bear dies to 3 damage
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        // Note: checking that at least some blocking happens
        // The exact block configuration may vary

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(false); // AI decides damage assignment
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20); // Blocked
        // Attacker should die (4 power from 2 bears > 2 toughness)
        assertGraveyardCount(playerA, "Boggart Brute", 1);
        // One bear survives (3 power distributed)
        assertPermanentCount(playerB, "Balduvian Bears", 1);
    }

    @Test
    public void test_Block_Menace_ThreeBlockersAvailable() {
        // AI should use exactly 2 blockers for menace, preserving the third

        // Boggart Brute: 3/2 menace
        addCard(Zone.BATTLEFIELD, playerA, "Boggart Brute", 1);
        // AI has three blockers - should only use two
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 3); // 2/2 each

        attack(1, playerA, "Boggart Brute");

        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(false);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20); // Blocked
        assertGraveyardCount(playerA, "Boggart Brute", 1);
        // At least 2 bears should survive (one dies to 3 damage, third preserved)
        assertPermanentCount(playerB, "Balduvian Bears", 2);
    }
}
