package mage.client.cards;

import java.util.LinkedHashSet;
import java.util.Set;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.sets.Sets;
import mage.utils.CardUtil;

public class CardsStorage {
	private static Set<Card> allCards = new LinkedHashSet<Card>();
	private static Set<Card> landCards = new LinkedHashSet<Card>();
	
	static {
    	for (ExpansionSet set: Sets.getInstance().values()) {
    		Set<Card> cards = set.createCards();
			allCards.addAll(cards);
			for (Card card : cards) {
				if (CardUtil.isLand(card)) {
					landCards.add(card);
				}
				if (card.getName().contains("Pledge")) {
					System.out.println(card.getName());
				}
			}
		}
	}
	
	public static Set<Card> getAllCards() {
		return allCards;
	}
	
	public static Set<Card> getLandCards() {
		return landCards;
	}
}
