package mage.player.ai.combo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * AI: Holds the result of combo detection including confidence level,
 * which pieces are found, and what's still needed.
 *
 * @author duxbuse
 */
public class ComboDetectionResult {

    public enum ComboState {
        NOT_DETECTED,      // Combo pieces not found in deck
        PARTIAL,           // Some pieces found, not all
        READY_IN_DECK,     // All pieces in deck, need to draw
        READY_IN_HAND,     // All pieces in hand, need mana/setup
        EXECUTABLE         // Can execute combo now
    }

    private final String comboId;
    private final ComboState state;
    private final double confidence;  // 0.0 to 1.0
    private final Set<String> foundPieces;
    private final Set<String> missingPieces;
    private final Set<String> piecesInHand;
    private final Set<String> piecesOnBattlefield;
    private final Set<String> piecesInGraveyard;
    private final String notes;

    private ComboDetectionResult(Builder builder) {
        this.comboId = builder.comboId;
        this.state = builder.state;
        this.confidence = builder.confidence;
        this.foundPieces = Collections.unmodifiableSet(new HashSet<>(builder.foundPieces));
        this.missingPieces = Collections.unmodifiableSet(new HashSet<>(builder.missingPieces));
        this.piecesInHand = Collections.unmodifiableSet(new HashSet<>(builder.piecesInHand));
        this.piecesOnBattlefield = Collections.unmodifiableSet(new HashSet<>(builder.piecesOnBattlefield));
        this.piecesInGraveyard = Collections.unmodifiableSet(new HashSet<>(builder.piecesInGraveyard));
        this.notes = builder.notes;
    }

    public String getComboId() {
        return comboId;
    }

    public ComboState getState() {
        return state;
    }

    public double getConfidence() {
        return confidence;
    }

    public Set<String> getFoundPieces() {
        return foundPieces;
    }

    public Set<String> getMissingPieces() {
        return missingPieces;
    }

    public Set<String> getPiecesInHand() {
        return piecesInHand;
    }

    public Set<String> getPiecesOnBattlefield() {
        return piecesOnBattlefield;
    }

    public Set<String> getPiecesInGraveyard() {
        return piecesInGraveyard;
    }

    public String getNotes() {
        return notes;
    }

    public boolean isDetected() {
        return state != ComboState.NOT_DETECTED;
    }

    public boolean isExecutable() {
        return state == ComboState.EXECUTABLE;
    }

    /**
     * Calculate a score bonus based on combo progress.
     * Used by GameStateEvaluator2 to boost AI decisions toward combos.
     *
     * @return score bonus (0 to ~10000+ depending on state)
     */
    public int getScoreBonus() {
        switch (state) {
            case NOT_DETECTED:
                return 0;
            case PARTIAL:
                // Small bonus per piece found (encourages keeping combo pieces)
                return (int) (confidence * 300);
            case READY_IN_DECK:
                // Moderate bonus - we have the combo, just need to draw
                return (int) (confidence * 500);
            case READY_IN_HAND:
                // Large bonus - combo assembled, need to play it
                return (int) (confidence * 2000);
            case EXECUTABLE:
                // Huge bonus - execute the combo!
                return 10000;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        return String.format("ComboDetectionResult[%s, state=%s, confidence=%.2f, found=%d, missing=%d]",
                comboId, state, confidence, foundPieces.size(), missingPieces.size());
    }

    public static Builder builder(String comboId) {
        return new Builder(comboId);
    }

    public static ComboDetectionResult notDetected(String comboId) {
        return new Builder(comboId)
                .state(ComboState.NOT_DETECTED)
                .confidence(0.0)
                .build();
    }

    public static class Builder {
        private final String comboId;
        private ComboState state = ComboState.NOT_DETECTED;
        private double confidence = 0.0;
        private Set<String> foundPieces = new HashSet<>();
        private Set<String> missingPieces = new HashSet<>();
        private Set<String> piecesInHand = new HashSet<>();
        private Set<String> piecesOnBattlefield = new HashSet<>();
        private Set<String> piecesInGraveyard = new HashSet<>();
        private String notes = "";

        private Builder(String comboId) {
            this.comboId = comboId;
        }

        public Builder state(ComboState state) {
            this.state = state;
            return this;
        }

        public Builder confidence(double confidence) {
            this.confidence = Math.max(0.0, Math.min(1.0, confidence));
            return this;
        }

        public Builder foundPieces(Set<String> foundPieces) {
            this.foundPieces = foundPieces;
            return this;
        }

        public Builder missingPieces(Set<String> missingPieces) {
            this.missingPieces = missingPieces;
            return this;
        }

        public Builder piecesInHand(Set<String> piecesInHand) {
            this.piecesInHand = piecesInHand;
            return this;
        }

        public Builder piecesOnBattlefield(Set<String> piecesOnBattlefield) {
            this.piecesOnBattlefield = piecesOnBattlefield;
            return this;
        }

        public Builder piecesInGraveyard(Set<String> piecesInGraveyard) {
            this.piecesInGraveyard = piecesInGraveyard;
            return this;
        }

        public Builder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public ComboDetectionResult build() {
            return new ComboDetectionResult(this);
        }
    }
}
