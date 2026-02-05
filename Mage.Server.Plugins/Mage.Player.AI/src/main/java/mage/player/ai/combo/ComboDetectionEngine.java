package mage.player.ai.combo;

import mage.cards.Card;
import mage.game.Game;
import mage.players.Player;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AI: Main engine for combo detection and tracking.
 * Analyzes player decks at game start and continuously tracks
 * combo progress throughout the game.
 *
 * @author Claude
 */
public class ComboDetectionEngine {

    private static final Logger logger = Logger.getLogger(ComboDetectionEngine.class);

    // Cache of detected combos per player
    private final Map<UUID, List<ComboDetectionResult>> playerCombos;

    // Set of all combo piece names for quick lookup (lowercase for case-insensitive matching)
    private final Map<UUID, Set<String>> playerComboPieces;

    // Patterns that apply to each player's deck
    private final Map<UUID, List<ComboPattern>> playerPatterns;

    // Track when deck was last analyzed
    private final Map<UUID, Integer> lastAnalyzedTurn;

    private final ComboRegistry registry;

    public ComboDetectionEngine() {
        this.playerCombos = new ConcurrentHashMap<>();
        this.playerComboPieces = new ConcurrentHashMap<>();
        this.playerPatterns = new ConcurrentHashMap<>();
        this.lastAnalyzedTurn = new ConcurrentHashMap<>();
        this.registry = ComboRegistry.getInstance();
    }

    /**
     * Analyze a player's deck for potential combos.
     * Should be called at game start and can be refreshed periodically.
     *
     * @param game the current game
     * @param playerId the player to analyze
     */
    public void analyzeDeck(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return;
        }

        logger.debug("Analyzing deck for player " + player.getName() + " for combo potential");

        // Collect all card names in the player's deck
        Set<String> deckCards = new HashSet<>();
        for (Card card : player.getLibrary().getCards(game)) {
            deckCards.add(card.getName());
        }
        // Also include cards already in hand
        for (Card card : player.getHand().getCards(game)) {
            deckCards.add(card.getName());
        }
        // And graveyard (for reanimator etc)
        for (Card card : player.getGraveyard().getCards(game)) {
            deckCards.add(card.getName());
        }

        // Find patterns that match any cards in this deck
        Set<ComboPattern> relevantPatterns = registry.getPatternsForCards(deckCards);

        // Filter to patterns where we have a reasonable chance of assembling the combo
        List<ComboPattern> applicablePatterns = new ArrayList<>();
        Set<String> comboPieces = new HashSet<>();

        for (ComboPattern pattern : relevantPatterns) {
            Set<String> pieces = pattern.getComboPieces();
            int matchCount = 0;
            for (String piece : pieces) {
                if (deckCards.contains(piece)) {
                    matchCount++;
                }
            }

            // For two-card combos, need both pieces
            // For complex patterns, need at least some pieces
            if (pieces.size() <= 2 && matchCount == pieces.size()) {
                applicablePatterns.add(pattern);
                comboPieces.addAll(pieces);
                logger.debug("Found applicable combo: " + pattern.getComboName());
            } else if (pieces.size() > 2 && matchCount >= 2) {
                // Complex pattern - check with actual detection
                ComboDetectionResult result = pattern.detectCombo(game, playerId);
                if (result.isDetected()) {
                    applicablePatterns.add(pattern);
                    comboPieces.addAll(result.getFoundPieces());
                    logger.debug("Found applicable complex combo: " + pattern.getComboName());
                }
            }
        }

        // Store results
        playerPatterns.put(playerId, applicablePatterns);
        playerComboPieces.put(playerId, comboPieces);
        lastAnalyzedTurn.put(playerId, game.getTurnNum());

        logger.info(String.format("Deck analysis complete for %s: %d potential combos, %d combo pieces",
                player.getName(), applicablePatterns.size(), comboPieces.size()));

        // Do initial detection
        updateComboState(game, playerId);
    }

    /**
     * Update the combo detection state for a player.
     * Call this when game state changes significantly (draw, mill, etc.)
     *
     * @param game the current game
     * @param playerId the player to update
     */
    public void updateComboState(Game game, UUID playerId) {
        List<ComboPattern> patterns = playerPatterns.get(playerId);
        if (patterns == null || patterns.isEmpty()) {
            return;
        }

        List<ComboDetectionResult> results = new ArrayList<>();
        for (ComboPattern pattern : patterns) {
            ComboDetectionResult result = pattern.detectCombo(game, playerId);
            if (result.isDetected()) {
                results.add(result);
            }
        }

        // Sort by score bonus (highest first)
        results.sort((a, b) -> Integer.compare(b.getScoreBonus(), a.getScoreBonus()));

        playerCombos.put(playerId, results);
    }

    /**
     * Get all detected combos for a player.
     *
     * @param playerId the player to check
     * @return list of detection results, sorted by priority
     */
    public List<ComboDetectionResult> getDetectedCombos(UUID playerId) {
        List<ComboDetectionResult> results = playerCombos.get(playerId);
        return results != null ? Collections.unmodifiableList(results) : Collections.emptyList();
    }

    /**
     * Get the best (highest priority) executable combo.
     *
     * @param game the current game
     * @param playerId the player to check
     * @return the best executable combo, or null if none
     */
    public ComboDetectionResult getBestExecutableCombo(Game game, UUID playerId) {
        updateComboState(game, playerId);

        List<ComboDetectionResult> results = playerCombos.get(playerId);
        if (results == null) {
            return null;
        }

        for (ComboDetectionResult result : results) {
            if (result.isExecutable()) {
                return result;
            }
        }
        return null;
    }

    /**
     * Check if a card name is part of any detected combo for this player.
     *
     * @param playerId the player to check
     * @param cardName the card name to look up
     * @return true if the card is a combo piece
     */
    public boolean isComboCard(UUID playerId, String cardName) {
        Set<String> pieces = playerComboPieces.get(playerId);
        return pieces != null && pieces.contains(cardName);
    }

    /**
     * Get all combo piece card names for a player.
     *
     * @param playerId the player to check
     * @return set of combo piece names
     */
    public Set<String> getComboPieces(UUID playerId) {
        Set<String> pieces = playerComboPieces.get(playerId);
        return pieces != null ? Collections.unmodifiableSet(pieces) : Collections.emptySet();
    }

    /**
     * Calculate total combo score bonus for game evaluation.
     *
     * @param game the current game
     * @param playerId the player to evaluate
     * @return total score bonus from all detected combos
     */
    public int calculateComboScoreBonus(Game game, UUID playerId) {
        // Ensure we have fresh detection
        List<ComboPattern> patterns = playerPatterns.get(playerId);
        if (patterns == null || patterns.isEmpty()) {
            return 0;
        }

        // Check if we should re-analyze (e.g., if many turns have passed)
        Integer lastTurn = lastAnalyzedTurn.get(playerId);
        if (lastTurn == null || game.getTurnNum() - lastTurn > 3) {
            // Refresh analysis periodically
            updateComboState(game, playerId);
        }

        List<ComboDetectionResult> results = playerCombos.get(playerId);
        if (results == null || results.isEmpty()) {
            return 0;
        }

        // Use the best combo's bonus (don't stack)
        // This prevents AI from overvaluing having multiple partial combos
        int bestBonus = 0;
        for (ComboDetectionResult result : results) {
            int bonus = result.getScoreBonus();
            if (bonus > bestBonus) {
                bestBonus = bonus;
            }
        }

        return bestBonus;
    }

    /**
     * Get the combo sequence for the best executable combo.
     *
     * @param game the current game
     * @param playerId the player
     * @return list of card names to play in order, or empty list
     */
    public List<String> getComboSequence(Game game, UUID playerId) {
        List<ComboPattern> patterns = playerPatterns.get(playerId);
        if (patterns == null) {
            return Collections.emptyList();
        }

        // Find the first executable combo and get its sequence
        for (ComboPattern pattern : patterns) {
            if (pattern.canExecuteNow(game, playerId)) {
                return pattern.getComboSequence(game, playerId);
            }
        }

        return Collections.emptyList();
    }

    /**
     * Check if the player has any executable combo right now.
     *
     * @param game the current game
     * @param playerId the player to check
     * @return true if at least one combo is ready to execute
     */
    public boolean hasExecutableCombo(Game game, UUID playerId) {
        return getBestExecutableCombo(game, playerId) != null;
    }

    /**
     * Get patterns for a player (for testing/debugging).
     */
    public List<ComboPattern> getPatterns(UUID playerId) {
        List<ComboPattern> patterns = playerPatterns.get(playerId);
        return patterns != null ? Collections.unmodifiableList(patterns) : Collections.emptyList();
    }

    /**
     * Clear cached data for a player (e.g., when game ends).
     */
    public void clearPlayer(UUID playerId) {
        playerCombos.remove(playerId);
        playerComboPieces.remove(playerId);
        playerPatterns.remove(playerId);
        lastAnalyzedTurn.remove(playerId);
    }

    /**
     * Clear all cached data.
     */
    public void clear() {
        playerCombos.clear();
        playerComboPieces.clear();
        playerPatterns.clear();
        lastAnalyzedTurn.clear();
    }
}
