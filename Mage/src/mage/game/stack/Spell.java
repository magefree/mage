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

import mage.abilities.SpellAbility;
import mage.game.*;
import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Zone;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.Card;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.Copier;
import mage.watchers.Watchers;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Spell implements StackObject, Card {

//	private static final transient Copier<Spell> copier = new Copier<Spell>();

	private Card card;
	private SpellAbility ability;
	private UUID controllerId;

	public Spell(Card card, SpellAbility ability, UUID controllerId) {
		this.card = card;
		this.ability = ability;
		this.controllerId = controllerId;
	}

	@Override
	public boolean resolve(Game game) {
		boolean result = false;
		if (card.getCardType().contains(CardType.INSTANT) || card.getCardType().contains(CardType.SORCERY)) {
			if (ability.getTargets().stillLegal(game)) {
				boolean replaced = resolveKicker(game);
				if (!replaced)
					result = ability.resolve(game);
				else
					result = true;

				if (ability.getEffects().contains(ExileSpellEffect.getInstance()))
					game.getExile().getPermanentExile().add(card);
				else
					game.getPlayers().get(controllerId).putInGraveyard(card, game, false);
				return result;
			}
			//20091005 - 608.2b
			counter(game);
			return false;
		}
		else if (card.getCardType().contains(CardType.ENCHANTMENT) && card.getSubtype().contains("Aura")) {
			if (ability.getTargets().stillLegal(game)) {
				Player controller = game.getPlayers().get(controllerId);
				if (controller.putOntoBattlefield(card, game)) {
					return ability.resolve(game);
				}
				return false;
			}
			//20091005 - 608.2b
			counter(game);
			return false;
		}
		else {
			Player controller = game.getPlayers().get(controllerId);
			result = controller.putOntoBattlefield(card, game);
			resolveKicker(game);
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

	@Override
	public void counter(Game game) {
		game.getPlayers().get(controllerId).putInGraveyard(card, game, false);
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
	public List<CardType> getCardType() {
		return card.getCardType();
	}

	@Override
	public List<String> getSubtype() {
		return card.getSubtype();
	}

	@Override
	public List<String> getSupertype() {
		return card.getSupertype();
	}

	@Override
	public Abilities getAbilities() {
		return card.getAbilities();
	}

	@Override
	public ObjectColor getColor() {
		return card.getColor();
	}

	@Override
	public ManaCosts getManaCost() {
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
	public MageInt getLoyalty() {
		return card.getLoyalty();
	}

	@Override
	public Zone getZone() {
		return Zone.STACK;
	}

	@Override
	public void setZone(Zone zone) {
		
	}

	@Override
	public UUID getId() {
		return card.getId();
	}

	@Override
	public UUID getOwnerId() {
		return card.getOwnerId();
	}

	@Override
	public String getArt() {
		return card.getArt();
	}

	@Override
	public void addAbility(Ability ability) {
		
	}

	@Override
	public SpellAbility getSpellAbility() {
		return card.getSpellAbility();
	}

	@Override
	public void setControllerId(UUID controllerId) {
		this.controllerId = controllerId;
	}

	@Override
	public void setOwnerId(UUID controllerId) {
		
	}

	@Override
	public Card copy() {
		return new Copier<Spell>().copy(this);
	}

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
	public UUID getExpansionSetId() {
		return card.getExpansionSetId();
	}

}
