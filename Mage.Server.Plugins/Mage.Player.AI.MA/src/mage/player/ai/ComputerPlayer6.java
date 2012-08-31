/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.player.ai;

import mage.Constants.Outcome;
import mage.Constants.PhaseStep;
import mage.Constants.RangeOfInfluence;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.PassAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.SearchEffect;
import mage.abilities.keyword.*;
import mage.cards.Card;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureForCombat;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.game.turn.*;
import mage.player.ai.ma.optimizers.TreeOptimizer;
import mage.player.ai.ma.optimizers.impl.DiscardCardOptimizer;
import mage.player.ai.ma.optimizers.impl.EquipOptimizer;
import mage.player.ai.ma.optimizers.impl.LevelUpOptimizer;
import mage.player.ai.util.CombatInfo;
import mage.player.ai.util.CombatUtil;
import mage.player.ai.util.SurviveInfo;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

/**
 *
 * @author nantuko
 */
public class ComputerPlayer6 extends ComputerPlayer<ComputerPlayer6> implements Player {

    private static final transient org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ComputerPlayer6.class);
    private static final ExecutorService pool = Executors.newFixedThreadPool(1);

    protected int maxDepth;
    protected int maxNodes;
    protected int maxThink;
    protected LinkedList<Ability> actions = new LinkedList<Ability>();
    protected List<UUID> targets = new ArrayList<UUID>();
    protected List<String> choices = new ArrayList<String>();
    protected Combat combat;
    protected int currentScore;
    protected SimulationNode2 root;

    private static final String FILE_WITH_INSTRUCTIONS = "config/ai.please.cast.this.txt";
    private List<String> suggested = new ArrayList<String>();
    protected Set<String> actionCache;

    private static final List<TreeOptimizer> optimizers = new ArrayList<TreeOptimizer>();

    static {
        optimizers.add(new LevelUpOptimizer());
        optimizers.add(new EquipOptimizer());
        optimizers.add(new DiscardCardOptimizer());
    }

    public ComputerPlayer6(String name, RangeOfInfluence range, int skill) {
        super(name, range);
        maxDepth = skill * 2;
        maxThink = skill * 3;
        maxNodes = Config2.maxNodes;
        getSuggestedActions();
        this.actionCache = new HashSet<String>();
    }

    public ComputerPlayer6(final ComputerPlayer6 player) {
        super(player);
        this.maxDepth = player.maxDepth;
        this.currentScore = player.currentScore;
        if (player.combat != null)
            this.combat = player.combat.copy();
        this.actions.addAll(player.actions);
        this.targets.addAll(player.targets);
        this.choices.addAll(player.choices);
        this.actionCache = player.actionCache;
    }

    @Override
    public ComputerPlayer6 copy() {
        return new ComputerPlayer6(this);
    }

    @Override
    public boolean priority(Game game) {
        logState(game);
        game.getState().setPriorityPlayerId(playerId);
        game.firePriorityEvent(playerId);
        switch (game.getTurn().getStepType()) {
            case UPKEEP:
            case DRAW:
                pass();
                return false;
            case PRECOMBAT_MAIN:
            case POSTCOMBAT_MAIN:
                if (game.getActivePlayerId().equals(playerId)) {
                    printOutState(game, playerId);
                    printOutState(game, game.getOpponents(playerId).iterator().next());
                    if (actions.size() == 0) {
                        calculateActions(game);
                    }
                    act(game);
                    return true;
                } else {
                    pass();
                }
                return false;
            case BEGIN_COMBAT:
            case FIRST_COMBAT_DAMAGE:
            case COMBAT_DAMAGE:
            case END_COMBAT:
                pass();
                return false;
            case DECLARE_ATTACKERS:
                if (game.getActivePlayerId().equals(playerId)) {
                    //declareAttackers(game, playerId);
                    pass();
                } else {
                    pass();
                }
                return false;
            case DECLARE_BLOCKERS:
                if (!game.getActivePlayerId().equals(playerId)) {
                    declareBlockers(game, playerId);
                    pass();
                } else {
                    pass();
                }
                return false;
            case END_TURN:
                pass();
                return false;
            case CLEANUP:
                pass();
                return false;
        }
        return false;
    }

    protected void printOutState(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        System.out.println("Turn::"+game.getTurnNum());
        System.out.println("[" + game.getPlayer(playerId).getName() + "] " + game.getTurn().getStepType().name() +", life=" + player.getLife());
        Player opponent = game.getPlayer(game.getOpponents(playerId).iterator().next());
        System.out.println("[Opponent] life=" + opponent.getLife());

        String s = "[";
        for (Card card : player.getHand().getCards(game)) {
            s += card.getName() + ";";
        }
        s += "]";
        System.out.println("Hand: " + s);
        s = "[";
        for (Permanent permanent : game.getBattlefield().getAllPermanents()) {
             if (permanent.getOwnerId().equals(player.getId())) {
                 s += permanent.getName();
                 if (permanent.isTapped()) {
                    s+="(tapped)";
                 }
                 if (permanent.isAttacking()) {
                    s+="(attacking)";
                 }
                 s+=";";
             }
        }
        s += "]";
        System.out.println("Permanents: " + s);
    }

    protected void act(Game game) {
        if (actions == null || actions.size() == 0)
            pass();
        else {
            boolean usedStack = false;
            while (actions.peek() != null) {
                Ability ability = actions.poll();
                System.out.println("[" + game.getPlayer(playerId).getName() + "] Action: " + ability.toString());
                if (ability.getTargets().size() > 0) {
                    for (Target target : ability.getTargets()) {
                        for (UUID id : target.getTargets()) {
                            target.updateTarget(id, game);
                        }
                    }
                    Player player = game.getPlayer(ability.getFirstTarget());
                    if (player != null) {
                        System.out.println("targets = " + player.getName());
                    }
                }
                this.activateAbility((ActivatedAbility) ability, game);
                if (ability.isUsesStack())
                    usedStack = true;
                if (!suggested.isEmpty() && !(ability instanceof PassAbility)) {
                    Iterator<String> it = suggested.iterator();
                    while (it.hasNext()) {
                        Card card = game.getCard(ability.getSourceId());
                        String action = it.next();
                        System.out.println("action="+action+";card="+card);
                        if (action.equals(card.getName())) {
                            System.out.println("removed from suggested="+action);
                            it.remove();
                        }
                    }
                }
            }
            if (usedStack)
                pass();
        }
    }

    protected void calculateActions(Game game) {
        if (!getNextAction(game)) {
            Game sim = createSimulation(game);
            SimulationNode2.resetCount();
            root = new SimulationNode2(null, sim, maxDepth, playerId);
            logger.info("simulating actions");
            //int bestScore = addActionsTimed(new FilterAbility());
            currentScore = GameStateEvaluator2.evaluate(playerId, game);
            addActionsTimed();
            if (root.children.size() > 0) {
                root = root.children.get(0);
                //GameStateEvaluator2.evaluate(playerId, root.getGame());
                int bestScore = root.getScore();
                //if (bestScore > currentScore) {
                    actions = new LinkedList<Ability>(root.abilities);
                    combat = root.combat;
                //} else {
                    //System.out.println("[" + game.getPlayer(playerId).getName() + "] Action: not better score");
                //}
            } else {
                System.out.println("[" + game.getPlayer(playerId).getName() + "] Action: skip");
            }
        }
    }

    protected boolean getNextAction(Game game) {
        if (root != null && root.children.size() > 0) {
            SimulationNode2 test = root;
            root = root.children.get(0);
            while (root.children.size() > 0 && !root.playerId.equals(playerId)) {
                test = root;
                root = root.children.get(0);
            }
            logger.info("simlating -- game value:" + game.getState().getValue(true) + " test value:" + test.gameValue);
            if (!suggested.isEmpty()) {
                return false;
            }
            if (root.playerId.equals(playerId) && root.abilities != null && game.getState().getValue(true).hashCode() == test.gameValue) {

                /*
                // Try to fix horizon effect
                if (root.combat == null || root.combat.getAttackers().size() == 0) {
                    FilterCreatureForAttack attackFilter = new FilterCreatureForAttack();
                    attackFilter.getControllerId().add(playerId);
                    List<Permanent> attackers = game.getBattlefield().getAllActivePermanents(attackFilter);
                    if (attackers.size() > 0) {
                        // we have attackers but don't attack with any of them
                        // let's try once again to avoid possible horizon effect
                        return false;
                    }
                }
                */

                logger.info("simulating -- continuing previous action chain");
                actions = new LinkedList<Ability>(root.abilities);
                combat = root.combat;
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    protected int minimaxAB(SimulationNode2 node, int depth, int alpha, int beta) {
        UUID currentPlayerId = node.getGame().getPlayerList().get();
        SimulationNode2 bestChild = null;
        for (SimulationNode2 child: node.getChildren()) {
            Combat _combat = child.getCombat();
            if (alpha >= beta) {
                //logger.info("alpha beta pruning");
                break;
            }
            if (SimulationNode2.nodeCount > maxNodes) {
                //logger.info("simulating -- reached end-state, count=" + SimulationNode2.nodeCount);
                break;
            }
            int val = addActions(child, depth-1, alpha, beta);
            if (!currentPlayerId.equals(playerId)) {
                if (val < beta) {
                    beta = val;
                    bestChild = child;
                    if (node.getCombat() == null) {
                        node.setCombat(_combat);
                        bestChild.setCombat(_combat);
                    }
                }
                // no need to check other actions
                if (val == GameStateEvaluator2.LOSE_GAME_SCORE) {
                    logger.debug("lose - break");
                    break;
                }
            }
            else {
                if (val > alpha) {
                    alpha = val;
                    bestChild = child;
                    if (node.getCombat() == null) {
                        node.setCombat(_combat);
                        bestChild.setCombat(_combat);
                    }
                }
                // no need to check other actions
                if (val == GameStateEvaluator2.WIN_GAME_SCORE) {
                    logger.debug("win - break");
                    break;
                }
            }
        }
        node.children.clear();
        if (bestChild != null)
            node.children.add(bestChild);
        if (!currentPlayerId.equals(playerId)) {
            //logger.info("returning minimax beta: " + beta);
            return beta;
        }
        else {
            //logger.info("returning minimax alpha: " + alpha);
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

    protected void resolve(SimulationNode2 node, int depth, Game game) {
        StackObject ability = game.getStack().pop();
        if (ability instanceof StackAbility) {
            SearchEffect effect = getSearchEffect((StackAbility) ability);
            if (effect != null && ability.getControllerId().equals(playerId)) {
                Target target = effect.getTarget();
                if (!target.doneChosing()) {
                    for (UUID targetId: target.possibleTargets(ability.getSourceId(), ability.getControllerId(), game)) {
                        Game sim = game.copy();
                        StackAbility newAbility = (StackAbility) ability.copy();
                        SearchEffect newEffect = getSearchEffect((StackAbility) newAbility);
                        newEffect.getTarget().addTarget(targetId, newAbility, sim);
                        sim.getStack().push(newAbility);
                        SimulationNode2 newNode = new SimulationNode2(node, sim, depth, ability.getControllerId());
                        node.children.add(newNode);
                        newNode.getTargets().add(targetId);
                        logger.debug("simulating search -- node#: " + SimulationNode2.getCount() + "for player: " + sim.getPlayer(ability.getControllerId()).getName());
                    }
                    return;
                }
            }
        }
        //logger.info("simulating resolve ");
        ability.resolve(game);
        game.applyEffects();
        game.getPlayers().resetPassed();
        game.getPlayerList().setCurrent(game.getActivePlayerId());
    }

    protected Integer addActionsTimed() {
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception
            {
                return addActions(root, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
            }
        });
        pool.execute(task);
        try {
            System.out.println("maxThink:" + maxThink);
            return task.get(maxThink, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            logger.info("simulating - timed out");
            task.cancel(true);
        } catch (ExecutionException e) {
            e.printStackTrace();
            task.cancel(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
            task.cancel(true);
        } catch (Exception e) {
            e.printStackTrace();
            task.cancel(true);
        }
        //TODO: timeout handling
        return 0;
    }

    protected int addActions(SimulationNode2 node, int depth, int alpha, int beta) {
        logger.debug("addActions: " + depth + ", alpha=" + alpha + ", beta=" + beta);
        Game game = node.getGame();
        int val;
        if (Thread.interrupted()) {
            Thread.currentThread().interrupt();
            val = GameStateEvaluator2.evaluate(playerId, game);
            logger.info("interrupted - " + val);
            return val;
        }
        if (depth <= 0 || SimulationNode2.nodeCount > maxNodes || game.isGameOver()) {
            logger.debug("simulating -- reached end state, node count=" + SimulationNode2.nodeCount + ", depth=" + depth);
            val = GameStateEvaluator2.evaluate(playerId, game);
            UUID currentPlayerId = node.getGame().getPlayerList().get();
            //logger.info("reached - " + val + ", playerId=" + playerId + ", node.pid="+currentPlayerId);
            return val;
        }
        else if (node.getChildren().size() > 0) {
            logger.debug("simulating -- somthing added children:" + node.getChildren().size());
            val = minimaxAB(node, depth-1, alpha, beta);
            return val;
        }
        else {
            logger.debug("simulating -- alpha: " + alpha + " beta: " + beta + " depth:" + depth + " step:" + game.getTurn().getStepType() + " for player:" + (node.getPlayerId().equals(playerId) ? "yes" : "no"));
            if (allPassed(game)) {
                if (!game.getStack().isEmpty()) {
                    resolve(node, depth, game);
                }
                else {
                    game.getPlayers().resetPassed();
                    playNext(game, game.getActivePlayerId(), node);
                }
            }

            if (game.isGameOver()) {
                val = GameStateEvaluator2.evaluate(playerId, game);
            } else if (node.getChildren().size() > 0) {
                //declared attackers or blockers or triggered abilities
                logger.debug("simulating -- attack/block/trigger added children:" + node.getChildren().size());
                val = minimaxAB(node, depth-1, alpha, beta);
            }
            else {
                val = simulatePriority(node, game, depth, alpha, beta);
            }
        }

        logger.debug("returning -- score: " + val + " depth:" + depth + " step:" + game.getTurn().getStepType() + " for player:" + game.getPlayer(node.getPlayerId()).getName());
        return val;

    }

    protected int simulatePriority(SimulationNode2 node, Game game, int depth, int alpha, int beta) {
        if (Thread.interrupted()) {
            Thread.currentThread().interrupt();
            logger.info("interrupted");
            return GameStateEvaluator2.evaluate(playerId, game);
        }
        node.setGameValue(game.getState().getValue(true).hashCode());
        SimulatedPlayer2 currentPlayer = (SimulatedPlayer2) game.getPlayer(game.getPlayerList().get());
        //logger.info("simulating -- player " + currentPlayer.getName());
        SimulationNode2 bestNode = null;
        List<Ability> allActions = currentPlayer.simulatePriority(game);
        optimize(game, allActions);
        logger.debug("simulating -- adding " + allActions.size() + " children:" + allActions);
        for (Ability action: allActions) {
            if (Thread.interrupted()) {
                Thread.currentThread().interrupt();
                logger.debug("interrupted");
                break;
            }
            Game sim = game.copy();
            if (sim.getPlayer(currentPlayer.getId()).activateAbility((ActivatedAbility) action.copy(), sim)) {
                sim.applyEffects();
                if (checkForRepeatedAction(sim, node, action, currentPlayer.getId()))
                    continue;
                if (!sim.isGameOver() && action.isUsesStack()) {
                    // only pass if the last action uses the stack
                    sim.getPlayer(currentPlayer.getId()).pass();
                    sim.getPlayerList().getNext();
                }
                SimulationNode2 newNode = new SimulationNode2(node, sim, action, depth, currentPlayer.getId());
                int testVal = GameStateEvaluator2.evaluate(currentPlayer.getId(), sim);
                logger.debug("simulating -- node #:" + SimulationNode2.getCount() + " actions:" + action);
                sim.checkStateAndTriggered();
                int val = addActions(newNode, depth-1, alpha, beta);
                logger.debug("val = " + val);
                if (!currentPlayer.getId().equals(playerId)) {
                    if (val < beta) {
                        beta = val;
                        bestNode = newNode;
                        bestNode.setScore(val);
                        if (newNode.getChildren().size() > 0) {
                            bestNode.setCombat(newNode.getChildren().get(0).getCombat());
                        }
                    }

                    // no need to check other actions
                    if (val == GameStateEvaluator2.LOSE_GAME_SCORE) {
                        logger.debug("lose - break");
                        break;
                    }
                }
                else {
                    if (val > alpha) {
                        alpha = val;
                        bestNode = newNode;
                        bestNode.setScore(val);
                        if (newNode.getChildren().size() > 0) {
                            bestNode.setCombat(newNode.getChildren().get(0).getCombat());
                        }
                        /*if (node.getTargets().size() > 0)
                            targets = node.getTargets();
                        if (node.getChoices().size() > 0)
                            choices = node.getChoices();
                        */
                        if (depth == maxDepth) {
                            logger.info("saved");
                            node.children.clear();
                            node.children.add(bestNode);
                            node.setScore(bestNode.getScore());
                        }
                    }

                    // no need to check other actions
                    if (val == GameStateEvaluator2.WIN_GAME_SCORE) {
                        logger.debug("win - break");
                        break;
                    }
                }
                if (alpha >= beta) {
                    //logger.info("simulating -- pruning");
                    break;
                }
                if (SimulationNode2.nodeCount > maxNodes) {
                    logger.debug("simulating -- reached end-state");
                    break;
                }
            }
        }
        if (bestNode != null) {
            node.children.clear();
            node.children.add(bestNode);
            node.setScore(bestNode.getScore());
        }
        if (!currentPlayer.getId().equals(playerId)) {
            /*if (beta == Integer.MAX_VALUE) {
                int val = GameStateEvaluator2.evaluate(playerId, game);
                logger.info("returning priority beta: " + val);
                return val;
            }*/
            //logger.info("returning priority beta: " + beta);
            return beta;
        }
        else {
            /*if (alpha == Integer.MIN_VALUE) {
                int val = GameStateEvaluator2.evaluate(playerId, game);
                logger.info("returning priority beta: " + val);
                return val;
            }*/
            //logger.info("returning priority alpha: " + alpha);
            return alpha;
        }
    }

    /**
     * Various AI optimizations for actions.
     *
     * @param allActions
     */
    protected void optimize(Game game, List<Ability> allActions) {
        for (TreeOptimizer optimizer : optimizers) {
            optimizer.optimize(game, allActions);
        }
        Collections.sort(allActions, new Comparator<Ability>() {
            @Override
            public int compare(Ability ability, Ability ability1) {
                String rule = ability.toString();
                String rule1 = ability1.toString();
                if (rule.equals("Pass")) {
                    return 1;
                }
                if (rule1.equals("Pass")) {
                    return -1;
                }
                if (rule.startsWith("Play")) {
                    return -1;
                }
                if (rule1.startsWith("Play")) {
                    return 1;
                }
                if (rule.startsWith("Cast")) {
                    return -1;
                }
                if (rule1.startsWith("Cast")) {
                    return 1;
                }
                return ability.getRule().compareTo(ability1.getRule());
            }
        });
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
        if (choices.isEmpty())
            return super.choose(outcome, choice, game);
        if (!choice.isChosen()) {
            for (String achoice: choices) {
                choice.setChoice(achoice);
                if (choice.isChosen()) {
                    choices.clear();
                    return true;
                }
            }
            return false;
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

        @Override
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
    }

    public void playNext(Game game, UUID activePlayerId, SimulationNode2 node) {
        boolean skip = false;
        while (true) {
            Phase currentPhase = game.getPhase();
            if (!skip)
                currentPhase.getStep().endStep(game, activePlayerId);
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
                    if (!game.checkStateAndTriggered() && !game.isGameOver()) {
                        game.getState().setActivePlayerId(game.getState().getPlayerList(game.getActivePlayerId()).getNext());
                        game.getTurn().setPhase(new BeginningPhase());
                        game.getPhase().setStep(new UntapStep());
                    }
            }
            if (!game.getStep().skipStep(game, game.getActivePlayerId())) {
                if (game.getTurn().getStepType() == PhaseStep.DECLARE_ATTACKERS) {
                    declareAttackers(game, activePlayerId, node);
                } else if (game.getTurn().getStepType() == PhaseStep.DECLARE_BLOCKERS) {
                    declareBlockers(game, activePlayerId, node);
                } else {
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

    private void declareBlockers(Game game, UUID activePlayerId) {
        game.fireEvent(new GameEvent(GameEvent.EventType.DECLARE_BLOCKERS_STEP_PRE, null, null, activePlayerId));
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARING_BLOCKERS, activePlayerId, activePlayerId))) {
            List<Permanent> attackers = getAttackers(game);
            if (attackers == null) {
                return;
            }

            List<Permanent> possibleBlockers = super.getAvailableBlockers(game);
            possibleBlockers = filterOutNonblocking(game, attackers, possibleBlockers);
            if (possibleBlockers.isEmpty()) {
                return;
            }

            attackers = filterOutUnblockable(game, attackers, possibleBlockers);
            if (attackers.isEmpty()) {
                return;
            }

            CombatUtil.sortByPower(attackers, false);

            CombatInfo combatInfo = CombatUtil.blockWithGoodTrade(game, attackers, possibleBlockers);
            Player player = game.getPlayer(this.playerId);

            boolean blocked = false;
            for (Map.Entry<Permanent, List<Permanent>> entry : combatInfo.getCombat().entrySet()) {
                UUID attackerId = entry.getKey().getId();
                List<Permanent> blockers = entry.getValue();
                if (blockers != null) {
                    for (Permanent blocker : blockers) {
                        player.declareBlocker(blocker.getId(), attackerId, game);
                        blocked = true;
                    }
                }
            }

            if (blocked) {
                game.getPlayers().resetPassed();
            }
        }
    }

    private List<Permanent> filterOutNonblocking(Game game, List<Permanent> attackers, List<Permanent> blockers) {
        List<Permanent> blockersLeft = new ArrayList<Permanent>();
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
        List<Permanent> attackersLeft = new ArrayList<Permanent>();
        for (Permanent attacker : attackers) {
            if (CombatUtil.canBeBlocked(game, attacker, blockers)) {
                attackersLeft.add(attacker);
            }
        }
        return attackersLeft;
    }

    private List<Permanent> getAttackers(Game game) {
        List<UUID> attackersUUID = game.getCombat().getAttackers();
        if (attackersUUID.isEmpty()) {
            return null;
        }

        List<Permanent> attackers = new ArrayList<Permanent>();
        for (UUID attackerId : attackersUUID) {
            Permanent permanent = game.getPermanent(attackerId);
            attackers.add(permanent);
        }
        return attackers;
    }

    private void declareBlockers(Game game, UUID activePlayerId, SimulationNode2 node) {
        game.fireEvent(new GameEvent(GameEvent.EventType.DECLARE_BLOCKERS_STEP_PRE, null, null, activePlayerId));
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARING_BLOCKERS, activePlayerId, activePlayerId))) {
            /*for (UUID defenderId: game.getCombat().getDefenders()) {
                //check if defender is being attacked
                if (game.getCombat().isAttacked(defenderId, game)) {
                    for (Combat engagement: ((SimulatedPlayer2)game.getPlayer(defenderId)).addBlockers(game)) {
                        Game sim = game.copy();
                        for (CombatGroup group: engagement.getGroups()) {
                            List<UUID> blockers = new ArrayList<UUID>();
                            blockers.addAll(group.getBlockers());
                            for (UUID blockerId: blockers) {
                                group.addBlocker(blockerId, defenderId, sim);
                            }
                            blockers = null;
                        }
                        sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_BLOCKERS, playerId, playerId));
                        SimulationNode2 newNode = new SimulationNode2(node, sim, node.getDepth()-1, defenderId);
                        logger.debug("simulating -- node #:" + SimulationNode2.getCount() + " declare blockers");
                        newNode.setCombat(sim.getCombat());
                        node.children.add(newNode);
                    }
                }
            }*/
        }
    }

    /**
     * Choose attackers based on static information.
     * That means that AI won't look to the future as it was before, but just choose attackers based on current state
     * of the game. This is worse, but at least it is easier to implement and won't lead to the case when AI doesn't
     * do anything - neither attack nor block.
     *
     * @param game
     * @param activePlayerId
     */
    private void declareAttackers(Game game, UUID activePlayerId) {
        game.fireEvent(new GameEvent(GameEvent.EventType.DECLARE_ATTACKERS_STEP_PRE, null, null, activePlayerId));
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARING_ATTACKERS, activePlayerId, activePlayerId))) {

            Player attackingPlayer = game.getPlayer(activePlayerId);
            UUID defenderId = game.getOpponents(playerId).iterator().next();
            Player defender = game.getPlayer(defenderId);

            List<Permanent> attackersList = super.getAvailableAttackers(game);
            if (attackersList.isEmpty()) {
                return;
            }

            List<Permanent> possibleBlockers = defender.getAvailableBlockers(game);

            List<Permanent> killers = CombatUtil.canKillOpponent(game, attackersList, possibleBlockers, defender);
            if (!killers.isEmpty()) {
                for (Permanent attacker : killers) {
                    attackingPlayer.declareAttacker(attacker.getId(), defenderId, game);
                }
                return;
            }

            CombatUtil.handleExalted();

            //TODO: refactor -- extract to method
            List<Permanent> counterAttackList = new ArrayList<Permanent>();
            int counterAttackDamage = 0;
            int defenderForces = 0;
            int defenderForcesForBlock = 0;

            FilterCreatureForCombat filter = new FilterCreatureForCombat();
            for (Permanent possibleAttacker : game.getBattlefield().getAllActivePermanents(filter, defender.getId(), game)) {
                //TODO: it can be improved with next turn emulation
                if (!possibleAttacker.getAbilities().contains(DefenderAbility.getInstance())) {
                    counterAttackList.add(possibleAttacker);
                    if (possibleAttacker.getPower().getValue() > 0) {
                        // TODO: DB and infect
                        counterAttackDamage += possibleAttacker.getPower().getValue();
                        defenderForces++;
                    }
                    if (CombatUtil.canBlock(game, possibleAttacker)) {
                        defenderForcesForBlock++;
                    }
                }
            }

            double oppScore = 1000000;
            if (counterAttackDamage > 0) {
                oppScore = (double) attackingPlayer.getLife() / counterAttackDamage;
            }

            List<Permanent> possibleAttackersList = new ArrayList<Permanent>();
            int possibleAttackersDamage = 0;
            int ourForces = 0;

            for (Permanent possibleAttacker : game.getBattlefield().getAllActivePermanents(filter, playerId, game)) {
                //TODO: it can be improved with next turn emulation
                if (!possibleAttacker.getAbilities().contains(DefenderAbility.getInstance())) {
                    possibleAttackersList.add(possibleAttacker);

                    if (possibleAttacker.getPower().getValue() > 0) {
                        // TODO: DB and infect
                        possibleAttackersDamage += possibleAttacker.getPower().getValue();
                        ourForces++;
                    }
                }
            }

            double ourScore = 1000000;
            if (possibleAttackersDamage > 0) {
                ourScore = (double) defender.getLife() / possibleAttackersDamage;
            }

            int outNumber = ourForces - defenderForces;

            double score = ourScore - oppScore;

            boolean doAttack = false;

            //attackersList
            CombatUtil.sortByPower(attackersList, false);
            int opponentLife = defender.getLife();

            List<Permanent> notBlockedAttackers = new ArrayList<Permanent>();
            for (int i = 0; i < (attackersList.size() - defenderForces); i++) {
                notBlockedAttackers.add(attackersList.get(i));
            }

            int attackRound = 1;
            while (notBlockedAttackers.size() > 0 && opponentLife > 0 && attackRound < 99) {
                int damageThisRound = 0;
                for (Permanent attacker : notBlockedAttackers) {
                    damageThisRound += attacker.getPower().getValue();
                }
                opponentLife -= damageThisRound;
                for (int i = 0; i < defenderForcesForBlock && !notBlockedAttackers.isEmpty(); i++) {
                    notBlockedAttackers.remove(notBlockedAttackers.size() - 1);
                }
                attackRound++;
                if (opponentLife <= 0) {
                    doAttack = true;
                }
            }

            double unblockableDamage = 0;
            double turnsUntilDeathByUnblockable = 0;
            boolean doUnblockableAttack = false;
            for (Permanent attacker : attackersList) {
                boolean isUnblockableCreature = true;
                for (Permanent blocker : possibleBlockers) {
                    if (blocker.canBlock(attacker.getId(), game)) {
                        isUnblockableCreature = false;
                    }
                }
                if (isUnblockableCreature) {
                    unblockableDamage += attacker.getPower().getValue();
                }
            }
            if (unblockableDamage > 0) {
                turnsUntilDeathByUnblockable = defender.getLife() / unblockableDamage;
            }
            if (unblockableDamage > defender.getLife()) {
                doUnblockableAttack = true;
            }

            int aggressionRate = 5;
            //aggressionRate = getAggressionRate(oppScore, ourScore, outNumber, score, doAttack, turnsUntilDeathByUnblockable, doUnblockableAttack, aggressionRate);
            System.out.println("AI aggression = " + String.valueOf(aggressionRate));


            System.out.println("AI attackers size: " + attackersList.size());

            List<Permanent> finalAttackers = new ArrayList<Permanent>();
            for (int i = 0; i < attackersList.size(); i++) {
                Permanent attacker = attackersList.get(i);
                int totalFirstStrikeBlockPower = 0;

                if (!attacker.getAbilities().contains(FirstStrikeAbility.getInstance()) && !attacker.getAbilities().contains(DoubleStrikeAbility.getInstance())) {
                    for (Permanent blockerWithFSorDB : game.getBattlefield().getAllActivePermanents(filter, playerId, game)) {
                        if (blockerWithFSorDB.getAbilities().contains(DoubleStrikeAbility.getInstance())) {
                            totalFirstStrikeBlockPower += 2 * blockerWithFSorDB.getPower().getValue();
                        } else
                        if (blockerWithFSorDB.getAbilities().contains(FirstStrikeAbility.getInstance())) {
                            totalFirstStrikeBlockPower += blockerWithFSorDB.getPower().getValue();
                        }
                    }

                }

                boolean shouldAttack = shouldAttack(game, attackingPlayer.getId(), defenderId, attacker, possibleBlockers, aggressionRate);

                if (aggressionRate == 5 || shouldAttack && (totalFirstStrikeBlockPower < attacker.getToughness().getValue()) ) {
                    finalAttackers.add(attacker);
                }
            }

             System.out.println("AI final attackers size: " + attackersList.size());

            for (Permanent attacker : finalAttackers) {
                attackingPlayer.declareAttacker(attacker.getId(), defenderId, game);
            }
        }
    }

    private boolean shouldAttack(Game game, UUID attackingPlayerId, UUID defenderId, Permanent attacker, List<Permanent> blockers, int aggressionRate) {
        boolean canBeKilledByOne = false;
        boolean canKillAll = true;
        boolean canKillAllDangerous = true;

        boolean isWorthLessThanAllKillers = true;
        boolean canBeBlocked = false;
        int numberOfPossibleBlockers = 0;

        int life = game.getPlayer(defenderId).getLife();
        int poison = game.getPlayer(defenderId).getCounters().getCount(CounterType.POISON);

        if (!isEffectiveAttacker(game, attackingPlayerId, defenderId, attacker, life, poison)) {
            return false;
        }

        for (Permanent defender : blockers) {
            if (defender.canBlock(attacker.getId(), game)) {
                numberOfPossibleBlockers += 1;
                SurviveInfo info = CombatUtil.willItSurvive(game, attackingPlayerId, defenderId, attacker, defender);
                if (info.isAttackerDied()) {
                    boolean canBeReallyKilled = true;
                    for (Ability ability : attacker.getAbilities()) {
                        if (ability instanceof UndyingAbility) {
                            if (attacker.getCounters().getCount(CounterType.P1P1) == 0) {
                                canBeReallyKilled = false;
                            }
                        }
                    }

                    if (canBeReallyKilled) {
                        canBeKilledByOne = true;
                        if (GameStateEvaluator2.evaluateCreature(defender, game) <= GameStateEvaluator2.evaluateCreature(attacker, game)) {
                            isWorthLessThanAllKillers = false;
                        }
                    }
                }
                // see if this attacking creature can destroy this defender, if
                // not record that it can't kill everything
                if (info.isBlockerDied()) {
                    canKillAll = false;
                    if (defender.getAbilities().contains(WitherAbility.getInstance()) || defender.getAbilities().contains(InfectAbility.getInstance())) {
                        canKillAllDangerous = false;
                    }
                }
            }
        }

        if (canKillAll && !CombatUtil.canBlock(game, attacker) && isWorthLessThanAllKillers) {
            System.out.println(attacker.getName()
                    + " = attacking because they can't block, expecting to kill or damage player");
            return true;
        }

        if (numberOfPossibleBlockers >= 1) {
            canBeBlocked = true;
        }

        switch (aggressionRate) {
           case 4:
                if (canKillAll || (canKillAllDangerous && !canBeKilledByOne) || !canBeBlocked) {
                    System.out.println(attacker.getName() + " = attacking expecting to at least trade with something");
                    return true;
                }
            case 3:
                if ((canKillAll && isWorthLessThanAllKillers) || (canKillAllDangerous && !canBeKilledByOne) || !canBeBlocked) {
                    System.out.println(attacker.getName()
                            + " = attacking expecting to kill creature or cause damage, or is unblockable");
                    return true;
                }
            case 2:
                if ((canKillAll && !canBeKilledByOne) || !canBeBlocked) {
                    System.out.println(attacker.getName() + " = attacking expecting to survive or attract group block");
                    return true;
                }
            case 1:
                if (!canBeBlocked) {
                    System.out.println(attacker.getName() + " = attacking expecting not to be blocked");
                    return true;
                }
            default:
                break;
        }

        return false;
    }

    private boolean isEffectiveAttacker(Game game, UUID attackingPlayerId, UUID defenderId, Permanent attacker, int life, int poison) {
        try {
            SurviveInfo info = CombatUtil.getCombatInfo(game, attackingPlayerId, defenderId, attacker);
            if (info.isAttackerDied()) {
                return false;
            }

            if (info.getDefender().getLife() < life) {
                return true;
            }

            if (info.getDefender().getCounters().getCount(CounterType.POISON) > poison && poison < 10) {
                return true;
            }

            if (info.isTriggered()) {
                return true;
            }
        } catch (Exception e) {
            // swallow exception and return false
            logger.error(e);
            return false;
        }

        return false;
    }


    private int getAggressionRate(double oppScore, double ourScore, int outNumber, double score, boolean doAttack, double turnsUntilDeathByUnblockable, boolean doUnblockableAttack, int aggressionRate) {
        if (score > 0 && doAttack) {
            aggressionRate = 5;
        } else if (((ourScore < 2) && score >= 0) || (score > 3)
                || (score > 0 && outNumber > 0)) {
            aggressionRate = 3;
        } else if (score >= 0 || (score + outNumber >= -1)) {
            aggressionRate = 2;
        } else if (score < 0 && oppScore > 1) {
            aggressionRate = 2;
        } else if (doUnblockableAttack || score * -1 < turnsUntilDeathByUnblockable) {
            aggressionRate = 2;
        } else if (score < 0) {
            aggressionRate = 1;
        }
        return aggressionRate;
    }

    private void declareAttackers(Game game, UUID activePlayerId, SimulationNode2 node) {
        game.fireEvent(new GameEvent(GameEvent.EventType.DECLARE_ATTACKERS_STEP_PRE, null, null, activePlayerId));
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARING_ATTACKERS, activePlayerId, activePlayerId))) {
            for (Combat engagement: ((SimulatedPlayer2)game.getPlayer(activePlayerId)).addAttackers(game)) {
                Game sim = game.copy();
                UUID defenderId = game.getOpponents(playerId).iterator().next();
                for (CombatGroup group: engagement.getGroups()) {
                    for (UUID attackerId: group.getAttackers()) {
                        sim.getPlayer(activePlayerId).declareAttacker(attackerId, defenderId, sim);
                    }
                }
                sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_ATTACKERS, playerId, playerId));
                SimulationNode2 newNode = new SimulationNode2(node, sim, node.getDepth()-1, activePlayerId);
                logger.debug("simulating -- node #:" + SimulationNode2.getCount() + " declare attakers");
                newNode.setCombat(sim.getCombat());
                node.children.add(newNode);
            }
        }
    }

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {
        logger.debug("selectAttackers");
        declareAttackers(game, playerId);
        /*if (combat != null) {
            UUID opponentId = game.getCombat().getDefenders().iterator().next();
            String attackers = "";
            for (UUID attackerId: combat.getAttackers()) {
                Permanent attacker = game.getPermanent(attackerId);
                if (attacker != null) {
                    attackers = "[" + attacker.getName() + "]";
                    this.declareAttacker(attackerId, opponentId, game);
                }
            }
            logger.info("declare attackers: " + (attackers.isEmpty() ? "none" : attackers));
        }*/

    }

    @Override
    public void selectBlockers(Game game, UUID defendingPlayerId) {
        logger.debug("selectBlockers");
        if (combat != null && combat.getGroups().size() > 0) {
            List<CombatGroup> groups = game.getCombat().getGroups();
            for (int i = 0; i < groups.size(); i++) {
                if (i < combat.getGroups().size()) {
                    for (UUID blockerId: combat.getGroups().get(i).getBlockers()) {
                        logger.info("select blocker: " + blockerId + " vs " + groups.get(i).getAttackers().get(0));
                        this.declareBlocker(blockerId, groups.get(i).getAttackers().get(0), game);
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

        for (Player copyPlayer: sim.getState().getPlayers().values()) {
            Player origPlayer = game.getState().getPlayers().get(copyPlayer.getId()).copy();
            System.out.println("suggested=" + suggested);
            SimulatedPlayer2 newPlayer = new SimulatedPlayer2(copyPlayer.getId(), copyPlayer.getId().equals(playerId), suggested);
            newPlayer.restore(origPlayer);
            sim.getState().getPlayers().put(copyPlayer.getId(), newPlayer);
        }
        return sim;
    }

    private boolean checkForRepeatedAction(Game sim, SimulationNode2 node, Ability action, UUID playerId) {
        if (action instanceof PassAbility)
            return false;
        int newVal = GameStateEvaluator2.evaluate(playerId, sim);
        SimulationNode2 test = node.getParent();
        while (test != null) {
            if (test.getPlayerId().equals(playerId)) {
                if (test.getAbilities() != null && test.getAbilities().size() == 1) {
                    if (action.toString().equals(test.getAbilities().get(0).toString())) {
                        if (test.getParent() != null) {
                            Game prevGame = node.getGame();
                            if (prevGame != null) {
                                int oldVal = GameStateEvaluator2.evaluate(playerId, prevGame);
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

    protected void getSuggestedActions() {
        try {
            File file = new File(FILE_WITH_INSTRUCTIONS);
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("cast:") || line.startsWith("play:")) {
                        suggested.add(line.substring(5, line.length()));
                    }
                }
                System.out.println("suggested::");
                for (int i = 0; i < suggested.size(); i++) {
                    System.out.println("    " + suggested.get(i));
                }
            }
        } catch (Exception e) {
            // swallow
            e.printStackTrace();
        }
    }

    @Override
    public void addAction(String action) {
        System.out.println("adding to suggested actions: " + action);
        if (action != null && action.startsWith("cast:") || action.startsWith("play:")) {
            suggested.add(action.substring(5, action.length()));
        }
    }
}
