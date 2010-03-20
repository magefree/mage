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

package mage.human;

import java.util.List;
import mage.abilities.effects.ReplacementEffect;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.players.*;
import java.util.Map;
import java.util.UUID;
import mage.Constants.Outcome;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.ActivatedAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.filter.common.FilterCreatureForCombat;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetDefender;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class HumanPlayer extends PlayerImpl {

	final PlayerResponse response = new PlayerResponse();

	private boolean abort = false;

	protected TargetPermanent targetCombat = new TargetPermanent(new FilterCreatureForCombat(), TargetController.YOU);

	public HumanPlayer(String name) {
		super(name);
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
		game.fireAskPlayerEvent(playerId, "Do you want to take a mulligan?");
		waitForBooleanResponse();
		if (!abort)
			return response.getBoolean();
		return false;
	}

	@Override
	public boolean chooseUse(Outcome outcome, String message, Game game) {
		game.fireAskPlayerEvent(playerId, message);
		waitForBooleanResponse();
		if (!abort)
			return response.getBoolean();
		return false;
	}

	@Override
	public int chooseEffect(List<ReplacementEffect> rEffects, Game game) {
		//TODO: implement this
		return 0;
	}

	@Override
	public boolean choose(Outcome outcome, Choice choice, Game game) {
		game.fireChooseEvent(playerId, choice);
		while (!abort) {
			waitForStringResponse();
			choice.setChoice(response.getString());
			return true;
		}
		return false;
	}

	@Override
	public boolean chooseTarget(Outcome outcome, Target target, Game game) {
		game.fireSelectTargetEvent(playerId, target.getMessage(), target.isRequired());
		while (!abort) {
			waitForResponse();
			if (response.getUUID() != null) {
				if (target.canTarget(response.getUUID(), game)) {
					target.addTarget(response.getUUID(), game);
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
				passed = true;
				return;
			}
			game.firePriorityEvent(playerId);
			waitForResponse();
			if (response.getBoolean() != null) {
				passed = true;
			} else if (response.getInteger() != null) {
				passed = true;
				passedTurn = true;
			} else if (response.getUUID() != null) {
				MageObject object = game.getObject(response.getUUID());
				if (object != null) {
					Map<UUID, ActivatedAbility> useableAbilities = null;
					switch (object.getZone()) {
						case HAND:
							useableAbilities = getUseableAbilities(object.getAbilities().getActivatedAbilities(Zone.HAND), game);
							break;
						case BATTLEFIELD:
							useableAbilities = getUseableAbilities(object.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD), game);
							break;
						case GRAVEYARD:
							useableAbilities = getUseableAbilities(object.getAbilities().getActivatedAbilities(Zone.GRAVEYARD), game);
							break;
					}
					if (useableAbilities != null && useableAbilities.size() > 0) {
						activateAbility(useableAbilities, game);
					}
				}
			}
		}
	}

	@Override
	public boolean searchCards(Cards cards, TargetCard target, Game game) {
		while (!abort && !target.doneChosing()) {
			game.fireSelectTargetEvent(playerId, target.getMessage(), cards, target.isRequired());
			waitForResponse();
			if (response.getUUID() != null && cards.containsKey(response.getUUID())) {
				target.addTarget(response.getUUID(), game);
			} else if (!target.isRequired()) {
				break;
			}
		}
		return target.isChosen();
	}

	@Override
	public boolean playMana(ManaCost unpaid, Game game) {
		game.firePlayManaEvent(playerId, "Pay " + unpaid.getText());
		waitForResponse();
		if (response.getBoolean() != null) {
			return false;
		} else if (response.getUUID() != null) {
			playManaAbilities(game);
		}
		return true;
	}

	public boolean playXMana(VariableManaCost cost, Game game) {
		game.firePlayXManaEvent(playerId, "Pay {X}: {X}=" + cost.getValue());
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
		MageObject object = game.getObject(response.getUUID());
		Map<UUID, ActivatedAbility> useableAbilities;
		switch (object.getZone()) {
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
		while (!abort) {
			targetCombat.getTargets().clear();
			game.fireSelectEvent(playerId, "Select attackers");
			waitForResponse();
			if (response.getBoolean() != null) {
				return;
			} else if (response.getUUID() != null) {
				if (targetCombat.canTarget(playerId, response.getUUID(), game)) {
					selectDefender(game.getCombat().getDefenders(), response.getUUID(), game);
				}
			}
		}
	}

	protected boolean selectDefender(List<UUID> defenders, UUID attackerId, Game game) {
		TargetDefender target = new TargetDefender(defenders);
		if (chooseTarget(Outcome.Damage, target, game)) {
			declareAttacker(attackerId, response.getUUID(), game);
			return true;
		}
		return false;
	}

	@Override
	public void selectBlockers(Game game) {
		while (!abort) {
			targetCombat.getTargets().clear();
			game.fireSelectEvent(playerId, "Select blockers");
			waitForResponse();
			if (response.getBoolean() != null) {
				return;
			} else if (response.getUUID() != null) {
				if (targetCombat.canTarget(playerId, response.getUUID(), game)) {
					selectCombatGroup(response.getUUID(), game);
				}
			}
		}
	}

	protected void selectCombatGroup(UUID blockerId, Game game) {
		TargetAttackingCreature target = new TargetAttackingCreature();
		game.fireSelectTargetEvent(playerId, "Select attacker to block", target.isRequired());
		waitForResponse();
		if (response.getBoolean() != null) {
			return;
		} else if (response.getUUID() != null) {
			declareBlocker(blockerId, response.getUUID(), game);
		}
	}

	@Override
	public void assignDamage(int damage, List<UUID> targets, UUID sourceId, Game game) {
		int remainingDamage = damage;
		while (remainingDamage > 0) {
			Target target = new TargetCreatureOrPlayer();
			chooseTarget(Outcome.Damage, target, game);
			if (targets.size() == 0 || targets.contains(target.getFirstTarget())) {
				int damageAmount = getAmount(0, remainingDamage, "Select amount", game);
				Permanent permanent = game.getPermanent(target.getFirstTarget());
				if (permanent != null) {
					permanent.damage(damageAmount, sourceId, game);
				}
				else {
					Player player = game.getPlayer(target.getFirstTarget());
					if (player != null) {
						player.damage(damageAmount, sourceId, game);
					}
				}
			}
		}
	}

	@Override
	public int getAmount(int min, int max, String message, Game game) {
		game.fireGetAmountEvent(playerId, message, min, max);
		waitForIntegerResponse();
		return response.getInteger();
	}

	protected void activateAbility(Map<UUID, ? extends ActivatedAbility> abilities, Game game) {
		if (abilities.size() == 1) {
			activateAbility(abilities.values().iterator().next(), game);
		}
		else {
			game.fireGetChoiceEvent(playerId, name, abilities.values());
			waitForUUIDResponse();
			if (abilities.containsKey(response.getUUID()))
				activateAbility(abilities.get(response.getUUID()), game);
		}
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

}
