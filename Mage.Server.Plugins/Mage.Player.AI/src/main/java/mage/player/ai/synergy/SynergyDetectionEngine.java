package mage.player.ai.synergy;

import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.synergy.patterns.*;
import mage.players.Player;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AI: Main engine for synergy detection and tracking.
 * Analyzes player decks and game state to identify powerful card synergies
 * that aren't infinite combos but should be protected.
 *
 * @author duxbuse
 */
public class SynergyDetectionEngine {

    private static final Logger logger = Logger.getLogger(SynergyDetectionEngine.class);

    // All registered synergy patterns
    private final List<SynergyPattern> allPatterns;

    // Cache of detected synergies per player
    private final Map<UUID, List<SynergyDetectionResult>> playerSynergies;

    // Set of all synergy piece names for quick lookup
    private final Map<UUID, Set<String>> playerSynergyPieces;

    // Patterns that apply to each player's deck
    private final Map<UUID, List<SynergyPattern>> playerPatterns;

    // Track when deck was last analyzed
    private final Map<UUID, Integer> lastAnalyzedTurn;

    public SynergyDetectionEngine() {
        this.allPatterns = new ArrayList<>();
        this.playerSynergies = new ConcurrentHashMap<>();
        this.playerSynergyPieces = new ConcurrentHashMap<>();
        this.playerPatterns = new ConcurrentHashMap<>();
        this.lastAnalyzedTurn = new ConcurrentHashMap<>();

        // Register built-in patterns
        registerBuiltInPatterns();
    }

    private void registerBuiltInPatterns() {
        // Equipment enabler synergy (Puresteel Paladin + Colossus Hammer, etc.)
        allPatterns.add(new EquipmentEnablerPattern());

        // Tribal lord synergy (lords that buff creatures of specific types)
        allPatterns.add(new TribalLordPattern());

        // Sacrifice outlet synergy (Aristocrats - sac outlets + death triggers)
        allPatterns.add(new SacrificeOutletPattern());

        // Enchantress synergy (draw cards on enchantment casts/ETB)
        allPatterns.add(new EnchantressPattern());

        // Spellslinger synergy (triggers on instant/sorcery casts)
        allPatterns.add(new SpellslingerPattern());

        // Counter synergy (+1/+1 counter doublers and payoffs)
        allPatterns.add(new CounterSynergyPattern());

        // Artifact synergy (artifact ETB triggers, cost reducers, affinity)
        allPatterns.add(new ArtifactSynergyPattern());

        // Graveyard synergy (self-mill, dredge, reanimation)
        allPatterns.add(new GraveyardSynergyPattern());

        // Mill strategy (milling opponents as a win condition)
        allPatterns.add(new MillStrategyPattern());
    }

    /**
     * Register a custom synergy pattern.
     *
     * @param pattern the pattern to register
     */
    public void registerPattern(SynergyPattern pattern) {
        allPatterns.add(pattern);
    }

    /**
     * Analyze a player's deck for potential synergies.
     * Should be called at game start and can be refreshed periodically.
     *
     * @param game     the current game
     * @param playerId the player to analyze
     */
    public void analyzeDeck(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return;
        }

        logger.debug("Analyzing deck for player " + player.getName() + " for synergy potential");

        // Collect all card names in the player's deck
        Set<String> deckCards = new HashSet<>();
        for (Card card : player.getLibrary().getCards(game)) {
            deckCards.add(card.getName());
        }
        for (Card card : player.getHand().getCards(game)) {
            deckCards.add(card.getName());
        }
        for (Card card : player.getGraveyard().getCards(game)) {
            deckCards.add(card.getName());
        }
        // Include battlefield permanents
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            deckCards.add(permanent.getName());
        }

        // Find patterns that match cards in this deck
        List<SynergyPattern> applicablePatterns = new ArrayList<>();
        Set<String> synergyPieces = new HashSet<>();

        for (SynergyPattern pattern : allPatterns) {
            Set<String> enablers = pattern.getEnablers();
            Set<String> payoffs = pattern.getPayoffs();

            // Check if we have any enablers
            boolean hasEnabler = false;
            for (String enabler : enablers) {
                if (deckCards.contains(enabler)) {
                    hasEnabler = true;
                    break;
                }
            }

            // Check if we have any payoffs (for patterns with static payoffs)
            boolean hasPayoff = false;
            for (String payoff : payoffs) {
                if (deckCards.contains(payoff)) {
                    hasPayoff = true;
                    break;
                }
            }

            // Handle patterns with dynamic payoffs based on type
            if (hasEnabler && !hasPayoff) {
                hasPayoff = checkDynamicPayoffs(game, player, playerId, pattern);
            }

            // Add pattern if we have enablers (dynamic patterns may not need explicit payoffs)
            // Let the pattern's detectSynergy method determine actual applicability
            if (hasEnabler && (hasPayoff || hasDynamicPayoffs(pattern))) {
                applicablePatterns.add(pattern);

                // Track synergy pieces
                for (String enabler : enablers) {
                    if (deckCards.contains(enabler)) {
                        synergyPieces.add(enabler);
                    }
                }
                for (String payoff : payoffs) {
                    if (deckCards.contains(payoff)) {
                        synergyPieces.add(payoff);
                    }
                }

                logger.debug("Found applicable synergy: " + pattern.getName());
            }
        }

        // Store results
        playerPatterns.put(playerId, applicablePatterns);
        playerSynergyPieces.put(playerId, synergyPieces);
        lastAnalyzedTurn.put(playerId, game.getTurnNum());

        logger.info(String.format("Synergy analysis complete for %s: %d synergies, %d synergy pieces",
                player.getName(), applicablePatterns.size(), synergyPieces.size()));

        // Do initial detection
        updateSynergyState(game, playerId);
    }

    /**
     * Update the synergy detection state for a player.
     * Call this when game state changes significantly.
     *
     * @param game     the current game
     * @param playerId the player to update
     */
    public void updateSynergyState(Game game, UUID playerId) {
        List<SynergyPattern> patterns = playerPatterns.get(playerId);
        if (patterns == null || patterns.isEmpty()) {
            return;
        }

        List<SynergyDetectionResult> results = new ArrayList<>();
        for (SynergyPattern pattern : patterns) {
            SynergyDetectionResult result = pattern.detectSynergy(game, playerId);
            if (result.isDetected()) {
                results.add(result);
            }
        }

        // Sort by score bonus (highest first)
        results.sort((a, b) -> Integer.compare(b.getScoreBonus(), a.getScoreBonus()));

        playerSynergies.put(playerId, results);
    }

    /**
     * Get all detected synergies for a player.
     *
     * @param playerId the player to check
     * @return list of detection results, sorted by priority
     */
    public List<SynergyDetectionResult> getDetectedSynergies(UUID playerId) {
        List<SynergyDetectionResult> results = playerSynergies.get(playerId);
        return results != null ? Collections.unmodifiableList(results) : Collections.emptyList();
    }

    /**
     * Check if a card name is part of any detected synergy for this player.
     *
     * @param playerId the player to check
     * @param cardName the card name to look up
     * @return true if the card is a synergy piece
     */
    public boolean isSynergyCard(UUID playerId, String cardName) {
        Set<String> pieces = playerSynergyPieces.get(playerId);
        return pieces != null && pieces.contains(cardName);
    }

    /**
     * Check if a card is a synergy enabler (should be heavily protected).
     *
     * @param playerId the player to check
     * @param cardName the card name to check
     * @return true if the card is an enabler
     */
    public boolean isSynergyEnabler(UUID playerId, String cardName) {
        List<SynergyPattern> patterns = playerPatterns.get(playerId);
        if (patterns == null) {
            return false;
        }
        for (SynergyPattern pattern : patterns) {
            if (pattern.isEnabler(cardName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get all synergy piece card names for a player.
     *
     * @param playerId the player to check
     * @return set of synergy piece names
     */
    public Set<String> getSynergyPieces(UUID playerId) {
        Set<String> pieces = playerSynergyPieces.get(playerId);
        return pieces != null ? Collections.unmodifiableSet(pieces) : Collections.emptySet();
    }

    /**
     * Calculate total synergy score bonus for game evaluation.
     *
     * @param game     the current game
     * @param playerId the player to evaluate
     * @return total score bonus from all detected synergies
     */
    public int calculateSynergyScoreBonus(Game game, UUID playerId) {
        List<SynergyPattern> patterns = playerPatterns.get(playerId);
        if (patterns == null || patterns.isEmpty()) {
            return 0;
        }

        // Check if we should re-analyze
        Integer lastTurn = lastAnalyzedTurn.get(playerId);
        if (lastTurn == null || game.getTurnNum() - lastTurn > 2) {
            updateSynergyState(game, playerId);
        }

        List<SynergyDetectionResult> results = playerSynergies.get(playerId);
        if (results == null || results.isEmpty()) {
            return 0;
        }

        // Sum all synergy bonuses (synergies can stack, unlike combo which takes best)
        int totalBonus = 0;
        for (SynergyDetectionResult result : results) {
            totalBonus += result.getScoreBonus();
        }

        return totalBonus;
    }

    /**
     * Get the synergy bonus for a specific permanent.
     * Used to determine how much to protect this permanent in combat.
     *
     * @param game      the current game
     * @param playerId  the player
     * @param permanent the permanent to evaluate
     * @return bonus value for this permanent due to synergies
     */
    public int getPermanentSynergyBonus(Game game, UUID playerId, Permanent permanent) {
        List<SynergyPattern> patterns = playerPatterns.get(playerId);
        if (patterns == null || patterns.isEmpty()) {
            return 0;
        }

        String cardName = permanent.getName();
        int bonus = 0;

        for (SynergyPattern pattern : patterns) {
            SynergyDetectionResult result = pattern.detectSynergy(game, playerId);
            if (!result.isDetected()) {
                continue;
            }

            // Enablers get the full synergy bonus
            if (pattern.isEnabler(cardName) && result.hasPayoffOnBattlefield()) {
                // Enabler with payoff in play - very valuable
                bonus += 500;
            } else if (pattern.isEnabler(cardName)) {
                // Enabler without payoff yet - still valuable
                bonus += 250;
            }

            // Payoffs get a smaller bonus
            if (pattern.isPayoff(cardName) && result.hasEnablerOnBattlefield()) {
                bonus += 200;
            }
        }

        return bonus;
    }

    /**
     * Check if the player has any active synergies right now.
     *
     * @param game     the current game
     * @param playerId the player to check
     * @return true if at least one synergy is active
     */
    public boolean hasActiveSynergy(Game game, UUID playerId) {
        updateSynergyState(game, playerId);
        List<SynergyDetectionResult> results = playerSynergies.get(playerId);
        if (results == null) {
            return false;
        }
        for (SynergyDetectionResult result : results) {
            if (result.isActive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get patterns for a player (for testing/debugging).
     */
    public List<SynergyPattern> getPatterns(UUID playerId) {
        List<SynergyPattern> patterns = playerPatterns.get(playerId);
        return patterns != null ? Collections.unmodifiableList(patterns) : Collections.emptyList();
    }

    /**
     * Check if a pattern type has dynamic payoffs (not hardcoded card names).
     */
    private boolean hasDynamicPayoffs(SynergyPattern pattern) {
        SynergyType type = pattern.getType();
        return type == SynergyType.TRIBAL_LORD ||
               type == SynergyType.ENCHANTRESS ||
               type == SynergyType.SPELLSLINGER ||
               type == SynergyType.ARTIFACT_SYNERGY ||
               type == SynergyType.GRAVEYARD_ENABLER ||
               type == SynergyType.MILL_STRATEGY;
    }

    /**
     * Check for dynamic payoffs based on pattern type.
     * Equipment checks for any equipment, tribal checks for creatures of tribe, etc.
     */
    private boolean checkDynamicPayoffs(Game game, Player player, UUID playerId, SynergyPattern pattern) {
        SynergyType type = pattern.getType();

        switch (type) {
            case EQUIPMENT_ENABLER:
                // Check for any equipment
                return hasEquipmentInDeck(game, player, playerId);

            case TRIBAL_LORD:
                // Tribal lords check their specific tribe via detectSynergy
                // If we have a lord, we likely have some tribal creatures
                return true;

            case ENCHANTRESS:
                // Check for enchantments in deck
                return hasEnchantmentsInDeck(game, player, playerId, 5);

            case SPELLSLINGER:
                // Check for instants/sorceries in deck
                return hasSpellsInDeck(game, player, playerId, 10);

            case ARTIFACT_SYNERGY:
                // Check for artifacts in deck
                return hasArtifactsInDeck(game, player, playerId, 8);

            case GRAVEYARD_ENABLER:
                // Graveyard synergy is always potentially applicable
                return true;

            case MILL_STRATEGY:
                // Mill strategy doesn't need payoffs - the win condition is depleting opponent's library
                return true;

            default:
                return false;
        }
    }

    private boolean hasEquipmentInDeck(Game game, Player player, UUID playerId) {
        for (Card card : player.getLibrary().getCards(game)) {
            if (card.isArtifact(game) && card.hasSubtype(mage.constants.SubType.EQUIPMENT, game)) {
                return true;
            }
        }
        for (Card card : player.getHand().getCards(game)) {
            if (card.isArtifact(game) && card.hasSubtype(mage.constants.SubType.EQUIPMENT, game)) {
                return true;
            }
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            if (permanent.isArtifact(game) && permanent.hasSubtype(mage.constants.SubType.EQUIPMENT, game)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasEnchantmentsInDeck(Game game, Player player, UUID playerId, int minCount) {
        int count = 0;
        for (Card card : player.getLibrary().getCards(game)) {
            if (card.isEnchantment(game)) {
                count++;
                if (count >= minCount) return true;
            }
        }
        for (Card card : player.getHand().getCards(game)) {
            if (card.isEnchantment(game)) {
                count++;
                if (count >= minCount) return true;
            }
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            if (permanent.isEnchantment(game)) {
                count++;
                if (count >= minCount) return true;
            }
        }
        return false;
    }

    private boolean hasSpellsInDeck(Game game, Player player, UUID playerId, int minCount) {
        int count = 0;
        for (Card card : player.getLibrary().getCards(game)) {
            if (card.isInstant(game) || card.isSorcery(game)) {
                count++;
                if (count >= minCount) return true;
            }
        }
        for (Card card : player.getHand().getCards(game)) {
            if (card.isInstant(game) || card.isSorcery(game)) {
                count++;
                if (count >= minCount) return true;
            }
        }
        return false;
    }

    private boolean hasArtifactsInDeck(Game game, Player player, UUID playerId, int minCount) {
        int count = 0;
        for (Card card : player.getLibrary().getCards(game)) {
            if (card.isArtifact(game)) {
                count++;
                if (count >= minCount) return true;
            }
        }
        for (Card card : player.getHand().getCards(game)) {
            if (card.isArtifact(game)) {
                count++;
                if (count >= minCount) return true;
            }
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            if (permanent.isArtifact(game)) {
                count++;
                if (count >= minCount) return true;
            }
        }
        return false;
    }

    /**
     * Clear cached data for a player.
     */
    public void clearPlayer(UUID playerId) {
        playerSynergies.remove(playerId);
        playerSynergyPieces.remove(playerId);
        playerPatterns.remove(playerId);
        lastAnalyzedTurn.remove(playerId);
    }

    /**
     * Clear all cached data.
     */
    public void clear() {
        playerSynergies.clear();
        playerSynergyPieces.clear();
        playerPatterns.clear();
        lastAnalyzedTurn.clear();
    }
}
