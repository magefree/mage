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

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageObjectImpl;
import mage.abilities.Ability;
import mage.abilities.PlayLandAbility;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbility;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentCard;
import mage.util.Logging;
import mage.watchers.Watchers;

public abstract class CardImpl<T extends CardImpl<T>> extends MageObjectImpl<T> implements Card {

	private final static Logger logger = Logging.getLogger(CardImpl.class.getName());

	protected UUID ownerId;
	protected int cardNumber;
	protected Watchers watchers = new Watchers();
	protected String expansionSetCode;
	protected Rarity rarity;

	public CardImpl(UUID ownerId, int cardNumber, String name, Rarity rarity, CardType[] cardTypes, String costs) {
		this(ownerId, name);
		this.rarity = rarity;
		this.cardNumber = cardNumber;
		for (CardType newCardType: cardTypes)
			this.cardType.add(newCardType);
		this.manaCost.load(costs);
		if (cardType.contains(CardType.LAND))
			addAbility(new PlayLandAbility(name));
		else
			addAbility(new SpellAbility(manaCost, name));
	}

	protected CardImpl(UUID ownerId, String name) {
		this.ownerId = ownerId;
		this.name = name;
	}

	protected CardImpl(UUID id, UUID ownerId, String name) {
		super(id);
		this.ownerId = ownerId;
		this.name = name;
	}

	public CardImpl(final CardImpl card) {
		super(card);
		ownerId = card.ownerId;
		cardNumber = card.cardNumber;
		expansionSetCode = card.expansionSetCode;
		rarity = card.rarity;
		watchers = card.watchers.copy();
	}

	public static Card createCard(String name) {
		try {
			Class<?> theClass  = Class.forName(name);
			Constructor<?> con = theClass.getConstructor(new Class[]{UUID.class});
			Card card = (Card) con.newInstance(new Object[] {null});
			card.setZone(Zone.OUTSIDE);
			return card;
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "Error loading card: " + name, e);
			return null;
		}
	}

	@Override
	public UUID getOwnerId() {
		return ownerId;
	}

	@Override
	public int getCardNumber() {
		return cardNumber;
	}

	@Override
	public Rarity getRarity() {
		return rarity;
	}

	@Override
	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
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
	public String getExpansionSetCode() {
		return expansionSetCode;
	}

	@Override
	public void setExpansionSetCode(String expansionSetCode) {
		this.expansionSetCode = expansionSetCode;
	}

	@Override
	public boolean moveToZone(Zone toZone, Game game, boolean flag) {
		Zone fromZone = zone;
		ZoneChangeEvent event = new ZoneChangeEvent(this.getId(), ownerId, fromZone, toZone);
		if (!game.replaceEvent(event)) {
			switch (event.getToZone()) {
				case GRAVEYARD:
					game.getPlayer(ownerId).putInGraveyard(this, game, !flag);
					break;
				case HAND:
					game.getPlayer(ownerId).getHand().add(this);
					break;
				case EXILED:
					game.getExile().getPermanentExile().add(this);
					break;
				case LIBRARY:
					if (flag)
						game.getPlayer(ownerId).getLibrary().putOnTop(this, game);
					else
						game.getPlayer(ownerId).getLibrary().putOnBottom(this, game);
					break;
				case BATTLEFIELD:
					PermanentCard permanent = new PermanentCard(this, ownerId);
					game.getBattlefield().addPermanent(permanent);
					permanent.entersBattlefield(game);
					game.applyEffects();
					break;
			}
			zone = event.getToZone();
			game.fireEvent(new ZoneChangeEvent(this.getId(), ownerId, fromZone, event.getToZone()));
			return zone == toZone;
		}
		return false;
	}

	@Override
	public boolean moveToExile(UUID exileId, String name, Game game) {
		Zone fromZone = zone;
		ZoneChangeEvent event = new ZoneChangeEvent(this.getId(), ownerId, fromZone, Zone.EXILED);
		if (!game.replaceEvent(event)) {
			if (exileId == null) {
				game.getExile().getPermanentExile().add(this);
			}
			else {
				game.getExile().createZone(exileId, name).add(this);
			}
			zone = event.getToZone();
			game.fireEvent(new ZoneChangeEvent(this.getId(), ownerId, fromZone, Zone.EXILED));
			return true;
		}
		return false;
	}

	@Override
	public boolean putOntoBattlefield(Game game, Zone fromZone, UUID controllerId) {
		PermanentCard permanent = new PermanentCard(this, controllerId);
		game.getBattlefield().addPermanent(permanent);
		zone = Zone.BATTLEFIELD;
		permanent.entersBattlefield(game);
		game.applyEffects();
		game.fireEvent(new ZoneChangeEvent(permanent.getId(), controllerId, fromZone, Zone.BATTLEFIELD));
		return true;
	}

}
