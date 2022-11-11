package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseAI;

/**
 * @author JayDi85
 */
public class AttackAndBlockByAITest extends CardTestPlayerBaseAI {

    // only PlayerA is AI controlled

    // Trove of Temptation
    // Each opponent must attack you or a planeswalker you control with at least one creature each combat if able.

    @Test
    public void test_Attack_2_big_vs_0() {
        // 2 x 2/2 vs 0 - can't lose any attackers
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 2); // 2/2

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2 - 2);
    }

    @Test
    public void test_Attack_2_big_vs_1_small() {
        // 2 x 2/2 vs 1 x 1/1 - can't lose any attackers
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 2); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf", 1); // 1/1

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2 - 2);
    }

    @Test
    public void test_Attack_1_big_vs_2_small() {
        // 1 x 2/2 vs 2 x 1/1 - can lose 1 attacker, but will kill more opponents
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf", 2); // 1/1

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_Attack_2_big_vs_2_small() {
        // 2 x 2/2 vs 2 x 1/1 - can lose 1 attacker, but will kill more opponents
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 2); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf", 2); // 1/1

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2 - 2);
    }

    @Test
    public void test_Attack_1_small_vs_0() {
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf", 1); // 1/1

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 1);
    }

    @Test
    public void test_Attack_1_small_vs_1_big() {
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20); // no attacks
    }

    @Test
    public void test_Attack_2_small_vs_1_big() {
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf", 2); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20); // no attacks
    }

    @Test
    public void test_Attack_15_small_vs_1_big_kill_stike() {
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 15); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Ancient Brontodon", 1); // 9/9

        block(1, playerB, "Ancient Brontodon", "Balduvian Bears");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - ((15 - 1) * 2)); // one will be blocked
    }

    @Test
    @Ignore // TODO: add massive attack vs small amount of blockers
    public void test_Attack_10_small_vs_1_big_massive_strike() {
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 10); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Ancient Brontodon", 1); // 9/9

        block(1, playerB, "Ancient Brontodon", "Balduvian Bears");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - ((10 - 1) * 2)); // one will be blocked
    }

    @Test
    public void test_ForceAttack_1_small_vs_1_big_a() {
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Brigand", 1); // 1/1, force to attack
        addCard(Zone.BATTLEFIELD, playerB, "Ancient Brontodon", 1); // 9/9

        block(1, playerB, "Ancient Brontodon", "Goblin Brigand");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Goblin Brigand", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void test_ForceAttack_2_small_vs_2_big() {
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Brigand", 2); // 1/1, force to attack
        addCard(Zone.BATTLEFIELD, playerB, "Ancient Brontodon", 2); // 9/9

        block(1, playerB, "Ancient Brontodon:0", "Goblin Brigand:0");
        block(1, playerB, "Ancient Brontodon:1", "Goblin Brigand:1");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Goblin Brigand", 2);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    @Ignore // TODO: need to fix Trove of Temptation effect (player must attack by one creature)
    public void test_ForceAttack_1_small_vs_1_big_b() {
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Ancient Brontodon", 1); // 9/9
        // Trove of Temptation
        // Each opponent must attack you or a planeswalker you control with at least one creature each combat if able.
        addCard(Zone.BATTLEFIELD, playerB, "Trove of Temptation", 1); // 9/9

        block(1, playerB, "Ancient Brontodon", "Arbor Elf");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Arbor Elf", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void test_Attack_1_with_counters_vs_1() {
        // chainbreaker real stats is 1/1, it's can be saftly attacked
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Chainbreaker", 1); // 3/3, but with 2x -1/-1 counters

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2);
    }
}
