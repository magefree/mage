package mage.player.ai.scoring;

import mage.abilities.Ability;
import mage.abilities.PlayLandAbility;
import mage.abilities.common.PassAbility;
import mage.constants.AbilityType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.player.ai.AiStrategyScore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Encodes phase-level intent before narrower tactical modules score the same candidate.
 * Keep this module generic: it should express timing discipline such as "wait for draw"
 * or "use interaction during an opponent's upkeep", not card-specific exceptions.
 */
public final class PhaseStrategyScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.phaseStrategy.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.phaseStrategy.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.phaseStrategy.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public PhaseStrategyScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "phase-strategy";
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
        addPreDrawStrategy(contributions, game, action, playerId);
        addMainPhaseStrategy(contributions, game, action, playerId);
        addCombatPhaseStrategy(contributions, game, action);

        int rawModifier = contributions.stream().mapToInt(AiStrategyScore.Contribution::getValue).sum();
        rawModifier = AiScoreSupport.clamp(rawModifier, AiScoreSupport.moduleCap(MAX_MODIFIER_PROPERTY, 40));
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

    private static void addPreDrawStrategy(List<AiStrategyScore.Contribution> contributions,
                                           Game game,
                                           Ability action,
                                           UUID playerId) {
        PhaseStep step = game.getTurnStepType();
        if (!AiScoreSupport.isBeforeDrawStep(game)
                || action instanceof PassAbility
                || action.isManaAbility()
                || AiScoreSupport.hasOutcome(action, Outcome.PutManaInPool)) {
            return;
        }

        boolean activePlayer = game.isActivePlayer(playerId);
        boolean targetsOpponent = AiScoreSupport.targetsOpponent(game, action, playerId);
        boolean badOutcome = AiScoreSupport.hasBadOutcome(action);
        int manaValue = AiScoreSupport.actionManaValue(action);
        boolean meaningfulSpend = manaValue >= 2;
        boolean castOrActivated = action.getAbilityType() == AbilityType.SPELL
                || action.getAbilityType().isActivatedAbility();

        if (activePlayer) {
            if (!castOrActivated || !meaningfulSpend || game.getStack().size() > 0) {
                return;
            }
            int penalty = -Math.min(34, 12 + manaValue * 4);
            if (AiScoreSupport.hasOutcome(action, Outcome.DrawCard)) {
                penalty = Math.min(-6, penalty / 2);
            }
            if (badOutcome && targetsOpponent) {
                penalty = Math.max(-8, penalty / 2);
            }
            contributions.add(new AiStrategyScore.Contribution(
                    "phase-strategy:own-pre-draw-hold",
                    penalty,
                    "own " + step + " action spends " + manaValue + " mana before seeing draw"
            ));
            return;
        }

        if (badOutcome && targetsOpponent && meaningfulSpend) {
            contributions.add(new AiStrategyScore.Contribution(
                    "phase-strategy:opponent-pre-draw-disrupt",
                    Math.min(18, 6 + manaValue * 2),
                    "interaction can disrupt opponent before draw/upkeep resources"
            ));
        } else if (castOrActivated && meaningfulSpend && !AiScoreSupport.hasOutcome(action, Outcome.DrawCard)) {
            contributions.add(new AiStrategyScore.Contribution(
                    "phase-strategy:opponent-turn-hold",
                    -Math.min(24, 8 + manaValue * 3),
                    "non-urgent action on opponent pre-draw step"
            ));
        }
    }

    private static void addMainPhaseStrategy(List<AiStrategyScore.Contribution> contributions,
                                             Game game,
                                             Ability action,
                                             UUID playerId) {
        if (!AiScoreSupport.isMainPhase(game) || game.getStack().size() > 0) {
            return;
        }
        PhaseStep step = game.getTurnStepType();
        boolean playLand = action instanceof PlayLandAbility || action.getAbilityType() == AbilityType.PLAY_LAND;
        if (playLand) {
            int lands = AiScoreSupport.countLands(game, playerId);
            int value = game.getTurnNum() <= 5 || lands <= 4 ? 8 : 3;
            contributions.add(new AiStrategyScore.Contribution(
                    "phase-strategy:main-land",
                    value,
                    step + " is a normal window for land development"
            ));
            return;
        }

        if (AiScoreSupport.developsOwnMana(game, action, playerId)) {
            int lands = AiScoreSupport.countLands(game, playerId);
            int value = game.getTurnNum() <= 5 || lands <= 4 ? 8 : 3;
            contributions.add(new AiStrategyScore.Contribution(
                    "phase-strategy:main-develop",
                    value,
                    step + " is a normal window for mana development"
            ));
        } else if (action.getAbilityType() == AbilityType.SPELL
                && !AiScoreSupport.hasBadOutcome(action)
                && AiScoreSupport.actionManaValue(action) >= 2) {
            contributions.add(new AiStrategyScore.Contribution(
                    step == PhaseStep.PRECOMBAT_MAIN
                            ? "phase-strategy:precombat-deploy"
                            : "phase-strategy:postcombat-deploy",
                    step == PhaseStep.PRECOMBAT_MAIN ? 2 : 4,
                    step + " spell deployment"
            ));
        }
    }

    private static void addCombatPhaseStrategy(List<AiStrategyScore.Contribution> contributions,
                                               Game game,
                                               Ability action) {
        if (!AiScoreSupport.isCombatStep(game)
                || game.getStack().size() > 0
                || action instanceof PassAbility
                || action.isManaAbility()
                || AiScoreSupport.hasBadOutcome(action)) {
            return;
        }
        if ((action.getAbilityType() == AbilityType.SPELL || action.getAbilityType().isActivatedAbility())
                && AiScoreSupport.actionManaValue(action) >= 2) {
            contributions.add(new AiStrategyScore.Contribution(
                    "phase-strategy:combat-discipline",
                    -6,
                    "non-urgent development during combat should usually wait"
            ));
        }
    }
}
