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

package mage.player.human;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import mage.Constants;
import mage.Constants.Outcome;
import mage.Constants.RangeOfInfluence;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.Mode;
import mage.abilities.Modes;
import mage.abilities.SpecialAction;
import mage.abilities.TriggeredAbilities;
import mage.abilities.TriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.PhyrexianManaCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.ReplacementEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.filter.common.FilterCreatureForCombat;
import mage.game.Game;
import mage.game.draft.Draft;
import mage.game.match.Match;
import mage.game.permanent.Permanent;
import mage.game.tournament.Tournament;
import mage.players.Player;
import mage.players.PlayerImpl;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetDefender;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class HumanPlayer extends PlayerImpl<HumanPlayer> {

	private final transient PlayerResponse response = new PlayerResponse();

	protected static FilterCreatureForCombat filter = new FilterCreatureForCombat();
	protected static Choice replacementEffectChoice = new ChoiceImpl(true);
	private static Map<String, Serializable> staticOptions = new HashMap<String, Serializable>();

	static {
		filter.setTargetController(TargetController.YOU);
		replacementEffectChoice.setMessage("Choose replacement effect");
		staticOptions.put("UI.right.btn.text", "Done");
	}
	protected transient TargetCreaturePermanent targetCombat = new TargetCreaturePermanent(filter);

	public HumanPlayer(String name, RangeOfInfluence range, int skill) {
		super(name, range);
		human = true;
	}

	public HumanPlayer(final HumanPlayer player) {
		super(player);
	}

	protected void waitForResponse() {
		response.clear();
		synchronized(response) {
			try {
				response.wait();
			} catch (InterruptedException ex) {	}
		}
	}

	protected void waitForBooleanResponse() {
		do {
			waitForResponse();
		} while (response.getBoolean() == null && !abort);
	}

	protected void waitForUUIDResponse() {
		do {
			waitForResponse();
		} while (response.getUUID() == null && !abort);
	}

	protected void waitForStringResponse() {
		do {
			waitForResponse();
		} while (response.getString() == null && !abort);
	}

	protected void waitForIntegerResponse() {
		do {
			waitForResponse();
		} while (response.getInteger() == null && !abort);
	}

	@Override
	public boolean chooseMulligan(Game game) {
		game.getState().setPriorityPlayerId(getId());
		game.fireAskPlayerEvent(playerId, "Do you want to take a mulligan?");
		waitForBooleanResponse();
		if (!abort)
			return response.getBoolean();
		return false;
	}

	@Override
	public boolean chooseUse(Outcome outcome, String message, Game game) {
		game.getState().setPriorityPlayerId(getId());
		game.fireAskPlayerEvent(playerId, message);
		waitForBooleanResponse();
		if (!abort)
			return response.getBoolean();
		return false;
	}

	@Override
	public int chooseEffect(List<ReplacementEffect> rEffects, Game game) {
		game.getState().setPriorityPlayerId(getId());
		replacementEffectChoice.getChoices().clear();
		int count = 1;
		for (ReplacementEffect effect: rEffects) {
			replacementEffectChoice.getChoices().add(count + ". " + effect.getText(null));
			count++;
		}
		if (replacementEffectChoice.getChoices().size() == 1)
			return 0;
		while (!abort) {
			game.fireChooseEvent(playerId, replacementEffectChoice);
			waitForResponse();
			System.out.println(response.getString());
			if (response.getString() != null) {
				replacementEffectChoice.setChoice(response.getString());
				count = 1;
				for (int i = 0; i < rEffects.size(); i++) {
					if (replacementEffectChoice.getChoice().equals(count + ". " + rEffects.get(i).getText(null)))
						return i;
					count++;
				}
			}
		}
		return 0;
	}

	@Override
	public boolean choose(Outcome outcome, Choice choice, Game game) {
		game.getState().setPriorityPlayerId(getId());
		while (!abort) {
			game.fireChooseEvent(playerId, choice);
			waitForResponse();
			if (response.getString() != null) {
				choice.setChoice(response.getString());
				return true;
			} else if (!choice.isRequired()) {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean choose(Outcome outcome, Target target, Game game) {
		 return choose(outcome, target, game, null);
	}

	@Override
	public boolean choose(Outcome outcome, Target target, Game game, Map<String, Serializable> options) {
		game.getState().setPriorityPlayerId(getId());
		while (!abort) {
			game.fireSelectTargetEvent(playerId, target.getMessage(), target.possibleTargets(null, playerId, game), target.isRequired(), options);
			waitForResponse();
			if (response.getUUID() != null) {
				if (target instanceof TargetPermanent) {
					if (((TargetPermanent)target).canTarget(playerId, response.getUUID(), null, game)) {
						target.add(response.getUUID(), game);
						return true;
					}
				}
				else if (target.canTarget(response.getUUID(), game)) {
					target.add(response.getUUID(), game);
					return true;
				}
			} else if (!target.isRequired()) {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
		game.getState().setPriorityPlayerId(getId());
		while (!abort) {
			game.fireSelectTargetEvent(playerId, target.getMessage(),
					target.possibleTargets(source==null?null:source.getId(), playerId, game),
					target.isRequired(), getOptions(target));
			waitForResponse();
			if (response.getUUID() != null) {
				if (target instanceof TargetPermanent) {
					if (((TargetPermanent)target).canTarget(playerId, response.getUUID(), source, game)) {
						target.addTarget(response.getUUID(), source, game);
						return true;
					}
				}
				else if (target.canTarget(response.getUUID(), source, game)) {
					target.addTarget(response.getUUID(), source, game);
					return true;
				}
			} else if (!target.isRequired()) {
				return false;
			}
		}
		return false;
	}

	private Map getOptions(Target target) {
		return target.getNumberOfTargets() != target.getMaxNumberOfTargets() ? staticOptions : null;
	}

	@Override
	public boolean choose(Outcome outcome, Cards cards, TargetCard target, Game game) {
		game.getState().setPriorityPlayerId(getId());
		while (!abort) {
			boolean required = target.isRequired();
			// if there is no cards to select from, then add possibility to cancel choosing action
			if (cards == null) {
				required = false;
			} else {
				int count = cards.count(target.getFilter(), game);
				if (count == 0) required = false;
			}
			game.fireSelectTargetEvent(playerId, target.getMessage(), cards, required, getOptions(target));
			waitForResponse();
			if (response.getUUID() != null) {
				if (target.canTarget(response.getUUID(), cards, game)) {
					target.add(response.getUUID(), game);
					return true;
				}
			} else if (!required) {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
		game.getState().setPriorityPlayerId(getId());
		while (!abort) {
			game.fireSelectTargetEvent(playerId, target.getMessage(), cards, target.isRequired(), null);
			waitForResponse();
			if (response.getUUID() != null) {
				if (target.canTarget(response.getUUID(), cards, game)) {
					target.addTarget(response.getUUID(), source, game);
					return true;
				}
			} else if (!target.isRequired()) {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean chooseTargetAmount(Outcome outcome, TargetAmount target, Ability source, Game game) {
		game.getState().setPriorityPlayerId(getId());
		while (!abort) {
			game.fireSelectTargetEvent(playerId, target.getMessage() + "\n Amount remaining:" + target.getAmountRemaining(), target.possibleTargets(source==null?null:source.getId(), playerId, game), target.isRequired(), null);
			waitForResponse();
			if (response.getUUID() != null) {
				if (target.canTarget(response.getUUID(), source, game)) {
					UUID targetId = response.getUUID();
					int amountSelected = getAmount(1, target.getAmountRemaining(), "Select amount", game);
					target.addTarget(targetId, amountSelected, source, game);
					return true;
				}
			} else if (!target.isRequired()) {
				return false;
			}
		}
		return false;
	}

	@Override
	public void priority(Game game) {
		passed = false;
		if (!abort) {
			if (passedTurn && game.getStack().isEmpty()) {
				pass();
				return;
			}
			game.firePriorityEvent(playerId);
			waitForResponse();
			if (response.getBoolean() != null) {
				pass();
			} else if (response.getInteger() != null) {
				pass();
				passedTurn = true;
			} else if (response.getString() != null && response.getString().equals("special")) {
				specialAction(game);
			} else if (response.getUUID() != null) {
				MageObject object = game.getObject(response.getUUID());
				if (object != null) {
					Map<UUID, ActivatedAbility> useableAbilities = null;
					switch (game.getZone(object.getId())) {
						case HAND:
							useableAbilities = getUseableAbilities(object.getAbilities().getActivatedAbilities(Zone.HAND), game);
							break;
						case BATTLEFIELD:
							useableAbilities = getUseableAbilities(object.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD), game);
							break;
						case GRAVEYARD:
							useableAbilities = getUseableAbilities(object.getAbilities().getActivatedAbilities(Zone.GRAVEYARD), game);
							playAsThoughInYourHand(game, object, useableAbilities);
							break;
						case LIBRARY:
							useableAbilities = getUseableAbilities(object.getAbilities().getActivatedAbilities(Zone.LIBRARY), game);
						    playAsThoughInYourHand(game, object, useableAbilities);
							break;
					}
					if (useableAbilities != null && useableAbilities.size() > 0) {
						activateAbility(useableAbilities, game);
					}
				}
			}
		}
	}

	// not sure it is the best to implement such stuff this way
	private void playAsThoughInYourHand(Game game, MageObject object, Map<UUID, ActivatedAbility> useableAbilities) {
		if (game.getContinuousEffects().asThough(object.getId(), Constants.AsThoughEffectType.CAST, game)) {
			for (Map.Entry<UUID, ActivatedAbility> entry : getUseableAbilities(object.getAbilities().getActivatedAbilities(Zone.HAND), game).entrySet()) {
				useableAbilities.put(entry.getKey(), entry.getValue());
			}
		}
	}

	@Override
	public TriggeredAbility chooseTriggeredAbility(TriggeredAbilities abilities, Game game) {
		game.getState().setPriorityPlayerId(getId());
		while (!abort) {
			game.fireSelectTargetEvent(playerId, "Pick triggered ability", abilities, true);
			waitForResponse();
			if (response.getUUID() != null) {
				for (TriggeredAbility ability: abilities) {
					if (ability.getId().equals(response.getUUID()))
						return ability;
				}
			}
		}
		return null;
	}

	@Override
	public boolean playMana(ManaCost unpaid, Game game) {
		game.getState().setPriorityPlayerId(getId());
		game.firePlayManaEvent(playerId, "Pay " + unpaid.getText());
		waitForResponse();
		if (response.getBoolean() != null) {
			return false;
		} else if (response.getUUID() != null) {
			playManaAbilities(game);
		} else if (response.getString() != null && response.getString().equals("special")) {
			if (unpaid instanceof ManaCostsImpl) {
				ManaCostsImpl<ManaCost> costs = (ManaCostsImpl<ManaCost>) unpaid;
				for (ManaCost cost : costs.getUnpaid()) {
					if (cost instanceof PhyrexianManaCost) {
						PhyrexianManaCost ph = (PhyrexianManaCost)cost;
						if (ph.canPay(null, playerId, game)) {
							((PhyrexianManaCost)cost).pay(null, game, null, playerId, false);
						}
						break;
					}
				}
			}
		} 
		return true;
	}

	@Override
	public boolean playXMana(VariableManaCost cost, ManaCosts<ManaCost> costs, Game game) {
		game.getState().setPriorityPlayerId(getId());
		game.firePlayXManaEvent(playerId, "Pay {X}: {X}=" + cost.getAmount());
		waitForResponse();
		if (response.getBoolean() != null) {
			if (!response.getBoolean())
				return false;
			cost.setPaid();
		} else if (response.getUUID() != null) {
			playManaAbilities(game);
		}
		return true;
	}

	protected void playManaAbilities(Game game) {
		game.getState().setPriorityPlayerId(getId());
		MageObject object = game.getObject(response.getUUID());
		if (object == null) return;
		Map<UUID, ActivatedAbility> useableAbilities;
		switch (game.getZone(object.getId())) {
			case HAND:
				useableAbilities = getUseableAbilities(object.getAbilities().getManaAbilities(Zone.HAND), game);
				if (useableAbilities.size() > 0) {
					activateAbility(useableAbilities, game);
				}
				break;
			case BATTLEFIELD:
				useableAbilities = getUseableAbilities(object.getAbilities().getManaAbilities(Zone.BATTLEFIELD), game);
				if (useableAbilities.size() > 0) {
					activateAbility(useableAbilities, game);
				}
				break;
			case GRAVEYARD:
				useableAbilities = getUseableAbilities(object.getAbilities().getManaAbilities(Zone.GRAVEYARD), game);
				if (useableAbilities.size() > 0) {
					activateAbility(useableAbilities, game);
				}
				break;
		}
	}

	@Override
	public void selectAttackers(Game game) {
		game.getState().setPriorityPlayerId(getId());
		while (!abort) {
			targetCombat.getTargets().clear();
			game.fireSelectEvent(playerId, "Select attackers");
			waitForResponse();
			if (response.getBoolean() != null) {
				return;
			} else if (response.getUUID() != null) {
				if (targetCombat.canTarget(playerId, response.getUUID(), null, game)) {
					selectDefender(game.getCombat().getDefenders(), response.getUUID(), game);
				}
			}
		}
	}

	protected boolean selectDefender(Set<UUID> defenders, UUID attackerId, Game game) {
		if (defenders.size() == 1) {
			declareAttacker(attackerId, defenders.iterator().next(), game);
			return true;
		}
		else {
			TargetDefender target = new TargetDefender(defenders, attackerId);
			if (chooseTarget(Outcome.Damage, target, null, game)) {
				declareAttacker(attackerId, response.getUUID(), game);
				return true;
			}
		}
		return false;
	}

	@Override
	public void selectBlockers(Game game) {
		game.getState().setPriorityPlayerId(getId());
		while (!abort) {
			targetCombat.getTargets().clear();
			game.fireSelectEvent(playerId, "Select blockers");
			waitForResponse();
			if (response.getBoolean() != null) {
				return;
			} else if (response.getUUID() != null) {
				if (targetCombat.canTarget(playerId, response.getUUID(), null, game)) {
					selectCombatGroup(response.getUUID(), game);
				}
			}
		}
	}

	@Override
	public UUID chooseAttackerOrder(List<Permanent> attackers, Game game) {
		game.getState().setPriorityPlayerId(getId());
		while (!abort) {
			game.fireSelectTargetEvent(playerId, "Pick attacker", attackers, true);
			waitForResponse();
			if (response.getUUID() != null) {
				for (Permanent perm: attackers) {
					if (perm.getId().equals(response.getUUID()))
						return perm.getId();
				}
			}
		}
		return null;
	}


	@Override
	public UUID chooseBlockerOrder(List<Permanent> blockers, Game game) {
		game.getState().setPriorityPlayerId(getId());
		while (!abort) {
			game.fireSelectTargetEvent(playerId, "Pick blocker", blockers, true);
			waitForResponse();
			if (response.getUUID() != null) {
				for (Permanent perm: blockers) {
					if (perm.getId().equals(response.getUUID()))
						return perm.getId();
				}
			}
		}
		return null;
	}

	protected void selectCombatGroup(UUID blockerId, Game game) {
		game.getState().setPriorityPlayerId(getId());
		TargetAttackingCreature target = new TargetAttackingCreature();
		game.fireSelectTargetEvent(playerId, "Select attacker to block", target.possibleTargets(null, playerId, game), target.isRequired(), null);
		waitForResponse();
		if (response.getBoolean() != null) {
			return;
		} else if (response.getUUID() != null) {
			declareBlocker(blockerId, response.getUUID(), game);
		}
	}

	@Override
	public void assignDamage(int damage, List<UUID> targets, String singleTargetName, UUID sourceId, Game game) {
		game.getState().setPriorityPlayerId(getId());
		int remainingDamage = damage;
		while (remainingDamage > 0) {
			Target target = new TargetCreatureOrPlayer();
			if (singleTargetName != null) target.setTargetName(singleTargetName);
			choose(Outcome.Damage, target, game);
			if (targets.isEmpty() || targets.contains(target.getFirstTarget())) {
				int damageAmount = getAmount(0, remainingDamage, "Select amount", game);
				Permanent permanent = game.getPermanent(target.getFirstTarget());
				if (permanent != null) {
					permanent.damage(damageAmount, sourceId, game, true, false);
					remainingDamage -= damageAmount;
				}
				else {
					Player player = game.getPlayer(target.getFirstTarget());
					if (player != null) {
						player.damage(damageAmount, sourceId, game, false, true);
						remainingDamage -= damageAmount;
					}
				}
			}
		}
	}

	@Override
	public int getAmount(int min, int max, String message, Game game) {
		game.getState().setPriorityPlayerId(getId());
		game.fireGetAmountEvent(playerId, message, min, max);
		waitForIntegerResponse();
		return response.getInteger();
	}

	@Override
	public void sideboard(Match match, Deck deck) {
		match.fireSideboardEvent(playerId, deck);
	}

	@Override
	public void construct(Tournament tournament, Deck deck) {
		tournament.fireConstructEvent(playerId, deck);
	}

	@Override
	public void pickCard(List<Card> cards, Deck deck, Draft draft) {
		draft.firePickCardEvent(playerId);
	}

	protected void specialAction(Game game) {
		Map<UUID, SpecialAction> specialActions = game.getState().getSpecialActions().getControlledBy(playerId);
		game.fireGetChoiceEvent(playerId, name, specialActions.values());
		waitForResponse();
		if (response.getUUID() != null) {
			if (specialActions.containsKey(response.getUUID()))
				activateAbility(specialActions.get(response.getUUID()), game);
		}
	}

	protected void activateAbility(Map<UUID, ? extends ActivatedAbility> abilities, Game game) {
		game.getState().setPriorityPlayerId(getId());
		if (abilities.size() == 1) {
			ActivatedAbility ability = abilities.values().iterator().next();
			if (ability.getTargets().size() != 0 || !(ability.getCosts().size() == 1 && ability.getCosts().get(0) instanceof SacrificeSourceCost)) {
				activateAbility(ability, game);
				return;
			}
		}
		game.fireGetChoiceEvent(playerId, name, abilities.values());
		waitForResponse();
		if (response.getUUID() != null) {
			if (abilities.containsKey(response.getUUID()))
				activateAbility(abilities.get(response.getUUID()), game);
		}
	}
	
	@Override
	public Mode chooseMode(Modes modes, Ability source, Game game) {
		game.getState().setPriorityPlayerId(getId());
		if (modes.size() > 1) {
			MageObject obj = game.getObject(source.getSourceId());
			Map<UUID, String> modeMap = new HashMap<UUID, String>();
			for (Mode mode: modes.values()) {
				String modeText = mode.getEffects().getText(mode);
				if (obj != null)
					modeText = modeText.replace("{source}", obj.getName());
				modeMap.put(mode.getId(), modeText);
			}
			game.fireGetModeEvent(playerId, "Choose Mode", modeMap);
			waitForResponse();
			if (response.getUUID() != null) {
				for (Mode mode: modes.values()) {
					if (mode.getId().equals(response.getUUID()))
						return mode;
				}
			}
			return null;
		}
		return modes.getMode();
	}

	@Override
	public void setResponseString(String responseString) {
		synchronized(response) {
			response.setString(responseString);
			response.notify();
		}
	}

	@Override
	public void setResponseUUID(UUID responseUUID) {
		synchronized(response) {
			response.setUUID(responseUUID);
			response.notify();
		}
	}

	@Override
	public void setResponseBoolean(Boolean responseBoolean) {
		synchronized(response) {
			response.setBoolean(responseBoolean);
			response.notify();
		}
	}

	@Override
	public void setResponseInteger(Integer responseInteger) {
		synchronized(response) {
			response.setInteger(responseInteger);
			response.notify();
		}
	}

	@Override
	public void abort() {
		abort = true;
		synchronized(response) {
			response.notify();
		}
	}

	@Override
	public HumanPlayer copy() {
		return new HumanPlayer(this);
	}

}
