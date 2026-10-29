package mage.cards;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.*;
import mage.cards.decks.CardNameUtil;
import mage.cards.repository.CardScanner;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubType;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;
import mage.util.ManaUtil;
import mage.view.CardView;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class responsible for reading ratings from resources and rating given cards.
 * Based on card relative ratings from resources and card parameters.
 *
 * @author nantuko
 */
public final class RateCard {

    public static final boolean PRELOAD_CARD_RATINGS_ON_STARTUP = false; // warning, rating and card classes preloading can cause lags for users with low memory

    // caches are shared by all threads and AI/bots, so make sure it's thread safe
    // draft rating is a card place among the cards of one set, so same card can have diff ratings in diff sets
    private static final Map<String, Map<String, Integer>> setRatings = new ConcurrentHashMap<>(); // set code -> card name -> rating from set file
    private static final Map<String, Integer> bonusRatings = new ConcurrentHashMap<>(); // card name -> rating from a file of another set (bonus sheet, example: OTP cards in OTJ file)
    private static final Map<String, Integer> nameRatings = new ConcurrentHashMap<>(); // card name -> max rating from all files (for sets without ratings file)
    private static final Map<String, Integer> baseRatings = new ConcurrentHashMap<>(); // set code + card name -> base rating
    private static final Map<String, Integer> ratedCard = new ConcurrentHashMap<>(); // set code + card name -> score
    private static final Map<String, Integer> ratedCardView = new ConcurrentHashMap<>(); // Rating is not exactly the same for CardView, so cached in a different map.
    private static boolean isLoaded = false;

    /**
     * Rating that is given for new cards.
     * Ratings are in [1,10] range, so setting it high will make new cards appear more often.
     * nowadays, cards that are more rare are more powerful, lets trust that and play the shiny cards.
     */
    private static final int DEFAULT_BASIC_LAND_RATING = 1;
    private static final int DEFAULT_NOT_RATED_CARD_RATING = 40;
    private static final int DEFAULT_NOT_RATED_UNCOMMON_RATING = 60;
    private static final int DEFAULT_NOT_RATED_RARE_RATING = 75;
    private static final int DEFAULT_NOT_RATED_MYTHIC_RATING = 90;
    
    // Cards that aren't in the deck's colors get a penalty to their rating
    private static final int OFF_COLOR_PENALTY = -100;

    // Score of a card without ratings (rarity + type + mana + removal) must be comparable with the file ratings [1..100]
    // of other cards in the same booster or pool (mixed sets, cubes, cards without ratings in a set with ratings),
    // so it uses a regression from all 17Lands sets (8622 cards): rating = 0.5 * heuristic score (spearman 0.46)
    private static final float HEURISTIC_TO_RATING_SCALE = 0.5f;

    private static String RATINGS_DIR = "/ratings/";
    private static String RATINGS_SET_LIST = RATINGS_DIR + "setsWithRatings.csv";

    private static final Logger log = Logger.getLogger(RateCard.class);

    /**
     * Hide constructor.
     */
    private RateCard() {
    }

    public static void bootstrapCardsAndRatings() {
        // preload cards and ratings
        log.info("Loading cards and rating...");
        List<Card> cards = CardScanner.getAllCards(false);
        for (Card card : cards) {
            RateCard.rateCard(card, null);
        }
    }

    /**
     * Get absolute score of the card.
     * Depends on type, manacost, rating.
     * If allowedColors is null then the rating is retrieved from the cache
     *
     * @param card
     * @param allowedColors
     * @return
     */
    public static int rateCard(Card card, List<ColoredManaSymbol> allowedColors) {
        return rateCard(card, allowedColors, true);
    }

    public static int rateCard(CardView cardview, List<ColoredManaSymbol> allowedColors) {
        return rateCard(cardview, allowedColors, true);
    }

    public static int rateCard(Card card, List<ColoredManaSymbol> allowedColors, boolean useCache) {
        if (card == null) {
            return 0;
        }

        String cacheKey = getCardKey(card.getExpansionSetCode(), card.getName());
        if (useCache && allowedColors.isEmpty()) {
            Integer cachedScore = ratedCard.get(cacheKey);
            if (cachedScore != null) {
                return cachedScore;
            }
        }

        int score;
        if (hasFileRating(card.getName(), card.getExpansionSetCode())) {
            // file rating already contains the card's value (type, removal, mana cost), so use it as is
            // (any bonuses on top make bot's picks worse, checked on real human picks from 17Lands)
            score = getBaseCardScore(card);
        } else {
            // no ratings, so try to find card's value by rarity and other params
            int heuristicScore = getBaseCardScore(card) + 2 * typeMultiplier(card)
                    + getManaCostScore(card, Collections.emptyList()) + 40 * isRemoval(card);
            score = Math.round(heuristicScore * HEURISTIC_TO_RATING_SCALE);
        }
        if (isOffColorCard(card, allowedColors)) {
            score += OFF_COLOR_PENALTY;
        }

        if (useCache && allowedColors.isEmpty()) {
            ratedCard.put(cacheKey, score);
        }

        return score;
    }

    public static int rateCard(CardView cardview, List<ColoredManaSymbol> allowedColors, boolean useCache) {
        if (cardview == null) {
            return 0;
        }

        String cacheKey = getCardKey(cardview.getExpansionSetCode(), cardview.getName());
        if (useCache && allowedColors.isEmpty()) {
            Integer cachedScore = ratedCardView.get(cacheKey);
            if (cachedScore != null) {
                return cachedScore;
            }
        }

        // same as for card
        int score;
        if (hasFileRating(cardview.getName(), cardview.getExpansionSetCode())) {
            score = getBaseCardScore(cardview);
        } else {
            // Cardview does not have enough info to know the card is a removal.
            int heuristicScore = getBaseCardScore(cardview) + 2 * typeMultiplier(cardview)
                    + getManaCostScore(cardview, Collections.emptyList());
            score = Math.round(heuristicScore * HEURISTIC_TO_RATING_SCALE);
        }
        if (isOffColorCard(cardview, allowedColors)) {
            score += OFF_COLOR_PENALTY;
        }

        if (useCache && allowedColors.isEmpty()) {
            ratedCardView.put(cacheKey, score);
        }

        return score;
    }

    protected static int typeMultiplier(Card card) {
        return typeMultiplier(
                card.isPlaneswalker(),
                card.isCreature(),
                card.getSubtype().contains(SubType.EQUIPMENT),
                card.getSubtype().contains(SubType.AURA),
                card.isInstant()
        );
    }

    protected static int typeMultiplier(CardView cardview) {
        return typeMultiplier(
                cardview.isPlaneswalker(),
                cardview.isCreature(),
                cardview.getSubTypes().contains(SubType.EQUIPMENT),
                cardview.getSubTypes().contains(SubType.AURA),
                cardview.isInstant()
        );
    }

    protected static int typeMultiplier(
            boolean isPlaneswalker,
            boolean isCreature,
            boolean isEquipment,
            boolean isAura,
            boolean isInstant
    ) {
        if (isPlaneswalker) {
            return 15;
        } else if (isCreature) {
            return 10;
        } else if (isEquipment) {
            return 8;
        } else if (isAura) {
            return 5;
        } else if (isInstant) {
            return 7;
        } else {
            return 6;
        }
    }

    private static int isRemoval(Card card) {
        if (card.isEnchantment() || card.isInstantOrSorcery()) {
            for (Ability ability : card.getAbilities()) {
                for (Effect effect : ability.getEffects()) {
                    if (isEffectRemoval(card, ability, effect) == 1) {
                        return 1;
                    }
                }
                for (Mode mode : ability.getModes().values()) {
                    for (Effect effect : mode.getEffects()) {
                        if (isEffectRemoval(card, ability, effect) == 1) {
                            return 1;
                        }
                    }
                }
            }
        }
        return 0;
    }

    private static int isEffectRemoval(Card card, Ability ability, Effect effect) {
        // it's effect relates score, do not use custom outcome from ability
        if (effect.getOutcome() == Outcome.Removal) {
            // found removal
            return 1;
        }
        if (effect instanceof FightTargetsEffect
                || effect instanceof DamageWithPowerFromOneToAnotherTargetEffect
                || effect instanceof DamageWithPowerFromSourceToAnotherTargetEffect) {
            return 1;
        }
        if (effect.getOutcome() == Outcome.Damage || effect instanceof DamageTargetEffect) {
            for (Target target : ability.getTargets()) {
                if (!(target instanceof TargetPlayerOrPlaneswalker)) {
                    // found damage dealer
                    return 1;
                }
            }
        }
        if (effect.getOutcome() == Outcome.DestroyPermanent ||
                effect instanceof DestroyTargetEffect ||
                effect instanceof ExileTargetEffect ||
                effect instanceof ExileUntilSourceLeavesEffect) {
            for (Target target : ability.getTargets()) {
                if (target instanceof TargetPermanent) {
                    // found destroyer/exiler
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * Return rating of the card.
     *
     * @param card Card to rate.
     * @return Rating number from [1:100].
     */
    public static int getBaseCardScore(Card card) {
        return getBaseScore(
                card.getName(),
                card.getExpansionSetCode(),
                card.getRarity()
        );
    }

    public static int getBaseCardScore(CardView cardview) {
        return getBaseScore(
                cardview.getName(),
                cardview.getExpansionSetCode(),
                cardview.getRarity()
        );
    }

    protected static int getBaseScore(String name, String setCode, Rarity rarity) {
        // same card name in same set must have same rating

        // ratings from files
        // lazy loading on first request
        prepareAndLoadRatings();

        String key = getCardKey(setCode, name);
        Integer cachedRating = baseRatings.get(key);
        if (cachedRating != null) {
            return cachedRating;
        }

        Integer newRating = findFileRating(name, setCode);
        if (newRating == null) {
            newRating = getRarityRating(name, rarity);
        }

        // some cards can have different rarity in same set -- it's will be used from first card
        Integer oldRating = baseRatings.putIfAbsent(key, newRating);
        return oldRating != null ? oldRating : newRating;
    }

    /**
     * Rating from files, priority:
     * 1. file of the card's set;
     * 2. file of another set that contains the card as a bonus sheet card (example: OTP cards in OTJ boosters and OTJ file);
     * 3. max rating from all files (reprint in a set without file, so it can be another scale than other cards of the set)
     *
     * @return null for card without ratings
     */
    private static Integer findFileRating(String name, String setCode) {
        Map<String, Integer> ratings = setRatings.get(getSetKey(setCode));
        if (ratings != null && ratings.containsKey(name)) {
            return ratings.get(name);
        }
        if (bonusRatings.containsKey(name)) {
            return bonusRatings.get(name);
        }
        return nameRatings.get(name);
    }

    /**
     * Card has a rating from files (not a rarity based rating)
     */
    public static boolean hasFileRating(String name, String setCode) {
        prepareAndLoadRatings();
        return findFileRating(name, setCode) != null;
    }

    private static int getRarityRating(String name, Rarity rarity) {
        int newRating;
        if (rarity != null) {
            switch (rarity) {
                case COMMON:
                    newRating = DEFAULT_NOT_RATED_CARD_RATING;
                    break;
                case UNCOMMON:
                    newRating = DEFAULT_NOT_RATED_UNCOMMON_RATING;
                    break;
                case RARE:
                    newRating = DEFAULT_NOT_RATED_RARE_RATING;
                    break;
                case MYTHIC:
                    newRating = DEFAULT_NOT_RATED_MYTHIC_RATING;
                    break;
                default:
                    if (isBasicLand(name)) {
                        newRating = DEFAULT_BASIC_LAND_RATING;
                    } else {
                        newRating = DEFAULT_NOT_RATED_CARD_RATING;
                    }
                    break;
            }
        } else {
            // tokens
            newRating = DEFAULT_NOT_RATED_CARD_RATING;
        }
        return newRating;
    }

    private static String getSetKey(String setCode) {
        return setCode == null ? "" : setCode.toUpperCase(Locale.ENGLISH);
    }

    private static String getCardKey(String setCode, String name) {
        return getSetKey(setCode) + "-" + name;
    }

    /**
     * reads the list of sets that have ratings csv files and read each file
     */
    public synchronized static void prepareAndLoadRatings() {
        if (isLoaded) {
            return;
        }

        // load sets list
        List<String> setsToLoad = new ArrayList<>();
        try {
            setsToLoad.addAll(parseSetsList(readLines(RATINGS_SET_LIST)));
        } catch (Throwable e) {
            log.error("Failed to read ratings sets list file: " + RATINGS_SET_LIST, e);
        }

        // load set data
        for (String code : setsToLoad) {
            // one broken file must not stop other sets
            String rateFile = RATINGS_DIR + code + ".csv";
            try {
                readFromFile(rateFile, code);
            } catch (Exception e) {
                log.error("Failed to read ratings set file: " + rateFile, e);
            }
        }

        isLoaded = true;
    }

    /**
     * Set codes from the lines of setsWithRatings.csv (comments and empty lines are ignored)
     */
    public static List<String> parseSetsList(List<String> lines) {
        List<String> res = new ArrayList<>();
        for (String line : lines) {
            String code = line.trim();
            if (!code.isEmpty() && !code.startsWith("#")) {
                res.add(code);
            }
        }
        return res;
    }

    private static List<String> readLines(String path) {
        // warning, files contain non ascii card names, so use utf-8 (default charset depends on OS before java 18)
        List<String> res = new ArrayList<>();
        try (Scanner scanner = new Scanner(RateCard.class.getResourceAsStream(path), "UTF-8")) {
            while (scanner.hasNextLine()) {
                res.add(scanner.nextLine());
            }
        }
        return res;
    }

    /**
     * reads ratings from the file
     */
    private synchronized static void readFromFile(String path, String setCode) {
        Integer min = Integer.MAX_VALUE, max = 0;
        Map<String, Integer> thisFileRatings = new HashMap<>();

        // load
        for (String line : readLines(path)) {
            String[] s = line.split(":");
            if (s.length == 2) {
                Integer rating = Integer.parseInt(s[1].trim());
                // files keep original names from the data source (17Lands: Tura Kennerüd), xmage uses ascii names
                String name = CardNameUtil.normalizeCardName(s[0].trim());
                if (rating > max) {
                    max = rating;
                }
                if (rating < min) {
                    min = rating;
                }
                thisFileRatings.put(name, rating);
            }
        }

        // cards from other sets inside the file (bonus sheets like OTP cards in OTJ file)
        ExpansionSet set = Sets.findSet(setCode.toUpperCase(Locale.ENGLISH));
        Set<String> setCardNames = new HashSet<>();
        if (set != null) {
            set.getSetCardInfo().forEach(info -> setCardNames.add(info.getName()));
        }

        // normalize for the file to [1..100]
        Map<String, Integer> fileRatings = new HashMap<>();
        for (Map.Entry<String, Integer> ratingByName : thisFileRatings.entrySet()) {
            int r = ratingByName.getValue();
            String name = ratingByName.getKey();
            int newRating = max > min ? 1 + (int) (99.0f * (r - min) / (max - min)) : 100;
            fileRatings.put(name, newRating);
            nameRatings.merge(name, newRating, Math::max);
            if (set != null && !setCardNames.contains(name)) {
                bonusRatings.merge(name, newRating, Math::max);
            }
        }
        setRatings.put(getSetKey(setCode), fileRatings);
    }

    private static final int[] SINGLE_PENALTY = {0, 1, 1, 3, 6, 9};
    private static final int MULTICOLOR_BONUS = 15;

    /**
     * Get manacost score.
     * Depends on chosen colors. Returns negative score for those cards that doesn't fit allowed colors.
     * If allowed colors are not chosen, then score based on converted cost is returned with penalty for heavy colored cards.
     * gives bonus to multicolor cards that fit within allowed colors and if allowed colors is <5
     *
     * @param card
     * @param allowedColors Can be null.
     * @return
     */

    private static int getManaCostScore(Card card, List<ColoredManaSymbol> allowedColors) {
        // lands have no mana cost, so check the colors they make instead
        if (isOffColorNonBasicLand(card, allowedColors)) {
            return OFF_COLOR_PENALTY;
        }
        // split and room cards: each half can be cast alone, so use the best half
        // (whole card contains mana symbols of both halves)
        if (card instanceof SplitCard) {
            SplitCard splitCard = (SplitCard) card;
            return Math.max(
                    getManaCostScore(splitCard.getLeftHalfCard(), allowedColors),
                    getManaCostScore(splitCard.getRightHalfCard(), allowedColors)
            );
        }
        return getManaCostScore(
                card.getName(),
                card.getManaCostSymbols(),
                allowedColors
        );
    }

    /**
     * Card with mana symbols of other colors (it's useless for a deck with allowed colors)
     */
    public static boolean isOffColorCard(Card card, List<ColoredManaSymbol> allowedColors) {
        return !allowedColors.isEmpty() && getManaCostScore(card, allowedColors) == OFF_COLOR_PENALTY;
    }

    public static boolean isOffColorCard(CardView cardview, List<ColoredManaSymbol> allowedColors) {
        return !allowedColors.isEmpty() && getManaCostScore(cardview, allowedColors) == OFF_COLOR_PENALTY;
    }

    /**
     * Nonbasic land with colored mana in its rules (color identity), but without any allowed color
     * (lands without colored mana symbols like "any color" or colorless ones are fine for any deck)
     */
    private static boolean isOffColorNonBasicLand(Card card, List<ColoredManaSymbol> allowedColors) {
        if (allowedColors.isEmpty() || !card.isLand() || card.isBasic()) {
            return false;
        }
        List<ObjectColor> landColors = ManaUtil.getColorIdentity(card).getColors();
        if (landColors.isEmpty()) {
            return false;
        }
        ObjectColor deckColors = new ObjectColor();
        for (ColoredManaSymbol allowedColor : allowedColors) {
            deckColors.addColor(new ObjectColor(allowedColor.toString()));
        }
        for (ObjectColor landColor : landColors) {
            if (deckColors.shares(landColor)) {
                return false;
            }
        }
        return true;
    }

    private static int getManaCostScore(CardView cardview, List<ColoredManaSymbol> allowedColors) {
        return getManaCostScore(
                cardview.getName(),
                cardview.getManaCostSymbols(),
                allowedColors
        );
    }

    private static int getManaCostScore(String name, List<String> manaCostSymbols, List<ColoredManaSymbol> allowedColors) {
        // mana value is not used here: an expensive card must not be better than a cheap one by its cost only,
        // a deck curve is checked by the deck building logic instead
        if (allowedColors.isEmpty()) {
            int colorPenalty = 0;
            for (String symbol : manaCostSymbols) {
                if (isColoredMana(symbol)) {
                    colorPenalty++;
                }
            }
            return -2 * colorPenalty;
        }

        // Basic lands have no value so they're always treated as off-color
        if (isBasicLand(name)) {
            return OFF_COLOR_PENALTY;
        }

        final Map<String, Integer> singleCount = new HashMap<>();
        int maxSingleCount = 0;
        for (String symbol : manaCostSymbols) {
            int count = 0;
            symbol = symbol.replace("{", "").replace("}", "");
            if (isOffColorHybridMana(symbol, allowedColors)) {
                return OFF_COLOR_PENALTY;
            }
            if (isColoredMana(symbol)) {
                for (ColoredManaSymbol allowed : allowedColors) {
                    if (allowed.toString().equals(symbol)) {
                        count++;
                    }
                }
                if (count == 0) {
                    return OFF_COLOR_PENALTY;
                }
                Integer typeCount = singleCount.get(symbol);
                if (typeCount == null) {
                    typeCount = 0;
                }
                typeCount += 1;
                singleCount.put(symbol, typeCount);
                maxSingleCount = Math.max(maxSingleCount, typeCount);
            }
        }
        if (maxSingleCount > 5)
            maxSingleCount = 5;

        int rate = 3 * (10 - SINGLE_PENALTY[maxSingleCount]);
        if (singleCount.size() > 1 && singleCount.size() < 5) {
            rate += MULTICOLOR_BONUS;
        }
        return rate;
    }

    /**
     * Hybrid mana symbol of two colors (example: R/G) without any allowed color
     * (mono hybrid like 2/G and phyrexian like G/P can be paid by generic mana or life, so it's fine)
     */
    private static boolean isOffColorHybridMana(String symbol, List<ColoredManaSymbol> allowedColors) {
        String[] parts = symbol.split("/");
        if (parts.length != 2 || !isColoredMana(parts[0]) || !isColoredMana(parts[1])) {
            return false;
        }
        for (ColoredManaSymbol allowed : allowedColors) {
            if (allowed.toString().equals(parts[0]) || allowed.toString().equals(parts[1])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines whether mana symbol is color.
     *
     * @param symbol
     * @return
     */
    public static boolean isColoredMana(String symbol) {
        String s = symbol;
        if (s.length() > 1) {
            s = s.replace("{", "").replace("}", "");
        }
        if (s.length() > 1) {
            return false;
        }
        return s.equals("W") || s.equals("G") || s.equals("U") || s.equals("B") || s.equals("R");
    }

    /**
     * Return number of color mana symbols in manacost.
     *
     * @param card
     * @return
     */
    public static int getColorManaCount(Card card) {
        int count = 0;
        for (String symbol : card.getManaCostSymbols()) {
            if (isColoredMana(symbol)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Return number of different color mana symbols in manacost.
     *
     * @param card
     * @return
     */
    public static int getDifferentColorManaCount(Card card) {
        Set<String> symbols = new HashSet<>();
        for (String symbol : card.getManaCostSymbols()) {
            if (isColoredMana(symbol)) {
                symbols.add(symbol);
            }
        }
        return symbols.size();
    }

    /**
     * Return true if the card is one of the basic land types that can be added to the deck for free.
     *
     * @param name
     * @return
     */
    public static boolean isBasicLand(String name) {
        if (name.equals("Plains")
                || name.equals("Island")
                || name.equals("Swamp")
                || name.equals("Mountain")
                || name.equals("Forest")) {
            return true;
        } else {
            return false;
        }
    }
}
