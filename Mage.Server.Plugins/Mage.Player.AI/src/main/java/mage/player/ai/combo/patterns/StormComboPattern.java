package mage.player.ai.combo.patterns;

import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.combo.ComboDetectionResult;
import mage.player.ai.combo.ComboDetectionResult.ComboState;
import mage.player.ai.combo.ComboPattern;
import mage.players.Player;
import mage.watchers.Watcher;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.*;

/**
 * AI: Storm combo pattern detection.
 * Detects storm decks and helps AI execute storm sequences properly:
 * Rituals -> Cantrips -> Storm Payoff
 *
 * @author Claude
 */
public class StormComboPattern implements ComboPattern {

    private static final String COMBO_ID = "storm-combo";

    // Storm payoff cards - the cards that win with storm
    private static final Set<String> STORM_PAYOFFS = new HashSet<>(Arrays.asList(
            "Tendrils of Agony",
            "Grapeshot",
            "Brain Freeze",
            "Mind's Desire",
            "Empty the Warrens",
            "Ground Rift",
            "Temporal Fissure",
            "Hunting Pack",
            "Wing Shards",
            "Dragonstorm",
            "Ignite Memories",
            "Flusterstorm",
            "Grape Shot"  // alternate spelling
    ));

    // Mana acceleration - rituals and fast mana
    private static final Set<String> RITUALS = new HashSet<>(Arrays.asList(
            "Dark Ritual",
            "Cabal Ritual",
            "Seething Song",
            "Pyretic Ritual",
            "Desperate Ritual",
            "Rite of Flame",
            "Rain of Filth",
            "Culling the Weak",
            "Infernal Plunge",
            "Songs of the Damned",
            "Burnt Offering",
            "Sacrifice",
            "Channel",
            "High Tide"
    ));

    // Fast mana artifacts
    private static final Set<String> FAST_MANA = new HashSet<>(Arrays.asList(
            "Lion's Eye Diamond",
            "Lotus Petal",
            "Chrome Mox",
            "Mox Opal",
            "Mox Diamond",
            "Simian Spirit Guide",
            "Elvish Spirit Guide",
            "Jeweled Lotus",
            "Mana Crypt",
            "Sol Ring",
            "Mana Vault",
            "Grim Monolith"
    ));

    // Cantrips - card draw to find pieces and build storm
    private static final Set<String> CANTRIPS = new HashSet<>(Arrays.asList(
            "Brainstorm",
            "Ponder",
            "Preordain",
            "Gitaxian Probe",
            "Manamorphose",
            "Serum Visions",
            "Sleight of Hand",
            "Opt",
            "Consider",
            "Thought Scour",
            "Mental Note",
            "Street Wraith",
            "Faithless Looting",
            "Careful Study",
            "Ideas Unbound",
            "Night's Whisper",
            "Sign in Blood"
    ));

    // Engine cards - enable storm turns
    private static final Set<String> ENGINE_CARDS = new HashSet<>(Arrays.asList(
            "Past in Flames",
            "Yawgmoth's Will",
            "Underworld Breach",
            "Echo of Eons",
            "Timetwister",
            "Wheel of Fortune",
            "Windfall",
            "Ad Nauseam",
            "Necropotence",
            "Peer into the Abyss"
    ));

    // All combo pieces combined
    private final Set<String> allComboPieces;

    public StormComboPattern() {
        allComboPieces = new HashSet<>();
        allComboPieces.addAll(STORM_PAYOFFS);
        allComboPieces.addAll(RITUALS);
        allComboPieces.addAll(FAST_MANA);
        allComboPieces.addAll(CANTRIPS);
        allComboPieces.addAll(ENGINE_CARDS);
    }

    @Override
    public String getComboId() {
        return COMBO_ID;
    }

    @Override
    public String getComboName() {
        return "Storm Combo";
    }

    @Override
    public ComboDetectionResult detectCombo(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return ComboDetectionResult.notDetected(COMBO_ID);
        }

        Set<String> foundPieces = new HashSet<>();
        Set<String> piecesInHand = new HashSet<>();
        Set<String> piecesOnBattlefield = new HashSet<>();
        Set<String> piecesInGraveyard = new HashSet<>();

        int payoffsFound = 0;
        int ritualsFound = 0;
        int cantripsFound = 0;
        int enginesFound = 0;

        boolean hasPayoffInHand = false;
        int ritualsInHand = 0;

        // Scan all zones
        // Hand
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (STORM_PAYOFFS.contains(name)) {
                payoffsFound++;
                hasPayoffInHand = true;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (RITUALS.contains(name) || FAST_MANA.contains(name)) {
                ritualsFound++;
                ritualsInHand++;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (CANTRIPS.contains(name)) {
                cantripsFound++;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (ENGINE_CARDS.contains(name)) {
                enginesFound++;
                piecesInHand.add(name);
                foundPieces.add(name);
            }
        }

        // Battlefield (for mana rocks and such)
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            String name = permanent.getName();
            if (FAST_MANA.contains(name)) {
                ritualsFound++;
                piecesOnBattlefield.add(name);
                foundPieces.add(name);
            }
        }

        // Library
        for (Card card : player.getLibrary().getCards(game)) {
            String name = card.getName();
            if (STORM_PAYOFFS.contains(name)) {
                payoffsFound++;
                foundPieces.add(name);
            } else if (RITUALS.contains(name) || FAST_MANA.contains(name)) {
                ritualsFound++;
                foundPieces.add(name);
            } else if (CANTRIPS.contains(name)) {
                cantripsFound++;
                foundPieces.add(name);
            } else if (ENGINE_CARDS.contains(name)) {
                enginesFound++;
                foundPieces.add(name);
            }
        }

        // Graveyard (for Past in Flames)
        for (Card card : player.getGraveyard().getCards(game)) {
            String name = card.getName();
            if (allComboPieces.contains(name)) {
                piecesInGraveyard.add(name);
                foundPieces.add(name);
            }
        }

        // Determine if this is a storm deck
        // Need at least: 1 payoff, 3+ rituals, some cantrips
        boolean isStormDeck = payoffsFound >= 1 && ritualsFound >= 3 && (cantripsFound >= 2 || enginesFound >= 1);

        if (!isStormDeck) {
            return ComboDetectionResult.notDetected(COMBO_ID);
        }

        // Calculate confidence and state
        double confidence = Math.min(1.0,
                (payoffsFound * 0.3) + (ritualsFound * 0.1) + (cantripsFound * 0.05) + (enginesFound * 0.15));

        // Check current storm count
        int currentStormCount = getStormCount(game, playerId);

        ComboState state;
        String notes;

        // Can we execute lethal storm now?
        Player opponent = getOpponent(game, playerId);
        int opponentLife = opponent != null ? opponent.getLife() : 20;

        if (hasPayoffInHand && currentStormCount >= 9 && ritualsInHand >= 1) {
            // We can likely go off - 10 storm Tendrils is 20 damage
            state = ComboState.EXECUTABLE;
            notes = "Storm count: " + currentStormCount + ", can cast lethal payoff";
        } else if (hasPayoffInHand && ritualsInHand >= 3) {
            // Have pieces in hand to attempt combo
            state = ComboState.READY_IN_HAND;
            notes = "Payoff and rituals in hand, storm count: " + currentStormCount;
        } else if (payoffsFound > 0 && ritualsFound >= 3) {
            // Have deck pieces, need to draw
            state = ComboState.READY_IN_DECK;
            notes = "Storm deck assembled, need to draw pieces";
        } else {
            state = ComboState.PARTIAL;
            notes = "Partial storm setup";
        }

        return ComboDetectionResult.builder(COMBO_ID)
                .state(state)
                .confidence(confidence)
                .foundPieces(foundPieces)
                .missingPieces(Collections.emptySet())
                .piecesInHand(piecesInHand)
                .piecesOnBattlefield(piecesOnBattlefield)
                .piecesInGraveyard(piecesInGraveyard)
                .notes(notes)
                .build();
    }

    @Override
    public boolean canExecuteNow(Game game, UUID playerId) {
        ComboDetectionResult result = detectCombo(game, playerId);
        return result.getState() == ComboState.EXECUTABLE;
    }

    @Override
    public List<String> getComboSequence(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return Collections.emptyList();
        }

        List<String> sequence = new ArrayList<>();
        List<String> ritualsInHand = new ArrayList<>();
        List<String> cantripsInHand = new ArrayList<>();
        String payoffInHand = null;
        String engineInHand = null;

        // Categorize cards in hand
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (STORM_PAYOFFS.contains(name) && payoffInHand == null) {
                payoffInHand = name;
            } else if (RITUALS.contains(name) || FAST_MANA.contains(name)) {
                ritualsInHand.add(name);
            } else if (CANTRIPS.contains(name)) {
                cantripsInHand.add(name);
            } else if (ENGINE_CARDS.contains(name) && engineInHand == null) {
                engineInHand = name;
            }
        }

        // Build sequence: Fast mana -> Rituals -> Cantrips -> (Engine) -> Payoff

        // 1. Fast mana first (Lotus Petal, LED, etc.)
        for (String ritual : ritualsInHand) {
            if (FAST_MANA.contains(ritual)) {
                sequence.add(ritual);
            }
        }

        // 2. Rituals
        for (String ritual : ritualsInHand) {
            if (RITUALS.contains(ritual)) {
                sequence.add(ritual);
            }
        }

        // 3. Cantrips to build storm and find more pieces
        sequence.addAll(cantripsInHand);

        // 4. Engine card if we have one (Past in Flames, etc.)
        if (engineInHand != null) {
            sequence.add(engineInHand);
        }

        // 5. Storm payoff last
        if (payoffInHand != null) {
            sequence.add(payoffInHand);
        }

        return sequence;
    }

    @Override
    public Set<String> getComboPieces() {
        return Collections.unmodifiableSet(allComboPieces);
    }

    @Override
    public int getPriority() {
        // Storm is a powerful but complex combo
        return 85;
    }

    @Override
    public String getExpectedOutcome() {
        return "lethal storm damage or tokens";
    }

    private int getStormCount(Game game, UUID playerId) {
        // Try to get storm count from watcher
        Watcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher instanceof CastSpellLastTurnWatcher) {
            return ((CastSpellLastTurnWatcher) watcher).getAmountOfSpellsPlayerCastOnCurrentTurn(playerId);
        }
        return 0;
    }

    private Player getOpponent(Game game, UUID playerId) {
        Set<UUID> opponents = game.getOpponents(playerId);
        if (!opponents.isEmpty()) {
            return game.getPlayer(opponents.iterator().next());
        }
        return null;
    }

    @Override
    public String toString() {
        return "StormComboPattern";
    }
}
