package mage.player.ai.combo.patterns;

import mage.MageObject;
import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.player.ai.combo.ComboDetectionResult;
import mage.player.ai.combo.ComboDetectionResult.ComboState;
import mage.player.ai.combo.ComboPattern;
import mage.players.Player;

import java.util.*;

/**
 * AI: Thassa's Oracle / Laboratory Maniac combo pattern.
 * Detects decks trying to win by emptying their library with
 * Demonic Consultation, Tainted Pact, or similar effects.
 *
 * @author Claude
 */
public class ThassasOraclePattern implements ComboPattern {

    private static final String COMBO_ID = "thassas-oracle-combo";

    // Win conditions that win with empty library
    private static final Set<String> WIN_CONDITIONS = new HashSet<>(Arrays.asList(
            "Thassa's Oracle",
            "Laboratory Maniac",
            "Jace, Wielder of Mysteries"
    ));

    // Cards that exile your entire library
    private static final Set<String> LIBRARY_EXILERS = new HashSet<>(Arrays.asList(
            "Demonic Consultation",
            "Tainted Pact",
            "Doomsday",
            "Paradigm Shift",
            "Thought Lash",
            "Leveler"
    ));

    // Self-mill enablers (for backup plans)
    private static final Set<String> SELF_MILL = new HashSet<>(Arrays.asList(
            "Hermit Druid",
            "Mesmeric Orb",
            "Altar of Dementia",
            "Basalt Monolith",  // with Mesmeric Orb
            "Mirror-Mad Phantasm",
            "Traumatize",
            "Fleet Swallower"
    ));

    // Tutors to find combo pieces
    private static final Set<String> RELEVANT_TUTORS = new HashSet<>(Arrays.asList(
            "Demonic Tutor",
            "Vampiric Tutor",
            "Imperial Seal",
            "Mystical Tutor",
            "Personal Tutor",
            "Wishclaw Talisman",
            "Grim Tutor"
    ));

    private final Set<String> allComboPieces;

    public ThassasOraclePattern() {
        allComboPieces = new HashSet<>();
        allComboPieces.addAll(WIN_CONDITIONS);
        allComboPieces.addAll(LIBRARY_EXILERS);
        allComboPieces.addAll(SELF_MILL);
        // Don't include tutors in combo pieces - they're too generic
    }

    @Override
    public String getComboId() {
        return COMBO_ID;
    }

    @Override
    public String getComboName() {
        return "Thassa's Oracle Combo";
    }

    @Override
    public ComboDetectionResult detectCombo(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return ComboDetectionResult.notDetected(COMBO_ID);
        }

        Set<String> foundPieces = new HashSet<>();
        Set<String> missingPieces = new HashSet<>();
        Set<String> piecesInHand = new HashSet<>();
        Set<String> piecesOnBattlefield = new HashSet<>();
        Set<String> piecesInGraveyard = new HashSet<>();

        boolean hasWinCondition = false;
        boolean hasWinConditionInHand = false;
        boolean hasWinConditionOnBattlefield = false;
        boolean hasExiler = false;
        boolean hasExilerInHand = false;

        int librarySize = player.getLibrary().size();
        boolean libraryEmpty = librarySize == 0;
        boolean librarySmall = librarySize <= 5;

        // Check hand
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (WIN_CONDITIONS.contains(name)) {
                hasWinCondition = true;
                hasWinConditionInHand = true;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (LIBRARY_EXILERS.contains(name)) {
                hasExiler = true;
                hasExilerInHand = true;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (SELF_MILL.contains(name)) {
                piecesInHand.add(name);
                foundPieces.add(name);
            }
        }

        // Check battlefield
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            String name = permanent.getName();
            if (WIN_CONDITIONS.contains(name)) {
                hasWinCondition = true;
                hasWinConditionOnBattlefield = true;
                piecesOnBattlefield.add(name);
                foundPieces.add(name);
            } else if (SELF_MILL.contains(name)) {
                piecesOnBattlefield.add(name);
                foundPieces.add(name);
            }
        }

        // Check library
        for (Card card : player.getLibrary().getCards(game)) {
            String name = card.getName();
            if (WIN_CONDITIONS.contains(name)) {
                hasWinCondition = true;
                foundPieces.add(name);
            } else if (LIBRARY_EXILERS.contains(name)) {
                hasExiler = true;
                foundPieces.add(name);
            } else if (SELF_MILL.contains(name)) {
                foundPieces.add(name);
            }
        }

        // Check graveyard
        for (Card card : player.getGraveyard().getCards(game)) {
            String name = card.getName();
            if (allComboPieces.contains(name)) {
                piecesInGraveyard.add(name);
            }
        }

        // Check if Oracle trigger is on stack (we may have already cast it)
        boolean oracleOnStack = isOracleTriggerOnStack(game, playerId);

        // Build missing pieces
        if (!hasWinCondition) {
            missingPieces.add("Thassa's Oracle (or Lab Man)");
        }
        if (!hasExiler && !libraryEmpty) {
            missingPieces.add("Demonic Consultation (or library exile)");
        }

        // Is this a consultation/oracle deck?
        boolean isOracleDeck = hasWinCondition && (hasExiler || foundPieces.stream().anyMatch(SELF_MILL::contains));

        if (!isOracleDeck) {
            return ComboDetectionResult.notDetected(COMBO_ID);
        }

        // Determine state
        ComboState state;
        double confidence;
        String notes;

        if (libraryEmpty && hasWinConditionInHand) {
            // Library empty and Oracle in hand - just cast it to win
            state = ComboState.EXECUTABLE;
            confidence = 1.0;
            notes = "Library empty, cast Oracle to win";
        } else if (oracleOnStack && hasExilerInHand) {
            // Oracle on stack, cast Consultation in response
            state = ComboState.EXECUTABLE;
            confidence = 1.0;
            notes = "Oracle trigger on stack, cast Consultation to win";
        } else if (hasWinConditionOnBattlefield && hasExilerInHand && librarySmall) {
            // Lab Man on battlefield, can mill/exile remaining library
            state = ComboState.EXECUTABLE;
            confidence = 0.95;
            notes = "Win condition on battlefield, can exile library";
        } else if (hasWinConditionInHand && hasExilerInHand) {
            // Have both pieces in hand - can attempt combo
            // Classic line: Cast Oracle, hold priority, cast Consultation
            state = ComboState.READY_IN_HAND;
            confidence = 0.9;
            notes = "Both pieces in hand, can attempt combo";
        } else if (hasWinCondition && hasExiler) {
            // Have both in deck
            state = ComboState.READY_IN_DECK;
            confidence = 0.7;
            notes = "Combo pieces in deck";
        } else {
            state = ComboState.PARTIAL;
            confidence = 0.4;
            notes = "Partial combo setup";
        }

        return ComboDetectionResult.builder(COMBO_ID)
                .state(state)
                .confidence(confidence)
                .foundPieces(foundPieces)
                .missingPieces(missingPieces)
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
        String oracleInHand = null;
        String consultationInHand = null;
        boolean labManOnBattlefield = false;
        boolean libraryEmpty = player.getLibrary().size() == 0;

        // Find pieces
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (name.equals("Thassa's Oracle")) {
                oracleInHand = name;
            } else if (WIN_CONDITIONS.contains(name) && oracleInHand == null) {
                oracleInHand = name;  // Lab Man or Jace as backup
            } else if (LIBRARY_EXILERS.contains(name) && consultationInHand == null) {
                consultationInHand = name;
            }
        }

        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            if (WIN_CONDITIONS.contains(permanent.getName())) {
                labManOnBattlefield = true;
            }
        }

        // Build sequence based on situation
        if (libraryEmpty && oracleInHand != null) {
            // Just cast Oracle
            sequence.add(oracleInHand);
        } else if (labManOnBattlefield && consultationInHand != null) {
            // Cast Consultation naming a card not in deck
            sequence.add(consultationInHand);
        } else if (oracleInHand != null && consultationInHand != null) {
            // Classic line:
            // 1. Cast Oracle (ETB trigger goes on stack)
            // 2. With trigger on stack, cast Consultation naming card not in deck
            // 3. Consultation resolves, exiles library
            // 4. Oracle trigger resolves, we win
            sequence.add(oracleInHand);
            sequence.add(consultationInHand);  // Cast in response to ETB
        }

        return sequence;
    }

    @Override
    public Set<String> getComboPieces() {
        return Collections.unmodifiableSet(allComboPieces);
    }

    @Override
    public int getPriority() {
        // Highest priority - this is a "win the game" combo
        return 100;
    }

    @Override
    public String getExpectedOutcome() {
        return "win the game";
    }

    /**
     * Check if Thassa's Oracle ETB trigger is on the stack
     */
    private boolean isOracleTriggerOnStack(Game game, UUID playerId) {
        for (StackObject stackObject : game.getStack()) {
            if (stackObject.getControllerId().equals(playerId)) {
                // Check if this is an Oracle ETB trigger
                String sourceCardName = "";
                MageObject sourceObject = game.getObject(stackObject.getSourceId());
                if (sourceObject != null) {
                    sourceCardName = sourceObject.getName();
                }
                if (sourceCardName.equals("Thassa's Oracle")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "ThassasOraclePattern";
    }
}
