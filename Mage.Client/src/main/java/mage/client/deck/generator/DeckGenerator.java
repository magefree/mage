package mage.client.deck.generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mage.Constants.CardType;
import mage.Constants.ColoredManaSymbol;
import mage.Mana;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.ExpansionSet;
import mage.cards.decks.Deck;
import mage.client.cards.CardsStorage;
import mage.client.util.gui.ColorsChooser;
import mage.sets.Sets;
import mage.utils.CardUtil;

public class DeckGenerator {

	private static JDialog dlg;
	private static String selectedColors;

	private static final int SPELL_BOOSTER_PACK_SIZE = 60;

	private static final int DECK_COUNT[] = { 3, 6, 6, 4, 3, 2 };
	private static final int DECK_COST[] = { 1, 2, 3, 4, 6, 10 };
	private static final int DECK_SPELLS = 24;
	private static final int DECK_LANDS = 16;
	private static final int DECK_SIZE = DECK_SPELLS + DECK_LANDS;
	private static final int MIN_CARD_SCORE = 25;
	private static final int MIN_SOURCE = 16;
	private static final int MAX_NON_BASIC_SOURCE = DECK_LANDS / 2;

	private static Deck deck = new Deck();
	private static String manaSource;

	public static String generateDeck() {
		JPanel p0 = new JPanel();
		p0.setLayout(new BoxLayout(p0, BoxLayout.Y_AXIS));
		JLabel text = new JLabel("Choose color for your deck: ");
		p0.add(text);
		p0.add(Box.createVerticalStrut(5));
		final ColorsChooser colorsChooser = new ColorsChooser("bu");
		p0.add(colorsChooser);

		final JButton btnGenerate = new JButton("Ok");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGenerate.setEnabled(false);
				colorsChooser.setEnabled(false);
				selectedColors = (String) colorsChooser.getSelectedItem();
				dlg.setVisible(false);
			}
		});
		final JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dlg.setVisible(false);
				selectedColors = null;
			}
		});
		Object[] options = { btnGenerate, btnCancel };
		JOptionPane optionPane = new JOptionPane(p0, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		dlg = optionPane.createDialog("Generating deck");
		dlg.setVisible(true);
		dlg.dispose();

		if (selectedColors != null) {
			buildDeck();
			try {
				File tmp = File.createTempFile("tempDeck" + UUID.randomUUID().toString(), ".dck");
				tmp.createNewFile();
				deck.setName("Generated-Deck-" + UUID.randomUUID());
				Sets.saveDeck(tmp.getAbsolutePath(), deck.getDeckCardLists());
				//JOptionPane.showMessageDialog(null, "Deck has been generated.");
				return tmp.getAbsolutePath();
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Couldn't generate deck. Try once again.");
			}
		}

		return selectedColors;
	}

	protected static void buildDeck() {
		deck = new Deck();
		List<ColoredManaSymbol> allowedColors = new ArrayList<ColoredManaSymbol>();
		selectedColors = selectedColors.toUpperCase();
		for (int i = 0; i < selectedColors.length(); i++) {
			char c = selectedColors.charAt(i);
			allowedColors.add(ColoredManaSymbol.lookup(c));
		}

		List<Card> spellCardPool = new ArrayList<Card>();
		List<Card> landCardPool = new ArrayList<Card>();
		int nonBasicLandCount = 0;
		for (ExpansionSet set : Sets.getInstance().values()) {
			try {
				List<Card> booster = set.createBooster();
				for (Card card : booster) {
					if (!card.getCardType().contains(CardType.LAND)) {
						spellCardPool.add(card);
					} else {
						if (!CardUtil.isBasicLand(card)) {
							if (nonBasicLandCount < MAX_NON_BASIC_SOURCE) {
								int score = 0;
								for (Mana mana : card.getMana()) {
									for (ColoredManaSymbol color : allowedColors) {
										score += mana.getColor(color);
									}
								}
								if (score > 1) {
									nonBasicLandCount++;
									landCardPool.add(card);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				//ignore
			}
		}
		out: 
		while (nonBasicLandCount < MAX_NON_BASIC_SOURCE) {
			boolean found = false;
			for (Card card : CardsStorage.getLandCards()) {
				int score = 0;
				for (Mana mana : card.getMana()) {
					for (ColoredManaSymbol color : allowedColors) {
						score += mana.getColor(color);
					}
				}
				if (score > 1) {
					nonBasicLandCount++;
					landCardPool.add(card);
					found = true;
				}
				if (nonBasicLandCount > MAX_NON_BASIC_SOURCE) {
					break out;
				}
			}
			if (!found) { //there is no compatible non basic land
				break out;
			}
		}
		System.out.println("deck generator card pool: spells=" + spellCardPool.size() + ", lands=" + landCardPool.size());

		final Collection<MageScoredCard> remainingCards = new ArrayList<MageScoredCard>();
		for (final Card card : spellCardPool) {
			remainingCards.add(new MageScoredCard(card, allowedColors));
		}
		int min = 0;
		for (int index = 0; index < DECK_COUNT.length; index++) {
			final int max = DECK_COST[index];
			addCardsToDeck(remainingCards, min, max, DECK_COUNT[index]);
			min = max + 1;
		}
		addCardsToDeck(remainingCards, 0, 4, DECK_SPELLS - deck.getCards().size());
		addCardsToDeck(remainingCards, 5, 10, DECK_SPELLS - deck.getCards().size());
		addLandsToDeck(allowedColors, landCardPool);
	}

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

	private static void addLandsToDeck(List<ColoredManaSymbol> allowedColors, List<Card> landCardPool) {

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
			final Card landCard = getBestBasicLand(bestColor);
			Integer count = colorSource.get(bestColor.toString());
			count++;
			colorSource.put(bestColor.toString(), count);
			deck.getCards().add(landCard);
		}
	}
	
	private static Card getBestBasicLand(ColoredManaSymbol color) {
		manaSource = color.toString();
		if (color.equals(ColoredManaSymbol.G)) {
			return CardImpl.createCard(Sets.findCard("Forest"));
		}
		if (color.equals(ColoredManaSymbol.R)) {
			return CardImpl.createCard(Sets.findCard("Mountain"));
		}
		if (color.equals(ColoredManaSymbol.B)) {
			return CardImpl.createCard(Sets.findCard("Swamp"));
		}
		if (color.equals(ColoredManaSymbol.U)) {
			return CardImpl.createCard(Sets.findCard("Island"));
		}
		if (color.equals(ColoredManaSymbol.W)) {
			return CardImpl.createCard(Sets.findCard("Plains"));
		}

		return null;
	}

	private static class MageScoredCard {

		private Card card;
		private int score;

		private static final int SINGLE_PENALTY[] = { 0, 1, 1, 3, 6, 9 };
		//private static final int DOUBLE_PENALTY[] = { 0, 0, 1, 2, 4, 6 };

		public MageScoredCard(Card card, List<ColoredManaSymbol> allowedColors) {
			this.card = card;

			int type = 0;
			if (card.getCardType().contains(CardType.CREATURE)) {
				type = 10;
			} else if (card.getSubtype().contains("Equipment")) {
				type = 8;
			} else if (card.getSubtype().contains("Aura")) {
				type = 5;
			} else if (card.getCardType().contains(CardType.INSTANT)) {
				type = 7;
			} else {
				type = 6;
			}

			this.score = 
				// 5*card.getValue() + // not possible now
				3 * CardsStorage.rateCard(card) + 
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

	public static void main(String[] args) {
		for (Card card : CardsStorage.getAllCards()) {
			System.out.println(card.getName());
			System.out.print("     ");
			for (String symbol : card.getManaCost().getSymbols()) {
				symbol = symbol.replace("{", "").replace("}", "");
				System.out.print(symbol + " ");
			}
			System.out.println(CardUtil.isBasicLand(card));
			List<ColoredManaSymbol> allowedColors = new ArrayList<ColoredManaSymbol>();
			allowedColors.add(ColoredManaSymbol.lookup('B'));
			allowedColors.add(ColoredManaSymbol.lookup('G'));
			DeckGenerator.MageScoredCard m = new DeckGenerator.MageScoredCard(card, allowedColors);

			System.out.println();
			System.out.println("  score: " + m.getScore());
			System.out.println();
		}
		
		//System.out.println("Done! Path: " + generateDeck());
		
	}
}
