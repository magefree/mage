package mage.player.ai.synergy;

import mage.game.Game;

import java.util.Set;
import java.util.UUID;

/**
 * AI: Interface for synergy detection patterns.
 * Synergies are powerful card interactions that aren't infinite combos
 * but provide significant strategic value worth protecting.
 *
 * Unlike combos which win the game, synergies amplify strategy effectiveness:
 * - Equipment enablers make expensive equipment playable
 * - Tribal lords make tribal decks stronger
 * - Sacrifice outlets enable death triggers
 *
 * @author duxbuse
 */
public interface SynergyPattern {

    /**
     * Get unique identifier for this synergy pattern.
     *
     * @return synergy identifier string
     */
    String getSynergyId();

    /**
     * Get human-readable name for this synergy.
     *
     * @return synergy name
     */
    String getName();

    /**
     * Get the type of synergy this pattern represents.
     *
     * @return synergy type
     */
    SynergyType getType();

    /**
     * Get the cards that enable this synergy.
     * These are the cards that should be protected.
     *
     * @return set of enabler card names
     */
    Set<String> getEnablers();

    /**
     * Get the cards that benefit from this synergy.
     * The presence of these cards makes enablers more valuable.
     *
     * @return set of payoff card names
     */
    Set<String> getPayoffs();

    /**
     * Detect the synergy state for a player.
     *
     * @param game     the current game
     * @param playerId the player to check
     * @return detection result with state and score bonus
     */
    SynergyDetectionResult detectSynergy(Game game, UUID playerId);

    /**
     * Check if this synergy is currently active and providing value.
     * A synergy is active when enablers and payoffs are working together.
     *
     * @param game     the current game
     * @param playerId the player to check
     * @return true if synergy is active
     */
    boolean isActive(Game game, UUID playerId);

    /**
     * Calculate score bonus for having this synergy available.
     * Higher bonus means AI should protect these pieces more.
     *
     * @param game     the current game
     * @param playerId the player to check
     * @return score bonus to add to game evaluation
     */
    int calculateSynergyBonus(Game game, UUID playerId);

    /**
     * Get all card names that are part of this synergy (enablers + payoffs).
     *
     * @return set of all synergy piece names
     */
    Set<String> getAllPieces();

    /**
     * Check if a specific card is an enabler for this synergy.
     *
     * @param cardName the card name to check
     * @return true if the card is an enabler
     */
    default boolean isEnabler(String cardName) {
        return getEnablers().contains(cardName);
    }

    /**
     * Check if a specific card is a payoff for this synergy.
     *
     * @param cardName the card name to check
     * @return true if the card is a payoff
     */
    default boolean isPayoff(String cardName) {
        return getPayoffs().contains(cardName);
    }
}
