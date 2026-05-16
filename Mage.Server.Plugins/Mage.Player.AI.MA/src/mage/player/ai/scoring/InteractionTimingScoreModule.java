package mage.player.ai.scoring;

import mage.abilities.Ability;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.player.ai.AiStrategyScore;
import mage.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class InteractionTimingScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.interactionTiming.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.interactionTiming.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.interactionTiming.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public InteractionTimingScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "interaction-timing";
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

        List<AiStrategyScore.Contribution> contributions = new ArrayList<>();
        if (AiScoreSupport.hasBadOutcome(action)) {
            addStackTiming(contributions, game, action);
            addLowValueInteraction(contributions, game, action, playerId);
        }

        int rawModifier = contributions.stream().mapToInt(AiStrategyScore.Contribution::getValue).sum();
        rawModifier = AiScoreSupport.clamp(rawModifier, AiScoreSupport.moduleCap(MAX_MODIFIER_PROPERTY, 35));
        if (rawModifier == 0 && contributions.isEmpty()) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        return AiStrategyScore.of(
                context.getBaseScore(),
                rawModifier,
                AiScoreSupport.apply(rawModifier, globalApplyModifiers, APPLY_PROPERTY),
                contributions
        );
    }

    private static void addStackTiming(List<AiStrategyScore.Contribution> contributions, Game game, Ability action) {
        for (Target target : action.getAllSelectedTargets()) {
            for (UUID targetId : target.getTargets()) {
                StackObject stackObject = game.getStack().getStackObject(targetId);
                if (stackObject != null) {
                    contributions.add(new AiStrategyScore.Contribution(
                            "interaction-timing:stack",
                            30,
                            "interaction answers stack object " + stackObject.getName()
                    ));
                    return;
                }
            }
        }
    }

    private static void addLowValueInteraction(List<AiStrategyScore.Contribution> contributions,
                                               Game game,
                                               Ability action,
                                               UUID playerId) {
        if (AiScoreSupport.isCombatStep(game) || game.getStack().size() > 0) {
            return;
        }
        UUID targetController = AiScoreSupport.findFirstTargetedOpponent(game, action, playerId);
        if (targetController == null) {
            return;
        }
        int priority = 0;
        for (Target target : action.getAllSelectedTargets()) {
            for (UUID targetId : target.getTargets()) {
                if (targetController.equals(FfaScoringUtil.getTargetController(game, targetId))) {
                    priority = Math.max(priority, FfaScoringUtil.estimateTargetPriority(game, targetId));
                }
            }
        }
        int tableThreat = FfaTableSnapshot.fromGame(game, playerId).getOpponent(targetController) == null
                ? 0
                : FfaTableSnapshot.fromGame(game, playerId).getOpponent(targetController).getScore();
        if (priority > 0 && priority < 14 && tableThreat < 18) {
            contributions.add(new AiStrategyScore.Contribution(
                    "interaction-timing:hold",
                    -16,
                    "low-value interaction outside combat/stack; target priority " + priority
            ));
        }
    }
}
