package mage.player.ai.synergy;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * AI: Holds the result of synergy detection including what pieces are found
 * and the calculated score bonus.
 *
 * @author duxbuse
 */
public class SynergyDetectionResult {

    public enum SynergyState {
        NOT_DETECTED,       // No synergy pieces found
        ENABLER_ONLY,       // Have enabler(s) but no payoffs
        PAYOFF_ONLY,        // Have payoff(s) but no enablers
        PARTIAL,            // Have some of each but synergy not yet active
        READY,              // All pieces available, can be activated
        ACTIVE              // Synergy is currently working (enabler + payoff in play)
    }

    private final String synergyId;
    private final SynergyType type;
    private final SynergyState state;
    private final Set<String> enablersFound;
    private final Set<String> payoffsFound;
    private final Set<String> enablersOnBattlefield;
    private final Set<String> payoffsOnBattlefield;
    private final int scoreBonus;
    private final String notes;

    private SynergyDetectionResult(Builder builder) {
        this.synergyId = builder.synergyId;
        this.type = builder.type;
        this.state = builder.state;
        this.enablersFound = Collections.unmodifiableSet(new HashSet<>(builder.enablersFound));
        this.payoffsFound = Collections.unmodifiableSet(new HashSet<>(builder.payoffsFound));
        this.enablersOnBattlefield = Collections.unmodifiableSet(new HashSet<>(builder.enablersOnBattlefield));
        this.payoffsOnBattlefield = Collections.unmodifiableSet(new HashSet<>(builder.payoffsOnBattlefield));
        this.scoreBonus = builder.scoreBonus;
        this.notes = builder.notes;
    }

    public String getSynergyId() {
        return synergyId;
    }

    public SynergyType getType() {
        return type;
    }

    public SynergyState getState() {
        return state;
    }

    public Set<String> getEnablersFound() {
        return enablersFound;
    }

    public Set<String> getPayoffsFound() {
        return payoffsFound;
    }

    public Set<String> getEnablersOnBattlefield() {
        return enablersOnBattlefield;
    }

    public Set<String> getPayoffsOnBattlefield() {
        return payoffsOnBattlefield;
    }

    public int getScoreBonus() {
        return scoreBonus;
    }

    public String getNotes() {
        return notes;
    }

    public boolean isDetected() {
        return state != SynergyState.NOT_DETECTED;
    }

    public boolean isActive() {
        return state == SynergyState.ACTIVE;
    }

    public boolean hasEnablerOnBattlefield() {
        return !enablersOnBattlefield.isEmpty();
    }

    public boolean hasPayoffOnBattlefield() {
        return !payoffsOnBattlefield.isEmpty();
    }

    /**
     * Get all synergy pieces found anywhere.
     */
    public Set<String> getAllFoundPieces() {
        Set<String> all = new HashSet<>(enablersFound);
        all.addAll(payoffsFound);
        return all;
    }

    /**
     * Get all synergy pieces currently on battlefield.
     */
    public Set<String> getPiecesOnBattlefield() {
        Set<String> all = new HashSet<>(enablersOnBattlefield);
        all.addAll(payoffsOnBattlefield);
        return all;
    }

    @Override
    public String toString() {
        return String.format("SynergyDetectionResult[%s, type=%s, state=%s, enablers=%d, payoffs=%d, bonus=%d]",
                synergyId, type, state, enablersFound.size(), payoffsFound.size(), scoreBonus);
    }

    public static Builder builder(String synergyId, SynergyType type) {
        return new Builder(synergyId, type);
    }

    public static SynergyDetectionResult notDetected(String synergyId, SynergyType type) {
        return new Builder(synergyId, type)
                .state(SynergyState.NOT_DETECTED)
                .scoreBonus(0)
                .build();
    }

    public static class Builder {
        private final String synergyId;
        private final SynergyType type;
        private SynergyState state = SynergyState.NOT_DETECTED;
        private Set<String> enablersFound = new HashSet<>();
        private Set<String> payoffsFound = new HashSet<>();
        private Set<String> enablersOnBattlefield = new HashSet<>();
        private Set<String> payoffsOnBattlefield = new HashSet<>();
        private int scoreBonus = 0;
        private String notes = "";

        private Builder(String synergyId, SynergyType type) {
            this.synergyId = synergyId;
            this.type = type;
        }

        public Builder state(SynergyState state) {
            this.state = state;
            return this;
        }

        public Builder enablersFound(Set<String> enablersFound) {
            this.enablersFound = enablersFound;
            return this;
        }

        public Builder payoffsFound(Set<String> payoffsFound) {
            this.payoffsFound = payoffsFound;
            return this;
        }

        public Builder enablersOnBattlefield(Set<String> enablersOnBattlefield) {
            this.enablersOnBattlefield = enablersOnBattlefield;
            return this;
        }

        public Builder payoffsOnBattlefield(Set<String> payoffsOnBattlefield) {
            this.payoffsOnBattlefield = payoffsOnBattlefield;
            return this;
        }

        public Builder scoreBonus(int scoreBonus) {
            this.scoreBonus = scoreBonus;
            return this;
        }

        public Builder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public SynergyDetectionResult build() {
            return new SynergyDetectionResult(this);
        }
    }
}
