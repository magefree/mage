package mage.player.ai.score;

import mage.game.Game;
import mage.game.permanent.Permanent;

import java.io.Serializable;
import java.util.UUID;

/**
 * AI position evaluator abstraction.
 */
public interface PositionEvaluator extends Serializable {

    GameStateEvaluator2.PlayerEvaluateScore evaluate(UUID playerId, Game game);

    GameStateEvaluator2.PlayerEvaluateScore evaluate(UUID playerId, Game game, boolean useCombatPermanentScore);

    int evaluatePermanent(Permanent permanent, Game game, boolean useCombatPermanentScore);
}
