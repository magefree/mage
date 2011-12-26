package mage.player.ai.ma.optimizers;

import mage.abilities.Ability;
import mage.game.Game;

import java.util.List;

/**
 * Interface for ai optimizer that cuts the tree of decision.
 *
 * @author ayratn
 */
public interface TreeOptimizer {

    /**
     * Optimize provided actions removing those of them that are redundant or lead to combinatorial explosion.
     *
     * @param game
     * @param actions
     */
    void optimize(Game game, List<Ability> actions);
}
