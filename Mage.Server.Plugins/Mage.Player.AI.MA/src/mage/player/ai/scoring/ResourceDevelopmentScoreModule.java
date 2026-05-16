package mage.player.ai.scoring;

import mage.abilities.Ability;
import mage.game.Game;
import mage.player.ai.AiStrategyScore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ResourceDevelopmentScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.resourceDevelopment.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.resourceDevelopment.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.resourceDevelopment.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public ResourceDevelopmentScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "resource-development";
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
        Ability action = context.getAction();
        if (before == null || after == null || playerId == null || action == null || !AiScoreSupport.isMainPhase(before)) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        List<AiStrategyScore.Contribution> contributions = new ArrayList<>();
        addManaSourceDelta(contributions, before, after, playerId);
        addManaActionTiming(contributions, before, action, playerId);

        int rawModifier = contributions.stream().mapToInt(AiStrategyScore.Contribution::getValue).sum();
        rawModifier = AiScoreSupport.clamp(rawModifier, AiScoreSupport.moduleCap(MAX_MODIFIER_PROPERTY, 45));
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

    private static void addManaSourceDelta(List<AiStrategyScore.Contribution> contributions,
                                           Game before,
                                           Game after,
                                           UUID playerId) {
        int beforeSources = AiScoreSupport.countManaSources(before, playerId);
        int afterSources = AiScoreSupport.countManaSources(after, playerId);
        int gained = Math.max(0, afterSources - beforeSources);
        if (gained <= 0) {
            return;
        }
        int turn = before.getTurnNum();
        int value = gained * (turn <= 4 ? 18 : turn <= 7 ? 10 : 4);
        contributions.add(new AiStrategyScore.Contribution(
                "resource-development:mana-source",
                value,
                "mana sources " + beforeSources + " -> " + afterSources + " on turn " + turn
        ));
    }

    private static void addManaActionTiming(List<AiStrategyScore.Contribution> contributions,
                                            Game game,
                                            Ability action,
                                            UUID playerId) {
        if (!AiScoreSupport.developsOwnMana(game, action, playerId)) {
            return;
        }
        int lands = AiScoreSupport.countLands(game, playerId);
        int hand = AiScoreSupport.handSize(game, playerId);
        if (lands >= 8 && game.getTurnNum() >= 8 && hand <= 2) {
            contributions.add(new AiStrategyScore.Contribution(
                    "resource-development:late-ramp",
                    -18,
                    "late ramp with " + lands + " lands and " + hand + " cards in hand"
            ));
        } else if (lands <= 4 || game.getTurnNum() <= 5) {
            contributions.add(new AiStrategyScore.Contribution(
                    "resource-development:curve",
                    14,
                    "early mana development"
            ));
        }
    }
}
