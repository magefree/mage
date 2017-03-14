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

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLayout;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.util.ClassScanner;
import mage.util.RandomUtil;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Sets extends HashMap<String, ExpansionSet> {

    private static final Logger logger = Logger.getLogger(Sets.class);
    private static final Sets instance = new Sets();

    public static Sets getInstance() {
        return instance;
    }

    private Set<String> customSets = new HashSet<>();

    private Sets() {
        ArrayList<String> packages = new ArrayList<>();
        packages.add("mage.sets");
        for (Class c : ClassScanner.findClasses(null, packages, ExpansionSet.class)) {
            try {
                addSet((ExpansionSet) c.getMethod("getInstance").invoke(null));
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
    }

    public void addSet(ExpansionSet set) {
        if (containsKey(set.getCode())) {
            throw new IllegalArgumentException("Set code " + set.getCode() + " already exists.");
        }
        this.put(set.getCode(), set);
        if (set.isCustomSet()) {
            customSets.add(set.getCode());
        }
    }

    public static boolean isCustomSet(String setCode) {
        return getInstance().customSets.contains(setCode);
    }

    /**
     * Generates card pool of cardsCount cards that have manacost of allowed
     * colors.
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
        List<Card> cardPool = new ArrayList<>();
        while (count < cardsCount) {
            CardInfo cardInfo = cards.get(RandomUtil.nextInt(cards.size()));
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
        if (instance.containsKey(code)) {
            return instance.get(code);
        }
        return null;
    }

    public static void saveDeck(String file, DeckCardLists deck) throws FileNotFoundException {
        Map<String, DeckCardInfo> deckCards = new HashMap<>();
        Map<String, DeckCardInfo> sideboard = new HashMap<>();
        try (PrintWriter out = new PrintWriter(file)) {
            if (deck.getName() != null && !deck.getName().isEmpty()) {
                out.println("NAME:" + deck.getName());
            }
            if (deck.getAuthor() != null && !deck.getAuthor().isEmpty()) {
                out.println("AUTHOR:" + deck.getAuthor());
            }
            for (DeckCardInfo deckCardInfo : deck.getCards()) {
                if (deckCards.containsKey(deckCardInfo.getCardKey())) {
                    deckCards.put(deckCardInfo.getCardKey(), deckCards.get(deckCardInfo.getCardKey()).increaseQuantity());
                } else {
                    deckCards.put(deckCardInfo.getCardKey(), deckCardInfo);
                }
            }

            for (DeckCardInfo deckCardInfo : deck.getSideboard()) {
                if (sideboard.containsKey(deckCardInfo.getCardKey())) {
                    sideboard.put(deckCardInfo.getCardKey(), sideboard.get(deckCardInfo.getCardKey()).increaseQuantity());
                } else {
                    sideboard.put(deckCardInfo.getCardKey(), deckCardInfo);
                }
            }

            // Write out all of the cards
            for (Entry<String, DeckCardInfo> entry : deckCards.entrySet()) {
                out.printf("%d [%s:%s] %s%n", entry.getValue().getQuantity(), entry.getValue().getSetCode(), entry.getValue().getCardNum(), entry.getValue().getCardName());
            }
            for (Entry<String, DeckCardInfo> entry : sideboard.entrySet()) {
                out.printf("SB: %d [%s:%s] %s%n", entry.getValue().getQuantity(), entry.getValue().getSetCode(), entry.getValue().getCardNum(), entry.getValue().getCardName());
            }

            // Write out the layout
            out.print("LAYOUT MAIN:");
            writeCardLayout(out, deck.getCardLayout());
            out.print("\n");
            out.print("LAYOUT SIDEBOARD:");
            writeCardLayout(out, deck.getSideboardLayout());
            out.print("\n");
        }
    }

    private static void writeCardLayout(PrintWriter out, DeckCardLayout layout) {
        if (layout == null) {
            return;
        }
        List<List<List<DeckCardInfo>>> cardGrid = layout.getCards();
        int height = cardGrid.size();
        int width = (height > 0) ? cardGrid.get(0).size() : 0;
        out.print("(" + height + ',' + width + ')');
        out.print(layout.getSettings());
        out.print("|");
        for (List<List<DeckCardInfo>> row : cardGrid) {
            for (List<DeckCardInfo> stack : row) {
                out.print("(");
                for (int i = 0; i < stack.size(); ++i) {
                    DeckCardInfo info = stack.get(i);
                    out.printf("[%s:%s]", info.getSetCode(), info.getCardNum());
                    if (i != stack.size() - 1) {
                        out.print(",");
                    }
                }
                out.print(")");
            }
        }
    }

}
