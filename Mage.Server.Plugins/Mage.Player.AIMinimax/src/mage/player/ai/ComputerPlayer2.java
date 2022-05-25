

package mage.player.ai;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.PassAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.SearchEffect;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.game.turn.*;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.*;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ComputerPlayer2 extends ComputerPlayer implements Player {

    private static final Logger logger = Logger.getLogger(ComputerPlayer2.class);
    private static final ExecutorService pool = Executors.newFixedThreadPool(1);

    protected int maxDepth;
    protected int maxNodes;
    protected int maxThink;
    protected int nodeCount = 0;
    protected long thinkTime = 0;
    protected transient LinkedList<Ability> actions = new LinkedList<>();
    protected transient List<UUID> targets = new ArrayList<>();
    protected transient List<String> choices = new ArrayList<>();
    protected transient Combat combat;
    protected int currentScore;
    protected transient SimulationNode root;

    public ComputerPlayer2(String name, RangeOfInfluence range, int skill) {
        super(name, range);
        maxDepth = skill * 2;
        maxThink = skill * 3;
        maxNodes = Config.maxNodes;
    }

    public ComputerPlayer2(final ComputerPlayer2 player) {
        super(player);
        this.maxDepth = player.maxDepth;
        this.currentScore = player.currentScore;
        if (player.combat != null) {
            this.combat = player.combat.copy();
        }
        this.actions.addAll(player.actions);
        this.targets.addAll(player.targets);
        this.choices.addAll(player.choices);
    }

    @Override
    public ComputerPlayer2 copy() {
        return new ComputerPlayer2(this);
    }

    @Override
    public boolean priority(Game game) {
        logState(game);
        game.getState().setPriorityPlayerId(playerId);
        game.firePriorityEvent(playerId);
        switch (game.getTurn().getStepType()) {
            case UPKEEP:
            case DRAW:
                pass(game);
                return false;
            case PRECOMBAT_MAIN:
            case BEGIN_COMBAT:
            case DECLARE_ATTACKERS:
            case DECLARE_BLOCKERS:
            case FIRST_COMBAT_DAMAGE:
            case COMBAT_DAMAGE:
            case END_COMBAT:
            case POSTCOMBAT_MAIN:
                if (actions.isEmpty()) {
                    calculateActions(game);
                }
                act(game);
                return true;
            case END_TURN:
            case CLEANUP:
                pass(game);
                return false;
        }
        return false;
    }

    protected void act(Game game) {
        if (actions == null || actions.isEmpty())
            pass(game);
        else {
            boolean usedStack = false;
            while (actions.peek() != null) {
                Ability ability = actions.poll();
                this.activateAbility((ActivatedAbility) ability, game);
                if (logger.isDebugEnabled())
                    logger.debug("activating: " + ability);
                if (ability.isUsesStack())
                    usedStack = true;
            }
            if (usedStack)
                pass(game);
        }
        logger.info("Turn " + game.getTurnNum() + " Step " + game.getStep().toString() + " Player " + name + " Life " + life);
    }

    protected void calculateActions(Game game) {
        currentScore = GameStateEvaluator.evaluate(playerId, game);
        if (!getNextAction(game)) {
            Game sim = createSimulation(game);
            SimulationNode.resetCount();
            root = new SimulationNode(null, sim, playerId);
            logger.debug("simulating actions");
            if (!isTestMode)
                addActionsTimed();
            else
                addActions(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
            logger.info(name + " simulated " + nodeCount + " nodes in " + thinkTime/1000000000.0 + "s - average " + nodeCount/(thinkTime/1000000000.0) + " nodes/s");
            if (!root.children.isEmpty()) {
                root = root.children.get(0);
                actions = new LinkedList<>(root.abilities);
                combat = root.combat;
                if (logger.isDebugEnabled())
                    logger.debug("adding actions:" + actions);
            }
            else
                logger.debug("no actions added");
        }
    }

    protected boolean getNextAction(Game game) {
        if (root != null && !root.children.isEmpty()) {
            SimulationNode test = root;
            root = root.children.get(0);
            while (!root.children.isEmpty() && !root.playerId.equals(playerId)) {
                test = root;
                root = root.children.get(0);
            }
            logger.debug("simlating -- game value:" + game.getState().getValue(true) + " test value:" + test.gameValue);
            if (root.playerId.equals(playerId) && root.abilities != null && game.getState().getValue(true).hashCode() == test.gameValue) {
                logger.debug("simulating -- continuing previous action chain");
                actions = new LinkedList<>(root.abilities);
                combat = root.combat;
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    protected int minimaxAB(SimulationNode node, int alpha, int beta) {
        UUID currentPlayerId = node.getGame().getPlayerList().get();
        SimulationNode bestChild = null;
        boolean isSimulatedPlayer = currentPlayerId.equals(playerId);
        for (SimulationNode child: node.getChildren()) {
            if (alpha >= beta) {
                logger.debug(indent(node.depth) + "alpha beta pruning");
                break;
            }
//            if (SimulationNode.nodeCount > maxNodes) {
//                logger.debug(indent(node.depth) + "simulating -- reached end-state");
//                break;
//            }
            int val = addActions(child, alpha, beta);
            if (!isSimulatedPlayer) {
                if (val < beta) {
                    beta = val;
                    bestChild = child;
                    node.setCombat(child.getCombat());
                }
                if (val == GameStateEvaluator.LOSE_SCORE) {
                    logger.debug(indent(node.depth) + "simulating -- lose, can't do worse than this");
                    break;
                }
            }
            else {
                if (val > alpha) {
                    alpha = val;
                    bestChild = child;
                    node.setCombat(child.getCombat());
                }
                if (val == GameStateEvaluator.WIN_SCORE) {
                    logger.debug(indent(node.depth) + "simulating -- win, can't do better than this");
                    break;
                }
            }
        }
        node.children.clear();
        if (bestChild != null)
            node.children.add(bestChild);
        if (!isSimulatedPlayer) {
            logger.debug(indent(node.depth) + "returning minimax beta: " + beta);
            return beta;
        }
        else {
            logger.debug(indent(node.depth) + "returning minimax alpha: " + alpha);
            return alpha;
        }
    }

    protected SearchEffect getSearchEffect(StackAbility ability) {
        for (Effect effect: ability.getEffects()) {
            if (effect instanceof SearchEffect) {
                return (SearchEffect) effect;
            }
        }
        return null;
    }

    protected void resolve(SimulationNode node, Game game) {
        StackObject ability = game.getStack().pop();
        if (ability instanceof StackAbility) {
            SearchEffect effect = getSearchEffect((StackAbility) ability);
            if (effect != null && ability.getControllerId().equals(playerId)) {
                Target target = effect.getTarget();
                if (!target.doneChosing()) {
                    for (UUID targetId: target.possibleTargets(ability.getControllerId(), ability.getStackAbility(), game)) {
                        Game sim = game.copy();
                        StackAbility newAbility = (StackAbility) ability.copy();
                        SearchEffect newEffect = getSearchEffect((StackAbility) newAbility);
                        newEffect.getTarget().addTarget(targetId, newAbility, sim);
                        sim.getStack().push(newAbility);
                        SimulationNode newNode = new SimulationNode(node, sim, ability.getControllerId());
                        node.children.add(newNode);
                        newNode.getTargets().add(targetId);
                        logger.debug(indent(node.depth) + "simulating search -- node#: " + SimulationNode.getCount() + "for player: " + sim.getPlayer(ability.getControllerId()).getName());
                    }
                    return;
                }
            }
        }
        logger.debug(indent(node.depth) + "simulating resolve ");
        ability.resolve(game);
        game.applyEffects();
        game.getPlayers().resetPassed();
        game.getPlayerList().setCurrent(game.getActivePlayerId());
    }

    protected void addActionsTimed() {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return addActions(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
            }
        });
        long startTime = System.nanoTime();
        pool.execute(task);
        try {
            task.get(maxThink, TimeUnit.SECONDS);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            logger.info("Calculated " + SimulationNode.nodeCount + " nodes in " + duration/1000000000.0 + 's');
            nodeCount += SimulationNode.nodeCount;
            thinkTime += duration;
        } catch (TimeoutException e) {
            logger.debug("simulating - timed out");
            task.cancel(true);
            // sleep for 1 second to allow cleanup to finish
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                logger.fatal("can't sleep");
            }
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            logger.info("Timeout - Calculated " + SimulationNode.nodeCount + " nodes in " + duration/1000000000.0 + 's');
            nodeCount += SimulationNode.nodeCount;
            thinkTime += duration;
        } catch (ExecutionException e) {
            logger.fatal("Simulation error", e);
            task.cancel(true);
        } catch (InterruptedException e) {
            logger.fatal("Simulation interrupted", e);
            task.cancel(true);
        }
    }

    protected int addActions(SimulationNode node, int alpha, int beta) {
        Game game = node.getGame();
        if (Thread.interrupted()) {
            Thread.currentThread().interrupt();
            logger.debug(indent(node.depth) + "interrupted");
            return GameStateEvaluator.evaluate(playerId, game);
        }
        int val;
        if (node.depth > maxDepth || game.checkIfGameIsOver()) {
            logger.debug(indent(node.depth) + "simulating -- reached end state");
            val = GameStateEvaluator.evaluate(playerId, game);
        }
        else if (!node.getChildren().isEmpty()) {
            logger.debug(indent(node.depth) + "simulating -- somthing added children:" + node.getChildren().size());
            val = minimaxAB(node, alpha, beta);
        }
        else {
            if (logger.isDebugEnabled())
                logger.debug(indent(node.depth) + "simulating -- alpha: " + alpha + " beta: " + beta + " depth:" + node.depth + " step:" + game.getTurn().getStepType() + " for player:" + (node.getPlayerId().equals(playerId)?"yes":"no"));
            if (allPassed(game)) {
                if (!game.getStack().isEmpty()) {
                    resolve(node, game);
                }
                else {
//                    int testScore = GameStateEvaluator.evaluate(playerId, game);
//                    if (testScore < currentScore) {
//                        // if score at end of step is worse than original score don't check any further
//                        logger.debug("simulating -- abandoning current check, no immediate benefit");
//                        return testScore;
//                    }
                    game.getPlayers().resetPassed();
                    playNext(game, game.getActivePlayerId(), node);
                }
            }

            if (game.checkIfGameIsOver()) {
                val = GameStateEvaluator.evaluate(playerId, game);
            }
            else if (!node.getChildren().isEmpty()) {
                //declared attackers or blockers or triggered abilities
                logger.debug(indent(node.depth) + "simulating -- attack/block/trigger added children:" + node.getChildren().size());
                val = minimaxAB(node, alpha, beta);
            }
            else {
                val = simulatePriority(node, game, alpha, beta);
            }
        }

        if (logger.isDebugEnabled())
            logger.debug(indent(node.depth) + "returning -- score: " + val + " depth:" + node.depth + " step:" + game.getTurn().getStepType() + " for player:" + game.getPlayer(node.getPlayerId()).getName());
        return val;

    }

    protected int simulatePriority(SimulationNode node, Game game, int alpha, int beta) {
        // NOT USED in real AI, see ComputerPlayer6
        if (Thread.interrupted()) {
            Thread.currentThread().interrupt();
            logger.debug(indent(node.depth) + "interrupted");
            return GameStateEvaluator.evaluate(playerId, game);
        }
        node.setGameValue(game.getState().getValue(true).hashCode());
        SimulatedPlayer currentPlayer = (SimulatedPlayer) game.getPlayer(game.getPlayerList().get());
        boolean isSimulatedPlayer = currentPlayer.getId().equals(playerId);
        logger.debug(indent(node.depth) + "simulating priority -- player " + currentPlayer.getName());
        SimulationNode bestNode = null;
        List<Ability> allActions = currentPlayer.simulatePriority(game);
        if (logger.isDebugEnabled())
            logger.debug(indent(node.depth) + "simulating -- adding " + allActions.size() + " children:" + allActions);
        for (Ability action: allActions) {
            if (Thread.interrupted()) {
                Thread.currentThread().interrupt();
                logger.debug(indent(node.depth) + "interrupted");
                break;
            }
            Game sim = game.copy();
            if (sim.getPlayer(currentPlayer.getId()).activateAbility((ActivatedAbility) action.copy(), sim)) {
                sim.applyEffects();
                if (checkForUselessAction(sim, node, action, currentPlayer.getId())) {
                    logger.debug(indent(node.depth) + "found useless action: " + action);
                    continue;
                }
                if (!sim.checkIfGameIsOver() && action.isUsesStack()) {
                    // only pass if the last action uses the stack
                    sim.getPlayer(currentPlayer.getId()).pass(game);
                    sim.getPlayerList().getNext();
                }
                SimulationNode newNode = new SimulationNode(node, sim, action, currentPlayer.getId());
                if (logger.isDebugEnabled())
                    logger.debug(indent(newNode.depth) + "simulating -- node #:" + SimulationNode.getCount() + " actions:" + action);
                sim.checkStateAndTriggered();
                int val = addActions(newNode, alpha, beta);
                if (!isSimulatedPlayer) {
                    if (val < beta) {
                        beta = val;
                        bestNode = newNode;
                        node.setCombat(newNode.getCombat());
                    }
                    if (val == GameStateEvaluator.LOSE_SCORE) {
                        logger.debug(indent(node.depth) + "simulating -- lose, can't do worse than this");
                        break;
                    }
                }
                else {
                    if (val > alpha) {
                        alpha = val;
                        bestNode = newNode;
                        node.setCombat(newNode.getCombat());
                        if (!node.getTargets().isEmpty())
                            targets = node.getTargets();
                        if (!node.getChoices().isEmpty())
                            choices = node.getChoices();
                    }
                    if (val == GameStateEvaluator.WIN_SCORE) {
                        logger.debug(indent(node.depth) + "simulating -- win, can't do better than this");
                        break;
                    }
                }
                if (alpha >= beta) {
                    logger.debug(indent(node.depth) + "simulating -- pruning");
                    break;
                }
//                if (SimulationNode.nodeCount > maxNodes) {
//                    logger.debug(indent(node.depth) + "simulating -- reached end-state");
//                    break;
//                }
            }
        }
        if (bestNode != null) {
            node.children.clear();
            node.children.add(bestNode);
        }
        if (!isSimulatedPlayer) {
            logger.debug(indent(node.depth) + "returning priority beta: " + beta);
            return beta;
        }
        else {
            logger.debug(indent(node.depth) + "returning priority alpha: " + alpha);
            return alpha;
        }
    }

    protected boolean allPassed(Game game) {
        for (Player player: game.getPlayers().values()) {
            if (!player.isPassed() && !player.hasLost() && !player.hasLeft())
                return false;
        }
        return true;
    }

    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        if (choices.isEmpty()) {
            return super.choose(outcome, choice, game);
        }

        if (!choice.isChosen()) {
            if(!choice.setChoiceByAnswers(choices, true)){
                choice.setRandomChoice();
            }
        }

        return true;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game)  {
        if (targets.isEmpty())
            return super.chooseTarget(outcome, cards, target, source, game);
        if (!target.doneChosing()) {
            for (UUID targetId: targets) {
                target.addTarget(targetId, source, game);
                if (target.doneChosing()) {
                    targets.clear();
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean choose(Outcome outcome, Cards cards, TargetCard target, Game game)  {
        if (targets.isEmpty())
            return super.choose(outcome, cards, target, game);
        if (!target.doneChosing()) {
            for (UUID targetId: targets) {
                target.add(targetId, game);
                if (target.doneChosing()) {
                    targets.clear();
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    /*@Override
    public boolean playXMana(VariableManaCost cost, ManaCosts<ManaCost> costs, Game game) {
        //SimulatedPlayer.simulateVariableCosts method adds a generic mana cost for each option
        for (ManaCost manaCost: costs) {
            if (manaCost instanceof GenericManaCost) {
                cost.setPayment(manaCost.getPayment());
                logger.debug("using X = " + cost.getPayment().count());
                break;
            }
        }
        game.informPlayers(getName() + " payed " + cost.getPayment().count() + " for " + cost.getText());
        cost.setPaid();
        return true;
    }*/

    public void playNext(Game game, UUID activePlayerId, SimulationNode node) {
        boolean skip = false;
        while (true) {
            Phase currentPhase = game.getPhase();
            if (!skip) {
                currentPhase.getStep().endStep(game, activePlayerId);
            }
            game.applyEffects();
            switch (currentPhase.getStep().getType()) {
                case UNTAP:
                    game.getPhase().setStep(new UpkeepStep());
                    break;
                case UPKEEP:
                    game.getPhase().setStep(new DrawStep());
                    break;
                case DRAW:
                    game.getTurn().setPhase(new PreCombatMainPhase());
                    game.getPhase().setStep(new PreCombatMainStep());
                    break;
                case PRECOMBAT_MAIN:
                    game.getTurn().setPhase(new CombatPhase());
                    game.getPhase().setStep(new BeginCombatStep());
                    break;
                case BEGIN_COMBAT:
                    game.getPhase().setStep(new DeclareAttackersStep());
                    break;
                case DECLARE_ATTACKERS:
                    game.getPhase().setStep(new DeclareBlockersStep());
                    break;
                case DECLARE_BLOCKERS:
                    game.getPhase().setStep(new FirstCombatDamageStep());
                    break;
                case FIRST_COMBAT_DAMAGE:
                    game.getPhase().setStep(new CombatDamageStep());
                    break;
                case COMBAT_DAMAGE:
                    game.getPhase().setStep(new EndOfCombatStep());
                    break;
                case END_COMBAT:
                    game.getTurn().setPhase(new PostCombatMainPhase());
                    game.getPhase().setStep(new PostCombatMainStep());
                    break;
                case POSTCOMBAT_MAIN:
                    game.getTurn().setPhase(new EndPhase());
                    game.getPhase().setStep(new EndStep());
                    break;
                case END_TURN:
                    game.getPhase().setStep(new CleanupStep());
                    break;
                case CLEANUP:
                    game.getPhase().getStep().beginStep(game, activePlayerId);
                    if (!game.checkStateAndTriggered() && !game.checkIfGameIsOver()) {
                        game.getState().setActivePlayerId(game.getState().getPlayerList(game.getActivePlayerId()).getNext());
                        game.getTurn().setPhase(new BeginningPhase());
                        game.getPhase().setStep(new UntapStep());
                    }
            }
            if (!game.getStep().skipStep(game, game.getActivePlayerId())) {
                if (game.getTurn().getStepType() == PhaseStep.DECLARE_ATTACKERS) {
                    game.fireEvent(new GameEvent(GameEvent.EventType.DECLARE_ATTACKERS_STEP_PRE, null, null, activePlayerId));
                    if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARING_ATTACKERS, activePlayerId, activePlayerId))) {
                        for (Combat engagement: ((SimulatedPlayer)game.getPlayer(activePlayerId)).addAttackers(game)) {
                            Game sim = game.copy();
                            UUID defenderId = game.getOpponents(playerId).iterator().next();
                            for (CombatGroup group: engagement.getGroups()) {
                                for (UUID attackerId: group.getAttackers()) {
                                    sim.getPlayer(activePlayerId).declareAttacker(attackerId, defenderId, sim, false);
                                }
                            }
                            sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_ATTACKERS, playerId, playerId));
                            SimulationNode newNode = new SimulationNode(node, sim, activePlayerId);
                            logger.debug(indent(node.depth) + "simulating -- node #:" + SimulationNode.getCount() + " declare attakers");
                            newNode.setCombat(sim.getCombat());
                            node.children.add(newNode);
                        }
                    }
                }
                else if (game.getTurn().getStepType() == PhaseStep.DECLARE_BLOCKERS) {
                    game.fireEvent(new GameEvent(GameEvent.EventType.DECLARE_BLOCKERS_STEP_PRE, null, null, activePlayerId));
                    if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARING_BLOCKERS, activePlayerId, activePlayerId))) {
                        for (UUID defenderId: game.getCombat().getDefenders()) {
                            //check if defender is being attacked
                            if (game.getCombat().isAttacked(defenderId, game)) {
                                for (Combat engagement: ((SimulatedPlayer)game.getPlayer(defenderId)).addBlockers(game)) {
                                    Game sim = game.copy();
                                    for (CombatGroup group: engagement.getGroups()) {
                                        for (UUID blockerId: group.getBlockers()) {
                                            group.addBlocker(blockerId, defenderId, sim);
                                        }
                                    }
                                    sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_BLOCKERS, playerId, playerId));
                                    SimulationNode newNode = new SimulationNode(node, sim, defenderId);
                                    logger.debug(indent(node.depth) + "simulating -- node #:" + SimulationNode.getCount() + " declare blockers");
                                    newNode.setCombat(sim.getCombat());
                                    node.children.add(newNode);
                                }
                            }
                        }
                    }
                }
                else {
                    game.getStep().beginStep(game, activePlayerId);
                }
                if (game.getStep().getHasPriority())
                    break;
            }
            else {
                skip = true;
            }
        }
        game.checkStateAndTriggered();
    }

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {
        if (logger.isDebugEnabled() && (combat == null || combat.getGroups().isEmpty())) {
            logger.debug("not attacking");
        }
        if (combat != null) {
            UUID opponentId = game.getCombat().getDefenders().iterator().next();
            for (UUID attackerId: combat.getAttackers()) {
                this.declareAttacker(attackerId, opponentId, game, false);
                if (logger.isDebugEnabled()) {
                    Permanent p = game.getPermanent(attackerId);
                    if (p != null) {
                        logger.debug("attacking with:" + p.getName());
                    }
                }
             }
        }
    }

    @Override
    public void selectBlockers(Ability source, Game game, UUID defendingPlayerId) {
        logger.debug("selectBlockers");
        if (combat != null && !combat.getGroups().isEmpty()) {
            List<CombatGroup> groups = game.getCombat().getGroups();
            for (int i = 0; i < groups.size(); i++) {
                if (i < combat.getGroups().size()) {
                    for (UUID blockerId: combat.getGroups().get(i).getBlockers()) {
                        this.declareBlocker(defendingPlayerId, blockerId, groups.get(i).getAttackers().get(0), game);
                        if (logger.isDebugEnabled()) {
                            Permanent blocker = game.getPermanent(blockerId);
                            if (blocker != null)
                                logger.debug("blocking with:" + blocker.getName());
                        }
                    }
                }
            }
        }
    }

    /**
     * Copies game and replaces all players in copy with simulated players
     *
     * @param game
     * @return a new game object with simulated players
     */
    protected Game createSimulation(Game game) {
        Game sim = game.copy();

        for (Player oldPlayer: sim.getState().getPlayers().values()) {
            Player origPlayer = game.getState().getPlayers().get(oldPlayer.getId()).copy();
            SimulatedPlayer newPlayer = new SimulatedPlayer(oldPlayer, oldPlayer.getId().equals(playerId), maxDepth);
            newPlayer.restore(origPlayer);
            sim.getState().getPlayers().put(oldPlayer.getId(), newPlayer);
        }
        sim.setSimulation(true);
        return sim;
    }

    /**
     * resolve current ability on the stack if there is one, then
     * check if current game state is the same as the previous, if so then
     * action has no effect and is not useful
     * 
     * @param sim
     * @param node
     * @param action
     * @param playerId
     * @return
     */
    private boolean checkForUselessAction(Game sim, SimulationNode node, Ability action, UUID playerId) {
        int currentVal = 0;
        int prevVal = 0;
        if (action instanceof PassAbility)
            return false;
        SimulationNode test = node.getParent();
        if (test == null)
            return false;
        if (action.isUsesStack()) {
            Game testSim = sim.copy();
            StackObject ability = testSim.getStack().pop();
            ability.resolve(testSim);
            testSim.applyEffects();
            currentVal = GameStateEvaluator.evaluate(playerId, testSim, true);
        }
        else {
            currentVal = GameStateEvaluator.evaluate(playerId, sim, true);
        }
        prevVal = GameStateEvaluator.evaluate(playerId, test.getGame(), true);
        return currentVal == prevVal;
    }

    protected String indent(int num) {
        char[] fill = new char[num];
        Arrays.fill(fill, ' ');
        return Integer.toString(num) + new String(fill);
    }

}
