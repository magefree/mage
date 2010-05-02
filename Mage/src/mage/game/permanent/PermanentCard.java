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

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Zone;
import mage.MageInt;
import mage.cards.Card;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PermanentCard extends PermanentImpl {

	protected Card card;

	public PermanentCard(Card card, UUID controllerId) {
		super(card.getOwnerId(), controllerId, card.getName());
		this.card = card;
		this.objectId = card.getId();
		reset();
		if (card.getCardType().contains(CardType.PLANESWALKER)) {
			this.loyalty = new MageInt(card.getLoyalty().getValue());
		}		
	}

	@Override
	public void reset() {
		// when the permanent is reset copy all original values from the card
		// must copy card each reset so that the original values don't get modified
		// TODO: might need to find way to handle abilities that need to preserve state
		Card copy = card.copy();
		this.name = copy.getName();
		this.abilities = copy.getAbilities();
		this.abilities.setControllerId(this.controllerId);
		this.cardType = copy.getCardType();
		this.color = copy.getColor();
		this.manaCost = copy.getManaCost();
		this.power = copy.getPower();
		this.toughness = copy.getToughness();
		this.subtype = copy.getSubtype();
		this.supertype = copy.getSupertype();
		this.art = copy.getArt();
		super.reset();
	}

	public Card getCard() {
		return card;
	}

	@Override
	public boolean moveToZone(Zone zone, Game game, boolean sacrificed) {
		if (!game.replaceEvent(new ZoneChangeEvent(this.getId(), this.getControllerId(), Zone.BATTLEFIELD, zone))) {
			if (game.getPlayer(controllerId).removeFromBattlefield(this, game)) {
				switch (zone) {
					case GRAVEYARD:
						game.getPlayer(ownerId).putInGraveyard(card, game, !sacrificed);
						break;
					case HAND:
						game.getPlayer(ownerId).getHand().add(card);
						break;
					case EXILED:
						game.getExile().getPermanentExile().add(card);
						break;
				}
				game.fireEvent(new ZoneChangeEvent(this.getId(), this.getControllerId(), Zone.BATTLEFIELD, zone));
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean moveToExile(UUID exileId, String name, Game game) {
		if (!game.replaceEvent(new ZoneChangeEvent(this.getId(), this.getControllerId(), Zone.BATTLEFIELD, Zone.EXILED))) {
			if (game.getPlayer(controllerId).removeFromBattlefield(this, game)) {
				if (exileId == null) {
					game.getExile().getPermanentExile().add(card);
				}
				else {
					game.getExile().createZone(exileId, name).add(card);
				}
				game.fireEvent(new ZoneChangeEvent(this.getId(), this.getControllerId(), Zone.BATTLEFIELD, Zone.EXILED));
				return true;
			}
		}
		return false;
	}
}
