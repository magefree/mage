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
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.Card;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.Copier;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Spell implements StackObject, Card {

	private Card card;
	private UUID controllerId;

	public Spell(Card card, UUID controllerId) {
		this.card = card;
		this.controllerId = controllerId;
	}

	public boolean resolve(Game game) {
		boolean result = false;
		if (card.getCardType().contains(CardType.INSTANT) || card.getCardType().contains(CardType.SORCERY)) {
			SpellAbility ability = card.getSpellAbility();
			if (ability.getTargets().stillLegal(game)) {
				boolean replaced = false;
				for (KickerAbility kicker: card.getAbilities().getKickerAbilities()) {
					if (kicker.isKicked()) {
						if (kicker.isReplaces()) {
							replaced = true;
						}
						kicker.resolve(game);
					}
				}
				if (!replaced)
					result = ability.resolve(game);

				if (ability.getEffects().contains(ExileSourceEffect.getInstance()))
					game.getExile().getPermanentExile().add(card);
				else
					game.getPlayers().get(controllerId).putInGraveyard(card, game, false);
				return result;
			}
			//20091005 - 608.2b
			counter(game);
			return false;
		}
		else {
			Player controller = game.getPlayers().get(controllerId);
			return controller.putOntoBattlefield(card, game);
		}
	}

	public void counter(Game game) {
		game.getPlayers().get(controllerId).putInGraveyard(card, game, false);
	}

	public UUID getSourceId() {
		return card.getId();
	}

	public UUID getControllerId() {
		return this.controllerId;
	}

	public String getName() {
		return card.getName();
	}

	public List<CardType> getCardType() {
		return card.getCardType();
	}

	public List<String> getSubtype() {
		return card.getSubtype();
	}

	public List<String> getSupertype() {
		return card.getSupertype();
	}

	public Abilities getAbilities() {
		return card.getAbilities();
	}

	public ObjectColor getColor() {
		return card.getColor();
	}

	public ManaCosts getManaCost() {
		return card.getManaCost();
	}

	public MageInt getPower() {
		return card.getPower();
	}

	public MageInt getToughness() {
		return card.getToughness();
	}

	public MageInt getLoyalty() {
		return card.getLoyalty();
	}

	public Zone getZone() {
		return Zone.STACK;
	}

	public void setZone(Zone zone) {
		
	}

	public UUID getId() {
		return card.getId();
	}

	public UUID getOwnerId() {
		return card.getOwnerId();
	}

	public String getArt() {
		return card.getArt();
	}

	public void addAbility(Ability ability) {
		
	}

	public SpellAbility getSpellAbility() {
		return card.getSpellAbility();
	}

	public void setControllerId(UUID controllerId) {
		this.controllerId = controllerId;
	}

	public void setOwnerId(UUID controllerId) {
		
	}

	public Card copy() {
		return new Copier<Spell>().copy(this);
	}

	public void handleEvent(GameEvent event, Game game) {
		handleEvent(Zone.STACK, event, game);
	}

	public void handleEvent(Zone zone, GameEvent event, Game game) {
		card.handleEvent(zone, event, game);
	}

	public List<String> getRules() {
		return card.getRules();
	}

}
