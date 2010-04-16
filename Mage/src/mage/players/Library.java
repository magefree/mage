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
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
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

//	private static final transient Copier<Library> copier = new Copier<Library>();

	private static Random rnd = new Random();

	private boolean emptyDraw = false;
	private Deque<Card> library = new ArrayDeque<Card>();
	private UUID playerId;

	public Library(UUID playerId) {
		this.playerId = playerId;
	}

	public void shuffle() {
		Card[] shuffled = library.toArray(new Card[0]);
		for (int n = shuffled.length - 1; n > 0; n--) {
			int r = rnd.nextInt(n);
			Card temp = shuffled[n];
			shuffled[n] = shuffled[r];
			shuffled[r] = temp;
		}
		library.clear();
		for (Card card: shuffled) {
			library.add(card);
		}
	}

	public Card removeFromTop(Game game) {
		Card card = library.pollFirst();
		if (card == null) {
			emptyDraw = true;
		}
		return card;
	}

	public Card getFromTop(Game game) {
		return library.peekFirst();
	}

	public void putOnTop(Card card, Game game) {
		if (card.getOwnerId().equals(playerId))
			library.addFirst(card);
		else
			game.getPlayer(card.getOwnerId()).getLibrary().putOnTop(card, game);
	}

	public void putOnBottom(Card card, Game game) {
		if (card.getOwnerId().equals(playerId))
			library.add(card);
		else
			game.getPlayer(card.getOwnerId()).getLibrary().putOnBottom(card, game);
	}

	public Library copy() {
		return new Copier<Library>().copy(this);
	}

	public void clear() {
		library.clear();
	}

	public int size() {
		return library.size();
	}
	
	public void set(Library newLibrary) {
		library.clear();
		for (Card card: newLibrary.getCards()) {
			library.add(card);
		}
	}

	public List<Card> getCards() {
		return Arrays.asList(library.toArray(new Card[0]));
	}

	public int count(FilterCard filter) {
		int result = 0;
		for (Card card: library) {
			if (filter.match(card))
				result++;
		}
		return result;
	}


	public boolean isEmtpyDraw() {
		return emptyDraw;
	}

	void setControllerId(UUID playerId) {
		for (Card card: library) {
			card.setControllerId(playerId);
		}
	}

	public void addAll(Cards cards) {
		library.addAll(cards.values());
	}

	public Card getCard(UUID cardId) {
		for (Card card: library) {
			if (card.getId().equals(cardId))
				return card;
		}
		return null;
	}

	public Card remove(UUID cardId) {
		Iterator<Card> it = library.iterator();
		while(it.hasNext()) {
			Card card = it.next();
			if (card.getId().equals(cardId)) {
				it.remove();
				return card;
			}
		}
		return null;
	}

}
