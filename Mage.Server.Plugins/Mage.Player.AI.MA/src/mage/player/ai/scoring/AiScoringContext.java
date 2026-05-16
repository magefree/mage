package mage.player.ai.scoring;

import mage.abilities.Ability;
import mage.game.Game;

import java.util.UUID;

/**
 * Immutable input for top-level AI candidate scoring modules.
 */
public final class AiScoringContext {

    private final Game decisionGame;
    private final Game finalGame;
    private final UUID playerId;
    private final Ability action;
    private final int baseScore;

    private AiScoringContext(Game decisionGame, Game finalGame, UUID playerId, Ability action, int baseScore) {
        this.decisionGame = decisionGame;
        this.finalGame = finalGame;
        this.playerId = playerId;
        this.action = action;
        this.baseScore = baseScore;
    }

    public static AiScoringContext candidate(Game decisionGame, Game finalGame, UUID playerId, Ability action, int baseScore) {
        return new AiScoringContext(decisionGame, finalGame, playerId, action, baseScore);
    }

    public Game getDecisionGame() {
        return decisionGame;
    }

    public Game getFinalGame() {
        return finalGame;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public Ability getAction() {
        return action;
    }

    public int getBaseScore() {
        return baseScore;
    }
}
