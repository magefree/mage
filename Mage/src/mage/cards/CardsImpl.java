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

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import mage.Constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CardsImpl extends LinkedHashSet<UUID> implements Cards, Serializable {

	private static Random rnd = new Random();
	private UUID ownerId;
	private Zone zone;

	public CardsImpl() { }

	public CardsImpl(Zone zone) {
		this.zone = zone;
	}

	public CardsImpl(Zone zone, List<Card> cards) {
		this(zone);
		for (Card card: cards) {
			add(card);
		}
	}

	public CardsImpl(final CardsImpl cards) {
		for (UUID cardId: cards) {
			this.add(cardId);
		}
		this.ownerId = cards.ownerId;
		this.zone = cards.zone;
	}

	@Override
	public Cards copy() {
		return new CardsImpl(this);
	}

	@Override
	public void add(Card card) {
		this.add(card.getId());
//		if (zone != null)
//			card.setZone(zone);
	}

	@Override
	public Card get(UUID cardId, Game game) {
		if (this.contains(cardId))
			return game.getCard(cardId);
		return null;
	}

	@Override
	public void remove(Card card) {
		this.remove(card.getId());
	}

	@Override
	public void setOwner(UUID ownerId, Game game) {
		this.ownerId = ownerId;
		for (UUID card: this) {
			game.getCard(card).setOwnerId(ownerId);
		}
	}

	@Override
	public Card getRandom(Game game) {
		UUID[] cards = this.toArray(new UUID[0]);
		return game.getCard(cards[rnd.nextInt(cards.length)]);
	}

	@Override
	public int count(FilterCard filter, Game game) {
		int result = 0;
		for (UUID card: this) {
			if (filter.match(game.getCard(card)))
				result++;
		}
		return result;
	}

	@Override
	public void checkTriggers(GameEvent event, Game game) {
		for (UUID card: this) {
			game.getCard(card).checkTriggers(zone, event, game);
		}
	}

	@Override
	public Set<Card> getCards(FilterCard filter, Game game) {
		Set<Card> cards = new LinkedHashSet<Card>();
		for (UUID card: this) {
			boolean match = filter.match(game.getCard(card));
			if (match)
				cards.add(game.getCard(card));
		}
		return cards;
	}

	@Override
	public Set<Card> getCards(Game game) {
		Set<Card> cards = new LinkedHashSet<Card>();
		for (UUID card: this) {
			cards.add(game.getCard(card));
		}
		return cards;
	}

	@Override
	public void addAll(List<Card> cards) {
		for (Card card: cards) {
			add(card);
		}
	}

	@Override
	public Collection<Card> getUniqueCards(Game game) {
		Map<String, Card> cards = new HashMap<String, Card>();
		for(UUID card: this) {
			if (!cards.containsKey(game.getCard(card).getName())) {
				cards.put(game.getCard(card).getName(), game.getCard(card));
			}
		}
		return cards.values();
	}

}
