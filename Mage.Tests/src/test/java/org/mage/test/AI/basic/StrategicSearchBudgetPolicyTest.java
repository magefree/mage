package org.mage.test.AI.basic;

import mage.player.ai.StrategicSearchBudgetPolicy;
import org.junit.Assert;
import org.junit.Test;

public class StrategicSearchBudgetPolicyTest {

    @Test
    public void capsLargeCommanderBoardDepth() {
        StrategicSearchBudgetPolicy.Decision decision = StrategicSearchBudgetPolicy.decide(input(
                true,
                12,
                40,
                0,
                10,
                5000,
                0.20,
                4096
        ));

        Assert.assertEquals(6, decision.getEffectiveDepth());
        Assert.assertTrue(decision.getReasons().contains("search-budget:large-commander-board"));
        Assert.assertTrue(decision.getReasons().contains("search-budget:effective-depth"));
        Assert.assertFalse(decision.isStopForHeapPressure());
    }

    @Test
    public void capsHugeCommanderBoardDepth() {
        StrategicSearchBudgetPolicy.Decision decision = StrategicSearchBudgetPolicy.decide(input(
                true,
                12,
                55,
                0,
                10,
                5000,
                0.20,
                4096
        ));

        Assert.assertEquals(5, decision.getEffectiveDepth());
        Assert.assertTrue(decision.getReasons().contains("search-budget:huge-commander-board"));
    }

    @Test
    public void keepsMinimumDepthUnderLegalActionPressure() {
        StrategicSearchBudgetPolicy.Decision decision = StrategicSearchBudgetPolicy.decide(input(
                true,
                40,
                60,
                30,
                6,
                5000,
                0.20,
                4096
        ));

        Assert.assertEquals(4, decision.getEffectiveDepth());
        Assert.assertTrue(decision.getReasons().contains("search-budget:legal-action-pressure"));
        Assert.assertTrue(decision.getReasons().contains("search-budget:late-game-board"));
    }

    @Test
    public void detectsHeapPressureWithInjectedValues() {
        StrategicSearchBudgetPolicy.Decision decision = StrategicSearchBudgetPolicy.decide(input(
                false,
                5,
                5,
                0,
                8,
                5000,
                0.90,
                1024
        ));

        Assert.assertTrue(decision.isStopForHeapPressure());
        Assert.assertTrue(decision.getReasons().contains("search-budget:heap-pressure"));
        Assert.assertTrue(decision.getReasons().contains("search-budget:heap-stop"));
    }

    @Test
    public void doesNotAffectNormalNonCommanderBoard() {
        StrategicSearchBudgetPolicy.Decision decision = StrategicSearchBudgetPolicy.decide(input(
                false,
                5,
                12,
                0,
                8,
                5000,
                0.20,
                4096
        ));

        Assert.assertEquals(8, decision.getEffectiveDepth());
        Assert.assertEquals(5000, decision.getMaxNodes());
        Assert.assertTrue(decision.getReasons().isEmpty());
        Assert.assertFalse(decision.hasChanges(8, 5000));
    }

    private static StrategicSearchBudgetPolicy.Input input(boolean commander,
                                                           int turnNumber,
                                                           int battlefieldPermanentCount,
                                                           int legalActionCount,
                                                           int maxDepth,
                                                           int maxNodes,
                                                           double usedHeapRatio,
                                                           long freeHeapMb) {
        return new StrategicSearchBudgetPolicy.Input(
                commander,
                turnNumber,
                battlefieldPermanentCount,
                0,
                legalActionCount,
                maxDepth,
                maxNodes,
                4,
                40,
                55,
                true,
                usedHeapRatio,
                freeHeapMb,
                0.82,
                512
        );
    }
}
