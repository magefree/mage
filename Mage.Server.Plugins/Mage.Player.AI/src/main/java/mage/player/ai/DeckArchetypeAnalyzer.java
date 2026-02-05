package mage.player.ai;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.*;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.*;

/**
 * Analyzes a player's deck composition to determine its archetype (aggro vs control).
 * Returns an "aggro score" from 0 (pure control) to 100 (pure aggro).
 *
 * The analysis considers:
 * - Average mana value (low = aggro)
 * - Creature density (high = aggro)
 * - Removal spell count (high = control)
 * - Haste creature count (high = aggro)
 * - High-CMC bombs (high = control)
 *
 * For opponents, analysis is based only on revealed information (cards played,
 * graveyard, revealed from hand) to simulate realistic game knowledge.
 *
 * @author AI improvements
 */
public final class DeckArchetypeAnalyzer {

    // Cache aggro scores per game to avoid recalculating
    private static final Map<String, Integer> aggroScoreCache = new WeakHashMap<>();

    // Cache for revealed-only opponent analysis
    private static final Map<String, Integer> revealedAggroScoreCache = new WeakHashMap<>();

    // Minimum cards needed for reliable opponent analysis
    private static final int MIN_REVEALED_CARDS_FOR_ANALYSIS = 3;

    private DeckArchetypeAnalyzer() {
        // Utility class
    }

    /**
     * Analyzes a player's deck and returns an aggro score from 0-100.
     * 0 = pure control (wants long games)
     * 50 = midrange (flexible)
     * 100 = pure aggro (wants short games)
     *
     * @param player The player whose deck to analyze
     * @param game The current game state
     * @return Aggro score from 0-100
     */
    public static int analyzeAggroScore(Player player, Game game) {
        if (player == null || game == null) {
            return 50; // Default to midrange
        }

        // Check cache first
        String cacheKey = player.getId().toString() + "_" + game.getId().toString();
        Integer cachedScore = aggroScoreCache.get(cacheKey);
        if (cachedScore != null) {
            return cachedScore;
        }

        // Gather all cards in the player's deck (all zones)
        List<Card> allCards = getAllDeckCards(player, game);
        if (allCards.isEmpty()) {
            return 50; // Default to midrange if no cards found
        }

        // Use the internal analysis method with full confidence (own deck)
        int score = analyzeCardList(allCards, game, false);

        // Cache the result
        aggroScoreCache.put(cacheKey, score);

        return score;
    }

    /**
     * Clears the cache. Call this at the start of a new game.
     */
    public static void clearCache() {
        aggroScoreCache.clear();
        revealedAggroScoreCache.clear();
    }

    /**
     * Analyzes an opponent's deck based ONLY on revealed information.
     * This simulates realistic game knowledge - we can only know about cards
     * the opponent has played, cards in their graveyard, and cards revealed from hand.
     *
     * Returns 50 (midrange/unknown) if insufficient cards have been revealed.
     *
     * @param opponent The opponent player to analyze
     * @param game The current game state
     * @return Aggro score from 0-100, or 50 if insufficient data
     */
    public static int analyzeOpponentFromRevealedInfo(Player opponent, Game game) {
        if (opponent == null || game == null) {
            return 50; // Default to midrange/unknown
        }

        // Build cache key that includes turn number so we re-evaluate as more is revealed
        String cacheKey = opponent.getId().toString() + "_" + game.getId().toString() + "_revealed_" + game.getTurnNum();
        Integer cachedScore = revealedAggroScoreCache.get(cacheKey);
        if (cachedScore != null) {
            return cachedScore;
        }

        // Gather only revealed cards
        List<Card> revealedCards = getRevealedCards(opponent, game);

        // If we haven't seen enough cards, return unknown (midrange assumption)
        if (revealedCards.size() < MIN_REVEALED_CARDS_FOR_ANALYSIS) {
            return 50;
        }

        // Analyze based on revealed cards with adjusted weights
        // (smaller sample size means we weight each indicator less heavily)
        int score = analyzeCardList(revealedCards, game, true);

        // Cache the result
        revealedAggroScoreCache.put(cacheKey, score);

        return score;
    }

    /**
     * Gets cards that have been revealed to the AI about the opponent:
     * - Cards on the battlefield (currently in play)
     * - Cards in the graveyard (have been played and died/resolved)
     * - Cards in exile that were owned by opponent
     * - Does NOT include opponent's hand or library (hidden information)
     */
    private static List<Card> getRevealedCards(Player opponent, Game game) {
        List<Card> cards = new ArrayList<>();

        // Battlefield permanents - we can see these
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(opponent.getId())) {
            Card card = game.getCard(permanent.getId());
            if (card != null) {
                cards.add(card);
            }
        }

        // Graveyard - public zone
        for (Card card : opponent.getGraveyard().getCards(game)) {
            if (card != null) {
                cards.add(card);
            }
        }

        // Exile zone - cards owned by opponent that we can see
        for (Card card : game.getExile().getAllCards(game)) {
            if (card != null && card.getOwnerId().equals(opponent.getId())) {
                cards.add(card);
            }
        }

        return cards;
    }

    /**
     * Internal method to analyze a list of cards and return an aggro score.
     * @param cards The cards to analyze
     * @param game The game state
     * @param isPartialInfo If true, uses more conservative modifiers for small sample sizes
     * @return Aggro score 0-100
     */
    private static int analyzeCardList(List<Card> cards, Game game, boolean isPartialInfo) {
        if (cards.isEmpty()) {
            return 50;
        }

        int score = 50; // Start at midrange

        // Calculate a confidence factor based on sample size
        // More cards = more confident in our analysis
        double confidenceFactor = isPartialInfo ? Math.min(1.0, cards.size() / 15.0) : 1.0;

        // Mana curve analysis
        double avgCMC = calculateAverageCMC(cards, game);
        score += (int) (getManaCurveModifier(avgCMC) * confidenceFactor);

        // Creature density
        double creatureRatio = calculateCreatureRatio(cards, game);
        score += (int) (getCreatureDensityModifier(creatureRatio) * confidenceFactor);

        // Removal density
        int removalCount = countRemovalSpells(cards, game);
        score += (int) (getRemovalModifier(removalCount, cards.size()) * confidenceFactor);

        // Haste creatures
        int hasteCount = countHasteCreatures(cards, game);
        score += (int) (getHasteModifier(hasteCount) * confidenceFactor);

        // Late-game bombs (CMC 6+)
        int bombCount = countHighCMCCards(cards, game, 6);
        score += (int) (getBombModifier(bombCount) * confidenceFactor);

        // Card draw spells
        int cardDrawCount = countCardDrawSpells(cards, game);
        score += (int) (getCardDrawModifier(cardDrawCount) * confidenceFactor);

        // Clamp to 0-100 range
        return Math.max(0, Math.min(100, score));
    }

    /**
     * Gets all cards that belong to the player's deck (library, hand, battlefield, graveyard).
     */
    private static List<Card> getAllDeckCards(Player player, Game game) {
        List<Card> cards = new ArrayList<>();

        // Library
        for (Card card : player.getLibrary().getCards(game)) {
            if (card != null) {
                cards.add(card);
            }
        }

        // Hand
        for (UUID cardId : player.getHand()) {
            Card card = game.getCard(cardId);
            if (card != null) {
                cards.add(card);
            }
        }

        // Battlefield permanents (get the card, not the permanent)
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(player.getId())) {
            Card card = game.getCard(permanent.getId());
            if (card != null) {
                cards.add(card);
            }
        }

        // Graveyard
        for (Card card : player.getGraveyard().getCards(game)) {
            if (card != null) {
                cards.add(card);
            }
        }

        return cards;
    }

    /**
     * Calculates the average mana value of non-land cards.
     */
    private static double calculateAverageCMC(List<Card> cards, Game game) {
        int totalCMC = 0;
        int nonLandCount = 0;

        for (Card card : cards) {
            if (!card.isLand(game)) {
                totalCMC += card.getManaValue();
                nonLandCount++;
            }
        }

        if (nonLandCount == 0) {
            return 3.0; // Default
        }

        return (double) totalCMC / nonLandCount;
    }

    /**
     * Returns score modifier based on mana curve.
     * Low curve = aggro bonus, high curve = control bonus.
     */
    private static int getManaCurveModifier(double avgCMC) {
        if (avgCMC < 2.0) {
            return 25;
        } else if (avgCMC < 2.5) {
            return 15;
        } else if (avgCMC < 3.0) {
            return 5;
        } else if (avgCMC < 3.5) {
            return -5;
        } else if (avgCMC < 4.0) {
            return -15;
        } else {
            return -25;
        }
    }

    /**
     * Calculates the ratio of creatures to total non-land cards.
     */
    private static double calculateCreatureRatio(List<Card> cards, Game game) {
        int creatureCount = 0;
        int nonLandCount = 0;

        for (Card card : cards) {
            if (!card.isLand(game)) {
                nonLandCount++;
                if (card.isCreature(game)) {
                    creatureCount++;
                }
            }
        }

        if (nonLandCount == 0) {
            return 0.4; // Default
        }

        return (double) creatureCount / nonLandCount;
    }

    /**
     * Returns score modifier based on creature density.
     * High creature count = aggro bonus.
     */
    private static int getCreatureDensityModifier(double creatureRatio) {
        if (creatureRatio > 0.6) {
            return 20;
        } else if (creatureRatio > 0.5) {
            return 10;
        } else if (creatureRatio > 0.4) {
            return 0;
        } else if (creatureRatio > 0.3) {
            return -10;
        } else {
            return -20;
        }
    }

    /**
     * Counts the number of removal spells in the deck.
     */
    private static int countRemovalSpells(List<Card> cards, Game game) {
        int count = 0;

        for (Card card : cards) {
            if (isRemovalSpell(card, game)) {
                count++;
            }
        }

        return count;
    }

    /**
     * Checks if a card is a removal spell.
     * Based on the logic from RateCard.isRemoval but adapted for our needs.
     */
    private static boolean isRemovalSpell(Card card, Game game) {
        if (card.isEnchantment(game) || card.isInstantOrSorcery(game)) {
            for (Ability ability : card.getAbilities(game)) {
                for (Effect effect : ability.getEffects()) {
                    if (isRemovalEffect(ability, effect)) {
                        return true;
                    }
                }
                for (Mode mode : ability.getModes().values()) {
                    for (Effect effect : mode.getEffects()) {
                        if (isRemovalEffect(ability, effect)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean isRemovalEffect(Ability ability, Effect effect) {
        if (effect.getOutcome() == Outcome.Removal) {
            return true;
        }
        if (effect instanceof FightTargetsEffect
                || effect instanceof DamageWithPowerFromOneToAnotherTargetEffect
                || effect instanceof DamageWithPowerFromSourceToAnotherTargetEffect) {
            return true;
        }
        if (effect.getOutcome() == Outcome.Damage || effect instanceof DamageTargetEffect) {
            for (Target target : ability.getTargets()) {
                if (!(target instanceof TargetPlayerOrPlaneswalker)) {
                    return true;
                }
            }
        }
        if (effect.getOutcome() == Outcome.DestroyPermanent ||
                effect instanceof DestroyTargetEffect ||
                effect instanceof ExileTargetEffect ||
                effect instanceof ExileUntilSourceLeavesEffect) {
            for (Target target : ability.getTargets()) {
                if (target instanceof TargetPermanent) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns score modifier based on removal count.
     * High removal = control bonus.
     */
    private static int getRemovalModifier(int removalCount, int totalCards) {
        // Normalize based on deck size (assuming ~60 card deck = ~40 non-land)
        double normalizedRemoval = removalCount * (40.0 / Math.max(totalCards, 1));

        if (normalizedRemoval > 10) {
            return -15;
        } else if (normalizedRemoval > 7) {
            return -10;
        } else if (normalizedRemoval > 5) {
            return -5;
        } else if (normalizedRemoval < 3) {
            return 10;
        } else {
            return 0;
        }
    }

    /**
     * Counts the number of creatures with haste.
     */
    private static int countHasteCreatures(List<Card> cards, Game game) {
        int count = 0;

        for (Card card : cards) {
            if (card.isCreature(game)) {
                if (card.getAbilities(game).containsKey(HasteAbility.getInstance().getId())) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Returns score modifier based on haste creature count.
     * Haste = aggro bonus.
     */
    private static int getHasteModifier(int hasteCount) {
        return Math.min(hasteCount * 3, 15); // Cap at +15
    }

    /**
     * Counts cards with CMC at or above the threshold.
     */
    private static int countHighCMCCards(List<Card> cards, Game game, int cmcThreshold) {
        int count = 0;

        for (Card card : cards) {
            if (!card.isLand(game) && card.getManaValue() >= cmcThreshold) {
                count++;
            }
        }

        return count;
    }

    /**
     * Returns score modifier based on high-CMC bomb count.
     * Bombs = control bonus.
     */
    private static int getBombModifier(int bombCount) {
        return Math.max(-bombCount * 5, -20); // Cap at -20
    }

    /**
     * Counts cards that draw cards.
     */
    private static int countCardDrawSpells(List<Card> cards, Game game) {
        int count = 0;

        for (Card card : cards) {
            if (!card.isLand(game)) {
                for (Ability ability : card.getAbilities(game)) {
                    for (Effect effect : ability.getEffects()) {
                        if (effect instanceof DrawCardSourceControllerEffect
                                || effect instanceof DrawCardTargetEffect) {
                            count++;
                            break;
                        }
                    }
                }
            }
        }

        return count;
    }

    /**
     * Returns score modifier based on card draw count.
     * Card draw = control bonus.
     */
    private static int getCardDrawModifier(int cardDrawCount) {
        if (cardDrawCount > 5) {
            return -10;
        } else if (cardDrawCount > 3) {
            return -5;
        } else if (cardDrawCount < 2) {
            return 5;
        }
        return 0;
    }
}
