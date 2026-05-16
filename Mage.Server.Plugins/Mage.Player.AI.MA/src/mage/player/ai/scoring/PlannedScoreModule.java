package mage.player.ai.scoring;

import mage.player.ai.AiStrategyScore;

/**
 * Placeholder for a scoring module whose boundary is reserved but whose signal is not active yet.
 */
public abstract class PlannedScoreModule implements AiScoreModule {

    private final String name;

    protected PlannedScoreModule(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AiStrategyScore evaluate(AiScoringContext context) {
        return AiStrategyScore.none(context == null ? 0 : context.getBaseScore());
    }
}
