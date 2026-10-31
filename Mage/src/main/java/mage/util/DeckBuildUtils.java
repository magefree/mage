package mage.util;

import mage.cards.Card;
import mage.cards.SplitCard;
import mage.constants.ColoredManaSymbol;
import mage.filter.FilterMana;

import java.util.*;

public final class DeckBuildUtils {

    private static final List<ColoredManaSymbol> BASIC_LAND_COLORS = Arrays.asList(
            ColoredManaSymbol.W,
            ColoredManaSymbol.U,
            ColoredManaSymbol.B,
            ColoredManaSymbol.R,
            ColoredManaSymbol.G
    );

    /**
     * Returns the number of basic lands suggested to complete a deck
     * as an array of five ints: plains, islands, swamps, mountains, forests
     * Total number of lands always sufficient to reach deckSize
     */
    public static int[] landCountSuggestion(int deckSize, Set<Card> deckList) {
        int landsNeeded = deckSize - deckList.size();
        if (landsNeeded <= 0) {
            return new int[]{0, 0, 0, 0, 0};
        }

        // deck colors are unknown here, so find it from the deck itself
        Map<ColoredManaSymbol, Double> colorsNeed = countColoredManaSymbols(deckList, findDeckColors(deckList));
        Map<ColoredManaSymbol, Integer> lands;
        if (colorsNeed.isEmpty()) {
            // no colored mana symbols, so distribute evenly
            BASIC_LAND_COLORS.forEach(color -> colorsNeed.put(color, 1.0));
            lands = splitLandsByColors(landsNeeded, colorsNeed);
        } else {
            lands = splitBasicLandsByColors(landsNeeded, colorsNeed, deckList);
        }
        return BASIC_LAND_COLORS.stream()
                .mapToInt(color -> lands.getOrDefault(color, 0))
                .toArray();
    }

    /**
     * Deck colors by colored mana symbols of the cards (two colors hybrid symbols are ignored, e.g. {R/G})
     */
    public static List<ColoredManaSymbol> findDeckColors(Collection<Card> cards) {
        Set<ColoredManaSymbol> res = EnumSet.noneOf(ColoredManaSymbol.class);
        for (Card card : cards) {
            for (String symbol : card.getManaCostSymbols()) {
                List<ColoredManaSymbol> symbolColors = getSymbolColors(symbol);
                if (symbolColors.size() == 1) {
                    res.add(symbolColors.get(0));
                }
            }
        }
        return new ArrayList<>(res);
    }

    /**
     * Colored mana symbols of the cards by colors. Hybrid symbol is paid by one color only, so it counts
     * for the deck colors only (a half per color if both are deck colors), e.g. {R/G} in a green deck needs Forest only
     */
    public static Map<ColoredManaSymbol, Double> countColoredManaSymbols(Collection<Card> cards, List<ColoredManaSymbol> deckColors) {
        Map<ColoredManaSymbol, Double> res = new EnumMap<>(ColoredManaSymbol.class);
        for (Card card : cards) {
            for (String symbol : getCastableManaCostSymbols(card, deckColors)) {
                List<ColoredManaSymbol> symbolColors = getSymbolColors(symbol);
                List<ColoredManaSymbol> payColors = new ArrayList<>(symbolColors);
                payColors.retainAll(deckColors);
                if (payColors.isEmpty()) {
                    // off-color card in the deck
                    payColors = symbolColors;
                }
                for (ColoredManaSymbol color : payColors) {
                    res.merge(color, 1.0 / payColors.size(), Double::sum);
                }
            }
        }
        return res;
    }

    /**
     * Mana symbols of the card. Split and room cards: each half can be cast alone, so it uses halves with deck colors only
     * (example: Fire // Ice in a red deck needs Mountains only)
     */
    private static List<String> getCastableManaCostSymbols(Card card, List<ColoredManaSymbol> deckColors) {
        if (card instanceof SplitCard) {
            SplitCard splitCard = (SplitCard) card;
            List<String> res = new ArrayList<>();
            for (Card half : Arrays.asList(splitCard.getLeftHalfCard(), splitCard.getRightHalfCard())) {
                if (isCastableByColors(half.getManaCostSymbols(), deckColors)) {
                    res.addAll(half.getManaCostSymbols());
                }
            }
            if (!res.isEmpty()) {
                return res;
            }
        }
        return card.getManaCostSymbols();
    }

    private static boolean isCastableByColors(List<String> manaCostSymbols, List<ColoredManaSymbol> deckColors) {
        for (String symbol : manaCostSymbols) {
            List<ColoredManaSymbol> symbolColors = getSymbolColors(symbol);
            if (!symbolColors.isEmpty() && symbolColors.stream().noneMatch(deckColors::contains)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Split lands between colors by needed amount, total amount is always landsCount (largest remainder method)
     */
    public static Map<ColoredManaSymbol, Integer> splitLandsByColors(int landsCount, Map<ColoredManaSymbol, Double> colorsNeed) {
        Map<ColoredManaSymbol, Integer> res = new EnumMap<>(ColoredManaSymbol.class);
        double total = colorsNeed.values().stream().mapToDouble(Double::doubleValue).sum();
        if (landsCount <= 0 || total <= 0) {
            return res;
        }

        Map<ColoredManaSymbol, Double> remainders = new EnumMap<>(ColoredManaSymbol.class);
        int usedLands = 0;
        for (Map.Entry<ColoredManaSymbol, Double> entry : colorsNeed.entrySet()) {
            double exactLands = landsCount * entry.getValue() / total;
            int lands = (int) Math.floor(exactLands);
            res.put(entry.getKey(), lands);
            remainders.put(entry.getKey(), exactLands - lands);
            usedLands += lands;
        }

        // remaining lands go to the colors with the biggest remainders
        List<ColoredManaSymbol> byRemainder = new ArrayList<>(remainders.keySet());
        byRemainder.sort((c1, c2) -> Double.compare(remainders.get(c2), remainders.get(c1)));
        for (int i = 0; i < landsCount - usedLands; i++) {
            ColoredManaSymbol color = byRemainder.get(i);
            res.put(color, res.get(color) + 1);
        }
        return res;
    }

    /**
     * Split basic lands between colors by needed amount, nonbasic lands of the deck are used as color sources too
     * (example: a G/W deck with 3 green nonbasic lands needs fewer Forests), total amount is always basicLandsCount
     */
    public static Map<ColoredManaSymbol, Integer> splitBasicLandsByColors(int basicLandsCount, Map<ColoredManaSymbol, Double> colorsNeed, Collection<Card> deckCards) {
        Map<ColoredManaSymbol, Integer> sources = countNonBasicLandSources(deckCards);
        double totalNeed = colorsNeed.values().stream().mapToDouble(Double::doubleValue).sum();
        if (sources.isEmpty() || basicLandsCount <= 0 || totalNeed <= 0) {
            return splitLandsByColors(basicLandsCount, colorsNeed);
        }

        // all lands of the deck must give sources by needed amount, so basic lands cover the missing part only
        int nonBasicLandsCount = (int) deckCards.stream().filter(DeckBuildUtils::isNonBasicLand).count();
        int allLandsCount = basicLandsCount + nonBasicLandsCount;
        Map<ColoredManaSymbol, Double> colorsMissing = new EnumMap<>(ColoredManaSymbol.class);
        for (Map.Entry<ColoredManaSymbol, Double> entry : colorsNeed.entrySet()) {
            double missing = allLandsCount * entry.getValue() / totalNeed - sources.getOrDefault(entry.getKey(), 0);
            if (missing > 0) {
                colorsMissing.put(entry.getKey(), missing);
            }
        }
        if (colorsMissing.isEmpty()) {
            // nonbasic lands give enough sources for all colors
            return splitLandsByColors(basicLandsCount, colorsNeed);
        }
        return splitLandsByColors(basicLandsCount, colorsMissing);
    }

    /**
     * Nonbasic lands as color sources: a land counts for every color of its color identity (example: a G/U dual land
     * is a source of G and U), lands without colored mana symbols (any color, colorless) are not counted
     */
    public static Map<ColoredManaSymbol, Integer> countNonBasicLandSources(Collection<Card> cards) {
        Map<ColoredManaSymbol, Integer> res = new EnumMap<>(ColoredManaSymbol.class);
        cards.stream()
                .filter(DeckBuildUtils::isNonBasicLand)
                .forEach(card -> {
                    FilterMana colors = ManaUtil.getColorIdentity(card);
                    if (colors.isWhite()) {
                        res.merge(ColoredManaSymbol.W, 1, Integer::sum);
                    }
                    if (colors.isBlue()) {
                        res.merge(ColoredManaSymbol.U, 1, Integer::sum);
                    }
                    if (colors.isBlack()) {
                        res.merge(ColoredManaSymbol.B, 1, Integer::sum);
                    }
                    if (colors.isRed()) {
                        res.merge(ColoredManaSymbol.R, 1, Integer::sum);
                    }
                    if (colors.isGreen()) {
                        res.merge(ColoredManaSymbol.G, 1, Integer::sum);
                    }
                });
        return res;
    }

    private static boolean isNonBasicLand(Card card) {
        return card.isLand() && !card.isBasic();
    }

    /**
     * Colors of the mana symbol, examples: {G} - G, {R/G} - R and G, {2/G} - G, {G/P} - G, {2} - nothing
     */
    private static List<ColoredManaSymbol> getSymbolColors(String symbol) {
        List<ColoredManaSymbol> res = new ArrayList<>();
        for (String part : symbol.replace("{", "").replace("}", "").split("/")) {
            if (part.length() == 1 && "WUBRG".contains(part)) {
                res.add(ColoredManaSymbol.lookup(part.charAt(0)));
            }
        }
        return res;
    }
}
