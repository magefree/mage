package mage.utils;

import mage.Mana;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;
import mage.interfaces.rate.RateCallback;
import mage.util.RandomUtil;

import java.util.*;

/**
 * Builds deck from provided card pool.
 *
 * @author nantuko
 */
public final class DeckBuilder {

    private static final int[] DECK_COUNT40 = {3, 6, 6, 4, 3, 2};
    private static final int[] DECK_COUNT60 = {4, 9, 9, 5, 5, 3};
    private static final int[] DECK_COST = {1, 2, 3, 4, 6, 10};
    private static final int MIN_CARD_SCORE = 25;
    private static final int MIN_SOURCE = 3; // minmal number of sources for a mana color, will be taken also if ratio would give a lower number
    private static Deck deck;

    private static int[] deckCount;
    private static int deckSize;
    private static int deckSpells;
    private static int deckLands;

    /**
     * Hide constructor.
     */
    private DeckBuilder() {
    }

    public synchronized static Deck buildDeck(List<Card> spellCardPool, List<ColoredManaSymbol> allowedColors, List<String> setsToUse, List<Card> landCardPool, int deckCardSize, RateCallback callback) {
        deckSize = deckCardSize;
        deck = new Deck();

        final Collection<MageScoredCard> remainingCards = new ArrayList<>();
        Set<String> names = new HashSet<>();
        for (final Card card : spellCardPool) {
            if (names.contains(card.getName())) {
                continue;
            }
            remainingCards.add(new MageScoredCard(card, allowedColors, callback));
            names.add(card.getName());
        }
// prints score and manaScore to log
//        for(MageScoredCard scoreCard :remainingCards) {
//            Logger.getLogger(DeckBuilder.class).info(
//                    new StringBuilder("Score: ")
//                    .append(scoreCard.getScore())
//                    .append(" ManaScore: ")
//                    .append(scoreCard.getManaCostScore(scoreCard.getCard(), allowedColors))
//                    .append(" ")
//                    .append(scoreCard.getCard().getName())
//                    .append(" ")
//                    .append(scoreCard.getCard().getManaCost().getText()).toString()
//                    );
//        }
        int min = 0;
        if (deckSize == 40) {
            deckCount = DECK_COUNT40;
            deckSpells = 23;
            deckLands = 17;
        } else {
            deckCount = DECK_COUNT60;
            deckSpells = 35;
            deckLands = 25;
        }

        for (int index = 0; index < deckCount.length; index++) {
            final int max = DECK_COST[index];
            addCardsToDeck(remainingCards, min, max, deckCount[index]);
            min = max + 1;
        }
        addCardsToDeck(remainingCards, 0, 4, deckSpells - deck.getMaindeckCards().size());
        addCardsToDeck(remainingCards, 5, 10, deckSpells - deck.getMaindeckCards().size());
        addLandsToDeck(allowedColors, setsToUse, landCardPool, callback);

        Deck returnedDeck = deck;
        deck = null;
        return returnedDeck;
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
     * Adds lands from non basic land (if provided), adds basic lands getting
     * them from provided {@link RateCallback}}.
     *
     * @param allowedColors
     * @param landCardPool
     * @param callback
     */
    private static void addLandsToDeck(List<ColoredManaSymbol> allowedColors, List<String> setsToUse, List<Card> landCardPool, RateCallback callback) {

        // Calculate statistics per color.
        final Map<String, Integer> colorCount = new HashMap<>();
        for (final Card card : deck.getCards()) {

            for (String symbol : card.getManaCostSymbols()) {
                int count = 0;
                symbol = symbol.replace("{", "").replace("}", "");
                if (isColoredMana(symbol)) {
                    for (ColoredManaSymbol allowed : allowedColors) {
                        if (symbol.contains(allowed.toString())) {
                            count++;
                        }
                    }
                    if (count > 0) {
                        Integer typeCount = colorCount.getOrDefault(symbol, 0);
                        typeCount += 1;
                        colorCount.put(symbol, typeCount);
                    }
                }
            }
        }

        // Add suitable non basic lands to deck in order of pack.
        final Map<String, Integer> colorSource = new HashMap<>();
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
        while (deck.getMaindeckCards().size() < deckSize) {
            ColoredManaSymbol bestColor = null;
            //Default to a color in the allowed colors
            if (allowedColors != null && !allowedColors.isEmpty()) {
                bestColor = allowedColors.get(RandomUtil.nextInt(allowedColors.size()));
            }
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
            final Card landCard = callback.getBestBasicLand(bestColor, setsToUse);
            Integer count = colorSource.get(bestColor.toString());
            count++;
            colorSource.put(bestColor.toString(), count);
            deck.getCards().add(landCard);
        }
    }

    private static class MageScoredCard {

        private Card card;
        private final int score;

        private static final int[] SINGLE_PENALTY = {0, 1, 1, 3, 6, 9};
        //private static final int DOUBLE_PENALTY[] = { 0, 0, 1, 2, 4, 6 };

        public MageScoredCard(Card card, List<ColoredManaSymbol> allowedColors, RateCallback cardRater) {
            this.card = card;

            int type;
            if (card.isCreature()) {
                type = 10;
            } else if (card.hasSubtype(SubType.EQUIPMENT, null)) {
                type = 8;
            } else if (card.hasSubtype(SubType.AURA, null)) {
                type = 5;
            } else if (card.isInstant()) {
                type = 7;
            } else {
                type = 6;
            }

            this.score
                    = // 5*card.getValue() + // not possible now
                    3 * cardRater.rateCard(card)
                            + // 3*card.getRemoval() + // not possible now
                            type + getManaCostScore(card, allowedColors);
        }

        private int getManaCostScore(Card card, List<ColoredManaSymbol> allowedColors) {
            int converted = card.getManaValue();
            final Map<String, Integer> singleCount = new HashMap<>();
            int maxSingleCount = 0;
            int multicolor = 0;
            Set<String> colors = new HashSet<>();
            for (String symbol : card.getManaCostSymbols()) {
                int count = 0;
                symbol = symbol.replace("{", "").replace("}", "");
                if (isColoredMana(symbol)) {
                    for (ColoredManaSymbol allowed : allowedColors) {
                        if (symbol.contains(allowed.toString())) {
                            count++;
                        }
                    }
                    // colored but no selected colors, go back with negative value
                    if (count == 0) {
                        return -30;
                    }
                    if (!colors.contains(symbol)) {
                        multicolor += 1;
                        colors.add(symbol);
                    }
                    Integer typeCount = singleCount.getOrDefault(symbol, 0);
                    typeCount += 1;
                    singleCount.put(symbol, typeCount);
                    maxSingleCount = Math.max(maxSingleCount, typeCount);
                }
            }
            int multicolorBonus = multicolor > 1 ? 30 : 0;
            maxSingleCount = Math.min(maxSingleCount, SINGLE_PENALTY.length - 1);
            return 2 * converted + 3 * (10 - SINGLE_PENALTY[maxSingleCount]/*-DOUBLE_PENALTY[doubleCount]*/) + multicolorBonus;
        }

        public int getScore() {
            return this.score;
        }

        public int getConvertedCost() {
            return this.card.getManaValue();
        }

        public Card getCard() {
            return this.card;
        }
    }

    protected static boolean isColoredMana(String symbol) {
        return symbol.equals("W") || symbol.equals("G") || symbol.equals("U") || symbol.equals("B") || symbol.equals("R") || symbol.contains("/");
    }

}
