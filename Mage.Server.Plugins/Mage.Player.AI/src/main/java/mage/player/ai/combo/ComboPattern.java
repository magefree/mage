package mage.player.ai.combo;

import mage.game.Game;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * AI: Interface for combo detection patterns.
 * Implementations define how to detect specific combo archetypes and provide
 * execution guidance for the AI.
 *
 * @author Claude
 */
public interface ComboPattern {

    /**
     * Get unique identifier for this combo pattern
     *
     * @return combo identifier string
     */
    String getComboId();

    /**
     * Get human-readable name for this combo
     *
     * @return combo name
     */
    String getComboName();

    /**
     * Detect if this combo is present in the player's deck/game state
     *
     * @param game the current game
     * @param playerId the player to check for
     * @return detection result with confidence and state info
     */
    ComboDetectionResult detectCombo(Game game, UUID playerId);

    /**
     * Check if the combo can be executed right now
     *
     * @param game the current game
     * @param playerId the player to check for
     * @return true if all pieces are in position and combo can be executed
     */
    boolean canExecuteNow(Game game, UUID playerId);

    /**
     * Get the recommended sequence of actions to execute the combo.
     * Returns card names in the order they should be played/activated.
     *
     * @param game the current game
     * @param playerId the player to check for
     * @return ordered list of card names to play
     */
    List<String> getComboSequence(Game game, UUID playerId);

    /**
     * Get all card names that are pieces of this combo
     *
     * @return set of card names
     */
    Set<String> getComboPieces();

    /**
     * Get the priority of this combo pattern.
     * Higher priority combos are checked and executed first.
     * Win-on-the-spot combos should have highest priority.
     *
     * @return priority value (higher = more important)
     */
    int getPriority();

    /**
     * Get the expected outcome when combo is executed.
     * Used for AI decision making.
     *
     * @return description of combo outcome (e.g., "infinite damage", "infinite mana", "win game")
     */
    String getExpectedOutcome();
}
