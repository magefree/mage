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

package mage.cards;

import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Zone;
import mage.MageObjectImpl;
import mage.abilities.Ability;
import mage.abilities.PlayLandAbility;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbility;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.Copier;
import mage.watchers.Watchers;

public abstract class CardImpl extends MageObjectImpl implements Card {

	private static final transient Copier<Card> copier = new Copier<Card>();

	protected UUID ownerId;
	protected String art = "";
	protected Watchers watchers = new Watchers();
	protected UUID expansionSetId;
	
	public CardImpl(UUID ownerId, String name, CardType[] cardTypes, String costs) {
		this.ownerId = ownerId;
		this.name = name;
		for (CardType newCardType: cardTypes)
			this.cardType.add(newCardType);
		this.manaCost.load(costs);
		if (cardType.contains(CardType.LAND))
			addAbility(new PlayLandAbility());
		else
			addAbility(new SpellAbility(manaCost));
	}

	protected CardImpl(UUID ownerId, String name) {
		this.ownerId = ownerId;
		this.name = name;
	}

	@Override
	public UUID getOwnerId() {
		return ownerId;
	}

	@Override
	public String getArt() {
		return art;
	}

	@Override
	public List<String> getRules() {
		List<String> rules = abilities.getRules();
		if (cardType.contains(CardType.INSTANT) || cardType.contains(CardType.SORCERY)) {
			rules.add(0, getSpellAbility().getRule());
		}
		return rules;
	}

	@Override
	public void addAbility(Ability ability) {
		ability.setSourceId(this.getId());
		abilities.add(ability);
	}
	
	@Override
	public SpellAbility getSpellAbility() {
		for (Ability ability: abilities.getActivatedAbilities(Zone.HAND)) {
			if (ability instanceof SpellAbility)
				return (SpellAbility)ability;
		}
		return null;
	}

	@Override
	public void setControllerId(UUID controllerId) {
		abilities.setControllerId(controllerId);
	}

	@Override
	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
		abilities.setControllerId(ownerId);
	}

	@Override
	public Card copy() {
		return copier.copy(this);
	}

	@Override
	public Watchers getWatchers() {
		return watchers;
	}

	@Override
	public void checkTriggers(Zone zone, GameEvent event, Game game) {
		for (TriggeredAbility ability: abilities.getTriggeredAbilities(zone)) {
			ability.checkTrigger(event, game);
		}
	}

	@Override
	public UUID getExpansionSetId() {
		return expansionSetId;
	}
}
