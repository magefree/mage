package mage.player.ai.scoring;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.game.Game;
import mage.player.ai.AiStrategyScore;

import java.util.Collections;
import java.util.UUID;

public final class HandPlanScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.handPlan.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.handPlan.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.handPlan.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public HandPlanScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "hand-plan";
    }

    @Override
    public AiStrategyScore evaluate(AiScoringContext context) {
        if (context == null) {
            return AiStrategyScore.none(0);
        }
        if (!AiScoreSupport.isEnabled(ENABLED_PROPERTY)) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        Game game = context.getDecisionGame();
        Ability action = context.getAction();
        UUID playerId = context.getPlayerId();
        if (game == null || action == null || playerId == null || !AiScoreSupport.isMainPhase(game)) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        int hand = AiScoreSupport.handSize(game, playerId);
        int raw = 0;
        String detail = null;
        if (hand <= 2 && AiScoreSupport.hasOutcome(action, Outcome.DrawCard)) {
            raw = 24;
            detail = "low hand size makes card access valuable";
        } else if (hand >= 6 && AiScoreSupport.developsOwnMana(game, action, playerId)) {
            raw = 12;
            detail = "large hand can convert extra mana";
        }
        if (raw == 0) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        raw = AiScoreSupport.clamp(raw, AiScoreSupport.moduleCap(MAX_MODIFIER_PROPERTY, 28));
        return AiStrategyScore.of(
                context.getBaseScore(),
                raw,
                AiScoreSupport.apply(raw, globalApplyModifiers, APPLY_PROPERTY),
                Collections.singletonList(new AiStrategyScore.Contribution("hand-plan:hand-context", raw, detail))
        );
    }
}
