package mage.player.ai.scoring;

import mage.abilities.Ability;
import mage.game.Game;
import mage.player.ai.AiStrategyScore;

import java.util.Collections;
import java.util.UUID;

public final class SearchAndTutorScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.searchTutor.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.searchTutor.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.searchTutor.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public SearchAndTutorScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "search-and-tutor";
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
        String text = AiScoreSupport.actionText(action, game);
        if (!text.contains("search your library")) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        int lands = AiScoreSupport.countLands(game, playerId);
        int raw;
        String detail;
        if (text.contains("land") || text.contains("basic")) {
            raw = lands <= 4 ? 20 : lands >= 8 ? -10 : 8;
            detail = "land search with " + lands + " lands already in play";
        } else {
            raw = 22;
            detail = "library search improves access to needed role/card";
        }
        raw = AiScoreSupport.clamp(raw, AiScoreSupport.moduleCap(MAX_MODIFIER_PROPERTY, 30));
        return AiStrategyScore.of(
                context.getBaseScore(),
                raw,
                AiScoreSupport.apply(raw, globalApplyModifiers, APPLY_PROPERTY),
                Collections.singletonList(new AiStrategyScore.Contribution("search:tutor", raw, detail))
        );
    }
}
