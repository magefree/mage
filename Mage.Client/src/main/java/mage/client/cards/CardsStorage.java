package mage.client.cards;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.client.plugins.impl.Plugins;
import mage.components.ImagePanel;
import mage.sets.Sets;
import mage.utils.CardUtil;

public class CardsStorage {
	private static Set<Card> allCards = new LinkedHashSet<Card>();
	private static Set<Card> landCards = new LinkedHashSet<Card>();
	private static Map<String, Integer> ratings;
	private static Integer min = Integer.MAX_VALUE, max = 0;

	static {
		for (ExpansionSet set : Sets.getInstance().values()) {
			Set<Card> cards = set.createCards();
			allCards.addAll(cards);
			for (Card card : cards) {
				if (CardUtil.isLand(card)) {
					landCards.add(card);
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

	/**
	 * Return rating of a card: 1-10.
	 * 
	 * @param card
	 * @return
	 */
	public static int rateCard(Card card) {
		if (ratings == null) {
			readRatings();
		}
		if (ratings.containsKey(card.getName())) {
			int r = ratings.get(card.getName());
			float f = 10.0f * (r - min) / (max - min);
			return (int)Math.round(f); // normalize to [1..10]
		}
		return 0;
	}
	
	private synchronized static void readRatings() {
		if (ratings == null) {
			ratings = new HashMap<String, Integer>();
			String filename = "/ratings.txt";
			try {
				InputStream is = CardsStorage.class.getResourceAsStream(filename);
				Scanner scanner = new Scanner(is);
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] s = line.split(":");
					if (s.length == 2) {
						Integer rating = Integer.parseInt(s[0].trim());
						String name = s[1].trim();
						if (rating > max) { max = rating; }
						if (rating < min) { min = rating; }
						ratings.put(name, rating);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				ratings.clear(); // no rating available on exception
			}
		}
	}

	public static void main(String[] argv) {
		for (Card card : getAllCards()) {
			String name = card.getName();
			if (name.equals("Baneslayer Angel") || name.equals("Lightning Bolt") || name.equals("Zombie Outlander")
					|| name.equals("Naturalize") || name.equals("Kraken's Eye") || name.equals("Serra Angel")) {
				System.out.println(name + " : " + rateCard(card));
			}
		}
	}
}
