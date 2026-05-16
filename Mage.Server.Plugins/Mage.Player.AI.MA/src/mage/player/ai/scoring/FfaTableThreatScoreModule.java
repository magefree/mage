package mage.player.ai.scoring;

import mage.abilities.Ability;
import mage.game.Game;
import mage.player.ai.AiStrategyScore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class FfaTableThreatScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.ffaThreat.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.ffaThreat.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.ffaThreat.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public FfaTableThreatScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "ffa-table-threat";
    }

    @Override
    public AiStrategyScore evaluate(AiScoringContext context) {
        if (context == null) {
            return AiStrategyScore.none(0);
        }
        if (!Boolean.parseBoolean(System.getProperty(ENABLED_PROPERTY, "true"))) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        Game game = context.getDecisionGame();
        Ability action = context.getAction();
        if (game == null || action == null || context.getPlayerId() == null) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        if (!FfaScoringUtil.isHostileAction(action)) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        UUID targetedOpponentId = FfaScoringUtil.findHighestThreatTargetedOpponent(game, action, context.getPlayerId());
        if (targetedOpponentId == null) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        FfaTableSnapshot snapshot = FfaTableSnapshot.fromGame(game, context.getPlayerId());
        FfaOpponentThreat threat = snapshot.getOpponent(targetedOpponentId);
        if (threat == null || snapshot.getMaxThreatScore() <= 0) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        int maxModifier = Math.max(0, Integer.getInteger(MAX_MODIFIER_PROPERTY, 40));
        int rawModifier = Math.max(1, Math.min(maxModifier,
                (int) Math.round((double) threat.getScore() * maxModifier / snapshot.getMaxThreatScore())));
        int appliedModifier = globalApplyModifiers && Boolean.getBoolean(APPLY_PROPERTY) ? rawModifier : 0;
        int rank = snapshot.getRank(targetedOpponentId);

        List<AiStrategyScore.Contribution> contributions = new ArrayList<>();
        contributions.add(new AiStrategyScore.Contribution(
                "ffa-threat:target",
                rawModifier,
                "target " + threat.getPlayerName()
                        + " rank " + rank + "/" + snapshot.getOpponents().size()
                        + "; " + threat.describe()
        ));
        return AiStrategyScore.of(context.getBaseScore(), rawModifier, appliedModifier, contributions);
    }
}
