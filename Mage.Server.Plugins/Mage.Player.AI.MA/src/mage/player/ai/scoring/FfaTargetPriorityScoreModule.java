package mage.player.ai.scoring;

import mage.abilities.Ability;
import mage.game.Game;
import mage.player.ai.AiStrategyScore;
import mage.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class FfaTargetPriorityScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.ffaTarget.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.ffaTarget.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.ffaTarget.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public FfaTargetPriorityScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "ffa-target-priority";
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
        if (game == null || action == null || context.getPlayerId() == null || !FfaScoringUtil.isHostileAction(action)) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        TargetChoice targetChoice = findBestOpponentTarget(game, action, context.getPlayerId());
        if (targetChoice == null || targetChoice.priority <= 0) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        int maxModifier = Math.max(0, Integer.getInteger(MAX_MODIFIER_PROPERTY, 30));
        int rawModifier = Math.max(1, Math.min(maxModifier, targetChoice.priority));
        int appliedModifier = globalApplyModifiers && Boolean.getBoolean(APPLY_PROPERTY) ? rawModifier : 0;
        List<AiStrategyScore.Contribution> contributions = new ArrayList<>();
        contributions.add(new AiStrategyScore.Contribution(
                "ffa-target:priority",
                rawModifier,
                "target " + FfaScoringUtil.getTargetName(game, targetChoice.targetId)
                        + "; priority " + targetChoice.priority
        ));
        return AiStrategyScore.of(context.getBaseScore(), rawModifier, appliedModifier, contributions);
    }

    private TargetChoice findBestOpponentTarget(Game game, Ability action, UUID playerId) {
        TargetChoice best = null;
        FfaTableSnapshot snapshot = FfaTableSnapshot.fromGame(game, playerId);
        for (Target target : action.getAllSelectedTargets()) {
            for (UUID targetId : target.getTargets()) {
                UUID controllerId = FfaScoringUtil.getTargetController(game, targetId);
                if (snapshot.getOpponent(controllerId) == null) {
                    continue;
                }
                int priority = FfaScoringUtil.estimateTargetPriority(game, targetId);
                if (best == null || priority > best.priority) {
                    best = new TargetChoice(targetId, priority);
                }
            }
        }
        return best;
    }

    private static final class TargetChoice {
        private final UUID targetId;
        private final int priority;

        private TargetChoice(UUID targetId, int priority) {
            this.targetId = targetId;
            this.priority = priority;
        }
    }
}
