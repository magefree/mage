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

package mage.players;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import mage.Constants.Zone;
import mage.cards.Card;
import mage.cards.Cards;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.util.Copier;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Library implements Serializable {

	private static Random rnd = new Random();

	private boolean emptyDraw;
	private Deque<UUID> library = new ArrayDeque<UUID>();
	private UUID playerId;

	public Library(UUID playerId) {
		this.playerId = playerId;
	}

	public Library(final Library lib) {
		this.emptyDraw = lib.emptyDraw;
		this.playerId = lib.playerId;
		for (UUID id: lib.library) {
			this.library.addLast(id);
		}
	}

	public void shuffle() {
		UUID[] shuffled = library.toArray(new UUID[0]);
		for (int n = shuffled.length - 1; n > 0; n--) {
			int r = rnd.nextInt(n);
			UUID temp = shuffled[n];
			shuffled[n] = shuffled[r];
			shuffled[r] = temp;
		}
		library.clear();
		for (UUID card: shuffled) {
			library.add(card);
		}
	}

	/**
	 * Removes the top card of the Library and returns it
	 * 
	 * @param game
	 * @return Card
	 * @see Card
	 */
	public Card removeFromTop(Game game) {
		Card card = game.getCard(library.pollFirst());
		if (card == null) {
			emptyDraw = true;
		}
		return card;
	}

	/**
	 * Returns the top card of the Library without removing it
	 *
	 * @param game
	 * @return Card
	 * @see Card
	 */
	public Card getFromTop(Game game) {
		return game.getCard(library.peekFirst());
	}

	public void putOnTop(Card card, Game game) {
		if (card.getOwnerId().equals(playerId)) {
			card.setZone(Zone.LIBRARY);
			library.addFirst(card.getId());
		}
		else {
			game.getPlayer(card.getOwnerId()).getLibrary().putOnTop(card, game);
		}
	}

	public void putOnBottom(Card card, Game game) {
		if (card.getOwnerId().equals(playerId)) {
			card.setZone(Zone.LIBRARY);
			library.add(card.getId());
		}
		else {
			game.getPlayer(card.getOwnerId()).getLibrary().putOnBottom(card, game);
		}
	}

	public Library copy() {
//		return new Copier<Library>().copy(this);
		return new Library(this);
	}

	public void clear() {
		library.clear();
	}

	public int size() {
		return library.size();
	}
	
	public void set(Library newLibrary) {
		library.clear();
		for (UUID card: newLibrary.getCardList()) {
			library.add(card);
		}
	}

	public List<UUID> getCardList() {
		return new ArrayList(library);
	}

	public List<Card> getCards(Game game) {
		List<Card> cards = new ArrayList<Card>();
		for (UUID cardId: library) {
			cards.add(game.getCard(cardId));
		}
		return cards;
	}

	public Collection<Card> getUniqueCards(Game game) {
		Map<String, Card> cards = new HashMap<String, Card>();
		for (UUID cardId: library) {
			Card card = game.getCard(cardId);
			if (!cards.containsKey(card.getName())) {
				cards.put(card.getName(), card);
			}
		}
		return cards.values();
	}

	public int count(FilterCard filter, Game game) {
		int result = 0;
		for (UUID card: library) {
			if (filter.match(game.getCard(card)))
				result++;
		}
		return result;
	}


	public boolean isEmtpyDraw() {
		return emptyDraw;
	}

	void setControllerId(UUID playerId, Game game) {
		for (UUID cardId: library) {
			game.getCard(cardId).setControllerId(playerId);
		}
	}

	public void addAll(Set<Card> cards) {
		for (Card card: cards) {
			card.setZone(Zone.LIBRARY);
			library.add(card.getId());
		}
	}

	public Card getCard(UUID cardId, Game game) {
		for (UUID card: library) {
			if (card.equals(cardId))
				return game.getCard(card);
		}
		return null;
	}

	public Card remove(UUID cardId, Game game) {
		Iterator<UUID> it = library.iterator();
		while(it.hasNext()) {
			UUID card = it.next();
			if (card.equals(cardId)) {
				it.remove();
				return game.getCard(card);
			}
		}
		return null;
	}

}
