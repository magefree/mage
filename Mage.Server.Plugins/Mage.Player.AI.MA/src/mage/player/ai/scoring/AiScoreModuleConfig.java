package mage.player.ai.scoring;

import java.util.UUID;

/**
 * Player-scoped configuration used when building candidate scoring modules.
 */
public final class AiScoreModuleConfig {

    private final UUID playerId;
    private final int maxAppliedModifier;
    private final boolean applyModifiers;

    public AiScoreModuleConfig(UUID playerId, int maxAppliedModifier, boolean applyModifiers) {
        this.playerId = playerId;
        this.maxAppliedModifier = maxAppliedModifier;
        this.applyModifiers = applyModifiers;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getMaxAppliedModifier() {
        return maxAppliedModifier;
    }

    public boolean isApplyModifiers() {
        return applyModifiers;
    }
}
