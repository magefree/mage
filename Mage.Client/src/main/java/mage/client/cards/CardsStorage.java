package mage.client.cards;

import java.util.LinkedHashSet;
import java.util.Set;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.sets.Sets;

public class CardsStorage {
	private static Set<Card> allCards = new LinkedHashSet<Card>();
	
	static {
    	for (ExpansionSet set: Sets.getInstance().values()) {
			allCards.addAll(set.createCards());
		}
	}
	
	public static Set<Card> getAllCards() {
		return allCards;
	}
}
