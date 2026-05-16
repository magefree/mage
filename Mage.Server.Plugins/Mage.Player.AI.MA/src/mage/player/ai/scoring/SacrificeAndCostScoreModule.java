package mage.player.ai.scoring;

import mage.abilities.Ability;
import mage.game.Game;
import mage.player.ai.AiStrategyScore;

import java.util.Collections;
import java.util.UUID;

public final class SacrificeAndCostScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.sacrificeCost.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.sacrificeCost.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.sacrificeCost.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public SacrificeAndCostScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "sacrifice-and-cost";
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
        if (game == null || action == null || playerId == null) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        String text = AiScoreSupport.actionText(action, game);
        int creatureCount = AiScoreSupport.countCreatures(game, playerId);
        int raw = 0;
        String detail = null;
        if (text.contains("sacrifice") && creatureCount <= 2) {
            raw = -28;
            detail = "sacrifice cost with only " + creatureCount + " creatures";
        } else if (text.contains("discard") && AiScoreSupport.handSize(game, playerId) <= 2) {
            raw = -18;
            detail = "discard cost with low hand size";
        } else if (text.contains("pay") && text.contains("life") && AiScoreSupport.playerLife(game, playerId) <= 10) {
            raw = -18;
            detail = "life payment while life total is low";
        }
        if (raw == 0) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        raw = AiScoreSupport.clamp(raw, AiScoreSupport.moduleCap(MAX_MODIFIER_PROPERTY, 35));
        return AiStrategyScore.of(
                context.getBaseScore(),
                raw,
                AiScoreSupport.apply(raw, globalApplyModifiers, APPLY_PROPERTY),
                Collections.singletonList(new AiStrategyScore.Contribution("cost:risk", raw, detail))
        );
    }
}
