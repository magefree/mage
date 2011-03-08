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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import mage.Constants.Outcome;
import mage.Constants.PhaseStep;
import mage.Constants.RangeOfInfluence;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.PassAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.SearchEffect;
import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.filter.FilterAbility;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.game.turn.BeginCombatStep;
import mage.game.turn.BeginningPhase;
import mage.game.turn.CleanupStep;
import mage.game.turn.CombatDamageStep;
import mage.game.turn.CombatPhase;
import mage.game.turn.DeclareAttackersStep;
import mage.game.turn.DeclareBlockersStep;
import mage.game.turn.DrawStep;
import mage.game.turn.EndOfCombatStep;
import mage.game.turn.EndPhase;
import mage.game.turn.EndStep;
import mage.game.turn.Phase;
import mage.game.turn.PostCombatMainPhase;
import mage.game.turn.PostCombatMainStep;
import mage.game.turn.PreCombatMainPhase;
import mage.game.turn.PreCombatMainStep;
import mage.game.turn.UntapStep;
import mage.game.turn.UpkeepStep;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ComputerPlayer2 extends ComputerPlayer<ComputerPlayer2> implements Player {

	private static final transient Logger logger = Logger.getLogger(ComputerPlayer2.class);
	private static final ExecutorService pool = Executors.newFixedThreadPool(1);

	protected int maxDepth;
	protected int maxNodes;
	protected LinkedList<Ability> actions = new LinkedList<Ability>();
	protected List<UUID> targets = new ArrayList<UUID>();
	protected List<String> choices = new ArrayList<String>();
	protected Combat combat;
	protected int currentScore;
	protected SimulationNode root;

	public ComputerPlayer2(String name, RangeOfInfluence range) {
		super(name, range);
		maxDepth = Config.maxDepth;
		maxNodes = Config.maxNodes;
	}

	public ComputerPlayer2(final ComputerPlayer2 player) {
		super(player);
		this.maxDepth = player.maxDepth;
		this.currentScore = player.currentScore;
		if (player.combat != null)
			this.combat = player.combat.copy();
		for (Ability ability: player.actions) {
			actions.add(ability);
		}
		for (UUID targetId: player.targets) {
			targets.add(targetId);
		}
		for (String choice: player.choices) {
			choices.add(choice);
		}
	}

	@Override
	public ComputerPlayer2 copy() {
		return new ComputerPlayer2(this);
	}

	@Override
	public void priority(Game game) {
		logState(game);
		game.firePriorityEvent(playerId);
		switch (game.getTurn().getStepType()) {
			case UPKEEP:
			case DRAW:
				pass();
				break;
			case PRECOMBAT_MAIN:
			case BEGIN_COMBAT:
			case DECLARE_ATTACKERS:
			case DECLARE_BLOCKERS:
			case COMBAT_DAMAGE:
			case END_COMBAT:
			case POSTCOMBAT_MAIN:
				if (actions.size() == 0) {
					calculateActions(game);
				}
				act(game);
				break;
			case END_TURN:
			case CLEANUP:
				pass();
				break;
		}
	}

	protected void act(Game game) {
		if (actions == null || actions.size() == 0)
			pass();
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
				pass();
		}
	}

	protected void calculateActions(Game game) {
		currentScore = GameStateEvaluator.evaluate(playerId, game);
		if (!getNextAction(game)) {
			Game sim = createSimulation(game);
			SimulationNode.resetCount();
			root = new SimulationNode(null, sim, playerId);
			logger.debug("simulating actions");
			addActionsTimed(new FilterAbility());
			if (root.children.size() > 0) {
				root = root.children.get(0);
				actions = new LinkedList<Ability>(root.abilities);
				combat = root.combat;
				if (logger.isDebugEnabled())
					logger.debug("adding actions:" + actions);
			}
			else
				logger.debug("no actions added");
		}
	}

	protected boolean getNextAction(Game game) {
		if (root != null && root.children.size() > 0) {
			SimulationNode test = root;
			root = root.children.get(0);
			while (root.children.size() > 0 && !root.playerId.equals(playerId)) {
				test = root;
				root = root.children.get(0);
			}
			logger.debug("simlating -- game value:" + game.getState().getValue() + " test value:" + test.gameValue);
			if (root.playerId.equals(playerId) && root.abilities != null && game.getState().getValue() == test.gameValue) {
				logger.debug("simulating -- continuing previous action chain");
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

	protected int minimaxAB(SimulationNode node, FilterAbility filter, int alpha, int beta) {
		UUID currentPlayerId = node.getGame().getPlayerList().get();
		SimulationNode bestChild = null;
		boolean isSimulatedPlayer = currentPlayerId.equals(playerId);
		for (SimulationNode child: node.getChildren()) {
			if (alpha >= beta) {
				logger.debug(indent(node.depth) + "alpha beta pruning");
				break;
			}
//			if (SimulationNode.nodeCount > maxNodes) {
//				logger.debug(indent(node.depth) + "simulating -- reached end-state");
//				break;
//			}
			int val = addActions(child, filter, alpha, beta);
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
					for (UUID targetId: target.possibleTargets(ability.getSourceId(), ability.getControllerId(), game)) {
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

	protected void addActionsTimed(final FilterAbility filter) {
		FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
			public Integer call() throws Exception
			{
				return addActions(root, filter, Integer.MIN_VALUE, Integer.MAX_VALUE);
			}
		});
		pool.execute(task);
		try {
			task.get(Config.maxThinkSeconds, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			logger.debug("simulating - timed out");
			task.cancel(true);
			// sleep for 1 second to allow cleanup to finish
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				logger.fatal("can't sleep");
			}
		} catch (ExecutionException e) {
			logger.fatal("Simulation error", e);
			task.cancel(true);
		} catch (InterruptedException e) {
			logger.fatal("Simulation interrupted", e);
			task.cancel(true);
		}
	}

	protected int addActions(SimulationNode node, FilterAbility filter, int alpha, int beta) {
		Game game = node.getGame();
		if (Thread.interrupted()) {
			Thread.currentThread().interrupt();
			logger.debug(indent(node.depth) + "interrupted");
			return GameStateEvaluator.evaluate(playerId, game);
		}
		int val;
		if (node.depth > maxDepth || game.isGameOver()) {
			logger.debug(indent(node.depth) + "simulating -- reached end state");
			val = GameStateEvaluator.evaluate(playerId, game);
		}
		else if (node.getChildren().size() > 0) {
			logger.debug(indent(node.depth) + "simulating -- somthing added children:" + node.getChildren().size());
			val = minimaxAB(node, filter, alpha, beta);
		}
		else {
			if (logger.isDebugEnabled())
				logger.debug(indent(node.depth) + "simulating -- alpha: " + alpha + " beta: " + beta + " depth:" + node.depth + " step:" + game.getTurn().getStepType() + " for player:" + (node.getPlayerId().equals(playerId)?"yes":"no"));
			if (allPassed(game)) {
				if (!game.getStack().isEmpty()) {
					resolve(node, game);
				}
				else {
//					int testScore = GameStateEvaluator.evaluate(playerId, game);
//					if (testScore < currentScore) {
//						// if score at end of step is worse than original score don't check any further
//						logger.debug("simulating -- abandoning current check, no immediate benefit");
//						return testScore;
//					}
					game.getPlayers().resetPassed();
					playNext(game, game.getActivePlayerId(), node);
				}
			}

			if (game.isGameOver()) {
				val = GameStateEvaluator.evaluate(playerId, game);
			}
			else if (node.getChildren().size() > 0) {
				//declared attackers or blockers or triggered abilities
				logger.debug(indent(node.depth) + "simulating -- attack/block/trigger added children:" + node.getChildren().size());
				val = minimaxAB(node, filter, alpha, beta);
			}
			else {
				val = simulatePriority(node, game, filter, alpha, beta);
			}
		}

		if (logger.isDebugEnabled())
			logger.debug(indent(node.depth) + "returning -- score: " + val + " depth:" + node.depth + " step:" + game.getTurn().getStepType() + " for player:" + game.getPlayer(node.getPlayerId()).getName());
		return val;

	}

	protected int simulatePriority(SimulationNode node, Game game, FilterAbility filter, int alpha, int beta) {
		if (Thread.interrupted()) {
			Thread.currentThread().interrupt();
			logger.debug(indent(node.depth) + "interrupted");
			return GameStateEvaluator.evaluate(playerId, game);
		}
		node.setGameValue(game.getState().getValue());
		SimulatedPlayer currentPlayer = (SimulatedPlayer) game.getPlayer(game.getPlayerList().get());
		boolean isSimulatedPlayer = currentPlayer.getId().equals(playerId);
		logger.debug(indent(node.depth) + "simulating -- player " + currentPlayer.getName());
		SimulationNode bestNode = null;
		List<Ability> allActions = currentPlayer.simulatePriority(game, filter);
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
				if (!sim.isGameOver() && action.isUsesStack()) {
					// only pass if the last action uses the stack
					sim.getPlayer(currentPlayer.getId()).pass();
					sim.getPlayerList().getNext();
				}
				SimulationNode newNode = new SimulationNode(node, sim, action, currentPlayer.getId());
				if (logger.isDebugEnabled())
					logger.debug(indent(node.depth) + "simulating -- node #:" + SimulationNode.getCount() + " actions:" + action);
				sim.checkStateAndTriggered();
				int val = addActions(newNode, filter, alpha, beta);
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
						if (node.getTargets().size() > 0)
							targets = node.getTargets();
						if (node.getChoices().size() > 0)
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
//				if (SimulationNode.nodeCount > maxNodes) {
//					logger.debug(indent(node.depth) + "simulating -- reached end-state");
//					break;
//				}
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
		if (choices.size() == 0)
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
	public boolean chooseTarget(Cards cards, TargetCard target, Ability source, Game game)  {
		if (targets.size() == 0)
			return super.chooseTarget(cards, target, source, game);
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
	public boolean choose(Cards cards, TargetCard target, Game game)  {
		if (targets.size() == 0)
			return super.choose(cards, target, game);
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
		cost.setPaid();
		return true;
	}

	public void playNext(Game game, UUID activePlayerId, SimulationNode node) {
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
					game.getPhase().setStep(new CombatDamageStep(true));
					break;
				case COMBAT_DAMAGE:
					if (((CombatDamageStep)currentPhase.getStep()).getFirst())
						game.getPhase().setStep(new CombatDamageStep(false));
					else
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
					game.fireEvent(new GameEvent(GameEvent.EventType.DECLARE_ATTACKERS_STEP_PRE, null, null, activePlayerId));
					if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARING_ATTACKERS, activePlayerId, activePlayerId))) {
						for (Combat engagement: ((SimulatedPlayer)game.getPlayer(activePlayerId)).addAttackers(game)) {
							Game sim = game.copy();
							UUID defenderId = game.getOpponents(playerId).iterator().next();
							for (CombatGroup group: engagement.getGroups()) {
								for (UUID attackerId: group.getAttackers()) {
									sim.getPlayer(activePlayerId).declareAttacker(attackerId, defenderId, sim);
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
	public void selectAttackers(Game game) {
		if (logger.isDebugEnabled() && (combat == null || combat.getGroups().isEmpty()))
			logger.debug("not attacking");
		if (combat != null) {
			UUID opponentId = game.getCombat().getDefenders().iterator().next();
			for (UUID attackerId: combat.getAttackers()) {
				this.declareAttacker(attackerId, opponentId, game);
				if (logger.isDebugEnabled())
					logger.debug("attacking with:" + game.getPermanent(attackerId).getName());
 			}
		}
	}

	@Override
	public void selectBlockers(Game game) {
		logger.debug("selectBlockers");
		if (combat != null && combat.getGroups().size() > 0) {
			List<CombatGroup> groups = game.getCombat().getGroups();
			for (int i = 0; i < groups.size(); i++) {
				if (i < combat.getGroups().size()) {
					for (UUID blockerId: combat.getGroups().get(i).getBlockers()) {
						this.declareBlocker(blockerId, groups.get(i).getAttackers().get(0), game);
						if (logger.isDebugEnabled())
							logger.debug("blocking with:" + game.getPermanent(blockerId).getName());
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
			Player origPlayer = game.getState().getPlayers().get(copyPlayer.getId());
			SimulatedPlayer newPlayer = new SimulatedPlayer(copyPlayer.getId(), copyPlayer.getId().equals(playerId));
			newPlayer.restore(origPlayer);
			sim.getState().getPlayers().put(copyPlayer.getId(), newPlayer);
		}
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
