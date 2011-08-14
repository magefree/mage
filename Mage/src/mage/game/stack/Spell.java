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

package mage.game.stack;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.Effect;
import mage.abilities.effects.PostResolveEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.Card;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.Target;
import mage.watchers.Watchers;

import java.util.List;
import java.util.UUID;
import mage.watchers.Watcher;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Spell<T extends Spell<T>> implements StackObject, Card {

	private Card card;
	private SpellAbility ability;
	private UUID controllerId;
	private boolean copiedSpell;

	public Spell(Card card, SpellAbility ability, UUID controllerId) {
		this.card = card;
		this.ability = ability;
		this.controllerId = controllerId;
	}

	public Spell(final Spell<T> spell) {
		this.card = spell.card.copy();
		this.ability = spell.ability.copy();
		this.controllerId = spell.controllerId;
	}

	@Override
	public boolean resolve(Game game) {
		boolean result = false;
		if (card.getCardType().contains(CardType.INSTANT) || card.getCardType().contains(CardType.SORCERY)) {
			if (ability.getTargets().stillLegal(ability, game)) {
				boolean replaced = resolveKicker(game);
				if (!replaced)
					result = ability.resolve(game);
				else
					result = true;

				if (!copiedSpell) {
					for (Effect effect: ability.getEffects()) {
						if (effect instanceof PostResolveEffect) {
							((PostResolveEffect)effect).postResolve(card, ability, controllerId, game);
							return result;
						}
					}
					card.moveToZone(Zone.GRAVEYARD, ability.getId(), game, false);
				}

				return result;
			}
			//20091005 - 608.2b
			counter(null, game);
			return false;
		}
		else if (card.getCardType().contains(CardType.ENCHANTMENT) && card.getSubtype().contains("Aura")) {
			if (ability.getTargets().stillLegal(ability, game)) {
				if (card.putOntoBattlefield(game, Zone.HAND, ability.getId(), controllerId)) {
					return ability.resolve(game);
				}
				return false;
			}
			//20091005 - 608.2b
			counter(null, game);
			return false;
		}
		else {
			resolveKicker(game);
			result = card.putOntoBattlefield(game, Zone.HAND, ability.getId(), controllerId);
			return result;
		}
	}

	protected boolean resolveKicker(Game game) {
		boolean replaced = false;
		for (KickerAbility kicker: card.getAbilities().getKickerAbilities()) {
			if (kicker.isKicked()) {
				if (kicker.isReplaces()) {
					replaced = true;
				}
				kicker.resolve(game);
			}
		}
		return replaced;
	}

	/**
	 * Choose new targets for the spell
	 *
	 * @param game
	 * @param playerId Player UUID who changes the targets.
	 * @return
	 */
	public boolean chooseNewTargets(Game game, UUID playerId) {
		Player player = game.getPlayer(playerId);
		if (player != null) {
			for (Target target: ability.getTargets()) {
				Target newTarget = target.copy();
				newTarget.clearChosen();
				for (UUID targetId: target.getTargets()) {
					MageObject object = game.getObject(targetId);
					String name = null;
					if (object == null) {
						Player targetPlayer = game.getPlayer(targetId);
						if (player != null) name = targetPlayer.getName();
					} else {
						name = object.getName();
					}
					if (name != null && player.chooseUse(ability.getEffects().get(0).getOutcome(), "Change target from " + name + "?", game)) {
						if (!player.chooseTarget(ability.getEffects().get(0).getOutcome(), newTarget, ability, game))
							newTarget.addTarget(targetId, ability, game);
					}
					else {
						newTarget.addTarget(targetId, ability, game);
					}
				}
				target.clearChosen();
				for (UUID newTargetId: newTarget.getTargets()) {
					target.addTarget(newTargetId, ability, game);
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public void counter(UUID sourceId, Game game) {
		card.moveToZone(Zone.GRAVEYARD, sourceId, game, false);
	}

	@Override
	public UUID getSourceId() {
		return card.getId();
	}

	@Override
	public UUID getControllerId() {
		return this.controllerId;
	}

	@Override
	public String getName() {
		return card.getName();
	}

	@Override
	public void setName(String name) {}

	@Override
	public Rarity getRarity() {
		return card.getRarity();
	}

	@Override
	public void setRarity(Rarity rarity) {}

	@Override
	public List<CardType> getCardType() {
		return card.getCardType();
	}

	@Override
	public List<String> getSubtype() {
		return card.getSubtype();
	}

	@Override
	public boolean hasSubtype(String subtype) {
		return card.hasSubtype(subtype);
	}

	@Override
	public List<String> getSupertype() {
		return card.getSupertype();
	}

	@Override
	public Abilities<Ability> getAbilities() {
		return card.getAbilities();
	}

	@Override
	public ObjectColor getColor() {
		return card.getColor();
	}

	@Override
	public ManaCosts<ManaCost> getManaCost() {
		return card.getManaCost();
	}

	@Override
	public MageInt getPower() {
		return card.getPower();
	}

	@Override
	public MageInt getToughness() {
		return card.getToughness();
	}

	@Override
	public UUID getId() {
		return ability.getId();
	}

	@Override
	public UUID getOwnerId() {
		return card.getOwnerId();
	}

	@Override
	public void addAbility(Ability ability) {}

	@Override
	public void addWatcher(Watcher watcher) {}

    @Override
	public SpellAbility getSpellAbility() {
		return ability;
	}

	@Override
	public void setControllerId(UUID controllerId) {
		this.controllerId = controllerId;
	}

	@Override
	public void setOwnerId(UUID controllerId) {}

	@Override
	public void checkTriggers(GameEvent event, Game game) {
		checkTriggers(Zone.STACK, event, game);
	}

	@Override
	public void checkTriggers(Zone zone, GameEvent event, Game game) {
		card.checkTriggers(zone, event, game);
	}

	@Override
	public List<String> getRules() {
		return card.getRules();
	}

	@Override
	public Watchers getWatchers() {
		return card.getWatchers();
	}

	@Override
	public String getExpansionSetCode() {
		return card.getExpansionSetCode();
	}

	@Override
	public void setExpansionSetCode(String expansionSetCode) {}

	@Override
	public void setFaceDown(boolean value) {
		throw new RuntimeException("Not implemented.");
	}

	@Override
	public boolean isFaceDown() {
		return false;
	}

	@Override
	public Spell copy() {
		return new Spell(this);
	}

	public Spell copySpell() {
		return new Spell(this.card.copy(), this.ability.copySpell(), this.controllerId);
	}
	
	@Override
	public void adjustCosts(Ability ability, Game game) {}

	@Override
	public boolean moveToZone(Zone zone, UUID sourceId, Game game, boolean flag) {
		throw new UnsupportedOperationException("Unsupported operation");
	}

	@Override
	public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game) {
		throw new UnsupportedOperationException("Unsupported operation");
	}

	@Override
	public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId) {
		throw new UnsupportedOperationException("Unsupported operation");
	}

	@Override
	public int getCardNumber() {
		return card.getCardNumber();
	}

    @Override
    public void setCardNumber(int cid) {
        card.setCardNumber(cid);
    }

    @Override
	public List<Mana> getMana() {
		return card.getMana();
	}

	@Override
	public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
		throw new UnsupportedOperationException("Unsupported operation");
	}

	@Override
	public Ability getStackAbility() {
		return this.ability;
	}

	@Override
	public void assignNewId() {
		throw new UnsupportedOperationException("Unsupported operation");
	}

	public void setCopiedSpell(boolean isCopied) {
		this.copiedSpell = isCopied;
	}
}

