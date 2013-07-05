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

package mage.cards;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import mage.cards.decks.DeckCardInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.util.ClassScanner;
import org.apache.log4j.Logger;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Sets extends HashMap<String, ExpansionSet> {

    private static final Logger logger = Logger.getLogger(Sets.class);
    private static final Sets fINSTANCE =  new Sets();
    protected static Random rnd = new Random();

    public static Sets getInstance() {
        return fINSTANCE;
    }

    private Sets() {
        ArrayList<String> packages = new ArrayList<String>();
        packages.add("mage.sets");
        for (Class c : ClassScanner.findClasses(packages, ExpansionSet.class)) {
            try {
                addSet((ExpansionSet) c.getMethod("getInstance").invoke(null));
            } catch (Exception ex) {
            }
        }
    }

    private void addSet(ExpansionSet set) {
        this.put(set.getCode(), set);
    }

    /**
     * Generates card pool of cardsCount cards that have manacost of allowed colors.
     *
     * @param cardsCount
     * @param allowedColors
     * @return
     */
    public static List<Card> generateRandomCardPool(int cardsCount, List<ColoredManaSymbol> allowedColors) {
        CardCriteria criteria = new CardCriteria();
        criteria.notTypes(CardType.LAND);
        for (ColoredManaSymbol color : allowedColors) {
            switch (color) {
                case W:
                    criteria.white(true);
                    break;
                case U:
                    criteria.blue(true);
                    break;
                case B:
                    criteria.black(true);
                    break;
                case R:
                    criteria.red(true);
                    break;
                case G:
                    criteria.green(true);
                    break;
            }
        }
        List<CardInfo> cards = CardRepository.instance.findCards(criteria);

        int count = 0;
        int tries = 0;
        List<Card> cardPool = new ArrayList<Card>();
        while (count < cardsCount) {
            CardInfo cardInfo = cards.get(rnd.nextInt(cards.size()));
            Card card = cardInfo != null ? cardInfo.getCard() : null;
            if (card != null) {
                cardPool.add(card);
                count++;
            }
            tries++;
            if (tries > 4096) { // to avoid infinite loop
                throw new IllegalStateException("Not enough cards for chosen colors to generate deck: " + allowedColors);
            }
        }

        return cardPool;
    }

    public static ExpansionSet findSet(String code) {
        if (fINSTANCE.containsKey(code)) {
            return fINSTANCE.get(code);
        }
        return null;
    }

    public static void saveDeck(String file, DeckCardLists deck) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(file);
        Map<String, DeckCardInfo> deckCards = new HashMap<String, DeckCardInfo>();
        Map<String, DeckCardInfo> sideboard = new HashMap<String, DeckCardInfo>();
        try {
            if (deck.getName() != null && deck.getName().length() > 0) {
                out.println("NAME:" + deck.getName());
            }
            if (deck.getAuthor() != null && deck.getAuthor().length() > 0) {
                out.println("AUTHOR:" + deck.getAuthor());
            }
            for (DeckCardInfo deckCardInfo: deck.getCards()) {
                if (deckCards.containsKey(deckCardInfo.getCardKey())) {
                    deckCards.put(deckCardInfo.getCardKey(), deckCards.get(deckCardInfo.getCardKey()).increaseQuantity());
                }
                else {
                    deckCards.put(deckCardInfo.getCardKey(), deckCardInfo);
                }
            }

            for (DeckCardInfo deckCardInfo: deck.getSideboard()) {
                if (sideboard.containsKey(deckCardInfo.getCardKey())) {
                    sideboard.put(deckCardInfo.getCardKey(), sideboard.get(deckCardInfo.getCardKey()).increaseQuantity());
                }
                else {
                    sideboard.put(deckCardInfo.getCardKey(), deckCardInfo);
                }
            }

            for (Map.Entry<String, DeckCardInfo> entry: deckCards.entrySet()) {
                out.printf("%d [%s:%d] %s%n", entry.getValue().getQuantity(), entry.getValue().getSetCode(), entry.getValue().getCardNum(), entry.getValue().getCardName());
            }
            for (Map.Entry<String, DeckCardInfo> entry: sideboard.entrySet()) {
                out.printf("SB: %d [%s:%d] %s%n", entry.getValue().getQuantity(), entry.getValue().getSetCode(), entry.getValue().getCardNum(), entry.getValue().getCardName());
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

    public ExpansionSet[] getWithBoosterSortedByReleaseDate() {
        ExpansionSet[] allSets = getSortedByReleaseDate();
        ArrayList<ExpansionSet> boosterSets = new ArrayList<ExpansionSet>();
        for (ExpansionSet set: allSets) {
            if (set.hasBoosters) {
                boosterSets.add(set);
            }
        }
        return boosterSets.toArray(new ExpansionSet[0]);
    }

    /**
     * Gives back the set codes from the sets that include basic lands.
     * If the input set itself does not incluse basic lands, but it has a parent set,
     * only this parent set code is added to the return sets.
     *
     * @param setCodes
     * @return - setCodes that have basic lands
     */
    public static Set<String> getSetsWithBasicLandsAsCodes(Set<String> setCodes) {
        Set<String> landSets = new LinkedHashSet<String>();
        if (setCodes != null && !setCodes.isEmpty()) {
            // Add parent sets with the basic lands if the setlist don't include them
            for (String setCode: setCodes) {
                ExpansionSet expansionSet = Sets.findSet(setCode);
                if (expansionSet.hasBasicLands()) {
                    landSets.add(setCode);
                } else if (expansionSet.getParentSet() != null && !landSets.contains(expansionSet.getParentSet().getCode())) {
                    landSets.add(expansionSet.getParentSet().getCode());
                }
            }
        }
        if (landSets.isEmpty()) {
            // if set has no lands and also it has no parent or parent has no lands get last set with lands
            // select a set with basic lands by random
            Random generator = new Random();
            int maxRndValue = 0;
            String selectedSetCode = null;
            for (ExpansionSet set :Sets.getInstance().getSortedByReleaseDate()) {
                if (set.hasBasicLands) {
                    int rndValue = generator.nextInt(100);
                    if (rndValue >= maxRndValue) {
                        maxRndValue = rndValue;
                        selectedSetCode = set.getCode();
                    }
                }
            }
            if (selectedSetCode != null) {
                landSets.add(selectedSetCode);
            } else {
                throw new IllegalArgumentException("No set with basic land was found");
            }
        }
        return landSets;
    }

}
