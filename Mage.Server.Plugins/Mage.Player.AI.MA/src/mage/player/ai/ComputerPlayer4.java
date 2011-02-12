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
import java.util.logging.Logger;
import mage.Constants.Outcome;
import mage.Constants.PhaseStep;
import mage.Constants.RangeOfInfluence;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.SearchEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.filter.FilterAbility;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
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
import mage.util.Logging;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ComputerPlayer4 extends ComputerPlayer<ComputerPlayer4> implements Player {

	private static final transient Logger logger = Logging.getLogger(ComputerPlayer4.class.getName());
	private static final ExecutorService pool = Executors.newFixedThreadPool(1);

	protected int maxDepth;
	protected int maxNodes;
	protected LinkedList<Ability> actions = new LinkedList<Ability>();
	protected List<UUID> targets = new ArrayList<UUID>();
	protected List<String> choices = new ArrayList<String>();
	protected Combat combat;
	protected int currentScore;
	protected SimulationNode2 root;

	public ComputerPlayer4(String name, RangeOfInfluence range) {
		super(name, range);
		maxDepth = Config2.maxDepth;
		maxNodes = Config2.maxNodes;
	}

	public ComputerPlayer4(final ComputerPlayer4 player) {
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
	public ComputerPlayer4 copy() {
		return new ComputerPlayer4(this);
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
			case POSTCOMBAT_MAIN:
				if (game.getActivePlayerId().equals(playerId)) {
					Player player = game.getPlayer(playerId);
					System.out.println("Turn::"+game.getTurnNum());
					System.out.println("[" + game.getPlayer(playerId).getName() + "] " + game.getTurn().getStepType().name() +", life=" + player.getLife());
					String s = "[";
					for (Card card : player.getHand().getCards(game)) {
						s += card.getName() + ";";
					}
					s += "]";
					System.out.println("Hand: " + s);
					s = "[";
					for (Permanent permanent : game.getBattlefield().getAllPermanents()) {
						 if (permanent.getOwnerId().equals(player.getId())) {
							 s += permanent.getName() + ";";
						 }
					}
					s += "]";
					System.out.println("Permanents: " + s);
				}
				if (actions.size() == 0) {
					calculateActions(game);
				}
				act(game);
				break;
						case BEGIN_COMBAT:
			case DECLARE_ATTACKERS:
			case DECLARE_BLOCKERS:
			case COMBAT_DAMAGE:
			case END_COMBAT:
			case END_TURN:
				pass();
				break;
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
				System.out.println("[" + game.getPlayer(playerId).getName() + "] Action: " + ability.toString());
				this.activateAbility((ActivatedAbility) ability, game);
				if (ability.isUsesStack())
					usedStack = true;
			}
			if (usedStack)
				pass();
		}
	}

	protected void calculateActions(Game game) {
		currentScore = GameStateEvaluator2.evaluate(playerId, game);
		if (!getNextAction(game)) {
			Game sim = createSimulation(game);
			SimulationNode2.resetCount();
			root = new SimulationNode2(sim, maxDepth, playerId);
			logger.info("simulating actions");
			addActionsTimed(new FilterAbility());
			if (root.children.size() > 0) {
				root = root.children.get(0);
				int bestScore = GameStateEvaluator2.evaluate(playerId, root.getGame());
				if (bestScore > currentScore) {
					actions = new LinkedList<Ability>(root.abilities);
					combat = root.combat;
				}
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
			logger.info("simlating -- game value:" + game.getState().getValue() + " test value:" + test.gameValue);
			if (root.playerId.equals(playerId) && root.abilities != null && game.getState().getValue() == test.gameValue) {
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

	protected int minimaxAB(SimulationNode2 node, FilterAbility filter, int depth, int alpha, int beta) {
		UUID currentPlayerId = node.getGame().getPlayerList().get();
		SimulationNode2 bestChild = null;
		for (SimulationNode2 child: node.getChildren()) {
			if (alpha >= beta) {
				logger.info("alpha beta pruning");
				break;
			}
			if (SimulationNode2.nodeCount > maxNodes) {
				logger.info("simulating -- reached end-state, count=" + SimulationNode2.nodeCount);
				break;
			}
			int val = addActions(child, filter, depth-1, alpha, beta);
			if (!currentPlayerId.equals(playerId)) {
				if (val < beta) {
					beta = val;
					bestChild = child;
					if (node.getCombat() == null)
						node.setCombat(child.getCombat());
				}
			}
			else {
				if (val > alpha) {
					alpha = val;
					bestChild = child;
					if (node.getCombat() == null)
						node.setCombat(child.getCombat());
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
						SimulationNode2 newNode = new SimulationNode2(sim, depth, ability.getControllerId());
						node.children.add(newNode);
						newNode.getTargets().add(targetId);
						logger.fine("simulating search -- node#: " + SimulationNode2.getCount() + "for player: " + sim.getPlayer(ability.getControllerId()).getName());
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

	protected void addActionsTimed(final FilterAbility filter) {
		FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
			public Integer call() throws Exception
			{
				return addActions(root, filter, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
			}
		});
		pool.execute(task);
		try {
			task.get(Config2.maxThinkSeconds, TimeUnit.MINUTES);
		} catch (TimeoutException e) {
			logger.info("simulating - timed out");
			task.cancel(true);
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected int addActions(SimulationNode2 node, FilterAbility filter, int depth, int alpha, int beta) {
		logger.fine("addActions: " + depth + ", alpha=" + alpha + ", beta=" + beta);
		Game game = node.getGame();
		int val;
		if (Thread.interrupted()) {
			Thread.currentThread().interrupt();
			logger.info("interrupted");
			return GameStateEvaluator2.evaluate(playerId, game);
		}
		if (depth <= 0 || SimulationNode2.nodeCount > maxNodes || game.isGameOver()) {
			logger.fine("simulating -- reached end state, node count="+ SimulationNode2.nodeCount + ", depth="+depth);
			val = GameStateEvaluator2.evaluate(playerId, game);
		}
		else if (node.getChildren().size() > 0) {
			logger.fine("simulating -- somthing added children:" + node.getChildren().size());
			val = minimaxAB(node, filter, depth-1, alpha, beta);
		}
		else {
			if (logger.isLoggable(Level.FINE))
				logger.fine("simulating -- alpha: " + alpha + " beta: " + beta + " depth:" + depth + " step:" + game.getTurn().getStepType() + " for player:" + (node.getPlayerId().equals(playerId)?"yes":"no"));
			if (allPassed(game)) {
				if (!game.getStack().isEmpty()) {
					resolve(node, depth, game);
				}
				else {
//					int testScore = GameStateEvaluator.evaluate(playerId, game);
//					if (testScore < currentScore) {
//						// if score at end of step is worse than original score don't check any further
//						logger.info("simulating -- abandoning current check, no immediate benefit");
//						return testScore;
//					}
					game.getPlayers().resetPassed();
					playNext(game, game.getActivePlayerId(), node);
				}
			}

			if (game.isGameOver()) {
				val = GameStateEvaluator2.evaluate(playerId, game);
			}
			else if (node.getChildren().size() > 0) {
				//declared attackers or blockers or triggered abilities
				logger.fine("simulating -- attack/block/trigger added children:" + node.getChildren().size());
				val = minimaxAB(node, filter, depth-1, alpha, beta);
			}
			else {
				val = simulatePriority(node, game, filter, depth, alpha, beta);
			}
		}

		if (logger.isLoggable(Level.FINE))
			logger.fine("returning -- score: " + val + " depth:" + depth + " step:" + game.getTurn().getStepType() + " for player:" + game.getPlayer(node.getPlayerId()).getName());
		return val;

	}

	protected int simulatePriority(SimulationNode2 node, Game game, FilterAbility filter, int depth, int alpha, int beta) {
		if (Thread.interrupted()) {
			Thread.currentThread().interrupt();
			logger.info("interrupted");
			return GameStateEvaluator2.evaluate(playerId, game);
		}
		node.setGameValue(game.getState().getValue());
		SimulatedPlayer2 currentPlayer = (SimulatedPlayer2) game.getPlayer(game.getPlayerList().get());
		//logger.info("simulating -- player " + currentPlayer.getName());
		SimulationNode2 bestNode = null;
		List<Ability> allActions = currentPlayer.simulatePriority(game, filter);
		if (logger.isLoggable(Level.FINE))
			logger.fine("simulating -- adding " + allActions.size() + " children:" + allActions);
		for (Ability action: allActions) {
			Game sim = game.copy();
			if (sim.getPlayer(currentPlayer.getId()).activateAbility((ActivatedAbility) action.copy(), sim)) {
				sim.applyEffects();
				if (!sim.isGameOver() && action.isUsesStack()) {
					// only pass if the last action uses the stack
					sim.getPlayer(currentPlayer.getId()).pass();
					sim.getPlayerList().getNext();
				}
				SimulationNode2 newNode = new SimulationNode2(sim, action, depth, currentPlayer.getId());
				if (logger.isLoggable(Level.FINE))
					logger.fine("simulating -- node #:" + SimulationNode2.getCount() + " actions:" + action);
				sim.checkStateAndTriggered();
				int val = addActions(newNode, filter, depth-1, alpha, beta);
				if (!currentPlayer.getId().equals(playerId)) {
					if (val < beta) {
						beta = val;
						bestNode = newNode;
						node.setCombat(newNode.getCombat());
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
				}
				if (alpha >= beta) {
					//logger.info("simulating -- pruning");
					break;
				}
				if (SimulationNode2.nodeCount > maxNodes) {
					logger.fine("simulating -- reached end-state");
					break;
				}
			}
		}
		if (bestNode != null) {
			node.children.clear();
			node.children.add(bestNode);
		}
		if (!currentPlayer.getId().equals(playerId)) {
			//logger.info("returning priority beta: " + beta);
			return beta;
		}
		else {
			//logger.info("returning priority alpha: " + alpha);
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
						for (Combat engagement: ((SimulatedPlayer2)game.getPlayer(activePlayerId)).addAttackers(game)) {
							Game sim = game.copy();
							UUID defenderId = game.getOpponents(playerId).iterator().next();
							for (CombatGroup group: engagement.getGroups()) {
								for (UUID attackerId: group.getAttackers()) {
									sim.getPlayer(activePlayerId).declareAttacker(attackerId, defenderId, sim);
								}
							}
							sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_ATTACKERS, playerId, playerId));
							SimulationNode2 newNode = new SimulationNode2(sim, node.getDepth()-1, activePlayerId);
							logger.info("simulating -- node #:" + SimulationNode2.getCount() + " declare attakers");
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
								for (Combat engagement: ((SimulatedPlayer2)game.getPlayer(defenderId)).addBlockers(game)) {
									Game sim = game.copy();
									for (CombatGroup group: engagement.getGroups()) {
										for (UUID blockerId: group.getBlockers()) {
											group.addBlocker(blockerId, defenderId, sim);
										}
									}
									sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_BLOCKERS, playerId, playerId));
									SimulationNode2 newNode = new SimulationNode2(sim, node.getDepth()-1, defenderId);
									logger.info("simulating -- node #:" + SimulationNode2.getCount() + " declare blockers");
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
		logger.info("selectAttackers");
		if (combat != null) {
			UUID opponentId = game.getCombat().getDefenders().iterator().next();
			for (UUID attackerId: combat.getAttackers()) {
				logger.info("declare attacker: " + game.getCard(attackerId).getName());
				this.declareAttacker(attackerId, opponentId, game);
			}
		}
	}

	@Override
	public void selectBlockers(Game game) {
		logger.info("selectBlockers");
		if (combat != null && combat.getGroups().size() > 0) {
			List<CombatGroup> groups = game.getCombat().getGroups();
			for (int i = 0; i < groups.size(); i++) {
				if (i < combat.getGroups().size()) {
					for (UUID blockerId: combat.getGroups().get(i).getBlockers()) {
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
			Player origPlayer = game.getState().getPlayers().get(copyPlayer.getId());
			SimulatedPlayer2 newPlayer = new SimulatedPlayer2(copyPlayer.getId(), copyPlayer.getId().equals(playerId));
			newPlayer.restore(origPlayer);
			sim.getState().getPlayers().put(copyPlayer.getId(), newPlayer);
		}
		return sim;
	}

}
