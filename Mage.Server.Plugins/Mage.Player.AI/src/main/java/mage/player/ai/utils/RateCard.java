package mage.player.ai.utils;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.target.Target;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetCreaturePermanent;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.*;

/**
 * Class responsible for reading ratings from resources and rating gived cards.
 * Based on card relative ratings from resources and card parameters.
 *
 * @author nantuko
 */
public class RateCard {

    private static Map<String, Integer> ratings;
    private static Map<String, Integer> rated = new HashMap<String, Integer>();
    private static Integer min = Integer.MAX_VALUE, max = 0;

    /**
     * Rating that is given for new cards.
     * Ratings are in [1,10] range, so setting it high will make new cards appear more often.
     */
    private static final int DEFAULT_NOT_RATED_CARD_RATING = 4;

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
     * @return
     */
    public static int rateCard(Card card, List<Constants.ColoredManaSymbol> allowedColors) {
        if (allowedColors == null && rated.containsKey(card.getName())) {
            return rated.get(card.getName());
        }
        int type = 0;
        if (card.getCardType().contains(Constants.CardType.CREATURE)) {
            type = 15;
        } if (card.getCardType().contains(Constants.CardType.CREATURE)) {
            type = 10;
        } else if (card.getSubtype().contains("Equipment")) {
            type = 8;
        } else if (card.getSubtype().contains("Aura")) {
            type = 5;
        } else if (card.getCardType().contains(Constants.CardType.INSTANT)) {
            type = 7;
        } else {
            type = 6;
        }
        int score = 10 * getCardRating(card) + 2 * type + getManaCostScore(card, allowedColors)
                + 40 * isRemoval(card);
        if (allowedColors == null)
            rated.put(card.getName(), score);
        return score;
    }

    private static int isRemoval(Card card) {
        if (card.getSubtype().contains("Aura") || card.getCardType().contains(Constants.CardType.INSTANT)
                || card.getCardType().contains(Constants.CardType.SORCERY)) {

            for (Ability ability : card.getAbilities()) {
                for (Effect effect : ability.getEffects()) {
                    if (effect.getOutcome().equals(Constants.Outcome.Removal)) {
                        log.debug("Found removal: " + card.getName());
                        return 1;
                    }
                    if (effect.getOutcome().equals(Constants.Outcome.Damage)) {
                        if (effect instanceof DamageTargetEffect) {
                            DamageTargetEffect damageEffect = (DamageTargetEffect) effect;
                            if (damageEffect.getAmount() > 1) {
                                for (Target target : ability.getTargets()) {
                                    if (target instanceof TargetCreaturePermanent || target instanceof TargetCreatureOrPlayer) {
                                        log.debug("Found damage dealer: " + card.getName());
                                        return 1;
                                    }
                                }
                            }
                        }
                    }
                    if (effect.getOutcome().equals(Constants.Outcome.DestroyPermanent)) {
                        for (Target target : ability.getTargets()) {
                            if (target instanceof TargetCreaturePermanent) {
                                log.info("Found destroyer: " + card.getName());
                                return 1;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Return rating of the card.
     *
     * @param card Card to rate.
     * @return Rating number from [1;10].
     */
    public static int getCardRating(Card card) {
        if (ratings == null) {
            readRatings();
        }
        if (ratings.containsKey(card.getName())) {
            int r = ratings.get(card.getName());
            // normalize to [1..10]
            float f = 10.0f * (r - min) / (max - min);
            return (int) Math.round(f);
        }
        return DEFAULT_NOT_RATED_CARD_RATING;
    }

    /**
     * Reads ratings from resources.
     */
    private synchronized static void readRatings() {
        if (ratings == null) {
            ratings = new HashMap<String, Integer>();
            String filename = "/ratings.txt";
            try {
                InputStream is = RateCard.class.getResourceAsStream(filename);
                Scanner scanner = new Scanner(is);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] s = line.split(":");
                    if (s.length == 2) {
                        Integer rating = Integer.parseInt(s[0].trim());
                        String name = s[1].trim();
                        if (rating > max) {
                            max = rating;
                        }
                        if (rating < min) {
                            min = rating;
                        }
                        ratings.put(name, rating);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                ratings.clear(); // no rating available on exception
            }
        }
    }

    private static final int SINGLE_PENALTY[] = {0, 1, 1, 3, 6, 9};

    /**
     * Get manacost score.
     * Depends on chosen colors. Returns negative score for those cards that doesn't fit allowed colors.
     * If allowed colors are not chosen, then score based on converted cost is returned with penalty for heavy colored cards.
     *
     *
     * @param card
     * @param allowedColors Can be null.
     * @return
     */
    private static int getManaCostScore(Card card, List<Constants.ColoredManaSymbol> allowedColors) {
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
        final Map<String, Integer> singleCount = new HashMap<String, Integer>();
        int maxSingleCount = 0;
        for (String symbol : card.getManaCost().getSymbols()) {
            int count = 0;
            symbol = symbol.replace("{", "").replace("}", "");
            if (isColoredMana(symbol)) {
                for (Constants.ColoredManaSymbol allowed : allowedColors) {
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
        return 2 * converted + 3 * (10 - SINGLE_PENALTY[maxSingleCount]/*-DOUBLE_PENALTY[doubleCount]*/);
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
            s = s.replace("{","").replace("}","");
        }
        if (s.length() > 1) {
            return false;
        }
        return s.equals("W") || s.equals("G") || s.equals("U") || s.equals("B") || s.equals("R");
    }

    /**
     * Return number of color mana symbols in manacost.
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
     * @param card
     * @return
     */
    public static int getDifferentColorManaCount(Card card) {
        Set<String> symbols = new HashSet<String>();
        for (String symbol : card.getManaCost().getSymbols()) {
            if (isColoredMana(symbol)) {
                symbols.add(symbol);
            }
        }
        return symbols.size();
    }
}
