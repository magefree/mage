package mage.player.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Optional deck-strategy adjustment applied on top of the normal game-state score.
 */
public final class AiStrategyScore {

    private static final int MAX_REAL_SCORE = Integer.MAX_VALUE - 1;
    private static final int MIN_REAL_SCORE = Integer.MIN_VALUE + 1;

    private final int baseScore;
    private final int rawModifier;
    private final int appliedModifier;
    private final int adjustedScore;
    private final List<Contribution> contributions;

    private AiStrategyScore(int baseScore, int rawModifier, int appliedModifier, List<Contribution> contributions) {
        this.baseScore = baseScore;
        this.rawModifier = rawModifier;
        this.appliedModifier = appliedModifier;
        this.adjustedScore = saturatedAdd(baseScore, appliedModifier);
        this.contributions = Collections.unmodifiableList(new ArrayList<>(contributions));
    }

    public static AiStrategyScore none(int baseScore) {
        return new AiStrategyScore(baseScore, 0, 0, Collections.emptyList());
    }

    public static AiStrategyScore of(int baseScore, int rawModifier, int appliedModifier, List<Contribution> contributions) {
        return new AiStrategyScore(baseScore, rawModifier, appliedModifier, contributions);
    }

    private static int saturatedAdd(int left, int right) {
        long value = (long) left + right;
        if (value > MAX_REAL_SCORE) {
            return MAX_REAL_SCORE;
        }
        if (value < MIN_REAL_SCORE) {
            return MIN_REAL_SCORE;
        }
        return (int) value;
    }

    public int getBaseScore() {
        return baseScore;
    }

    public int getRawModifier() {
        return rawModifier;
    }

    public int getAppliedModifier() {
        return appliedModifier;
    }

    public int getAdjustedScore() {
        return adjustedScore;
    }

    public List<Contribution> getContributions() {
        return contributions;
    }

    public boolean hasStrategySignal() {
        return appliedModifier != 0 || rawModifier != 0 || !contributions.isEmpty();
    }

    public static final class Contribution {
        private final String label;
        private final int value;
        private final String detail;

        public Contribution(String label, int value, String detail) {
            this.label = label;
            this.value = value;
            this.detail = detail;
        }

        public String getLabel() {
            return label;
        }

        public int getValue() {
            return value;
        }

        public String getDetail() {
            return detail;
        }
    }
}
