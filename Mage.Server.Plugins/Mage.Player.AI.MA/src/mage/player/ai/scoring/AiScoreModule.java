package mage.player.ai.scoring;

import mage.player.ai.AiStrategyScore;

/**
 * A named, ordered scoring contributor for top-level AI candidate decisions.
 */
public interface AiScoreModule {

    String getName();

    AiStrategyScore evaluate(AiScoringContext context);

    default void cleanUpOnMatchEnd() {
    }
}
