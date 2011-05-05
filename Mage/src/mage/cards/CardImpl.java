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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageObjectImpl;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.PlayLandAbility;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.mana.ManaAbility;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.Spell;
import mage.watchers.Watchers;
import org.apache.log4j.Logger;

public abstract class CardImpl<T extends CardImpl<T>> extends MageObjectImpl<T> implements Card {

	private final static Logger logger = Logger.getLogger(CardImpl.class);

	protected UUID ownerId;
	protected int cardNumber;
	protected Watchers watchers = new Watchers();
	protected String expansionSetCode;
	protected Rarity rarity;

	public CardImpl(UUID ownerId, int cardNumber, String name, Rarity rarity, CardType[] cardTypes, String costs) {
		this(ownerId, name);
		this.rarity = rarity;
		this.cardNumber = cardNumber;
		this.cardType.addAll(Arrays.asList(cardTypes));
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

	@Override
	public void assignNewId() {
		this.objectId = UUID.randomUUID();
		this.abilities.setSourceId(objectId);
	}

	public static Card createCard(String name) {
		try {
			Class<?> theClass  = Class.forName(name);
			Constructor<?> con = theClass.getConstructor(new Class[]{UUID.class});
			Card card = (Card) con.newInstance(new Object[] {null});
			return card;
		}
		catch (Exception e) {
			logger.fatal("Error loading card: " + name, e);
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
		List<String> rules = abilities.getRules(this.name);
		if (cardType.contains(CardType.INSTANT) || cardType.contains(CardType.SORCERY)) {
			rules.add(0, getSpellAbility().getRule(this.name));
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
			if (ability.checkTrigger(event, game)) {
				ability.trigger(game, ownerId);
			}
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
	public List<Mana> getMana() {
		List<Mana> mana = new ArrayList<Mana>();
		for (ManaAbility ability: this.abilities.getManaAbilities(Zone.BATTLEFIELD)) {
			mana.add(ability.getNetMana(null));
		}
		return mana;
	}

	@Override
	public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag) {
		Zone fromZone = game.getZone(objectId);
		ZoneChangeEvent event = new ZoneChangeEvent(this.objectId, sourceId, ownerId, fromZone, toZone);
		if (!game.replaceEvent(event)) {
            if (event.getFromZone() != null) {
                switch (event.getFromZone()) {
                    case GRAVEYARD:
                        game.getPlayer(ownerId).removeFromGraveyard(this, game);
                        break;
                    default:
                        //logger.warning("moveToZone, not fully implemented: from="+event.getFromZone() + ", to="+event.getToZone());
                }
	            game.rememberLKI(objectId, event.getFromZone(), this);
            }
			switch (event.getToZone()) {
				case GRAVEYARD:
					game.getPlayer(ownerId).putInGraveyard(this, game, !flag);
					break;
				case HAND:
					game.getPlayer(ownerId).getHand().add(this);
					break;
				case STACK:
					game.getStack().push(new Spell(this, this.getSpellAbility().copy(), ownerId));
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
					permanent.entersBattlefield(sourceId, game);
					game.applyEffects();
					if (flag)
						permanent.setTapped(true);
					break;
			}
			game.setZone(objectId, event.getToZone());
			game.fireEvent(event);
			return game.getZone(objectId) == toZone;
		}
		return false;
	}

	@Override
	public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
		ZoneChangeEvent event = new ZoneChangeEvent(this.objectId, ability.getId(), controllerId, fromZone, Zone.STACK);
		if (!game.replaceEvent(event)) {
			if (event.getFromZone() != null) {
				switch (event.getFromZone()) {
					case GRAVEYARD:
						game.getPlayer(ownerId).removeFromGraveyard(this, game);
						break;
					default:
						//logger.warning("moveToZone, not fully implemented: from="+event.getFromZone() + ", to="+event.getToZone());
				}
				game.rememberLKI(objectId, event.getFromZone(), this);
			}
			game.getStack().push(new Spell(this, ability.copy(), controllerId));
			game.setZone(objectId, event.getToZone());
			game.fireEvent(event);
			return game.getZone(objectId) == Zone.STACK;
		}
		return false;
	}

	@Override
	public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game) {
		Zone fromZone = game.getZone(objectId);
		ZoneChangeEvent event = new ZoneChangeEvent(this.objectId, sourceId, ownerId, fromZone, Zone.EXILED);
		if (!game.replaceEvent(event)) {
			if (exileId == null) {
				game.getExile().getPermanentExile().add(this);
			}
			else {
				game.getExile().createZone(exileId, name).add(this);
			}
			game.setZone(objectId, event.getToZone());
			game.fireEvent(event);
			return true;
		}
		return false;
	}

	@Override
	public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId) {
		PermanentCard permanent = new PermanentCard(this, controllerId);
		game.getBattlefield().addPermanent(permanent);
		game.setZone(objectId, Zone.BATTLEFIELD);
		game.applyEffects();
		permanent.entersBattlefield(sourceId, game);
		game.fireEvent(new ZoneChangeEvent(permanent, controllerId, fromZone, Zone.BATTLEFIELD));
		return true;
	}

    @Override
    public String getArt() {
        return "";
    }

    @Override
    public void setCardNumber(int cid) {
        this.cardNumber = cid;
    }
}
