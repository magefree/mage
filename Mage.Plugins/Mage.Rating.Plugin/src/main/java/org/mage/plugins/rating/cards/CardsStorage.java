package org.mage.plugins.rating.cards;

import java.util.ArrayList;
import java.util.List;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.sets.Sets;

public class CardsStorage {
	private static List<Card> allCards = new ArrayList<Card>();
	
	static {
    	for (ExpansionSet set: Sets.getInstance().values()) {
			allCards.addAll(set.createCards());
		}
	}
	
	public static List<Card> getAllCards() {
		return allCards;
	}
}
