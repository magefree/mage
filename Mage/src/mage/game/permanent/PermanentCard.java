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

package mage.game.permanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.keyword.LevelAbility;
import mage.cards.Card;
import mage.cards.LevelerCard;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PermanentCard extends PermanentImpl<PermanentCard> {

	protected String art;
	protected List<String> levelerRules;

	public PermanentCard(Card card, UUID controllerId) {
		super(card.getId(), card.getOwnerId(), controllerId, card.getName());
		init(card);
	}

	protected PermanentCard(UUID id, Card card, UUID controllerId) {
		super(card.getId(), card.getOwnerId(), controllerId, card.getName());
		init(card);
	}

	protected void init(Card card) {
		copyFromCard(card);
		if (card.getCardType().contains(CardType.PLANESWALKER)) {
			this.loyalty = new MageInt(card.getLoyalty().getValue());
		}
		if (card instanceof LevelerCard) {
			levelerRules = ((LevelerCard)card).getRules();
		}
	}

	public PermanentCard(final PermanentCard permanent) {
		super(permanent);
		this.art = permanent.art;
	}

	@Override
	public void reset(Game game) {
		// when the permanent is reset copy all original values from the card
		// must copy card each reset so that the original values don't get modified
		Card copy = game.getCard(objectId).copy();
		copyFromCard(copy);
		super.reset(game);
	}

	protected void copyFromCard(Card card) {
		this.name = card.getName();
		this.abilities = card.getAbilities();
		this.abilities.setControllerId(this.controllerId);
		this.cardType = card.getCardType();
		this.color = card.getColor();
		this.manaCost = card.getManaCost();
		this.power = card.getPower();
		this.toughness = card.getToughness();
		if (card instanceof LevelerCard) {
			LevelAbility level = ((LevelerCard)card).getLevel(this.getCounters().getCount("Level"));
			if (level != null) {
				this.power.setValue(level.getPower());
				this.toughness.setValue(level.getToughness());
				for (Ability ability: level.getAbilities()) {
					this.addAbility(ability);
				}
			}
		}
		this.subtype = card.getSubtype();
		this.supertype = card.getSupertype();
		this.art = card.getArt();
		this.expansionSetCode = card.getExpansionSetCode();
		this.rarity = card.getRarity();
		this.cardNumber = card.getCardNumber();
	}

//	@Override
//	public boolean moveToZone(Zone zone, Game game, boolean flag) {
//		ZoneChangeEvent event = new ZoneChangeEvent(this.getId(), this.getControllerId(), Zone.BATTLEFIELD, zone);
//		if (!game.replaceEvent(event)) {
//			if (game.getPlayer(controllerId).removeFromBattlefield(this, game)) {
//				CardImpl card = (CardImpl) game.getCard(objectId);
//				return card.moveToZone(event.getToZone(), controllerId, game, flag);
//			}
//		}
//		return false;
//	}

	@Override
	public boolean moveToZone(Zone toZone, Game game, boolean flag) {
		Zone fromZone = zone;
		ZoneChangeEvent event = new ZoneChangeEvent(this, controllerId, fromZone, toZone);
		if (!game.replaceEvent(event)) {
			Player controller = game.getPlayer(controllerId);
			if (controller != null && controller.removeFromBattlefield(this, game)) {
				Card card = game.getCard(objectId);
				Player owner = game.getPlayer(ownerId);
				if (owner != null) {
					switch (event.getToZone()) {
						case GRAVEYARD:
							owner.putInGraveyard(card, game, !flag);
							break;
						case HAND:
							owner.getHand().add(card);
							break;
						case EXILED:
							game.getExile().getPermanentExile().add(card);
							break;
						case LIBRARY:
							if (flag)
								owner.getLibrary().putOnTop(card, game);
							else
								owner.getLibrary().putOnBottom(card, game);
							break;
						case BATTLEFIELD:
							//should never happen
							break;
					}
					zone = event.getToZone();
					game.fireEvent(event);
					return zone == toZone;
				}
			}
		}
		return false;
	}


	@Override
	public boolean moveToExile(UUID exileId, String name, Game game) {
		if (game.getPlayer(controllerId).removeFromBattlefield(this, game)) {
			return game.getCard(objectId).moveToExile(exileId, name, game);
		}
		return false;
	}

	@Override
	public String getArt() {
		return art;
	}

	@Override
	public void setArt(String art) {
		this.art = art;
	}

	@Override
	public PermanentCard copy() {
		return new PermanentCard(this);
	}

	@Override
	public List<String> getRules() {
		if (levelerRules == null)
			return super.getRules();
		List<String> rules = new ArrayList<String>();
		rules.addAll(super.getRules());
		rules.addAll(levelerRules);
		return rules;
	}

}
