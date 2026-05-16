package mage.player.ai.scoring;

import mage.game.Game;
import mage.player.ai.AiStrategyScore;

import java.util.Collections;
import java.util.UUID;

public final class ThreatProjectionScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.threatProjection.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.threatProjection.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.threatProjection.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public ThreatProjectionScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "threat-projection";
    }

    @Override
    public AiStrategyScore evaluate(AiScoringContext context) {
        if (context == null) {
            return AiStrategyScore.none(0);
        }
        if (!AiScoreSupport.isEnabled(ENABLED_PROPERTY)) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        Game before = context.getDecisionGame();
        Game after = context.getFinalGame();
        UUID playerId = context.getPlayerId();
        if (before == null || after == null || playerId == null || AiScoreSupport.countLivingOpponents(after, playerId) <= 1) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        int beforeThreat = AiScoreSupport.maxOpponentThreat(before, playerId);
        int afterThreat = AiScoreSupport.maxOpponentThreat(after, playerId);
        int ownDefense = AiScoreSupport.totalCreaturePower(after, playerId) + AiScoreSupport.countCreatures(after, playerId) * 2;
        int projectedGap = afterThreat - ownDefense;
        if (projectedGap <= 12 && afterThreat <= beforeThreat + 4) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        int raw = -Math.min(AiScoreSupport.moduleCap(MAX_MODIFIER_PROPERTY, 35), Math.max(8, projectedGap / 2));
        return AiStrategyScore.of(
                context.getBaseScore(),
                raw,
                AiScoreSupport.apply(raw, globalApplyModifiers, APPLY_PROPERTY),
                Collections.singletonList(new AiStrategyScore.Contribution(
                        "threat-projection:next-turn",
                        raw,
                        "strongest opponent pressure " + beforeThreat + " -> " + afterThreat + ", defense " + ownDefense
                ))
        );
    }
}
