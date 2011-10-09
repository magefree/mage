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

import mage.Constants.AbilityType;
import mage.Constants.PhaseStep;
import mage.Constants.RangeOfInfluence;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.filter.FilterAbility;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.turn.*;
import mage.players.Player;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.UUID;

/**
 *
 * @author ayratn
 */
public class ComputerPlayer7 extends ComputerPlayer6 implements Player {

	private static final transient Logger logger = Logger.getLogger(ComputerPlayer7.class);

	private static FilterAbility filterLand = new FilterAbility();
	private static FilterAbility filterNotLand = new FilterAbility();

	static {
		filterLand.getTypes().add(AbilityType.PLAY_LAND);
		filterLand.setZone(Zone.HAND);

		filterNotLand.getTypes().add(AbilityType.PLAY_LAND);
		filterNotLand.setZone(Zone.HAND);
		filterNotLand.setNotFilter(true);

	}

	public ComputerPlayer7(String name, RangeOfInfluence range, int skill) {
		super(name, range, skill);
	}

	public ComputerPlayer7(final ComputerPlayer7 player) {
		super(player);
	}

	@Override
	public ComputerPlayer7 copy() {
		return new ComputerPlayer7(this);
	}

	@Override
	public void priority(Game game) {
		logState(game);
		if (logger.isDebugEnabled())
			logger.debug("Game State: Turn-" + game.getTurnNum() + " Step-" + game.getTurn().getStepType() + " ActivePlayer-" + game.getPlayer(game.getActivePlayerId()).getName() + " PriorityPlayer-" + name);
		game.firePriorityEvent(playerId);
		switch (game.getTurn().getStepType()) {
			case UPKEEP:
			case DRAW:
				pass();
				break;
			case PRECOMBAT_MAIN:
				if (game.getActivePlayerId().equals(playerId)) {
					System.out.println("Computer7:");
					printOutState(game, playerId);
					printOutState(game, game.getOpponents(playerId).iterator().next());
					if (actions.size() == 0) {
						calculatePreCombatActions(game);
					}
					act(game);
				}
				else
					pass();
				break;
			case BEGIN_COMBAT:
				pass();
				break;
			case DECLARE_ATTACKERS:
				if (!game.getActivePlayerId().equals(playerId)) {
					printOutState(game, playerId);
					printOutState(game, game.getOpponents(playerId).iterator().next());
					if (actions.size() == 0) {
						calculatePreCombatActions(game);
					}
					act(game);
				}
				else
					pass();
				break;
			case DECLARE_BLOCKERS:
            case FIRST_COMBAT_DAMAGE:
			case COMBAT_DAMAGE:
			case END_COMBAT:
				pass();
				break;
			case POSTCOMBAT_MAIN:
				if (game.getActivePlayerId().equals(playerId)) {
					printOutState(game, playerId);
					printOutState(game, game.getOpponents(playerId).iterator().next());
					if (actions.size() == 0) {
						calculatePostCombatActions(game);
					}
					act(game);
				}
				else
					pass();
				break;
			case END_TURN:
			case CLEANUP:
				pass();
				break;
		}
	}

	protected void calculatePreCombatActions(Game game) {
		if (!getNextAction(game)) {
			currentScore = GameStateEvaluator2.evaluate(playerId, game);
			Game sim = createSimulation(game);
			SimulationNode2.resetCount();
			root = new SimulationNode2(null, sim, maxDepth, playerId);
			logger.debug("simulating pre combat actions -----------------------------------------------------------------------------------------");

			addActionsTimed(new FilterAbility());
			if (root.children.size() > 0) {
				root = root.children.get(0);
				int bestScore = root.getScore();
				if (bestScore > currentScore) {
					actions = new LinkedList<Ability>(root.abilities);
					combat = root.combat;
				}
			}
		}
	}

	protected void calculatePostCombatActions(Game game) {
		if (!getNextAction(game)) {
			currentScore = GameStateEvaluator2.evaluate(playerId, game);
			Game sim = createSimulation(game);
			SimulationNode2.resetCount();
			root = new SimulationNode2(null, sim, maxDepth, playerId);
			logger.debug("simulating post combat actions ----------------------------------------------------------------------------------------");

			addActionsTimed(new FilterAbility());
			if (root.children.size() > 0) {
				root = root.children.get(0);
				int bestScore = root.getScore();
				if (bestScore > currentScore) {
					actions = new LinkedList<Ability>(root.abilities);
					combat = root.combat;
				}
			}
		}
	}

	@Override
	protected int addActions(SimulationNode2 node, FilterAbility filter, int depth, int alpha, int beta) {
		boolean stepFinished = false;
		int val;
		Game game = node.getGame();
		if (Thread.interrupted()) {
			Thread.currentThread().interrupt();
			logger.debug("interrupted");
			return GameStateEvaluator2.evaluate(playerId, game);
		}
		if (depth <= 0 || SimulationNode2.nodeCount > maxNodes || game.isGameOver()) {
			logger.debug("simulating -- reached end state");
			val = GameStateEvaluator2.evaluate(playerId, game);
		}
		else if (node.getChildren().size() > 0) {
			logger.debug("simulating -- somthing added children:" + node.getChildren().size());
			val = minimaxAB(node, filter, depth-1, alpha, beta);
		}
		else {
			if (logger.isDebugEnabled())
				logger.debug("simulating -- alpha: " + alpha + " beta: " + beta + " depth:" + depth + " step:" + game.getTurn().getStepType() + " for player:" + game.getPlayer(game.getPlayerList().get()).getName());
			if (allPassed(game)) {
				if (!game.getStack().isEmpty()) {
					resolve(node, depth, game);
				}
				else {
					stepFinished = true;
				}
			}

			if (game.isGameOver()) {
				val = GameStateEvaluator2.evaluate(playerId, game);
			}
			else if (stepFinished) {
				logger.debug("step finished");
				int testScore = GameStateEvaluator2.evaluate(playerId, game);
				if (game.getActivePlayerId().equals(playerId)) {
					if (testScore < currentScore) {
						// if score at end of step is worse than original score don't check further
						logger.debug("simulating -- abandoning check, no immediate benefit");
						val = testScore;
					}
					else {
						switch (game.getTurn().getStepType()) {
							case PRECOMBAT_MAIN:
								val = simulateCombat(game, node, depth-1, alpha, beta, false);
								break;
							case POSTCOMBAT_MAIN:
								val = simulateCounterAttack(game, node, depth-1, alpha, beta);
								break;
							default:
								val = GameStateEvaluator2.evaluate(playerId, game);
								break;
						}
					}
				}
				else {
					if (game.getTurn().getStepType() == PhaseStep.DECLARE_ATTACKERS)
						val = simulateBlockers(game, node, playerId, depth-1, alpha, beta, true);
					else
						val = GameStateEvaluator2.evaluate(playerId, game);
				}
			}
			else if (node.getChildren().size() > 0) {
				logger.debug("simulating -- trigger added children:" + node.getChildren().size());
				val = minimaxAB(node, filter, depth, alpha, beta);
			}
			else {
				val = simulatePriority(node, game, filter, depth, alpha, beta);
			}
		}

		if (logger.isDebugEnabled())
			logger.debug("returning -- score: " + val + " depth:" + depth + " step:" + game.getTurn().getStepType() + " for player:" + game.getPlayer(node.getPlayerId()).getName());
		return val;

	}

	protected int simulateCombat(Game game, SimulationNode2 node, int depth, int alpha, int beta, boolean counter) {
		Integer val = null;
		if (Thread.interrupted()) {
			Thread.currentThread().interrupt();
			logger.debug("interrupted");
			return GameStateEvaluator2.evaluate(playerId, game);
		}
		if (game.getTurn().getStepType() != PhaseStep.DECLARE_BLOCKERS) {
			game.getTurn().setPhase(new CombatPhase());
			if (game.getPhase().beginPhase(game, game.getActivePlayerId())) {
				simulateStep(game, new BeginCombatStep());
				game.getPhase().setStep(new DeclareAttackersStep());
				if (!game.getStep().skipStep(game, game.getActivePlayerId())) {
					game.fireEvent(new GameEvent(GameEvent.EventType.DECLARE_ATTACKERS_STEP_PRE, null, null, game.getActivePlayerId()));
					if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARING_ATTACKERS, game.getActivePlayerId(), game.getActivePlayerId()))) {
						val = simulateAttackers(game, node, game.getActivePlayerId(), depth, alpha, beta, counter);
					}
				}
				else if (!counter) {
					simulateToEnd(game);
					val = simulatePostCombatMain(game, node, depth, alpha, beta);
				}
			}
		}
		else {
			if (!game.getStep().skipStep(game, game.getActivePlayerId())) {
				game.fireEvent(new GameEvent(GameEvent.EventType.DECLARE_BLOCKERS_STEP_PRE, null, null, game.getActivePlayerId()));
				if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARING_BLOCKERS, game.getActivePlayerId(), game.getActivePlayerId()))) {
					//only suitable for two player games - only simulates blocks for 1st defender
					val = simulateBlockers(game, node, game.getCombat().getDefenders().iterator().next(), depth, alpha, beta, counter);
				}
			}
			else if (!counter) {
				finishCombat(game);
				val = simulateCounterAttack(game, node, depth, alpha, beta);
			}
		}
		if (val == null)
			val = GameStateEvaluator2.evaluate(playerId, game);
		if (logger.isDebugEnabled())
			logger.debug("returning -- combat score: " + val + " depth:" + depth + " for player:" + game.getPlayer(node.getPlayerId()).getName());
		return val;
	}


	protected int simulateAttackers(Game game, SimulationNode2 node, UUID attackerId, int depth, int alpha, int beta, boolean counter) {
		if (Thread.interrupted()) {
			Thread.currentThread().interrupt();
			logger.debug("interrupted");
			return GameStateEvaluator2.evaluate(playerId, game);
		}
		Integer val = null;
		SimulationNode2 bestNode = null;
		SimulatedPlayer2 attacker = (SimulatedPlayer2) game.getPlayer(attackerId);

		if (logger.isDebugEnabled())
			logger.debug(attacker.getName() + "'s possible attackers: " + attacker.getAvailableAttackers(game));
		for (Combat engagement: attacker.addAttackers(game)) {
			if (alpha >= beta) {
				logger.debug("simulating -- pruning attackers");
				break;
			}
			Game sim = game.copy();
			UUID defenderId = game.getOpponents(attackerId).iterator().next();
			for (CombatGroup group: engagement.getGroups()) {
				for (UUID attackId: group.getAttackers()) {
					sim.getPlayer(attackerId).declareAttacker(attackId, defenderId, sim);
				}
			}
			sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_ATTACKERS, attackerId, attackerId));
			SimulationNode2 newNode = new SimulationNode2(node, sim, depth, attackerId);
			if (logger.isDebugEnabled())
				logger.debug("simulating attack for player:" + game.getPlayer(attackerId).getName());
			sim.checkStateAndTriggered();
			while (!sim.getStack().isEmpty()) {
				sim.getStack().resolve(sim);
				logger.debug("resolving triggered abilities");
				sim.applyEffects();
			}
			sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARE_ATTACKERS_STEP_POST, sim.getActivePlayerId(), sim.getActivePlayerId()));
			Combat simCombat = sim.getCombat().copy();
			sim.getPhase().setStep(new DeclareBlockersStep());
			val = simulateCombat(sim, newNode, depth-1, alpha, beta, counter);
			if (!attackerId.equals(playerId)) {
				if (val < beta) {
					beta = val;
					bestNode = newNode;
					bestNode.setScore(val);
					node.setCombat(simCombat);
				}
			}
			else {
				if (val > alpha) {
					alpha = val;
					bestNode = newNode;
					bestNode.setScore(val);
					node.setCombat(simCombat);
				}
			}
		}
		if (val == null)
			val = GameStateEvaluator2.evaluate(playerId, game);
		if (bestNode != null) {
			node.children.clear();
			node.children.add(bestNode);
			node.setScore(bestNode.getScore());
		}
		if (logger.isDebugEnabled())
			logger.debug("returning -- combat attacker score: " + val + " depth:" + depth + " for player:" + game.getPlayer(node.getPlayerId()).getName());
		return val;
	}

	protected int simulateBlockers(Game game, SimulationNode2 node, UUID defenderId, int depth, int alpha, int beta, boolean counter) {
		if (Thread.interrupted()) {
			Thread.currentThread().interrupt();
			logger.debug("interrupted");
			return GameStateEvaluator2.evaluate(playerId, game);
		}
		Integer val = null;
		SimulationNode2 bestNode = null;
		//check if defender is being attacked
		if (game.getCombat().isAttacked(defenderId, game)) {
			SimulatedPlayer2 defender = (SimulatedPlayer2) game.getPlayer(defenderId);
			if (logger.isDebugEnabled())
				logger.debug(defender.getName() + "'s possible blockers: " + defender.getAvailableBlockers(game));
			for (Combat engagement: defender.addBlockers(game)) {
				if (alpha >= beta) {
					logger.debug("simulating -- pruning blockers");
					break;
				}
				Game sim = game.copy();
				for (CombatGroup group: engagement.getGroups()) {
					if (group.getAttackers().size() > 0) {
						UUID attackerId = group.getAttackers().get(0);
						for (UUID blockerId: group.getBlockers()) {
							sim.getPlayer(defenderId).declareBlocker(blockerId, attackerId, sim);
						}
					}
				}
				sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_BLOCKERS, defenderId, defenderId));
				SimulationNode2 newNode = new SimulationNode2(node, sim, depth, defenderId);
				if (logger.isDebugEnabled())
					logger.debug("simulating block for player:" + game.getPlayer(defenderId).getName());
				sim.checkStateAndTriggered();
				while (!sim.getStack().isEmpty()) {
					sim.getStack().resolve(sim);
					logger.debug("resolving triggered abilities");
					sim.applyEffects();
				}
				sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARE_BLOCKERS_STEP_POST, sim.getActivePlayerId(), sim.getActivePlayerId()));
				Combat simCombat = sim.getCombat().copy();
				finishCombat(sim);
				if (sim.isGameOver()) {
					val = GameStateEvaluator2.evaluate(playerId, sim);
				}
				else if (!counter) {
					val = simulatePostCombatMain(sim, newNode, depth-1, alpha, beta);
				}
				else
					val = GameStateEvaluator2.evaluate(playerId, sim);
				if (!defenderId.equals(playerId)) {
					if (val < beta) {
						beta = val;
						bestNode = newNode;
						bestNode.setScore(val);
						node.setCombat(simCombat);
					}
				}
				else {
					if (val > alpha) {
						alpha = val;
						bestNode = newNode;
						node.setCombat(simCombat);
					}
				}
			}
		}
		if (val == null)
			val = GameStateEvaluator2.evaluate(playerId, game);
		if (bestNode != null) {
			node.children.clear();
			node.children.add(bestNode);
			node.setScore(bestNode.getScore());
		}
		if (logger.isDebugEnabled())
			logger.debug("returning -- combat blocker score: " + val + " depth:" + depth + " for player:" + game.getPlayer(node.getPlayerId()).getName());
		return val;
	}

	protected int simulateCounterAttack(Game game, SimulationNode2 node, int depth, int alpha, int beta) {
		if (Thread.interrupted()) {
			Thread.currentThread().interrupt();
			logger.debug("interrupted");
			return GameStateEvaluator2.evaluate(playerId, game);
		}
		Integer val = null;
		if (!game.isGameOver()) {
			simulateToEnd(game);
			game.getState().setActivePlayerId(game.getState().getPlayerList(game.getActivePlayerId()).getNext());
			logger.debug("simulating -- counter attack for player " + game.getPlayer(game.getActivePlayerId()).getName());
			game.getTurn().setPhase(new BeginningPhase());
			if (game.getPhase().beginPhase(game, game.getActivePlayerId())) {
				simulateStep(game, new UntapStep());
				simulateStep(game, new UpkeepStep());
				simulateStep(game, new DrawStep());
				game.getPhase().endPhase(game, game.getActivePlayerId());
			}
			val = simulateCombat(game, node, depth-1, alpha, beta, true);
			if (logger.isDebugEnabled())
				logger.debug("returning -- counter attack score: " + val + " depth:" + depth + " for player:" + game.getPlayer(node.getPlayerId()).getName());
		}
		if (val == null)
			val = GameStateEvaluator2.evaluate(playerId, game);
		return val;
	}

	protected void simulateStep(Game game, Step step) {
		if (Thread.interrupted()) {
			Thread.currentThread().interrupt();
			logger.debug("interrupted");
			return;
		}
		if (!game.isGameOver()) {
			game.getPhase().setStep(step);
			if (!step.skipStep(game, game.getActivePlayerId())) {
				step.beginStep(game, game.getActivePlayerId());
				game.checkStateAndTriggered();
				while (!game.getStack().isEmpty()) {
					game.getStack().resolve(game);
					game.applyEffects();
				}
				step.endStep(game, game.getActivePlayerId());
			}
		}
	}

	protected void finishCombat(Game game) {
		if (Thread.interrupted()) {
			Thread.currentThread().interrupt();
			logger.debug("interrupted");
			return;
		}
		simulateStep(game, new FirstCombatDamageStep());
		simulateStep(game, new CombatDamageStep());
		simulateStep(game, new EndOfCombatStep());
	}

	protected int simulatePostCombatMain(Game game, SimulationNode2 node, int depth, int alpha, int beta) {
		if (Thread.interrupted()) {
			Thread.currentThread().interrupt();
			logger.debug("interrupted");
			return GameStateEvaluator2.evaluate(playerId, game);
		}
		logger.debug("simulating -- post combat main");
		game.getTurn().setPhase(new PostCombatMainPhase());
		if (game.getPhase().beginPhase(game, game.getActivePlayerId())) {
			game.getPhase().setStep(new PostCombatMainStep());
			game.getStep().beginStep(game, playerId);
			game.getPlayers().resetPassed();
			return addActions(node, new FilterAbility(), depth, alpha, beta);
		}
		return simulateCounterAttack(game, node, depth, alpha, beta);
	}

	protected void simulateToEnd(Game game) {
		if (Thread.interrupted()) {
			Thread.currentThread().interrupt();
			logger.debug("interrupted");
			return;
		}
		if (!game.isGameOver()) {
			game.getTurn().getPhase().endPhase(game, game.getActivePlayerId());
			game.getTurn().setPhase(new EndPhase());
			if (game.getTurn().getPhase().beginPhase(game, game.getActivePlayerId())) {
				simulateStep(game, new EndStep());
				simulateStep(game, new CleanupStep());
			}
		}
	}

}
