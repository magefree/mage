package mage.player.ai.scoring;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.AiStrategyScore;
import mage.player.ai.util.AttackTriggerProjection;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class CombatPressureScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.combatPressure.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.combatPressure.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.combatPressure.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public CombatPressureScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "combat-pressure";
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
        if (before == null || after == null || playerId == null) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        List<AiStrategyScore.Contribution> contributions = new ArrayList<>();
        addOpponentLifePressure(contributions, before, after, playerId);
        addOwnLifeRisk(contributions, before, after, playerId);
        addCombatBoardTrade(contributions, before, after, playerId);
        addPressurePosture(contributions, before, action, playerId);
        addAttackDeclarationProjection(contributions, before, playerId);

        int rawModifier = contributions.stream().mapToInt(AiStrategyScore.Contribution::getValue).sum();
        rawModifier = AiScoreSupport.clamp(rawModifier, AiScoreSupport.moduleCap(MAX_MODIFIER_PROPERTY, 60));
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

    private static void addOpponentLifePressure(List<AiStrategyScore.Contribution> contributions,
                                                Game before,
                                                Game after,
                                                UUID playerId) {
        int bestDamage = 0;
        String bestName = null;
        for (UUID opponentId : before.getOpponents(playerId, true)) {
            Player beforeOpponent = before.getPlayer(opponentId);
            Player afterOpponent = after.getPlayer(opponentId);
            if (beforeOpponent == null || afterOpponent == null) {
                continue;
            }
            if (!afterOpponent.isInGame() || afterOpponent.hasLost() || afterOpponent.getLife() <= 0) {
                contributions.add(new AiStrategyScore.Contribution(
                        "combat-pressure:lethal",
                        60,
                        "line eliminates " + beforeOpponent.getName()
                ));
                return;
            }
            int damage = Math.max(0, beforeOpponent.getLife() - afterOpponent.getLife());
            if (damage > bestDamage) {
                bestDamage = damage;
                bestName = beforeOpponent.getName();
            }
        }
        if (bestDamage > 0) {
            contributions.add(new AiStrategyScore.Contribution(
                    "combat-pressure:damage",
                    Math.min(36, bestDamage * 3),
                    "pushes " + bestName + " for " + bestDamage + " life"
            ));
        }
    }

    private static void addOwnLifeRisk(List<AiStrategyScore.Contribution> contributions, Game before, Game after, UUID playerId) {
        int lifeLost = Math.max(0, AiScoreSupport.playerLife(before, playerId) - AiScoreSupport.playerLife(after, playerId));
        if (lifeLost <= 0) {
            return;
        }
        contributions.add(new AiStrategyScore.Contribution(
                "combat-pressure:self-risk",
                -Math.min(45, lifeLost * 4),
                "line costs " + lifeLost + " life"
        ));
    }

    private static void addCombatBoardTrade(List<AiStrategyScore.Contribution> contributions, Game before, Game after, UUID playerId) {
        int ownPowerDelta = AiScoreSupport.totalCreaturePower(after, playerId) - AiScoreSupport.totalCreaturePower(before, playerId);
        int opponentPowerDelta = 0;
        for (UUID opponentId : before.getOpponents(playerId, true)) {
            opponentPowerDelta += AiScoreSupport.totalCreaturePower(before, opponentId) - AiScoreSupport.totalCreaturePower(after, opponentId);
        }
        int trade = opponentPowerDelta + ownPowerDelta;
        if (Math.abs(trade) < 4) {
            return;
        }
        contributions.add(new AiStrategyScore.Contribution(
                "combat-pressure:trade",
                Math.max(-35, Math.min(35, trade)),
                "board power swing " + trade + " after combat/action"
        ));
    }

    private static void addPressurePosture(List<AiStrategyScore.Contribution> contributions,
                                           Game game,
                                           Ability action,
                                           UUID playerId) {
        if (action == null || !AiScoreSupport.isCombatStep(game)) {
            return;
        }
        if (AiScoreSupport.hasOutcome(action, Outcome.BoostCreature, Outcome.AddAbility)
                && AiScoreSupport.targetsOpponent(game, action, playerId)) {
            contributions.add(new AiStrategyScore.Contribution(
                    "combat-pressure:push-now",
                    12,
                    "combat-phase pressure action"
            ));
        }
    }

    private static void addAttackDeclarationProjection(List<AiStrategyScore.Contribution> contributions,
                                                       Game game,
                                                       UUID playerId) {
        if (!AiScoreSupport.isCombatStep(game)) {
            return;
        }
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return;
        }
        int bestValue = 0;
        String bestDetail = null;
        for (UUID defenderId : game.getOpponents(playerId, true)) {
            Player defender = game.getPlayer(defenderId);
            if (defender == null || !defender.isInGame() || defender.hasLost()) {
                continue;
            }
            for (Permanent attacker : player.getAvailableAttackers(defenderId, game)) {
                AttackTriggerProjection.Projection projection = AttackTriggerProjection.projectDeclaredAttacker(attacker, game);
                if (projection.getValue() <= bestValue) {
                    continue;
                }
                bestValue = projection.getValue();
                bestDetail = attacker.getName() + " -> " + defender.getName()
                        + "; " + String.join(", ", projection.getReasons());
            }
        }
        if (bestValue <= 0) {
            return;
        }
        contributions.add(new AiStrategyScore.Contribution(
                "combat-pressure:attacks-trigger",
                Math.min(35, bestValue),
                bestDetail
        ));
    }
}
