package mage.player.ai.scoring;

import mage.abilities.Ability;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.AiStrategyScore;
import mage.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class BoardRoleScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.boardRole.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.boardRole.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.boardRole.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public BoardRoleScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "board-role";
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
                RoleValue roleValue = estimateRoleValue(permanent, game);
                if (roleValue.value > 0) {
                    contributions.add(new AiStrategyScore.Contribution(
                            "board-role:" + roleValue.role,
                            roleValue.value,
                            "target " + permanent.getName() + " classified as " + roleValue.role
                    ));
                }
            }
        }
        int raw = contributions.stream().mapToInt(AiStrategyScore.Contribution::getValue).sum();
        raw = AiScoreSupport.clamp(raw, AiScoreSupport.moduleCap(MAX_MODIFIER_PROPERTY, 36));
        if (raw == 0 && contributions.isEmpty()) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        return AiStrategyScore.of(context.getBaseScore(), raw, AiScoreSupport.apply(raw, globalApplyModifiers, APPLY_PROPERTY), contributions);
    }

    private static RoleValue estimateRoleValue(Permanent permanent, Game game) {
        String rules = AiScoreSupport.rules(permanent);
        if (rules.contains("whenever") || rules.contains("at the beginning") || rules.contains("you win the game")) {
            return new RoleValue("engine", 28);
        }
        if (rules.contains("creatures you control") || rules.contains("other creatures") || rules.contains("tokens you control")) {
            return new RoleValue("payoff", 24);
        }
        if (permanent.isCreature(game)) {
            int power = Math.max(0, permanent.getPower().getValue());
            int toughness = Math.max(0, permanent.getToughness().getValue());
            if (power + toughness >= 10) {
                return new RoleValue("finisher", 22);
            }
            return new RoleValue("body", Math.min(18, 4 + power + toughness));
        }
        if (rules.contains("add {")) {
            return new RoleValue("resource", 14);
        }
        return new RoleValue("utility", 10);
    }

    private static final class RoleValue {
        private final String role;
        private final int value;

        private RoleValue(String role, int value) {
            this.role = role;
            this.value = value;
        }
    }

}
