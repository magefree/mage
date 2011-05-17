/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.sets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mage.Constants.CardType;
import mage.Constants.ColoredManaSymbol;
import mage.Mana;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.ExpansionSet;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Sets extends HashMap<String, ExpansionSet> {

	private final static Logger logger = Logger.getLogger(Sets.class);
	private static final Sets fINSTANCE =  new Sets();
	private static Set<String> names;
	private static List<Card> cards;
    protected static Random rnd = new Random();

	public static Sets getInstance() {
		return fINSTANCE;
	}

	private Sets() {
		names = new TreeSet<String>();
		cards = new ArrayList<Card>();
		this.addSet(AlaraReborn.getInstance());
		this.addSet(Conflux.getInstance());
        this.addSet(Darksteel.getInstance());
        this.addSet(Dissension.getInstance());
        this.addSet(ElspethvsTezzeret.getInstance());
        this.addSet(Eventide.getInstance());
        this.addSet(Guildpact.getInstance());
        this.addSet(Guru.getInstance());
		this.addSet(Magic2010.getInstance());
		this.addSet(Magic2011.getInstance());
        this.addSet(Mirrodin.getInstance());
		this.addSet(MirrodinBesieged.getInstance());
		this.addSet(Planechase.getInstance());
        this.addSet(RavnicaCityOfGuilds.getInstance());
		this.addSet(RiseOfTheEldrazi.getInstance());
		this.addSet(ShardsOfAlara.getInstance());
        this.addSet(ScarsOfMirrodin.getInstance());
		this.addSet(Tenth.getInstance());
		this.addSet(Worldwake.getInstance());
		this.addSet(Zendikar.getInstance());
	}

	private void addSet(ExpansionSet set) {
		this.put(set.getCode(), set);
		for (Card card: set.getCards()) {
			cards.add(card);
			names.add(card.getName());
		}
	}

	public static Set<String> getCardNames() {
		return names;
	}

	public static Card getRandomCard() {
		return cards.get(rnd.nextInt(cards.size()));
	}

    /**
     * Generates card pool of cardsCount cards that have manacost of allowed colors.
     *
     * @param cardsCount
     * @param allowedColors
     * @return
     */
    public static List<Card> generateRandomCardPool(int cardsCount, List<ColoredManaSymbol> allowedColors) {
        List<Card> cardPool = new ArrayList<Card>();

        int count = 0;
		int tries = 0;
		while (count < cardsCount) {
			Card card = getRandomCard();
			if (!card.getCardType().contains(CardType.LAND)) {
				if (cardFitsChosenColors(card, allowedColors)) {
					cardPool.add(card);
					count++;
				}
			}
			tries++;
			if (tries > 4096) { // to avoid infinite loop
				throw new IllegalStateException("Not enough cards for chosen colors to generate deck: " + allowedColors);
			}
		}

        return cardPool;
    }

	/**
     * Check that card can be played using chosen (allowed) colors.
     *
     * @param card
     * @param allowedColors
     * @return
     */
    private static boolean cardFitsChosenColors(Card card, List<ColoredManaSymbol> allowedColors) {
		if (card.getCardType().contains(CardType.LAND))  {
			if (!card.getSupertype().contains("Basic")) {
				int score = 0;
				for (Mana mana : card.getMana()) {
					for (ColoredManaSymbol color : allowedColors) {
						score += mana.getColor(color);
					}
				}
				if (score > 1) {
					return true;
				}
			}
		}
		else {
			for (String symbol : card.getManaCost().getSymbols()) {
				boolean found = false;
				symbol = symbol.replace("{", "").replace("}", "");
				if (isColoredMana(symbol)) {
					for (ColoredManaSymbol allowed : allowedColors) {
						if (allowed.toString().equals(symbol)) {
							found = true;
							break;
						}
					}
					if (!found) {
						return false;
					}
				}
	        }
	        return true;
		}
		return false;
    }

    protected static boolean isColoredMana(String symbol) {
        return symbol.equals("W") || symbol.equals("G") || symbol.equals("U") || symbol.equals("B") || symbol.equals("R");
    }

	public static Deck generateDeck() {
		List<ColoredManaSymbol> allowedColors = new ArrayList<ColoredManaSymbol>();
		int numColors = rnd.nextInt(2) + 1;
        int cardPoolSize = 60;
        if (numColors > 2) {
            cardPoolSize += 20;
        }
		Deck deck = new Deck();

		return deck;
	}

	public static Card findCard(String name) {
		for (ExpansionSet set: fINSTANCE.values()) {
			Card card = set.findCard(name);
			if (card != null)
				return card;
		}
		return null;
	}

    public static Card findCard(String name, boolean random) {
        if (!random) {
            return findCard(name);
        } else {
            List<Card> cards = new ArrayList<Card>();
            for (ExpansionSet set: fINSTANCE.values()) {
                Card card = set.findCard(name, true);
                if (card != null) {
                    cards.add(card);
                }
            }
            if (cards.size() > 0) {
                return cards.get(rnd.nextInt(cards.size()));
            }
        }
		return null;
	}

	public static Card createCard(Class clazz) {
		try {
			Constructor<?> con = clazz.getConstructor(new Class[]{UUID.class});
			return (Card) con.newInstance(new Object[] {null});
		} catch (Exception ex) {
			logger.fatal("Error creating card:" + clazz.getName(), ex);
			return null;
		}
	}


	public static ExpansionSet findSet(String code) {
		for (ExpansionSet set: fINSTANCE.values()) {
			if (set.getCode().equals(code))
				return set;
		}
		return null;
	}

	public static DeckCardLists loadDeck(String file) throws FileNotFoundException {
		DeckCardLists deckList = new DeckCardLists();

		File f = new File(file);
		Scanner scanner = new Scanner(f);
		Pattern pattern = Pattern.compile("(SB:)?\\s*(\\d*)\\s*\\[([a-zA-Z0-9]{3}):(\\d*)\\].*");
		try {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				if (line.startsWith("#")) continue;
				Matcher m = pattern.matcher(line);
				if (m.matches()) {
					boolean sideboard = false;
					if (m.group(1) != null && m.group(1).equals("SB:"))
						sideboard = true;
					int count = Integer.parseInt(m.group(2));
					String setCode = m.group(3);
					int cardNum = Integer.parseInt(m.group(4));
					ExpansionSet set = Sets.findSet(setCode);
					String card = set.findCard(cardNum);
					for (int i = 0; i < count; i++) {
						if (!sideboard) {
							deckList.getCards().add(card);
						}
						else {
							deckList.getSideboard().add(card);
						}
					}
				}
				else if (line.startsWith("NAME:")) {
					deckList.setName(line.substring(5, line.length()));
				}
				else if (line.startsWith("AUTHOR:")) {
					deckList.setAuthor(line.substring(7, line.length()));
				}
			}
		}
		finally {
			scanner.close();
		}

		return deckList;
	}

	public static void saveDeck(String file, DeckCardLists deck) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(file);
		Map<String, Integer> cards = new HashMap<String, Integer>();
		Map<String, Integer> sideboard = new HashMap<String, Integer>();
		try {
			if (deck.getName() != null && deck.getName().length() > 0)
				out.println("NAME:" + deck.getName());
			if (deck.getAuthor() != null && deck.getAuthor().length() > 0)
				out.println("AUTHOR:" + deck.getAuthor());
			for (String cardClass: deck.getCards()) {
				if (cards.containsKey(cardClass)) {
					cards.put(cardClass, cards.get(cardClass) + 1);
				}
				else {
					cards.put(cardClass, 1);
				}
			}
			for (String cardClass: deck.getSideboard()) {
				if (sideboard.containsKey(cardClass)) {
					sideboard.put(cardClass, sideboard.get(cardClass) + 1);
				}
				else {
					sideboard.put(cardClass, 1);
				}
			}
			for (Map.Entry<String, Integer> entry: cards.entrySet()) {
				Card card = CardImpl.createCard(entry.getKey());
				if (card != null) {
					out.printf("%d [%s:%d] %s%n", entry.getValue(), card.getExpansionSetCode(), card.getCardNumber(), card.getName());
				}
			}
			for (Map.Entry<String, Integer> entry: sideboard.entrySet()) {
				Card card = CardImpl.createCard(entry.getKey());
				if (card != null) {
					out.printf("SB: %d [%s:%d] %s%n", entry.getValue(), card.getExpansionSetCode(), card.getCardNumber(), card.getName());
				}
			}
		}
		finally {
			out.close();
		}
	}
}
