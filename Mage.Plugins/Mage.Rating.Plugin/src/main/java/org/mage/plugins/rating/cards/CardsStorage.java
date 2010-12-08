package org.mage.plugins.rating.cards;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.sets.Sets;

public class CardsStorage {
	private static List<Card> allCards = new ArrayList<Card>();
	private static List<Card> uniqueCards = new ArrayList<Card>();
	
	static {
    	for (ExpansionSet set: Sets.getInstance().values()) {
			allCards.addAll(set.createCards());
		}
    	Set<String> names = new HashSet<String>();
    	for (Card card : allCards) {
    		if (!names.contains(card.getName())) {
    			uniqueCards.add(card);
    			names.add(card.getName());
    		}
    	}
    	System.out.println("cards=" + allCards.size() + ", unique cards=" + uniqueCards.size());
	}
	
	public static List<Card> getAllCards() {
		return allCards;
	}
	
	public static List<Card> getUniqueCards() {
		return uniqueCards;
	}
}
