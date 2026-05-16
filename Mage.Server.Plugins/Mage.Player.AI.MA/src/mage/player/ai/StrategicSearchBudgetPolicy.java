package mage.player.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Strategic-only search budget policy for bounding expensive Mad AI simulations.
 */
public final class StrategicSearchBudgetPolicy {

    private StrategicSearchBudgetPolicy() {
    }

    public static Decision decide(Input input) {
        int effectiveDepth = input.maxDepth;
        List<String> reasons = new ArrayList<>();

        if (input.commander && input.battlefieldPermanentCount >= input.hugeBoardThreshold) {
            effectiveDepth = Math.min(effectiveDepth, 5);
            reasons.add("search-budget:huge-commander-board");
        } else if (input.commander && input.battlefieldPermanentCount >= input.largeBoardThreshold) {
            effectiveDepth = Math.min(effectiveDepth, 6);
            reasons.add("search-budget:large-commander-board");
        }

        if (input.turnNumber >= 35 && input.battlefieldPermanentCount >= 35) {
            effectiveDepth = Math.min(effectiveDepth, 6);
            reasons.add("search-budget:late-game-board");
        }

        if (input.legalActionCount > 25) {
            effectiveDepth -= 1;
            reasons.add("search-budget:legal-action-pressure");
        }

        effectiveDepth = Math.max(input.minDepth, effectiveDepth);

        boolean heapStop = input.heapGuardEnabled
                && (input.usedHeapRatio >= input.heapGuardMaxUsedRatio
                || input.freeHeapMb < input.heapGuardMinFreeMb);
        if (heapStop) {
            reasons.add("search-budget:heap-pressure");
            reasons.add("search-budget:heap-stop");
        }

        int effectiveMaxNodes = input.maxNodes;
        if (effectiveDepth < input.maxDepth && input.maxDepth > 0) {
            effectiveMaxNodes = Math.max(1, Math.min(input.maxNodes, input.maxNodes * effectiveDepth / input.maxDepth));
            reasons.add("search-budget:max-nodes");
        }
        if (effectiveDepth != input.maxDepth) {
            reasons.add("search-budget:effective-depth");
        }

        return new Decision(
                effectiveDepth,
                effectiveMaxNodes,
                heapStop,
                reasons
        );
    }

    public static final class Input {
        public final boolean commander;
        public final int turnNumber;
        public final int battlefieldPermanentCount;
        public final int stackSize;
        public final int legalActionCount;
        public final int maxDepth;
        public final int maxNodes;
        public final int minDepth;
        public final int largeBoardThreshold;
        public final int hugeBoardThreshold;
        public final boolean heapGuardEnabled;
        public final double usedHeapRatio;
        public final long freeHeapMb;
        public final double heapGuardMaxUsedRatio;
        public final long heapGuardMinFreeMb;

        public Input(boolean commander,
                     int turnNumber,
                     int battlefieldPermanentCount,
                     int stackSize,
                     int legalActionCount,
                     int maxDepth,
                     int maxNodes,
                     int minDepth,
                     int largeBoardThreshold,
                     int hugeBoardThreshold,
                     boolean heapGuardEnabled,
                     double usedHeapRatio,
                     long freeHeapMb,
                     double heapGuardMaxUsedRatio,
                     long heapGuardMinFreeMb) {
            this.commander = commander;
            this.turnNumber = turnNumber;
            this.battlefieldPermanentCount = battlefieldPermanentCount;
            this.stackSize = stackSize;
            this.legalActionCount = legalActionCount;
            this.maxDepth = maxDepth;
            this.maxNodes = maxNodes;
            this.minDepth = minDepth;
            this.largeBoardThreshold = largeBoardThreshold;
            this.hugeBoardThreshold = hugeBoardThreshold;
            this.heapGuardEnabled = heapGuardEnabled;
            this.usedHeapRatio = usedHeapRatio;
            this.freeHeapMb = freeHeapMb;
            this.heapGuardMaxUsedRatio = heapGuardMaxUsedRatio;
            this.heapGuardMinFreeMb = heapGuardMinFreeMb;
        }
    }

    public static final class Decision {
        private final int effectiveDepth;
        private final int maxNodes;
        private final boolean stopForHeapPressure;
        private final List<String> reasons;

        private Decision(int effectiveDepth, int maxNodes, boolean stopForHeapPressure, List<String> reasons) {
            this.effectiveDepth = effectiveDepth;
            this.maxNodes = maxNodes;
            this.stopForHeapPressure = stopForHeapPressure;
            this.reasons = Collections.unmodifiableList(new ArrayList<>(reasons));
        }

        public int getEffectiveDepth() {
            return effectiveDepth;
        }

        public int getMaxNodes() {
            return maxNodes;
        }

        public boolean isStopForHeapPressure() {
            return stopForHeapPressure;
        }

        public List<String> getReasons() {
            return reasons;
        }

        public boolean hasChanges(int originalDepth, int originalMaxNodes) {
            return stopForHeapPressure || effectiveDepth != originalDepth || maxNodes != originalMaxNodes || !reasons.isEmpty();
        }
    }
}
