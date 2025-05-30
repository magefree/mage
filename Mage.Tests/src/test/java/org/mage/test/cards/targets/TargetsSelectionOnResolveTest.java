package org.mage.test.cards.targets;

import org.junit.Test;

/**
 * Testing targets selection on resolve (player.choose)
 * <p>
 * Player can use any logic and choose any number of targets
 *
 * @author JayDi85
 */
public class TargetsSelectionOnResolveTest extends TargetsSelectionBaseTest {

    // no selects

    @Test
    public void test_OnActivate_0_of_0() {
        run_PlayerChoose_OnResolve(0, 0);
    }

    @Test
    public void test_OnActivate_0_of_1() {
        run_PlayerChoose_OnResolve(0, 1);
    }

    @Test
    public void test_OnActivate_0_of_2() {
        run_PlayerChoose_OnResolve(0, 2);
    }

    @Test
    public void test_OnActivate_0_of_3() {
        run_PlayerChoose_OnResolve(0, 3);
    }

    @Test
    public void test_OnActivate_0_of_10() {
        run_PlayerChoose_OnResolve(0, 10);
    }

    // 1 select

    @Test
    public void test_OnActivate_1_of_1() {
        run_PlayerChoose_OnResolve(1, 1);
    }

    @Test
    public void test_OnActivate_1_of_2() {
        run_PlayerChoose_OnResolve(1, 2);
    }

    @Test
    public void test_OnActivate_1_of_3() {
        run_PlayerChoose_OnResolve(1, 3);
    }

    @Test
    public void test_OnActivate_1_of_10() {
        run_PlayerChoose_OnResolve(1, 10);
    }

    // 2 selects

    @Test
    public void test_OnActivate_2_of_2() {
        run_PlayerChoose_OnResolve(2, 2);
    }

    @Test
    public void test_OnActivate_2_of_3() {
        run_PlayerChoose_OnResolve(2, 3);
    }

    @Test
    public void test_OnActivate_2_of_10() {
        run_PlayerChoose_OnResolve(2, 10);
    }

    // 3 selects

    @Test
    public void test_OnActivate_3_of_3() {
        run_PlayerChoose_OnResolve(3, 3);
    }

    @Test
    public void test_OnActivate_3_of_10() {
        run_PlayerChoose_OnResolve(3, 10);
    }
}
