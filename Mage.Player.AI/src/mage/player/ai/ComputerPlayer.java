/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.player.ai;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Logger;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.Mana;
import mage.abilities.ActivatedAbility;
import mage.abilities.TriggeredAbilities;
import mage.abilities.TriggeredAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.HybridManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.MonoHybridManaCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffect;
import mage.abilities.effects.common.BecomesCreatureSourceEOTEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.ManaAbility;
import mage.abilities.mana.ManaOptions;
import mage.player.ai.simulators.CombatGroupSimulator;
import mage.player.ai.simulators.CombatSimulator;
import mage.player.ai.simulators.CreatureSimulator;
import mage.cards.Card;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.filter.common.FilterCreatureForAttack;
import mage.filter.common.FilterCreatureForCombat;
import mage.filter.common.FilterLandCard;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.game.GameState;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerImpl;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetDiscard;
import mage.target.common.TargetSacrificePermanent;
import mage.util.Copier;
import mage.util.Logging;
import mage.util.TreeNode;

/**
 *
 * only suitable for two player games
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ComputerPlayer extends PlayerImpl implements Player {

	private final static Logger logger = Logging.getLogger(ComputerPlayer.class.getName());
	private boolean abort = false;
	private Map<Mana, Card> unplayable = new TreeMap<Mana, Card>();
	private List<Card> playableNonInstant = new ArrayList<Card>();
	private List<Card> playableInstant = new ArrayList<Card>();
	private List<ActivatedAbility> playableAbilities = new ArrayList<ActivatedAbility>();

	public ComputerPlayer(String name) {
		super(name);
		human = false;
	}

	@Override
	public boolean chooseMulligan(Game game) {
		logger.fine("chooseMulligan");
		if (hand.size() < 6)
			return false;
		List<Card> lands = hand.getCards(new FilterLandCard());
		if (lands.size() < 2 || lands.size() > hand.size() - 2)
			return true;
		return false;
	}

	@Override
	public boolean chooseTarget(Outcome outcome, Target target, Game game) {
		logger.fine("chooseTarget: " + outcome.toString() + ":" + target.toString());
		UUID opponentId = game.getOpponents(playerId).get(0);
		if (target instanceof TargetPlayer) {
			if (outcome.isGood()) {
				if (target.canTarget(playerId, game)) {
					target.addTarget(playerId, game);
					return true;
				}
			}
			else {
				if (target.canTarget(playerId, game)) {
					target.addTarget(opponentId, game);
					return true;
				}
			}
		}
		if (target instanceof TargetDiscard) {
			findPlayables(game);
			if (unplayable.size() > 0) {
				for (int i = unplayable.size() - 1; i >= 0; i--) {
					if (target.canTarget(unplayable.values().toArray(new Card[0])[i].getId(), game)) {
						target.addTarget(unplayable.values().toArray(new Card[0])[i].getId(), game);
						return true;
					}
				}
			}
			if (hand.size() > 0) {
				if (target.canTarget(hand.keySet().toArray(new UUID[0])[0], game)) {
					target.addTarget(hand.keySet().toArray(new UUID[0])[0], game);
					return true;
				}
			}
		}
		if (target instanceof TargetSacrificePermanent) {
			List<Permanent> targets;
			targets = threats(playerId, (TargetPermanent) target, game);
			Collections.reverse(targets);
			for (Permanent permanent: targets) {
				if (target.canTarget(permanent.getId(), game)) {
					target.addTarget(permanent.getId(), game);
					return true;
				}
			}
		}
		if (target instanceof TargetPermanent) {
			List<Permanent> targets;
			if (outcome.isGood()) {
				targets = threats(playerId, (TargetPermanent) target, game);
			}
			else {
				targets = threats(opponentId, (TargetPermanent) target, game);
			}
			for (Permanent permanent: targets) {
				if (target.canTarget(permanent.getId(), game)) {
					target.addTarget(permanent.getId(), game);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void priority(Game game) {
		logger.fine("priority");
		UUID opponentId = game.getOpponents(playerId).get(0);
		if (game.getActivePlayerId().equals(playerId)) {
			if (game.isMainPhase() && game.getStack().isEmpty()) {
				playLand(game);
			}
			switch (game.getTurn().getStep()) {
				case UPKEEP:
					findPlayables(game);
					break;
				case DRAW:
					logState(game);
				case PRECOMBAT_MAIN:
					findPlayables(game);
					if (playableAbilities.size() > 0) {
						for (ActivatedAbility ability: playableAbilities) {
							if (ability.canActivate(playerId, game)) {
								if (ability.getEffects().hasOutcome(Outcome.PutLandInPlay)) {
									if (this.activateAbility(ability, game))
										return;
								}
								if (ability.getEffects().hasOutcome(Outcome.PutCreatureInPlay)) {
									if (getOpponentBlockers(opponentId, game).size() <= 1)
										if (this.activateAbility(ability, game))
											return;
								}
							}
						}
					}
					break;
				case DECLARE_BLOCKERS:
					playRemoval(game.getCombat().getBlockers(), game);
					playDamage(game.getCombat().getBlockers(), game);
				case END_COMBAT:
					playDamage(game.getCombat().getBlockers(), game);
				case POSTCOMBAT_MAIN:
					findPlayables(game);
					if (game.getStack().isEmpty()) {
						if (playableNonInstant.size() > 0) {
							for (Card card: playableNonInstant) {
								if (card.getSpellAbility().canActivate(playerId, game)) {
									if (this.activateAbility(card.getSpellAbility(), game))
										return;
								}
							}
						}
						if (playableAbilities.size() > 0) {
							for (ActivatedAbility ability: playableAbilities) {
								if (ability.canActivate(playerId, game)) {
									if (!(ability.getEffects().get(0) instanceof BecomesCreatureSourceEOTEffect)) {
										if (this.activateAbility(ability, game))
											return;
									}
								}
							}
						}
					}
					break;
			}
		}
		else {
			//respond to opponent events
			switch (game.getTurn().getStep()) {
				case DECLARE_ATTACKERS:
					playRemoval(game.getCombat().getAttackers(), game);
					playDamage(game.getCombat().getAttackers(), game);
				case END_COMBAT:
					playDamage(game.getCombat().getAttackers(), game);
			}
		}
		this.passed = true;
	}

	private void playLand(Game game) {
		logger.fine("playLand");
		List<Card> lands = hand.getCards(new FilterLandCard());
		while (lands.size() > 0 && this.landsPlayed < this.landsPerTurn) {
			if (lands.size() == 1)
				this.playLand(lands.get(0), game);
			else {
				playALand(lands, game);
			}
		}
	}

	private void playALand(List<Card> lands, Game game) {
		logger.fine("playALand");
		//play a land that will allow us to play an unplayable
		for (Mana mana: unplayable.keySet()) {
			for (Card card: lands) {
				for (ManaAbility ability: card.getAbilities().getManaAbilities(Zone.BATTLEFIELD)) {
					if (ability.getNetMana().enough(mana)) {
						this.playLand(card, game);
						lands.remove(card);
						return;
					}
				}
			}
		}
		//play a land that will get us closer to playing an unplayable
		for (Mana mana: unplayable.keySet()) {
			for (Card card: lands) {
				for (ManaAbility ability: card.getAbilities().getManaAbilities(Zone.BATTLEFIELD)) {
					if (mana.contains(ability.getNetMana())) {
						this.playLand(card, game);
						lands.remove(card);
						return;
					}
				}
			}
		}
		//play first available land
		this.playLand(lands.get(0), game);
		lands.remove(0);
	}

	private void findPlayables(Game game) {
		playableInstant.clear();
		playableNonInstant.clear();
		unplayable.clear();
		playableAbilities.clear();
		List<Card> nonLands = hand.getCards(new FilterNonlandCard());
		ManaOptions available = getManaAvailable(game);
		available.addMana(manaPool.getMana());

		for (Card card: nonLands) {
			ManaOptions options = card.getManaCost().getOptions();
			if (card.getManaCost().getVariableCosts().size() > 0) {
				//don't use variable mana costs unless there is at least 3 extra mana for X
				for (Mana option: options) {
					option.add(Mana.ColorlessMana(3));
				}
			}
			for (Mana mana: options) {
				for (Mana avail: available) {
					if (mana.enough(avail)) {
						if (card.getCardType().contains(CardType.INSTANT))
							playableInstant.add(card);
						else
							playableNonInstant.add(card);
					}
					else {
						if (!playableInstant.contains(card) && !playableNonInstant.contains(card))
							unplayable.put(mana.needed(avail), card);
					}
				}
			}
		}
		for (Permanent permanent: game.getBattlefield().getActivePermanents(playerId)) {
			for (ActivatedAbility ability: permanent.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD)) {
				if (!(ability instanceof ManaAbility) && ability.canActivate(playerId, game)) {
					ManaOptions abilityOptions = ability.getManaCosts().getOptions();
					if (ability.getManaCosts().getVariableCosts().size() > 0) {
						//don't use variable mana costs unless there is at least 3 extra mana for X
						for (Mana option: abilityOptions) {
							option.add(Mana.ColorlessMana(3));
						}
					}
					if (abilityOptions.size() == 0) {
						playableAbilities.add(ability);
					}
					else {
						for (Mana mana: abilityOptions) {
							for (Mana avail: available) {
								if (mana.enough(avail)) {
									playableAbilities.add(ability);
								}
							}
						}
					}
				}
			}
		}
		logger.fine("findPlayables: " + playableInstant.toString() + "---" + playableNonInstant.toString() + "---" + playableAbilities.toString() );
	}

	protected ManaOptions getManaAvailable(Game game) {
		logger.fine("getManaAvailable");
		List<Permanent> manaPerms = this.getAvailableManaProducers(game);
		
		ManaOptions available = new ManaOptions();
		for (Permanent perm: manaPerms) {
			available.addMana(perm.getAbilities().getManaAbilities(Zone.BATTLEFIELD));
		}
		return available;
	}

	@Override
	public boolean playMana(ManaCost unpaid, Game game) {
		logger.fine("playMana");
		ManaCost cost;
		List<Permanent> producers;
		if (unpaid instanceof ManaCosts) {
			cost = ((ManaCosts)unpaid).get(0);
			producers = getSortedProducers((ManaCosts)unpaid, game);
		}
		else {
			cost = unpaid;
			producers = this.getAvailableManaProducers(game);
		}
		for (Permanent perm: producers) {
			// pay all colored costs first
			for (ManaAbility ability: perm.getAbilities().getManaAbilities(Zone.BATTLEFIELD)) {
				if (cost instanceof ColoredManaCost) {
					if (cost.testPay(ability.getNetMana())) {
						if (activateAbility(ability, game))
							return true;
					}
				}
			}
			// then pay hybrid
			for (ManaAbility ability: perm.getAbilities().getManaAbilities(Zone.BATTLEFIELD)) {
				if (cost instanceof HybridManaCost) {
					if (cost.testPay(ability.getNetMana())) {
						if (activateAbility(ability, game))
							return true;
					}
				}
			}
			// then pay mono hybrid
			for (ManaAbility ability: perm.getAbilities().getManaAbilities(Zone.BATTLEFIELD)) {
				if (cost instanceof MonoHybridManaCost) {
					if (cost.testPay(ability.getNetMana())) {
						if (activateAbility(ability, game))
							return true;
					}
				}
			}
			// finally pay generic
			for (ManaAbility ability: perm.getAbilities().getManaAbilities(Zone.BATTLEFIELD)) {
				if (cost instanceof GenericManaCost) {
					if (cost.testPay(ability.getNetMana())) {
						if (activateAbility(ability, game))
							return true;
					}
				}
			}
		}
		return false;
	}

	private List<Permanent> getSortedProducers(ManaCosts unpaid, Game game) {
		List<Permanent> unsorted = this.getAvailableManaProducers(game);
		Map<Permanent, Integer> scored = new HashMap<Permanent, Integer>();
		for (Permanent permanent: unsorted) {
			int score = 0;
			for (ManaCost cost: unpaid) {
				for (ManaAbility ability: permanent.getAbilities().getManaAbilities(Zone.BATTLEFIELD)) {
					if (cost.testPay(ability.getNetMana())) {
						score++;
						break;
					}
				}
			}
			scored.put(permanent, score);
		}
		return sortByValue(scored);
	}

	private List<Permanent> sortByValue(Map<Permanent, Integer> map) {
		List<Entry<Permanent, Integer>> list = new LinkedList<Entry<Permanent, Integer>>(map.entrySet());
		Collections.sort(list, new Comparator<Entry<Permanent, Integer>>() {
			@Override
			public int compare(Entry<Permanent, Integer> o1, Entry<Permanent, Integer> o2) {
				return (o1.getValue().compareTo(o2.getValue()));
			}
		});
		List<Permanent> result = new ArrayList<Permanent>();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Entry<Permanent, Integer> entry = (Entry<Permanent, Integer>)it.next();
			result.add(entry.getKey());
		}
		return result;
	}

	@Override
	public boolean playXMana(VariableManaCost cost, Game game) {
		logger.fine("playXMana");
		//put everything into X
		for (Permanent perm: this.getAvailableManaProducers(game)) {
			for (ManaAbility ability: perm.getAbilities().getManaAbilities(Zone.BATTLEFIELD)) {
				if (activateAbility(ability, game))
					return true;
			}
		}
		cost.setPaid();
		return true;
	}

	@Override
	public void abort() {
		abort = true;
	}

	@Override
	public boolean chooseUse(Outcome outcome, String message, Game game) {
		logger.fine("chooseUse");
		//TODO: improve this
		return outcome.isGood();
	}

	@Override
	public boolean choose(Outcome outcome, Choice choice, Game game) {
		logger.fine("choose");
		//TODO: improve this
		choice.setChoice(choice.getChoices().get(0));
		return true;
	}

	@Override
	public boolean searchCards(Cards cards, TargetCard target, Game game)  {
		logger.fine("searchCards");
		//TODO: improve ths
		//return first match
		for (Card card: cards.getCards(target.getFilter())) {
			target.addTarget(card.getId(), game);
			return true;
		}
		return false;
	}

	@Override
	public void selectAttackers(Game game) {
		logger.fine("selectAttackers");
		UUID opponentId = game.getOpponents(playerId).get(0);
		Attackers attackers = getPotentialAttackers(game);
		List<Permanent> blockers = getOpponentBlockers(opponentId, game);
		List<Permanent> actualAttackers = new ArrayList<Permanent>();
		if (blockers.isEmpty()) {
			actualAttackers = attackers.getAttackers();
		}
		else if (attackers.size() - blockers.size() >= game.getPlayer(opponentId).getLife()) {
			actualAttackers = attackers.getAttackers();
		}
		else {
			CombatSimulator combat = simulateAttack(attackers, blockers, opponentId, game);
			if (combat.rating > 2) {
				for (CombatGroupSimulator group: combat.groups) {
					this.declareAttacker(group.attackers.get(0).id, group.defenderId, game);
				}
			}
		}
		for (Permanent attacker: actualAttackers) {
			this.declareAttacker(attacker.getId(), opponentId, game);
		}
		return;
	}

	@Override
	public void selectBlockers(Game game) {
		logger.fine("selectBlockers");

		List<Permanent> blockers = getAvailableBlockers(game);

		CombatSimulator sim = simulateBlock(CombatSimulator.load(game), blockers, game);

		List<CombatGroup> groups = game.getCombat().getGroups();
		for (int i = 0; i< groups.size(); i++) {
			for (CreatureSimulator creature: sim.groups.get(i).blockers) {
				groups.get(i).addBlocker(creature.id, playerId, game);
			}
		}
	}
	
	@Override
	public int chooseEffect(List<ReplacementEffect> rEffects, Game game) {
		logger.fine("chooseEffect");
		//TODO: implement this
		return 0;
	}

	@Override
	public TriggeredAbility chooseTriggeredAbility(TriggeredAbilities abilities, Game game) {
		logger.fine("chooseTriggeredAbility");
		//TODO: improve this
		if (abilities.size() > 0)
			return abilities.get(0);
		return null;
	}

	@Override
	public void assignDamage(int damage, List<UUID> targets, UUID sourceId, Game game) {
		logger.fine("assignDamage");
		//TODO: improve this
		game.getPermanent(targets.get(0)).damage(damage, sourceId, game);
	}

	@Override
	public int getAmount(int min, int max, String message, Game game) {
		logger.fine("getAmount");
		//TODO: improve this
		return min;
	}

	protected List<Permanent> getAvailableManaProducers(Game game) {
		logger.fine("getAvailableManaProducers");
		List<Permanent> result = new ArrayList<Permanent>();
		for (Permanent permanent: game.getBattlefield().getActivePermanents(playerId)) {
			for (ManaAbility ability: permanent.getAbilities().getManaAbilities(Zone.BATTLEFIELD)) {
				if (ability.canActivate(playerId, game)) {
					result.add(permanent);
					break;
				}
			}
		}
		return result;
	}

	protected Attackers getPotentialAttackers(Game game) {
		logger.fine("getAvailableAttackers");
		Attackers attackers = new Attackers();
		List<Permanent> creatures = super.getAvailableAttackers(game);
		for (Permanent creature: creatures) {
			int potential = combatPotential(creature, game);
			if (potential > 0) {
				List<Permanent> l = attackers.get(potential);
				if (l == null)
					attackers.put(potential, l = new ArrayList<Permanent>());
				l.add(creature);
			}
		}	
		return attackers;
	}

	protected int combatPotential(Permanent creature, Game game) {
		logger.fine("combatPotential");
		if (!creature.canAttack(game))
			return 0;
		int potential = creature.getPower().getValue();
		potential += creature.getAbilities().getEvasionAbilities().size();
		potential += creature.getAbilities().getProtectionAbilities().size();
		potential += creature.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId())?1:0;
		potential += creature.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId())?2:0;
		potential += creature.getAbilities().containsKey(TrampleAbility.getInstance().getId())?1:0;
		return potential;
	}

	protected List<Permanent> getAvailableBlockers(Game game) {
		logger.fine("getAvailableBlockers");
		FilterCreatureForCombat blockFilter = new FilterCreatureForCombat();
		blockFilter.getControllerId().add(playerId);
		List<Permanent> blockers = game.getBattlefield().getActivePermanents(blockFilter);
		return blockers;
	}

	protected List<Permanent> getOpponentBlockers(UUID opponentId, Game game) {
		logger.fine("getOpponentBlockers");
		FilterCreatureForCombat blockFilter = new FilterCreatureForCombat();
		blockFilter.getControllerId().add(opponentId);
		List<Permanent> blockers = game.getBattlefield().getActivePermanents(blockFilter);
		return blockers;
	}

	protected CombatSimulator simulateAttack(Attackers attackers, List<Permanent> blockers, UUID opponentId, Game game) {
		logger.fine("simulateAttack");
		List<Permanent> attackersList = attackers.getAttackers();
		CombatSimulator best = new CombatSimulator();
		int bestResult = 0;
		//use binary digits to calculate powerset of attackers
		int powerElements = (int) Math.pow(2, attackersList.size());
		for (int i = 1; i < powerElements; i++) {
			String binary = Integer.toBinaryString(i);
			while(binary.length() < attackersList.size()) {
				binary = "0" + binary;
			}
			List<Permanent> trialAttackers = new ArrayList<Permanent>();
			for (int j = 0; j < attackersList.size(); j++) {
				if (binary.charAt(j) == '1')
					trialAttackers.add(attackersList.get(j));
			}
			CombatSimulator combat = new CombatSimulator();
			for (Permanent permanent: trialAttackers) {
				combat.groups.add(new CombatGroupSimulator(opponentId, Arrays.asList(permanent.getId()), new ArrayList<UUID>(), game));
			}
			CombatSimulator test = simulateBlock(combat, blockers, game);
			if (test.evaluate() > bestResult) {
				best = test;
				bestResult = test.evaluate();
			}
		}

		return best;
	}

	protected CombatSimulator simulateBlock(CombatSimulator combat, List<Permanent> blockers, Game game) {
		logger.fine("simulateBlock");

		TreeNode<CombatSimulator> simulations;

		simulations = new TreeNode<CombatSimulator>(combat);
		addBlockSimulations(blockers, simulations, game);
		combat.simulate();

		return getWorstSimulation(simulations);

	}

	protected void addBlockSimulations(List<Permanent> blockers, TreeNode<CombatSimulator> node, Game game) {
		int numGroups = node.getData().groups.size();
		Copier<CombatSimulator> copier = new Copier<CombatSimulator>();
		for (Permanent blocker: blockers) {
			List<Permanent> subList = remove(blockers, blocker);
			for (int i = 0; i < numGroups; i++) {
				if (node.getData().groups.get(i).canBlock(blocker, game)) {
					CombatSimulator combat = copier.copy(node.getData());
					combat.groups.get(i).blockers.add(new CreatureSimulator(blocker));
					TreeNode<CombatSimulator> child = new TreeNode<CombatSimulator>(combat);
					node.addChild(child);
					addBlockSimulations(subList, child, game);
					combat.simulate();
				}
			}
		}
	}

	protected List<Permanent> remove(List<Permanent> source, Permanent element) {
		List<Permanent> newList = new ArrayList<Permanent>();
		for (Permanent permanent: source) {
			if (!permanent.equals(element)) {
				newList.add(permanent);
			}
		}
		return newList;
	}

	protected CombatSimulator getBestSimulation(TreeNode<CombatSimulator> simulations) {
		CombatSimulator best = simulations.getData();
		int bestResult = best.evaluate();
		for (TreeNode<CombatSimulator> node: simulations.getChildren()) {
			CombatSimulator bestSub = getBestSimulation(node);
			if (bestSub.evaluate() > bestResult) {
				best = node.getData();
				bestResult = best.evaluate();
			}
		}
		return best;
	}

	protected CombatSimulator getWorstSimulation(TreeNode<CombatSimulator> simulations) {
		CombatSimulator worst = simulations.getData();
		int worstResult = worst.evaluate();
		for (TreeNode<CombatSimulator> node: simulations.getChildren()) {
			CombatSimulator worstSub = getWorstSimulation(node);
			if (worstSub.evaluate() < worstResult) {
				worst = node.getData();
				worstResult = worst.evaluate();
			}
		}
		return worst;
	}

	protected List<Permanent> threats(UUID playerId, TargetPermanent target, Game game) {
		List<Permanent> threats = game.getBattlefield().getActivePermanents(target.getFilter());
		Iterator<Permanent> it = threats.iterator();
		while(it.hasNext()) {
			Permanent permanent = it.next();
			if (!permanent.getControllerId().equals(playerId)) {
				it.remove();
			}
		}

		Collections.sort(threats, new PermanentComparator(game));
		return threats;
	}

	private void logState(Game game) {
		StringBuilder sb = new StringBuilder();
		sb.append("computer player hand: ");
		for (Card card: hand.values()) {
			sb.append(card.getName()).append(",");
		}
		logger.fine(sb.toString());
	}

	private void playRemoval(List<UUID> creatures, Game game) {
		for (UUID creatureId: creatures) {
			for (Card card: this.playableInstant) {
				if (card.getSpellAbility().canActivate(playerId, game)) {
					for (Effect effect: card.getSpellAbility().getEffects()) {
						if (effect.getOutcome().equals(Outcome.DestroyPermanent)) {
							if (card.getSpellAbility().getTargets().get(0).canTarget(creatureId, game)) {
								if (this.activateAbility(card.getSpellAbility(), game))
									return;
							}
						}
					}
				}
			}
		}
	}

	private void playDamage(List<UUID> creatures, Game game) {
		for (UUID creatureId: creatures) {
			Permanent creature = game.getPermanent(creatureId);
			for (Card card: this.playableInstant) {
				if (card.getSpellAbility().canActivate(playerId, game)) {
					for (Effect effect: card.getSpellAbility().getEffects()) {
						if (effect instanceof DamageTargetEffect) {
							if (card.getSpellAbility().getTargets().get(0).canTarget(creatureId, game)) {
								if (((DamageTargetEffect)effect).getAmount() > (creature.getPower().getValue() - creature.getDamage())) {
									if (this.activateAbility(card.getSpellAbility(), game))
										return;
								}
							}
						}
					}
				}
			}
		}
	}
}

