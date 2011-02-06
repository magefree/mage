package mage.client.cards;

import mage.Constants;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.sets.Sets;
import mage.utils.CardUtil;

import java.io.InputStream;
import java.util.*;

/**
 * Stores all implemented cards on client side.
 * Used by deck editor, deck generator, collection viewer, etc.
 *
 * @author nantuko
 */
public class CardsStorage {
	private static List<Card> allCards = new ArrayList<Card>();
	private static Set<Card> nonBasicLandCards = new LinkedHashSet<Card>();
	private static Map<String, Integer> ratings;
	private static Integer min = Integer.MAX_VALUE, max = 0;
	private static int cardsCount;
	private static List<String> setCodes = new ArrayList<String>();
	private static List<Card> notImplementedCards;

	/**
	 * Rating that is given for new cards.
	 * Ratings are in [1,10] range, so setting it high will make new cards appear more often.
	 */
	private static final int DEFAULT_NOT_RATED_CARD_RATING = 6;

	static {
		for (ExpansionSet set : Sets.getInstance().values()) {
			setCodes.add(set.getCode());
			Set<Card> cards = set.createCards();
			allCards.addAll(cards);
			for (Card card : cards) {
				if (CardUtil.isLand(card) && !CardUtil.isBasicLand(card)) {
					nonBasicLandCards.add(card);
				}
			}
		}
		Collections.sort(allCards, new CardComparator());
		Collections.sort(setCodes, new SetComparator());
		cardsCount = allCards.size();
	}

	public static List<Card> getAllCards() {
		return allCards;
	}

	/**
	 * Get cards from card pool starting from start index and ending with end index.
	 * Can filter cards by set (if parameter is not null).
	 *
	 * @param start
	 * @param end
	 * @param set			 Cards set code. Can be null.
	 * @param onlyImplemented return only implemented cards
	 * @return
	 */
	public static List<Card> getAllCards(int start, int end, String set, boolean onlyImplemented) {
		List<Card> cards = new ArrayList<Card>();
		List<Card> pool;
		if (set == null) {
			pool = allCards;
		} else {
			pool = new ArrayList<Card>();
			for (Card card : allCards) {
				if (card.getExpansionSetCode().equals(set)) {
					pool.add(card);
				}
			}
		}
		if (!onlyImplemented) {
			for (Card card : getNotImplementedCards()) {
				if (card.getExpansionSetCode().equals(set)) {
					pool.add(card);
				}
			}
			Collections.sort(pool, new CardComparator());
		}
		for (int i = start; i < Math.min(end + 1, pool.size()); i++) {
			cards.add(pool.get(i));
		}
		return cards;
	}

	public static int getCardsCount() {
		return cardsCount;
	}

	public static List<String> getSetCodes() {
		return setCodes;
	}

	public static Set<Card> getNonBasicLandCards() {
		return nonBasicLandCards;
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
			// normalize to [1..10]
			return (int) Math.round(f);
		}
		return DEFAULT_NOT_RATED_CARD_RATING;
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
						if (rating > max) {
							max = rating;
						}
						if (rating < min) {
							min = rating;
						}
						ratings.put(name, rating);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				ratings.clear(); // no rating available on exception
			}
		}
	}

	/**
	 * Get list of not implemented cards.
	 * Used in collection viewer to show what cards need to be done for the latest set.
	 *
	 * @return
	 */
	public static List<Card> getNotImplementedCards() {
		List<Card> cards = new ArrayList<Card>();
		if (notImplementedCards == null) {
			String filename = "/som.txt";
			if (allCards.size() == 0) {
				return cards;
			}

			Set<String> names = new HashSet<String>();
			for (Card card : allCards) {
				names.add(card.getExpansionSetCode() + card.getName());
			}

			readUnimplemented("SOM", "/som.txt", names, cards);
			readUnimplemented("MBS", "/mbs.txt", names, cards);

			names.clear();
			names = null;
		}
		return cards;
	}

	private static void readUnimplemented(String set, String filename, Set<String> names, List<Card> cards) {
		try {
			Card tmp = allCards.get(0);
			InputStream is = CardsStorage.class.getResourceAsStream(filename);
			Scanner scanner = new Scanner(is);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] s = line.split("\\|");
				if (s.length == 6) {
					String name = s[1].trim();
					if (!names.contains(set + name)) {
						Integer cid = Integer.parseInt(s[5]);
						Card card = tmp.copy();
						card.setName(name);
						card.setExpansionSetCode(set);
						card.setCardNumber(cid);
						card.setRarity(Constants.Rarity.NA); // mark as not implemented
						card.getCardType().clear();
						cards.add(card);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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

	/**
	 * Card comparator.
	 * First compares set codes, then collector ids and just then card names.
	 * <p/>
	 * Show latest set cards on top.
	 *
	 * @author nantuko
	 */
	private static class CardComparator implements Comparator<Card> {
		private static final String LATEST_SET_CODE = "SOM";

		@Override
		public int compare(Card o1, Card o2) {
			String set1 = o1.getExpansionSetCode();
			String set2 = o2.getExpansionSetCode();
			if (set1.equals(set2)) {
				Integer cid1 = o1.getCardNumber();
				Integer cid2 = o2.getCardNumber();
				if (cid1 == cid2) {
					return o1.getName().compareTo(o2.getName());
				} else {
					return cid1.compareTo(cid2);
				}
			} else {
				// put latest set on top
				if (set1.equals(LATEST_SET_CODE)) {
					return -1;
				}
				if (set2.equals(LATEST_SET_CODE)) {
					return 1;
				}
				return set1.compareTo(set2);
			}
		}
	}

	/**
	 * Set comparator. Puts latest set on top.
	 */
	private static class SetComparator implements Comparator<String> {
		private static final String LATEST_SET_CODE = "SOM";
		private static final String LATEST_SET_CODE_2 = "MBS";

		@Override
		public int compare(String set1, String set2) {
			// put latest set on top
			if (set1.equals(LATEST_SET_CODE)) {
				return -1;
			}
			if (set2.equals(LATEST_SET_CODE)) {
				return 1;
			}

			if (set1.equals(LATEST_SET_CODE_2)) {
				return -1;
			}
			if (set2.equals(LATEST_SET_CODE_2)) {
				return 1;
			}
			return set1.compareTo(set2);
		}
	}
}
