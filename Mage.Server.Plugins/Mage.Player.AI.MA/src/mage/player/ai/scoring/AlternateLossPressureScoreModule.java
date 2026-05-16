package mage.player.ai.scoring;

import mage.abilities.Ability;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.AiStrategyScore;
import mage.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class AlternateLossPressureScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.alternateLoss.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.alternateLoss.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.alternateLoss.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public AlternateLossPressureScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "alternate-loss-pressure";
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
        if (game == null || action == null || playerId == null || !AiScoreSupport.hasBadOutcome(action)) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        List<AiStrategyScore.Contribution> contributions = new ArrayList<>();
        for (Target target : action.getAllSelectedTargets()) {
            for (UUID targetId : target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null || permanent.isControlledBy(playerId)) {
                    continue;
                }
                String risk = describeAlternatePressure(permanent);
                if (risk != null) {
                    contributions.add(new AiStrategyScore.Contribution(
                            "alternate-loss:answer",
                            42,
                            "answers " + risk + ": " + permanent.getName()
                    ));
                    break;
                }
            }
        }

        int rawModifier = contributions.stream().mapToInt(AiStrategyScore.Contribution::getValue).sum();
        rawModifier = AiScoreSupport.clamp(rawModifier, AiScoreSupport.moduleCap(MAX_MODIFIER_PROPERTY, 50));
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

    private static String describeAlternatePressure(Permanent permanent) {
        String rules = AiScoreSupport.rules(permanent);
        if (rules.contains("you win the game") || rules.contains("wins the game")) {
            return "alternate win condition";
        }
        if (rules.contains("you lose the game") || rules.contains("loses the game")) {
            return "alternate loss condition";
        }
        if (rules.contains("poison counter") || rules.contains("poison counters")) {
            return "poison pressure";
        }
        if (rules.contains("commander damage")) {
            return "commander damage pressure";
        }
        if (rules.contains("mill") || rules.contains("puts the top") && rules.contains("graveyard")) {
            return "library pressure";
        }
        return null;
    }
}
