package mage.game.draft;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.repository.CardScanner;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.*;

/**
 * Class responsible for reading ratings from resources and rating given cards.
 * Based on card relative ratings from resources and card parameters.
 *
 * @author nantuko
 */
public final class RateCard {

    public static final boolean PRELOAD_CARD_RATINGS_ON_STARTUP = false; // warning, rating and card classes preloading can cause lags for users with low memory

    private static Map<String, Integer> baseRatings = new HashMap<>();
    private static final Map<String, Integer> rated = new HashMap<>();
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

    public static int rateCard(Card card, List<ColoredManaSymbol> allowedColors, boolean useCache) {
        if (card == null) {
            return 0;
        }

        if (useCache && allowedColors == null && rated.containsKey(card.getName())) {
            int rate = rated.get(card.getName());
            return rate;
        }

        int type;
        if (card.isPlaneswalker()) {
            type = 15;
        } else if (card.isCreature()) {
            type = 10;
        } else if (card.getSubtype().contains(SubType.EQUIPMENT)) {
            type = 8;
        } else if (card.getSubtype().contains(SubType.AURA)) {
            type = 5;
        } else if (card.isInstant()) {
            type = 7;
        } else {
            type = 6;
        }
        int score = getBaseCardScore(card) + 2 * type + getManaCostScore(card, allowedColors)
                + 40 * isRemoval(card);

        if (useCache && allowedColors == null)
            rated.put(card.getName(), score);

        return score;
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
        //static List<Effect> removalEffects =[BoostTargetEffect,BoostEnchantedEffect]
        if (effect instanceof BoostTargetEffect || effect instanceof BoostEnchantedEffect) {
            String text = effect.getText(null);
            if (text.contains("/-")) {
                // toughness reducer, aka removal
                return 1;
            }
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
        // same card name must have same rating

        // ratings from files
        // lazy loading on first request
        prepareAndLoadRatings();

        // ratings from card rarity
        // some cards can have different rarity -- it's will be used from first set
        int newRating;
        if (card.getRarity() != null) {
            switch (card.getRarity()) {
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
                    if (isBasicLand(card)) {
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

        int oldRating = baseRatings.getOrDefault(card.getName(), 0);
        if (oldRating != 0 && oldRating != newRating) {
            //log.info("card have different rating by sets: " + card.getName() + " (" + oldRating + " <> " + newRating + ")");
        }

        if (oldRating != 0) {
            return oldRating;
        } else {
            baseRatings.put(card.getName(), newRating);
            return newRating;
        }
    }

    /**
     * reads the list of sets that have ratings csv files and read each file
     */
    public synchronized static void prepareAndLoadRatings() {
        if (isLoaded) {
            return;
        }

        // load sets list
        List<String> setsToLoad = new LinkedList<>();
        try {
            InputStream is = RateCard.class.getResourceAsStream(RATINGS_SET_LIST);
            Scanner scanner = new Scanner(is);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.substring(0, 1).equals("#")) {
                    setsToLoad.add(line);
                }
            }
        } catch (Throwable e) {
            log.error("Failed to read ratings sets list file: " + RATINGS_SET_LIST, e);
        }

        // load set data
        String rateFile = "";
        try {
            for (String code : setsToLoad) {
                //log.info("Reading ratings for the set " + code);
                rateFile = RATINGS_DIR + code + ".csv";
                readFromFile(rateFile);
            }
        } catch (Exception e) {
            log.error("Failed to read ratings set file: " + rateFile, e);
        }

        isLoaded = true;
    }

    /**
     * reads ratings from the file
     */
    private synchronized static void readFromFile(String path) {
        // card must get max rating from multiple cards
        Integer min = Integer.MAX_VALUE, max = 0;
        Map<String, Integer> thisFileRatings = new HashMap<>();

        // load
        InputStream is = RateCard.class.getResourceAsStream(path);
        Scanner scanner = new Scanner(is);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] s = line.split(":");
            if (s.length == 2) {
                Integer rating = Integer.parseInt(s[1].trim());
                String name = s[0].trim();
                name = name.replace("’", "'");
                if (rating > max) {
                    max = rating;
                }
                if (rating < min) {
                    min = rating;
                }
                thisFileRatings.put(name, rating);
            }
        }

        // normalize for the file to [1..100]
        for (Map.Entry<String, Integer> ratingByName : thisFileRatings.entrySet()) {
            int r = ratingByName.getValue();
            String name = ratingByName.getKey();
            int newRating = (int) (100.0f * (r - min) / (max - min));
            int oldRating = baseRatings.getOrDefault(name, 0);
            if (newRating > oldRating) {
                baseRatings.put(name, newRating);
            }
        }
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
        int converted = card.getManaValue();
        if (allowedColors == null) {
            int colorPenalty = 0;
            for (String symbol : card.getManaCostSymbols()) {
                if (isColoredMana(symbol)) {
                    colorPenalty++;
                }
            }
            return 2 * (converted - colorPenalty + 1);
        }
        
        // Basic lands have no value so they're always treated as off-color
        if (isBasicLand(card)) {
            return OFF_COLOR_PENALTY;
        }
        
        final Map<String, Integer> singleCount = new HashMap<>();
        int maxSingleCount = 0;
        for (String symbol : card.getManaCostSymbols()) {
            int count = 0;
            symbol = symbol.replace("{", "").replace("}", "");
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

        int rate = 2 * converted + 3 * (10 - SINGLE_PENALTY[maxSingleCount]);
        if (singleCount.size() > 1 && singleCount.size() < 5) {
            rate += MULTICOLOR_BONUS;
        }
        return rate;
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
     * @param card
     * @return
     */
     public static boolean isBasicLand(Card card) {
        String name = card.getName();
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
