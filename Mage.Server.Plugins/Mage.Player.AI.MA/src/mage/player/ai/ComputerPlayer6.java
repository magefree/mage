package mage.player.ai;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.common.PassAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.SearchEffect;
import mage.abilities.keyword.*;
import mage.abilities.keyword.special.JohanVigilanceAbility;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.constants.Outcome;
import mage.constants.RangeOfInfluence;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.player.ai.ma.optimizers.TreeOptimizer;
import mage.player.ai.ma.optimizers.impl.*;
import mage.player.ai.score.GameStateEvaluator2;
import mage.player.ai.score.LegacyPositionEvaluator;
import mage.player.ai.score.PositionEvaluator;
import mage.player.ai.util.CombatInfo;
import mage.player.ai.util.CombatUtil;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.target.TargetCard;
import mage.util.CardUtil;
import mage.util.RandomUtil;
import mage.util.ThreadUtils;
import mage.util.XmageThreadFactory;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * AI: server side bot with game simulations (mad bot, part of implementation)
 *
 * @author nantuko, JayDi85
 */
public class ComputerPlayer6 extends ComputerPlayer {

    private static final Logger logger = Logger.getLogger(ComputerPlayer6.class);

    // TODO: add and research maxNodes logs, is it good to increase from 5000 to 50000 for better results?
    // TODO: increase maxNodes due AI skill level like max depth?
    private static final int MAX_SIMULATED_NODES_PER_CALC = 5000;
    private static final int MAX_SIMULATED_NODES_PER_ERROR = 5100; // TODO: debug only, set low value to find big calculations
    private static final int MAX_REAL_SCORE = Integer.MAX_VALUE - 1;
    private static final int MIN_REAL_SCORE = Integer.MIN_VALUE + 1;

    // same params as Executors.newFixedThreadPool
    // no needs errors check in afterExecute here cause that pool used for FutureTask with result check already
    private static final ExecutorService threadPoolSimulations = new ThreadPoolExecutor(
            COMPUTER_MAX_THREADS_FOR_SIMULATIONS,
            COMPUTER_MAX_THREADS_FOR_SIMULATIONS,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new XmageThreadFactory(ThreadUtils.THREAD_PREFIX_AI_SIMULATION_MAD)
    );
    protected int maxDepth;
    protected int maxNodes;
    protected int maxThinkTimeSecs;
    protected LinkedList<Ability> actions = new LinkedList<>();
    protected List<UUID> targets = new ArrayList<>();
    protected List<String> choices = new ArrayList<>();
    protected Combat combat;
    protected int currentScore;
    protected SimulationNode2 root;
    protected PositionEvaluator evaluator;
    protected transient AiDecisionTrace decisionTrace;
    protected long nextTraceDecisionId;
    protected transient long activeTraceDecisionId;
    protected transient long topLevelCandidateDeadlineMillis;
    protected transient boolean topLevelCandidateBudgetHit;
    protected transient Map<SearchCacheKey, Integer> searchCache;
    protected transient int searchCacheHits;
    protected transient int searchCacheMisses;
    protected transient List<AiCandidateEvaluation> advisoryCandidateEvaluations;
    protected transient boolean aiSearchStopRequested;
    protected transient String aiSearchStopReason;
    List<Permanent> attackersList = new ArrayList<>();
    List<Permanent> attackersToCheck = new ArrayList<>();

    protected Set<String> actionCache;
    protected static final List<TreeOptimizer> optimizers = new ArrayList<>();
    protected int lastLoggedTurn = 0; // for debug logs: mark start of the turn
    protected static final String BLANKS = "...............................................";

    static {
        optimizers.add(new WrongCodeUsageOptimizer());
        optimizers.add(new LevelUpOptimizer());
        optimizers.add(new EquipOptimizer());
        optimizers.add(new DiscardCardOptimizer());
        optimizers.add(new OutcomeOptimizer());
    }

    public ComputerPlayer6(String name, RangeOfInfluence range, int skill) {
        super(name, range);
        initSearchSettings(skill);
    }

    protected ComputerPlayer6(UUID id, int skill) {
        super(id);
        initSearchSettings(skill);
    }

    private void initSearchSettings(int skill) {
        if (skill < 4) {
            maxDepth = 4; // TODO: can be increased to support better calculations? (example = 8, skill * 2)
        } else {
            maxDepth = skill;
        }
        maxThinkTimeSecs = skill * 3;
        maxNodes = MAX_SIMULATED_NODES_PER_CALC;
        this.actionCache = new HashSet<>();
        this.evaluator = LegacyPositionEvaluator.INSTANCE;
        this.decisionTrace = createDecisionTrace();
    }

    public ComputerPlayer6(final ComputerPlayer6 player) {
        super(player);
        this.maxDepth = player.maxDepth;
        this.currentScore = player.currentScore;
        this.evaluator = player.evaluator;
        this.decisionTrace = NoOpAiDecisionTrace.INSTANCE;
        this.nextTraceDecisionId = player.nextTraceDecisionId;
        this.activeTraceDecisionId = 0;
        if (player.combat != null) {
            this.combat = player.combat.copy();
        }
        this.actions.addAll(player.actions);
        this.targets.addAll(player.targets);
        this.choices.addAll(player.choices);
        this.actionCache = player.actionCache;
    }

    /**
     * Change simulation timeout - used for AI stability tests only
     */
    public void setMaxThinkTimeSecs(int maxThinkTimeSecs) {
        this.maxThinkTimeSecs = maxThinkTimeSecs;
    }

    @Override
    public ComputerPlayer6 copy() {
        return new ComputerPlayer6(this);
    }

    protected void printBattlefieldScore(Game game, String info) {
        if (logger.isInfoEnabled()) {
            logger.info("");
            logger.info("=================== " + info + ", turn " + game.getTurnNum() + ", " + game.getPlayer(game.getPriorityPlayerId()).getName() + " ===================");
            logger.info("[Stack]: " + game.getStack());
            printBattlefieldScore(game, playerId);
            for (UUID opponentId : game.getOpponents(playerId)) {
                printBattlefieldScore(game, opponentId);
            }
        }
    }

    protected void printBattlefieldScore(Game game, UUID playerId) {
        // hand
        Player player = game.getPlayer(playerId);
        GameStateEvaluator2.PlayerEvaluateScore score = evaluator.evaluate(playerId, game);
        logger.info(new StringBuilder("[").append(game.getPlayer(playerId).getName()).append("]")
                .append(", life = ").append(player.getLife())
                .append(", score = ").append(score.getTotalScore())
                .append(" (").append(score.getPlayerInfoFull()).append(")")
                .toString());
        String cardsInfo = player.getHand().getCards(game).stream()
                .map(card -> card.getName() + ":" + GameStateEvaluator2.HAND_CARD_SCORE) // TODO: add card score here after implement
                .collect(Collectors.joining("; "));
        StringBuilder sb = new StringBuilder("-> Hand: [")
                .append(cardsInfo)
                .append("]");
        logger.info(sb.toString());

        // battlefield
        sb.setLength(0);
        String ownPermanentsInfo = game.getBattlefield().getAllPermanents().stream()
                .filter(p -> p.isOwnedBy(player.getId()))
                .map(p -> p.getName()
                        + (p.isTapped() ? ",tapped" : "")
                        + (p.isAttacking() ? ",attacking" : "")
                        + (p.getBlocking() > 0 ? ",blocking" : "")
                        + ":" + evaluator.evaluatePermanent(p, game, true))
                .collect(Collectors.joining("; "));
        sb.append("-> Permanents: [").append(ownPermanentsInfo).append("]");
        logger.info(sb.toString());
    }

    protected void act(Game game) {
        if (actions == null
                || actions.isEmpty()) {
            pass(game);
        } else {
            boolean usedStack = false;
            while (actions.peek() != null) {
                Ability ability = actions.poll();
                // example: ===> SELECTED ACTION for PlayerA: Play Swamp
                logger.info(String.format("===> SELECTED ACTION for %s: %s",
                        getName(),
                        getAbilityAndSourceInfo(game, ability, true)
                ));
                if (!ability.getTargets().isEmpty()) {
                    for (Target target : ability.getTargets()) {
                        for (UUID id : target.getTargets()) {
                            target.updateTarget(id, game);
                            if (!target.isNotTarget()) {
                                game.addSimultaneousEvent(GameEvent.getEvent(GameEvent.EventType.TARGETED, id, ability, ability.getControllerId()));
                            }
                        }
                    }
                }
                this.activateAbility((ActivatedAbility) ability, game);
                if (ability.isUsesStack()) {
                    usedStack = true;
                }
            }
            if (usedStack) {
                pass(game);
            }
        }
    }

    protected int addActions(SimulationNode2 node, int depth, int alpha, int beta) {
        boolean stepFinished = false;
        int val;
        if (logger.isTraceEnabled()
                && node != null
                && node.getAbilities() != null
                && !node.getAbilities().toString().equals("[Pass]")) {
            logger.trace("Add Action [" + depth + "] " + node.getAbilities().toString() + "  a: " + alpha + " b: " + beta);
        }
        Game game = node.getGame();
        if (!COMPUTER_DISABLE_TIMEOUT_IN_GAME_SIMULATIONS && Thread.currentThread().isInterrupted()) {
            logger.debug("AI game sim interrupted by timeout");
            return evaluator.evaluate(playerId, game).getTotalScore();
        }
        if (isTopLevelCandidateBudgetExceeded()) {
            logger.debug("AI top-level candidate budget exceeded");
            return evaluator.evaluate(playerId, game).getTotalScore();
        }
        SearchCacheKey cacheKey = createSearchCacheKey(game, depth, alpha, beta);
        if (cacheKey != null && searchCache != null) {
            Integer cached = searchCache.get(cacheKey);
            if (cached != null) {
                searchCacheHits++;
                node.setScore(cached);
                traceSearchNode(game, node, "cache-hit", depth, alpha, beta, cached, null);
                return cached;
            }
            searchCacheMisses++;
        }
        // Condition to stop deeper simulation
        if (isSimulatedNodeErrorLimitExceeded()) {
            // how-to fix: make sure you are disabled debug mode by COMPUTER_DISABLE_TIMEOUT_IN_GAME_SIMULATIONS = false
            throw new IllegalStateException("AI ERROR: too much nodes (possible actions)");
        }
        if (depth <= 0
                || SimulationNode2.nodeCount > maxNodes
                || game.checkIfGameIsOver()) {
            val = evaluator.evaluate(playerId, game).getTotalScore();
            if (logger.isTraceEnabled()) {
                StringBuilder sb = new StringBuilder("Add Actions -- reached end state  <").append(val).append('>');
                SimulationNode2 logNode = node;
                do {
                    sb.append(new StringBuilder(" <- [" + logNode.getDepth() + ']' + (logNode.getAbilities() != null ? logNode.getAbilities().toString() : "[empty]")));
                    logNode = logNode.getParent();
                } while ((logNode.getParent() != null));
                logger.trace(sb);
            }
        } else if (!node.getChildren().isEmpty()) {
            if (logger.isDebugEnabled()) {
                StringBuilder sb = new StringBuilder("Add Action [").append(depth)
                        .append("] -- something added children ")
                        .append(node.getAbilities() != null ? node.getAbilities().toString() : "null")
                        .append(" added children: ").append(node.getChildren().size()).append(" (");
                for (SimulationNode2 logNode : node.getChildren()) {
                    sb.append(logNode.getAbilities() != null ? logNode.getAbilities().toString() : "null").append(", ");
                }
                sb.append(')');
                logger.debug(sb);
            }
            val = minimaxAB(node, depth - 1, alpha, beta);
        } else {
            logger.trace("Add Action -- alpha: " + alpha + " beta: " + beta + " depth:" + depth + " step:" + game.getTurnStepType() + " for player:" + game.getPlayer(game.getActivePlayerId()).getName());
            if (allPassed(game)) {
                if (!game.getStack().isEmpty()) {
                    resolve(node, depth, game);
                } else {
                    stepFinished = true;
                }
            }

            if (game.checkIfGameIsOver()) {
                val = evaluator.evaluate(playerId, game).getTotalScore();
            } else if (stepFinished) {
                logger.debug("Step finished");
                int testScore = evaluator.evaluate(playerId, game).getTotalScore();
                if (game.isActivePlayer(playerId)) {
                    if (testScore < currentScore) {
                        // if score at end of step is worse than original score don't check further
                        //logger.debug("Add Action -- abandoning check, no immediate benefit");
                        val = testScore;
                    } else {
                        val = evaluator.evaluate(playerId, game).getTotalScore();
                    }
                } else {
                    val = evaluator.evaluate(playerId, game).getTotalScore();
                }
            } else if (!node.getChildren().isEmpty()) {
                if (logger.isDebugEnabled()) {
                    StringBuilder sb = new StringBuilder("Add Action [").append(depth)
                            .append("] -- trigger ")
                            .append(node.getAbilities() != null ? node.getAbilities().toString() : "null")
                            .append(" added children: ").append(node.getChildren().size()).append(" (");
                    for (SimulationNode2 logNode : node.getChildren()) {
                        sb.append(logNode.getAbilities() != null ? logNode.getAbilities().toString() : "null").append(", ");
                    }
                    sb.append(')');
                    logger.debug(sb);
                }
                val = minimaxAB(node, depth - 1, alpha, beta);
            } else {
                val = simulatePriority(node, game, depth, alpha, beta);
            }
        }
        node.setScore(val);
        if (cacheKey != null && searchCache != null && !topLevelCandidateBudgetHit) {
            searchCache.put(cacheKey, val);
        }
        traceSearchNode(game, node, "evaluated", depth, alpha, beta, val, null);
        logger.trace("returning -- score: " + val + " depth:" + depth + " step:" + game.getTurnStepType() + " for player:" + game.getPlayer(node.getPlayerId()).getName());
        return val;

    }

    protected boolean getNextAction(Game game) {
        if (!shouldReusePreviousActionsChain(game)) {
            root = null;
            return false;
        }
        if (root != null
                && !root.children.isEmpty()) {
            SimulationNode2 test = root;
            root = root.children.get(0);
            while (!root.children.isEmpty()
                    && !root.playerId.equals(playerId)) {
                test = root;
                root = root.children.get(0);
            }
            logger.trace("Sim getNextAction -- game value:" + game.getState().getValue(true) + " test value:" + test.gameValue);
            if (root.playerId.equals(playerId)
                    && root.abilities != null
                    && game.getState().getValue(true).hashCode() == test.gameValue) {
                logger.info("simulating -- continuing previous actions chain");
                actions = new LinkedList<>(root.abilities);
                combat = root.combat;
                return true;
            } else {
                if (root.abilities == null || root.abilities.isEmpty()) {
                    logger.info("simulating -- need re-calculation (no more actions)");
                } else if (game.getState().getValue(true).hashCode() != test.gameValue) {
                    logger.info("simulating -- need re-calculation (game state changed between actions)");
                } else if (!root.playerId.equals(playerId)) {
                    // TODO: need research, why need playerId and why it taken from stack objects as controller
                    logger.info("simulating -- need re-calculation (active controller changed)");
                } else {
                    logger.info("simulating -- need re-calculation (unknown reason)");
                }
                return false;
            }
        }
        return false;
    }

    protected boolean shouldReusePreviousActionsChain(Game game) {
        return true;
    }

    protected int minimaxAB(SimulationNode2 node, int depth, int alpha, int beta) {
        logger.trace("Sim minimaxAB [" + depth + "] -- a: " + alpha + " b: " + beta + " <" + (node != null ? node.getScore() : "null") + '>');
        UUID currentPlayerId = node.getGame().getPlayerList().get();
        SimulationNode2 bestChild = null;
        if (!useStableMinimaxBestChildSelection()) {
            for (SimulationNode2 child : node.getChildren()) {
                Combat _combat = child.getCombat();
                if (alpha >= beta) {
                    break;
                }
                if (isSimulatedNodeErrorLimitExceeded()) {
                    throw new IllegalStateException("AI ERROR: too much nodes (possible actions)");
                }
                if (SimulationNode2.nodeCount > maxNodes) {
                    break;
                }
                if (isTopLevelCandidateBudgetExceeded()) {
                    return evaluator.evaluate(playerId, node.getGame()).getTotalScore();
                }
                int val = addActions(child, depth - 1, alpha, beta);
                if (!currentPlayerId.equals(playerId)) {
                    if (val < beta) {
                        beta = val;
                        bestChild = child;
                        if (node.getCombat() == null) {
                            node.setCombat(_combat);
                            bestChild.setCombat(_combat);
                        }
                    }
                    if (val == GameStateEvaluator2.LOSE_GAME_SCORE) {
                        logger.debug("lose - break");
                        break;
                    }
                } else {
                    if (val > alpha) {
                        alpha = val;
                        bestChild = child;
                        if (node.getCombat() == null) {
                            node.setCombat(_combat);
                            bestChild.setCombat(_combat);
                        }
                    }
                    if (val == GameStateEvaluator2.WIN_GAME_SCORE) {
                        logger.debug("win - break");
                        break;
                    }
                }
            }
            node.children.clear();
            if (bestChild != null) {
                node.children.add(bestChild);
            }
            if (!currentPlayerId.equals(playerId)) {
                return beta;
            } else {
                return alpha;
            }
        }
        int bestValue = currentPlayerId.equals(playerId) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        boolean evaluatedChild = false;
        for (SimulationNode2 child : node.getChildren()) {
            Combat _combat = child.getCombat();
            if (alpha >= beta) {
                break;
            }
            if (isSimulatedNodeErrorLimitExceeded()) {
                throw new IllegalStateException("AI ERROR: too much nodes (possible actions)");
            }
            if (SimulationNode2.nodeCount > maxNodes) {
                break;
            }
            if (isTopLevelCandidateBudgetExceeded()) {
                return evaluator.evaluate(playerId, node.getGame()).getTotalScore();
            }
            int val = addActions(child, depth - 1, alpha, beta);
            evaluatedChild = true;
            if (!currentPlayerId.equals(playerId)) {
                if (val < bestValue) {
                    bestValue = val;
                    bestChild = child;
                    if (node.getCombat() == null) {
                        node.setCombat(_combat);
                        bestChild.setCombat(_combat);
                    }
                }
                if (val < beta) {
                    beta = val;
                }
                // no need to check other actions
                if (val == GameStateEvaluator2.LOSE_GAME_SCORE) {
                    logger.debug("lose - break");
                    break;
                }
            } else {
                if (val > bestValue) {
                    bestValue = val;
                    bestChild = child;
                    if (node.getCombat() == null) {
                        node.setCombat(_combat);
                        bestChild.setCombat(_combat);
                    }
                }
                if (val > alpha) {
                    alpha = val;
                }
                // no need to check other actions
                if (val == GameStateEvaluator2.WIN_GAME_SCORE) {
                    logger.debug("win - break");
                    break;
                }
            }
        }
        node.children.clear();
        if (!evaluatedChild || bestChild == null) {
            int fallbackScore = evaluator.evaluate(playerId, node.getGame()).getTotalScore();
            node.setScore(fallbackScore);
            traceSearchNode(node.getGame(), node, "fallback", depth, alpha, beta, fallbackScore, "minimax-no-child");
            return fallbackScore;
        }
        node.children.add(bestChild);
        node.setScore(bestValue);
        traceSearchNode(node.getGame(), bestChild, "selected", depth, alpha, beta, bestChild.getScore(), "minimax");
        return bestValue;
    }

    protected SearchEffect getSearchEffect(StackAbility ability) {
        for (Effect effect : ability.getEffects()) {
            if (effect instanceof SearchEffect) {
                return (SearchEffect) effect;
            }
        }
        return null;
    }

    protected void resolve(SimulationNode2 node, int depth, Game game) {
        if (isAiSearchStopRequested()) {
            return;
        }
        StackObject stackObject = game.getStack().getFirstOrNull();
        if (stackObject == null) {
            throw new IllegalStateException("Catch empty stack on resolve (something wrong with sim code)");
        }
        if (stackObject instanceof StackAbility) {
            // AI hint for search effects (calc all possible cards for best score)
            SearchEffect effect = getSearchEffect((StackAbility) stackObject);
            if (effect != null
                    && stackObject.getControllerId().equals(playerId)) {
                Target target = effect.getTarget();
                if (!target.isChoiceCompleted(getId(), (StackAbility) stackObject, game, null)) {
                    for (UUID targetId : target.possibleTargets(stackObject.getControllerId(), stackObject.getStackAbility(), game)) {
                        if (isAiSearchStopRequested()) {
                            return;
                        }
                        if (shouldSkipSimulationCopy(game, depth, "search-target")) {
                            traceSearchNode(game, node, "copy-skipped", depth, Integer.MIN_VALUE, Integer.MAX_VALUE, null, aiSearchStopReason);
                            return;
                        }
                        Game sim = game.createSimulationForAI();
                        if (isAiSearchStopRequested()) {
                            return;
                        }
                        StackAbility newAbility = (StackAbility) stackObject.copy();
                        SearchEffect newEffect = getSearchEffect(newAbility);
                        newEffect.getTarget().addTarget(targetId, newAbility, sim);
                        sim.getStack().push(sim, newAbility);
                        SimulationNode2 newNode = new SimulationNode2(node, sim, depth, stackObject.getControllerId());
                        node.children.add(newNode);
                        newNode.getTargets().add(targetId);
                        traceSearchNode(game, newNode, "created", depth, Integer.MIN_VALUE, Integer.MAX_VALUE, null, "search-target");
                        logger.trace("Sim search -- node#: " + SimulationNode2.getCount() + " for player: " + sim.getPlayer(stackObject.getControllerId()).getName());
                    }
                    return;
                }
            }
        }
        stackObject.resolve(game);
        if (isAiSearchStopRequested()) {
            return;
        }
        if (stackObject instanceof StackAbility) {
            game.getStack().remove(stackObject, game);
        }
        game.applyEffects();
        if (isAiSearchStopRequested()) {
            return;
        }
        game.getPlayers().resetPassed();
        game.getPlayerList().setCurrent(game.getActivePlayerId());
    }

    /**
     * Base call for simulation of AI actions
     *
     * @return
     */
    protected Integer addActionsTimed() {
        // TODO: all actions added and calculated one by one,
        //  multithreading do not supported here
        startTraceDecision();
        StrategicSearchBudgetPolicy.Decision searchBudget = createSearchBudget(root == null ? null : root.getGame());
        int originalMaxDepth = maxDepth;
        int originalMaxNodes = maxNodes;
        if (searchBudget != null && searchBudget.hasChanges(originalMaxDepth, originalMaxNodes)) {
            maxDepth = searchBudget.getEffectiveDepth();
            maxNodes = searchBudget.getMaxNodes();
            if (root != null) {
                traceSearchNode(root.game, root, "search-budget", maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, null, String.join(",", searchBudget.getReasons()));
            }
            if (shouldStopImmediatelyForSearchBudget(searchBudget)) {
                requestAiSearchStop("search-budget:heap-stop");
            }
        }
        // run new game simulation in parallel thread
        FutureTask<Integer> task = new FutureTask<>(() -> {
            initializeSearchCache();
            try {
                return addActions(root, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
            } finally {
                searchCache = null;
            }
        });
        threadPoolSimulations.execute(task);
        try {
            int maxSeconds = maxThinkTimeSecs;
            if (COMPUTER_DISABLE_TIMEOUT_IN_GAME_SIMULATIONS) {
                maxSeconds = 3600;
            }
            logger.debug("maxThink: " + maxSeconds + " seconds ");
            Integer res = task.get(maxSeconds, TimeUnit.SECONDS);
            if (res != null) {
                return res;
            }
        } catch (TimeoutException | InterruptedException e) {
            // AI thinks too long
            // how-to fix: look at stack info - it can contain bad ability with infinite choose dialog
            logger.warn("");
            logger.warn("AI player thinks too long (report it to github):");
            logger.warn(" - player: " + getName());
            logger.warn(" - battlefield size: " + root.game.getBattlefield().getAllPermanents().size());
            logger.warn(" - stack: " + root.game.getStack());
            logger.warn(" - game: " + root.game);
            printFreezeNode(root);
            if (isDecisionTraceEnabled()) {
                decisionTrace.recordSearchTimeout(
                        root.game,
                        this,
                        maxDepth,
                        maxThinkTimeSecs,
                        SimulationNode2.nodeCount,
                        root.children == null ? 0 : root.children.size(),
                        collectBestLine(root.game, root)
                );
            }
            logger.warn("");
            task.cancel(true);
            searchCache = null;
            if (root != null && root.children != null && !root.children.isEmpty()) {
                logger.warn("AI timeout fallback keeps best completed candidate");
                traceSearchNode(root.game, root.children.get(0), "timeout-fallback", maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, root.children.get(0).getScore(), "search-timeout:best-completed-candidate");
                return root.children.get(0).getScore();
            }
            traceSearchNode(root == null ? null : root.game, root, "timeout-fallback", maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, null, "search-timeout:no-completed-candidate");
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
        } catch (ExecutionException e) {
            // game error
            logger.error("AI player catch game error in simulation - " + getName()
                    + " - " + (root == null ? "unknown game" : root.game) + ": " + e, e);
            task.cancel(true);
            // real games: must catch and log
            // unit tests: must raise again for fast fail
            if (this.isTestMode() && this.isFastFailInTestMode()) {
                throw new IllegalStateException("One of the simulated games raise the error: " + e, e);
            }
        } catch (Throwable e) {
            // ?
            logger.error("AI simulation catch unknown error: " + e, e);
            task.cancel(true);
        } finally {
            maxDepth = originalMaxDepth;
            maxNodes = originalMaxNodes;
            clearAiSearchStop();
        }
        //TODO: timeout handling
        return 0;
    }

    protected List<AiCandidateEvaluation> calculateAdvisoryCandidates(Game game) {
        currentScore = evaluator.evaluate(playerId, game).getTotalScore();
        Game sim = createSimulation(game);
        SimulationNode2.resetCount();
        root = new SimulationNode2(null, sim, maxDepth, playerId);
        advisoryCandidateEvaluations = new ArrayList<>();
        try {
            addActionsTimed();
            advisoryCandidateEvaluations.sort(Comparator
                    .comparingInt(AiCandidateEvaluation::getScore).reversed()
                    .thenComparing(AiCandidateEvaluation::getActionText));
            return new ArrayList<>(advisoryCandidateEvaluations);
        } finally {
            advisoryCandidateEvaluations = null;
            root = null;
        }
    }

    protected String calculateCombatAdvice(Game game) {
        if (game == null || game.getTurnStepType() == null) {
            return "";
        }
        switch (game.getTurnStepType()) {
            case DECLARE_ATTACKERS:
                if (game.isActivePlayer(playerId)) {
                    return calculateAttackersAdvice(game);
                }
                return "";
            case DECLARE_BLOCKERS:
                return calculateBlockersAdvice(game);
            default:
                return "";
        }
    }

    private String calculateAttackersAdvice(Game game) {
        Player attackingPlayer = game.getPlayer(playerId);
        if (attackingPlayer == null) {
            return "";
        }
        for (UUID defenderId : game.getOpponents(playerId, true)) {
            Player defender = game.getPlayer(defenderId);
            if (defender == null || !defender.isInGame()) {
                continue;
            }
            List<Permanent> availableAttackers = attackingPlayer.getAvailableAttackers(defenderId, game);
            if (availableAttackers.isEmpty()) {
                continue;
            }
            List<Permanent> possibleBlockers = defender.getAvailableBlockers(game);
            List<Permanent> lethalAttackers = CombatUtil.canKillOpponentWithMinimalAttackers(
                    game,
                    availableAttackers,
                    possibleBlockers,
                    defender
            );
            if (!lethalAttackers.isEmpty()) {
                return formatAttackersAdvice(
                        "AI companion combat suggestion",
                        "Attack " + defender.getName() + " for lethal.",
                        lethalAttackers,
                        defenderId,
                        game,
                        availableAttackers.size(),
                        possibleBlockers.size()
                );
            }
        }

        for (UUID defenderId : game.getOpponents(playerId, true)) {
            Player defender = game.getPlayer(defenderId);
            if (defender == null || !defender.isInGame()) {
                continue;
            }
            List<Permanent> availableAttackers = attackingPlayer.getAvailableAttackers(defenderId, game);
            if (availableAttackers.isEmpty()) {
                continue;
            }
            List<Permanent> possibleBlockers = defender.getAvailableBlockers(game);
            List<Permanent> safeAttackers = keepDefenseReserve(game, findSafeAttackers(game, availableAttackers, possibleBlockers));
            if (!safeAttackers.isEmpty()) {
                return formatAttackersAdvice(
                        "AI companion combat suggestion",
                        "Attack " + defender.getName() + " with safe attackers.",
                        safeAttackers,
                        defenderId,
                        game,
                        availableAttackers.size(),
                        possibleBlockers.size()
                );
            }
        }
        return "AI companion combat suggestion\nNo profitable attackers found by the current combat heuristic.";
    }

    private List<Permanent> findSafeAttackers(Game game, List<Permanent> availableAttackers, List<Permanent> possibleBlockers) {
        List<Permanent> safeAttackers = new ArrayList<>();
        CombatEvaluator eval = new CombatEvaluator();
        for (Permanent attacker : availableAttackers) {
            boolean safeToAttack = true;
            int attackerValue = eval.evaluate(attacker, game);
            for (Permanent blocker : possibleBlockers) {
                int blockerValue = eval.evaluate(blocker, game);
                int attackerDamage = getAttackHeuristicDamage(attacker, game);
                int blockerDamage = getAttackHeuristicDamage(blocker, game);
                if (attackerDamage <= blocker.getToughness().getValue()
                        && attacker.getToughness().getValue() <= blockerDamage) {
                    safeToAttack = false;
                }
                if (attacker.getToughness().getValue() == blockerDamage
                        && attackerDamage == blocker.getToughness().getValue()) {
                    if (attackerValue > blockerValue
                            || blocker.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId())
                            || blocker.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId())
                            || blocker.getAbilities().contains(new ExaltedAbility())
                            || blocker.getAbilities().containsKey(DeathtouchAbility.getInstance().getId())
                            || blocker.getAbilities().containsKey(IndestructibleAbility.getInstance().getId())
                            || !attacker.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId())
                            || !attacker.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId())
                            || !attacker.getAbilities().contains(new ExaltedAbility())) {
                        safeToAttack = false;
                    }
                }
                if (attacker.getAbilities().containsKey(DeathtouchAbility.getInstance().getId())
                        || attacker.getAbilities().containsKey(IndestructibleAbility.getInstance().getId())) {
                    safeToAttack = true;
                }
                if (attacker.getAbilities().containsKey(FlyingAbility.getInstance().getId())
                        && !blocker.getAbilities().containsKey(FlyingAbility.getInstance().getId())
                        && !blocker.getAbilities().containsKey(ReachAbility.getInstance().getId())) {
                    safeToAttack = true;
                }
                if (!safeToAttack) {
                    break;
                }
            }
            if (getAttackHeuristicDamage(attacker, game) == 0) {
                safeToAttack = false;
            }
            if (safeToAttack) {
                safeAttackers.add(attacker);
            }
        }
        return safeAttackers;
    }

    private List<Permanent> keepDefenseReserve(Game game, List<Permanent> safeAttackers) {
        Player player = game.getPlayer(playerId);
        if (player == null || safeAttackers.size() <= 1 || countLivingOpponents(game) <= 1) {
            return safeAttackers;
        }

        int futurePressure = estimateFutureCombatPressure(game);
        if (futurePressure <= 0) {
            return safeAttackers;
        }

        int acceptableDamage = Math.max(4, player.getLife() / 4);
        int reserveNeeded = futurePressure - acceptableDamage;
        if (reserveNeeded <= 0) {
            return safeAttackers;
        }

        Set<UUID> forcedAttackers = safeAttackers.stream()
                .filter(attacker -> isRequiredToAttack(attacker, game))
                .map(Permanent::getId)
                .collect(Collectors.toSet());
        if (!forcedAttackers.isEmpty()) {
            List<Permanent> forcedOnly = safeAttackers.stream()
                    .filter(attacker -> forcedAttackers.contains(attacker.getId()) || attacksWithoutTapping(attacker, game))
                    .collect(Collectors.toList());
            if (!forcedOnly.isEmpty()) {
                return forcedOnly;
            }
        }

        Set<UUID> safeAttackerIds = safeAttackers.stream()
                .map(Permanent::getId)
                .collect(Collectors.toSet());
        int retainedDefense = 0;
        for (Permanent blocker : player.getAvailableBlockers(game)) {
            if (!safeAttackerIds.contains(blocker.getId())
                    || attacksWithoutTapping(blocker, game)
                    || forcedAttackers.contains(blocker.getId())) {
                retainedDefense += defensiveValue(blocker, game);
            }
        }
        if (retainedDefense >= reserveNeeded) {
            return safeAttackers;
        }

        List<Permanent> reserveCandidates = safeAttackers.stream()
                .filter(attacker -> !forcedAttackers.contains(attacker.getId()))
                .filter(attacker -> !attacksWithoutTapping(attacker, game))
                .filter(attacker -> player.getAvailableBlockers(game).stream()
                        .anyMatch(blocker -> blocker.getId().equals(attacker.getId())))
                .sorted(Comparator.comparingInt((Permanent permanent) -> defensiveValue(permanent, game)).reversed())
                .collect(Collectors.toList());
        if (reserveCandidates.isEmpty()) {
            return safeAttackers;
        }

        Set<UUID> reserved = new HashSet<>();
        for (Permanent reserveCandidate : reserveCandidates) {
            reserved.add(reserveCandidate.getId());
            retainedDefense += defensiveValue(reserveCandidate, game);
            if (retainedDefense >= reserveNeeded) {
                break;
            }
        }

        List<Permanent> filteredAttackers = safeAttackers.stream()
                .filter(attacker -> !reserved.contains(attacker.getId()))
                .collect(Collectors.toList());
        return filteredAttackers;
    }

    private int countLivingOpponents(Game game) {
        int count = 0;
        for (UUID opponentId : game.getOpponents(playerId, true)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null && opponent.isInGame() && !opponent.hasLost()) {
                count++;
            }
        }
        return count;
    }

    private int estimateFutureCombatPressure(Game game) {
        Player defendingPlayer = game.getPlayer(playerId);
        if (defendingPlayer == null) {
            return 0;
        }
        List<Integer> opponentPressures = new ArrayList<>();
        for (UUID opponentId : game.getOpponents(playerId, true)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null || !opponent.isInGame() || opponent.hasLost()) {
                continue;
            }
            int pressure = opponent.getAvailableAttackers(playerId, game)
                    .stream()
                    .mapToInt(attacker -> CombatUtil.getCombatDamageValue(attacker, game))
                    .sum();
            if (pressure > 0) {
                opponentPressures.add(pressure);
            }
        }
        if (opponentPressures.isEmpty()) {
            return 0;
        }
        opponentPressures.sort(Comparator.reverseOrder());
        int pressure = opponentPressures.get(0);
        for (int i = 1; i < opponentPressures.size(); i++) {
            pressure += opponentPressures.get(i) / 2;
        }
        return pressure;
    }

    private boolean attacksWithoutTapping(Permanent permanent, Game game) {
        return permanent.hasAbility(VigilanceAbility.getInstance(), game)
                || permanent.hasAbility(JohanVigilanceAbility.getInstance(), game);
    }

    private boolean isRequiredToAttack(Permanent permanent, Game game) {
        if (permanent == null) {
            return false;
        }
        if (!permanent.getGoadingPlayers().isEmpty()) {
            return true;
        }
        for (Map.Entry<RequirementEffect, Set<Ability>> entry
                : game.getContinuousEffects().getApplicableRequirementEffects(permanent, false, game).entrySet()) {
            if (entry.getKey().mustAttack(game)) {
                return true;
            }
        }
        return false;
    }

    private int defensiveValue(Permanent permanent, Game game) {
        int value = Math.max(permanent.getToughness().getValue(), CombatUtil.getCombatDamageValue(permanent, game));
        if (permanent.getAbilities(game).containsClass(DeathtouchAbility.class)) {
            value += 3;
        }
        if (permanent.getAbilities(game).containsClass(FirstStrikeAbility.class)
                || permanent.getAbilities(game).containsClass(DoubleStrikeAbility.class)) {
            value += 2;
        }
        if (permanent.getAbilities(game).containsClass(ReachAbility.class)
                || permanent.getAbilities(game).containsClass(FlyingAbility.class)) {
            value += 1;
        }
        return Math.max(0, value);
    }

    private String calculateBlockersAdvice(Game game) {
        Player defendingPlayer = game.getPlayer(playerId);
        if (defendingPlayer == null) {
            return "";
        }
        List<Permanent> attackers = getAttackersDefendingPlayer(game, playerId);
        if (attackers == null || attackers.isEmpty()) {
            return "";
        }
        List<Permanent> possibleBlockers = defendingPlayer.getAvailableBlockers(game);
        int availableBlockers = possibleBlockers.size();
        possibleBlockers = filterOutNonblocking(game, attackers, possibleBlockers);
        if (possibleBlockers.isEmpty()) {
            return "AI companion combat suggestion\nNo legal blockers available.";
        }
        attackers = filterOutUnblockable(game, attackers, possibleBlockers);
        if (attackers.isEmpty()) {
            return "AI companion combat suggestion\nNo blockable attackers found.";
        }
        CombatUtil.sortByPower(attackers, false);
        CombatInfo combatInfo = CombatUtil.blockWithGoodTrade2(game, attackers, possibleBlockers);
        if (combatInfo.getCombat().isEmpty()) {
            return "AI companion combat suggestion\nNo profitable blocks found by the current combat heuristic.";
        }
        StringBuilder message = new StringBuilder("AI companion combat suggestion");
        message.append('\n').append("Recommended blocks:");
        for (Map.Entry<Permanent, List<Permanent>> entry : combatInfo.getCombat().entrySet()) {
            List<Permanent> blockers = entry.getValue();
            if (blockers == null || blockers.isEmpty()) {
                continue;
            }
            message.append('\n')
                    .append("- Block ")
                    .append(permanentSummary(entry.getKey(), game))
                    .append(" with ")
                    .append(blockers.stream()
                            .map(blocker -> permanentSummary(blocker, game))
                            .collect(Collectors.joining(", ")));
        }
        message.append('\n')
                .append("Available attackers: ")
                .append(attackers.size())
                .append(", available blockers: ")
                .append(availableBlockers);
        return message.toString();
    }

    private String formatAttackersAdvice(String title, String reason, List<Permanent> attackers, UUID defenderId,
                                         Game game, int availableAttackers, int availableBlockers) {
        StringBuilder message = new StringBuilder(title);
        int totalDamage = attackers.stream().mapToInt(attacker -> CombatUtil.getLikelyAttackingDamageValue(attacker, game)).sum();
        message.append('\n')
                .append(reason)
                .append(" Estimated combat damage: ")
                .append(totalDamage)
                .append('.');
        for (Permanent attacker : attackers) {
            message.append('\n')
                    .append("- ")
                    .append(permanentSummary(attacker, game))
                    .append(" -> ")
                    .append(objectName(game, defenderId));
        }
        message.append('\n')
                .append("Available attackers: ")
                .append(availableAttackers)
                .append(", defender blockers: ")
                .append(availableBlockers);
        return message.toString();
    }

    private String permanentSummary(Permanent permanent, Game game) {
        if (permanent == null) {
            return "unknown";
        }
        return permanent.getName()
                + " ["
                + permanent.getPower().getValue()
                + "/"
                + permanent.getToughness().getValue()
                + ", damage "
                + CombatUtil.getLikelyAttackingDamageValue(permanent, game)
                + "]";
    }

    private void printFreezeNode(SimulationNode2 root) {
        // print simple tree - there are possible multiple child nodes, but ignore it - same for abilities
        List<String> chain = new ArrayList<>();
        SimulationNode2 node = root;
        while (node != null) {
            if (node.abilities != null && !node.abilities.isEmpty()) {
                Ability ability = node.abilities.get(0);
                String sourceInfo = CardUtil.getSourceIdName(node.game, ability);
                chain.add(String.format("%s: %s",
                        (sourceInfo.isEmpty() ? "unknown" : sourceInfo),
                        ability
                ));
            }
            node = node.children == null || node.children.isEmpty() ? null : node.children.get(0);
        }
        logger.warn("Possible freeze chain:");
        if (root != null && chain.isEmpty()) {
            logger.warn(" - unknown use case (too many possible targets?)"); // maybe can't finish any calc, maybe related to target options
        }
        chain.forEach(s -> {
            logger.warn(" - " + s);
        });
    }

    protected int simulatePriority(SimulationNode2 node, Game game, int depth, int alpha, int beta) {
        if (isAiSearchStopRequested()) {
            logger.debug("AI game sim interrupted by timeout");
            return evaluator.evaluate(playerId, game).getTotalScore();
        }
        node.setGameValue(game.getState().getValue(true).hashCode());
        SimulatedPlayer2 currentPlayer = (SimulatedPlayer2) game.getPlayer(game.getPlayerList().get());
        SimulationNode2 bestNode = null;
        List<Ability> allActions = currentPlayer.simulatePriority(game);
        if (isAiSearchStopRequested()) {
            return evaluator.evaluate(playerId, game).getTotalScore();
        }
        optimize(game, allActions);
        if (isAiSearchStopRequested()) {
            return evaluator.evaluate(playerId, game).getTotalScore();
        }
        long decisionDeadlineMillis = depth == maxDepth
                ? System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(maxThinkTimeSecs)
                : 0;
        GameStateEvaluator2.PlayerEvaluateScore startedEvaluateScore = evaluator.evaluate(this.getId(), node.getGame());
        int startedScore = startedEvaluateScore.getTotalScore();
        boolean traceDecision = isDecisionTraceEnabled() && depth == maxDepth;
        AiDecisionTrace.ScoreSnapshot startedScoreSnapshot = traceDecision ? new AiDecisionTrace.ScoreSnapshot(startedEvaluateScore) : null;
        long decisionId = traceDecision ? getActiveTraceDecisionId() : 0;
        if (traceDecision) {
            traceSearchNode(game, node, "root", depth, alpha, beta, startedScore, null);
        }
        if (logger.isInfoEnabled()
                && !allActions.isEmpty()
                && depth == maxDepth) {
            logger.info(String.format("POSSIBLE ACTION CHAINS for %s (%d, started score: %d)%s",
                    getName(),
                    allActions.size(),
                    startedScore,
                    (actions.isEmpty() ? "" : ":")
            ));
            for (int i = 0; i < allActions.size(); i++) {
                // print possible actions with detailed targets
                Ability possibleAbility = allActions.get(i);
                logger.info(String.format("-> #%d (%s)", i + 1, getAbilityAndSourceInfo(game, possibleAbility, true)));
            }
        }
        int actionNumber = 0;
        int bestValSubNodes = Integer.MIN_VALUE;
        int bestOpponentValSubNodes = Integer.MAX_VALUE;
        boolean evaluatedAnyAction = false;
        if (depth == maxDepth) {
            String budgetAdjustment = adjustSearchBudgetForLegalActions(game, allActions.size());
            if (budgetAdjustment != null && depth > maxDepth) {
                depth = maxDepth;
                traceSearchNode(game, node, "search-budget", depth, alpha, beta, null, budgetAdjustment);
            }
        }
        for (Ability action : allActions) {
            actionNumber++;
            long actionStart = System.currentTimeMillis();
            int candidateCacheHitsStart = searchCacheHits;
            int candidateCacheMissesStart = searchCacheMisses;
            if (isAiSearchStopRequested()) {
                logger.info("Sim Prio [" + depth + "] -- interrupted");
                break;
            }
            long previousTopLevelCandidateDeadlineMillis = topLevelCandidateDeadlineMillis;
            boolean previousTopLevelCandidateBudgetHit = topLevelCandidateBudgetHit;
            topLevelCandidateBudgetHit = false;
            if (depth == maxDepth) {
                long remainingDecisionMillis = Math.max(1, decisionDeadlineMillis - System.currentTimeMillis());
                long candidateBudgetMillis = getTopLevelCandidateBudgetMillis(
                        allActions.size(),
                        actionNumber,
                        allActions.size() - actionNumber + 1,
                        remainingDecisionMillis
                );
                topLevelCandidateDeadlineMillis = candidateBudgetMillis > 0
                        ? System.currentTimeMillis() + candidateBudgetMillis
                        : 0;
            }
            if (shouldSkipSimulationCopy(game, depth, "priority-action")) {
                traceSearchNode(game, node, "copy-skipped", depth, alpha, beta, null, aiSearchStopReason);
                break;
            }
            Game sim = game.createSimulationForAI();
            try {
                if (isAiSearchStopRequested()) {
                    break;
                }
                if (!(action instanceof StaticAbility) //for MorphAbility, etc
                        && sim.getPlayer(currentPlayer.getId()).activateAbility((ActivatedAbility) action.copy(), sim)) {
                    boolean candidateBudgetExceeded = isTopLevelCandidateBudgetExceeded();
                    if (!candidateBudgetExceeded) {
                        sim.applyEffects();
                        candidateBudgetExceeded = isTopLevelCandidateBudgetExceeded();
                    }
                    if (!candidateBudgetExceeded && checkForRepeatedAction(sim, node, action, currentPlayer.getId())) {
                        logger.debug("Sim Prio [" + depth + "] -- repeated action: " + action);
                        continue;
                    }
                    if (!candidateBudgetExceeded
                            && !sim.checkIfGameIsOver()
                            && (action.isUsesStack() || action instanceof PassAbility)) {
                        // skip priority for opponents before stack resolve
                        UUID nextPlayerId = sim.getPlayerList().get();
                        int passedPlayers = 0;
                        int maxPlayersToPass = Math.max(1, sim.getPlayers().size() + 1);
                        do {
                            if (isAiSearchStopRequested() || passedPlayers++ > maxPlayersToPass) {
                                candidateBudgetExceeded = true;
                                break;
                            }
                            Player nextPlayer = sim.getPlayer(nextPlayerId);
                            if (nextPlayer == null) {
                                candidateBudgetExceeded = true;
                                break;
                            }
                            nextPlayer.pass(shouldPassSimulatedPlayersOnSimulatedGame() ? sim : game);
                            nextPlayerId = sim.getPlayerList().getNext();
                        } while (!Objects.equals(nextPlayerId, this.getId()));
                    }
                    SimulationNode2 newNode = new SimulationNode2(node, sim, action, depth, currentPlayer.getId());
                    traceSearchNode(game, newNode, "created", depth, alpha, beta, null, null);
                    if (!candidateBudgetExceeded) {
                        sim.checkStateAndTriggered();
                        candidateBudgetExceeded = isTopLevelCandidateBudgetExceeded();
                    }
                    AiDecisionTrace.ScoreSnapshot immediateScoreSnapshot = null;
                    if (traceDecision) {
                        immediateScoreSnapshot = new AiDecisionTrace.ScoreSnapshot(evaluator.evaluate(this.getId(), sim));
                    }
                    int finalScore;
                    if (candidateBudgetExceeded) {
                        finalScore = evaluator.evaluate(this.getId(), sim).getTotalScore();
                    } else if (action instanceof PassAbility && sim.getStack().isEmpty()) {
                        // no more next actions, it's a final score
                        finalScore = evaluator.evaluate(this.getId(), sim).getTotalScore();
                    } else {
                        // resolve current action and calc all next actions to find best score (return max possible score)
                        finalScore = addActions(newNode, depth - 1, alpha, beta);
                    }
                    SimulationNode2 strategyFinalNode = getFinalBestLineNode(newNode);
                    AiStrategyScore strategyScore = AiStrategyScore.none(finalScore);
                    if (depth == maxDepth) {
                        strategyScore = evaluateTopLevelCandidateStrategy(
                                game,
                                strategyFinalNode == null ? sim : strategyFinalNode.getGame(),
                                action,
                                finalScore
                        );
                        finalScore = strategyScore.getAdjustedScore();
                        newNode.setStrategyScore(strategyScore);
                    }
                    traceSearchNode(game, newNode, candidateBudgetExceeded ? "budget-evaluated" : "evaluated", depth, alpha, beta, finalScore, null);
                    if (traceDecision) {
                        Game finalImpactGame = strategyFinalNode == null ? sim : strategyFinalNode.getGame();
                        boolean traceStateImpact = isStateImpactTraceEnabled();
                        decisionTrace.recordCandidate(
                                game,
                                this,
                                decisionId,
                                action,
                                depth,
                                actionNumber,
                                startedScoreSnapshot,
                                immediateScoreSnapshot,
                                new AiDecisionTrace.ScoreSnapshot(finalScore, evaluator.evaluate(this.getId(), strategyFinalNode == null ? sim : strategyFinalNode.getGame())),
                                SimulationNode2.nodeCount,
                                System.currentTimeMillis() - actionStart,
                                topLevelCandidateBudgetHit,
                                searchCacheHits - candidateCacheHitsStart,
                                searchCacheMisses - candidateCacheMissesStart,
                                searchCache == null ? 0 : searchCache.size(),
                                collectBestLine(game, newNode),
                                strategyScore,
                                traceStateImpact ? AiStateImpact.between(game, sim, this.getId()) : null,
                                traceStateImpact ? AiStateImpact.between(game, finalImpactGame, this.getId()) : null,
                                AiDecisionFeatureSnapshot.from(game, this.getId()),
                                AiDecisionFeatureSnapshot.from(finalImpactGame, this.getId())
                        );
                    }
                    logger.debug("Sim Prio " + BLANKS.substring(0, 2 + (maxDepth - depth) * 3) + '[' + depth + "]#" + actionNumber + " <" + finalScore + "> - (" + action + ") ");
                    evaluatedAnyAction = true;

                // Hints on data:
                // * node - started game with executed command (pay and put on stack)
                // * newNode - resolved game with resolved command (resolve stack)
                // * node.children - rewrites to store only best tree (e.g. contains only final data)
                // * node.score - rewrites to store max score (e.g. contains only final data)
                if (logger.isInfoEnabled()
                        && depth >= maxDepth) {
                    // show final calculated score and best actions chain from it
                    List<SimulationNode2> fullChain = new ArrayList<>();
                    fullChain.add(newNode);
                    SimulationNode2 finalNode = newNode;
                    while (!finalNode.getChildren().isEmpty()) {
                        finalNode = finalNode.getChildren().get(0);
                        fullChain.add(finalNode);
                    }

                    // example: Sim Prio [6] #1 <diff -19, +4444> (Lightning Bolt [aa5]: Cast Lightning Bolt -> Balduvian Bears [c49])
                    // total
                    logger.info(String.format("Sim Prio [%d] #%d <total score diff %s (from %s to %s)>",
                            depth,
                            actionNumber,
                            printDiffScore((long) finalScore - startedScore),
                            printDiffScore(startedScore),
                            printDiffScore(finalScore)
                    ));

                    // details
                    for (int chainIndex = 0; chainIndex < fullChain.size(); chainIndex++) {
                        SimulationNode2 currentNode = fullChain.get(chainIndex);
                        SimulationNode2 prevNode;
                        if (chainIndex == 0) {
                            prevNode = node;
                        } else {
                            prevNode = fullChain.get(chainIndex - 1);
                        }

                        int currentScore = evaluator.evaluate(this.getId(), currentNode.getGame()).getTotalScore();
                        int prevScore = evaluator.evaluate(this.getId(), prevNode.getGame()).getTotalScore();

                        if (currentNode.getAbilities() != null) {
                            // ON PRIORITY

                            // runtime check
                            if (currentNode.getAbilities().size() != 1) {
                                throw new IllegalStateException("AI's simulated game must contains only one selected action, but found: " + currentNode.getAbilities());
                            }
                            if (!currentNode.getTargets().isEmpty() || !currentNode.getChoices().isEmpty()) {
                                throw new IllegalStateException("WTF, simulated abilities with targets/choices");
                            }
                            logger.info(String.format("Sim Prio [%d] -> next action: [%d]<diff %s> (%s)",
                                    depth,
                                    currentNode.getDepth(),
                                    printDiffScore((long) currentScore - prevScore),
                                    getAbilityAndSourceInfo(currentNode.getGame(), currentNode.getAbilities().get(0), true)
                            ));
                        } else if (!currentNode.getTargets().isEmpty()) {
                            // ON TARGETS
                            String targetsInfo = currentNode.getTargets()
                                    .stream()
                                    .map(id -> {
                                        Player player = game.getPlayer(id);
                                        if (player != null) {
                                            return player.getName();
                                        }
                                        MageObject object = game.getObject(id);
                                        if (object != null) {
                                            return object.getIdName();
                                        }
                                        return "unknown";
                                    })
                                    .collect(Collectors.joining(", "));
                            logger.info(String.format("Sim Prio [%d] -> with possible choices: [%d]<diff %s> (%s)",
                                    depth,
                                    currentNode.getDepth(),
                                    printDiffScore((long) currentScore - prevScore),
                                    targetsInfo)
                            );
                        } else if (!currentNode.getChoices().isEmpty()) {
                            // ON CHOICES
                            String choicesInfo = String.join(", ", currentNode.getChoices());
                            logger.info(String.format("Sim Prio [%d] -> with possible choices (must not see that code): [%d]<diff %s> (%s)",
                                    depth,
                                    currentNode.getDepth(),
                                    printDiffScore((long) currentScore - prevScore),
                                    choicesInfo)
                            );
                        } else {
                            logger.info(String.format("Sim Prio [%d] -> with do nothing: [%d]<diff %s>",
                                    depth,
                                    currentNode.getDepth(),
                                    printDiffScore((long) currentScore - prevScore))
                            );
                        }
                    }
                }

                    if (currentPlayer.getId().equals(playerId)) {
                    int selectionScore = finalScore;
                    if (depth == maxDepth
                            && action instanceof PassAbility) {
                        selectionScore = safeSubtract(selectionScore, PASSIVITY_PENALTY); // passivity penalty
                    }
                    if (depth == maxDepth && advisoryCandidateEvaluations != null) {
                        SimulationNode2 advisoryFinalNode = getFinalBestLineNode(newNode);
                        Game finalImpactGame = advisoryFinalNode == null ? sim : advisoryFinalNode.getGame();
                        advisoryCandidateEvaluations.add(new AiCandidateEvaluation(
                                getAbilityAndSourceInfo(game, action, true),
                                startedScore,
                                selectionScore,
                                strategyScore,
                                collectBestLine(game, newNode),
                                evaluator.evaluate(this.getId(), finalImpactGame)
                        ));
                    }
                    if (finalScore > bestValSubNodes) {
                        bestValSubNodes = finalScore;
                    }
                    if (depth == maxDepth
                            && action instanceof PassAbility) {
                        finalScore = safeSubtract(finalScore, PASSIVITY_PENALTY); // passivity penalty
                    }
                    if ((useStablePriorityFallbackSelection() && bestNode == null)
                            || finalScore > alpha
                            || (depth == maxDepth
                            && finalScore == alpha
                            && RandomUtil.nextBoolean())) { // Adding random for equal value to get change sometimes
                        if (finalScore > alpha) {
                            alpha = finalScore;
                        }
                        bestNode = newNode;
                        bestNode.setScore(finalScore);
                        if (!newNode.getChildren().isEmpty()) {
                            // TODO: wtf, must review all code to remove shared objects
                            bestNode.setCombat(newNode.getChildren().get(0).getCombat());
                        }

                        // keep only best node
                        if (depth == maxDepth) {
                            logger.info("Sim Prio [" + depth + "] -* BEST actions chain so far: <final score " + bestNode.getScore() + ">");
                            node.children.clear();
                            node.children.add(bestNode);
                            node.setScore(bestNode.getScore());
                        }
                    }

                    // no need to check other actions
                    if (finalScore == GameStateEvaluator2.WIN_GAME_SCORE) {
                        logger.debug("Sim Prio -- win - break");
                        break;
                    }
                } else {
                    if (finalScore < bestOpponentValSubNodes) {
                        bestOpponentValSubNodes = finalScore;
                    }
                    if ((useStablePriorityFallbackSelection() && bestNode == null) || finalScore < beta) {
                        if (finalScore < beta) {
                            beta = finalScore;
                        }
                        bestNode = newNode;
                        bestNode.setScore(finalScore);
                        if (!newNode.getChildren().isEmpty()) {
                            bestNode.setCombat(newNode.getChildren().get(0).getCombat());
                        }
                    }

                    // no need to check other actions
                    if (finalScore == GameStateEvaluator2.LOSE_GAME_SCORE) {
                        logger.debug("Sim Prio -- lose - break");
                        break;
                    }
                }
                if (alpha >= beta) {
                    break;
                }
                if (isSimulatedNodeErrorLimitExceeded()) {
                    throw new IllegalStateException("AI ERROR: too many nodes (possible actions)");
                }
                    if (SimulationNode2.nodeCount > maxNodes) {
                        logger.debug("Sim Prio -- reached end-state");
                        break;
                    }
                }
            } finally {
                topLevelCandidateDeadlineMillis = previousTopLevelCandidateDeadlineMillis;
                topLevelCandidateBudgetHit = previousTopLevelCandidateBudgetHit;
            }
        } // end of for (allActions)

        if (depth == maxDepth) {
            // TODO: buggy? Why it ended with depth limit 6 on one Pass action?!
            logger.info("Sim Prio [" + depth + "] ## Ended due max actions chain depth limit (" + maxDepth + ") -- Nodes calculated: " + SimulationNode2.nodeCount);
        }
        if (useStablePriorityFallbackSelection() && (!evaluatedAnyAction || bestNode == null)) {
            node.setScore(startedScore);
            traceSearchNode(game, node, "fallback", depth, alpha, beta, startedScore, "priority-no-action");
            return startedScore;
        }
        if (bestNode != null) {
            node.children.clear();
            node.children.add(bestNode);
            node.setScore(bestNode.getScore());
            traceSearchNode(game, bestNode, "selected", depth, alpha, beta, bestNode.getScore(), null);
            if (traceDecision && bestNode.getAbilities() != null && !bestNode.getAbilities().isEmpty()) {
                SimulationNode2 finalNode = getFinalBestLineNode(bestNode);
                Game finalGame = finalNode == null ? bestNode.getGame() : finalNode.getGame();
                boolean traceStateImpact = isStateImpactTraceEnabled();
                decisionTrace.recordChosen(
                        game,
                        this,
                        decisionId,
                        bestNode.getAbilities().get(0),
                        depth,
                        startedScoreSnapshot,
                        new AiDecisionTrace.ScoreSnapshot(bestNode.getScore(), evaluator.evaluate(this.getId(), finalGame)),
                        SimulationNode2.nodeCount,
                        collectBestLine(game, bestNode),
                        bestNode.getStrategyScore(),
                        traceStateImpact ? AiStateImpact.between(game, finalGame, this.getId()) : null,
                        AiDecisionFeatureSnapshot.from(game, this.getId()),
                        AiDecisionFeatureSnapshot.from(finalGame, this.getId())
                );
            }
            if (logger.isTraceEnabled()
                    && !bestNode.getAbilities().toString().equals("[Pass]")) {
                logger.trace(new StringBuilder("Sim Prio [").append(depth).append("] -- Set after (depth=").append(depth).append(")  <").append(bestNode.getScore()).append("> ").append(bestNode.getAbilities().toString()).toString());
            }
        }

        if (currentPlayer.getId().equals(playerId)) {
            return bestValSubNodes;
        } else {
            return useStablePriorityFallbackSelection() ? bestOpponentValSubNodes : beta;
        }
    }

    public static final class AiCandidateEvaluation {
        private final String actionText;
        private final int startedScore;
        private final int score;
        private final AiStrategyScore strategyScore;
        private final List<AiDecisionTrace.BestLineStep> bestLine;
        private final GameStateEvaluator2.PlayerEvaluateScore finalScoreBreakdown;

        private AiCandidateEvaluation(String actionText,
                                      int startedScore,
                                      int score,
                                      AiStrategyScore strategyScore,
                                      List<AiDecisionTrace.BestLineStep> bestLine,
                                      GameStateEvaluator2.PlayerEvaluateScore finalScoreBreakdown) {
            this.actionText = actionText;
            this.startedScore = startedScore;
            this.score = score;
            this.strategyScore = strategyScore;
            this.bestLine = bestLine == null ? Collections.emptyList() : new ArrayList<>(bestLine);
            this.finalScoreBreakdown = finalScoreBreakdown;
        }

        public String getActionText() {
            return actionText;
        }

        public int getStartedScore() {
            return startedScore;
        }

        public int getScore() {
            return score;
        }

        public AiStrategyScore getStrategyScore() {
            return strategyScore;
        }

        public List<AiDecisionTrace.BestLineStep> getBestLine() {
            return new ArrayList<>(bestLine);
        }

        public GameStateEvaluator2.PlayerEvaluateScore getFinalScoreBreakdown() {
            return finalScoreBreakdown;
        }
    }

    protected String getAbilityAndSourceInfo(Game game, Ability ability, boolean showTargets) {
        // ability
        // TODO: add modal info
        // + (action.isModal() ? " Mode = " + action.getModes().getMode().toString() : "")
        if (ability.isModal()) {
            //throw new IllegalStateException("TODO: need implement");
        }
        MageObject sourceObject = ability.getSourceObject(game);
        String abilityInfo = (sourceObject == null ? "" : sourceObject.getIdName() + ": ") + CardUtil.substring(ability.toString(), 30, "...");
        // targets
        String targetsInfo = "";
        if (showTargets) {
            List<String> allTargetsInfo = new ArrayList<>();
            ability.getAllSelectedTargets().forEach(target -> {
                target.getTargets().forEach(selectedId -> {
                    String xInfo = "";
                    if (target instanceof TargetAmount) {
                        xInfo = "x" + target.getTargetAmount(selectedId) + " ";
                    }

                    String targetInfo = null;
                    Player player = game.getPlayer(selectedId);
                    if (player != null) {
                        targetInfo = player.getName();
                    }
                    if (targetInfo == null) {
                        MageObject object = game.getObject(selectedId);
                        if (object != null) {
                            targetInfo = object.getIdName();
                        }
                    }
                    if (targetInfo == null) {
                        StackObject stackObject = game.getState().getStack().getStackObject(selectedId);
                        if (stackObject != null) {
                            targetInfo = CardUtil.substring(stackObject.toString(), 20, "...");
                        }
                    }
                    if (targetInfo == null) {
                        targetInfo = "unknown";
                    }
                    allTargetsInfo.add(xInfo + targetInfo);
                });
            });
            targetsInfo = String.join(" + ", allTargetsInfo);
        }
        return abilityInfo + (targetsInfo.isEmpty() ? "" : " -> " + targetsInfo);
    }

    private String printDiffScore(long score) {
        if (score >= 0) {
            return "+" + score;
        } else {
            return "" + score;
        }
    }

    private static int safeSubtract(int left, int right) {
        long value = (long) left - right;
        if (value > MAX_REAL_SCORE) {
            return MAX_REAL_SCORE;
        }
        if (value < MIN_REAL_SCORE) {
            return MIN_REAL_SCORE;
        }
        return (int) value;
    }

    /**
     * Various AI optimizations for actions.
     *
     * @param game
     * @param allActions
     */
    protected void optimize(Game game, List<Ability> allActions) {
        for (TreeOptimizer optimizer : getOptimizers()) {
            optimizer.optimize(game, allActions);
        }
        Collections.sort(allActions, new Comparator<Ability>() {
            @Override
            public int compare(Ability ability1, Ability ability2) {
                String rule1 = ability1.toString();
                String rule2 = ability2.toString();

                // pass
                boolean pass1 = rule1.startsWith("Pass");
                boolean pass2 = rule2.startsWith("Pass");
                if (pass1 != pass2) {
                    if (pass1) {
                        return 1;
                    } else {
                        return -1;
                    }
                }

                // play
                boolean play1 = rule1.startsWith("Play");
                boolean play2 = rule2.startsWith("Play");
                if (play1 != play2) {
                    if (play1) {
                        return -1;
                    } else {
                        return 1;
                    }
                }

                // cast
                boolean cast1 = rule1.startsWith("Cast");
                boolean cast2 = rule2.startsWith("Cast");
                if (cast1 != cast2) {
                    if (cast1) {
                        return -1;
                    } else {
                        return 1;
                    }
                }

                // default
                return ability1.getRule().compareTo(ability2.getRule());
            }
        });
    }

    protected List<TreeOptimizer> getOptimizers() {
        return optimizers;
    }

    protected AiDecisionTrace createDecisionTrace() {
        return NoOpAiDecisionTrace.INSTANCE;
    }

    protected AiStrategyScore evaluateTopLevelCandidateStrategy(Game decisionGame, Game finalGame, Ability action, int baseScore) {
        return AiStrategyScore.none(baseScore);
    }

    protected StrategicSearchBudgetPolicy.Decision createSearchBudget(Game game) {
        return null;
    }

    protected boolean shouldStopImmediatelyForSearchBudget(StrategicSearchBudgetPolicy.Decision searchBudget) {
        return searchBudget != null && searchBudget.isStopForHeapPressure();
    }

    protected String adjustSearchBudgetForLegalActions(Game game, int legalActionCount) {
        return null;
    }

    protected long getTopLevelCandidateBudgetMillis(int actionCount, int actionNumber, int remainingActions, long remainingDecisionMillis) {
        return 0;
    }

    protected boolean isTopLevelCandidateBudgetExceeded() {
        if (topLevelCandidateDeadlineMillis <= 0 || System.currentTimeMillis() < topLevelCandidateDeadlineMillis) {
            return false;
        }
        topLevelCandidateBudgetHit = true;
        return true;
    }

    protected int getMaxSimulatedNodesPerError() {
        return MAX_SIMULATED_NODES_PER_ERROR;
    }

    protected boolean isSimulatedNodeErrorLimitExceeded() {
        int nodeErrorLimit = getMaxSimulatedNodesPerError();
        return nodeErrorLimit > 0 && SimulationNode2.nodeCount > nodeErrorLimit;
    }

    protected boolean isAiSearchStopRequested() {
        if (aiSearchStopRequested) {
            return true;
        }
        if (!COMPUTER_DISABLE_TIMEOUT_IN_GAME_SIMULATIONS && Thread.currentThread().isInterrupted()) {
            requestAiSearchStop("search-budget:interrupted");
            return true;
        }
        return isTopLevelCandidateBudgetExceeded();
    }

    protected boolean shouldSkipSimulationCopy(Game game, int depth, String reason) {
        return isAiSearchStopRequested();
    }

    protected void requestAiSearchStop(String reason) {
        aiSearchStopRequested = true;
        aiSearchStopReason = reason;
    }

    protected void clearAiSearchStop() {
        aiSearchStopRequested = false;
        aiSearchStopReason = null;
    }

    protected boolean allPassed(Game game) {
        for (Player player : game.getPlayers().values()) {
            if (!player.isPassed()
                    && !player.hasLost()
                    && !player.hasLeft()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        if (choices.isEmpty()) {
            return super.choose(outcome, choice, game);
        }
        if (!choice.isChosen()) {
            if (!choice.setChoiceByAnswers(choices, true)) {
                choice.setRandomChoice();
            }
        }
        return true;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        if (targets.isEmpty()) {
            return super.chooseTarget(outcome, cards, target, source, game);
        }

        UUID abilityControllerId = target.getAffectedAbilityControllerId(getId());
        if (!target.isChoiceCompleted(abilityControllerId, source, game, cards)) {
            for (UUID targetId : targets) {
                target.addTarget(targetId, source, game);
                if (target.isChoiceCompleted(abilityControllerId, source, game, cards)) {
                    targets.clear();
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean choose(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        if (targets.isEmpty()) {
            return super.choose(outcome, cards, target, source, game);
        }

        UUID abilityControllerId = target.getAffectedAbilityControllerId(getId());
        if (!target.isChoiceCompleted(abilityControllerId, source, game, cards)) {
            for (UUID targetId : targets) {
                target.add(targetId, game);
                if (target.isChoiceCompleted(abilityControllerId, source, game, cards)) {
                    targets.clear();
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private void declareBlockers(Game game, UUID activePlayerId) {
        game.fireEvent(new GameEvent(GameEvent.EventType.DECLARE_BLOCKERS_STEP_PRE, null, null, activePlayerId));
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARING_BLOCKERS, activePlayerId, activePlayerId))) {
            List<Permanent> attackers = getAttackers(game);
            if (attackers == null) {
                return;
            }

            List<Permanent> possibleBlockers = super.getAvailableBlockers(game);
            int availableBlockers = possibleBlockers.size();
            possibleBlockers = filterOutNonblocking(game, attackers, possibleBlockers);
            if (possibleBlockers.isEmpty()) {
                traceBlockersSelected(game, attackers, null, "no-legal-blockers", attackers.size(), availableBlockers);
                return;
            }

            attackers = filterOutUnblockable(game, attackers, possibleBlockers);
            if (attackers.isEmpty()) {
                traceBlockersSelected(game, Collections.emptyList(), null, "no-blockable-attackers", 0, availableBlockers);
                return;
            }

            CombatUtil.sortByPower(attackers, false); // most powerfull go to first

            CombatInfo combatInfo = CombatUtil.blockWithGoodTrade2(game, attackers, possibleBlockers);
            Player player = game.getPlayer(playerId);

            boolean blocked = false;
            for (Map.Entry<Permanent, List<Permanent>> entry : combatInfo.getCombat().entrySet()) {
                UUID attackerId = entry.getKey().getId();
                List<Permanent> blockers = entry.getValue();
                if (blockers != null) {
                    for (Permanent blocker : blockers) {
                        // TODO: buggy or miss on multi blocker requirements?!
                        player.declareBlocker(player.getId(), blocker.getId(), attackerId, game);
                        blocked = true;
                    }
                }
            }
            if (blocked) {
                game.getPlayers().resetPassed();
            }
            traceBlockersSelected(
                    game,
                    attackers,
                    combatInfo,
                    blocked ? "good-trade-static" : "no-profitable-blocks",
                    attackers.size(),
                    availableBlockers
            );
        }
    }

    private List<Permanent> filterOutNonblocking(Game game, List<Permanent> attackers, List<Permanent> blockers) {
        List<Permanent> blockersLeft = new ArrayList<>();
        for (Permanent blocker : blockers) {
            for (Permanent attacker : attackers) {
                if (blocker.canBlock(attacker.getId(), game)) {
                    blockersLeft.add(blocker);
                    break;
                }
            }
        }
        return blockersLeft;
    }

    private List<Permanent> filterOutUnblockable(Game game, List<Permanent> attackers, List<Permanent> blockers) {
        List<Permanent> attackersLeft = new ArrayList<>();
        for (Permanent attacker : attackers) {
            if (CombatUtil.canBeBlocked(game, attacker, blockers)) {
                attackersLeft.add(attacker);
            }
        }
        return attackersLeft;
    }

    private List<Permanent> getAttackers(Game game) {
        Set<UUID> attackersUUID = game.getCombat().getAttackers();
        if (attackersUUID.isEmpty()) {
            return null;
        }

        List<Permanent> attackers = new ArrayList<>();
        for (UUID attackerId : attackersUUID) {
            Permanent permanent = game.getPermanent(attackerId);
            attackers.add(permanent);
        }
        return attackers;
    }

    private List<Permanent> getAttackersDefendingPlayer(Game game, UUID defendingPlayerId) {
        Set<UUID> attackersUUID = game.getCombat().getAttackers();
        if (attackersUUID.isEmpty()) {
            return null;
        }

        List<Permanent> attackers = new ArrayList<>();
        for (UUID attackerId : attackersUUID) {
            if (!isAttackingDefenderControlledBy(game, attackerId, defendingPlayerId)) {
                continue;
            }
            Permanent permanent = game.getPermanent(attackerId);
            if (permanent != null) {
                attackers.add(permanent);
            }
        }
        return attackers;
    }

    private boolean isAttackingDefenderControlledBy(Game game, UUID attackerId, UUID defendingPlayerId) {
        UUID defenderId = game.getCombat().getDefenderId(attackerId);
        if (defenderId == null || defendingPlayerId == null) {
            return false;
        }
        if (defenderId.equals(defendingPlayerId)) {
            return true;
        }
        Permanent defenderPermanent = game.getPermanent(defenderId);
        return defenderPermanent != null && defenderPermanent.isControlledBy(defendingPlayerId);
    }

    private Set<UUID> traceCombatAttackersBefore(Game game) {
        if (!isDecisionTraceEnabled()) {
            return Collections.emptySet();
        }
        return new HashSet<>(game.getCombat().getAttackers());
    }

    private int countNewAttackers(Game game, Set<UUID> attackersBefore) {
        int count = 0;
        for (UUID attackerId : game.getCombat().getAttackers()) {
            if (!attackersBefore.contains(attackerId)) {
                count++;
            }
        }
        return count;
    }

    private void traceAttackersSelected(Game game, UUID defenderId, String reason, int availableAttackers,
                                        int availableBlockers, Set<UUID> attackersBefore) {
        if (!isDecisionTraceEnabled()) {
            return;
        }
        List<AiDecisionTrace.Combatant> selectedAttackers = new ArrayList<>();
        for (UUID attackerId : game.getCombat().getAttackers()) {
            if (attackersBefore.contains(attackerId)) {
                continue;
            }
            Permanent attacker = game.getPermanent(attackerId);
            AiDecisionTrace.Combatant combatant = toCombatant(
                    attacker,
                    game,
                    game.getCombat().getDefenderId(attackerId)
            );
            if (combatant != null) {
                selectedAttackers.add(combatant);
            }
        }
        decisionTrace.recordCombatSelection(
                game,
                this,
                new AiDecisionTrace.CombatSelection(
                        "attackers",
                        reason,
                        idString(defenderId),
                        objectName(game, defenderId),
                        availableAttackers,
                        availableBlockers,
                        selectedAttackers,
                        Collections.emptyList()
                )
        );
    }

    private void traceBlockersSelected(Game game, List<Permanent> attackers, CombatInfo combatInfo, String reason,
                                       int availableAttackers, int availableBlockers) {
        if (!isDecisionTraceEnabled()) {
            return;
        }
        List<AiDecisionTrace.Combatant> attackingCombatants = new ArrayList<>();
        for (Permanent attacker : attackers) {
            AiDecisionTrace.Combatant combatant = toCombatant(
                    attacker,
                    game,
                    attacker == null ? null : game.getCombat().getDefenderId(attacker.getId())
            );
            if (combatant != null) {
                attackingCombatants.add(combatant);
            }
        }

        List<AiDecisionTrace.CombatAssignment> assignments = new ArrayList<>();
        if (combatInfo != null) {
            for (Map.Entry<Permanent, List<Permanent>> entry : combatInfo.getCombat().entrySet()) {
                Permanent attacker = entry.getKey();
                AiDecisionTrace.Combatant attackerCombatant = toCombatant(
                        attacker,
                        game,
                        attacker == null ? null : game.getCombat().getDefenderId(attacker.getId())
                );
                if (attackerCombatant == null) {
                    continue;
                }
                List<AiDecisionTrace.Combatant> blockers = new ArrayList<>();
                List<Permanent> assignedBlockers = entry.getValue();
                if (assignedBlockers != null) {
                    for (Permanent blocker : assignedBlockers) {
                        AiDecisionTrace.Combatant blockerCombatant = toCombatant(blocker, game, null);
                        if (blockerCombatant != null) {
                            blockers.add(blockerCombatant);
                        }
                    }
                }
                if (!blockers.isEmpty()) {
                    assignments.add(new AiDecisionTrace.CombatAssignment(attackerCombatant, blockers));
                }
            }
        }

        decisionTrace.recordCombatSelection(
                game,
                this,
                new AiDecisionTrace.CombatSelection(
                        "blockers",
                        reason,
                        idString(playerId),
                        getName(),
                        availableAttackers,
                        availableBlockers,
                        attackingCombatants,
                        assignments
                )
        );
    }

    private AiDecisionTrace.Combatant toCombatant(Permanent permanent, Game game, UUID defenderId) {
        if (permanent == null) {
            return null;
        }
        Player controller = game.getPlayer(permanent.getControllerId());
        CombatEvaluator combatEvaluator = new CombatEvaluator();
        return new AiDecisionTrace.Combatant(
                idString(permanent.getId()),
                permanent.getName(),
                idString(permanent.getControllerId()),
                controller == null ? "" : controller.getName(),
                idString(defenderId),
                objectName(game, defenderId),
                permanent.getPower().getValue(),
                permanent.getToughness().getValue(),
                CombatUtil.getCombatDamageValue(permanent, game),
                combatEvaluator.evaluate(permanent, game),
                combatAbilityNames(permanent, game)
        );
    }

    private List<String> combatAbilityNames(Permanent permanent, Game game) {
        List<String> abilities = new ArrayList<>();
        addCombatAbility(abilities, permanent, game, FlyingAbility.class, "flying");
        addCombatAbility(abilities, permanent, game, ReachAbility.class, "reach");
        addCombatAbility(abilities, permanent, game, FirstStrikeAbility.class, "first strike");
        addCombatAbility(abilities, permanent, game, DoubleStrikeAbility.class, "double strike");
        addCombatAbility(abilities, permanent, game, DeathtouchAbility.class, "deathtouch");
        addCombatAbility(abilities, permanent, game, IndestructibleAbility.class, "indestructible");
        addCombatAbility(abilities, permanent, game, TrampleAbility.class, "trample");
        addCombatAbility(abilities, permanent, game, VigilanceAbility.class, "vigilance");
        addCombatAbility(abilities, permanent, game, LifelinkAbility.class, "lifelink");
        addCombatAbility(abilities, permanent, game, MenaceAbility.class, "menace");
        return abilities;
    }

    private void addCombatAbility(List<String> abilities, Permanent permanent, Game game,
                                  Class abilityClass, String name) {
        if (permanent.getAbilities(game).containsClass(abilityClass)) {
            abilities.add(name);
        }
    }

    private String objectName(Game game, UUID objectId) {
        if (objectId == null) {
            return "";
        }
        Player player = game.getPlayer(objectId);
        if (player != null) {
            return player.getName();
        }
        MageObject object = game.getObject(objectId);
        return object == null ? String.valueOf(objectId) : object.getName();
    }

    private String idString(UUID objectId) {
        return objectId == null ? "" : objectId.toString();
    }

    /**
     * Choose attackers based on static information. That means that AI won't
     * look to the future as it was before, but just choose attackers based on
     * current state of the game. This is worse, but at least it is easier to
     * implement and won't lead to the case when AI doesn't do anything -
     * neither attack nor block.
     *
     * @param game
     * @param activePlayerId
     */
    private void declareAttackers(Game game, UUID activePlayerId) {
        attackersToCheck.clear();
        attackersList.clear();
        game.fireEvent(new GameEvent(GameEvent.EventType.DECLARE_ATTACKERS_STEP_PRE, null, null, activePlayerId));
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARING_ATTACKERS, activePlayerId, activePlayerId))) {
            Player attackingPlayer = game.getPlayer(activePlayerId);

            // check lethal strike first
            for (UUID defenderId : game.getOpponents(playerId, true)) {
                Player defender = game.getPlayer(defenderId);
                if (!defender.isInGame()) {
                    continue;
                }

                attackersList = super.getAvailableAttackers(defenderId, game);
                if (attackersList.isEmpty()) {
                    continue;
                }
                List<Permanent> possibleBlockers = defender.getAvailableBlockers(game);
                List<Permanent> killers = chooseLethalAttackers(game, attackersList, possibleBlockers, defender);
                if (!killers.isEmpty()) {
                    Set<UUID> attackersBefore = traceCombatAttackersBefore(game);
                    for (Permanent attacker : killers) {
                        attackingPlayer.declareAttacker(attacker.getId(), defenderId, game, false);
                    }
                    traceAttackersSelected(
                            game,
                            defenderId,
                            "alpha-strike-lethal",
                            attackersList.size(),
                            possibleBlockers.size(),
                            attackersBefore
                    );
                    return;
                }
            }

            // TODO: add game simulations here to find best attackers/blockers combination

            // find safe attackers (can't be killed by blockers)
            for (UUID defenderId : game.getOpponents(playerId, true)) {
                Player defender = game.getPlayer(defenderId);
                if (!defender.isInGame()) {
                    continue;
                }
                attackersList = super.getAvailableAttackers(defenderId, game);
                if (attackersList.isEmpty()) {
                    continue;
                }
                List<Permanent> possibleBlockers = defender.getAvailableBlockers(game);
                Set<UUID> attackersBefore = traceCombatAttackersBefore(game);

                // The AI will now attack more sanely.  Simple, but good enough for now.
                // The sim minmax does not work at the moment.
                boolean safeToAttack;
                CombatEvaluator eval = new CombatEvaluator();

                for (Permanent attacker : attackersList) {
                    safeToAttack = true;
                    int attackerValue = eval.evaluate(attacker, game);
                    for (Permanent blocker : possibleBlockers) {
                        int blockerValue = eval.evaluate(blocker, game);

                        int attackerDamage = getAttackHeuristicDamage(attacker, game);
                        int blockerDamage = getAttackHeuristicDamage(blocker, game);

                        // blocker can kill attacker
                        if (attackerDamage <= blocker.getToughness().getValue()
                                && attacker.getToughness().getValue() <= blockerDamage) {
                            safeToAttack = false;
                        }

                        // attacker and blocker have the same P/T, check their overall value
                        if (attacker.getToughness().getValue() == blockerDamage
                                && attackerDamage == blocker.getToughness().getValue()) {
                            if (attackerValue > blockerValue
                                    || blocker.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId())
                                    || blocker.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId())
                                    || blocker.getAbilities().contains(new ExaltedAbility())
                                    || blocker.getAbilities().containsKey(DeathtouchAbility.getInstance().getId())
                                    || blocker.getAbilities().containsKey(IndestructibleAbility.getInstance().getId())
                                    || !attacker.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId())
                                    || !attacker.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId())
                                    || !attacker.getAbilities().contains(new ExaltedAbility())) {
                                safeToAttack = false;
                            }
                        }

                        // attacker can kill by deathtouch
                        if (attacker.getAbilities().containsKey(DeathtouchAbility.getInstance().getId())
                                || attacker.getAbilities().containsKey(IndestructibleAbility.getInstance().getId())) {
                            safeToAttack = true;
                        }

                        // attacker has flying and blocker has neither flying nor reach
                        if (attacker.getAbilities().containsKey(FlyingAbility.getInstance().getId())
                                && !blocker.getAbilities().containsKey(FlyingAbility.getInstance().getId())
                                && !blocker.getAbilities().containsKey(ReachAbility.getInstance().getId())) {
                            safeToAttack = true;
                        }

                        // if any check fails, move on to the next possible attacker
                        if (!safeToAttack) {
                            break;
                        }
                    }

                    // 0 combat damage, don't bother attacking
                    if (getAttackHeuristicDamage(attacker, game) == 0) {
                        safeToAttack = false;
                    }

                    // add attacker to the next list of all attackers that can safely attack
                    if (safeToAttack) {
                        attackersToCheck.add(attacker);
                    }
                }
                if (shouldKeepDefenseReserve(game)) {
                    attackersToCheck = keepDefenseReserve(game, attackersToCheck);
                }

                // find possible target for attack (priority: planeswalker -> battle -> player)
                int totalDamageOfAttackers = 0;
                int usedDamageOfAttackers = 0;
                for (Permanent attacker : attackersToCheck) {
                    totalDamageOfAttackers += getAttackHeuristicDamage(attacker, game);
                }

                // TRY ATTACK PLANESWALKER + BATTLE
                List<Permanent> possiblePermanentDefenders = new ArrayList<>();
                // planeswalker first priority
                game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_PLANESWALKER, activePlayerId, game)
                        .stream()
                        .filter(p -> p.canBeAttacked(null, defenderId, game))
                        .forEach(possiblePermanentDefenders::add);
                // battle second priority
                game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_BATTLE, activePlayerId, game)
                        .stream()
                        .filter(p -> p.canBeAttacked(null, defenderId, game))
                        .forEach(possiblePermanentDefenders::add);

                for (Permanent permanentDefender : possiblePermanentDefenders) {
                    if (usedDamageOfAttackers >= totalDamageOfAttackers) {
                        break;
                    }
                    int currentCounters;
                    if (permanentDefender.isPlaneswalker(game)) {
                        currentCounters = permanentDefender.getCounters(game).getCount(CounterType.LOYALTY);
                    } else if (permanentDefender.isBattle(game)) {
                        currentCounters = permanentDefender.getCounters(game).getCount(CounterType.DEFENSE);
                    } else {
                        // impossible error (SBA must remove all planeswalkers/battles with 0 counters before declare attackers)
                        throw new IllegalStateException("AI: can't find counters for defending permanent " + permanentDefender.getName(), new Throwable());
                    }

                    // attack anyway (for kill or damage)
                    // TODO: add attackers optimization here (1 powerfull + min number of additional permanents,
                    //  current code uses random/etb order)
                    for (Permanent attackingPermanent : attackersToCheck) {
                        if (attackingPermanent.isAttacking()) {
                            // already used for another target
                            continue;
                        }
                        attackingPlayer.declareAttacker(attackingPermanent.getId(), permanentDefender.getId(), game, true);
                        int attackDamage = getAttackHeuristicDamage(attackingPermanent, game);
                        currentCounters -= attackDamage;
                        usedDamageOfAttackers += attackDamage;
                        if (currentCounters <= 0) {
                            break;
                        }
                    }
                }

                // TRY ATTACK PLAYER
                // any remaining attackers go for the player
                for (Permanent attackingPermanent : attackersToCheck) {
                    if (attackingPermanent.isAttacking()) {
                        continue;
                    }
                    attackingPlayer.declareAttacker(attackingPermanent.getId(), defenderId, game, true);
                }
                int selectedAttackers = countNewAttackers(game, attackersBefore);
                traceAttackersSelected(
                        game,
                        defenderId,
                        selectedAttackers > 0 ? "safe-attackers-static" : "no-attackers-selected-static",
                        attackersList.size(),
                        possibleBlockers.size(),
                        attackersBefore
                );
            }
        }
    }

    protected List<Permanent> chooseLethalAttackers(Game game, List<Permanent> attackersList,
                                                    List<Permanent> possibleBlockers, Player defender) {
        return CombatUtil.canKillOpponent(game, attackersList, possibleBlockers, defender);
    }

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {
        logger.debug("selectAttackers");
        declareAttackers(game, playerId);
    }

    @Override
    public void selectBlockers(Ability source, Game game, UUID defendingPlayerId) {
        logger.debug("selectBlockers");
        declareBlockers(game, playerId);
    }

    /**
     * Copies game and replaces all players in copy with simulated players
     *
     * @param game
     * @return a new game object with simulated players
     */
    protected Game createSimulation(Game game) {
        Game sim = game.createSimulationForAI();
        for (Player oldPlayer : sim.getState().getPlayers().values()) {
            // replace original player by simulated player and find result (execute/resolve current action)
            Player origPlayer = game.getState().getPlayers().get(oldPlayer.getId()).copy();
            SimulatedPlayer2 simPlayer = new SimulatedPlayer2(
                    oldPlayer,
                    oldPlayer.getId().equals(playerId),
                    getMaxSimulatedAttackCombinations(),
                    getMaxSimulatedTargetOptionsPerAbility(),
                    shouldUseHandQualityDiscardTargeting(),
                    shouldUseStrategicCastVariants()
            );
            simPlayer.restore(origPlayer);
            sim.getState().getPlayers().put(oldPlayer.getId(), simPlayer);
        }
        return sim;
    }

    protected int getMaxSimulatedAttackCombinations() {
        return 0;
    }

    protected int getMaxSimulatedTargetOptionsPerAbility() {
        return 0;
    }

    protected int getMaxSearchCacheEntries() {
        return 0;
    }

    protected boolean shouldUseHandQualityDiscardTargeting() {
        return false;
    }

    protected boolean shouldUseStrategicCastVariants() {
        return false;
    }

    protected boolean useStableMinimaxBestChildSelection() {
        return false;
    }

    protected boolean shouldUseCombatDamageForAttackHeuristics() {
        return false;
    }

    protected boolean shouldKeepDefenseReserve(Game game) {
        return false;
    }

    protected boolean useStablePriorityFallbackSelection() {
        return false;
    }

    protected boolean shouldPassSimulatedPlayersOnSimulatedGame() {
        return false;
    }

    protected int getAttackHeuristicDamage(Permanent permanent, Game game) {
        if (permanent == null) {
            return 0;
        }
        if (shouldUseCombatDamageForAttackHeuristics()) {
            return CombatUtil.getCombatDamageValue(permanent, game);
        }
        return Math.max(0, permanent.getPower().getValue());
    }

    private void initializeSearchCache() {
        searchCacheHits = 0;
        searchCacheMisses = 0;
        int maxEntries = getMaxSearchCacheEntries();
        if (maxEntries <= 0) {
            searchCache = null;
            return;
        }
        searchCache = new LinkedHashMap<SearchCacheKey, Integer>(Math.min(maxEntries, 1024), 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<SearchCacheKey, Integer> eldest) {
                return size() > maxEntries;
            }
        };
    }

    private SearchCacheKey createSearchCacheKey(Game game, int depth, int alpha, int beta) {
        if (searchCache == null || game == null) {
            return null;
        }
        UUID priorityPlayerId = game.getPlayerList().get();
        int stateHash = game.getState().getValue(true).hashCode();
        int stackHash = game.getStack().toString().hashCode();
        return new SearchCacheKey(stateHash, stackHash, priorityPlayerId, depth, alpha, beta);
    }

    private static final class SearchCacheKey {
        private final int stateHash;
        private final int stackHash;
        private final UUID priorityPlayerId;
        private final int depth;
        private final int alpha;
        private final int beta;

        private SearchCacheKey(int stateHash, int stackHash, UUID priorityPlayerId, int depth, int alpha, int beta) {
            this.stateHash = stateHash;
            this.stackHash = stackHash;
            this.priorityPlayerId = priorityPlayerId;
            this.depth = depth;
            this.alpha = alpha;
            this.beta = beta;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof SearchCacheKey)) {
                return false;
            }
            SearchCacheKey that = (SearchCacheKey) object;
            return stateHash == that.stateHash
                    && stackHash == that.stackHash
                    && depth == that.depth
                    && alpha == that.alpha
                    && beta == that.beta
                    && Objects.equals(priorityPlayerId, that.priorityPlayerId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(stateHash, stackHash, priorityPlayerId, depth, alpha, beta);
        }
    }

    private boolean isDecisionTraceEnabled() {
        return decisionTrace != null && decisionTrace != NoOpAiDecisionTrace.INSTANCE;
    }

    private boolean isStateImpactTraceEnabled() {
        return Boolean.getBoolean(JsonlAiDecisionTrace.TRACE_STATE_IMPACT_PROPERTY);
    }

    private void startTraceDecision() {
        if (!isDecisionTraceEnabled() || root == null) {
            return;
        }
        activeTraceDecisionId = ++nextTraceDecisionId;
        traceSearchNode(root.getGame(), root, "decision-start", maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, currentScore, null);
    }

    private long getActiveTraceDecisionId() {
        if (activeTraceDecisionId <= 0) {
            activeTraceDecisionId = ++nextTraceDecisionId;
        }
        return activeTraceDecisionId;
    }

    private void traceSearchNode(Game game, SimulationNode2 node, String eventType, int searchDepth,
                                 int alpha, int beta, Integer score, String note) {
        if (!isDecisionTraceEnabled() || activeTraceDecisionId <= 0) {
            return;
        }
        decisionTrace.recordSearchNode(game, this, activeTraceDecisionId, node, eventType, searchDepth, alpha, beta, score, note);
    }

    private SimulationNode2 getFinalBestLineNode(SimulationNode2 node) {
        SimulationNode2 current = node;
        while (current != null && current.getChildren() != null && !current.getChildren().isEmpty()) {
            current = current.getChildren().get(0);
        }
        return current == null ? node : current;
    }

    private List<AiDecisionTrace.BestLineStep> collectBestLine(Game displayGame, SimulationNode2 startNode) {
        List<AiDecisionTrace.BestLineStep> bestLine = new ArrayList<>();
        SimulationNode2 current = startNode;
        while (current != null && bestLine.size() < 8) {
            if (current.getAbilities() != null && !current.getAbilities().isEmpty()) {
                Ability ability = current.getAbilities().get(0);
                bestLine.add(new AiDecisionTrace.BestLineStep(
                        current.getDepth(),
                        getAbilityAndSourceInfo(current.getGame() == null ? displayGame : current.getGame(), ability, true)
                ));
            }
            if (current.getChildren() == null || current.getChildren().isEmpty()) {
                break;
            }
            current = current.getChildren().get(0);
        }
        return bestLine;
    }

    private boolean checkForRepeatedAction(Game sim, SimulationNode2 node, Ability action, UUID playerId) {
        // pass or casting two times a spell multiple times on hand is ok
        if (action instanceof PassAbility || action instanceof SpellAbility || action.isManaAbility()) {
            return false;
        }
        int newVal = evaluator.evaluate(playerId, sim).getTotalScore();
        SimulationNode2 test = node.getParent();
        while (test != null) {
            if (test.getPlayerId().equals(playerId)) {
                if (test.getAbilities() != null && test.getAbilities().size() == 1) {
                    if (action.toString().equals(test.getAbilities().get(0).toString())) {
                        if (test.getParent() != null) {
                            Game prevGame = node.getGame();
                            if (prevGame != null) {
                                int oldVal = evaluator.evaluate(playerId, prevGame).getTotalScore();
                                if (oldVal >= newVal) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            test = test.getParent();
        }
        return false;
    }

    @Override
    public void cleanUpOnMatchEnd() {
        root = null;
        if (decisionTrace != null) {
            decisionTrace.close();
            decisionTrace = NoOpAiDecisionTrace.INSTANCE;
        }
        super.cleanUpOnMatchEnd();
    }

}
