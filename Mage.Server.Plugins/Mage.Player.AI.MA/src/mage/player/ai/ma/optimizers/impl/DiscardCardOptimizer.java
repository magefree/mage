package mage.player.ai.ma.optimizers.impl;

import mage.abilities.Ability;
import mage.game.Game;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Set;

/**
 * AI: removes abilities that require only discard a card for activation.
 * Enhanced to be combo-aware: protects combo pieces from being discarded.
 *
 * @author magenoxx_at_gmail.com, Claude
 */
public class DiscardCardOptimizer extends BaseTreeOptimizer {

    private static final Logger logger = Logger.getLogger(DiscardCardOptimizer.class);

    // Thread-local storage for combo pieces (set by ComboAwareOptimizer context)
    private static final ThreadLocal<Set<String>> comboPiecesHolder = new ThreadLocal<>();

    /**
     * Set the combo pieces for the current optimization context.
     * Called from ComboAwareOptimizer to share context.
     */
    public static void setComboPieces(Set<String> comboPieces) {
        comboPiecesHolder.set(comboPieces);
    }

    /**
     * Clear the combo pieces after optimization.
     */
    public static void clearComboPieces() {
        comboPiecesHolder.remove();
    }

    @Override
    public void filter(Game game, List<Ability> actions, List<Ability> actionsToRemove) {
        Set<String> comboPieces = comboPiecesHolder.get();

        for (Ability ability : actions) {
            String abilityText = ability.toString();

            // Check for discard abilities
            if (abilityText.startsWith("Discard card") || abilityText.contains("Discard a card")) {
                // If no combo detection, just remove all discards (original behavior)
                if (comboPieces == null || comboPieces.isEmpty()) {
                    actionsToRemove.add(ability);
                    continue;
                }

                // With combo detection: check if the discard target is a combo piece
                // For generic "Discard card" abilities, we can't know the target yet,
                // so we still remove them to be safe
                if (abilityText.startsWith("Discard card")) {
                    actionsToRemove.add(ability);
                    continue;
                }

                // For abilities that specify a card, check if it's a combo piece
                // This handles cases like "Discard [Card Name]: Effect"
                String cardName = extractCardNameFromDiscard(abilityText);
                if (cardName != null && comboPieces.contains(cardName)) {
                    // Don't discard combo pieces!
                    logger.debug("Protecting combo piece from discard: " + cardName);
                    actionsToRemove.add(ability);
                }
                // Allow discarding non-combo cards
            }
        }
    }

    /**
     * Try to extract the card name being discarded from the ability text.
     * Returns null if unable to determine.
     */
    private String extractCardNameFromDiscard(String abilityText) {
        // Pattern: "Discard [Card Name]:" or "Discard [Card Name]"
        if (abilityText.startsWith("Discard ")) {
            String rest = abilityText.substring("Discard ".length());
            // Find the end of the card name (usually at ":" or end of string)
            int colonIndex = rest.indexOf(':');
            if (colonIndex > 0) {
                return rest.substring(0, colonIndex).trim();
            }
            // If no colon, the whole rest might be the card name
            // but this is less reliable
        }
        return null;
    }
}
