package mage.client.cards;

import mage.Constants;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.ExpansionSet;
import mage.sets.Sets;
import mage.utils.CardUtil;

import java.io.InputStream;
import java.util.*;

import org.apache.log4j.Logger;

/**
 * Stores all implemented cards on client side.
 * Used by deck editor, deck generator, collection viewer, etc.
 *
 * @author nantuko
 */
public class CardsStorage {
	private static final Logger log = Logger.getLogger(CardsStorage.class);
	
	private static final List<Card> allCards;
	private static final Set<Card> nonBasicLandCards;
	private static final List<String> setCodes;
	private static Map<String, Integer> ratings;
	private static Integer min = Integer.MAX_VALUE, max = 0;
	private static List<Card> notImplementedCards;

	/**
	 * Rating that is given for new cards.
	 * Ratings are in [1,10] range, so setting it high will make new cards appear more often.
	 */
	private static final int DEFAULT_NOT_RATED_CARD_RATING = 6;

    static {
        allCards = new ArrayList<Card>();
        nonBasicLandCards = new LinkedHashSet<Card>();
        setCodes = new ArrayList<String>();

        List<ExpansionSet> sets = new ArrayList<ExpansionSet>(Sets.getInstance().values());
        Collections.sort(sets, new SetComparator());
        for (ExpansionSet set : sets) {
            setCodes.add(set.getCode());
        }

        for (ExpansionSet set : sets) {
            List<Card> cards = set.getCards();
            Collections.sort(cards, new CardComparator());
            allCards.addAll(cards);
            for (Card card : cards) {
                if (CardUtil.isLand(card) && !CardUtil.isBasicLand(card)) {
                    nonBasicLandCards.add(card);
                }
            }
        }
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
		return allCards.size();
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
			if (allCards.isEmpty()) {
				return cards;
			}

			Set<String> names = new HashSet<String>();
			for (Card card : allCards) {
				names.add(card.getExpansionSetCode() + card.getName());
			}

			readUnimplemented("ZEN", "/zen.txt", names, cards);
			readUnimplemented("WWK", "/wwk.txt", names, cards);
			readUnimplemented("ROE", "/roe.txt", names, cards);
			readUnimplemented("ISD", "/isd.txt", names, cards);

			names.clear();
			names = null;
		}
		return cards;
	}

    private static final class UnimplementedCardImpl extends CardImpl {

        public UnimplementedCardImpl(CardImpl card) {
            super(card);
        }

        @Override
        public UnimplementedCardImpl copy() {
            return new UnimplementedCardImpl(this);
        }

        public void setCanTransform(boolean canTransform) {
            this.canTransform = canTransform;
        }

        public void setNightCard(boolean nightCard) {
            this.nightCard = nightCard;
        }

        public void setSecondSideCard(Card secondSideCard) {
            this.secondSideCard = secondSideCard;
        }
    }

    private static void readUnimplemented(String set, String filename, Set<String> names, List<Card> cards) {
        try {
            CardImpl tmp = (CardImpl) allCards.get(0);
            InputStream is = CardsStorage.class.getResourceAsStream(filename);
            if (is == null) {
                log.error("Couldn't find: " + filename);
                return;
            }
            Scanner scanner = new Scanner(is);
            UnimplementedCardImpl cardToAdd = new UnimplementedCardImpl(tmp);
            boolean addCard = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] s = line.split("\\|");
                UnimplementedCardImpl card = new UnimplementedCardImpl(tmp);
                if (s.length == 2) {
                    String name = s[1].trim();
                    if (!names.contains(set + name)) {
                        Integer cid;
                        boolean secondFace = false;
                        if (s[0].endsWith("a")) {
                            cid = Integer.parseInt(s[0].replace("a", ""));
                        } else if (s[0].endsWith("b")) {
                            cid = Integer.parseInt(s[0].replace("b", ""));
                            secondFace = true;
                            addCard = true;
                        } else {
                            cid = Integer.parseInt(s[0]);
                            addCard = true;
                        }
                        card.setName(name);
                        card.setExpansionSetCode(set);
                        card.setCardNumber(cid);
                        card.setRarity(Constants.Rarity.NA); // mark as not implemented
                        card.getCardType().clear();
                        if (secondFace) {
                            cardToAdd.setCanTransform(true);
                            cardToAdd.setSecondSideCard(card);
                            card.setCanTransform(true);
                            card.setNightCard(true);
                        } else {
                            cardToAdd = card;
                        }
                    }
                }
                if (addCard) {
                    cards.add(cardToAdd);
                    addCard = false;
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
	 * First compares collector ids and then card names.
	 * <p/>
	 * Show latest set cards on top.
	 *
	 * @author nantuko
	 */
    private static class CardComparator implements Comparator<Card> {

        @Override
        public int compare(Card o1, Card o2) {
            Integer cid1 = o1.getCardNumber();
            Integer cid2 = o2.getCardNumber();
            if (cid1 == cid2) {
                return o1.getName().compareTo(o2.getName());
            } else {
                return cid1.compareTo(cid2);
            }
        }
    }

	/**
	 * Set comparator. Puts latest set on top.
	 */
	private static class SetComparator implements Comparator<ExpansionSet> {

		@Override
		public int compare(ExpansionSet set1, ExpansionSet set2) {
			return set2.getReleaseDate().compareTo(set1.getReleaseDate());
		}
	}
}
