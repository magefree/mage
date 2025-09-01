package org.mage.test.cards.targets;

import org.junit.Test;

/**
 * Testing targets selection on activate/cast (player.chooseTarget)
 *
 * @author JayDi85
 */
public class TargetsSelectionOnActivateTest extends TargetsSelectionBaseTest {

    // no selects

    @Test
    public void test_OnActivate_0_of_0() {
        run_PlayerChooseTarget_OnActivate(0, 0);
    }

    @Test
    public void test_OnActivate_0_of_1() {
        run_PlayerChooseTarget_OnActivate(0, 1);
    }

    @Test
    public void test_OnActivate_0_of_2() {
        run_PlayerChooseTarget_OnActivate(0, 2);
    }

    @Test
    public void test_OnActivate_0_of_3() {
        run_PlayerChooseTarget_OnActivate(0, 3);
    }

    @Test
    public void test_OnActivate_0_of_10() {
        run_PlayerChooseTarget_OnActivate(0, 10);
    }

    // 1 select

    @Test
    public void test_OnActivate_1_of_1() {
        run_PlayerChooseTarget_OnActivate(1, 1);
    }

    @Test
    public void test_OnActivate_1_of_2() {
        run_PlayerChooseTarget_OnActivate(1, 2);
    }

    @Test
    public void test_OnActivate_1_of_3() {
        run_PlayerChooseTarget_OnActivate(1, 3);
    }

    @Test
    public void test_OnActivate_1_of_10() {
        run_PlayerChooseTarget_OnActivate(1, 10);
    }

    // 2 selects

    @Test
    public void test_OnActivate_2_of_2() {
        run_PlayerChooseTarget_OnActivate(2, 2);
    }

    @Test
    public void test_OnActivate_2_of_3() {
        run_PlayerChooseTarget_OnActivate(2, 3);
    }

    @Test
    public void test_OnActivate_2_of_10() {
        run_PlayerChooseTarget_OnActivate(2, 10);
    }

    // 3 selects

    @Test
    public void test_OnActivate_3_of_3() {
        run_PlayerChooseTarget_OnActivate(3, 3);
    }

    @Test
    public void test_OnActivate_3_of_10() {
        run_PlayerChooseTarget_OnActivate(3, 10);
    }
}
