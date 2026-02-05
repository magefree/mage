package mage.player.ai.ma.optimizers.impl;

import mage.abilities.Ability;
import mage.game.Game;
import mage.player.ai.combo.ComboDetectionEngine;
import mage.player.ai.combo.ComboDetectionResult;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * AI: Tree optimizer that prioritizes combo actions.
 * When a combo is executable, filters out non-combo actions to help
 * AI focus on executing the winning line.
 *
 * @author Claude
 */
public class ComboAwareOptimizer extends BaseTreeOptimizer {

    private static final Logger logger = Logger.getLogger(ComboAwareOptimizer.class);

    // Thread-local storage for combo detection engine (since optimizers are static/shared)
    private static final ThreadLocal<ComboDetectionEngine> engineHolder = new ThreadLocal<>();
    private static final ThreadLocal<UUID> playerIdHolder = new ThreadLocal<>();

    /**
     * Set the combo detection engine for the current AI calculation.
     * Must be called before optimization runs.
     */
    public static void setEngine(ComboDetectionEngine engine, UUID playerId) {
        engineHolder.set(engine);
        playerIdHolder.set(playerId);
    }

    /**
     * Clear the engine after AI calculation completes.
     */
    public static void clearEngine() {
        engineHolder.remove();
        playerIdHolder.remove();
    }

    @Override
    void filter(Game game, List<Ability> actions, List<Ability> actionsToRemove) {
        ComboDetectionEngine engine = engineHolder.get();
        UUID playerId = playerIdHolder.get();

        if (engine == null || playerId == null) {
            // No combo engine configured, skip optimization
            return;
        }

        // Check if we have an executable combo
        ComboDetectionResult executableCombo = engine.getBestExecutableCombo(game, playerId);
        if (executableCombo == null || !executableCombo.isExecutable()) {
            // No executable combo - just boost combo piece actions
            boostComboPieceActions(game, actions, engine, playerId);
            return;
        }

        // We have an executable combo - prioritize combo actions
        logger.debug("Executable combo detected: " + executableCombo.getComboId() +
                ", filtering non-combo actions");

        List<String> comboSequence = engine.getComboSequence(game, playerId);
        Set<String> comboPieces = engine.getComboPieces(playerId);

        if (comboSequence.isEmpty()) {
            // No specific sequence, but we have combo - keep combo piece actions
            filterNonComboActions(actions, actionsToRemove, comboPieces);
        } else {
            // Have a specific sequence - prioritize first action in sequence
            String nextCardToPlay = comboSequence.get(0);
            prioritizeAction(actions, actionsToRemove, nextCardToPlay, comboPieces);
        }
    }

    /**
     * When no executable combo, move combo piece actions earlier in the list
     * to increase likelihood of AI considering them.
     */
    private void boostComboPieceActions(Game game, List<Ability> actions,
                                        ComboDetectionEngine engine, UUID playerId) {
        Set<String> comboPieces = engine.getComboPieces(playerId);
        if (comboPieces.isEmpty()) {
            return;
        }

        // Separate combo piece actions from others
        List<Ability> comboActions = new ArrayList<>();
        List<Ability> otherActions = new ArrayList<>();

        for (Ability action : actions) {
            String cardName = getSourceCardName(game, action);
            if (cardName != null && comboPieces.contains(cardName)) {
                comboActions.add(action);
            } else {
                otherActions.add(action);
            }
        }

        // Reorder: combo pieces first (but after Pass if present)
        if (!comboActions.isEmpty()) {
            actions.clear();
            // Find and preserve Pass action at end
            Ability passAction = null;
            for (Ability action : otherActions) {
                if (action.toString().startsWith("Pass")) {
                    passAction = action;
                    break;
                }
            }
            otherActions.remove(passAction);

            // Add combo actions first
            actions.addAll(comboActions);
            // Then other actions
            actions.addAll(otherActions);
            // Pass at end
            if (passAction != null) {
                actions.add(passAction);
            }
        }
    }

    /**
     * Filter out actions that don't involve combo pieces.
     * Keep Pass action as fallback.
     */
    private void filterNonComboActions(List<Ability> actions, List<Ability> actionsToRemove,
                                       Set<String> comboPieces) {
        for (Ability action : actions) {
            String rule = action.toString();

            // Always keep Pass action
            if (rule.startsWith("Pass")) {
                continue;
            }

            // Keep mana abilities (needed to cast combo)
            if (action.isManaAbility()) {
                continue;
            }

            // Check if action involves a combo piece
            String cardName = getSourceCardName(action);
            if (cardName != null && comboPieces.contains(cardName)) {
                continue;  // Keep combo piece actions
            }

            // Remove non-combo actions
            actionsToRemove.add(action);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Filtered " + actionsToRemove.size() + " non-combo actions");
        }
    }

    /**
     * Prioritize the specific next action in the combo sequence.
     */
    private void prioritizeAction(List<Ability> actions, List<Ability> actionsToRemove,
                                  String nextCardToPlay, Set<String> comboPieces) {
        Ability targetAction = null;

        for (Ability action : actions) {
            String rule = action.toString();

            // Always keep Pass
            if (rule.startsWith("Pass")) {
                continue;
            }

            // Always keep mana abilities
            if (action.isManaAbility()) {
                continue;
            }

            String cardName = getSourceCardName(action);

            // Is this the card we want to play next?
            if (cardName != null && cardName.equals(nextCardToPlay)) {
                targetAction = action;
                continue;  // Keep this
            }

            // Keep other combo piece actions as backup
            if (cardName != null && comboPieces.contains(cardName)) {
                continue;
            }

            // Remove non-combo actions
            actionsToRemove.add(action);
        }

        // Move target action to front of list (after removing non-combo actions)
        if (targetAction != null) {
            actions.remove(targetAction);
            actions.add(0, targetAction);
            logger.debug("Prioritized combo action: " + nextCardToPlay);
        }
    }

    /**
     * Get the source card name from an ability.
     */
    private String getSourceCardName(Ability action) {
        if (action.getSourceObject(null) != null) {
            return action.getSourceObject(null).getName();
        }
        return null;
    }

    /**
     * Get the source card name from an ability with game context.
     */
    private String getSourceCardName(Game game, Ability action) {
        if (action.getSourceObject(game) != null) {
            return action.getSourceObject(game).getName();
        }
        return getSourceCardName(action);
    }
}
