package mage.player.ai.combo.patterns;

import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.combo.ComboDetectionResult;
import mage.player.ai.combo.ComboDetectionResult.ComboState;
import mage.player.ai.combo.ComboPattern;
import mage.players.Player;

import java.util.*;

/**
 * AI: Generic two-card infinite combo pattern.
 * Loaded from infinite-combos.txt, represents combos where two specific
 * cards together create an infinite loop or game-winning condition.
 *
 * @author Claude
 */
public class TwoCardComboPattern implements ComboPattern {

    private final String card1;
    private final String card2;
    private final String comboId;
    private final Set<String> comboPieces;

    public TwoCardComboPattern(String card1, String card2) {
        // Normalize order for consistent ID
        if (card1.compareTo(card2) <= 0) {
            this.card1 = card1;
            this.card2 = card2;
        } else {
            this.card1 = card2;
            this.card2 = card1;
        }
        this.comboId = "two-card:" + this.card1 + "+" + this.card2;
        this.comboPieces = new HashSet<>(Arrays.asList(card1, card2));
    }

    @Override
    public String getComboId() {
        return comboId;
    }

    @Override
    public String getComboName() {
        return card1 + " + " + card2;
    }

    @Override
    public ComboDetectionResult detectCombo(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return ComboDetectionResult.notDetected(comboId);
        }

        Set<String> foundPieces = new HashSet<>();
        Set<String> missingPieces = new HashSet<>();
        Set<String> piecesInHand = new HashSet<>();
        Set<String> piecesOnBattlefield = new HashSet<>();
        Set<String> piecesInGraveyard = new HashSet<>();

        boolean card1Found = false;
        boolean card2Found = false;
        boolean card1InHand = false;
        boolean card2InHand = false;
        boolean card1OnBattlefield = false;
        boolean card2OnBattlefield = false;

        // Check hand
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (name.equals(card1)) {
                card1Found = true;
                card1InHand = true;
                piecesInHand.add(card1);
            } else if (name.equals(card2)) {
                card2Found = true;
                card2InHand = true;
                piecesInHand.add(card2);
            }
        }

        // Check battlefield
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            String name = permanent.getName();
            if (name.equals(card1)) {
                card1Found = true;
                card1OnBattlefield = true;
                piecesOnBattlefield.add(card1);
            } else if (name.equals(card2)) {
                card2Found = true;
                card2OnBattlefield = true;
                piecesOnBattlefield.add(card2);
            }
        }

        // Check library (for deck detection)
        for (Card card : player.getLibrary().getCards(game)) {
            String name = card.getName();
            if (name.equals(card1) && !card1Found) {
                card1Found = true;
            } else if (name.equals(card2) && !card2Found) {
                card2Found = true;
            }
        }

        // Check graveyard (some combos work from graveyard)
        for (Card card : player.getGraveyard().getCards(game)) {
            String name = card.getName();
            if (name.equals(card1)) {
                piecesInGraveyard.add(card1);
                // Don't set found unless we have recursion
            } else if (name.equals(card2)) {
                piecesInGraveyard.add(card2);
            }
        }

        // Build found/missing sets
        if (card1Found || piecesInHand.contains(card1) || piecesOnBattlefield.contains(card1)) {
            foundPieces.add(card1);
        } else {
            missingPieces.add(card1);
        }
        if (card2Found || piecesInHand.contains(card2) || piecesOnBattlefield.contains(card2)) {
            foundPieces.add(card2);
        } else {
            missingPieces.add(card2);
        }

        // Determine state
        ComboState state;
        double confidence;

        if (foundPieces.size() == 0) {
            state = ComboState.NOT_DETECTED;
            confidence = 0.0;
        } else if (foundPieces.size() == 1) {
            state = ComboState.PARTIAL;
            confidence = 0.5;
        } else {
            // Both pieces found
            boolean bothAvailable = (card1InHand || card1OnBattlefield) && (card2InHand || card2OnBattlefield);
            boolean bothOnBattlefield = card1OnBattlefield && card2OnBattlefield;

            if (bothOnBattlefield) {
                // Both pieces already in play - combo should be executable
                state = ComboState.EXECUTABLE;
                confidence = 1.0;
            } else if (bothAvailable) {
                // Both in hand or one on battlefield, one in hand
                state = ComboState.READY_IN_HAND;
                confidence = 0.9;
            } else {
                // Both in deck but not drawn yet
                state = ComboState.READY_IN_DECK;
                confidence = 0.7;
            }
        }

        return ComboDetectionResult.builder(comboId)
                .state(state)
                .confidence(confidence)
                .foundPieces(foundPieces)
                .missingPieces(missingPieces)
                .piecesInHand(piecesInHand)
                .piecesOnBattlefield(piecesOnBattlefield)
                .piecesInGraveyard(piecesInGraveyard)
                .build();
    }

    @Override
    public boolean canExecuteNow(Game game, UUID playerId) {
        ComboDetectionResult result = detectCombo(game, playerId);
        return result.getState() == ComboState.EXECUTABLE;
    }

    @Override
    public List<String> getComboSequence(Game game, UUID playerId) {
        // For generic two-card combos, we just return both pieces
        // More specific patterns can override with proper sequencing
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return Collections.emptyList();
        }

        List<String> sequence = new ArrayList<>();
        boolean card1OnBattlefield = false;
        boolean card2OnBattlefield = false;

        // Check what's already on battlefield
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            String name = permanent.getName();
            if (name.equals(card1)) {
                card1OnBattlefield = true;
            } else if (name.equals(card2)) {
                card2OnBattlefield = true;
            }
        }

        // Add cards that need to be played (not already on battlefield)
        if (!card1OnBattlefield) {
            sequence.add(card1);
        }
        if (!card2OnBattlefield) {
            sequence.add(card2);
        }

        return sequence;
    }

    @Override
    public Set<String> getComboPieces() {
        return Collections.unmodifiableSet(comboPieces);
    }

    @Override
    public int getPriority() {
        // Two-card infinite combos are generally high priority
        // but lower than specific win-the-game combos like Oracle
        return 80;
    }

    @Override
    public String getExpectedOutcome() {
        return "infinite combo";
    }

    @Override
    public String toString() {
        return "TwoCardComboPattern[" + card1 + " + " + card2 + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TwoCardComboPattern that = (TwoCardComboPattern) o;
        return comboId.equals(that.comboId);
    }

    @Override
    public int hashCode() {
        return comboId.hashCode();
    }
}
