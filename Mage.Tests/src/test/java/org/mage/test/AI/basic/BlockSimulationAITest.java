package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
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

        // ai must block
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Arbor Elf", 1);
    }

    @Test
    public void test_Block_1_small_attacker_vs_1_small_blocker() {
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf", 1); // 1/1

        attack(1, playerA, "Arbor Elf");

        // ai must block
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Arbor Elf", 1);
        assertGraveyardCount(playerB, "Arbor Elf", 1);
    }

    @Test
    public void test_Block_1_big_attacker_vs_1_small_blocker() {
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf", 1); // 1/1

        attack(1, playerA, "Balduvian Bears");

        // ai must not block
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);

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

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Balduvian Bears", 1);
        assertDamageReceived(playerB, "Spectral Bears", 2);
    }

    // TODO: add tests with multi blocker requirement effects
    // TODO: add tests for DeathtouchAbility
    // TODO: add tests for FirstStrikeAbility
    // TODO: add tests for DoubleStrikeAbility
    // TODO: add tests for IndestructibleAbility
    // TODO: add tests for FlyingAbility
    // TODO: add tests for ReachAbility
    // TODO: add tests for ExaltedAbility???
}
