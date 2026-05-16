package mage.player.ai;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.PassAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.cards.decks.analysis.DeckProfile;
import mage.cards.decks.analysis.DeckProfileService;
import mage.cards.decks.analysis.DeckRole;
import mage.cards.decks.analysis.DeckSynergy;
import mage.constants.CommanderCardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.score.GameStateEvaluator2;
import mage.players.Player;
import mage.target.Target;
import mage.util.CardUtil;
import mage.watchers.common.CommanderPlaysCountWatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Converts a deck strategy profile into small, explainable priority-score nudges.
 */
public final class DeckStrategyAdvisor {

    private static final Set<DeckRole> UTILITY_ROLES = EnumSet.of(
            DeckRole.REMOVAL,
            DeckRole.BOARD_WIPE,
            DeckRole.CARD_DRAW,
            DeckRole.RAMP,
            DeckRole.MANA_FIXING,
            DeckRole.COUNTER_OR_PROTECTION,
            DeckRole.COMBAT_TRICK
    );
    private static final int COMMANDER_TAX_SCORE_PER_MANA = 22;
    private static final int COMMANDER_TAX_MIN_CAP = 120;
    private static final int COMMANDER_TAX_CAP_MULTIPLIER = 2;
    private static final int COMMANDER_PROTECTION_BASE_SCORE = 30;
    private static final int COMMANDER_PROTECTION_SCORE_PER_TAX_MANA = 8;
    private static final int COMMANDER_PROTECTION_MAX_SCORE = 120;

    private final UUID playerId;
    private final DeckStrategyProfile profile;
    private final int maxAppliedModifier;
    private final boolean applyModifiers;

    private DeckStrategyAdvisor(UUID playerId, DeckStrategyProfile profile, int maxAppliedModifier, boolean applyModifiers) {
        this.playerId = playerId;
        this.profile = profile;
        this.maxAppliedModifier = Math.max(0, maxAppliedModifier);
        this.applyModifiers = applyModifiers;
    }

    public static DeckStrategyAdvisor fromGame(Game game, UUID playerId, int maxAppliedModifier, boolean applyModifiers) {
        Player player = game == null ? null : game.getPlayer(playerId);
        if (player == null) {
            return new DeckStrategyAdvisor(playerId, DeckStrategyProfile.fromProfiles(null, null), maxAppliedModifier, applyModifiers);
        }
        DeckProfile mainDeck = DeckProfileService.analyze(collectMainDeckCards(game, player));
        DeckProfile commandZone = DeckProfileService.analyze(collectCommanderCards(game, player));
        return new DeckStrategyAdvisor(playerId, DeckStrategyProfile.fromProfiles(mainDeck, commandZone), maxAppliedModifier, applyModifiers);
    }

    public DeckStrategyProfile getProfile() {
        return profile;
    }

    public AiStrategyScore evaluateCandidate(Game decisionGame, Game finalGame, Ability action, int baseScore) {
        if (baseScore >= GameStateEvaluator2.WIN_GAME_SCORE || baseScore <= GameStateEvaluator2.LOSE_GAME_SCORE) {
            return AiStrategyScore.none(baseScore);
        }
        if (decisionGame == null || action == null || action instanceof PassAbility) {
            return AiStrategyScore.none(baseScore);
        }

        ActionContext actionContext = ActionContext.fromGame(decisionGame, action, playerId);
        boolean commanderAction = isCommanderAction(decisionGame, action);
        int commanderTaxMana = getCommanderTaxMana(decisionGame, action, commanderAction);
        Set<DeckRole> rawActionRoles = classifyAction(decisionGame, action);
        Set<DeckRole> actionRoles = filterStrategicRoles(actionContext, rawActionRoles);
        String sourceName = getSourceName(decisionGame, action);
        Set<String> actionMechanics = classifyActionMechanics(decisionGame, action, sourceName);
        List<DeckStrategyProfile.FeatureSignal> featureSignals = sourceName == null
                ? Collections.emptyList()
                : profile.getFeatureSignalsForCard(sourceName);
        boolean useFeatureSignals = shouldUseFeatureProfileSignals(actionContext);
        if (actionRoles.isEmpty()
                && actionMechanics.isEmpty()
                && (!useFeatureSignals || featureSignals.isEmpty())
                && commanderTaxMana <= 0) {
            return AiStrategyScore.none(baseScore);
        }

        RoleState roleState = RoleState.fromGame(decisionGame, playerId);
        List<AiStrategyScore.Contribution> contributions = new ArrayList<>();
        addRoleContributions(contributions, decisionGame, actionContext, actionRoles, roleState);
        addPackageContributions(contributions, actionContext, actionRoles, roleState);
        addCardDrawContribution(contributions, actionContext, roleState);
        addCommanderContribution(contributions, actionContext, commanderAction, actionRoles);
        addCommanderTaxContribution(contributions, commanderTaxMana, roleState);
        addCommanderProtectionContribution(contributions, actionContext, actionRoles);
        addMechanicContributions(contributions, actionContext, actionMechanics);
        addFeatureProfileContributions(contributions, actionContext, sourceName, featureSignals);

        int rawModifier = contributions.stream().mapToInt(AiStrategyScore.Contribution::getValue).sum();
        int appliedModifier = applyModifiers
                ? getAppliedModifier(decisionGame, action, actionRoles, roleState, contributions, commanderAction)
                : 0;
        return AiStrategyScore.of(baseScore, rawModifier, appliedModifier, contributions);
    }

    private void addRoleContributions(List<AiStrategyScore.Contribution> contributions,
                                      Game game,
                                      ActionContext actionContext,
                                      Set<DeckRole> actionRoles,
                                      RoleState roleState) {
        for (DeckRole role : actionRoles) {
            int weight = profile.getRoleWeight(role);
            if (weight <= 0) {
                continue;
            }
            int value = roleState.hasOnline(role) ? Math.max(4, weight / 3) : weight;
            if (role == DeckRole.THREAT && actionRoles.size() > 1) {
                value = Math.min(value, 18);
            }
            if (role == DeckRole.MANA_FIXING && roleState.getOnlineCount(role) > 1) {
                value = Math.max(3, value / 2);
            }
            value = applyRoleContext(game, actionContext, role, actionRoles, roleState, value);
            if (value <= 0) {
                continue;
            }
            contributions.add(new AiStrategyScore.Contribution(
                    "role:" + role.name(),
                    value,
                    emptyToDefault(profile.describeRole(role), "deck role")
            ));
        }
    }

    private void addPackageContributions(List<AiStrategyScore.Contribution> contributions,
                                         ActionContext actionContext,
                                         Set<DeckRole> actionRoles,
                                         RoleState roleState) {
        if (!actionContext.mainPhase) {
            return;
        }
        for (DeckSynergy synergy : profile.getPrimarySynergiesAdvancedBy(actionRoles)) {
            List<DeckRole> componentRoles = profile.getPackageComponentRoles(synergy);
            int availableComponents = countAvailableComponents(componentRoles, actionRoles, roleState);
            if (componentRoles.size() > 1 && availableComponents < 2) {
                continue;
            }
            int value = Math.max(6, profile.getPackageWeight(synergy) / 3);
            int actionComponents = countActionComponents(componentRoles, actionRoles);
            if (actionComponents > 1) {
                value += 6;
            }
            contributions.add(new AiStrategyScore.Contribution(
                    "package:" + synergy.name(),
                    value,
                    profile.describePackage(synergy)
            ));
        }
    }

    private static int countActionComponents(List<DeckRole> componentRoles, Set<DeckRole> actionRoles) {
        int count = 0;
        for (DeckRole role : componentRoles) {
            if (actionRoles.contains(role)) {
                count++;
            }
        }
        return count;
    }

    private static int countAvailableComponents(List<DeckRole> componentRoles, Set<DeckRole> actionRoles, RoleState roleState) {
        int count = 0;
        for (DeckRole role : componentRoles) {
            if (actionRoles.contains(role) || roleState.hasAvailable(role)) {
                count++;
            }
        }
        return count;
    }

    private void addCardDrawContribution(List<AiStrategyScore.Contribution> contributions,
                                         ActionContext actionContext,
                                         RoleState roleState) {
        if (!actionContext.mainPhase) {
            return;
        }
        boolean drawsCards = contributions.stream()
                .anyMatch(contribution -> contribution.getLabel().equals("role:" + DeckRole.CARD_DRAW.name()));
        if (!drawsCards) {
            return;
        }
        DrawValue drawValue = estimateCardDrawValue(roleState);
        if (drawValue.value <= 0) {
            return;
        }
        contributions.add(new AiStrategyScore.Contribution(
                "hidden-info:CARD_DRAW",
                drawValue.value,
                drawValue.detail
        ));
    }

    private DrawValue estimateCardDrawValue(RoleState roleState) {
        if (roleState.remainingLibrarySize <= 0) {
            return DrawValue.none();
        }
        List<RoleProbability> probabilities = new ArrayList<>();
        for (Map.Entry<DeckRole, Integer> entry : profile.getRoleWeights().entrySet()) {
            DeckRole role = entry.getKey();
            if (role == DeckRole.CARD_DRAW || roleState.hasAvailable(role)) {
                continue;
            }
            int remainingCount = roleState.getRemainingLibraryCount(role);
            if (remainingCount <= 0) {
                continue;
            }
            double probability = Math.min(1.0, remainingCount / (double) roleState.remainingLibrarySize);
            int value = (int) Math.round(entry.getValue() * probability);
            if (value > 0) {
                probabilities.add(new RoleProbability(role, remainingCount, value));
            }
        }
        probabilities.sort(Comparator.comparingInt(RoleProbability::getValue).reversed());
        int value = probabilities.stream().limit(4).mapToInt(RoleProbability::getValue).sum();
        if (value <= 0) {
            return DrawValue.none();
        }
        String detail = probabilities.stream()
                .limit(4)
                .map(probability -> probability.role.name() + " " + probability.remainingCount + "/" + roleState.remainingLibrarySize)
                .collect(Collectors.joining(", "));
        return new DrawValue(Math.min(140, value), "expected access to missing roles: " + detail);
    }

    private void addCommanderContribution(List<AiStrategyScore.Contribution> contributions,
                                          ActionContext actionContext,
                                          boolean commanderAction,
                                          Set<DeckRole> actionRoles) {
        if (!commanderAction) {
            return;
        }
        if (!actionContext.mainPhase && !hasTacticalRole(actionRoles)) {
            return;
        }
        int roleWeight = actionRoles.stream()
                .mapToInt(profile::getRoleWeight)
                .max()
                .orElse(0);
        if (roleWeight <= 0) {
            return;
        }
        contributions.add(new AiStrategyScore.Contribution(
                "commander:ANCHOR",
                Math.max(10, Math.min(35, roleWeight / 4)),
                "commander action supports profiled roles"
        ));
    }

    private void addCommanderTaxContribution(List<AiStrategyScore.Contribution> contributions,
                                             int commanderTaxMana,
                                             RoleState roleState) {
        int penalty = estimateCommanderTaxPenalty(commanderTaxMana, roleState.handSize, maxAppliedModifier);
        if (penalty >= 0) {
            return;
        }
        contributions.add(new AiStrategyScore.Contribution(
                "commander:TAX",
                penalty,
                "commander tax +" + commanderTaxMana + "; hand options " + roleState.handSize
        ));
    }

    private static void addCommanderProtectionContribution(List<AiStrategyScore.Contribution> contributions,
                                                           ActionContext actionContext,
                                                           Set<DeckRole> actionRoles) {
        if (!actionContext.targetsFriendlyCommander || !actionRoles.contains(DeckRole.COUNTER_OR_PROTECTION)) {
            return;
        }
        int value = Math.min(
                COMMANDER_PROTECTION_MAX_SCORE,
                COMMANDER_PROTECTION_BASE_SCORE
                        + actionContext.friendlyCommanderTaxMana * COMMANDER_PROTECTION_SCORE_PER_TAX_MANA
        );
        contributions.add(new AiStrategyScore.Contribution(
                "commander:PROTECT",
                value,
                "protects commander; current tax +" + actionContext.friendlyCommanderTaxMana
        ));
    }

    private void addFeatureProfileContributions(List<AiStrategyScore.Contribution> contributions,
                                                ActionContext actionContext,
                                                String sourceName,
                                                List<DeckStrategyProfile.FeatureSignal> featureSignals) {
        if (!shouldUseFeatureProfileSignals(actionContext) || sourceName == null || featureSignals.isEmpty()) {
            return;
        }
        for (DeckStrategyProfile.FeatureSignal signal : featureSignals) {
            contributions.add(new AiStrategyScore.Contribution(
                    signal.getLabel(),
                    signal.getValue(),
                    sourceName + " | " + signal.getDetail()
            ));
        }
    }

    private void addMechanicContributions(List<AiStrategyScore.Contribution> contributions,
                                          ActionContext actionContext,
                                          Set<String> actionMechanics) {
        if (!actionContext.mainPhase || actionMechanics.isEmpty()) {
            return;
        }
        List<AiStrategyScore.Contribution> mechanicContributions = new ArrayList<>();
        for (String mechanic : actionMechanics) {
            int value = mechanicContributionValue(mechanic);
            if (value <= 0) {
                continue;
            }
            mechanicContributions.add(new AiStrategyScore.Contribution(
                    "mechanic:" + mechanic,
                    value,
                    mechanicContributionDetail(mechanic)
            ));
        }
        mechanicContributions.stream()
                .sorted(Comparator.comparingInt(AiStrategyScore.Contribution::getValue).reversed())
                .limit(3)
                .forEach(contributions::add);
    }

    private int mechanicContributionValue(String mechanic) {
        if (mechanic == null || mechanic.isEmpty()) {
            return 0;
        }
        if (mechanic.startsWith("TRIBAL:")) {
            String subtype = mechanic.substring("TRIBAL:".length());
            int subtypeCount = "ANY".equals(subtype)
                    ? bestSubtypeCount()
                    : profile.getMainDeck().getMechanicCount("SUBTYPE:" + subtype);
            if (subtypeCount >= 10) {
                return 14;
            }
            if (subtypeCount >= 6) {
                return 10;
            }
            if (subtypeCount >= 3) {
                return 6;
            }
            return 0;
        }
        if (mechanic.startsWith("SUBTYPE:")) {
            String subtype = mechanic.substring("SUBTYPE:".length());
            int payoffWeight = profile.getMechanicWeight("TRIBAL:" + subtype);
            if (payoffWeight <= 0) {
                return 0;
            }
            return Math.max(4, Math.min(10, payoffWeight / 2));
        }
        switch (mechanic) {
            case "SACRIFICE":
            case "TOKENS":
            case "GRAVEYARD":
            case "PLUS_ONE_COUNTERS":
            case "LIFE_GAIN":
            case "ARTIFACTS":
                int weight = profile.getMechanicWeight(mechanic);
                return weight <= 0 ? 0 : Math.max(3, Math.min(10, weight / 2));
            default:
                return 0;
        }
    }

    private String mechanicContributionDetail(String mechanic) {
        if (mechanic.startsWith("TRIBAL:")) {
            String subtype = mechanic.substring("TRIBAL:".length());
            if ("ANY".equals(subtype)) {
                return "generic tribal payoff; best subtype count " + bestSubtypeCount();
            }
            return "tribal payoff; deck has " + profile.getMainDeck().getMechanicCount("SUBTYPE:" + subtype)
                    + " " + subtype + " cards";
        }
        if (mechanic.startsWith("SUBTYPE:")) {
            String subtype = mechanic.substring("SUBTYPE:".length());
            return "on-theme subtype; payoff weight " + profile.getMechanicWeight("TRIBAL:" + subtype);
        }
        return emptyToDefault(profile.describeMechanic(mechanic), "deck mechanic context");
    }

    private int bestSubtypeCount() {
        int best = 0;
        for (Map.Entry<String, Integer> entry : profile.getMainDeck().getMechanicCounts().entrySet()) {
            if (entry.getKey().startsWith("SUBTYPE:")) {
                best = Math.max(best, entry.getValue());
            }
        }
        return best;
    }

    private static Set<DeckRole> filterStrategicRoles(ActionContext actionContext, Set<DeckRole> rawActionRoles) {
        if (rawActionRoles.isEmpty()) {
            return rawActionRoles;
        }
        Set<DeckRole> roles = EnumSet.noneOf(DeckRole.class);
        for (DeckRole role : rawActionRoles) {
            if (!shouldKeepRoleForPhase(actionContext.step, role, rawActionRoles)) {
                continue;
            }
            if (!shouldKeepRoleForTargetPolarity(
                    role,
                    actionContext.hasTargets,
                    actionContext.hasFriendlyTarget,
                    actionContext.hasOpponentTarget,
                    actionContext.hasUnknownTarget
            )) {
                continue;
            }
            roles.add(role);
        }
        return roles;
    }

    private static boolean shouldUseFeatureProfileSignals(ActionContext actionContext) {
        return actionContext.mainPhase;
    }

    static boolean shouldKeepRoleForPhase(PhaseStep step, DeckRole role, Set<DeckRole> rawActionRoles) {
        if (isMainPhase(step)) {
            return true;
        }
        switch (role) {
            case REMOVAL:
            case BOARD_WIPE:
            case COUNTER_OR_PROTECTION:
                return true;
            case COMBAT_TRICK:
                return isCombatStep(step);
            case X_SPELL:
                return isCombatStep(step) && hasTacticalRole(rawActionRoles);
            default:
                return false;
        }
    }

    static boolean shouldKeepRoleForTargetPolarity(DeckRole role,
                                                   boolean hasTargets,
                                                   boolean hasFriendlyTarget,
                                                   boolean hasOpponentTarget,
                                                   boolean hasUnknownTarget) {
        if (!hasTargets || hasUnknownTarget) {
            return true;
        }
        switch (role) {
            case REMOVAL:
                return hasOpponentTarget;
            case COUNTER_OR_PROTECTION:
            case COMBAT_TRICK:
                return hasFriendlyTarget || !hasOpponentTarget;
            default:
                return true;
        }
    }

    private static boolean hasTacticalRole(Set<DeckRole> roles) {
        return roles.contains(DeckRole.REMOVAL)
                || roles.contains(DeckRole.BOARD_WIPE)
                || roles.contains(DeckRole.COUNTER_OR_PROTECTION)
                || roles.contains(DeckRole.COMBAT_TRICK);
    }

    private int applyRoleContext(Game game, ActionContext actionContext, DeckRole role, Set<DeckRole> actionRoles, RoleState roleState, int value) {
        double scale = roleContextScale(game, actionContext, role, actionRoles, roleState);
        if (scale <= 0.0) {
            return 0;
        }
        return Math.max(2, (int) Math.round(value * scale));
    }

    private double roleContextScale(Game game, ActionContext actionContext, DeckRole role, Set<DeckRole> actionRoles, RoleState roleState) {
        switch (role) {
            case SACRIFICE_OUTLET:
                return hasAvailableOrAction(roleState, actionRoles,
                        DeckRole.SACRIFICE_FODDER,
                        DeckRole.SACRIFICE_FODDER_PROVIDER,
                        DeckRole.TOKEN_MAKER,
                        DeckRole.DEATH_PAYOFF) ? 1.0 : 0.35;
            case SACRIFICE_FODDER_PROVIDER:
            case SACRIFICE_FODDER:
            case TOKEN_MAKER:
                return hasAvailableOrAction(roleState, actionRoles,
                        DeckRole.SACRIFICE_OUTLET,
                        DeckRole.DEATH_PAYOFF,
                        DeckRole.PLUS_ONE_COUNTER_PAYOFF) ? 1.0 : 0.55;
            case DEATH_PAYOFF:
                return hasAvailableOrAction(roleState, actionRoles,
                        DeckRole.SACRIFICE_OUTLET,
                        DeckRole.SACRIFICE_FODDER,
                        DeckRole.SACRIFICE_FODDER_PROVIDER,
                        DeckRole.TOKEN_MAKER) ? 1.0 : 0.45;
            case GRAVEYARD_RECURSION:
                return hasAvailableOrAction(roleState, actionRoles,
                        DeckRole.DISCARD_OR_SELF_MILL,
                        DeckRole.SACRIFICE_FODDER,
                        DeckRole.SACRIFICE_FODDER_PROVIDER,
                        DeckRole.DEATH_PAYOFF) ? 1.0 : 0.55;
            case DISCARD_OR_SELF_MILL:
                return hasAvailableOrAction(roleState, actionRoles,
                        DeckRole.GRAVEYARD_RECURSION,
                        DeckRole.DEATH_PAYOFF) ? 1.0 : 0.55;
            case LIFE_GAIN:
                return hasAvailableOrAction(roleState, actionRoles, DeckRole.LIFE_GAIN_PAYOFF) ? 1.0 : 0.55;
            case LIFE_GAIN_PAYOFF:
                return hasAvailableOrAction(roleState, actionRoles, DeckRole.LIFE_GAIN) ? 1.0 : 0.45;
            case PLUS_ONE_COUNTER_MAKER:
                return hasAvailableOrAction(roleState, actionRoles,
                        DeckRole.PLUS_ONE_COUNTER_PAYOFF,
                        DeckRole.CHEAP_THREAT,
                        DeckRole.LARGE_THREAT) ? 1.0 : 0.6;
            case PLUS_ONE_COUNTER_PAYOFF:
                return hasAvailableOrAction(roleState, actionRoles, DeckRole.PLUS_ONE_COUNTER_MAKER) ? 1.0 : 0.45;
            case X_SPELL:
                return hasAvailableOrAction(roleState, actionRoles,
                        DeckRole.X_SPELL_PAYOFF,
                        DeckRole.RAMP,
                        DeckRole.CARD_DRAW) ? 1.0 : 0.65;
            case X_SPELL_PAYOFF:
                return hasAvailableOrAction(roleState, actionRoles, DeckRole.X_SPELL, DeckRole.RAMP) ? 1.0 : 0.5;
            case COUNTER_OR_PROTECTION:
                if (!isCounterOrProtectionModeRelevant(actionContext)) {
                    return 0.0;
                }
                if (isCombatStep(game)) {
                    return 1.0;
                }
                return roleState.hasProtectableBoard() ? 0.75 : 0.35;
            case COMBAT_TRICK:
                return isCombatStep(game) ? 1.0 : 0.25;
            case MANA_FIXING:
                return manaDevelopmentScale(game, roleState, 5, 7);
            case RAMP:
                return manaDevelopmentScale(game, roleState, 6, 8);
            case CARD_DRAW:
                if (roleState.handSize <= 2) {
                    return 1.15;
                }
                return roleState.handSize >= 6 ? 0.65 : 1.0;
            case REMOVAL:
                return 0.75;
            case BOARD_WIPE:
                return 0.55;
            case THREAT:
            case CHEAP_THREAT:
            case LARGE_THREAT:
                return roleState.getOnlineCount(DeckRole.THREAT) >= 2 ? 0.6 : 1.0;
            default:
                return 1.0;
        }
    }

    private double manaDevelopmentScale(Game game, RoleState roleState, int matureLandCount, int lateLandCount) {
        int turn = game == null ? 0 : game.getTurnNum();
        if (roleState.landPermanentCount >= lateLandCount || turn >= 10) {
            return 0.25;
        }
        if (roleState.landPermanentCount >= matureLandCount || turn >= 7) {
            return 0.5;
        }
        return 1.0;
    }

    private int getContextualAppliedModifierCap(Game game,
                                                ActionContext actionContext,
                                                Ability action,
                                                Set<DeckRole> actionRoles,
                                                RoleState roleState,
                                                List<AiStrategyScore.Contribution> contributions,
                                                boolean commanderAction) {
        int cap = maxAppliedModifier;
        if (cap <= 0 || contributions.isEmpty()) {
            return 0;
        }

        boolean profileSignal = hasContributionPrefix(contributions, "profile:");
        boolean packageSignal = hasContributionPrefix(contributions, "package:");
        boolean contextualRole = actionRoles.stream()
                .anyMatch(role -> roleContextScale(game, actionContext, role, actionRoles, roleState) >= 0.75);
        boolean onlyUtilityRoles = !actionRoles.isEmpty() && actionRoles.stream().allMatch(UTILITY_ROLES::contains);

        if (isAlternateCardAbility(game, action) && !commanderAction && !profileSignal) {
            cap = Math.min(cap, Math.max(20, maxAppliedModifier / 2));
        }
        if (onlyUtilityRoles && !commanderAction && !profileSignal) {
            cap = Math.min(cap, Math.max(25, maxAppliedModifier / 2));
        }
        if (actionRoles.size() >= 4 && !commanderAction) {
            cap = Math.min(cap, Math.max(35, (maxAppliedModifier * 2) / 3));
        }
        if (hasLargeClassifierPileup(contributions) && !commanderAction) {
            cap = Math.min(cap, Math.max(30, maxAppliedModifier / 2));
        }
        if (!commanderAction && !profileSignal && !packageSignal && !contextualRole) {
            cap = Math.min(cap, Math.max(15, maxAppliedModifier / 3));
        }
        if (isLateManaDevelopmentAction(game, actionRoles, roleState)) {
            cap = Math.min(cap, Math.max(12, maxAppliedModifier / 4));
        }
        if (actionRoles.contains(DeckRole.COMBAT_TRICK) && !isCombatStep(game)) {
            cap = Math.min(cap, Math.max(12, maxAppliedModifier / 4));
        }
        return Math.max(0, cap);
    }

    private int getAppliedModifier(Game game,
                                   Ability action,
                                   Set<DeckRole> actionRoles,
                                   RoleState roleState,
                                   List<AiStrategyScore.Contribution> contributions,
                                   boolean commanderAction) {
        int positiveModifier = contributions
                .stream()
                .filter(contribution -> !contribution.getLabel().startsWith("mechanic:"))
                .mapToInt(AiStrategyScore.Contribution::getValue)
                .filter(value -> value > 0)
                .sum();
        int negativeModifier = contributions
                .stream()
                .filter(contribution -> !contribution.getLabel().startsWith("mechanic:"))
                .mapToInt(AiStrategyScore.Contribution::getValue)
                .filter(value -> value < 0)
                .sum();
        int positiveCap = getContextualAppliedModifierCap(
                game, ActionContext.fromGame(game, action, playerId), action, actionRoles, roleState, contributions, commanderAction
        );
        return Math.min(positiveCap, positiveModifier) + negativeModifier;
    }

    private static boolean isCounterOrProtectionModeRelevant(ActionContext actionContext) {
        if (actionContext == null) {
            return true;
        }
        if (actionContext.targetsStackObject) {
            return true;
        }
        if (actionContext.hasNonCounterProtectionText) {
            return actionContext.hasFriendlyTarget || !actionContext.hasOpponentTarget;
        }
        return !actionContext.hasCounterSpellText;
    }

    static int estimateCommanderTaxPenalty(int commanderTaxMana, int handSize, int maxAppliedModifier) {
        if (commanderTaxMana <= 0) {
            return 0;
        }
        double handScale;
        if (handSize <= 0) {
            handScale = 0.45;
        } else if (handSize == 1) {
            handScale = 0.65;
        } else if (handSize <= 3) {
            handScale = 1.0;
        } else {
            handScale = 1.25;
        }
        int uncappedPenalty = (int) Math.round(commanderTaxMana * COMMANDER_TAX_SCORE_PER_MANA * handScale);
        int penaltyCap = Math.max(COMMANDER_TAX_MIN_CAP, maxAppliedModifier * COMMANDER_TAX_CAP_MULTIPLIER);
        return -Math.min(penaltyCap, uncappedPenalty);
    }

    private static boolean hasContributionPrefix(List<AiStrategyScore.Contribution> contributions, String prefix) {
        return contributions.stream().anyMatch(contribution -> contribution.getLabel().startsWith(prefix));
    }

    private static boolean hasLargeClassifierPileup(List<AiStrategyScore.Contribution> contributions) {
        int classifierSignals = 0;
        int classifierTotal = 0;
        for (AiStrategyScore.Contribution contribution : contributions) {
            String label = contribution.getLabel();
            if (label.startsWith("role:")
                    || label.startsWith("package:")
                    || label.startsWith("profile:")
                    || label.startsWith("hidden-info:")) {
                classifierSignals++;
                classifierTotal += Math.max(0, contribution.getValue());
            }
        }
        return classifierSignals >= 5 && classifierTotal >= 180;
    }

    private static boolean hasAvailableOrAction(RoleState roleState, Set<DeckRole> actionRoles, DeckRole... roles) {
        for (DeckRole role : roles) {
            if (actionRoles.contains(role) || roleState.hasAvailable(role)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isCombatStep(Game game) {
        PhaseStep step = game == null ? null : game.getTurnStepType();
        return isCombatStep(step);
    }

    private static boolean isMainPhase(PhaseStep step) {
        return step == PhaseStep.PRECOMBAT_MAIN || step == PhaseStep.POSTCOMBAT_MAIN;
    }

    private static boolean isCombatStep(PhaseStep step) {
        return step != null
                && !step.isBefore(PhaseStep.BEGIN_COMBAT)
                && !step.isAfter(PhaseStep.END_COMBAT);
    }

    private static boolean isLateManaDevelopmentAction(Game game, Set<DeckRole> actionRoles, RoleState roleState) {
        return (actionRoles.contains(DeckRole.MANA_FIXING) || actionRoles.contains(DeckRole.RAMP))
                && roleState.landPermanentCount >= 6
                && game != null
                && game.getTurnNum() >= 7;
    }

    private static boolean isAlternateCardAbility(Game game, Ability action) {
        return action != null
                && !action.getAbilityType().isPlayCardAbility()
                && action.getSourceObject(game) instanceof Card;
    }

    private static boolean hasCounterSpellText(String rule) {
        return rule.contains("counter target");
    }

    private static boolean hasNonCounterProtectionText(String rule) {
        return grantsKeyword(rule, "hexproof")
                || grantsKeyword(rule, "indestructible")
                || grantsProtectionFrom(rule)
                || rule.contains("prevent all")
                || rule.contains("regenerate ");
    }

    private static boolean grantsKeyword(String rule, String keyword) {
        return !rule.contains("lose " + keyword)
                && !rule.contains("loses " + keyword)
                && !rule.contains("can't have " + keyword)
                && !rule.contains("can't gain " + keyword)
                && (rule.contains("gain " + keyword)
                || rule.contains("gains " + keyword)
                || rule.contains("have " + keyword)
                || rule.contains("has " + keyword));
    }

    private static boolean grantsProtectionFrom(String rule) {
        return !rule.contains("lose protection from")
                && !rule.contains("loses protection from")
                && !rule.contains("can't have protection from")
                && (rule.contains("gain protection from")
                || rule.contains("gains protection from")
                || rule.contains("have protection from")
                || rule.contains("has protection from")
                || rule.contains("protection from"));
    }

    private Set<DeckRole> classifyAction(Game game, Ability action) {
        MageObject sourceObject = action.getSourceObject(game);
        if (sourceObject instanceof Card && action.getAbilityType().isPlayCardAbility()) {
            Card sourceCard = (Card) sourceObject;
            Set<DeckRole> roles = DeckProfileService.classify(sourceCard);
            if (sourceCard.isLand()) {
                return landPlayRolesOnly(roles);
            }
            Set<DeckRole> selectedEffectRoles = classifySelectedEffects(action, getActionRule(action), sourceObject);
            if (!selectedEffectRoles.isEmpty() && !sourceCard.isPermanent(game)) {
                return normalizeSelectedSpellRoles(sourceCard, selectedEffectRoles, roles);
            }
            return normalizeCardPlayRoles(sourceCard, roles);
        }
        String rule = getActionRule(action);
        if (sourceObject instanceof Card && isAlternateCastCardAbility(rule)) {
            return DeckProfileService.classify((Card) sourceObject);
        }
        Set<DeckRole> roles = EnumSet.noneOf(DeckRole.class);
        for (Effect effect : action.getAllEffects()) {
            addRoleForOutcome(roles, effect.getOutcome(), rule, sourceObject);
        }
        return roles;
    }

    private static Set<DeckRole> classifySelectedEffects(Ability action, String rule, MageObject sourceObject) {
        Set<DeckRole> roles = EnumSet.noneOf(DeckRole.class);
        if (action == null) {
            return roles;
        }
        for (Effect effect : action.getAllEffects()) {
            addRoleForOutcome(roles, effect.getOutcome(), rule, sourceObject);
        }
        return roles;
    }

    private Set<String> classifyActionMechanics(Game game, Ability action, String sourceName) {
        MageObject sourceObject = action.getSourceObject(game);
        if (sourceObject instanceof Card && action.getAbilityType().isPlayCardAbility()) {
            return DeckProfileService.classifyMechanics((Card) sourceObject);
        }
        if (sourceObject instanceof Card && isAlternateCastCardAbility(getActionRule(action))) {
            return DeckProfileService.classifyMechanics((Card) sourceObject);
        }
        if (sourceName == null || sourceName.isEmpty()) {
            return Collections.emptySet();
        }
        Map<String, Integer> mechanics = new LinkedHashMap<>();
        mechanics.putAll(profile.getMainDeck().getCardMechanics(sourceName));
        mechanics.putAll(profile.getCommandZone().getCardMechanics(sourceName));
        if (mechanics.isEmpty()) {
            return Collections.emptySet();
        }
        return new java.util.TreeSet<>(mechanics.keySet());
    }

    private static Set<DeckRole> landPlayRolesOnly(Set<DeckRole> roles) {
        if (roles.isEmpty()) {
            return roles;
        }
        Set<DeckRole> filtered = EnumSet.noneOf(DeckRole.class);
        if (roles.contains(DeckRole.MANA_FIXING)) {
            filtered.add(DeckRole.MANA_FIXING);
        }
        if (roles.contains(DeckRole.RAMP)) {
            filtered.add(DeckRole.RAMP);
        }
        return filtered;
    }

    private static Set<DeckRole> normalizeCardPlayRoles(Card card, Set<DeckRole> roles) {
        if (roles.isEmpty()) {
            return roles;
        }
        EnumSet<DeckRole> normalized = EnumSet.copyOf(roles);
        if (normalized.contains(DeckRole.REMOVAL) && !hasRemovalPlayText(card)) {
            normalized.remove(DeckRole.REMOVAL);
        }
        if (isSelfSacrificeUtilityCard(card)) {
            normalized.remove(DeckRole.SACRIFICE_OUTLET);
            if (!normalized.contains(DeckRole.TOKEN_MAKER) && !normalized.contains(DeckRole.DEATH_PAYOFF)) {
                normalized.remove(DeckRole.SACRIFICE_FODDER);
            }
        }
        return normalized;
    }

    private static Set<DeckRole> normalizeSelectedSpellRoles(Card card, Set<DeckRole> selectedRoles, Set<DeckRole> sourceRoles) {
        EnumSet<DeckRole> normalized = EnumSet.copyOf(selectedRoles);
        if (sourceRoles.contains(DeckRole.X_SPELL)) {
            normalized.add(DeckRole.X_SPELL);
        }
        if (sourceRoles.contains(DeckRole.COMBAT_TRICK) && hasCombatTrickPlayText(card)) {
            normalized.add(DeckRole.COMBAT_TRICK);
        }
        return normalized;
    }

    private static boolean hasCombatTrickPlayText(Card card) {
        String text = getCardRulesAndCosts(card);
        return text.contains("gets +")
                || text.contains("gains first strike")
                || text.contains("gains double strike")
                || text.contains("gains trample")
                || text.contains("gains deathtouch")
                || text.contains("gains lifelink")
                || text.contains("fight");
    }

    private static boolean hasRemovalPlayText(Card card) {
        String text = getCardRulesAndCosts(card);
        return text.contains("destroy target")
                || text.contains("exile target")
                || text.contains("deals damage to target")
                || text.contains("deal damage to target")
                || text.contains("fight target")
                || text.contains("fights target")
                || text.contains("target creature gets -")
                || text.contains("target opponent sacrifices")
                || text.contains("target player sacrifices")
                || text.contains("each opponent sacrifices")
                || text.contains("each player sacrifices")
                || text.contains("destroy all")
                || text.contains("exile all")
                || text.contains("damage to each creature");
    }

    private static boolean isSelfSacrificeUtilityCard(Card card) {
        String text = getCardRulesAndCosts(card);
        if (!text.contains("sacrifice")) {
            return false;
        }
        boolean selfSacrifice = text.contains("sacrifice {this}") || text.contains("sacrifice this");
        if (!selfSacrifice) {
            return false;
        }
        return !text.contains("sacrifice another")
                && !text.contains("sacrifice a creature")
                && !text.contains("sacrifice any number")
                && !text.contains("sacrifice x")
                && !text.contains("sacrifice a permanent")
                && !text.contains("sacrifice an artifact")
                && !text.contains("sacrifice an enchantment");
    }

    private static String getCardRulesAndCosts(Card card) {
        StringBuilder sb = new StringBuilder(String.join(" ", card.getRules()));
        for (Ability ability : card.getAbilities()) {
            for (Cost cost : ability.getCosts()) {
                sb.append(' ').append(cost.getText());
            }
        }
        return sb.toString().toLowerCase(Locale.ROOT);
    }

    private static String getActionRule(Ability action) {
        if (action == null) {
            return "";
        }
        try {
            String rule = action.getRule();
            return rule == null ? "" : rule.toLowerCase(Locale.ROOT);
        } catch (RuntimeException e) {
            return "";
        }
    }

    private static boolean isAlternateCastCardAbility(String rule) {
        return rule.contains("cast this card")
                || rule.startsWith("flashback")
                || rule.startsWith("jump-start")
                || rule.startsWith("retrace")
                || rule.startsWith("escape")
                || rule.startsWith("aftermath");
    }

    private static String getSourceName(Game game, Ability action) {
        MageObject sourceObject = action.getSourceObject(game);
        if (sourceObject == null || sourceObject.getName() == null || sourceObject.getName().isEmpty()) {
            return null;
        }
        return sourceObject.getName();
    }

    private boolean isCommanderAction(Game game, Ability action) {
        try {
            Player player = game.getPlayer(playerId);
            return player != null
                    && game.getCommandersIds(player, CommanderCardType.COMMANDER_OR_OATHBREAKER, true)
                    .contains(action.getSourceId());
        } catch (RuntimeException e) {
            return false;
        }
    }

    private int getCommanderTaxMana(Game game, Ability action, boolean commanderAction) {
        if (!commanderAction || action.getZone() != Zone.COMMAND) {
            return 0;
        }
        try {
            CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
            if (watcher == null) {
                return 0;
            }
            UUID mainCardId = CardUtil.getMainCardId(game, action.getSourceId());
            return 2 * watcher.getPlaysCount(mainCardId);
        } catch (RuntimeException e) {
            return 0;
        }
    }

    private static int getCommanderTaxMana(Game game, Player player, MageObject object) {
        if (game == null || player == null || object == null || !game.isCommanderObject(player, object)) {
            return 0;
        }
        try {
            CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
            if (watcher == null) {
                return 0;
            }
            UUID mainCardId = CardUtil.getMainCardId(game, object.getId());
            return 2 * watcher.getPlaysCount(mainCardId);
        } catch (RuntimeException e) {
            return 0;
        }
    }

    private static Collection<Card> collectMainDeckCards(Game game, Player player) {
        Map<UUID, Card> cards = new LinkedHashMap<>();
        Set<UUID> commanderIds = commanderIds(game, player);
        addCards(cards, player.getLibrary().getCards(game), commanderIds);
        addCards(cards, player.getHand().getCards(game), commanderIds);
        addCards(cards, player.getGraveyard().getCards(game), commanderIds);
        addCards(cards, game.getExile().getCardsOwned(game, player.getId()), commanderIds);
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(player.getId())) {
            addCard(cards, permanent, commanderIds);
        }
        return cards.values();
    }

    private static Collection<Card> collectCommanderCards(Game game, Player player) {
        try {
            return game.getCommanderCardsFromAnyZones(player, CommanderCardType.COMMANDER_OR_OATHBREAKER, Zone.ALL);
        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
    }

    private static Set<UUID> commanderIds(Game game, Player player) {
        try {
            return game.getCommandersIds(player, CommanderCardType.COMMANDER_OR_OATHBREAKER, true);
        } catch (RuntimeException e) {
            return Collections.emptySet();
        }
    }

    private static void addCards(Map<UUID, Card> cards, Collection<? extends Card> source, Set<UUID> excludedIds) {
        for (Card card : source) {
            addCard(cards, card, excludedIds);
        }
    }

    private static void addCard(Map<UUID, Card> cards, Card card, Set<UUID> excludedIds) {
        if (card == null || excludedIds.contains(card.getId())) {
            return;
        }
        cards.putIfAbsent(card.getId(), card);
    }

    private static void addRoleForOutcome(Set<DeckRole> roles, Outcome outcome, String rule, MageObject sourceObject) {
        if (outcome == null) {
            return;
        }
        switch (outcome) {
            case DrawCard:
                roles.add(DeckRole.CARD_DRAW);
                break;
            case DestroyPermanent:
            case Exile:
            case Removal:
            case Damage:
                roles.add(DeckRole.REMOVAL);
                break;
            case PutCreatureInPlay:
                if (isGraveyardRecursionText(rule)) {
                    roles.add(DeckRole.GRAVEYARD_RECURSION);
                    if (sourceObject instanceof Card && ((Card) sourceObject).isCreature()
                            && ((Card) sourceObject).getManaValue() <= 2) {
                        roles.add(DeckRole.SACRIFICE_FODDER);
                    }
                    break;
                }
                if (!isTokenMakerText(rule)) {
                    break;
                }
                roles.add(DeckRole.TOKEN_MAKER);
                roles.add(DeckRole.SACRIFICE_FODDER_PROVIDER);
                break;
            case PutManaInPool:
            case PutLandInPlay:
                roles.add(DeckRole.RAMP);
                break;
            case GainLife:
                roles.add(DeckRole.LIFE_GAIN);
                break;
            case Protect:
            case PreventDamage:
            case Regenerate:
                roles.add(DeckRole.COUNTER_OR_PROTECTION);
                break;
            default:
                break;
        }
    }

    private static boolean isTokenMakerText(String rule) {
        return rule.contains("create ") && rule.contains(" token");
    }

    private static boolean isGraveyardRecursionText(String rule) {
        return (rule.contains("from your graveyard") || rule.contains("from a graveyard"))
                && (rule.contains("return") || rule.contains("cast") || rule.contains("put"));
    }

    private static String emptyToDefault(String value, String defaultValue) {
        return value == null || value.isEmpty() ? defaultValue : value;
    }

    private static final class RoleState {
        private final Map<DeckRole, Integer> onlineCounts;
        private final Map<DeckRole, Integer> availableCounts;
        private final Map<DeckRole, Integer> remainingLibraryCounts;
        private final int remainingLibrarySize;
        private final int landPermanentCount;
        private final int handSize;

        private RoleState(Map<DeckRole, Integer> onlineCounts,
                          Map<DeckRole, Integer> availableCounts,
                          Map<DeckRole, Integer> remainingLibraryCounts,
                          int remainingLibrarySize,
                          int landPermanentCount,
                          int handSize) {
            this.onlineCounts = onlineCounts;
            this.availableCounts = availableCounts;
            this.remainingLibraryCounts = remainingLibraryCounts;
            this.remainingLibrarySize = remainingLibrarySize;
            this.landPermanentCount = landPermanentCount;
            this.handSize = handSize;
        }

        private static RoleState fromGame(Game game, UUID playerId) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                return new RoleState(new EnumMap<>(DeckRole.class), new EnumMap<>(DeckRole.class), new EnumMap<>(DeckRole.class), 0, 0, 0);
            }
            Map<DeckRole, Integer> onlineCounts = new EnumMap<>(DeckRole.class);
            Map<DeckRole, Integer> availableCounts = new EnumMap<>(DeckRole.class);
            addRoleCounts(availableCounts, player.getHand().getCards(game));
            int landPermanentCount = 0;
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
                if (permanent.isLand(game)) {
                    landPermanentCount++;
                }
                addRoleCounts(onlineCounts, Collections.singleton(permanent));
                addRoleCounts(availableCounts, Collections.singleton(permanent));
            }
            addRoleCounts(availableCounts, collectCommanderCards(game, player));

            Map<DeckRole, Integer> libraryCounts = new EnumMap<>(DeckRole.class);
            List<Card> libraryCards = player.getLibrary().getCards(game);
            addRoleCounts(libraryCounts, libraryCards);
            return new RoleState(
                    onlineCounts,
                    availableCounts,
                    libraryCounts,
                    libraryCards.size(),
                    landPermanentCount,
                    player.getHand().size()
            );
        }

        private boolean hasOnline(DeckRole role) {
            return getOnlineCount(role) > 0;
        }

        private int getOnlineCount(DeckRole role) {
            return onlineCounts.getOrDefault(role, 0);
        }

        private boolean hasAvailable(DeckRole role) {
            return availableCounts.getOrDefault(role, 0) > 0;
        }

        private int getRemainingLibraryCount(DeckRole role) {
            return remainingLibraryCounts.getOrDefault(role, 0);
        }

        private boolean hasProtectableBoard() {
            return hasOnline(DeckRole.THREAT)
                    || hasOnline(DeckRole.CHEAP_THREAT)
                    || hasOnline(DeckRole.LARGE_THREAT)
                    || hasOnline(DeckRole.SACRIFICE_OUTLET)
                    || hasOnline(DeckRole.DEATH_PAYOFF)
                    || hasOnline(DeckRole.LIFE_GAIN_PAYOFF)
                    || hasOnline(DeckRole.PLUS_ONE_COUNTER_PAYOFF)
                    || hasOnline(DeckRole.X_SPELL_PAYOFF)
                    || hasOnline(DeckRole.ARTIFACT_OR_ENCHANTMENT_PAYOFF);
        }

        private static void addRoleCounts(Map<DeckRole, Integer> counts, Collection<? extends Card> cards) {
            for (Card card : cards) {
                for (DeckRole role : DeckProfileService.classify(card)) {
                    counts.put(role, counts.getOrDefault(role, 0) + 1);
                }
            }
        }
    }

    private static final class ActionContext {
        private final PhaseStep step;
        private final boolean mainPhase;
        private final boolean hasTargets;
        private final boolean hasFriendlyTarget;
        private final boolean hasOpponentTarget;
        private final boolean hasUnknownTarget;
        private final boolean targetsStackObject;
        private final boolean hasCounterSpellText;
        private final boolean hasNonCounterProtectionText;
        private final boolean targetsFriendlyCommander;
        private final int friendlyCommanderTaxMana;

        private ActionContext(PhaseStep step,
                              boolean hasTargets,
                              boolean hasFriendlyTarget,
                              boolean hasOpponentTarget,
                              boolean hasUnknownTarget,
                              boolean targetsStackObject,
                              boolean hasCounterSpellText,
                              boolean hasNonCounterProtectionText,
                              boolean targetsFriendlyCommander,
                              int friendlyCommanderTaxMana) {
            this.step = step;
            this.mainPhase = isMainPhase(step);
            this.hasTargets = hasTargets;
            this.hasFriendlyTarget = hasFriendlyTarget;
            this.hasOpponentTarget = hasOpponentTarget;
            this.hasUnknownTarget = hasUnknownTarget;
            this.targetsStackObject = targetsStackObject;
            this.hasCounterSpellText = hasCounterSpellText;
            this.hasNonCounterProtectionText = hasNonCounterProtectionText;
            this.targetsFriendlyCommander = targetsFriendlyCommander;
            this.friendlyCommanderTaxMana = friendlyCommanderTaxMana;
        }

        private static ActionContext fromGame(Game game, Ability action, UUID playerId) {
            PhaseStep step = game == null ? null : game.getTurnStepType();
            if (game == null || action == null) {
                return new ActionContext(step, false, false, false, false, false, false, false, false, 0);
            }
            Player player = game.getPlayer(playerId);
            boolean hasTargets = false;
            boolean hasFriendlyTarget = false;
            boolean hasOpponentTarget = false;
            boolean hasUnknownTarget = false;
            boolean targetsStackObject = false;
            boolean targetsFriendlyCommander = false;
            int friendlyCommanderTaxMana = 0;
            String rule = getActionRule(action);
            for (Target target : action.getTargets()) {
                for (UUID targetId : target.getTargets()) {
                    if (targetId == null) {
                        continue;
                    }
                    hasTargets = true;
                    if (game.getStack().getStackObject(targetId) != null) {
                        targetsStackObject = true;
                        continue;
                    }
                    if (targetId.equals(playerId)) {
                        hasFriendlyTarget = true;
                        continue;
                    }
                    Player targetPlayer = game.getPlayer(targetId);
                    if (targetPlayer != null) {
                        if (targetPlayer.getId().equals(playerId)) {
                            hasFriendlyTarget = true;
                        } else {
                            hasOpponentTarget = true;
                        }
                        continue;
                    }
                    Permanent targetPermanent = game.getPermanent(targetId);
                    if (targetPermanent != null) {
                        if (targetPermanent.isControlledBy(playerId)) {
                            hasFriendlyTarget = true;
                            if (player != null && game.isCommanderObject(player, targetPermanent)) {
                                targetsFriendlyCommander = true;
                                friendlyCommanderTaxMana = Math.max(
                                        friendlyCommanderTaxMana,
                                        getCommanderTaxMana(game, player, targetPermanent)
                                );
                            }
                        } else {
                            hasOpponentTarget = true;
                        }
                    } else {
                        hasUnknownTarget = true;
                    }
                }
            }
            return new ActionContext(
                    step,
                    hasTargets,
                    hasFriendlyTarget,
                    hasOpponentTarget,
                    hasUnknownTarget,
                    targetsStackObject,
                    hasCounterSpellText(rule),
                    hasNonCounterProtectionText(rule),
                    targetsFriendlyCommander,
                    friendlyCommanderTaxMana
            );
        }
    }

    private static final class RoleProbability {
        private final DeckRole role;
        private final int remainingCount;
        private final int value;

        private RoleProbability(DeckRole role, int remainingCount, int value) {
            this.role = role;
            this.remainingCount = remainingCount;
            this.value = value;
        }

        private int getValue() {
            return value;
        }
    }

    private static final class DrawValue {
        private final int value;
        private final String detail;

        private DrawValue(int value, String detail) {
            this.value = value;
            this.detail = detail;
        }

        private static DrawValue none() {
            return new DrawValue(0, "");
        }
    }
}
