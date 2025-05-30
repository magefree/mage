package org.mage.test.cards.targets;

import mage.constants.Outcome;
import org.junit.Test;

/**
 * Testing targets selection on resolve (player.choose) for AI
 * <p>
 * AI must use logic like:
 * - for good effects - choose as much as possible targets
 * - for bad effects - choose as much as lower targets
 *
 * @author JayDi85
 */
public class TargetsSelectionOnResolveAITest extends TargetsSelectionBaseTest {

    @Test
    public void test_OnResolve_Good_0_of_0() {
        run_PlayerChoose_OnResolve_AI(Outcome.Benefit, 0, 3, 0, 0);
    }

    @Test
    public void test_OnResolve_Good_1_of_1() {
        run_PlayerChoose_OnResolve_AI(Outcome.Benefit, 0, 3, 1, 1);
    }

    @Test
    public void test_OnResolve_Good_2_of_2() {
        run_PlayerChoose_OnResolve_AI(Outcome.Benefit, 0, 3, 2, 2);
    }

    @Test
    public void test_OnResolve_Good_3_of_3() {
        run_PlayerChoose_OnResolve_AI(Outcome.Benefit, 0, 3, 3, 3);
    }

    @Test
    public void test_OnResolve_Good_3_of_10() {
        run_PlayerChoose_OnResolve_AI(Outcome.Benefit, 0, 3, 3, 10);
    }

    @Test
    public void test_OnResolve_Bad_0_of_0() {
        run_PlayerChoose_OnResolve_AI(Outcome.Detriment, 0, 3, 0, 0);
    }

    @Test
    public void test_OnResolve_Bad_0_of_1() {
        run_PlayerChoose_OnResolve_AI(Outcome.Detriment, 0, 3, 0, 1);
    }

    @Test
    public void test_OnResolve_Bad_0_of_2() {
        run_PlayerChoose_OnResolve_AI(Outcome.Detriment, 0, 3, 0, 2);
    }

    @Test
    public void test_OnResolve_Bad_0_of_3() {
        run_PlayerChoose_OnResolve_AI(Outcome.Detriment, 0, 3, 0, 3);
    }

    @Test
    public void test_OnResolve_Bad_0_of_10() {
        run_PlayerChoose_OnResolve_AI(Outcome.Detriment, 0, 3, 0, 10);
    }
}
