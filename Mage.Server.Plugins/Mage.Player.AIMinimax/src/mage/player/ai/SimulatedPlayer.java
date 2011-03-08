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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.PassAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.mana.ManaOptions;
import mage.choices.Choice;
import mage.filter.FilterAbility;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.target.Target;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SimulatedPlayer extends ComputerPlayer<SimulatedPlayer> {

	private final static transient Logger logger = Logger.getLogger(SimulatedPlayer.class);
	private boolean isSimulatedPlayer;
	private FilterAbility filter;
	private transient ConcurrentLinkedQueue<Ability> allActions;
	private static PassAbility pass = new PassAbility();
	protected int maxDepth;

	public SimulatedPlayer(UUID id, boolean isSimulatedPlayer) {
		super(id);
		maxDepth = Config.maxDepth;
		pass.setControllerId(playerId);
		this.isSimulatedPlayer = isSimulatedPlayer;
	}

	public SimulatedPlayer(final SimulatedPlayer player) {
		super(player);
		this.isSimulatedPlayer = player.isSimulatedPlayer;
		if (player.filter != null)
			this.filter = player.filter.copy();
	}

	@Override
	public SimulatedPlayer copy() {
		return new SimulatedPlayer(this);
	}

	public List<Ability> simulatePriority(Game game, FilterAbility filter) {
		allActions = new ConcurrentLinkedQueue<Ability>();
		Game sim = game.copy();
		this.filter = filter;

		simulateOptions(sim, pass);

		ArrayList<Ability> list = new ArrayList<Ability>(allActions);
		//Collections.shuffle(list);
		Collections.reverse(list);
		return list;
	}

	protected void simulateOptions(Game game, Ability previousActions) {
		allActions.add(previousActions);
		ManaOptions available = getManaAvailable(game);
		available.addMana(manaPool.getMana());
		List<Ability> playables = game.getPlayer(playerId).getPlayable(game, filter, available, isSimulatedPlayer);
		for (Ability ability: playables) {
			List<Ability> options = game.getPlayer(playerId).getPlayableOptions(ability, game);
			if (options.size() == 0) {
				if (ability.getManaCosts().getVariableCosts().size() > 0) {
					simulateVariableCosts(ability, game);
				}
				else {
					allActions.add(ability);
				}
//				simulateAction(game, previousActions, ability);
			}
			else {
//				ExecutorService simulationExecutor = Executors.newFixedThreadPool(4);
				for (Ability option: options) {
					if (ability.getManaCosts().getVariableCosts().size() > 0) {
						simulateVariableCosts(option, game);
					}
					else {
						allActions.add(option);
					}
//					SimulationWorker worker = new SimulationWorker(game, this, previousActions, option);
//					simulationExecutor.submit(worker);
				}
//				simulationExecutor.shutdown();
//				while(!simulationExecutor.isTerminated()) {}
			}
		}
	}

//	protected void simulateAction(Game game, SimulatedAction previousActions, Ability action) {
//		List<Ability> actions = new ArrayList<Ability>(previousActions.getAbilities());
//		actions.add(action);
//		Game sim = game.copy();
//		if (sim.getPlayer(playerId).activateAbility((ActivatedAbility) action.copy(), sim)) {
//			sim.applyEffects();
//			sim.getPlayers().resetPassed();
//			allActions.add(new SimulatedAction(sim, actions));
//		}
//	}

	//add a generic mana cost for each amount possible
	protected void simulateVariableCosts(Ability ability, Game game) {
		int numAvailable = getAvailableManaProducers(game).size();
		for (int i = 0; i < numAvailable; i++) {
			Ability newAbility = ability.copy();
			newAbility.addManaCost(new GenericManaCost(i));
			allActions.add(newAbility);
		}
	}

	@Override
	public boolean playXMana(VariableManaCost cost, ManaCosts<ManaCost> costs, Game game) {
		//simulateVariableCosts method adds a generic mana cost for each option
		for (ManaCost manaCost: costs) {
			if (manaCost instanceof GenericManaCost) {
				cost.setPayment(manaCost.getPayment());
				logger.debug("simulating -- X = " + cost.getPayment().count());
				break;
			}
		}
		cost.setPaid();
		return true;
	}

	public List<Combat> addAttackers(Game game) {
		Map<Integer, Combat> engagements = new HashMap<Integer, Combat>();
		//useful only for two player games - will only attack first opponent
		UUID defenderId = game.getOpponents(playerId).iterator().next();
		List<Permanent> attackersList = super.getAvailableAttackers(game);
		//use binary digits to calculate powerset of attackers
		int powerElements = (int) Math.pow(2, attackersList.size());
		StringBuilder binary = new StringBuilder();
		for (int i = powerElements - 1; i >= 0; i--) {
			Game sim = game.copy();
			binary.setLength(0);
			binary.append(Integer.toBinaryString(i));
			while (binary.length() < attackersList.size()) {
				binary.insert(0, "0");
			}
			for (int j = 0; j < attackersList.size(); j++) {
				if (binary.charAt(j) == '1')
					sim.getCombat().declareAttacker(attackersList.get(j).getId(), defenderId, sim);
			}
			if (engagements.put(sim.getCombat().getValue(sim), sim.getCombat()) != null) {
				logger.debug("simulating -- found redundant attack combination");
			}
			else if (logger.isDebugEnabled()) {
				logger.debug("simulating -- attack:" + sim.getCombat().getGroups().size());
			}
		}
		return new ArrayList<Combat>(engagements.values());
	}

	public List<Combat> addBlockers(Game game) {
		Map<Integer, Combat> engagements = new HashMap<Integer, Combat>();
		int numGroups = game.getCombat().getGroups().size();
		if (numGroups == 0) return new ArrayList<Combat>();

		//add a node with no blockers
		Game sim = game.copy();
		engagements.put(sim.getCombat().getValue(sim), sim.getCombat());
		sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_BLOCKERS, playerId, playerId));

		List<Permanent> blockers = getAvailableBlockers(game);
		addBlocker(game, blockers, engagements);

		return new ArrayList<Combat>(engagements.values());
	}

	protected void addBlocker(Game game, List<Permanent> blockers, Map<Integer, Combat> engagements) {
		if (blockers.size() == 0)
			return;
		int numGroups = game.getCombat().getGroups().size();
		//try to block each attacker with each potential blocker
		Permanent blocker = blockers.get(0);
		if (logger.isDebugEnabled())
			logger.debug("simulating -- block:" + blocker);
		List<Permanent> remaining = remove(blockers, blocker);
		for (int i = 0; i < numGroups; i++) {
			if (game.getCombat().getGroups().get(i).canBlock(blocker, game)) {
				Game sim = game.copy();
				sim.getCombat().getGroups().get(i).addBlocker(blocker.getId(), playerId, sim);
				if (engagements.put(sim.getCombat().getValue(sim), sim.getCombat()) != null)
					logger.debug("simulating -- found redundant block combination");
				addBlocker(sim, remaining, engagements);  // and recurse minus the used blocker
			}
		}
		addBlocker(game, remaining, engagements);
	}

	@Override
	public boolean triggerAbility(TriggeredAbility source, Game game) {
		Ability ability = source.copy();
		List<Ability> options = getPlayableOptions(ability, game);
		if (options.size() == 0) {
			if (logger.isDebugEnabled())
				logger.debug("simulating -- triggered ability:" + ability);
			game.getStack().push(new StackAbility(ability, playerId));
			ability.activate(game, false);
			game.applyEffects();
			game.getPlayers().resetPassed();
		}
		else {
			SimulationNode parent = (SimulationNode) game.getCustomData();
			if (parent.getDepth() == maxDepth) return true;
			logger.debug(indent(parent.getDepth()) + "simulating -- triggered ability - adding children:" + options.size());
			for (Ability option: options) {
				addAbilityNode(parent, option, game);
			}
		}
		return true;
	}

	protected void addAbilityNode(SimulationNode parent, Ability ability, Game game) {
		Game sim = game.copy();
		sim.getStack().push(new StackAbility(ability, playerId));
		ability.activate(sim, false);
		sim.applyEffects();
		SimulationNode newNode = new SimulationNode(parent, sim, playerId);
		logger.debug(indent(newNode.getDepth()) + "simulating -- node #:" + SimulationNode.getCount() + " triggered ability option");
		for (Target target: ability.getTargets()) {
			for (UUID targetId: target.getTargets()) {
				newNode.getTargets().add(targetId);
			}
		}
		for (Choice choice: ability.getChoices()) {
			newNode.getChoices().add(choice.getChoice());
		}
		parent.children.add(newNode);
	}

	@Override
	public void priority(Game game) {
		//should never get here
	}

	protected String indent(int num) {
		char[] fill = new char[num];
		Arrays.fill(fill, ' ');
		return new String(fill);
	}

}
