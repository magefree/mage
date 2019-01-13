package mage.player.ai.utils;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubType;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetAttackingOrBlockingCreature;
import mage.target.common.TargetCreaturePermanent;
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

    private static Map<String, Integer> ratings;
    private static List<String> setsWithRatingsToBeLoaded;
    private static final Map<String, Integer> rated = new HashMap<>();

    /**
     * Rating that is given for new cards.
     * Ratings are in [1,10] range, so setting it high will make new cards appear more often.
     * nowadays, cards that are more rare are more powerful, lets trust that and play the shiny cards.
     */
    private static final int DEFAULT_NOT_RATED_CARD_RATING = 40;
    private static final int DEFAULT_NOT_RATED_UNCOMMON_RATING = 60;
    private static final int DEFAULT_NOT_RATED_RARE_RATING = 75;
    private static final int DEFAULT_NOT_RATED_MYTHIC_RATING = 90;

    private static String RATINGS_DIR = "/ratings/";
    private static String RATINGS_SET_LIST = RATINGS_DIR + "setsWithRatings.csv";

    private static final Logger log = Logger.getLogger(RateCard.class);

    /**
     * Hide constructor.
     */
    private RateCard() {
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
        if (allowedColors == null && rated.containsKey(card.getName())) {
            int rate = rated.get(card.getName());
            return rate;
        }
        int type;
        if (card.isPlaneswalker()) {
            type = 15;
        } else if (card.isCreature()) {
            type = 10;
        } else if (card.getSubtype(null).contains(SubType.EQUIPMENT)) {
            type = 8;
        } else if (card.getSubtype(null).contains(SubType.AURA)) {
            type = 5;
        } else if (card.isInstant()) {
            type = 7;
        } else {
            type = 6;
        }
        int score = getCardRating(card) + 2 * type + getManaCostScore(card, allowedColors)
                + 40 * isRemoval(card);
        if (allowedColors == null)
            rated.put(card.getName(), score);
        return score;
    }

    private static int isRemoval(Card card) {
        if (card.isEnchantment() || card.isInstant() || card.isSorcery()) {

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
        if (effect.getOutcome() == Outcome.Removal) {
            log.debug("Found removal: " + card.getName());
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
        if (effect instanceof FightTargetsEffect || effect instanceof DamageWithPowerTargetEffect) {
            return 1;
        }
        if (effect.getOutcome() == Outcome.Damage || effect instanceof DamageTargetEffect) {
            for (Target target : ability.getTargets()) {
                if (!(target instanceof TargetPlayerOrPlaneswalker)) {
                    log.debug("Found damage dealer: " + card.getName());
                    return 1;
                }
            }
        }
        if (effect.getOutcome() == Outcome.DestroyPermanent ||
                effect instanceof DestroyTargetEffect ||
                effect instanceof ExileTargetEffect ||
                effect instanceof ExileUntilSourceLeavesEffect) {
            for (Target target : ability.getTargets()) {
                if (target instanceof TargetCreaturePermanent ||
                        target instanceof TargetAttackingCreature ||
                        target instanceof TargetAttackingOrBlockingCreature ||
                        target instanceof TargetPermanent) {
                    log.debug("Found destroyer/exiler: " + card.getName());
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
    public static int getCardRating(Card card) {
        readRatingSetList();
        String exp = card.getExpansionSetCode().toLowerCase();
        readRatings(exp);

        if (ratings != null && ratings.containsKey(card.getName())) {
            return ratings.get(card.getName());
        }

        Rarity r = card.getRarity();
        if (Rarity.COMMON == r) {
            return DEFAULT_NOT_RATED_CARD_RATING;
        } else if (Rarity.UNCOMMON == r) {
            return DEFAULT_NOT_RATED_UNCOMMON_RATING;
        } else if (Rarity.RARE == r) {
            return DEFAULT_NOT_RATED_RARE_RATING;
        } else if (Rarity.MYTHIC == r) {
            return DEFAULT_NOT_RATED_MYTHIC_RATING;
        }
        return DEFAULT_NOT_RATED_CARD_RATING;
    }

    /**
     * reads the list of sets that have ratings csv files
     * populates the setsWithRatingsToBeLoaded
     */
    private synchronized static void readRatingSetList() {
        try {
            if (setsWithRatingsToBeLoaded == null) {
                setsWithRatingsToBeLoaded = new LinkedList<>();
                InputStream is = RateCard.class.getResourceAsStream(RATINGS_SET_LIST);
                Scanner scanner = new Scanner(is);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (!line.substring(0, 1).equals("#")) {
                        setsWithRatingsToBeLoaded.add(line);
                    }
                }
            }
        } catch (Exception e) {
            log.info("failed to read ratings set list file: " + RATINGS_SET_LIST);
            e.printStackTrace();
        }
    }

    /**
     * Reads ratings from resources and loads them into ratings map
     */
    private synchronized static void readRatings(String expCode) {
        if (ratings == null) {
            ratings = new HashMap<>();
        }
        if (setsWithRatingsToBeLoaded.contains(expCode)) {
            log.info("reading draftbot ratings for the set " + expCode);
            readFromFile(RATINGS_DIR + expCode + ".csv");
            setsWithRatingsToBeLoaded.remove(expCode);
        }
    }

    /**
     * reads ratings from the file
     */
    private synchronized static void readFromFile(String path) {
        Integer min = Integer.MAX_VALUE, max = 0;
        Map<String, Integer> thisFileRatings = new HashMap<>();
        try {
            InputStream is = RateCard.class.getResourceAsStream(path);
            Scanner scanner = new Scanner(is);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] s = line.split(":");
                if (s.length == 2) {
                    Integer rating = Integer.parseInt(s[1].trim());
                    String name = s[0].trim();
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
            for (String name : thisFileRatings.keySet()) {
                int r = thisFileRatings.get(name);
                int newrating = (int) (100.0f * (r - min) / (max - min));
                if (!ratings.containsKey(name) ||
                        (ratings.containsKey(name) && newrating > ratings.get(name))) {
                    ratings.put(name, newrating);
                }
            }
        } catch (Exception e) {
            log.info("failed to read ratings file: " + path);
            e.printStackTrace();
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
        int converted = card.getManaCost().convertedManaCost();
        if (allowedColors == null) {
            int colorPenalty = 0;
            for (String symbol : card.getManaCost().getSymbols()) {
                if (isColoredMana(symbol)) {
                    colorPenalty++;
                }
            }
            return 2 * (converted - colorPenalty + 1);
        }
        final Map<String, Integer> singleCount = new HashMap<>();
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
                    return -100;
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
        for (String symbol : card.getManaCost().getSymbols()) {
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
        for (String symbol : card.getManaCost().getSymbols()) {
            if (isColoredMana(symbol)) {
                symbols.add(symbol);
            }
        }
        return symbols.size();
    }
}
