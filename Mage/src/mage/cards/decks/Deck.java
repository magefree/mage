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

package mage.cards.decks;

import mage.cards.*;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.UUID;
import mage.Constants.Zone;

public class Deck implements Serializable {

	private String name;
	private Cards cards = new CardsImpl(Zone.OUTSIDE);
	private Cards sideboard = new CardsImpl(Zone.OUTSIDE);

	public static Deck load(DeckCardLists deckCardLists) {
		Deck deck = new Deck();
		deck.setName(deckCardLists.getName());
		for (String cardName: deckCardLists.getCards()) {
			deck.cards.add(createCard(cardName));
		}
		for (String cardName: deckCardLists.getSideboard()) {
			deck.sideboard.add(createCard(cardName));
		}

		return deck;
	}

	private static Card createCard(String name) {
		try {
			Class<?> theClass  = Class.forName(name);
			Constructor<?> con = theClass.getConstructor(new Class[]{UUID.class});
			return (Card) con.newInstance(new Object[] {null});
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public DeckCardLists getDeckCardLists() {
		DeckCardLists deckCardLists = new DeckCardLists();

		deckCardLists.setName(name);
		for (Card card: cards.values()) {
			deckCardLists.getCards().add(card.getClass().getCanonicalName());
		}
		for (Card card: sideboard.values()) {
			deckCardLists.getSideboard().add(card.getClass().getCanonicalName());
		}
		
		return deckCardLists;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the cards
	 */
	public Cards getCards() {
		return cards;
	}

	/**
	 * @param cards the cards to set
	 */
	public void setCards(Cards cards) {
		this.cards = cards;
	}

	/**
	 * @return the sideboard
	 */
	public Cards getSideboard() {
		return sideboard;
	}

	/**
	 * @param sideboard the sideboard to set
	 */
	public void setSideboard(Cards sideboard) {
		this.sideboard = sideboard;
	}

	public void setOwnerId(UUID playerId) {
		cards.setOwner(playerId);
		sideboard.setOwner(playerId);
	}
	

	
}
