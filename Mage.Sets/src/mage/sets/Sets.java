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

import mage.Constants.CardType;
import mage.Constants.ColoredManaSymbol;
import mage.Mana;
import mage.cache.CacheService;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.ExpansionSet;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Sets extends HashMap<String, ExpansionSet> {

    private final static Logger logger = Logger.getLogger(Sets.class);
    private static final Sets fINSTANCE =  new Sets();
    private static Set<String> names;
    private static Set<String> nonLandNames;
    private static Set<String> creatureTypes;
    private static List<Card> cards;
    private static Map<String, Card> cardMap;
    protected static Random rnd = new Random();

    private static boolean loaded;

    public static Sets getInstance() {
        return fINSTANCE;
    }

    private Sets() {
        names = new TreeSet<String>();
        nonLandNames = new TreeSet<String>();
        cards = new ArrayList<Card>();
        cardMap = new HashMap<String, Card>();
        creatureTypes = new TreeSet<String>();
        this.addSet(AlaraReborn.getInstance());
        this.addSet(Apocalypse.getInstance());
        this.addSet(AvacynRestored.getInstance());
        this.addSet(BetrayersOfKamigawa.getInstance());
        this.addSet(ChampionsOfKamigawa.getInstance());
        this.addSet(Conflux.getInstance());
        this.addSet(DarkAscension.getInstance());
        this.addSet(Darksteel.getInstance());
        this.addSet(Dissension.getInstance());
        this.addSet(EighthEdition.getInstance());
        this.addSet(ElspethvsTezzeret.getInstance());
        this.addSet(Eventide.getInstance());
        this.addSet(FifthDawn.getInstance());
        this.addSet(FifthEdition.getInstance());
        this.addSet(FutureSight.getInstance());
        this.addSet(Guildpact.getInstance());
        this.addSet(Guru.getInstance());
        this.addSet(Innistrad.getInstance());
        this.addSet(Invasion.getInstance());
        this.addSet(Judgment.getInstance());
        this.addSet(Lorwyn.getInstance());
        this.addSet(Magic2010.getInstance());
        this.addSet(Magic2011.getInstance());
        this.addSet(Magic2012.getInstance());
        this.addSet(Magic2013.getInstance());
        this.addSet(MagicPlayerRewards.getInstance());
        this.addSet(Mirrodin.getInstance());
        this.addSet(MirrodinBesieged.getInstance());
        this.addSet(Morningtide.getInstance());
        this.addSet(NewPhyrexia.getInstance());
        this.addSet(NinthEdition.getInstance());
        this.addSet(Onslaught.getInstance());
        this.addSet(PlanarChaos.getInstance());
        this.addSet(Planechase.getInstance());
        this.addSet(Planeshift.getInstance());
        this.addSet(RavnicaCityOfGuilds.getInstance());
        this.addSet(RiseOfTheEldrazi.getInstance());
        this.addSet(SaviorsOfKamigawa.getInstance());
        this.addSet(ScarsOfMirrodin.getInstance());
        this.addSet(ShardsOfAlara.getInstance());
        this.addSet(Shadowmoor.getInstance());
        this.addSet(Tenth.getInstance());
        this.addSet(Tempest.getInstance());
        this.addSet(TimeSpiral.getInstance());
        this.addSet(TimeSpiralTimeshifted.getInstance());
        this.addSet(UrzasSaga.getInstance());
        this.addSet(UrzasLegacy.getInstance());
        this.addSet(Weatherlight.getInstance());
        this.addSet(Worldwake.getInstance());
        this.addSet(Zendikar.getInstance());
    }

	private void addSet(ExpansionSet set) {
		this.put(set.getCode(), set);
        //cards.addAll(set.getCards());
	}

    private static void loadCards() {
        if (!loaded) {
            synchronized (Sets.class) {
                if (!loaded) {
                    for (ExpansionSet set : getInstance().values()) {
                        cards.addAll(set.getCards());
                    }
                    names = CacheService.loadCardNames(cards);
                    creatureTypes = CacheService.loadCreatureTypes(cards);
                    nonLandNames = CacheService.loadNonLandNames(cards);
                    loaded = true;
                }
            }
        }
    }

	public static Set<String> getCardNames() {
        loadCards();
		return names;
	}

	public static Set<String> getNonLandCardNames() {
        loadCards();
		return nonLandNames;
	}

	public static Set<String> getCreatureTypes() {
        loadCards();
		return creatureTypes;
	}

	public static Card getRandomCard() {
        loadCards();
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
            List<Card> cardsFound = new ArrayList<Card>();
            for (ExpansionSet set: fINSTANCE.values()) {
                Card card = set.findCard(name, true);
                if (card != null) {
                    cardsFound.add(card);
                }
            }
            if (cardsFound.size() > 0) {
                Card card = cardsFound.get(rnd.nextInt(cardsFound.size()));
                String cardClassName = card.getClass().getName();
                return CardImpl.createCard(cardClassName);
            }
        }
        return null;
    }

    public static Card findCard(String expansionsetCode, int cardNum) {
        if (cardMap.containsKey(expansionsetCode + Integer.toString(cardNum))) {
            return cardMap.get(expansionsetCode + Integer.toString(cardNum));
        }
        if (fINSTANCE.containsKey(expansionsetCode)) {
            ExpansionSet set = fINSTANCE.get(expansionsetCode);
            Card card = set.findCard(cardNum);
            if (card != null) {
                cardMap.put(expansionsetCode + Integer.toString(cardNum), card);
                return card;
            }
        }
        logger.warn("Could not find card: set=" + expansionsetCode + "cardNum=" + Integer.toString(cardNum));
        return null;

    }

    public static Card createCard(Class clazz) {
        try {
            Constructor<?> con = clazz.getConstructor(new Class[]{UUID.class});
            Card card = (Card) con.newInstance(new Object[] {null});
            card.build();
            return card;
        } catch (Exception ex) {
            logger.fatal("Error creating card:" + clazz.getName(), ex);
            return null;
        }
    }


    public static ExpansionSet findSet(String code) {
        if (fINSTANCE.containsKey(code))
            return fINSTANCE.get(code);
        return null;
    }

    public static void saveDeck(String file, DeckCardLists deck) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(file);
        Map<String, Integer> deckCards = new HashMap<String, Integer>();
        Map<String, Integer> sideboard = new HashMap<String, Integer>();
        try {
            if (deck.getName() != null && deck.getName().length() > 0)
                out.println("NAME:" + deck.getName());
            if (deck.getAuthor() != null && deck.getAuthor().length() > 0)
                out.println("AUTHOR:" + deck.getAuthor());
            for (String cardClass: deck.getCards()) {
                if (deckCards.containsKey(cardClass)) {
                    deckCards.put(cardClass, deckCards.get(cardClass) + 1);
                }
                else {
                    deckCards.put(cardClass, 1);
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
            for (Map.Entry<String, Integer> entry: deckCards.entrySet()) {
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

    public ExpansionSet[] getSortedByReleaseDate() {
        ExpansionSet[] sets = Sets.getInstance().values().toArray(new ExpansionSet[0]);
        Arrays.sort(sets, new Comparator<ExpansionSet>() {
            @Override
            public int compare(ExpansionSet o1, ExpansionSet o2) {
                return o2.getReleaseDate().compareTo(o1.getReleaseDate());
            }
        });
        return sets;
    }
}
