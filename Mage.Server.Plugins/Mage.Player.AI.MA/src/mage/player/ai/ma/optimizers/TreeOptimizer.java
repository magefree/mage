package mage.player.ai.ma.optimizers;

import java.util.List;
import mage.abilities.Ability;
import mage.game.Game;

/**
 * AI: interface for ai optimizer that cuts the tree of decision.
 *
 * Warning, it's a static objects for all AI instances, don't store any data in it
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
