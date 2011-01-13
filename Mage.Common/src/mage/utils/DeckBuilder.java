package mage.utils;

import mage.Constants;
import mage.Constants.ColoredManaSymbol;
import mage.Mana;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.interfaces.rate.RateCallback;

import java.util.*;

/**
 * Builds deck from provided card pool.
 *
 * @author nantuko
 */
public class DeckBuilder {

	private static String selectedColors;

	private static final int SPELL_CARD_POOL_SIZE = 60;

	private static final int DECK_COUNT[] = {3, 6, 6, 4, 3, 2};
	private static final int DECK_COST[] = {1, 2, 3, 4, 6, 10};
	private static final int DECK_SPELLS = 24;
	private static final int DECK_LANDS = 16;
	private static final int DECK_SIZE = DECK_SPELLS + DECK_LANDS;
	private static final int MIN_CARD_SCORE = 25;
	private static final int MIN_SOURCE = 16;
	private static Deck deck = new Deck();

	/**
	 * Hide constructor.
	 */
	private DeckBuilder() {
	}

	public synchronized static Deck buildDeck(List<Card> spellCardPool, List<ColoredManaSymbol> allowedColors, List<Card> landCardPool, RateCallback callback) {
		deck = new Deck();

		final Collection<MageScoredCard> remainingCards = new ArrayList<MageScoredCard>();
		for (final Card card : spellCardPool) {
			remainingCards.add(new MageScoredCard(card, allowedColors, callback));
		}
		int min = 0;
		for (int index = 0; index < DECK_COUNT.length; index++) {
			final int max = DECK_COST[index];
			addCardsToDeck(remainingCards, min, max, DECK_COUNT[index]);
			min = max + 1;
		}
		addCardsToDeck(remainingCards, 0, 4, DECK_SPELLS - deck.getCards().size());
		addCardsToDeck(remainingCards, 5, 10, DECK_SPELLS - deck.getCards().size());
		addLandsToDeck(allowedColors, landCardPool, callback);

		return deck;
	}

	/**
	 * Checks that chosen card can produce mana of specific color.
	 *
	 * @param card
	 * @param allowedColors
	 * @return
	 */
	private static boolean cardCardProduceChosenColors(Card card, List<ColoredManaSymbol> allowedColors) {
		int score = 0;
		for (Mana mana : card.getMana()) {
			for (ColoredManaSymbol color : allowedColors) {
				score = score + mana.getColor(color);
			}
		}
		if (score > 1) {
			return true;
		}
		return false;
	}

	/**
	 * Chosed best scored card and adds it to the deck.
	 *
	 * @param remainingCards
	 * @param minCost
	 * @param maxCost
	 * @param count
	 */
	private static void addCardsToDeck(final Collection<MageScoredCard> remainingCards, final int minCost, final int maxCost,
	                                   final int count) {

		for (int c = count; c > 0; c--) {

			MageScoredCard bestCard = null;
			int bestScore = -1;

			for (final MageScoredCard draftedCard : remainingCards) {

				final int score = draftedCard.getScore();
				final int cost = draftedCard.getConvertedCost();
				if (score > bestScore && cost >= minCost && cost <= maxCost) {
					bestScore = score;
					bestCard = draftedCard;
				}
			}

			if (bestCard == null || bestScore < MIN_CARD_SCORE) {
				break;
			}
			deck.getCards().add(bestCard.card);
			remainingCards.remove(bestCard);
		}
	}

	/**
	 * Adds lands from non basic land (if provided), adds basic lands getting them from provided {@link RateCallback}}.
	 *
	 * @param allowedColors
	 * @param landCardPool
	 * @param callback
	 */
	private static void addLandsToDeck(List<ColoredManaSymbol> allowedColors, List<Card> landCardPool, RateCallback callback) {

		// Calculate statistics per color.
		final Map<String, Integer> colorCount = new HashMap<String, Integer>();
		for (final Card card : deck.getCards()) {

			for (String symbol : card.getManaCost().getSymbols()) {
				int count = 0;
				symbol = symbol.replace("{", "").replace("}", "");
				if (isColoredMana(symbol)) {
					for (ColoredManaSymbol allowed : allowedColors) {
						if (allowed.toString().equals(symbol)) {
							count++;
						}
					}
					if (count > 0) {
						Integer typeCount = colorCount.get(symbol);
						if (typeCount == null) {
							typeCount = new Integer(0);
						}
						typeCount += 1;
						colorCount.put(symbol, typeCount);
					}
				}
			}
		}

		// Add suitable non basic lands to deck in order of pack.
		final Map<String, Integer> colorSource = new HashMap<String, Integer>();
		for (final ColoredManaSymbol color : ColoredManaSymbol.values()) {
			colorSource.put(color.toString(), 0);
		}
		if (landCardPool != null) {
			for (final Card landCard : landCardPool) {
				deck.getCards().add(landCard);
				for (Mana mana : landCard.getMana()) {
					for (ColoredManaSymbol color : allowedColors) {
						int amount = mana.getColor(color);
						if (amount > 0) {
							Integer count = colorSource.get(color.toString());
							count += amount;
							colorSource.put(color.toString(), count);
						}
					}
				}
			}
		}

		// Add optimal basic lands to deck.
		while (deck.getCards().size() < DECK_SIZE) {
			ColoredManaSymbol bestColor = null;
			int lowestRatio = Integer.MAX_VALUE;
			for (final ColoredManaSymbol color : ColoredManaSymbol.values()) {

				final Integer count = colorCount.get(color.toString());
				if (count != null && count > 0) {
					final int source = colorSource.get(color.toString());
					final int ratio;
					if (source < MIN_SOURCE) {
						ratio = source - count;
					} else {
						ratio = source * 100 / count;
					}
					if (ratio < lowestRatio) {
						lowestRatio = ratio;
						bestColor = color;
					}
				}
			}
			final Card landCard = callback.getBestBasicLand(bestColor);
			Integer count = colorSource.get(bestColor.toString());
			count++;
			colorSource.put(bestColor.toString(), count);
			deck.getCards().add(landCard);
		}
	}

	private static class MageScoredCard {

		private Card card;
		private int score;

		private static final int SINGLE_PENALTY[] = {0, 1, 1, 3, 6, 9};
		//private static final int DOUBLE_PENALTY[] = { 0, 0, 1, 2, 4, 6 };

		public MageScoredCard(Card card, List<ColoredManaSymbol> allowedColors, RateCallback cardRater) {
			this.card = card;

			int type = 0;
			if (card.getCardType().contains(Constants.CardType.CREATURE)) {
				type = 10;
			} else if (card.getSubtype().contains("Equipment")) {
				type = 8;
			} else if (card.getSubtype().contains("Aura")) {
				type = 5;
			} else if (card.getCardType().contains(Constants.CardType.INSTANT)) {
				type = 7;
			} else {
				type = 6;
			}

			this.score =
					// 5*card.getValue() + // not possible now
					3 * cardRater.rateCard(card) +
							// 3*card.getRemoval() + // not possible now
							type + getManaCostScore(card, allowedColors);
		}

		private int getManaCostScore(Card card, List<ColoredManaSymbol> allowedColors) {
			int converted = card.getManaCost().convertedManaCost();
			final Map<String, Integer> singleCount = new HashMap<String, Integer>();
			int maxSingleCount = 0;
			for (String symbol : card.getManaCost().getSymbols()) {
				int count = 0;
				symbol = symbol.replace("{", "").replace("}", "");
				if (isColoredMana(symbol)) {
					for (ColoredManaSymbol allowed : allowedColors) {
						if (allowed.toString().equals(symbol)) {
							count++;
						}
					}
					if (count == 0) {
						return -30;
					}
					Integer typeCount = singleCount.get(symbol);
					if (typeCount == null) {
						typeCount = new Integer(0);
					}
					typeCount += 1;
					singleCount.put(symbol, typeCount);
					maxSingleCount = Math.max(maxSingleCount, typeCount);
				}
			}
			return 2 * converted + 3 * (10 - SINGLE_PENALTY[maxSingleCount]/*-DOUBLE_PENALTY[doubleCount]*/);
		}

		public int getScore() {
			return this.score;
		}

		public int getConvertedCost() {
			return this.card.getManaCost().convertedManaCost();
		}

		public Card getCard() {
			return this.card;
		}
	}

	protected static boolean isColoredMana(String symbol) {
		return symbol.equals("W") || symbol.equals("G") || symbol.equals("U") || symbol.equals("B") || symbol.equals("R");
	}

}