package mage.player.ai.scoring;

import mage.player.ai.AiStrategyScore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Runs top-level candidate scoring modules in a stable order and combines their contributions.
 */
public final class AiScoringEngine {

    private final List<AiScoreModule> modules;
    private final int maxAppliedModifier;

    private AiScoringEngine(List<AiScoreModule> modules, int maxAppliedModifier) {
        this.modules = Collections.unmodifiableList(new ArrayList<>(modules));
        this.maxAppliedModifier = Math.max(0, maxAppliedModifier);
    }

    public static AiScoringEngine of(AiScoreModule... modules) {
        if (modules == null || modules.length == 0) {
            return new AiScoringEngine(Collections.emptyList(), Integer.MAX_VALUE);
        }
        return new AiScoringEngine(Arrays.asList(modules), Integer.MAX_VALUE);
    }

    public static AiScoringEngine of(List<AiScoreModule> modules) {
        return of(modules, Integer.MAX_VALUE);
    }

    public static AiScoringEngine of(List<AiScoreModule> modules, int maxAppliedModifier) {
        if (modules == null || modules.isEmpty()) {
            return new AiScoringEngine(Collections.emptyList(), maxAppliedModifier);
        }
        return new AiScoringEngine(modules, maxAppliedModifier);
    }

    public AiStrategyScore evaluate(AiScoringContext context) {
        if (context == null) {
            return AiStrategyScore.none(0);
        }
        if (modules.isEmpty()) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        int rawModifier = 0;
        int appliedModifier = 0;
        List<AiStrategyScore.Contribution> contributions = new ArrayList<>();
        for (AiScoreModule module : modules) {
            if (module == null) {
                continue;
            }
            AiStrategyScore score = module.evaluate(context);
            if (score == null || !score.hasStrategySignal()) {
                continue;
            }
            rawModifier = saturatedAdd(rawModifier, score.getRawModifier());
            appliedModifier = saturatedAdd(appliedModifier, score.getAppliedModifier());
            contributions.addAll(score.getContributions());
        }
        appliedModifier = clamp(appliedModifier, -maxAppliedModifier, maxAppliedModifier);

        if (rawModifier == 0 && appliedModifier == 0 && contributions.isEmpty()) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        return AiStrategyScore.of(context.getBaseScore(), rawModifier, appliedModifier, contributions);
    }

    public void cleanUpOnMatchEnd() {
        for (AiScoreModule module : modules) {
            if (module != null) {
                module.cleanUpOnMatchEnd();
            }
        }
    }

    private static int saturatedAdd(int left, int right) {
        long value = (long) left + right;
        if (value > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (value < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return (int) value;
    }

    private static int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }
}
