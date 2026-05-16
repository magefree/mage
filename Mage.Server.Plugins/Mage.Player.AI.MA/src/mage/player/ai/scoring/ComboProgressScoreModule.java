package mage.player.ai.scoring;

import mage.abilities.Ability;
import mage.abilities.costs.CastCostPlan;
import mage.constants.Outcome;
import mage.game.Game;
import mage.MageObject;
import mage.player.ai.AiStrategyScore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ComboProgressScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.comboProgress.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.comboProgress.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.comboProgress.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public ComboProgressScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "combo-progress";
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
        Ability action = context.getAction();
        UUID playerId = context.getPlayerId();
        if (before == null || after == null || action == null || playerId == null) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        List<AiStrategyScore.Contribution> contributions = new ArrayList<>();
        String text = AiScoreSupport.actionText(action, before);
        if ((text.contains("whenever") || text.contains("for each") || text.contains("copy"))
                && AiScoreSupport.hasOutcome(action, Outcome.PutManaInPool, Outcome.DrawCard, Outcome.Copy, Outcome.PutCreatureInPlay)) {
            contributions.add(new AiStrategyScore.Contribution(
                    "combo-progress:engine-action",
                    20,
                    "repeatable/resource-positive action text"
            ));
        }
        addRepeatableLoopProgress(contributions, before, after, action, playerId);
        int creatureDelta = AiScoreSupport.countCreatures(after, playerId) - AiScoreSupport.countCreatures(before, playerId);
        int handDelta = AiScoreSupport.handSize(after, playerId) - AiScoreSupport.handSize(before, playerId);
        if (creatureDelta >= 3 || handDelta >= 2) {
            contributions.add(new AiStrategyScore.Contribution(
                    "combo-progress:resource-burst",
                    Math.min(30, creatureDelta * 5 + handDelta * 8),
                    "resource burst creatures +" + creatureDelta + ", hand +" + handDelta
            ));
        }
        int raw = contributions.stream().mapToInt(AiStrategyScore.Contribution::getValue).sum();
        raw = AiScoreSupport.clamp(raw, AiScoreSupport.moduleCap(MAX_MODIFIER_PROPERTY, 40));
        if (raw == 0 && contributions.isEmpty()) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        return AiStrategyScore.of(context.getBaseScore(), raw, AiScoreSupport.apply(raw, globalApplyModifiers, APPLY_PROPERTY), contributions);
    }

    private static void addRepeatableLoopProgress(List<AiStrategyScore.Contribution> contributions,
                                                  Game before,
                                                  Game after,
                                                  Ability action,
                                                  UUID playerId) {
        String text = AiScoreSupport.actionText(action, before);
        String variantLabel = CastCostPlan.getVariantLabel(action);
        boolean hasVariant = variantLabel != null && !variantLabel.isEmpty();
        boolean repeatableSpellAction = hasVariant || text.contains("buyback");

        int beforeAttackPower = AiScoreSupport.maxAvailableAttackPower(before, playerId);
        int afterAttackPower = AiScoreSupport.maxAvailableAttackPower(after, playerId);
        int lowestOpponentLife = lowestOpponentLife(before, playerId);
        boolean hadVisibleCombatGoal = lowestOpponentLife < Integer.MAX_VALUE && beforeAttackPower >= lowestOpponentLife;
        if (hadVisibleCombatGoal && repeatableSpellAction) {
            contributions.add(new AiStrategyScore.Contribution(
                    "combo-progress:loop-over-goal",
                    -35,
                    "repeatable action after visible combat goal is already met"
            ));
            return;
        }

        MageObject source = action.getSourceObject(before);
        String sourceName = source == null ? null : source.getName();
        int beforeSourceInHand = AiScoreSupport.handCount(before, playerId, sourceName);
        int afterSourceInHand = AiScoreSupport.handCount(after, playerId, sourceName);
        boolean plannedSourcePreserved = hasVariant && CastCostPlan.preservesSource(action);
        if (!hasVariant) {
            if (text.contains("buyback")
                    && beforeSourceInHand > 0
                    && afterSourceInHand < beforeSourceInHand
                    && lowestOpponentLife < Integer.MAX_VALUE
                    && afterAttackPower < lowestOpponentLife) {
                contributions.add(new AiStrategyScore.Contribution(
                        "combo-progress:repeatable-spell-spent",
                        -24,
                        "normal cast spends " + sourceName + " before visible combat goal is met"
                ));
            }
            return;
        }

        boolean spellPreserved = plannedSourcePreserved || beforeSourceInHand > 0 && afterSourceInHand >= beforeSourceInHand;
        if (spellPreserved) {
            contributions.add(new AiStrategyScore.Contribution(
                    "combo-progress:repeatable-spell-preserved",
                    plannedSourcePreserved ? 28 : 16,
                    plannedSourcePreserved
                            ? "variant " + variantLabel + " plans to keep " + sourceName + " available"
                            : "variant " + variantLabel + " keeps " + sourceName + " available"
            ));
        }

        int attackPowerDelta = afterAttackPower - beforeAttackPower;
        int totalPowerDelta = AiScoreSupport.totalCreaturePower(after, playerId) - AiScoreSupport.totalCreaturePower(before, playerId);
        int treasureDelta = AiScoreSupport.countTreasures(after, playerId) - AiScoreSupport.countTreasures(before, playerId);
        int handDelta = AiScoreSupport.handSize(after, playerId) - AiScoreSupport.handSize(before, playerId);
        int progressValue = Math.max(0, attackPowerDelta) * 2
                + Math.max(0, totalPowerDelta) / 2
                + Math.max(0, treasureDelta) * 4
                + Math.max(0, handDelta) * 3;
        if (progressValue > 0) {
            contributions.add(new AiStrategyScore.Contribution(
                    "combo-progress:loop-progress",
                    Math.min(34, Math.max(10, progressValue)),
                    "variant " + variantLabel
                            + " advances attack +" + attackPowerDelta
                            + ", total power +" + totalPowerDelta
                            + ", treasures +" + treasureDelta
            ));
        } else if (spellPreserved && !plannedSourcePreserved) {
            contributions.add(new AiStrategyScore.Contribution(
                    "combo-progress:loop-no-progress",
                    -20,
                    "repeatable variant " + variantLabel + " keeps looping without visible progress"
            ));
        }

        if (lowestOpponentLife < Integer.MAX_VALUE
                && beforeAttackPower < lowestOpponentLife
                && afterAttackPower >= lowestOpponentLife) {
            contributions.add(new AiStrategyScore.Contribution(
                    "combo-progress:resource-goal-met",
                    35,
                    "visible attackers can now pressure lethal damage " + afterAttackPower + " >= " + lowestOpponentLife
            ));
        }
    }

    private static int lowestOpponentLife(Game game, UUID playerId) {
        int lowestLife = Integer.MAX_VALUE;
        if (game == null || playerId == null) {
            return lowestLife;
        }
        for (UUID opponentId : game.getOpponents(playerId, true)) {
            mage.players.Player opponent = game.getPlayer(opponentId);
            if (opponent != null && opponent.isInGame() && !opponent.hasLost()) {
                lowestLife = Math.min(lowestLife, opponent.getLife());
            }
        }
        return lowestLife;
    }
}
