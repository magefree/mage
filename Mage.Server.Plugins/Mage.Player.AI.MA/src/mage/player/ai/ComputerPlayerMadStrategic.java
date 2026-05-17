package mage.player.ai;

import mage.abilities.Ability;
import mage.abilities.common.PassAbility;
import mage.abilities.costs.CastCostPlan;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.scoring.AiScoringContext;
import mage.player.ai.scoring.AiScoringEngine;
import mage.player.ai.scoring.AiScoringEngineFactory;
import mage.player.ai.util.CombatUtil;
import mage.players.Player;
import mage.target.Target;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Traced Mad AI with deck-profile strategy heuristics enabled.
 */
public class ComputerPlayerMadStrategic extends ComputerPlayerControllableProxy {

    private static final String CANDIDATE_BUDGET_MIN_MS_PROPERTY = "xmage.ai.strategy.candidateBudgetMinMs";
    private static final String CANDIDATE_BUDGET_MAX_MS_PROPERTY = "xmage.ai.strategy.candidateBudgetMaxMs";
    private static final String ATTACK_COMBINATION_LIMIT_PROPERTY = "xmage.ai.strategy.attackCombinationLimit";
    private static final String TARGET_OPTIONS_PER_ABILITY_PROPERTY = "xmage.ai.strategy.targetOptionsPerAbility";
    private static final String SEARCH_CACHE_MAX_ENTRIES_PROPERTY = "xmage.ai.strategy.searchCacheMaxEntries";
    private static final String MAX_THINK_TIME_SECS_PROPERTY = "xmage.ai.strategy.maxThinkTimeSecs";
    private static final String MAX_NODES_PROPERTY = "xmage.ai.strategy.maxNodes";
    private static final String SEARCH_BUDGET_ENABLED_PROPERTY = "xmage.ai.strategy.searchBudget.enabled";
    private static final String SEARCH_BUDGET_APPLY_PROPERTY = "xmage.ai.strategy.searchBudget.apply";
    private static final String SEARCH_BUDGET_MIN_DEPTH_PROPERTY = "xmage.ai.strategy.searchBudget.minDepth";
    private static final String SEARCH_BUDGET_LARGE_BOARD_THRESHOLD_PROPERTY = "xmage.ai.strategy.searchBudget.largeBoardThreshold";
    private static final String SEARCH_BUDGET_HUGE_BOARD_THRESHOLD_PROPERTY = "xmage.ai.strategy.searchBudget.hugeBoardThreshold";
    private static final String SEARCH_BUDGET_HEAP_GUARD_ENABLED_PROPERTY = "xmage.ai.strategy.searchBudget.heapGuard.enabled";
    private static final String SEARCH_BUDGET_HEAP_GUARD_MAX_USED_RATIO_PROPERTY = "xmage.ai.strategy.searchBudget.heapGuard.maxUsedRatio";
    private static final String SEARCH_BUDGET_HEAP_GUARD_MIN_FREE_MB_PROPERTY = "xmage.ai.strategy.searchBudget.heapGuard.minFreeMb";
    private static final String STRATEGY_ENABLED_PROPERTY = "xmage.ai.strategy";
    private static final String STRATEGY_TRACE_ONLY_PROPERTY = "xmage.ai.strategy.traceOnly";
    private static final String STRATEGY_MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.maxScoreModifier";
    private static final String HAND_QUALITY_ENABLED_PROPERTY = "xmage.ai.strategy.handQuality.enabled";
    private static final String HAND_QUALITY_APPLY_PROPERTY = "xmage.ai.strategy.handQuality.apply";
    private static final String CAST_VARIANTS_ENABLED_PROPERTY = "xmage.ai.strategy.castVariants.enabled";
    private static final String CAST_VARIANTS_APPLY_PROPERTY = "xmage.ai.strategy.castVariants.apply";

    private transient AiScoringEngine scoringEngine;
    private transient UUID scoringEnginePlayerId;
    private transient AiScoringEngineFactory scoringEngineFactory;
    private transient int searchBudgetOriginalMaxDepth;
    private transient int searchBudgetOriginalMaxNodes;

    public ComputerPlayerMadStrategic(String name, RangeOfInfluence range, int skill) {
        super(name, range, skill);
        applyStrategicSearchProperties();
    }

    protected ComputerPlayerMadStrategic(java.util.UUID id, int skill) {
        super(id, skill);
        applyStrategicSearchProperties();
    }

    public ComputerPlayerMadStrategic(final ComputerPlayerMadStrategic player) {
        super(player);
    }

    @Override
    public ComputerPlayerMadStrategic copy() {
        return new ComputerPlayerMadStrategic(this);
    }

    private void applyStrategicSearchProperties() {
        this.maxThinkTimeSecs = Integer.getInteger(MAX_THINK_TIME_SECS_PROPERTY, this.maxThinkTimeSecs);
        this.maxNodes = Integer.getInteger(MAX_NODES_PROPERTY, this.maxNodes);
    }

    @Override
    protected AiDecisionTrace createDecisionTrace() {
        return JsonlAiDecisionTrace.createIfEnabled();
    }

    @Override
    protected long getTopLevelCandidateBudgetMillis(int actionCount, int actionNumber, int remainingActions, long remainingDecisionMillis) {
        if (remainingDecisionMillis <= 0) {
            return 1;
        }
        long fairShare = remainingDecisionMillis / Math.max(1, remainingActions);
        long minBudget = Long.getLong(CANDIDATE_BUDGET_MIN_MS_PROPERTY, 250L);
        long maxBudget = Long.getLong(CANDIDATE_BUDGET_MAX_MS_PROPERTY, 3000L);
        return Math.max(minBudget, Math.min(maxBudget, fairShare));
    }

    @Override
    protected int getMaxSimulatedAttackCombinations() {
        return Integer.getInteger(ATTACK_COMBINATION_LIMIT_PROPERTY, 256);
    }

    @Override
    protected int getMaxSimulatedTargetOptionsPerAbility() {
        return Integer.getInteger(TARGET_OPTIONS_PER_ABILITY_PROPERTY, 256);
    }

    @Override
    protected int getMaxSearchCacheEntries() {
        return Integer.getInteger(SEARCH_CACHE_MAX_ENTRIES_PROPERTY, 25_000);
    }

    @Override
    protected int getMaxSimulatedNodesPerError() {
        int margin = Math.max(100, maxNodes / 10);
        return Math.max(super.getMaxSimulatedNodesPerError(), maxNodes + margin);
    }

    @Override
    protected boolean useStableMinimaxBestChildSelection() {
        return true;
    }

    @Override
    protected boolean shouldUseCombatDamageForAttackHeuristics() {
        return true;
    }

    @Override
    protected boolean shouldKeepDefenseReserve(Game game) {
        return true;
    }

    @Override
    protected boolean useStablePriorityFallbackSelection() {
        return true;
    }

    @Override
    protected boolean shouldPassSimulatedPlayersOnSimulatedGame() {
        return true;
    }

    @Override
    protected boolean shouldPassPriorityWhenCombatCanWinAllOpponents(Game game) {
        return true;
    }

    @Override
    protected PossibleTargetsSelector createPossibleTargetsSelector(Outcome outcome, Target target,
                                                                    UUID abilityControllerId, Ability source, Game game) {
        return new PossibleTargetsSelector(
                outcome,
                target,
                abilityControllerId,
                source,
                game,
                shouldUseHandQualityDiscardTargeting()
        );
    }

    @Override
    protected boolean shouldUseHandQualityDiscardTargeting() {
        return isStrategicBehaviorApplied(HAND_QUALITY_ENABLED_PROPERTY, HAND_QUALITY_APPLY_PROPERTY);
    }

    @Override
    protected boolean shouldUseStrategicCastVariants() {
        return isStrategicBehaviorApplied(CAST_VARIANTS_ENABLED_PROPERTY, CAST_VARIANTS_APPLY_PROPERTY);
    }

    @Override
    protected boolean shouldReusePreviousActionsChain(Game game) {
        if (!shouldUseStrategicCastVariants()) {
            return true;
        }
        return game != null && !game.getStack().isEmpty();
    }

    @Override
    protected void optimize(Game game, List<Ability> allActions) {
        boolean preferSourcePreservingCastVariant = shouldPreferSourcePreservingCastVariant(game);
        super.optimize(game, allActions);
        if (preferSourcePreservingCastVariant) {
            Set<UUID> sourcePreservingAbilityIds = new HashSet<>();
            for (Ability action : allActions) {
                if (CastCostPlan.preservesSource(action)) {
                    UUID familyId = getActionFamilyId(action);
                    if (familyId != null) {
                        sourcePreservingAbilityIds.add(familyId);
                    }
                }
            }
            if (!sourcePreservingAbilityIds.isEmpty()) {
                allActions.removeIf(action -> {
                    UUID familyId = getActionFamilyId(action);
                    return action instanceof PassAbility
                            || (familyId != null
                            && sourcePreservingAbilityIds.contains(familyId)
                            && !CastCostPlan.preservesSource(action));
                });
            }
        }
    }

    private boolean shouldPreferSourcePreservingCastVariant(Game game) {
        if (!shouldUseStrategicCastVariants()
                || game == null
                || !game.isActivePlayer(playerId)
                || game.getTurnStepType() != PhaseStep.PRECOMBAT_MAIN) {
            return false;
        }
        int lowestOpponentLife = lowestOpponentLife(game);
        int attackPower = maxAvailableAttackPower(game);
        return lowestOpponentLife < Integer.MAX_VALUE
                && attackPower > 0
                && attackPower < lowestOpponentLife;
    }

    private UUID getActionFamilyId(Ability action) {
        if (action == null) {
            return null;
        }
        UUID originalId = action.getOriginalId();
        return originalId == null ? action.getId() : originalId;
    }

    private int maxAvailableAttackPower(Game game) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return 0;
        }
        int maxPower = 0;
        for (UUID opponentId : game.getOpponents(playerId, true)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null || !opponent.isInGame() || opponent.hasLost()) {
                continue;
            }
            int power = 0;
            for (Permanent attacker : player.getAvailableAttackers(opponentId, game)) {
                if (attacker != null) {
                    power += Math.max(0, attacker.getPower().getValue());
                }
            }
            maxPower = Math.max(maxPower, power);
        }
        return maxPower;
    }

    private int lowestOpponentLife(Game game) {
        int lowestLife = Integer.MAX_VALUE;
        for (UUID opponentId : game.getOpponents(playerId, true)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null && opponent.isInGame() && !opponent.hasLost()) {
                lowestLife = Math.min(lowestLife, opponent.getLife());
            }
        }
        return lowestLife;
    }

    @Override
    protected List<Permanent> chooseLethalAttackers(Game game, List<Permanent> attackersList,
                                                    List<Permanent> possibleBlockers, Player defender) {
        return CombatUtil.canKillOpponentWithMinimalAttackers(game, attackersList, possibleBlockers, defender);
    }

    private static boolean isStrategicBehaviorApplied(String enabledProperty, String applyProperty) {
        return Boolean.parseBoolean(System.getProperty(STRATEGY_ENABLED_PROPERTY, "true"))
                && !Boolean.getBoolean(STRATEGY_TRACE_ONLY_PROPERTY)
                && Boolean.parseBoolean(System.getProperty(enabledProperty, "true"))
                && Boolean.getBoolean(applyProperty);
    }

    @Override
    protected StrategicSearchBudgetPolicy.Decision createSearchBudget(Game game) {
        if (game == null || !Boolean.parseBoolean(System.getProperty(SEARCH_BUDGET_ENABLED_PROPERTY, "true"))) {
            return null;
        }
        Runtime runtime = Runtime.getRuntime();
        long maxHeap = Math.max(1L, runtime.maxMemory());
        long usedHeap = runtime.totalMemory() - runtime.freeMemory();
        long freeHeapMb = Math.max(0L, (maxHeap - usedHeap) / (1024L * 1024L));
        StrategicSearchBudgetPolicy.Decision decision = StrategicSearchBudgetPolicy.decide(new StrategicSearchBudgetPolicy.Input(
                isCommanderGame(game),
                game.getTurnNum(),
                game.getBattlefield().getAllPermanents().size(),
                game.getStack().size(),
                -1,
                maxDepth,
                maxNodes,
                Integer.getInteger(SEARCH_BUDGET_MIN_DEPTH_PROPERTY, 4),
                Integer.getInteger(SEARCH_BUDGET_LARGE_BOARD_THRESHOLD_PROPERTY, 40),
                Integer.getInteger(SEARCH_BUDGET_HUGE_BOARD_THRESHOLD_PROPERTY, 55),
                Boolean.parseBoolean(System.getProperty(SEARCH_BUDGET_HEAP_GUARD_ENABLED_PROPERTY, "true")),
                (double) usedHeap / (double) maxHeap,
                freeHeapMb,
                Double.parseDouble(System.getProperty(SEARCH_BUDGET_HEAP_GUARD_MAX_USED_RATIO_PROPERTY, "0.82")),
                Long.getLong(SEARCH_BUDGET_HEAP_GUARD_MIN_FREE_MB_PROPERTY, 512L)
        ));
        return Boolean.parseBoolean(System.getProperty(SEARCH_BUDGET_APPLY_PROPERTY, "true"))
                ? decision
                : null;
    }

    @Override
    protected boolean shouldStopImmediatelyForSearchBudget(StrategicSearchBudgetPolicy.Decision searchBudget) {
        // Heap pressure should reduce strategic search depth, not skip the root legal-action choice entirely.
        return false;
    }

    @Override
    protected String adjustSearchBudgetForLegalActions(Game game, int legalActionCount) {
        if (game == null
                || legalActionCount <= 25
                || !Boolean.parseBoolean(System.getProperty(SEARCH_BUDGET_ENABLED_PROPERTY, "true"))
                || !Boolean.parseBoolean(System.getProperty(SEARCH_BUDGET_APPLY_PROPERTY, "true"))) {
            return null;
        }
        int minDepth = Integer.getInteger(SEARCH_BUDGET_MIN_DEPTH_PROPERTY, 4);
        if (maxDepth <= minDepth) {
            return null;
        }
        maxDepth = Math.max(minDepth, maxDepth - 1);
        if (searchBudgetOriginalMaxDepth > 0 && searchBudgetOriginalMaxNodes > 0) {
            maxNodes = Math.max(1, Math.min(maxNodes, searchBudgetOriginalMaxNodes * maxDepth / searchBudgetOriginalMaxDepth));
        }
        return "search-budget:legal-action-pressure,search-budget:effective-depth,search-budget:max-nodes";
    }

    @Override
    protected boolean shouldSkipSimulationCopy(Game game, int depth, String reason) {
        if (super.shouldSkipSimulationCopy(game, depth, reason)) {
            return true;
        }
        if (!Boolean.parseBoolean(System.getProperty(SEARCH_BUDGET_HEAP_GUARD_ENABLED_PROPERTY, "true"))) {
            return false;
        }
        Runtime runtime = Runtime.getRuntime();
        long maxHeap = Math.max(1L, runtime.maxMemory());
        long usedHeap = runtime.totalMemory() - runtime.freeMemory();
        long freeHeapMb = Math.max(0L, (maxHeap - usedHeap) / (1024L * 1024L));
        double usedRatio = (double) usedHeap / (double) maxHeap;
        double maxUsedRatio = Double.parseDouble(System.getProperty(SEARCH_BUDGET_HEAP_GUARD_MAX_USED_RATIO_PROPERTY, "0.82"));
        long minFreeMb = Long.getLong(SEARCH_BUDGET_HEAP_GUARD_MIN_FREE_MB_PROPERTY, 512L);
        if (usedRatio >= maxUsedRatio || freeHeapMb < minFreeMb) {
            if (depth < maxDepth || !"priority-action".equals(reason)) {
                aiSearchStopReason = "search-budget:heap-pressure";
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean isCommanderGame(Game game) {
        return game.getGameType() != null
                && game.getGameType().toString() != null
                && game.getGameType().toString().toLowerCase().contains("commander");
    }

    @Override
    protected Integer addActionsTimed() {
        searchBudgetOriginalMaxDepth = maxDepth;
        searchBudgetOriginalMaxNodes = maxNodes;
        try {
            return super.addActionsTimed();
        } finally {
            searchBudgetOriginalMaxDepth = 0;
            searchBudgetOriginalMaxNodes = 0;
        }
    }

    @Override
    protected AiStrategyScore evaluateTopLevelCandidateStrategy(Game decisionGame, Game finalGame, Ability action, int baseScore) {
        if (!Boolean.parseBoolean(System.getProperty(STRATEGY_ENABLED_PROPERTY, "true"))) {
            return AiStrategyScore.none(baseScore);
        }
        AiScoringEngine engine = getScoringEngine();
        if (engine == null) {
            return AiStrategyScore.none(baseScore);
        }
        return engine.evaluate(AiScoringContext.candidate(decisionGame, finalGame, getId(), action, baseScore));
    }

    private AiScoringEngine getScoringEngine() {
        if (scoringEngine == null || !getId().equals(scoringEnginePlayerId)) {
            scoringEngine = getScoringEngineFactory().createStrategicEngine(
                    getId(),
                    Integer.getInteger(STRATEGY_MAX_MODIFIER_PROPERTY, 280),
                    !Boolean.getBoolean(STRATEGY_TRACE_ONLY_PROPERTY)
            );
            scoringEnginePlayerId = getId();
        }
        return scoringEngine;
    }

    private AiScoringEngineFactory getScoringEngineFactory() {
        if (scoringEngineFactory == null) {
            scoringEngineFactory = AiScoringEngineFactory.createDefault();
        }
        return scoringEngineFactory;
    }

    @Override
    public void cleanUpOnMatchEnd() {
        if (scoringEngine != null) {
            scoringEngine.cleanUpOnMatchEnd();
        }
        scoringEngine = null;
        scoringEnginePlayerId = null;
        scoringEngineFactory = null;
        super.cleanUpOnMatchEnd();
    }
}
