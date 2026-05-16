package org.mage.test.AI.basic;

import mage.player.ai.AiStrategyScore;
import mage.player.ai.score.GameStateEvaluator2;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.UUID;

public class MadScoreSafetyAITest {

    private static final int MAX_REAL_SCORE = Integer.MAX_VALUE - 1;
    private static final int MIN_REAL_SCORE = Integer.MIN_VALUE + 1;

    @Test
    public void playerEvaluateScoreSaturatesTotals() {
        GameStateEvaluator2.PlayerEvaluateScore positive = new GameStateEvaluator2.PlayerEvaluateScore(
                UUID.randomUUID(),
                Integer.MAX_VALUE,
                Integer.MAX_VALUE,
                Integer.MAX_VALUE,
                0,
                0,
                0
        );
        Assert.assertEquals(MAX_REAL_SCORE, positive.getTotalScore());

        GameStateEvaluator2.PlayerEvaluateScore negative = new GameStateEvaluator2.PlayerEvaluateScore(
                UUID.randomUUID(),
                0,
                0,
                0,
                Integer.MAX_VALUE,
                Integer.MAX_VALUE,
                Integer.MAX_VALUE
        );
        Assert.assertEquals(-MAX_REAL_SCORE, negative.getTotalScore());
    }

    @Test
    public void strategyScoreSaturatesAdjustedScore() {
        AiStrategyScore score = AiStrategyScore.of(
                Integer.MAX_VALUE,
                100,
                100,
                Collections.emptyList()
        );

        Assert.assertEquals(MAX_REAL_SCORE, score.getAdjustedScore());
    }
}
