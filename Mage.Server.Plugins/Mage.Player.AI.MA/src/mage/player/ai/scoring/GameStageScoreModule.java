package mage.player.ai.scoring;

import mage.abilities.Ability;
import mage.game.Game;
import mage.player.ai.AiStrategyScore;

import java.util.Collections;
import java.util.UUID;

public final class GameStageScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.gameStage.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.gameStage.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.gameStage.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public GameStageScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "game-stage";
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
        int turn = game.getTurnNum();
        int opponents = AiScoreSupport.countLivingOpponents(game, playerId);
        int raw = 0;
        String detail = null;
        if (turn <= 4 && AiScoreSupport.hasBadOutcome(action) && opponents > 2) {
            raw = -8;
            detail = "early FFA interaction is usually lower urgency";
        } else if (turn >= 10 && AiScoreSupport.targetsOpponent(game, action, playerId)) {
            raw = 8;
            detail = "late-game actions should convert pressure";
        }
        if (raw == 0) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        raw = AiScoreSupport.clamp(raw, AiScoreSupport.moduleCap(MAX_MODIFIER_PROPERTY, 12));
        return AiStrategyScore.of(
                context.getBaseScore(),
                raw,
                AiScoreSupport.apply(raw, globalApplyModifiers, APPLY_PROPERTY),
                Collections.singletonList(new AiStrategyScore.Contribution("game-stage:context", raw, detail))
        );
    }
}
