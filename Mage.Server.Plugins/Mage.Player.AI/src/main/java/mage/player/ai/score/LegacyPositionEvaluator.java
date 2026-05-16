package mage.player.ai.score;

import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * Adapter for the current AI scoring implementation.
 */
public enum LegacyPositionEvaluator implements PositionEvaluator {
    INSTANCE;

    @Override
    public GameStateEvaluator2.PlayerEvaluateScore evaluate(UUID playerId, Game game) {
        return GameStateEvaluator2.evaluate(playerId, game);
    }

    @Override
    public GameStateEvaluator2.PlayerEvaluateScore evaluate(UUID playerId, Game game, boolean useCombatPermanentScore) {
        return GameStateEvaluator2.evaluate(playerId, game, useCombatPermanentScore);
    }

    @Override
    public int evaluatePermanent(Permanent permanent, Game game, boolean useCombatPermanentScore) {
        return GameStateEvaluator2.evaluatePermanent(permanent, game, useCombatPermanentScore);
    }
}
